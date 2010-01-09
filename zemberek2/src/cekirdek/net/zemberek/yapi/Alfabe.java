/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import net.zemberek.araclar.Kayitci;
import net.zemberek.bilgi.KaynakYukleyici;

public class Alfabe {

    // Turkce ozel
    public static final char CHAR_CC = '\u00c7'; // Kuyruklu buyuk c (ch)
    public static final char CHAR_cc = '\u00e7'; // Kuyruklu kucuk c (ch)
    public static final char CHAR_GG = '\u011e'; // Buyuk yumusak g
    public static final char CHAR_gg = '\u011f'; // Kucuk yumusak g
    public static final char CHAR_ii = '\u0131'; // Noktassiz kucuk i
    public static final char CHAR_II = '\u0130'; // Noktali buyuk i
    public static final char CHAR_OO = '\u00d6'; // Noktali buyuk o
    public static final char CHAR_oo = '\u00f6'; // Noktali kucuk o
    public static final char CHAR_SS = '\u015e'; // Kuyruklu buyuk s (sh)
    public static final char CHAR_ss = '\u015f'; // Kuyruklu kucuk s (sh)
    public static final char CHAR_UU = '\u00dc'; // Noktali buyuk u
    public static final char CHAR_uu = '\u00fc'; // Noktali kucuk u

    // Azeri ozel
    public static final char CHAR_ee = '\u0259'; // ?
    public static final char CHAR_EE = '\u018f'; // ?

    // Turkmen ozel
    public static final char CHAR_AA = '\u00c4'; // Noktali buyuk A
    public static final char CHAR_aa = '\u00e4'; // Noktali kucuk a
    public static final char CHAR_NN = '\u0147'; // Kuyruklu buyuk N
    public static final char CHAR_nn = '\u0148'; // Kuyruklu kucuk n
    public static final char CHAR_YY = '\u00dd'; // Kuyruklu buyuk Y
    public static final char CHAR_yy = '\u00fd'; // Kuyruklu kucuk y
    public static final char CHAR_JJ = '\u017d'; // J harfi
    public static final char CHAR_jj = '\u017e'; //j harfi

    // Sapkali
    public static final char CHAR_SAPKALI_A = '\u00c2'; // sapkali buyuk A
    public static final char CHAR_SAPKALI_a = '\u00e2'; // sapkali kucuk A
    public static final char CHAR_SAPKALI_I = '\u00ce'; // sapkali buyuk noktasiz i
    public static final char CHAR_SAPKALI_i = '\u00ee'; // sapkali kucuk noktasiz i
    public static final char CHAR_SAPKALI_U = '\u00db'; // sapkali buyuk U
    public static final char CHAR_SAPKALI_u = '\u00fb'; // sapkali kucuk u

    // tatar ozel
    public static final char CHAR_TT_n = '\u00f1'; // tildeli n
    public static final char CHAR_TT_N = '\u00d1'; // tildeli N

    public static final TurkceHarf TANIMSIZ_HARF = new TurkceHarf('#', 0);

    public static final char ALFABE_DISI_KARAKTER = '#';
    protected static final int TURKISH_CHAR_MAP_SIZE = 610;
    protected static final int TURKISH_HARF_MAP_SIZE = 50;
    protected char temizlemeDizisi[] = new char[TURKISH_CHAR_MAP_SIZE];
    protected char asciifierDizisi[] = new char[TURKISH_CHAR_MAP_SIZE];
    protected TurkceHarf turkceHarfDizisi[] = new TurkceHarf[TURKISH_CHAR_MAP_SIZE];
    protected TurkceHarf kucukHarflerDizi[] = new TurkceHarf[TURKISH_HARF_MAP_SIZE];
    protected TurkceHarf buyukHarflerDizi[] = new TurkceHarf[TURKISH_HARF_MAP_SIZE];
    protected boolean turkceMap[] = new boolean[TURKISH_CHAR_MAP_SIZE];
    protected byte alfabetikSiralar[] = new byte[TURKISH_CHAR_MAP_SIZE];

    protected Map<Character, TurkceHarf> harfler = new HashMap<Character, TurkceHarf>();
    private char[] asciiDisi = new char[20];

    private Map<TurkceHarf, TurkceHarf> ozelInceSesliler = new HashMap<TurkceHarf, TurkceHarf>();

    private static Logger logger = Kayitci.kayitciUret(Alfabe.class);

    public Alfabe(String dosyaAdi, String localeStr) throws IOException {
        Map<String, String> harfOzellikleri;
        harfOzellikleri = new KaynakYukleyici("UTF-8").kodlamaliOzellikDosyasiOku(dosyaAdi);
        this.locale = new Locale(localeStr);
        diziInit();
        harfBilgisiOlustur(harfOzellikleri);
    }

    private void diziInit() {

        for (int i = 0; i < TURKISH_CHAR_MAP_SIZE; i++) {
            temizlemeDizisi[i] = ALFABE_DISI_KARAKTER;
            turkceMap[i] = false;
            asciifierDizisi[i] = (char) i;
        }
    }

    /**
     * char olarak girilen harfin TurkceHarf karsiligini dondurur.
     * Bu sekilde harfin Turkce'ye has ozelliklerine erisilebilir. sesli, sert vs.
     *
     * @param harf istenen harfin char karsiligi
     * @return char harfin turkeceHarf karsiligi. Eger yoksa TANIMSIZ_HARF doner.
     */
    public TurkceHarf harf(char harf) {
        if (harf > TURKISH_CHAR_MAP_SIZE) return TANIMSIZ_HARF;
        return turkceHarfDizisi[harf];
    }


    /**
     * girilen stringi kucuk harfe donusturup icindeki uyumsuz karakterleri siler
     * "Wah'met-@" -> "ahmet"
     *
     * @param giris ayiklanacak kelime
     * @return girisin ayiklanmis hali (String)
     */
    public String ayikla(String giris) {
        final StringBuilder buf = new StringBuilder(giris.length());
        for (int i = 0; i < giris.length(); i++) {
            if (giris.charAt(i) >= TURKISH_CHAR_MAP_SIZE)
                continue;
            char temiz = temizlemeDizisi[giris.charAt(i)];
            if (temiz != ALFABE_DISI_KARAKTER)
                buf.append(temiz);
        }
        return buf.toString();
    }

    public boolean cozumlemeyeUygunMu(String giris) {
        for (int i = 0; i < giris.length(); i++)
            if (!turkceMap[giris.charAt(i)] || giris.charAt(i) > TURKISH_CHAR_MAP_SIZE) return false;
        return true;
    }

    public String asciifyString(String in) {
        char[] buffer = in.toCharArray();
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = asciifierDizisi[buffer[i]];
        }
        return new String(buffer);
    }

    public TurkceHarf buyukHarf(TurkceHarf harf) {
        final TurkceHarf buyuk = buyukHarflerDizi[harf.alfabetikSira() - 1];
        if (buyuk != null) return buyuk;
        return TANIMSIZ_HARF;
    }

    public TurkceHarf buyukHarf(char c) {
        final TurkceHarf buyuk = buyukHarflerDizi[harf(c).alfabetikSira() - 1];
        if (buyuk != null) return buyuk;
        return TANIMSIZ_HARF;
    }

    public TurkceHarf kucukHarf(TurkceHarf harf) {
        final TurkceHarf kucuk = kucukHarflerDizi[harf.alfabetikSira() - 1];
        if (kucuk != null) return kucuk;
        return TANIMSIZ_HARF;
    }

    public boolean asciiToleransliKiyasla(char harf1, char harf2) {
        if (harf1 > TURKISH_CHAR_MAP_SIZE || harf2 > TURKISH_CHAR_MAP_SIZE) return false;
        return (asciifierDizisi[harf1] == asciifierDizisi[harf2]);
    }

    public char[] asciiDisiHarfler() {
        return asciiDisi;
    }

    /**
     * istenilen kalin seslinin inceltilmis kopya halini dondurur. sadece ters sesli
     * ozel durumu isleminde kullanilmaslidir.
     *
     * @param kalinSesli inceltilecek sesli
     * @return eger varsa karsilik dusen kalin sesli. yoksa seslinin kendisi.
     */
    public TurkceHarf kalinSesliIncelt(TurkceHarf kalinSesli) {
        if (ozelInceSesliler.containsKey(kalinSesli))
            return ozelInceSesliler.get(kalinSesli);
        else return kalinSesli;
    }

    /**
     * bu degerler alfabe bilgisinin dosyadan okunmasi sirasinda kullanilir.
     */
    protected Locale locale;
    private final Pattern virgulReg = Pattern.compile("[,]");
    private final Pattern tireReg = Pattern.compile("[-]");


    public static final String HARFLER = "harfler";
    public static final String SESLI = "sesli";

    public static final String INCE_SESLI = "ince-sesli";
    public static final String DUZ_SESLI = "duz-sesli";
    public static final String YUVARLAK_SESLI = "yuvarlak-sesli";
    public static final String SERT = "sert";
    public static final String ASCII_DISI = "ascii-disi";
    public static final String ASCII_TURKCE = "ascii-turkce";
    public static final String TURKCE_ASCII = "turkce-ascii";
    public static final String YUMUSAMA_DONUSUM = "yumusama-donusum";
    public static final String SERT_DONUSUM = "sert-donusum";
    public static final String AYIKLAMA = "ayiklama";
    public static final String AYIKLAMA_DONUSUM = "ayiklama-donusum";
    public static final String OZEL_INCE_SESLI = "ozel-ince-sesli";
    public static final String LOCALE = "locale";

    /**
     * harf dosyasindan harf bilgilerini okur ve TurkceHarf, ve alfabe sinifi icin
     * gerekli harf iliskili veri yapilarinin olusturur.
     *
     * @param bilgi okunan ozellikler
     */
    private void harfBilgisiOlustur(Map<String, String> bilgi) {

        // eger alfabe taniminda secimlik locale bilgisi yer aliyorsa bunu normal locale degerinin yerine kullan
        Locale loc = this.locale;

        if (bilgi.containsKey(LOCALE)) {
            String lcl = ozellik(bilgi, LOCALE);
            if (lcl != null && lcl.length() > 0)
                loc = new Locale(lcl);
        }

        String tumKucukler = ozellik(bilgi, HARFLER);
        String tumBuyukler = tumKucukler.toUpperCase(loc);
        char[] kucukler = harfAyristir(tumKucukler);
        char[] buyukler = harfAyristir(tumBuyukler);

        //TurkceHarfleri olustur.
        for (int i = 0; i < kucukler.length; i++) {
            char c = kucukler[i];
            TurkceHarf harf = new TurkceHarf(c);
            harf.setAlfabetikSira(i + 1);
            harfler.put(c, harf);
            turkceHarfDizisi[c] = harf;
            kucukHarflerDizi[i] = harf;
            temizlemeDizisi[c] = c;
            turkceMap[c] = true;
            asciifierDizisi[c] = c;
        }

        for (int i = 0; i < buyukler.length; i++) {
            char c = buyukler[i];
            TurkceHarf harf = new TurkceHarf(c);
            if (Character.isLetter(c))
                harf.setBuyukHarf(true);
            harf.setAlfabetikSira(i + 1);
            harfler.put(c, harf);
            buyukHarflerDizi[i] = harf;

            temizlemeDizisi[c] = kucukHarflerDizi[i].charDeger();
            turkceHarfDizisi[c] = harf;
        }

        for (char c : harfAyristir(ozellik(bilgi, SESLI))) {
            harf(c).setSesli(true);
            buyukHarf(mapHarf(c)).setSesli(true);
        }

        for (char c : harfAyristir(ozellik(bilgi, INCE_SESLI))) {
            harf(c).setInceSesli(true);
            buyukHarf(c).setInceSesli(true);
        }

        for (char c : harfAyristir(ozellik(bilgi, DUZ_SESLI))) {
            harf(c).setDuzSesli(true);
            buyukHarf(c).setDuzSesli(true);
        }

        for (char c : harfAyristir(ozellik(bilgi, YUVARLAK_SESLI))) {
            harf(c).setYuvarlakSesli(true);
            buyukHarf(c).setYuvarlakSesli(true);
        }

        for (char c : harfAyristir(ozellik(bilgi, SERT))) {
            harf(c).setSert(true);
            buyukHarf(c).setSert(true);
        }

        asciiDisi = harfAyristir(ozellik(bilgi, ASCII_DISI));
        for (char c : asciiDisi)
            harf(c).setAsciiDisi(true);

        for (char c : harfAyristir(ozellik(bilgi, AYIKLAMA))) {
            temizlemeDizisi[c] = ALFABE_DISI_KARAKTER;
            temizlemeDizisi[buyukHarf(c).charDeger()] = ALFABE_DISI_KARAKTER;
        }

        for (HarfCifti cift : harfCiftiAyristir(ozellik(bilgi, AYIKLAMA_DONUSUM))) {
            temizlemeDizisi[cift.h1] = cift.h2;
            temizlemeDizisi[buyukHarf(cift.h1).charDeger()] = buyukHarf(cift.h2).charDeger();
        }

        List<HarfCifti> yumusamaDonusum = harfCiftiAyristir(ozellik(bilgi, YUMUSAMA_DONUSUM));
        for (HarfCifti cift : yumusamaDonusum) {
            harf(cift.h1).setYumusama(harf(cift.h2));
            buyukHarf(cift.h1).setYumusama(buyukHarf(cift.h2));
        }

        // eger sert donusum bilgisi harf ozelliklerinde yar almazsa
        // yumusama donusumun tersi olarak uygulanir.
        if (bilgi.containsKey(SERT_DONUSUM)) {
            for (HarfCifti cift : harfCiftiAyristir(ozellik(bilgi, SERT_DONUSUM))) {
                harf(cift.h1).setSertDonusum(harf(cift.h2));
                buyukHarf(cift.h1).setSertDonusum(buyukHarf(cift.h2));
            }
        } else {
            for (HarfCifti cift : yumusamaDonusum) {
                harf(cift.h2).setSertDonusum(harf(cift.h1));
                buyukHarf(cift.h2).setSertDonusum(buyukHarf(cift.h1));
            }
        }

        List<HarfCifti> asciiDonusum = harfCiftiAyristir(ozellik(bilgi, TURKCE_ASCII));
        for (HarfCifti cift : asciiDonusum) {
            asciifierDizisi[cift.h1] = cift.h2;
            mapHarf(cift.h1).setAsciiDonusum(harfler.get(cift.h2));
        }

        // eger ascii-turkce donusum ikilileri harf dosyasinda belirtilimisse okunur.
        // yoksa turkce-ascii ikililerinin tersi kullanilarak harflerin turkceDonusum ozellikleri belirlenir.
        if (bilgi.containsKey(ASCII_TURKCE)) {
            for (HarfCifti cift : harfCiftiAyristir(ozellik(bilgi, ASCII_TURKCE)))
                mapHarf(cift.h1).setTurkceDonusum(harfler.get(cift.h2));
        } else {
            for (HarfCifti cift : asciiDonusum)
                harf(cift.h2).setTurkceDonusum(harf(cift.h1));
        }

        // bazi turkcelerde yabanci dillerden gelen bazi kokler normal kalin-kalin sesli
        // uretimini bozar. Eger harf ozelliklerinde belirtilmisse burada ince ozelligine sahip kalin
        // sesli kopyalari uretilir. bu harfler normal sesli listesnde yer almaz. kiyaslama sirasinda
        // kalin hali ile ayni kabul edilir.
        if (bilgi.containsKey(OZEL_INCE_SESLI)) {
            for (char c : harfAyristir(ozellik(bilgi, OZEL_INCE_SESLI))) {
                TurkceHarf inceltilmisKalinSesli = harf(c).clone();
                inceltilmisKalinSesli.setInceSesli(true);
                ozelInceSesliler.put(harf(c), inceltilmisKalinSesli);
            }
        }
    }

    protected String ozellik(Map<String, String> harfOzellikleri, String anahtar) {
        if (harfOzellikleri.containsKey(anahtar))
            return harfOzellikleri.get(anahtar);
        else {
            logger.warning("harf ozelligi bulunamiyor: " + anahtar);
            return "";
        }
    }

    private TurkceHarf mapHarf(char c) {
        if (!harfler.containsKey(c))
            throw new RuntimeException(c + " icin Turkce Harf Bulunamiyor!");
        else return harfler.get(c);
    }

    /**
     * "a,b,c,d" seklindeki bir Stringi bosluklardan temizleyip {'a','b','c','d'} char dizisine donusturur.
     *
     * @param tum giris kelimesi
     * @return virgul ile ayrilmis karater dizisi.
     */
    protected char[] harfAyristir(String tum) {
        tum = tum.replaceAll("[ \t]", "");
        String[] charStrDizi = virgulReg.split(tum);
        char[] cDizi = new char[charStrDizi.length];
        for (int i = 0; i < charStrDizi.length; i++) {
            if (charStrDizi[i].length() != 1)
                logger.warning(tum + "ayristirilirken tek harf bekleniyordu. " + Arrays.toString(charStrDizi) + " uygun degil");
            cDizi[i] = charStrDizi[i].charAt(0);
        }
        return cDizi;
    }

    /**
     * "a-b,c-d,e-f" seklindeki Stringi Harf cifti listesine donusturur.
     *
     * @param tum giris.
     * @return TurkceHarf cifti tasiyan HarfCifti listesi
     */
    protected List<HarfCifti> harfCiftiAyristir(String tum) {
        tum = tum.replaceAll("[ \t]", "");
        String[] charStrDizi = virgulReg.split(tum);
        List<HarfCifti> ciftler = new ArrayList<HarfCifti>(charStrDizi.length);
        for (String s : charStrDizi) {
            String[] cift = tireReg.split(s);
            if (cift.length != 2)
                logger.warning(tum + "ayristirilirken harf cifti  bekleniyordu. " + s + " uygun degil.");
            if (cift[0].length() != 1 || cift[1].length() != 1)
                logger.warning(tum + "ayristirilirken tek harf bekleniyordu. " + Arrays.toString(charStrDizi) + " uygun degil");
            char h1 = cift[0].charAt(0);
            char h2 = cift[1].charAt(0);
            ciftler.add(new HarfCifti(h1, h2));
        }
        return ciftler;
    }

    protected class HarfCifti {

        final char h1;
        final char h2;

        public HarfCifti(char h1, char h2) {
            this.h1 = h1;
            this.h2 = h2;
        }
    }


}
