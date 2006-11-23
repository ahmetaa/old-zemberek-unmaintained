package net.zemberek.yapi;

import net.zemberek.bilgi.KaynakYukleyici;
import net.zemberek.bilgi.araclar.DuzYaziKokOkuyucu;
import net.zemberek.bilgi.araclar.IkiliKokOkuyucu;
import net.zemberek.bilgi.araclar.IkiliKokYazici;
import net.zemberek.bilgi.araclar.KokOkuyucu;
import net.zemberek.bilgi.kokler.AgacSozluk;
import net.zemberek.bilgi.kokler.Sozluk;
import net.zemberek.islemler.BasitDenetlemeCebi;
import net.zemberek.islemler.DenetlemeCebi;
import net.zemberek.islemler.TurkceCozumlemeYardimcisi;
import net.zemberek.islemler.cozumleme.CozumlemeYardimcisi;
import net.zemberek.yapi.ek.EkYonetici;
import net.zemberek.yapi.ek.TemelEkYonetici;
import net.zemberek.yapi.kok.KokOzelDurumBilgisi;
import net.zemberek.yapi.kok.TurkceKokOzelDurumBilgisi;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    private final String bilgiDizini;

    private final String alfabeDosyaAdi;
    private final String ekDosyaAdi;
    private final String kokDosyaAdi;
    private final String cepDosyaAdi;
    private final String kokIstatistikDosyaAdi;

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
        char c = File.separatorChar;
        bilgiDizini = "kaynaklar" + c + dilAyarlari.locale().getLanguage() + c + "bilgi" + c;
        alfabeDosyaAdi = dosyaAdiUret("harf", "txt");
        ekDosyaAdi = dosyaAdiUret("ek", "xml");
        kokDosyaAdi = dosyaAdiUret("kokler", "bin");
        cepDosyaAdi = dosyaAdiUret("kelime_cebi", "txt");
        kokIstatistikDosyaAdi = dosyaAdiUret("kok_istatistik", "bin");
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
        if (alfabe == null) {
            try {
                alfabe = new Alfabe(alfabeDosyaAdi, dilAyarlari.locale().getLanguage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return alfabe;
    }

    public EkYonetici ekler() {
        if (ekYonetici != null) {
            return ekYonetici;
        } else {
            alfabe();
            try {
                ekYonetici = new TemelEkYonetici(
                        alfabe,
                        ekDosyaAdi,
                        dilAyarlari.ekUretici(alfabe),
                        dilAyarlari.ekOzelDurumUretici(alfabe),
                        dilAyarlari.baslangiEkAdlari());
            } catch (Exception e) {
                System.out.println("ek yonetici sinif uretilemiyor.");
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
                System.out.println("binary kok dosyasi bulunamadi. proje icerisinden calisildigi varsayilarak \n" +
                        "calisilan dizine goreceli olarak '" + kokDosyaAdi + "' dosyasi uretilmeye calisacak.\n" +
                        "eger duz yazki kok bilgilerine erisim saglanamazsa sistem kok bilgisine uretemeycektir. ");
                try {
                    ikiliKokDosyasiUret();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("kok bilgilerine erisim saglanamadigindan uygulama calismaya devam edemez.");
                    System.exit(-1);
                }
            }
            alfabe();
            kokOzelDurumlari();
            System.out.println("Ikili okuyucu uretiliyor:");
            try {
                KokOkuyucu okuyucu = new IkiliKokOkuyucu(kokDosyaAdi, ozelDurumBilgisi);
                System.out.println("Sozluk ve agac uretiliyor:" + dilAdi);
                sozluk = new AgacSozluk(okuyucu, alfabe, ozelDurumBilgisi);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("sozluk uretilemiyor.");
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
                ozelDurumBilgisi = new TurkceKokOzelDurumBilgisi(ekYonetici, alfabe);
            } catch (Exception e) {
                System.out.println("kok ozel durum bilgi nesnesi uretilemiyor.");
                e.printStackTrace();
            }
        }
        return ozelDurumBilgisi;
    }

    public DenetlemeCebi cep() {
        if (cep != null) {
            return cep;
        } else {
            try {
                cep = new BasitDenetlemeCebi(cepDosyaAdi);
            } catch (IOException e) {
                System.out.println("cep dosyasina (" + cepDosyaAdi + ") erisilemiyor. sistem cep kullanmayacak.");
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
            try {

                heceleyici = new TurkceHeceBulucu(alfabe);
            } catch (Exception e) {
                System.out.println("heceleyici nesnesi uretilemiyor. heceleme islemi basarisiz olacak.");
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
                yardimci = new TurkceCozumlemeYardimcisi(alfabe, cep);
            } catch (Exception e) {
                System.out.println("cozumleme yardimcisi nesnesi uretilemiyor.");
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
        kokOzelDurumlari();
        System.out.println("Ikili sozluk dosyasi olusturuluyor...");

        //kokleri duz yazi dosyalardan oku
        List tumKokler = new ArrayList();
        for (String dosyaAdi : dilAyarlari.duzYaziKokDosyalari()) {
            KokOkuyucu okuyucu = new DuzYaziKokOkuyucu(
                    dosyaAdi,
                    ozelDurumBilgisi,
                    alfabe,
                    dilAyarlari.kelimeTipiAdlari());
            List list = okuyucu.hepsiniOku();
            System.out.println("Okunan kok sayisi: " + list.size());
            tumKokler.addAll(list);
        }
        System.out.println("Toplam kok sayisi:" + tumKokler.size());

        AgacSozluk sozluk = new AgacSozluk(tumKokler, alfabe, ozelDurumBilgisi);

        if (new File(kokIstatistikDosyaAdi).exists()) {
            // istatistikleri koklere bagla.
            // TODO: bu kod icinde istatistik islemleri simdilik yer almiyor

        } else {
            System.out.println("istatistik dosyasina erisilemedi, kok dosyasi istatistik bilgisi icermeyecek.");
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
     * @throws IOException
     */
    public static void main(String[] args) throws Exception {
        new TurkceDilBilgisi(new TurkiyeTurkcesi()).ikiliKokDosyasiUret();
    }

}
