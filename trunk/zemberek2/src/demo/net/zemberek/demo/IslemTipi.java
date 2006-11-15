/**
 */
package net.zemberek.demo;

public class IslemTipi {
    public static final IslemTipi YAZI_DENETLE = new IslemTipi("YAZI_DENETLE");
    public static final IslemTipi YAZI_COZUMLE = new IslemTipi("YAZI_COZUMLE");
    public static final IslemTipi HECELE = new IslemTipi("HECELE");
    public static final IslemTipi ASCII_TURKCE = new IslemTipi("ASCII_TURKCE");
    public static final IslemTipi TURKCE_ASCII = new IslemTipi("TURKCE_ASCII");
    public static final IslemTipi ONER = new IslemTipi("ONER");
    public static final IslemTipi TEMIZLE = new IslemTipi("TEMIZLE");

    private final String myName; // for debug only

    private IslemTipi(String name) {
        myName = name;
    }

    public String toString() {
        return myName;
    }
}
