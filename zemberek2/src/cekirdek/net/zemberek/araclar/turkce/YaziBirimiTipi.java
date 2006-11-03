/**
 */
package net.zemberek.araclar.turkce;

public class YaziBirimiTipi {
    public static final YaziBirimiTipi KELIME = new YaziBirimiTipi("KELIME");
    public static final YaziBirimiTipi NOKTALAMA = new YaziBirimiTipi("NOKTALAMA");
    public static final YaziBirimiTipi BOSLUK = new YaziBirimiTipi("BOSLUK");
    public static final YaziBirimiTipi CUMLE = new YaziBirimiTipi("CUMLE");
    public static final YaziBirimiTipi PARAGRAF = new YaziBirimiTipi("PARAGRAF");
    public static final YaziBirimiTipi DIGER = new YaziBirimiTipi("DIGER");

    private final String myName; // for debug only

    private YaziBirimiTipi(String name) {
        myName = name;
    }

    public String toString() {
        return myName;
    }
}
