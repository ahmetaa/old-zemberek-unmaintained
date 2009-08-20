package net.zemberek.erisim;

import net.zemberek.bilgi.kokler.KokBulucu;
import net.zemberek.bilgi.kokler.Sozluk;
import net.zemberek.islemler.AsciiDonusturucu;
import net.zemberek.islemler.Heceleyici;
import net.zemberek.islemler.KelimeKokFrekansKiyaslayici;
import net.zemberek.islemler.KelimeUretici;
import net.zemberek.islemler.cozumleme.*;
import net.zemberek.yapi.*;

import java.util.*;

/**
 * This is a facade for accessing the high level functions of the Zemberek library.
 * <p/>
 * Zemberek projesine ust seviye erisim icin kullanilan sinif.
 * Ilk olsum sirasinda kokler okuma ve agac olusumu nedeniyle belli bir miktar gecikme
 * yasanabilir.
 */
public class Zemberek {

    private KelimeCozumleyici cozumleyici;
    private KelimeUretici kelimeUretici;
    private KelimeCozumleyici asciiToleransliCozumleyici;
    private OneriUretici oneriUretici;
    private AsciiDonusturucu asciiDonusturucu;
    private Heceleyici heceleyici;
    private DilBilgisi dilBilgisi;

    public Zemberek(DilAyarlari dilayarlari) {
        this.dilBilgisi = new TurkceDilBilgisi(dilayarlari);
        initialize();
    }

    private void initialize() {
        //Sozluk hazirla.
        Sozluk kokler = dilBilgisi.kokler();
        //Normal denetleyici-cozumleyici olusumu
        KokBulucu kokBulucu = kokler.getKokBulucuFactory().getKesinKokBulucu();
        cozumleyici = new StandartCozumleyici(
                kokBulucu,
                new KesinHDKiyaslayici(),
                dilBilgisi.alfabe(),
                dilBilgisi.ekler(),
                dilBilgisi.cozumlemeYardimcisi());

        // ASCII-Turkce donusturucu icin tukce toleransli cozumleyici olusumu.
        KokBulucu turkceToleransliKokBulucu = kokler.getKokBulucuFactory().getAsciiKokBulucu();
        asciiToleransliCozumleyici = new StandartCozumleyici(
                turkceToleransliKokBulucu,
                new AsciiToleransliHDKiyaslayici(),
                dilBilgisi.alfabe(),
                dilBilgisi.ekler(),
                dilBilgisi.cozumlemeYardimcisi());

        KokBulucu toleransliBulucu = kokler.getKokBulucuFactory().getToleransliKokBulucu(1);
        ToleransliCozumleyici toleransliCozumleyici = new ToleransliCozumleyici(
                toleransliBulucu,
                dilBilgisi.ekler(),
                dilBilgisi.alfabe(),
                dilBilgisi.cozumlemeYardimcisi());

        oneriUretici = new OneriUretici(
                dilBilgisi.cozumlemeYardimcisi(),
                cozumleyici,
                asciiToleransliCozumleyici,
                toleransliCozumleyici
        );


        asciiDonusturucu = new AsciiDonusturucu(dilBilgisi.alfabe());
        heceleyici = new Heceleyici(dilBilgisi.alfabe(), dilBilgisi.heceBulucu());

        kelimeUretici = new KelimeUretici(dilBilgisi.alfabe(), dilBilgisi.cozumlemeYardimcisi());
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
     * @return heceleyici
     */
    public Heceleyici heceleyici() {
        return heceleyici;
    }

    /**
     * performs spell checking
     * <p/>
     * girisin imla denetimini yapar.
     *
     * @param giris giris kelimesi
     * @return EN: true:spell checking successfull, false otherwise.
     *         TR: true:imla denetimi basarili. false: Denetim basarisiz.
     */
    public boolean kelimeDenetle(String giris) {
        return cozumleyici.denetle(giris);
    }

    /**
     * performs morphological parsing of the word. Returns the possible solutions as an Kelime array.
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
        return cozumleyici.cozumle(giris);
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
        Kelime[] sonuclar = asciiToleransliCozumleyici.cozumle(giris);
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
        Kelime[] kelimeler = asciiCozumle(giris);
        // cift olusumlari temizle.
        Set<String> olusumlar = new HashSet(kelimeler.length);
        for (Kelime kelime : kelimeler) {
            String olusum = kelime.icerik().toString();
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
        return heceleyici.hecele(giris);
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
     * nesnenin olusumu sirasinda kullanilan DilBilgisi arabirimine sahip dili dondurur.
     * Eger nesne hic parametre kullanilmadan olusturulmussa bir adet TurkiyeTurkcesi nesnesi doner.
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
    public String kelimeUret(Kok kok, List ekler) {
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
     *         List<List<String>>
     *         Eger kelime ayristirilamiyorsa sifir uzunluklu String dizisi tasiyan tek elemanli
     *         liste doner. .
     */
    public List<List<String>> kelimeAyristir(String kelime) {
        Set<List<String>> sonuclar = new HashSet();
        Kelime[] cozumler = cozumleyici.cozumle(kelime);
        for (Kelime kel : cozumler) {
            sonuclar.add(kelimeUretici.ayristir(kel));
        }
        return new ArrayList(sonuclar);
    }
}
