/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi;


/**
 * Bu sinif Dil islemleri sirasinda Turkceye ozel islemler gerektiginden String-StringBuffer yerine kullanilir.
 * String gibi genel bir tasiyici degil ara islem nesnesi olarak kullanilmasi onerilir.
 * String'den farkli olarak "degistirilebilir" bir yapidadir ve Thread-safe degildir.
 */
public class HarfDizisi implements CharSequence, Comparable<HarfDizisi> {

    private TurkceHarf[] dizi;
    private int boy = 0;
    public static final HarfDizisi BOS_DIZI = new HarfDizisi(0);

    /**
     * default constructor. 7 boyutlu bir TurkceHarf referans dizisi olusturur.
     */
    public HarfDizisi() {
        dizi = new TurkceHarf[7];
    }

    /**
     * 'kapasite' boyutlu 'TurkceHarf' dizisine sahip nesne olusturur.
     *
     * @param kapasite baslangic olusan TurkceHarf[] boyu
     */
    public HarfDizisi(int kapasite) {
        dizi = new TurkceHarf[kapasite];
    }

    /**
     * 'kapasite' boyutlu 'TurkceHarf' dizisine sahip nesne olusturur. daha sonra
     * girisi String'i icindeki karakterleri TurkceHarf seklinde TurkceHarf dizisine aktarir.
     * Eger String boyu kapasiteden buyukse kapasite'yi boy'a esitler.
     * Eger String icindeki karakter Alfabe'de yar almiyorsa "TANIMSIZ_HARF" harfi olarak eklenir.
     *
     * @param str      ornek alincak String
     * @param kapasite baslangic olusan TurkceHarf[] boyu
     * @param alfabe   ilgili alfabe
     */
    public HarfDizisi(String str, Alfabe alfabe, int kapasite) {
        if (kapasite < str.length())
            kapasite = str.length();
        dizi = new TurkceHarf[kapasite];
        boy = str.length();
        for (int i = 0; i < boy; i++)
            dizi[i] = alfabe.harf(str.charAt(i));
    }


    /**
     * Belirlenen alfabe ile String icerigini Harflere donusturur.
     *
     * @param str    ornek alincak String
     * @param alfabe ilgili alfabe
     */
    public HarfDizisi(String str, Alfabe alfabe) {
        boy = str.length();
        dizi = new TurkceHarf[boy];
        for (int i = 0; i < boy; i++)
            dizi[i] = alfabe.harf(str.charAt(i));
    }

    /**
     * Copy-Constructor. gelen harf dizisi ile ayni icerige sahip olacak sekilde
     * TurkceHarf dizisi olusturur.
     *
     * @param hdizi ornek alinacak HarfDizisi
     */
    public HarfDizisi(HarfDizisi hdizi) {
        boy = hdizi.length();
        dizi = new TurkceHarf[boy];
        System.arraycopy(hdizi.dizi, 0, dizi, 0, boy);
    }

    /**
     * gelen TurkceHarf dizisini icerige kopyalar.
     *
     * @param inpDizi kopyalancak TurkceHarf dizisi.
     */
    private HarfDizisi(TurkceHarf[] inpDizi) {
        boy = inpDizi.length;
        dizi = new TurkceHarf[boy];
        System.arraycopy(inpDizi, 0, dizi, 0, boy);
    }

    /**
     * bu metod harf referansi dizisini serbest birakmaz,
     * sadece boyu sifira indirir.
     */
    public void sil() {
        boy = 0;
    }

    /**
     * Dizinin son harfini dondurur.
     *
     * @return varsa son harf, Yoksa TANIMSIZ_HARF.
     */
    public TurkceHarf sonHarf() {
        if (boy > 0)
            return dizi[boy - 1];
        else
            return Alfabe.TANIMSIZ_HARF;
    }

    /**
     * dizideki son sesliyi dondurur. eger dizi boyu 0 ise ya da sesli harf yoksa
     * TANIMSIZ_HARF doner.
     *
     * @return varsa son sesli yoksa TANIMSIZ_HARF
     */
    public TurkceHarf sonSesli() {
        for (int i = boy - 1; i >= 0; i--) {
            if (dizi[i].sesliMi())
                return dizi[i];
        }
        return Alfabe.TANIMSIZ_HARF;
    }

    /**
     * ic metod. harf dizisinin boyutu yetersiz geldiginde "ek" miktarinda daha
     * fazla yere sahip yeni dizi olusturulup icerik yeni diziye kopyalanir.
     *
     * @param ek eklenecek HarfDizisi miktari.
     */
    private void kapasiteAyarla(int ek) {
        TurkceHarf[] yeniDizi = new TurkceHarf[dizi.length + ek];
        System.arraycopy(dizi, 0, yeniDizi, 0, dizi.length);
        dizi = yeniDizi;
    }

    /**
     * otomatik kapasite ayarlama. dizi boyu iki katina cikarilir.
     */
    private void kapasiteAyarla() {
        TurkceHarf[] yeniDizi = new TurkceHarf[dizi.length * 2];
        System.arraycopy(dizi, 0, yeniDizi, 0, dizi.length);
        dizi = yeniDizi;
    }

    /**
     * kelimenin sonuna harf ekler.
     *
     * @param harf eklenecek harf
     * @return this
     */
    public HarfDizisi ekle(TurkceHarf harf) {
        if (boy == dizi.length)
            kapasiteAyarla(3);
        dizi[boy++] = harf;
        return this;
    }

    /**
     * girilen pozisyona herf ekler, bu noktadan sonraki harfler otelenir.
     * "armut" icin (2, a) "aramut" uretir.
     *
     * @param index eklenecek pozisyon
     * @param harf  eklenecek harf.
     * @throws ArrayIndexOutOfBoundsException
     */
    public void ekle(int index, TurkceHarf harf) {
        if (index < 0 || index > boy)
            throw new ArrayIndexOutOfBoundsException("index degeri:" + index + " fakat harf dizi boyu:" + boy);

        if (boy == dizi.length)
            kapasiteAyarla();

        for (int i = boy - 1; i >= index; i--)
            dizi[i + 1] = dizi[i];
        dizi[index] = harf;
        boy++;
    }

    /**
     * Diziye baska bir harf dizisinin icerigini ular.
     *
     * @param hdizi ulanacak harf dizisi.
     * @return this.
     */
    public HarfDizisi ekle(HarfDizisi hdizi) {
        int hboy = hdizi.length();
        if (boy + hboy > dizi.length)
            kapasiteAyarla(hboy);

        System.arraycopy(hdizi.dizi, 0, dizi, boy, hboy);
        boy += hdizi.length();
        return this;
    }

    /**
     * Diziye baska bir harf dizisinin icerigini index ile belirtilen harften itibaren ekler.
     * "armut" icin (2, hede) "arhedemut" uretir.
     *
     * @param index eklencek pozisyon
     * @param hdizi eklenecek harf dizisi
     * @return this.
     * @throws ArrayIndexOutOfBoundsException
     */
    public HarfDizisi ekle(int index, HarfDizisi hdizi) {
        if (index < 0 || index > boy)
            throw new ArrayIndexOutOfBoundsException("indeks degeri:" + index + " fakat harf dizi boyu:" + boy);

        //dizi kapasitesini ayarla
        int hboy = hdizi.length();
        if (boy + hboy > dizi.length)
            kapasiteAyarla(hboy);

        //sondan baslayarak this.dizinin index'ten sonraki kismini dizinin sonuna tasi
        for (int i = hboy + boy - 1; i >= hboy; i--)
            dizi[i] = dizi[i - hboy];

        //gelen diziyi kopyala ve boyutu degistir.
        System.arraycopy(hdizi.dizi, 0, dizi, index, hboy);
        boy += hdizi.length();
        return this;
    }

    public HarfDizisi araDizi(int bas, int son) {
        if (son < bas) return null;
        TurkceHarf[] yeniHarfler = new TurkceHarf[son - bas];
        System.arraycopy(dizi, bas, yeniHarfler, 0, son - bas);
        return new HarfDizisi(yeniHarfler);
    }

    /**
     * verilen pozisyondaki harfi dondurur. icerigi "kedi" olan HarfDizisi icin
     * harf(1) e dondurur.
     *
     * @param i istenilen pozisyondaki harf.
     * @return girilen pozisyondaki harf, yoksa TANIMSIZ_HARF
     */
    public TurkceHarf harf(int i) {
        if (i < 0)
            return Alfabe.TANIMSIZ_HARF;
        if (i < boy)
            return dizi[i];
        return Alfabe.TANIMSIZ_HARF;
    }

    /**
     * ilk sesliyi dondurur. eger sesli yoksa TANIMSIZ_HARF doner. aramaya belirtilen indeksten baslar.
     *
     * @param basla baslangic indeksi.
     * @return varsa ilk sesli, yoksa TANIMSIZ_HARF
     */
    public TurkceHarf ilkSesli(int basla) {
        for (int i = basla; i < boy; i++) {
            if (dizi[i].sesliMi())
                return dizi[i];
        }
        return Alfabe.TANIMSIZ_HARF;
    }

    /**
     * Tam esitlik kiyaslamasi. kiyaslama nesne tipi, ardindan da TurkceHarf dizisi icindeki
     * harflerin char iceriklerine gore yapilir.
     *
     * @param o kiyaslanacak nesne
     * @return true eger esitse.
     */
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;
        if (!(o instanceof HarfDizisi)) return false;

        final HarfDizisi harfDizisi = (HarfDizisi) o;
        if (boy != harfDizisi.boy) return false;
        for (int i = 0; i < boy; i++) {
            if (dizi[i].charDeger() != harfDizisi.dizi[i].charDeger())
                return false;
        }
        return true;
    }

    public int hashCode() {
        return toString().hashCode();
    }

    /**
     * ascii benzer harf toleransli esitlik kiyaslamasi.
     *
     * @param harfDizisi kiyaslanacak harfDizisi
     * @return true eger esitse.
     */
    public boolean asciiToleransliKiyasla(HarfDizisi harfDizisi) {
        if (harfDizisi == null) return false;
        if (this == harfDizisi) return true;
        if (boy != harfDizisi.boy) return false;
        for (int i = 0; i < boy; i++) {
            if (!dizi[i].asciiToleransliKiyasla(harfDizisi.dizi[i]))
                return false;
        }
        return true;
    }

    public boolean asciiToleransliAradanKiyasla(int baslangic, HarfDizisi kelime) {
        if (kelime == null) return false;
        if (boy < baslangic + kelime.length())
            return false;
        for (int i = 0; i < kelime.length(); i++)
            if (!dizi[baslangic + i].asciiToleransliKiyasla(kelime.harf(i)))
                return false;
        return true;
    }

    public boolean asciiToleransliBastanKiyasla(HarfDizisi giris) {
        if (giris == null) return false;
        if (giris.length() > this.boy)
            return false;
        for (int i = 0; i < giris.length(); i++)
            if (!dizi[i].asciiToleransliKiyasla(giris.harf(i)))
                return false;
        return true;
    }

    public boolean aradanKiyasla(int baslangic, HarfDizisi kelime) {
        if (kelime == null) return false;
        if (boy < baslangic + kelime.length())
            return false;
        for (int i = 0; i < kelime.length(); i++)
            if (dizi[baslangic + i].charDeger() != kelime.harf(i).charDeger())
                return false;
        return true;
    }

    public boolean bastanKiyasla(HarfDizisi giris) {
        if (giris == null) return false;
        if (giris.length() > this.boy)
            return false;
        for (int i = 0; i < giris.length(); i++)
            if (dizi[i].charDeger() != giris.harf(i).charDeger())
                return false;
        return true;
    }

    /**
     * istenen noktadaki harfi giris parametresi olan TurkceHarf ile degistirir.
     *
     * @param index degistirilecek indeks.
     * @param harf  kullanilacak harf
     * @throws ArrayIndexOutOfBoundsException
     */
    public void harfDegistir(int index, TurkceHarf harf) {
        if (index < 0 || index >= boy)
            throw new ArrayIndexOutOfBoundsException("indeks degeri:" + index + " fakat harf dizi boyu:" + boy);
        dizi[index] = harf;
    }

    /**
     * son harfi yumusatir. Eger harfin yumusamis formu yoksa harf degismez.
     */
    public void sonHarfYumusat() {
        if (boy == 0)
            return;
        TurkceHarf yum = dizi[boy - 1].yumusama();
        if (yum != null)
            dizi[boy - 1] = dizi[boy - 1].yumusama();
    }

    /**
     * son harfi siler. eger harf yoksa hicbir etki yapmaz.
     */
    public void sonHarfSil() {
        if (boy > 0)
            boy--;
    }

    /**
     * verilen pozisyondaki harfi siler. kelimenin kalan kismi otelenir.
     * eger verilen pozisyon yanlis ise  ArrayIndexOutOfBoundsException firlatir.
     * <p/>
     * "kedi" icin (2) "kei" olusturur.
     *
     * @param index silinecek harf pozisyonu
     * @return dizinin kendisi.
     * @throws ArrayIndexOutOfBoundsException
     */
    public HarfDizisi harfSil(int index) {
        if (index < 0 || index >= boy)
            throw new ArrayIndexOutOfBoundsException("indeks degeri:" + index + " fakat harf dizi boyu:" + boy);
        if (index == boy - 1) {
            boy--;
        } else {
            System.arraycopy(dizi, index + 1, dizi, index, boy - index - 1);
            boy--;
        }
        return this;
    }

    /**
     * verilen pozisyondan belli miktar harfi siler.
     * "kediler" icin (2,2) "keler" olusturur.
     *
     * @param index      silinmeye baslanacak pozisyon
     * @param harfSayisi silinecek harf miktari
     * @return dizinin kendisi
     */
    public HarfDizisi harfSil(int index, int harfSayisi) {
        if (index < 0 || index >= boy)
            throw new ArrayIndexOutOfBoundsException("indeks degeri:" + index + " fakat harf dizi boyu:" + boy);
        if (index + harfSayisi > boy)
            harfSayisi = boy - index;
        for (int i = index + harfSayisi; i < boy; i++)
            dizi[i - harfSayisi] = dizi[i];
        boy -= harfSayisi;
        return this;
    }

    /**
     * ilk harfi dondurur. eger harf yoksa TANIMSIZ_HARF doner.
     *
     * @return ilk TurkceHarf.
     */
    public TurkceHarf ilkHarf() {
        if (boy == 0) return Alfabe.TANIMSIZ_HARF;
        else
            return dizi[0];
    }

    /**
     * "index" numarali harften itibaren siler.
     * "kedi" icin (1) "k" olusturur.
     *
     * @param index kirpilmaya baslanacak pozisyon
     */
    public void kirp(int index) {
        if (index <= boy && index >= 0)
            boy = index;
    }

    /**
     * sadece belirli bir bolumunu String'e donusturur.
     *
     * @param index String'e donusum baslangic noktasi.
     * @return olusan String.
     */
    public String toString(int index) {
        if (index < 0 || index >= boy) return "";
        StringBuilder s = new StringBuilder(boy - index);
        for (int i = index; i < boy; i++)
            s.append(charAt(i));
        return s.toString();
    }

    @Override
    public String toString() {
        return new StringBuilder(this).toString();
    }

    /**
     * Compare to metodu siralama icin kiyaslama yapar. Kiyaslama oncelikle harflerin alfabetik sirasina
     * daha sonra dizilerin boyutuna gore yapilir.
     *
     * @param o kiyaslanacak dizi.
     * @return
     *        'kedi'.compareTo('kedi') -> 0
     *        'kedi'.compareTo('ke')  -> 2 (boy farki)
     *        'kedi'.compareTo('kedm') -> -4 (i->m alfabetik sira farki)
     *        'kedi'.compareTo(null) -> 1
     */
    public int compareTo(HarfDizisi o) {
        if (o == null)
            return 1;

        if (this == o)
            return 0;

        int l = o.boy;
        int n = Math.min(boy, l);

        for (int i = 0; i < n; i++) {
            if (!dizi[i].equals(o.dizi[i]))
                return dizi[i].alfabetikSira() - o.dizi[i].alfabetikSira();
        }
        return boy - l;
    }

    /* ------------------------- ozel metodlar ------------------------------- */

    /**
     * Genellikle kelimedeki hece sayisini bulmak icin kullanilir.
     *
     * @return inte, sesli harf sayisi.
     */
    public int sesliSayisi() {
        int sonuc = 0;
        for (int i = 0; i < boy; i++) {
            if (dizi[i].sesliMi())
                sonuc++;
        }
        return sonuc;
    }

    /**
     * @return hepsi buyuk harf ise true, boy=0 dahil.
     */
    public boolean hepsiBuyukHarfmi() {
        for (int i = 0; i < boy; i++) {
            if (!dizi[i].buyukHarfMi())
                return false;
        }
        return true;
    }

    //--------- asagidaki metodlar CharSequence arayuzu icin hazirlandi. -----

    public int length() {
        return boy;
    }

    public char charAt(int index) {
        if (index < 0 || index >= boy)
            throw new StringIndexOutOfBoundsException(index);
        return dizi[index].charDeger();
    }

    public CharSequence subSequence(int start, int end) {
        return araDizi(start, end);
    }
}
