/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tr.yapi;

import net.zemberek.tr.islemler.TurkceCozumlemeYardimcisi;
import net.zemberek.tr.yapi.ek.EkUreticiTr;
import net.zemberek.tr.yapi.ek.TurkceEkOzelDurumUretici;
import net.zemberek.tr.yapi.kok.TurkceKokOzelDurumBilgisi;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.DilAyarlari;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.ek.*;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static net.zemberek.tr.yapi.ek.TurkceEkAdlari.*;
import static net.zemberek.yapi.KelimeTipi.*;

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
//                "kaynaklar/tr/bilgi/kisaltmalar.txt",
                "kaynaklar/tr/bilgi/bilisim.txt"};
//                "kaynaklar/tr/bilgi/kisi-adlari.txt"};
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
        tipMap.put("ZARF", ZARF);
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
        baslangicEkAdlari.put(ZARF, ISIM_KOK);
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
