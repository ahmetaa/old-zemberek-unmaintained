/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi.ek;

import net.zemberek.tr.yapi.ek.TurkceEkAdlari;

import org.junit.Test;

/**
 */
public class TestFiilEkleri extends BaseTestEkler {

    private Ek ek(String ad) {
        return  dilBilgisi.ekler().ek(ad);
    }

    @Test
    public void testFiilCesine() {
        Ek ek = ek(TurkceEkAdlari.FIIL_GIBI_CESINE);
        String[] strs = {"gelir", "okuyacak", "yazar", "yer"};
        String[] gercekEkler = {"cesine", "cas\u0131na", "cas\u0131na", "cesine"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }

    @Test
    public void testFiilDik() {
        Ek ek = ek(TurkceEkAdlari.FIIL_BELIRTME_DIK);
        String[] strs = {"gel", "oku", "sek", "ye"};
        String[] gercekEkler = {"dik", "duk", "tik", "dik"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }


    @Test
    public void testFiilDikce() {
        Ek ek = ek(TurkceEkAdlari.FIIL_DEVAMLILIK_DIKCE);
        String[] strs = {"gel", "oku", "sek", "ye"};
        String[] gercekEkler = {"dik\u00e7e", "duk\u00e7a", "tik\u00e7e", "dik\u00e7e"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }

    @Test
    public void testFiilDiliGecmisZaman() {
        Ek ek = ek(TurkceEkAdlari.FIIL_GECMISZAMAN_DI);
        String[] strs = {"gel", "oku", "sek", "ye"};
        String[] gercekEkler = {"di", "du", "ti", "di"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }

    @Test
    public void testFiilDonusumEn() {
        Ek ek = ek(TurkceEkAdlari.FIIL_DONUSUM_EN);
        String[] strs = {"gel", "oku", "sek", "ye"};
        String[] gercekEkler = {"en", "yan", "en", "yen"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }

    @Test
    public void testFiilEdilgen() {
        Ek ek = ek(TurkceEkAdlari.FIIL_EDILGEN_IL);
        String[] strs = {"gel", "sek"};
        String[] gercekEkler = {"in", "il"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }

    @Test
    public void testFiilEli() {
        Ek ek = ek(TurkceEkAdlari.FIIL_BERI_ELI);
        String[] strs = {"gel", "oku", "sek", "ye"};
        String[] gercekEkler = {"eli", "yal\u0131", "eli", "yeli"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }

    @Test
    public void testFiilEmirO() {
        Ek ek = ek(TurkceEkAdlari.FIIL_EMIR_O_SIN);
        String[] strs = {"gel", "oku", "sek", "ye"};
        String[] gercekEkler = {"sin", "sun", "sin", "sin"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }

    @Test
    public void testFiilEmirOnlar() {
        Ek ek = ek(TurkceEkAdlari.FIIL_EMIR_ONLAR_SINLER);
        String[] strs = {"gel", "oku", "sek", "ye"};
        String[] gercekEkler = {"sinler", "sunlar", "sinler", "sinler"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }

    @Test
    public void testFiilEmirSiz() {
        Ek ek = ek(TurkceEkAdlari.FIIL_EMIR_SIZ_IN);
        String[] strs = {"gel", "oku", "sek", "ye"};
        String[] gercekEkler = {"in", "yun", "in", "yin"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }

    @Test
    public void testFiilEmirSizResmi() {
        Ek ek = ek(TurkceEkAdlari.FIIL_EMIR_SIZRESMI_INIZ);
        String[] strs = {"gel", "oku", "sek", "ye"};
        String[] gercekEkler = {"iniz", "yunuz", "iniz", "yiniz"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }

    @Test
    public void testFiilErek() {
        Ek ek = ek(TurkceEkAdlari.FIIL_SUREKLILIK_EREK);
        String[] strs = {"gel", "oku", "bak", "eri"};
        String[] gercekEkler = {"erek", "yarak", "arak", "yerek"}; //lutfen gulmeyin..
        olusanEkKontrol(strs, gercekEkler, ek);
    }

    @Test
    public void testFiilEttirgenTekrar() {
        Ek ek = ek(TurkceEkAdlari.FIIL_ETTIRGEN_TEKRAR_T);
        String[] strs = {"gerdir", "bakt\u0131r", "erittir"};
        String[] gercekEkler = {"t", "t", "t"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }

    @Test
    public void testFiilGelecekZaman() {
        Ek ek = ek(TurkceEkAdlari.FIIL_GELECEKZAMAN_ECEK);
        String[] strs = {"ger", "bak", "oku"};
        String[] gercekEkler = {"ecek", "acak", "yacak"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }

    public void testFiilGenisZaman() {
        //TODO: daha cok ornek eklenmeli
/*        Ek ek = TurkceEkYonetici.FIIL_GENIS_ZAMAN;
        Kok kok = new Kok("ger");
        kok.setOzelDurumlar(new HashSet(1));
        kok.ozelDurumlar().add(OzelDurumlar.GENIS_ZAMAN);
        ek.ekOlustur(new Kelime(kok));
        assertEquals(ek.icerik().toString(), "er");
        Kok kok2 = new Kok("gel", KelimeTipi.FIIL);
        ek.ekOlustur(new Kelime(kok2));
        assertEquals(ek.icerik().toString(), "ir");*/
    }

    @Test
    public void testFiilIci() {
        Ek ek = ek(TurkceEkAdlari.FIIL_TANIMLAMA_ICI);
        String[] strs = {"ger", "bak", "oku"};
        String[] gercekEkler = {"ici", "\u0131c\u0131", "yucu"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }

    @Test
    public void testFiilIm() {
        Ek ek = ek(TurkceEkAdlari.FIIL_DONUSUM_IM);
        String[] strs = {"ger", "bak", "oku"};
        String[] gercekEkler = {"im", "\u0131m", "num"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }

    @Test
    public void testFiilInce() {
        Ek ek = ek(TurkceEkAdlari.FIIL_ZAMAN_INCE);
        String[] strs = {"ger", "bak", "oku"};
        String[] gercekEkler = {"ince", "\u0131nca", "yunca"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }

    @Test
    public void testFiilIp() {
        Ek ek = ek(TurkceEkAdlari.FIIL_IMSI_IP);
        String[] strs = {"ger", "bak", "oku"};
        String[] gercekEkler = {"ip", "\u0131p", "yup"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }

    @Test
    public void testFiilIs() {
        Ek ek = ek(TurkceEkAdlari.FIIL_DONUSUM_IS);
        String[] strs = {"ger", "bak", "oku"};
        String[] gercekEkler = {"i\u015f", "\u0131\u015f", "yu\u015f"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }

/*    public void testFiilIstek() {
        Ek ek = TurkceEkYonetici.FIIL_ISTEK;
        String[] strs = {"ger", "bak", "oku"};
        String[] gercekEkler = {"e", "a", "ya"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }

    public void testFiilKen() {
        Ek ek = TurkceEkYonetici.FIIL_KEN;
        String[] strs = {"gerer", "bakar", "okur"};
        String[] gercekEkler = {"ken", "ken", "ken"};
        olusanEkKontrol(strs, gercekEkler, ek);
        assertTrue(ek.ilkHarfUygunmu(TurkceAlfabe.HARF_k));
    }

    public void testFiilMastar() {
        Ek ek = TurkceEkYonetici.FIIL_MASTAR;
        String[] strs = {"ger", "bak", "oku"};
        String[] gercekEkler = {"mek", "mak", "mak"};
        olusanEkKontrol(strs, gercekEkler, ek);
        assertTrue(ek.ilkHarfUygunmu(TurkceAlfabe.HARF_m));
    }

    public void testFiilMisliGecmisZaman() {
        Ek ek = TurkceEkYonetici.FIIL_MISLI_GECMIS_ZAMAN;
        String[] strs = {"ger", "bak", "oku"};
        String[] gercekEkler = {"mi\u015f", "m\u0131\u015f", "mu\u015f"};
        olusanEkKontrol(strs, gercekEkler, ek);
        assertTrue(ek.ilkHarfUygunmu(TurkceAlfabe.HARF_m));
    }

    public void testFiilOlumsuzluk() {
        Ek ek = TurkceEkYonetici.FIIL_OLUMSUZLUK;
        String[] strs = {"ger", "bak", "oku"};
        String[] gercekEkler = {"me", "ma", "ma"};
        olusanEkKontrol(strs, gercekEkler, ek);
        assertTrue(ek.ilkHarfUygunmu(TurkceAlfabe.HARF_m));
    }

    public void testFiilSahisBen() {
        Ek ek = TurkceEkYonetici.FIIL_SAHIS_BEN;
        Kok kok = new Kok("oku");
        Ek olumsuz = TurkceEkYonetici.FIIL_OLUMSUZLUK;
        Kelime kelime = new Kelime(kok);
        olumsuz.ekOlustur(new Kelime(kok));
        kelime.ekEkle(olumsuz);
        ek.ekOlustur(kelime);
        assertEquals(ek.icerik().toString(), "m");
        Kok kok2 = new Kok("okur");
        ek.ekOlustur(new Kelime(kok2));
        assertEquals(ek.icerik().toString(), "um");
        Kok kok3 = new Kok("gele");
        ek.ekOlustur(new Kelime(kok3));
        assertEquals(ek.icerik().toString(), "yim");
    }

    public void testFiilSahisBiz() {
        Ek ek = TurkceEkYonetici.FIIL_SAHIS_BIZ;
        Kok kok = new Kok("oku");
        Ek olumsuz = TurkceEkYonetici.FIIL_SART;
        Kelime kelime = new Kelime(kok);
        olumsuz.ekOlustur(kelime);
        kelime.ekEkle(olumsuz);
        ek.ekOlustur(kelime);
        assertEquals(ek.icerik().toString(), "k");
        Ek istek = TurkceEkYonetici.FIIL_ISTEK;
        Kelime kelime2 = new Kelime(kok);
        istek.ekOlustur(kelime2);
        kelime2.ekEkle(istek);
        ek.ekOlustur(kelime2);
        assertEquals(ek.icerik().toString(), "l\u0131m");
        Kok kok2 = new Kok("okur");
        ek.ekOlustur(new Kelime(kok2));
        assertEquals(ek.icerik().toString(), "uz");
        Kok kok3 = new Kok("gelme");
        ek.ekOlustur(new Kelime(kok3));
        assertEquals(ek.icerik().toString(), "yiz");
    }

    public void testFiilSahisO() {
        Ek ek = TurkceEkYonetici.FIIL_SAHIS_O;
        Kok kok = new Kok("oku");
        Ek olumsuz = TurkceEkYonetici.FIIL_OLUMSUZLUK;
        Kelime kelime = new Kelime(kok);
        olumsuz.ekOlustur(new Kelime(kok));
        kelime.ekEkle(olumsuz);
        ek.ekOlustur(kelime);
        assertEquals(ek.icerik().toString(), "z");
        Kok kok2 = new Kok("okur");
        assertTrue(ek.ekOlustur(new Kelime(kok2)) == false);
        assertTrue(ek.ilkHarfUygunmu(TurkceAlfabe.HARF_z));
    }

    public void testFiilSahisOnlar() {
        Ek ek = TurkceEkYonetici.FIIL_SAHIS_ONLAR;
        Kok kok = new Kok("oku");
        Ek olumsuz = TurkceEkYonetici.FIIL_OLUMSUZLUK;
        Kelime kelime = new Kelime(kok);
        olumsuz.ekOlustur(new Kelime(kok));
        kelime.ekEkle(olumsuz);
        ek.ekOlustur(kelime);
        assertEquals(ek.icerik().toString(), "zlar");
        Kok kok2 = new Kok("okur");
        ek.ekOlustur(new Kelime(kok2));
        assertEquals(ek.icerik().toString(), "lar");
        assertTrue(ek.ilkHarfUygunmu(TurkceAlfabe.HARF_z) && ek.ilkHarfUygunmu(TurkceAlfabe.HARF_l));
    }

    public void testFiilSahisSen() {
        Ek ek = TurkceEkYonetici.FIIL_SAHIS_SEN;
        Kok kok = new Kok("okur");
        Ek olumsuz = TurkceEkYonetici.FIIL_SART;
        Kelime kelime = new Kelime(kok);
        olumsuz.ekOlustur(kelime);
        kelime.ekEkle(olumsuz);
        ek.ekOlustur(kelime);
        assertEquals(ek.icerik().toString(), "n");
        Ek istek = TurkceEkYonetici.FIIL_OLUMSUZLUK;
        Kelime kelime2 = new Kelime(new Kok("oku"));
        istek.ekOlustur(kelime2);
        kelime2.ekEkle(istek);
        ek.ekOlustur(kelime2);
        assertEquals(ek.icerik().toString(), "zs\u0131n");
        Kok kok2 = new Kok("okur");
        ek.ekOlustur(new Kelime(kok2));
        assertEquals(ek.icerik().toString(), "sun");
    }

    public void testFiilSahisSiz() {
        Ek ek = TurkceEkYonetici.FIIL_SAHIS_SIZ;
        Kok kok = new Kok("okur");
        Ek olumsuz = TurkceEkYonetici.FIIL_SART;
        Kelime kelime = new Kelime(kok);
        olumsuz.ekOlustur(kelime);
        kelime.ekEkle(olumsuz);
        ek.ekOlustur(kelime);
        assertEquals(ek.icerik().toString(), "n\u0131z");
        Ek istek = TurkceEkYonetici.FIIL_OLUMSUZLUK;
        Kelime kelime2 = new Kelime(new Kok("oku"));
        istek.ekOlustur(kelime2);
        kelime2.ekEkle(istek);
        ek.ekOlustur(kelime2);
        assertEquals(ek.icerik().toString(), "zs\u0131n\u0131z");
        Kok kok2 = new Kok("okur");
        ek.ekOlustur(new Kelime(kok2));
        assertEquals(ek.icerik().toString(), "sunuz");
    }

    public void testFiilSart() {
        Ek ek = TurkceEkYonetici.FIIL_SART;
        Kok kok2 = new Kok("okur");
        ek.ekOlustur(new Kelime(kok2));
        assertEquals(ek.icerik().toString(), "sa");
        assertTrue(ek.ilkHarfUygunmu(TurkceAlfabe.HARF_s));
    }

    public void testFiilSimdikiZaman() {
        Ek ek = TurkceEkYonetici.FIIL_SIMDIKI_ZAMAN;
        String[] strs = {"ger", "bak", "oku"};
        String[] gercekEkler = {"iyor", "\u0131yor", "uyor"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }

    public void testFiilSurerlikAdur() {
        Ek ek = TurkceEkYonetici.FIIL_SURERLIK_ADUR;
        String[] strs = {"ger", "bak", "oku"};
        String[] gercekEkler = {"edur", "adur", "yadur"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }

    public void testFiilSurerlikAgel() {
        Ek ek = TurkceEkYonetici.FIIL_SURERLIK_AGEL;
        String[] strs = {"ger", "bak", "oku"};
        String[] gercekEkler = {"egel", "agel", "yagel"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }

    public void testFiilSurerlikAkal() {
        Ek ek = TurkceEkYonetici.FIIL_SURERLIK_AKAL;
        String[] strs = {"ger", "bak", "oku"};
        String[] gercekEkler = {"ekal", "akal", "yakal"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }
    public void testFiilSurerlikAgor() {
        String gor = "g" + '\u00f6' + "r";
        Ek ek = TurkceEkYonetici.FIIL_SURERLIK_AGOR;
        String[] strs = {"ger", "bak", "oku"};
        String[] gercekEkler = {"e"+gor, "a"+gor, "ya"+gor};
        olusanEkKontrol(strs, gercekEkler, ek);
    }
    public void testFiilTezlik() {
        Ek ek = TurkceEkYonetici.FIIL_TEZLIK;
        String[] strs = {"ger", "bak", "oku"};
        String[] gercekEkler = {"iver", "\u0131ver", "yuver"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }

    public void testFiilYaklasmaAyaz() {
        Ek ek = TurkceEkYonetici.FIIL_YAKLASMA_AYAZ;
        String[] strs = {"ger", "bak", "oku"};
        String[] gercekEkler = {"eyaz", "ayaz", "yayaz"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }

    public void testFiilYetenek() {
        Ek ek = TurkceEkYonetici.FIIL_YETENEK;
        String[] strs = {"ger", "bak", "oku"};
        String[] gercekEkler = {"ebil", "abil", "yabil"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }

    public void testFiilZorunluluk() {
        Ek ek = TurkceEkYonetici.FIIL_ZORUNLULUK;
        String[] strs = {"ger", "bak", "oku"};
        String[] gercekEkler = {"meli", "mal\u0131", "mal\u0131"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }

    public void testFiilSene() {
        Ek ek = TurkceEkYonetici.FIIL_SENE;
        String[] strs = {"ger", "bak", "oku", "git"};
        String[] gercekEkler = {"sene", "sana", "sana", "sene"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }*/
}
