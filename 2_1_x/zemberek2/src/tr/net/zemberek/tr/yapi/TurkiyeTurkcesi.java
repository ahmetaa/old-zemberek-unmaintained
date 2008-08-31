/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tr.yapi;

import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.BAGLAC_KOK;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.EDAT_KOK;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.FIIL_KOK;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.IMEK_KOK;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.ISIM_KOK;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.OZEL_KOK;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.SAYI_KOK;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.SORU_KOK;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.UNLEM_KOK;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.YANKI_KOK;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.ZAMAN_KOK;
import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.ZAMIR_KOK;
import static net.zemberek.yapi.KelimeTipi.BAGLAC;
import static net.zemberek.yapi.KelimeTipi.EDAT;
import static net.zemberek.yapi.KelimeTipi.FIIL;
import static net.zemberek.yapi.KelimeTipi.HATALI;
import static net.zemberek.yapi.KelimeTipi.IMEK;
import static net.zemberek.yapi.KelimeTipi.ISIM;
import static net.zemberek.yapi.KelimeTipi.KISALTMA;
import static net.zemberek.yapi.KelimeTipi.OZEL;
import static net.zemberek.yapi.KelimeTipi.SAYI;
import static net.zemberek.yapi.KelimeTipi.SIFAT;
import static net.zemberek.yapi.KelimeTipi.SORU;
import static net.zemberek.yapi.KelimeTipi.UNLEM;
import static net.zemberek.yapi.KelimeTipi.YANKI;
import static net.zemberek.yapi.KelimeTipi.ZAMAN;
import static net.zemberek.yapi.KelimeTipi.ZAMIR;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import net.zemberek.tr.islemler.TurkceCozumlemeYardimcisi;
import net.zemberek.tr.yapi.ek.EkUreticiTr;
import net.zemberek.tr.yapi.ek.TurkceEkOzelDurumUretici;
import net.zemberek.tr.yapi.kok.TurkceKokOzelDurumBilgisi;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.DilAyarlari;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.ek.EkKuralBilgisi;
import net.zemberek.yapi.ek.EkOzelDurumUretici;
import net.zemberek.yapi.ek.EkUretici;
import net.zemberek.yapi.ek.TemelEkUretimKurali;
import net.zemberek.yapi.ek.TemelEkYonetici;

/**
 * User: ahmet
 * Date: Sep 20, 2006
 */
public class TurkiyeTurkcesi implements DilAyarlari {

    public static final Locale LOCALE_TR = new Locale("tr");

    public Locale locale() {
        return LOCALE_TR;
    }

    public Class alfabeSinifi() {
        return Alfabe.class;
    }

    public Class ekYoneticiSinifi() {
        return TemelEkYonetici.class;
    }

    public Class heceleyiciSinifi() {
        return TurkceHeceleyici.class;
    }

    public Class kokOzelDurumBilgisiSinifi() {
        return TurkceKokOzelDurumBilgisi.class;
    }

    public Class cozumlemeYardimcisiSinifi() {
        return TurkceCozumlemeYardimcisi.class;
    }

    public EkUretici ekUretici(Alfabe alfabe) {
        return new EkUreticiTr(alfabe);
    }

    public EkOzelDurumUretici ekOzelDurumUretici(Alfabe alfabe) {
        return new TurkceEkOzelDurumUretici(alfabe);
    }

    public String[] duzYaziKokDosyalari() {
        return new String[]{
                "kaynaklar/tr/bilgi/duzyazi-kilavuz.txt",
                "kaynaklar/tr/bilgi/kisaltmalar.txt",
                "kaynaklar/tr/bilgi/bilisim.txt",
                "kaynaklar/tr/bilgi/kisi-adlari.txt"};
    }

    public EkKuralBilgisi ekKuralBilgisi() {
        return new TemelEkUretimKurali.TemelKuralBilgisi();
    }

    public Map<String, KelimeTipi> kokTipiAdlari() {
        Map<String, KelimeTipi> tipMap = new HashMap<String, KelimeTipi>();
        tipMap.put("IS", ISIM);
        tipMap.put("FI", FIIL);
        tipMap.put("SI", SIFAT);
        tipMap.put("SA", SAYI);
        tipMap.put("YA", YANKI);
        tipMap.put("ZA", ZAMIR);
        tipMap.put("SO", SORU);
        tipMap.put("IM", IMEK);
        tipMap.put("ZAMAN", ZAMAN);
        tipMap.put("HATALI", HATALI);
        tipMap.put("EDAT", EDAT);
        tipMap.put("BAGLAC", BAGLAC);
        tipMap.put("OZ", OZEL);
        tipMap.put("UN", UNLEM);
        tipMap.put("KI", KISALTMA);
        return tipMap;
    }

    public Map<KelimeTipi, String> baslangiEkAdlari() {
        Map<KelimeTipi, String> baslangicEkAdlari = new EnumMap<KelimeTipi, String>(KelimeTipi.class);
        baslangicEkAdlari.put(ISIM, ISIM_KOK);
        baslangicEkAdlari.put(SIFAT, ISIM_KOK);
        baslangicEkAdlari.put(FIIL, FIIL_KOK);
        baslangicEkAdlari.put(ZAMAN, ZAMAN_KOK);
        baslangicEkAdlari.put(ZAMIR, ZAMIR_KOK);
        baslangicEkAdlari.put(SAYI, SAYI_KOK);
        baslangicEkAdlari.put(SORU, SORU_KOK);
        baslangicEkAdlari.put(UNLEM, UNLEM_KOK);
        baslangicEkAdlari.put(EDAT, EDAT_KOK);
        baslangicEkAdlari.put(BAGLAC, BAGLAC_KOK);
        baslangicEkAdlari.put(OZEL, OZEL_KOK);
        baslangicEkAdlari.put(IMEK, IMEK_KOK);
        baslangicEkAdlari.put(YANKI, YANKI_KOK);
        baslangicEkAdlari.put(KISALTMA, ISIM_KOK);
        return baslangicEkAdlari;
    }

    public String ad() {
        return "Turkiye Turkcesi";
    }

}
