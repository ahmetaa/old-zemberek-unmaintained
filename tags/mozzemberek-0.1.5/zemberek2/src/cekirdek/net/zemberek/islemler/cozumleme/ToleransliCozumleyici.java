/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.islemler.cozumleme;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.zemberek.araclar.MetinAraclari;
import net.zemberek.bilgi.kokler.KokAdayiBulucu;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.ek.Ek;
import net.zemberek.yapi.ek.EkYonetici;

/**
 */
public class ToleransliCozumleyici {

    public static final Kelime[] BOS_KELIME_DIZISI = new Kelime[0];    

    public static final int TOLERANS = 1;
    private static Logger log = Logger.getLogger("ToleransliCozumleyici.class");
    private KokAdayiBulucu kokBulucu;
    private EkYonetici ekYonetici;
    private Alfabe alfabe;
    private CozumlemeYardimcisi yardimci;

    public ToleransliCozumleyici(KokAdayiBulucu kokBulucu,
                                 EkYonetici yonetici,
                                 Alfabe alfabe,
                                 CozumlemeYardimcisi yardimci) {
        this.kokBulucu = kokBulucu;
        this.ekYonetici = yonetici;
        this.alfabe = alfabe;
        this.yardimci = yardimci;
    }

    public Kelime[] cozumle(String strGiris) {
        String strIslenmis = alfabe.ayikla(strGiris);
        if (strIslenmis.length() == 0)
            return BOS_KELIME_DIZISI;
        List<Kok> kokler = kokBulucu.adayKokleriBul(strIslenmis);
        List<Kelime> cozumler = new ArrayList<Kelime>();
        if (log.isLoggable(Level.FINER)) log.finer("Giris: " + strIslenmis + ", Adaylar: " + kokler);

        HarfDizisi girisDizi = new HarfDizisi(strIslenmis, alfabe);
        boolean icerikDegisti = false;
        for (int i = kokler.size() - 1; i >= 0; i--) {
            Kok kok = kokler.get(i);
            HarfDizisi kokDizi = new HarfDizisi(kok.icerik(), alfabe);
            if (icerikDegisti) {
                girisDizi = new HarfDizisi(strIslenmis, alfabe);
            }
            //int kokHatasi=MetinAraclari.editDistance(kok.icerik(), strGiris.substring(0,kok.icerik().length()));
            int kokHatasi = 0;

            icerikDegisti = yardimci.kokGirisDegismiVarsaUygula(kok, kokDizi, girisDizi);

            if (log.isLoggable(Level.FINER)) log.finer("Aday:" + kok.icerik() + " tolerans:" + kokHatasi);
            if (MetinAraclari.duzeltmeMesafesiIcinde(kok.icerik(), strIslenmis, TOLERANS))
                cozumler.add(new Kelime(kok, alfabe));
            List<Kelime> sonuclar;
            if (TOLERANS > kokHatasi)
                sonuclar = coz(kok, kokDizi, girisDizi, TOLERANS - kokHatasi);
            else
                sonuclar = coz(kok, kokDizi, girisDizi, 0);
            cozumler.addAll(sonuclar);
        }
        for (Kelime kel: cozumler) {
            yardimci.kelimeBicimlendir(kel);
            if (Character.isUpperCase(strGiris.charAt(0)))
                kel.icerik().harfDegistir(0, alfabe.buyukHarf(kel.icerik().ilkHarf()));
        }
        return cozumler.toArray(new Kelime[cozumler.size()]);
    }

    private List<Kelime> coz(Kok kok, HarfDizisi kokDizi, HarfDizisi girisDizi, int tolerans) {

        Kelime kelime = new Kelime(kok, kokDizi);
        kelime.ekEkle(ekYonetici.ilkEkBelirle(kelime.kok()));
        BasitKelimeYigini kelimeYigini = new BasitKelimeYigini();

        List<Kelime> uygunSonuclar = new ArrayList<Kelime>();

        //analiz kelimesini kokler kokunden olustur.
        kelimeYigini.temizle();
        Ek bulunanEk = kelime.sonEk();

        int ardisilEkSirasi = 0;
        while (true) {
            //bulunan son ekten sonra gelebilecek eklerden siradakini al.
            Ek incelenenEk = bulunanEk.getArdisilEk(ardisilEkSirasi++);

            //siradaki ek yoksa incelenen ek yanlis demektir.
            // yigindan kelimenin onceki durumunu cek.
            if (incelenenEk == null) {
                //yigin bos ise sonuclar dondur.
                if (kelimeYigini.bosMu())
                    return uygunSonuclar;

                //kelimeyi ve bulunan eki onceki formuna donustur.
                BasitKelimeYigini.YiginKelime yiginKelime = kelimeYigini.al();
                kelime = yiginKelime.getKelime();
                bulunanEk = kelime.sonEk();
                ardisilEkSirasi = yiginKelime.getEkSirasi();
                continue;
            }

            //eger daha olusan kelime kok asamasinda ise (yani sadece YALIN eki eklenmisse)
            // ve kokun (kelime ile kok ayni ozel durumlara sahip) icinde bir ozel durum var ise
            // ozel durum denetlenir, yani kokun girilen ek ile degisip degismedigine bakilir.
            if (kelime.ekler().size() == 1 && kelime.kok().ozelDurumVarmi()) {
                if (!ozelDurumDenetle(kelime, girisDizi, incelenenEk, tolerans)) {
                    if (log.isLoggable(Level.FINEST)) log.finest("Ozel durum yanlis, ek:" + incelenenEk);
                    continue;
                }
            }

            //bazi eklerin olusumu, giris kelimesinin yapisina gore degisebilir.
            // ornegin giris "geleceGim" oldugu durumda gelecek zaman ekinin son harfinin
            // yumusamasi bilgisi ancak girise bakarak anlasilabilir. bu nedenle ek olusturma sirasinda giris
            // kullanilir

            HarfDizisi olusanEk = incelenenEk.cozumlemeIcinUret(kelime, girisDizi, null);
            //log.info("ek:" + incelenenEk + " olusum:" + olusanEk);
            if (olusanEk == null || olusanEk.length() == 0) {
                //log.info("bos ek.. " + incelenenEk);
                continue;
            }


            if (log.isLoggable(Level.FINER)) log.finest("Kok ve Olusan Ek:" + kelime.icerik() + " " + olusanEk);

            //Toleransli kiyaslama islemi burada yapiliyor. once gecici bir sekilde olusan kelimeye
            // olusan ek ekleniyor, ve giris ile toleransli kiyaslama yapiliyor. Eger kiyaslama
            // sonunda esik tolerans degeri asilmazsa dogru kabul edilip devam ediliyor.
            HarfDizisi olusum = new HarfDizisi(kelime.icerik());
            olusum.ekle(olusanEk);
            String olusumStr = olusum.toString();
            if (log.isLoggable(Level.FINEST)) log.finest("olusum:" + olusum);

            if (MetinAraclari.duzeltmeMesafesiIcinde(olusumStr, girisDizi.toString(), tolerans) ||
                    MetinAraclari.parcasiDuzeltmeMesafesiIcinde(olusumStr, girisDizi.toString(), tolerans)) {
                kelimeYigini.koy(kelime.clone(), ardisilEkSirasi);
                ardisilEkSirasi = 0;
                // ek ekleneceginde yumusama yapilip yapilmayacagi belirleniyor.. aci
                if (olusanEk.harf(0).sesliMi()
                        && kelime.sonHarf().sertMi()
                        && kelime.ekler().size() > 1
                        && olusanEk.ilkHarf().sertDonusum()!=null) {
                    kelime.icerik().sonHarfYumusat();
                }
                kelime.icerikEkle(olusanEk);
                kelime.ekEkle(incelenenEk);
                olusumStr = kelime.icerikStr();
                if (log.isLoggable(Level.FINEST)) log.finest("ekleme sonrasi olusan kelime: " + kelime.icerik());

                bulunanEk = incelenenEk;

                if (MetinAraclari.duzeltmeMesafesiIcinde(olusumStr, girisDizi.toString(), tolerans)) {
                    uygunSonuclar.add(kelime.clone());
                    if (log.isLoggable(Level.FINER)) log.finer("uygun kelime:" + kelime.icerik());
                }
/*
                        TurkceHarf ekIlkHarf = giris.harf(kelime.boy());
                        if (ekIlkHarf == TurkceAlfabe.TANIMSIZ_HARF)
                            return false;*/

            }
        }
    }

    private boolean ozelDurumDenetle(Kelime kelime, HarfDizisi girisDizi, Ek ek, int tolerans) {
        if (!kelime.kok().yapiBozucuOzelDurumVarmi())
            return true;
        HarfDizisi testKokIcerigi = kelime.kok().ozelDurumUygula(alfabe, ek);
        //if (log.isTraceEnabled()) log.trace("Ozel durum sonrasi:" + testKokIcerigi + "  ek:" + ek.getIsim());
        if (testKokIcerigi == null)
            return false;
        if (MetinAraclari.parcasiDuzeltmeMesafesiIcinde(testKokIcerigi.toString(), girisDizi.toString(), tolerans)) {
            kelime.setIcerik(new HarfDizisi(testKokIcerigi));
            //if (log.isTraceEnabled()) log.trace("basari, kelime:" + kelime.icerik());
            return true;
        } else
            kelime.setIcerik(new HarfDizisi(kelime.kok().icerik(), alfabe));
        //if (log.isTraceEnabled()) log.trace("kelime:" + kelime.icerik());
        return false;
    }

}
