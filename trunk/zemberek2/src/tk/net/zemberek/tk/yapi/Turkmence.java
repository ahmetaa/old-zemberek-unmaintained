/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tk.yapi;

import net.zemberek.tk.islemler.TurkmenceCozumlemeYardimcisi;
import net.zemberek.tk.yapi.ek.EkUreticiTm;
import net.zemberek.tk.yapi.ek.TurkmenceEkOzelDurumUretici;
import static net.zemberek.tk.yapi.ek.TurkmenceEkAdlari.*;
import net.zemberek.tk.yapi.kok.TurkmenceKokOzelDurumBilgisi;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.DilAyarlari;
import net.zemberek.yapi.KelimeTipi;
import static net.zemberek.yapi.KelimeTipi.*;
import net.zemberek.yapi.ek.*;

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
        return new Locale("tk");
    }

    public Class alfabeSinifi() {
        return Alfabe.class;
    }

    public Class ekYoneticiSinifi() {
        return TemelEkYonetici.class;
    }

    public Class heceleyiciSinifi() {
        return TurkmenceHeceleyici.class;
    }

    public Class kokOzelDurumBilgisiSinifi() {
        return TurkmenceKokOzelDurumBilgisi.class;
    }

    public Class cozumlemeYardimcisiSinifi() {
        return TurkmenceCozumlemeYardimcisi.class;
    }

    public String[] duzYaziKokDosyalari() {
        return new String[]{"kaynaklar/tk/bilgi/kokler.txt"};
    }

    public EkKuralBilgisi ekKuralBilgisi() {
        return new TemelEkUretimKurali.TemelKuralBilgisi();
    }    

    public Map<String, KelimeTipi> kokTipiAdlari() {
        Map kokTipAdlari = new HashMap();
        kokTipAdlari.put("AT", ISIM);
        kokTipAdlari.put("ISH", FIIL);
        kokTipAdlari.put("SI", SIFAT);
        kokTipAdlari.put("SA", SAYI);
        kokTipAdlari.put("YA", YANKI);
        kokTipAdlari.put("ZA", ZAMIR);
        kokTipAdlari.put("SO", SORU);
        kokTipAdlari.put("IM", IMEK);
        kokTipAdlari.put("ZAMAN", ZAMAN);
        kokTipAdlari.put("HATALI", HATALI);
        kokTipAdlari.put("EDAT", EDAT);
        kokTipAdlari.put("BAGLAC", BAGLAC);
        kokTipAdlari.put("OZ", OZEL);
        kokTipAdlari.put("UN", UNLEM);
        kokTipAdlari.put("KI", KISALTMA);
        return kokTipAdlari;
    }

    public Map<KelimeTipi, String> baslangiEkAdlari() {
        Map<KelimeTipi, String> baslangicEkAdlari = new EnumMap(KelimeTipi.class);
        baslangicEkAdlari.put(ISIM, AT_SADA_BOS);
        baslangicEkAdlari.put(SIFAT, AT_SADA_BOS);
        baslangicEkAdlari.put(FIIL, ISLIK_SADA_BOS);
        baslangicEkAdlari.put(ZAMAN, ZAMAN_SADA_BOS);
        baslangicEkAdlari.put(ZAMIR, CALISMA_SADA_BOS);
        baslangicEkAdlari.put(SAYI, SAN_SADA_BOS);
        baslangicEkAdlari.put(OZEL, HASAT_SADA_BOS);
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
