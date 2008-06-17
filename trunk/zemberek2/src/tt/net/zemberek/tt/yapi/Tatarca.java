/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tt.yapi;

import static net.zemberek.tt.yapi.ek.TatarcaEkAdlari.IS_KOK;
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

import net.zemberek.tt.islemler.TatarcaCozumlemeYardimcisi;
import net.zemberek.tt.yapi.ek.EkUreticiTt;
import net.zemberek.tt.yapi.ek.TatarcaEkOzelDurumUretici;
import net.zemberek.tt.yapi.ek.TatarcaEkUretimKurali;
import net.zemberek.tt.yapi.kok.TatarcaKokOzelDurumBilgisi;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.DilAyarlari;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.ek.EkKuralBilgisi;
import net.zemberek.yapi.ek.EkOzelDurumUretici;
import net.zemberek.yapi.ek.EkUretici;
import net.zemberek.yapi.ek.TemelEkYonetici;


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