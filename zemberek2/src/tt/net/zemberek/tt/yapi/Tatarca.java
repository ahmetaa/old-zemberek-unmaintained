/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tt.yapi;

import net.zemberek.tt.islemler.TatarcaCozumlemeYardimcisi;
import static net.zemberek.tt.yapi.ek.TatarcaEkAdlari.IS_KOK;
import net.zemberek.tt.yapi.ek.TatarcaEkOzelDurumUretici;
import net.zemberek.tt.yapi.ek.TatarcaEkUretimKurali;
import net.zemberek.tt.yapi.ek.EkUreticiTt;
import net.zemberek.tt.yapi.kok.TatarcaKokOzelDurumBilgisi;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.DilAyarlari;
import net.zemberek.yapi.KelimeTipi;
import static net.zemberek.yapi.KelimeTipi.*;
import net.zemberek.yapi.ek.*;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class Tatarca implements DilAyarlari {

    public Locale locale() {
        return new Locale("tt");
    }

    public Class<Alfabe> alfabeSinifi() {
        return Alfabe.class;
    }

    public Class<TemelEkYonetici> ekYoneticiSinifi() {
        return TemelEkYonetici.class;
    }

    public Class<TatarcaHeceleyici> heceleyiciSinifi() {
        return TatarcaHeceleyici.class;
    }

    public Class<TatarcaKokOzelDurumBilgisi> kokOzelDurumBilgisiSinifi() {
        return TatarcaKokOzelDurumBilgisi.class;
    }

    public Class<TatarcaCozumlemeYardimcisi> cozumlemeYardimcisiSinifi() {
        return TatarcaCozumlemeYardimcisi.class;
    }

    public EkUretici ekUretici(Alfabe alfabe) {
        return new EkUreticiTt(alfabe);
    }

    public EkOzelDurumUretici ekOzelDurumUretici(Alfabe alfabe) {
        return new TatarcaEkOzelDurumUretici(alfabe);
    }

    public String[] duzYaziKokDosyalari() {
        return new String[]{"kaynaklar/tt/bilgi/kokler.txt"};
    }

    public EkKuralBilgisi ekKuralBilgisi() {
        return new TatarcaEkUretimKurali.KarakterBilgisi();
    }

    public Map<String, KelimeTipi> kokTipiAdlari() {
        //TODO: burasi azericeye gore duzenlenmeli
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
        //TODO: Baslangic ekleri simidlik sadece ISIM_KOK baslangic ekini gosteriyor..
        baslangicEkAdlari.put(ISIM, IS_KOK);
        baslangicEkAdlari.put(SIFAT, IS_KOK);
        baslangicEkAdlari.put(FIIL, IS_KOK);
        baslangicEkAdlari.put(ZAMAN, IS_KOK);
        baslangicEkAdlari.put(ZAMIR, IS_KOK);
        baslangicEkAdlari.put(SAYI, IS_KOK);
        baslangicEkAdlari.put(SORU, IS_KOK);
        baslangicEkAdlari.put(UNLEM, IS_KOK);
        baslangicEkAdlari.put(EDAT, IS_KOK);
        baslangicEkAdlari.put(BAGLAC, IS_KOK);
        baslangicEkAdlari.put(OZEL, IS_KOK);
        baslangicEkAdlari.put(IMEK, IS_KOK);
        baslangicEkAdlari.put(YANKI, IS_KOK);
        baslangicEkAdlari.put(KISALTMA, IS_KOK);
        return baslangicEkAdlari;
    }

    public String ad() {
        return "TATARCA";
    }
}