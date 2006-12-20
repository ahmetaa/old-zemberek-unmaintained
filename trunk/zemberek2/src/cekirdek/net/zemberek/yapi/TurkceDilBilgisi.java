/*
 *  ***** BEGIN LICENSE BLOCK *****
 *
 *  Version: MPL 1.1
 *
 *  The contents of this file are subject to the Mozilla Public License Version
 *  1.1 (the "License"); you may not use this file except in compliance with
 *  the License. You may obtain a copy of the License at
 *  http://www.mozilla.org/MPL/
 *
 *  Software distributed under the License is distributed on an "AS IS" basis,
 *  WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 *  for the specific language governing rights and limitations under the
 *  License.
 *
 *  The Original Code is "Zemberek Dogal Dil Isleme Kutuphanesi"
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akin, Mehmet D. Akin.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberek.yapi;

import net.zemberek.araclar.Kayitci;
import net.zemberek.bilgi.KaynakYukleyici;
import net.zemberek.bilgi.ZemberekAyarlari;
import net.zemberek.bilgi.araclar.DuzYaziKokOkuyucu;
import net.zemberek.bilgi.araclar.IkiliKokOkuyucu;
import net.zemberek.bilgi.araclar.IkiliKokYazici;
import net.zemberek.bilgi.araclar.KokOkuyucu;
import net.zemberek.bilgi.kokler.AgacSozluk;
import net.zemberek.bilgi.kokler.Sozluk;
import net.zemberek.islemler.BasitDenetlemeCebi;
import net.zemberek.islemler.DenetlemeCebi;
import net.zemberek.islemler.cozumleme.CozumlemeYardimcisi;
import net.zemberek.istatistik.BinaryIstatistikOkuyucu;
import net.zemberek.yapi.ek.EkOzelDurumUretici;
import net.zemberek.yapi.ek.EkUretici;
import net.zemberek.yapi.ek.EkYonetici;
import net.zemberek.yapi.kok.KokOzelDurumBilgisi;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Bir dil icin gerekli parametrelerin kolay uretimi icin kullanilan fabrika sinifi.
 * Dile ozel siniflara iliskin nesneler reflection ile uretilir. Hangi dilin hangi sinifa
 * sahip oldgusu gibi bilgiler ilklendirme sirasindaki giris parametresi olan DilAyarlari
 * nesnesinden edinilir.
 * <p/>
 * User: ahmet
 * Date: Sep 17, 2006
 */
public class TurkceDilBilgisi implements DilBilgisi {

    private DilAyarlari dilAyarlari;
    private String dilAdi;

    private Alfabe alfabe;
    private Sozluk sozluk;
    private DenetlemeCebi cep;
    private CozumlemeYardimcisi yardimci;
    private EkYonetici ekYonetici;
    private KokOzelDurumBilgisi ozelDurumBilgisi;
    private HeceBulucu heceleyici;

    private static Logger logger = Kayitci.kayitciUret(TurkceDilBilgisi.class);

    private final String bilgiDizini;

    private final String alfabeDosyaAdi;
    private final String ekDosyaAdi;
    private final String kokDosyaAdi;
    private final String cepDosyaAdi;
    private final String kokIstatistikDosyaAdi;

    private boolean cepKullan=true;

    /**
     * istenilen dilayarlari nesnesine gore cesitli parametreleri (bilgi dizin adi, kaynak dosyalarin locale
     * uyumlu adlari gibi) olusturur. bilgi dosyalari
     * kaynaklar/<locale>/bilgi/ ana dizini altinda yer almak zorundadir.
     *
     * @param dilAyarlari
     */
    public TurkceDilBilgisi(DilAyarlari dilAyarlari) {

        this.dilAyarlari = dilAyarlari;
        this.dilAdi=dilAyarlari.ad();
        char c = File.separatorChar;
        bilgiDizini = "kaynaklar" + c + dilAyarlari.locale().getLanguage() + c + "bilgi" + c;
        alfabeDosyaAdi = dosyaAdiUret("harf", "txt");
        ekDosyaAdi = dosyaAdiUret("ek", "xml");
        kokDosyaAdi = dosyaAdiUret("kokler", "bin");
        cepDosyaAdi = dosyaAdiUret("kelime_cebi", "txt");
        kokIstatistikDosyaAdi = dosyaAdiUret("kok_istatistik", "bin");
    }

    public TurkceDilBilgisi(DilAyarlari dilAyarlari, ZemberekAyarlari zemberekAyarlari) {
        this(dilAyarlari);
        this.cepKullan = zemberekAyarlari.cepKullan();
    }

    /**
     * kok_<locale>.uzanti dosya adini uretir.
     *
     * @param kok
     * @param uzanti
     * @return olusan kaynak dosyasi adi.
     */
    private String dosyaAdiUret(String kok, String uzanti) {
        return bilgiDizini + kok + '_' + dilAyarlari.locale().getLanguage() + '.' + uzanti;
    }

    public Alfabe alfabe() {
        if (alfabe != null) {
            return alfabe;
        } else
            try {
                logger.fine("Alfabe uretiliyor:" + dilAdi);
                Class clazz = dilAyarlari.alfabeSinifi();
                Constructor c = clazz.getConstructor(String.class, String.class);
                alfabe = (Alfabe) c.newInstance(alfabeDosyaAdi, dilAyarlari.locale().getLanguage());
            } catch (Exception e) {
                logger.severe("Alfabe uretilemiyor. muhtemel dosya erisim hatasi.");
                e.printStackTrace();
            }
        return alfabe;
    }

    public EkYonetici ekler() {
        if (ekYonetici != null) {
            return ekYonetici;
        } else {
            alfabe();
            try {
                logger.fine("Ek yonetici uretiliyor:" + dilAdi);
                Class clazz = dilAyarlari.ekYoneticiSinifi();
                Constructor c = clazz.getConstructor(
                        Alfabe.class,
                        String.class,
                        EkUretici.class,
                        EkOzelDurumUretici.class,
                        Map.class);
                ekYonetici = (EkYonetici) c.newInstance(
                        alfabe,
                        ekDosyaAdi,
                        dilAyarlari.ekUretici(alfabe),
                        dilAyarlari.ekOzelDurumUretici(alfabe),
                        dilAyarlari.baslangiEkAdlari());
            } catch (Exception e) {
                logger.severe("ek yonetici sinif uretilemiyor.");
                e.printStackTrace();
            }
        }
        return ekYonetici;
    }

    /**
     * Sozluk, daha dogrusu Kokleri tasiyan agac ve iliskili kok secicileri tasiyan nesneyi uretir
     * Proje gelistirime asamasinda, eger ikili kok-sozluk dosyasi (kokler_xx.bin) dosyasi mevcut
     * degilse once onu uretmeye calisir, daha sonra asil sozluk uretim islemini yapar.
     * Normal kosullarda dagitim jar icerisinde bu dosya yer alacagindan bu islem (bin dosya uretimi) atlanir.
     *
     * @return Sozluk
     */
    public Sozluk kokler() {
        if (sozluk != null) {
            return sozluk;
        } else {
            if (!new KaynakYukleyici().kaynakMevcutmu(kokDosyaAdi)) {
                logger.info("binary kok dosyasi bulunamadi. proje icerisinden calisildigi varsayilarak \n" +
                        "calisilan dizine goreceli olarak '" + kokDosyaAdi + "' dosyasi uretilmeye calisacak.\n" +
                        "eger duz yazki kok bilgilerine erisim saglanamazsa sistem kok bilgisine uretemeycektir. ");
                try {
                    ikiliKokDosyasiUret();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.severe("kok bilgilerine erisim saglanamadigindan uygulama calismaya devam edemez.");
                    System.exit(-1);
                }
            }
            kokOzelDurumlari();
            logger.fine("Ikili okuyucu uretiliyor:");
            try {
                KokOkuyucu okuyucu = new IkiliKokOkuyucu(kokDosyaAdi, ozelDurumBilgisi);
                logger.fine("Sozluk ve agac uretiliyor:" + dilAdi);
                sozluk = new AgacSozluk(okuyucu, alfabe, ozelDurumBilgisi);
            } catch (IOException e) {
                e.printStackTrace();
                logger.severe("sozluk uretilemiyor.");
            }
        }
        return sozluk;
    }

    public KokOzelDurumBilgisi kokOzelDurumlari() {
        if (ozelDurumBilgisi != null) {
            return ozelDurumBilgisi;
        } else {
            ekler();
            try {
                Class clazz = dilAyarlari.kokOzelDurumBilgisiSinifi();
                Constructor c = clazz.getConstructor(EkYonetici.class, Alfabe.class);
                ozelDurumBilgisi = (KokOzelDurumBilgisi) c.newInstance(ekYonetici, alfabe);
            } catch (Exception e) {
                logger.severe("kok ozel durum bilgi nesnesi uretilemiyor.");
                e.printStackTrace();
            }
        }
        return ozelDurumBilgisi;
    }

    public DenetlemeCebi cep() {
        if(!cepKullan) {
            logger.info("cep kullanilmayacak.");
            return null;
        }

        if (cep != null) {
            return cep;
        } else {
            try {
                cep = new BasitDenetlemeCebi(cepDosyaAdi);
            } catch (IOException e) {
                logger.warning("cep dosyasina (" + cepDosyaAdi + ") erisilemiyor. sistem cep kullanmayacak.");
                cep = null;
            }
        }
        return cep;
    }

    public HeceBulucu heceBulucu() {
        if (heceleyici != null) {
            return heceleyici;
        } else {
            alfabe();
            Class clazz = dilAyarlari.heceBulucuSinifi();
            try {
                Constructor c = clazz.getConstructor(Alfabe.class);
                heceleyici = (HeceBulucu) c.newInstance(alfabe);
            } catch (Exception e) {
                try {
                    Constructor c = clazz.getConstructor();
                    heceleyici = (HeceBulucu) c.newInstance();
                } catch (Exception e2) {
                    logger.warning("heceleyici nesnesi uretilemiyor. heceleme islemi basarisiz olacak.");
                }
            }
        }
        return heceleyici;
    }




    public CozumlemeYardimcisi cozumlemeYardimcisi() {
        if (yardimci != null) {
            return yardimci;
        } else {
            alfabe();
            cep();
            try {
                Class clazz = dilAyarlari.cozumlemeYardimcisiSinifi();
                Constructor c = clazz.getConstructor(
                        Alfabe.class,
                        DenetlemeCebi.class);
                yardimci = (CozumlemeYardimcisi) c.newInstance(alfabe, cep);
            } catch (Exception e) {
                logger.severe("cozumleme yardimcisi nesnesi uretilemiyor.");
                e.printStackTrace();
            }
        }
        return yardimci;
    }

    /**
     * Bu metod ile ikili kok bilgisi dosyasi (kokler_xx.bin uretilir.)
     * Eger uretim sirasinda istatistik bilgisi mevcutsa bu da kullanilir.
     *
     * @throws IOException
     */
    public void ikiliKokDosyasiUret() throws IOException {
        alfabe();
        ekler();
        kokOzelDurumlari();
        logger.info("Ikili sozluk dosyasi olusturuluyor...");

        //kokleri duz yazi dosyalardan oku
        List tumKokler = new ArrayList();
        for (String dosyaAdi : dilAyarlari.duzYaziKokDosyalari()) {
            KokOkuyucu okuyucu = new DuzYaziKokOkuyucu(
                    dosyaAdi,
                    ozelDurumBilgisi,
                    alfabe,
                    dilAyarlari.kokTipiAdlari());
            List list = okuyucu.hepsiniOku();
            logger.info("Okunan kok sayisi: " + list.size());
            tumKokler.addAll(list);
        }
        logger.info("Toplam kok sayisi:" + tumKokler.size());

        AgacSozluk sozluk = new AgacSozluk(tumKokler, alfabe, ozelDurumBilgisi);

        if (new File(kokIstatistikDosyaAdi).exists()) {
            // istatistikleri koklere bagla.
            BinaryIstatistikOkuyucu istatistikOkuyucu = new BinaryIstatistikOkuyucu();
            istatistikOkuyucu.initialize(kokIstatistikDosyaAdi);
            istatistikOkuyucu.oku(sozluk);
        } else {
            logger.warning("istatistik dosyasina erisilemedi, kok dosyasi istatistik bilgisi icermeyecek." + kokIstatistikDosyaAdi);
        }
        // kokleri ikili olarak kaydet.
        IkiliKokYazici ozelYazici = new IkiliKokYazici(kokDosyaAdi);
        ozelYazici.yaz(tumKokler);
    }

    /**
     * Ana sinif calistiginda ikiliKokDosyasiUret uret sinifini calistirir. Eger parametre olarak
     * dil ayar sinifi adi gonderilirse iliskili dil icin uretim yapar. aksi halde Turkiye Turkcesi icin
     * ikili kok-sozluk dosyasini olusturur.
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        Class c = Class.forName("net.zemberek.tr.yapi.TurkiyeTurkcesi");
        if (args.length > 0) {
            String dilAyarSinifi = args[0];
            c = Class.forName(dilAyarSinifi);
        }
        new TurkceDilBilgisi((DilAyarlari) c.newInstance()).ikiliKokDosyasiUret();
    }

}
