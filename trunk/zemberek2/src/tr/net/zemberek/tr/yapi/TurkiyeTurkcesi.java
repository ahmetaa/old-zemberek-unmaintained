package net.zemberek.tr.yapi;

import net.zemberek.tr.islemler.TurkceCozumlemeYardimcisi;
import net.zemberek.tr.yapi.ek.TurkceEkAdlari;
import net.zemberek.tr.yapi.ek.EkUreticiTr;
import net.zemberek.tr.yapi.ek.TurkceEkOzelDurumUretici;
import net.zemberek.tr.yapi.kok.TurkceKokOzelDurumBilgisi;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.DilAyarlari;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.demo.TurkDiliTuru;
import net.zemberek.yapi.ek.EkUretici;
import net.zemberek.yapi.ek.EkOzelDurumUretici;
import net.zemberek.yapi.ek.TemelEkYonetici;

import java.util.HashMap;
import java.util.Map;
import java.util.EnumMap;
import java.util.Locale;

/**
 * User: ahmet
 * Date: Sep 20, 2006
 */
public class TurkiyeTurkcesi implements DilAyarlari {

    public Locale locale() {
        return new Locale("tr");
    }

    public Class alfabeSinifi() {
        return Alfabe.class;
    }

    public Class ekYoneticiSinifi() {
        return TemelEkYonetici.class;
    }

    public Class heceBulucuSinifi() {
        return TurkceHeceBulucu.class;
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

    public Map<String, KelimeTipi> kelimeTipiAdlari() {
        Map<String, KelimeTipi> tipMap = new HashMap();
        tipMap.put("IS", KelimeTipi.ISIM);
        tipMap.put("FI", KelimeTipi.FIIL);
        tipMap.put("SI", KelimeTipi.SIFAT);
        tipMap.put("SA", KelimeTipi.SAYI);
        tipMap.put("YA", KelimeTipi.YANKI);
        tipMap.put("ZA", KelimeTipi.ZAMIR);
        tipMap.put("SO", KelimeTipi.SORU);
        tipMap.put("IM", KelimeTipi.IMEK);
        tipMap.put("ZAMAN", KelimeTipi.ZAMAN);
        tipMap.put("HATALI", KelimeTipi.HATALI);
        tipMap.put("EDAT", KelimeTipi.EDAT);
        tipMap.put("BAGLAC", KelimeTipi.BAGLAC);
        tipMap.put("OZ", KelimeTipi.OZEL);
        tipMap.put("UN", KelimeTipi.UNLEM);
        tipMap.put("KI", KelimeTipi.KISALTMA);
        return tipMap;
    }

    public Map<KelimeTipi, String> baslangiEkAdlari() {
        Map<KelimeTipi, String> baslangicEkAdlari = new EnumMap(KelimeTipi.class);
        baslangicEkAdlari.put(KelimeTipi.ISIM, TurkceEkAdlari.ISIM_KOK);
        baslangicEkAdlari.put(KelimeTipi.SIFAT, TurkceEkAdlari.ISIM_KOK);
        baslangicEkAdlari.put(KelimeTipi.FIIL, TurkceEkAdlari.FIIL_KOK);
        baslangicEkAdlari.put(KelimeTipi.ZAMAN, TurkceEkAdlari.ZAMAN_KOK);
        baslangicEkAdlari.put(KelimeTipi.ZAMIR, TurkceEkAdlari.ZAMIR_KOK);
        baslangicEkAdlari.put(KelimeTipi.SAYI, TurkceEkAdlari.SAYI_KOK);
        baslangicEkAdlari.put(KelimeTipi.SORU, TurkceEkAdlari.SORU_KOK);
        baslangicEkAdlari.put(KelimeTipi.UNLEM, TurkceEkAdlari.UNLEM_KOK);
        baslangicEkAdlari.put(KelimeTipi.EDAT, TurkceEkAdlari.EDAT_KOK);
        baslangicEkAdlari.put(KelimeTipi.BAGLAC, TurkceEkAdlari.BAGLAC_KOK);
        baslangicEkAdlari.put(KelimeTipi.OZEL, TurkceEkAdlari.OZEL_KOK);
        baslangicEkAdlari.put(KelimeTipi.IMEK, TurkceEkAdlari.IMEK_KOK);
        baslangicEkAdlari.put(KelimeTipi.YANKI, TurkceEkAdlari.YANKI_KOK);
        baslangicEkAdlari.put(KelimeTipi.KISALTMA, TurkceEkAdlari.ISIM_KOK);
        return baslangicEkAdlari;
    }

    public String ad() {
        return "TURKIYE TURKCESI";
    }

}
