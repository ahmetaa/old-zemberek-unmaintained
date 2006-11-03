package net.zemberek.tm.yapi;

import net.zemberek.tm.islemler.TurkmenceCozumlemeYardimcisi;
import net.zemberek.tm.yapi.ek.EkUreticiTm;
import net.zemberek.tm.yapi.ek.TurkmenceEkAdlari;
import net.zemberek.tm.yapi.ek.TurkmenceEkOzelDurumUretici;
import net.zemberek.tm.yapi.kok.TurkmenceKokOzelDurumBilgisi;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.DilAyarlari;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.ek.EkOzelDurumUretici;
import net.zemberek.yapi.ek.EkUretici;
import net.zemberek.yapi.ek.TemelEkYonetici;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * User: ahmet
 * Date: Sep 6, 2005
 */
public class Turkmence implements DilAyarlari {

    public Locale locale() {
        return new Locale("tm");
    }

    public Class alfabeSinifi() {
        return Alfabe.class;
    }

    public Class ekYoneticiSinifi() {
        return TemelEkYonetici.class;
    }

    public Class heceBulucuSinifi() {
        return TurkmenceHeceBulucu.class;
    }

    public Class kokOzelDurumBilgisiSinifi() {
        return TurkmenceKokOzelDurumBilgisi.class;
    }

    public Class cozumlemeYardimcisiSinifi() {
        return TurkmenceCozumlemeYardimcisi.class;
    }

    public String[] duzYaziKokDosyalari() {
        return new String[]{"kaynaklar/tm/bilgi/kokler.txt"};
    }

    public Map<String, KelimeTipi> kelimeTipiAdlari() {
        Map kokTipAdlari = new HashMap();
        kokTipAdlari.put("AT", KelimeTipi.ISIM);
        kokTipAdlari.put("ISH", KelimeTipi.FIIL);
        kokTipAdlari.put("SI", KelimeTipi.SIFAT);
        kokTipAdlari.put("SA", KelimeTipi.SAYI);
        kokTipAdlari.put("YA", KelimeTipi.YANKI);
        kokTipAdlari.put("ZA", KelimeTipi.ZAMIR);
        kokTipAdlari.put("SO", KelimeTipi.SORU);
        kokTipAdlari.put("IM", KelimeTipi.IMEK);
        kokTipAdlari.put("ZAMAN", KelimeTipi.ZAMAN);
        kokTipAdlari.put("HATALI", KelimeTipi.HATALI);
        kokTipAdlari.put("EDAT", KelimeTipi.EDAT);
        kokTipAdlari.put("BAGLAC", KelimeTipi.BAGLAC);
        kokTipAdlari.put("OZ", KelimeTipi.OZEL);
        kokTipAdlari.put("UN", KelimeTipi.UNLEM);
        kokTipAdlari.put("KI", KelimeTipi.KISALTMA);
        return kokTipAdlari;
    }

    public Map<KelimeTipi, String> baslangiEkAdlari() {
        Map<KelimeTipi, String> baslangicEkAdlari = new EnumMap(KelimeTipi.class);
        baslangicEkAdlari.put(KelimeTipi.ISIM, TurkmenceEkAdlari.AT_SADA_BOS);
        baslangicEkAdlari.put(KelimeTipi.SIFAT, TurkmenceEkAdlari.AT_SADA_BOS);
        baslangicEkAdlari.put(KelimeTipi.FIIL, TurkmenceEkAdlari.ISLIK_SADA_BOS);
        baslangicEkAdlari.put(KelimeTipi.ZAMAN, TurkmenceEkAdlari.ZAMAN_SADA_BOS);
        baslangicEkAdlari.put(KelimeTipi.ZAMIR, TurkmenceEkAdlari.CALISMA_SADA_BOS);
        baslangicEkAdlari.put(KelimeTipi.SAYI, TurkmenceEkAdlari.SAN_SADA_BOS);
        baslangicEkAdlari.put(KelimeTipi.OZEL, TurkmenceEkAdlari.HASAT_SADA_BOS);
        return baslangicEkAdlari;
    }

    public String ad() {
        return "TURKMENCE";
    }

    public EkUretici ekUretici(Alfabe alfabe) {
        return new EkUreticiTm(alfabe);
    }

    public EkOzelDurumUretici ekOzelDurumUretici(Alfabe alfabe) {
        return new TurkmenceEkOzelDurumUretici(alfabe);
    }
}
