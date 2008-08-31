/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

/*
 * Created on 11.Ara.2004
 *
 */
package net.zemberek.istatistik;

/**
 * @author MDA
 */
public class Hece implements Comparable<Hece> {
    private String hece = null;
    private long kullanim = 1;

    public Hece(String hece) {
        this.hece = hece;
    }

    public void arttir() {
        kullanim++;
    }

    public int compareTo(Hece o) {
        return (int)(o.kullanim - this.kullanim);
    }

    /**
     * @return Returns the kullanim.
     */
    public long getKullanim() {
        return kullanim;
    }

    /**
     * @return Returns the sonHeceHarfSayisi.
     */
    public String getHece() {
        return hece;
    }

    public String toString() {
        return hece + ":" + kullanim;
    }
}
