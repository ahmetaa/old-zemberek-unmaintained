/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

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
import net.zemberek.yapi.ek.EkKuralKelimesiCozumleyici;
import net.zemberek.yapi.ek.EkYonetici;
import net.zemberek.yapi.ek.XmlEkOkuyucu;
import net.zemberek.yapi.kok.KokOzelDurumBilgisi;

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
    private Heceleyici heceleyici;

    private static Logger logger = Kayitci.kayitciUret(TurkceDilBilgisi.class);

    private final String bilgiDizini;

    private final String alfabeDosyaAdi;
    private final String ekDosyaAdi;
    private final String kokDosyaAdi;
    private final String cepDosyaAdi;
    private final String kokIstatistikDosyaAdi;

    private boolean cepKullan = true;

    /**
     * istenilen dilayarlari nesnesine gore cesitli parametreleri (bilgi dizin adi, kaynak dosyalarin locale
     * uyumlu adlari gibi) olusturur. bilgi dosyalari
     * kaynaklar/<locale>/bilgi/ ana dizini altinda yer almak zorundadir.
     *
     * @param dilAyarlari
     */
    public TurkceDilBilgisi(DilAyarlari dilAyarlari) {

        this.dilAyarlari = dilAyarlari;
        this.dilAdi = dilAyarlari.ad();
        bilgiDizini = "kaynaklar/" + dilAyarlari.locale().getLanguage() + "/bilgi/";
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
    	//FIXME: Race condition?
        if (alfabe != null) {
            return alfabe;
        } else
            try {
                logger.fine("Alfabe uretiliyor:" + dilAdi);
                Class clazz = dilAyarlari.alfabeSinifi();
                Constructor c = clazz.getConstructor(String.class, String.class);
                alfabe = (Alfabe) c.newInstance(alfabeDosyaAdi, dilAyarlari.locale().getLanguage());
            } catch (Exception e) {
                logger.severe("Alfabe uretilemiyor. muhtemel dosya erisim hatasi."+ e.getMessage());
            }
        return alfabe;
    }

    public EkYonetici ekler() {
    	//FIXME: Race condition?
        if (ekYonetici != null) {
            return ekYonetici;
        } else {
            alfabe();
            try {
                EkKuralKelimesiCozumleyici kuralCozumleyici = new EkKuralKelimesiCozumleyici(alfabe, dilAyarlari.ekKuralBilgisi());
                XmlEkOkuyucu ekOkuyucu = new XmlEkOkuyucu(
                        ekDosyaAdi,
                        dilAyarlari.ekUretici(alfabe),
                        dilAyarlari.ekOzelDurumUretici(alfabe),
                        kuralCozumleyici);
                logger.fine("Ek yonetici uretiliyor:" + dilAdi);
                Class clazz = dilAyarlari.ekYoneticiSinifi();
                Constructor c = clazz.getConstructor(
                        Map.class,
                        XmlEkOkuyucu.class);
                ekYonetici = (EkYonetici) c.newInstance(dilAyarlari.baslangiEkAdlari(), ekOkuyucu);
            } catch (Exception e) {
                logger.severe("ek yonetici sinif uretilemiyor." + e.getMessage());
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
    	//FIXME: Race condition?
        if (sozluk != null) {
            return sozluk;
        } else {
            if (!new KaynakYukleyici().kaynakMevcutmu(kokDosyaAdi)) {
                logger.warning("binary kok dosyasi bulunamadi. proje icerisinden calisildigi varsayilarak \n" +
                        "calisilan dizine goreceli olarak '" + kokDosyaAdi + "' dosyasi uretilmeye calisacak.\n" +
                        "eger duz yazki kok bilgilerine erisim saglanamazsa sistem kok bilgisine uretemeycektir. ");
                try {
                    ikiliKokDosyasiUret();
                } catch (IOException e) {
                    logger.severe("kok bilgilerine erisim saglanamadigindan uygulama calismaya devam edemez." + e.getMessage());
                    return null;
                }
            }
            kokOzelDurumlari();
            logger.fine("Ikili okuyucu uretiliyor:");
            try {
                KokOkuyucu okuyucu = new IkiliKokOkuyucu(kokDosyaAdi, ozelDurumBilgisi);
                logger.fine("Sozluk ve agac uretiliyor:" + dilAdi);
                sozluk = new AgacSozluk(okuyucu, alfabe, ozelDurumBilgisi);
            } catch (IOException e) {
                logger.severe("sozluk uretilemiyor." + e.getMessage());
            }
        }
        return sozluk;
    }

    public KokOzelDurumBilgisi kokOzelDurumlari() {
    	//FIXME: Race condition?
        if (ozelDurumBilgisi != null) {
            return ozelDurumBilgisi;
        } else {
            ekler();
            try {
                Class clazz = dilAyarlari.kokOzelDurumBilgisiSinifi();
                Constructor c = clazz.getConstructor(EkYonetici.class, Alfabe.class);
                ozelDurumBilgisi = (KokOzelDurumBilgisi) c.newInstance(ekYonetici, alfabe);
            } catch (Exception e) {
                logger.severe("kok ozel durum bilgi nesnesi uretilemiyor."+ e.getMessage());
            }
        }
        return ozelDurumBilgisi;
    }

    public DenetlemeCebi denetlemeCebi() {
        if (!cepKullan) {
            logger.info("denetlemeCebi kullanilmayacak.");
            return null;
        }

      //FIXME: Race condition?
        if (cep != null) {
            return cep;
        } else {
            try {
                cep = new BasitDenetlemeCebi(cepDosyaAdi);
            } catch (IOException e) {
                logger.warning("denetlemeCebi dosyasina (" + cepDosyaAdi + ") erisilemiyor. sistem denetlemeCebi kullanmayacak.");
                cep = null;
            }
        }
        return cep;
    }

    public Heceleyici heceBulucu() {
    	//FIXME: Race condition?
        if (heceleyici != null) {
            return heceleyici;
        } else {
            alfabe();
            Class clazz = dilAyarlari.heceleyiciSinifi();
            try {
                Constructor c = clazz.getConstructor(Alfabe.class);
                heceleyici = (Heceleyici) c.newInstance(alfabe);
            } catch (Exception e) {
                try {
                    Constructor c = clazz.getConstructor();
                    heceleyici = (Heceleyici) c.newInstance();
                } catch (Exception e2) {
                    logger.warning("heceleyici nesnesi uretilemiyor. heceleme islemi basarisiz olacak."+ e2.getMessage());
                }
            }
        }
        return heceleyici;
    }


    public CozumlemeYardimcisi cozumlemeYardimcisi() {
    	//FIXME: Race condition?
        if (yardimci != null) {
            return yardimci;
        } else {
            alfabe();
            try {
                Class clazz = dilAyarlari.cozumlemeYardimcisiSinifi();
                Constructor c = clazz.getConstructor(Alfabe.class);
                yardimci = (CozumlemeYardimcisi) c.newInstance(alfabe);
            } catch (Exception e) {
                logger.severe("cozumleme yardimcisi nesnesi uretilemiyor."+ e.getMessage());
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
        List<Kok> tumKokler = new ArrayList<Kok>();
        for (String dosyaAdi : dilAyarlari.duzYaziKokDosyalari()) {
            KokOkuyucu okuyucu = new DuzYaziKokOkuyucu(
                    dosyaAdi,
                    ozelDurumBilgisi,
                    alfabe,
                    dilAyarlari.kokTipiAdlari());
            List<Kok> list = okuyucu.hepsiniOku();
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
