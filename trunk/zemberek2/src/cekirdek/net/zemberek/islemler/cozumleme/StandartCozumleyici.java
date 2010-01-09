/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.islemler.cozumleme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.zemberek.bilgi.kokler.KokAdayiBulucu;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.TurkceHarf;
import net.zemberek.yapi.ek.Ek;
import net.zemberek.yapi.ek.EkYonetici;

/**
 * User: ahmet
 * Date: Jan 9, 2005
 */
public class StandartCozumleyici implements KelimeCozumleyici {

    public static final Kelime[] BOS_KELIME_DIZISI = new Kelime[0];

    private static Logger log = Logger.getLogger(StandartCozumleyici.class.getName());
    private final KokAdayiBulucu kokBulucu;
    private final HarfDizisiKiyaslayici harfDizisiKiyaslayici;
    private EkYonetici ekYonetici;
    private Alfabe alfabe;
    private CozumlemeYardimcisi yardimci;

    public StandartCozumleyici(KokAdayiBulucu kokBulucu,
                               HarfDizisiKiyaslayici kiyaslayci,
                               Alfabe alfabe,
                               EkYonetici ekYonetici,
                               CozumlemeYardimcisi yardimci) {
        this.kokBulucu = kokBulucu;
        this.harfDizisiKiyaslayici = kiyaslayci;
        this.ekYonetici = ekYonetici;
        this.alfabe = alfabe;
        this.yardimci = yardimci;
    }

    /**
     * eger hepsiniCozumle=true ise dogru olabilecek tum kok ve ek kombinasyonlarini
     * dondurur.
     * Eger flag false ise ilk dogru cozumu tek elemanli dizi seklinde
     * dondurur.bu yontem hiz gerektiren denetleme islemi icin kullanilir.
     *
     * @param strGiris String cinsinden giris kelimesi
     * @param seviye:  cozumnleme isleminin ne zaman sona erecegi bu bilesenin degerine gore
     *                 anlasilir.
     * @return tek ya da coklu kelime dizisi.
     */
    public Kelime[] cozumle(String strGiris, CozumlemeSeviyesi seviye) {

        //on islemler
        String strIslenmis = alfabe.ayikla(strGiris);
        if (!alfabe.cozumlemeyeUygunMu(strIslenmis) || strIslenmis.length() == 0)
            return BOS_KELIME_DIZISI;

        //kok adaylarinin bulunmasi.
        List<Kok> kokler = kokBulucu.adayKokleriBul(strIslenmis);

        if (log.isLoggable(Level.FINE)) log.fine("Giris: " + strGiris + ", Adaylar: " + kokler);
        HarfDizisi girisDizi = new HarfDizisi(strIslenmis, alfabe);
        boolean icerikDegisti = false;

        //cozumlerin bulunmasi
        List<Kelime> cozumler = new ArrayList<Kelime>(2);
        for (int i = kokler.size() - 1; i >= 0; i--) {
            if (icerikDegisti) {
                girisDizi = new HarfDizisi(strIslenmis, alfabe);
                icerikDegisti = false;
            }
            Kok kok = kokler.get(i);
            if (log.isLoggable(Level.FINER)) log.finest("Aday:" + kok.icerik());
            HarfDizisi kokDizi = new HarfDizisi(kok.icerik(), alfabe);

            //eger giris koke dogrudan esitse cozmeden kelimeyi olustur.
            if (harfDizisiKiyaslayici.kiyasla(kokDizi, girisDizi)) {
                Kelime kelime = kelimeUret(kok, kokDizi);
                if (yardimci.kelimeBicimiDenetle(kelime, strGiris)) {
                    if (seviye == CozumlemeSeviyesi.TEK_KOK) {
                        return new Kelime[]{kelime};
                    } else
                        cozumler.add(kelime);
                }
            } else {
                icerikDegisti = yardimci.kokGirisDegismiVarsaUygula(kok, kokDizi, girisDizi);
                List<Kelime> sonuclar = coz(kok, kokDizi, girisDizi, seviye);
                for (Kelime sonuc : sonuclar) {
                    if (yardimci.kelimeBicimiDenetle(sonuc, strGiris)) {
                        if (seviye == CozumlemeSeviyesi.TEK_KOK) {
                            return new Kelime[]{sonuc};
                        }
                        cozumler.add(sonuc);
                    }
                }
            }
        }
        return cozumler.toArray(new Kelime[cozumler.size()]);
    }

    public Kelime[] kokIcinCoz(Kok kok, String strGiris) {
        //on islemler
        String strIslenmis = alfabe.ayikla(strGiris);
        if (!alfabe.cozumlemeyeUygunMu(strIslenmis) || strIslenmis.length() == 0)
            return BOS_KELIME_DIZISI;
        HarfDizisi girisDizi = new HarfDizisi(strIslenmis, alfabe);

        //cozumlerin bulunmasi
        List<Kelime> cozumler = new ArrayList<Kelime>(2);

        if (log.isLoggable(Level.FINER)) log.finest("Aday:" + kok.icerik());
        HarfDizisi kokDizi = new HarfDizisi(kok.icerik(), alfabe);

        //eger giris koke dogrudan esitse cozmeden kelimeyi olustur.
        if (harfDizisiKiyaslayici.kiyasla(kokDizi, girisDizi)) {
            Kelime kelime = kelimeUret(kok, kokDizi);
            if (yardimci.kelimeBicimiDenetle(kelime, strGiris)) {
                cozumler.add(kelime);
            }
        } else {
            yardimci.kokGirisDegismiVarsaUygula(kok, kokDizi, girisDizi);
            List<Kelime> sonuclar = coz(kok, kokDizi, girisDizi, CozumlemeSeviyesi.TUM_KOK_VE_EKLER);
            for (Kelime sonuc : sonuclar) {
                if (yardimci.kelimeBicimiDenetle(sonuc, strGiris)) {
                    cozumler.add(sonuc);
                }
            }
        }
        return cozumler.toArray(new Kelime[cozumler.size()]);
    }

    public Kelime[] formatsizCoz(String strGiris, CozumlemeSeviyesi seviye) {
        //on islemler
        String strIslenmis = alfabe.ayikla(strGiris);
        if (!alfabe.cozumlemeyeUygunMu(strIslenmis) || strIslenmis.length() == 0)
            return BOS_KELIME_DIZISI;

        //kok adaylarinin bulunmasi.
        List<Kok> kokler = kokBulucu.adayKokleriBul(strIslenmis);

        if (log.isLoggable(Level.FINE)) log.fine("Giris: " + strGiris + ", Adaylar: " + kokler);
        HarfDizisi girisDizi = new HarfDizisi(strIslenmis, alfabe);
        boolean icerikDegisti = false;

        //cozumlerin bulunmasi
        List<Kelime> cozumler = new ArrayList<Kelime>(2);
        for (int i = kokler.size() - 1; i >= 0; i--) {
            if (icerikDegisti) {
                girisDizi = new HarfDizisi(strIslenmis, alfabe);
                icerikDegisti = false;
            }
            Kok kok = kokler.get(i);
            if (log.isLoggable(Level.FINER)) log.finest("Aday:" + kok.icerik());
            HarfDizisi kokDizi = new HarfDizisi(kok.icerik(), alfabe);

            //eger giris koke dogrudan esitse cozmeden kelimeyi olustur.
            if (harfDizisiKiyaslayici.kiyasla(kokDizi, girisDizi)) {
                Kelime kelime = kelimeUret(kok, kokDizi);
                if (seviye == CozumlemeSeviyesi.TEK_KOK) {
                    return new Kelime[]{kelime};
                } else
                    cozumler.add(kelime);
            } else {
                icerikDegisti = yardimci.kokGirisDegismiVarsaUygula(kok, kokDizi, girisDizi);
                List<Kelime> sonuclar = coz(kok, kokDizi, girisDizi, seviye);
                for (Kelime sonuc : sonuclar) {
                    if (seviye == CozumlemeSeviyesi.TEK_KOK) {
                        return new Kelime[]{sonuc};
                    }
                    cozumler.add(sonuc);
                }
            }
        }
        return cozumler.toArray(new Kelime[cozumler.size()]);
    }

    public boolean cozumlenebilir(String giris) {
        return cozumle(giris, CozumlemeSeviyesi.TEK_KOK).length == 1;
    }

    private Kelime kelimeUret(Kok kok, HarfDizisi dizi) {
        Kelime kelime = new Kelime(kok, dizi);
        kelime.ekEkle(ekYonetici.ilkEkBelirle(kelime.kok()));
        return kelime;
    }

    private List<Kelime> coz(Kok kok, HarfDizisi kokDizi, HarfDizisi giris, CozumlemeSeviyesi seviye) {

        Kelime kelime = kelimeUret(kok, kokDizi);
        BasitKelimeYigini kelimeYigini = new BasitKelimeYigini();
        Ek bulunanEk = kelime.sonEk();
        int ardisilEkSirasi = 0;
        List<Kelime> uygunSonuclar = Collections.emptyList();
        TurkceHarf ilkEkHarfi = giris.harf(kelime.boy());
        while (true) {
            //bulunan son ekten sonra gelebilecek eklerden siradakini al.
            Ek incelenenEk = bulunanEk.getArdisilEk(ardisilEkSirasi++);
            //siradaki ek yoksa incelenen ek yanlis demektir.
            // yigindan kelimenin onceki durumunu cek.
            if (incelenenEk == null) {
                //yigin bos ise basarisizlik.
                if (kelimeYigini.bosMu())
                    return uygunSonuclar;

                //kelimeyi ve bulunan eki onceki formuna donustur.
                BasitKelimeYigini.YiginKelime yiginKelime = kelimeYigini.al();
                kelime = yiginKelime.getKelime();
                bulunanEk = kelime.sonEk();
                ardisilEkSirasi = yiginKelime.getEkSirasi();
                ilkEkHarfi = giris.harf(kelime.boy());
                continue;
            }

            if (kelime.gercekEkYok() && kelime.kok().ozelDurumVarmi()) {
                if (!ozelDurumUygula(kelime, giris, incelenenEk)) {
                    continue;
                } else
                    ilkEkHarfi = giris.harf(kelime.boy());
            }

            if (!incelenenEk.ilkHarfDenetle(ilkEkHarfi))
                continue;

            HarfDizisi olusanEkIcerigi = incelenenEk.cozumlemeIcinUret(kelime, giris, harfDizisiKiyaslayici);
            if (olusanEkIcerigi == null || olusanEkIcerigi.length() == 0) {
                continue;
            }

            if (harfDizisiKiyaslayici.aradanKiyasla(giris,
                    olusanEkIcerigi,
                    kelime.boy())) {
                // ek dongusu testi
                //if (kelime.ekDongusuVarmi(incelenenEk)) continue;
                kelimeYigini.koy(kelime.clone(), ardisilEkSirasi);
                ardisilEkSirasi = 0;
                kelime.ekEkle(incelenenEk);
                kelime.icerikEkle(olusanEkIcerigi);
                ilkEkHarfi = giris.harf(kelime.boy());
                if (log.isLoggable(Level.FINE)) log.fine("ekleme sonrasi olusan kelime: " + kelime.icerik());

                bulunanEk = incelenenEk;

                if (harfDizisiKiyaslayici.kiyasla(kelime.icerik(), giris) && !incelenenEk.sonEkOlamazMi()) {
                    if (seviye != CozumlemeSeviyesi.TUM_KOK_VE_EKLER) {
                        uygunSonuclar = new ArrayList<Kelime>(1);
                        uygunSonuclar.add(kelime);
                        return uygunSonuclar;
                    }
                    if (uygunSonuclar.isEmpty())
                        uygunSonuclar = new ArrayList<Kelime>(2);
                    uygunSonuclar.add(kelime.clone());
                }
            }
        }
    }

    private boolean ozelDurumUygula(Kelime kelime, HarfDizisi giris, Ek ek) {
        if (!kelime.kok().yapiBozucuOzelDurumVarmi())
            return true;
        HarfDizisi testKokIcerigi = kelime.kok().ozelDurumUygula(alfabe, ek);
        if (testKokIcerigi == null) return false;
        if (log.isLoggable(Level.FINER))
            log.finer("Ozel durum sonrasi:" + testKokIcerigi + "  ek:" + ek.ad());
        kelime.setIcerik(testKokIcerigi);
        return harfDizisiKiyaslayici.bastanKiyasla(giris, testKokIcerigi);
    }
}

