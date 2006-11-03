package net.zemberek.az.yapi;

import net.zemberek.yapi.KelimeTipi;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

/**
 * User: ahmet
 * Date: May 20, 2006
 */
public class KokTipiAdlari {

    private static final Map<String, KelimeTipi> kokTipAdlari = new HashMap<String, KelimeTipi>();

    static {
        kokTipAdlari.put("AD", KelimeTipi.ISIM);
        kokTipAdlari.put("EY", KelimeTipi.FIIL);
        kokTipAdlari.put("RA", KelimeTipi.SAYI);
        kokTipAdlari.put("YA", KelimeTipi.YANKI);
        kokTipAdlari.put("ZAMAN", KelimeTipi.ZAMAN);
        kokTipAdlari.put("OZ", KelimeTipi.OZEL);
    }

    public static Map<String, KelimeTipi> getKokTipAdlari() {
        return Collections.unmodifiableMap(kokTipAdlari);
    }
}
