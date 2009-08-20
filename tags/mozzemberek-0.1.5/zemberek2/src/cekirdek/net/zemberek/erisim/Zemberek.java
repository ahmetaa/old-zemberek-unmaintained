/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.erisim;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import net.zemberek.araclar.Kayitci;
import net.zemberek.bilgi.KaynakYukleyici;
import net.zemberek.bilgi.ZemberekAyarlari;
import net.zemberek.bilgi.kokler.KokAdayiBulucu;
import net.zemberek.bilgi.kokler.Sozluk;
import net.zemberek.islemler.AsciiDonusturucu;
import net.zemberek.islemler.DenetlemeCebi;
import net.zemberek.islemler.HeceIslemleri;
import net.zemberek.islemler.KelimeKokFrekansKiyaslayici;
import net.zemberek.islemler.KelimeTabanliKokBulucu;
import net.zemberek.islemler.KelimeUretici;
import net.zemberek.islemler.KokBulucu;
import net.zemberek.islemler.TurkceYaziTesti;
import net.zemberek.islemler.cozumleme.AsciiToleransliHDKiyaslayici;
import net.zemberek.islemler.cozumleme.CozumlemeSeviyesi;
import net.zemberek.islemler.cozumleme.KelimeCozumleyici;
import net.zemberek.islemler.cozumleme.KesinHDKiyaslayici;
import net.zemberek.islemler.cozumleme.OneriUretici;
import net.zemberek.islemler.cozumleme.StandartCozumleyici;
import net.zemberek.islemler.cozumleme.ToleransliCozumleyici;
import net.zemberek.yapi.DilAyarlari;
import net.zemberek.yapi.DilBilgisi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.TurkceDilBilgisi;
import net.zemberek.yapi.ek.Ek;

/**
 * <b>EN:</b>This is a facade for accessing the high level functions of the Zemberek library.
 * This class should be creatd only once per language.
 * <p/>
 * <b>TR:</b>Zemberek projesine ust seviye erisim icin kullanilan sinif.
 * Ilk olsum sirasinda kokler okuma ve agac olusumu nedeniyle belli bir miktar gecikme
 * yasanabilir. Bu sinifin her dil icin sadece bir defa olusturulmasi onerilir.
 */
public class Zemberek {

    static Logger log = Kayitci.kayitciUret(Zemberek.class);
    private KelimeCozumleyici cozumleyici;
    private KelimeUretici kelimeUretici;
    private KelimeCozumleyici asciiToleransliCozumleyici;
    private TurkceYaziTesti turkceTest;
    private OneriUretici oneriUretici;
    private AsciiDonusturucu asciiDonusturucu;
    private HeceIslemleri heceIslemleri;
    private ZemberekAyarlari ayarlar;
    private DilBilgisi dilBilgisi;
    private DenetlemeCebi denetlemeCebi;

    /**
     * Default constructor.
     *
     * @param dilayarlari
     */
    public Zemberek(DilAyarlari dilayarlari) {
        ayarlar = new ZemberekAyarlari(dilayarlari.locale().getLanguage());
        this.dilBilgisi = new TurkceDilBilgisi(dilayarlari, ayarlar);
        initialize();
    }

    /**
     * Dosya sisteminden zemberek properties dosyasini yukleyip ZemberekAyarlari nesnesine atar.
     *
     * @param disKonfigurasyon
     * @return
     * @throws IOException
     */
    public static ZemberekAyarlari ayarOlustur(String disKonfigurasyon) throws IOException {
        URI uri = new File(disKonfigurasyon).toURI();
        Properties props = new KaynakYukleyici().konfigurasyonYukle(uri);
        return new ZemberekAyarlari(props);
    }

    private void initialize() {
        //Sozluk hazirla.
        Sozluk kokler = dilBilgisi.kokler();
        //Normal denetleyici-cozumleyici olusumu
        KokAdayiBulucu kokBulucu = kokler.kokBulucuFactory().kesinKokBulucu();
        cozumleyici = new StandartCozumleyici(
                kokBulucu,
                new KesinHDKiyaslayici(),
                dilBilgisi.alfabe(),
                dilBilgisi.ekler(),
                dilBilgisi.cozumlemeYardimcisi());

        // ASCII-Turkce donusturucu icin tukce toleransli cozumleyici olusumu.
        KokAdayiBulucu turkceToleransliKokBulucu = kokler.kokBulucuFactory().asciiKokBulucu();
        asciiToleransliCozumleyici = new StandartCozumleyici(
                turkceToleransliKokBulucu,
                new AsciiToleransliHDKiyaslayici(),
                dilBilgisi.alfabe(),
                dilBilgisi.ekler(),
                dilBilgisi.cozumlemeYardimcisi());

        KokAdayiBulucu toleransliBulucu = kokler.kokBulucuFactory().toleransliKokBulucu(1);
        ToleransliCozumleyici toleransliCozumleyici = new ToleransliCozumleyici(
                toleransliBulucu,
                dilBilgisi.ekler(),
                dilBilgisi.alfabe(),
                dilBilgisi.cozumlemeYardimcisi());

        oneriUretici = new OneriUretici(
                dilBilgisi.alfabe(),
                dilBilgisi.cozumlemeYardimcisi(),
                cozumleyici,
                asciiToleransliCozumleyici,
                toleransliCozumleyici,
                ayarlar);

        turkceTest = new TurkceYaziTesti(cozumleyici, asciiToleransliCozumleyici);

        asciiDonusturucu = new AsciiDonusturucu(dilBilgisi.alfabe());
        heceIslemleri = new HeceIslemleri(dilBilgisi.alfabe(), dilBilgisi.heceBulucu());

        kelimeUretici = new KelimeUretici(dilBilgisi.alfabe(), dilBilgisi.ekler(), dilBilgisi.cozumlemeYardimcisi());

        denetlemeCebi = dilBilgisi.denetlemeCebi();
    }

    /**
     * return the word parser.
     *
     * @return cozumleyici
     */
    public KelimeCozumleyici cozumleyici() {
        return cozumleyici;
    }

    public KelimeUretici kelimeUretici() {
        return kelimeUretici;
    }

    public KelimeCozumleyici asciiToleransliCozumleyici() {
        return asciiToleransliCozumleyici;
    }

    /**
     * Accessor for the word suggestion producer.
     *
     * @return oneri uretici.
     */
    public OneriUretici oneriUretici() {
        return oneriUretici;
    }

    /**
     * Accessor for the syllable extractor.
     *
     * @return heceIslemleri
     */
    public HeceIslemleri heceleyici() {
        return heceIslemleri;
    }

    /**
     * performs spell checking
     * <p/>
     * girisin imla denetimini yapar. Eger varsa denetleme cebini kullanir.
     *
     * @param giris giris kelimesi
     * @return EN: true:spell checking successfull, false otherwise.
     *         TR: true:imla denetimi basarili. false: Denetim basarisiz.
     */
    public boolean kelimeDenetle(String giris) {
        if (denetlemeCebi != null)
            return denetlemeCebi.kontrol(giris) || cozumleyici.cozumlenebilir(giris);
        else return cozumleyici.cozumlenebilir(giris);
    }

    /**
     * performs morphological parsing of the word. Returns the possible solutions as a Kelime array.
     * Kelime object contains the root and a suffix list. kok() method can be used for accessing the
     * root. ekler() can be used for accessing the Ek object List.
     * <p/>
     * <p/>
     * giris kelimesinin olasi tum (kok+ekler) cozumlemelerini dondurur.
     *
     * @param giris giris kelimesi
     * @return Kelime sinifi cinsinden dizi. Eger dizinin boyu 0 ise kelime cozumlenemedi demektir.
     *         Kelime kokune erisim icin kok(), eklere erisim icin Ek cinsinden nesne listesi donduren
     *         ekler() metodu kullanilir.
     * @see net.zemberek.yapi.Kelime
     */
    public Kelime[] kelimeCozumle(String giris) {
        return cozumleyici.cozumle(giris, CozumlemeSeviyesi.TUM_KOK_VE_EKLER);
    }

    /**
     * performs morphological parsing of the word. Returns the possible solution(s) as a Kelime array.
     * Kelime object contains the root and a suffix list. kok() method can be used for accessing the
     * root. ekler() can be used for accessing the Ek object List.
     * <p/>
     * <p/>
     * giris kelimesinin olasi tum (kok+ekler) cozumlemelerini dondurur.
     *
     * @param giris giris kelimesi
     * @param strateji EN:defines the parsing strategy.
     *                 TR:cozumleme stratejisini belirler.
     * @return Kelime sinifi cinsinden dizi. Eger dizinin boyu 0 ise kelime cozumlenemedi demektir.
     *         Kelime kokune erisim icin kok(), eklere erisim icin Ek cinsinden nesne listesi donduren
     *         ekler() metodu kullanilir.
     * @see net.zemberek.yapi.Kelime
     */
    public Kelime[] kelimeCozumle(String giris, CozumlemeSeviyesi strateji) {
        return cozumleyici.cozumle(giris, strateji);
    }

    /**
     * giris kelimesinin ascii karakter toleransli olarak cozumleyip
     * Kelime cinsinden(kok+ekler) cozumlemelerini dondurur.
     * Birden cok cozumun oldugu durumda simdilik donen adaylarin
     * hangisinin gercekten yazidaki kelime olup olmadigi belirlenmiyor. ancak donen sonuclar
     * basitce kok kullanim frekansina gore dizilir. Yani ilk kelime buyuk ihtimalle kastedilen kelimedir.
     *
     * @param giris giris kelimesi
     * @return Kelime sinifi cinsinden dizi. Eger dizinin boyu 0 ise kelime cozumlenemedi demektir.
     *         Kelime kokune erisim icin kok(), eklere erisim icin Ek cinsinden nesne listesi donduren
     *         ekler() metodu kullanilir.  Kelimenin String cinsinden ifadesi icin icerik().toString()
     *         metodu kullanilabilir.
     * @see net.zemberek.yapi.Kelime
     */
    public Kelime[] asciiCozumle(String giris) {
        Kelime[] sonuclar = asciiToleransliCozumleyici.cozumle(giris, CozumlemeSeviyesi.TUM_KOK_VE_EKLER);
        Arrays.sort(sonuclar, new KelimeKokFrekansKiyaslayici());
        return sonuclar;
    }

    public Kelime[] asciiCozumle(String giris, CozumlemeSeviyesi seviye) {
        Kelime[] sonuclar = asciiToleransliCozumleyici.cozumle(giris, seviye);
        Arrays.sort(sonuclar, new KelimeKokFrekansKiyaslayici());
        return sonuclar;
    }

    /**
     * Brings the most probable tukish equivalents of a string that uses ascii look alikes of
     * those characters.
     * <p/>
     * asciiCozumle ile benzer bir yapidadir. Farki String[] dizisi donmesi ve
     * donus degerlerinin tekil olmasidir, yani ayni kelime tekrari olmaz.
     *
     * @param giris giris kelimesi
     * @return EN:possible turkish equivalents of the ascii turkish string in a String array.
     *         TR:yazilan kelimenin olasi turkce karakter iceren halleri. String[] seklinde.
     */
    public String[] asciidenTurkceye(String giris) {
        Kelime[] kelimeler = asciiCozumle(giris, CozumlemeSeviyesi.TUM_KOKLER);
        // cift olusumlari temizle.
        ArrayList<String> olusumlar = new ArrayList<String>(kelimeler.length);
        for (Kelime kelime : kelimeler) {
            String olusum = kelime.icerikStr();
            if (!olusumlar.contains(olusum))
                olusumlar.add(olusum);
        }
        //kumeyi tekrar diziye donustur.
        return olusumlar.toArray(new String[olusumlar.size()]);
    }

    /**
     * kelime icindeki dile ozel karakterleri ASCII benzer formalarina dondurur.
     *
     * @param giris giris kelimesi
     * @return turkce karakter tasimayan String.
     */
    public String asciiyeDonustur(String giris) {
        return asciiDonusturucu.toAscii(giris);
    }

    /**
     * girilen kelimeyi heceler.
     *
     * @param giris giris kelimesi
     * @return String dizisi. Eger dizi boyu 0 ise kelime hecelenememis demektir.
     */
    public String[] hecele(String giris) {
        return heceIslemleri.hecele(giris);
    }

    /**
     * giris kelimesine yakin Stringleri dondurur. Yani eger kelime bozuk ise bu kelimeye
     * benzeyen dogru kelime olasiliklarini dondurur. simdilik
     * - 1 harf eksikligi
     * - 1 harf fazlaligi
     * - 1 yanlis harf kullanimi
     * - yanyana yeri yanlis harf kullanimi.
     * hatalarini giderecek sekilde cozumleri donduruyor. Bu metod dogru kelimeler icin de
     * isler, yani giris "kedi" ise donus listesinde kedi ve kedi'ye benzesen kelimeler de doner.
     * Ornegin "kedim", "yedi" .. gibi.
     *
     * @param giris giris kelimesi
     * @return String sinifi cinsinden dizi. Eger dizinin boyu 0 ise kelime cozumlenemedi demektir.
     * @see net.zemberek.yapi.Kelime
     */
    public String[] oner(String giris) {
        return oneriUretici.oner(giris);
    }

    /**
     * Basit sekilde giris kelime ya da kelime dizisinin Zemberek olusturulrken kullanilan dil ile
     * benzerligi kestirir. Girilen kelime sayisinin azligi soznucun basarimini dusurur.
     * donus farkli seviyelerde olabilir.
     *
     * @param giris giris string
     * @return Donus integer 0-4 arasi deger alabilir. nesne olusturulurken kullanilan dil D ise
     *         0 yazinin D dili olmadigi 4 ise kesin D oldugunu belirtir.
     *         ara degerler
     *         1- yazi icinde D olabilecek kelimeler var, ama genel D degil.
     *         2- yazi D, cok fazla yabanci ya da bozuk kelime var.
     *         3- yazi D, yabanci ve bozuk kelimeler iceriyor.
     */
    public int dilTesti(String giris) {
        return turkceTest.turkceTest(giris);
    }

    /**
     * Zemberek olusumu sirasinda kullanilan DilBilgisi nesnesi doner. Bu nesne uzerinden
     * dile ozel cesitli dil bilgisi islemlerine erisilebilir.
     *
     * @return bu nesneyi olustururken kullanilan DilBilgisi arayuzune sahip nesne.
     */
    public DilBilgisi dilBilgisi() {
        return dilBilgisi;
    }

    /**
     * Istenilen kok ve ek listesi ile kelime uretir.
     *
     * @param kok   kok nesnesi
     * @param ekler ek listesi
     * @return String olarak uretilen kelime.
     */
    public String kelimeUret(Kok kok, List<Ek> ekler) {
        return kelimeUretici.kelimeUret(kok, ekler);
    }

    /**
     * Istenilen kok ve rasgele sayida ek ile kelime uretir.
     *
     * @param kok   kok nesnesi
     * @param ekler ek listesi
     * @return String olarak uretilen kelime.
     */
    public String kelimeUret(Kok kok, Ek... ekler) {
        return kelimeUretici.kelimeUret(kok, ekler);
    }

    /**
     * Istenilen kelimenin olasi String acilimlarini bulur.
     * Ornegin, "alayim" icin
     * "al-a-yim" ve "ala-yim" cozumleri String dizileri seklinde uretilir.
     * sonucta olusan diziler bir Listeye eklenir.
     *
     * @param kelime giris kelimesi
     * @return Kok ve ek olusumlarini ifade eden String dizilerini tasiyan List.
     *         List<String[]>
     *         Eger kelime ayristirilamiyorsa sifir uzunluklu String dizisi tasiyan tek elemanli
     *         liste doner. .
     */
    public List<String[]> kelimeAyristir(String kelime) {
        Set<String[]> sonuclar = new HashSet<String[]>();
        Kelime[] cozumler = cozumleyici.cozumle(kelime, CozumlemeSeviyesi.TUM_KOK_VE_EKLER);
        for (Kelime kel : cozumler) {
            sonuclar.add(kelimeUretici.ayristir(kel));
        }
        return new ArrayList<String[]>(sonuclar);
    }

    /**
     * Zemberek konfigurasyon parametrelerini dondurur.
     *
     * @return ayarlar.
     */
    public ZemberekAyarlari ayarlar() {
        return ayarlar;
    }

    /**
     * kok bulucu mekanizmayi dondurur.
     * @return
     */
    public KokBulucu kokBulucu() {
        return new KelimeTabanliKokBulucu(cozumleyici, dilBilgisi.alfabe());
    }

}
