/*
 * Created on 19.Haz.2005
 *
 */
package net.zemberek.istatistik;

import net.zemberek.yapi.ek.Ek;

public class EkFrekansBilgisi implements Comparable {
    private int kullanim = 1;
    private double kullanimFrekansi = 0.0d;
    private Ek ek;

    public EkFrekansBilgisi(Ek ek) {
        this.ek = ek;
    }

    public void kullanimArttir() {
        kullanim++;
    }

    public int getKullanim() {
        return kullanim;
    }

    public int compareTo(Object o) {
        EkFrekansBilgisi giris = (EkFrekansBilgisi) o;
        return (giris.kullanim - this.kullanim > 0 ? 1 : -1);
    }

    public Ek getEk() {
        return ek;
    }

    public double getKullanimFrekansi() {
        return kullanimFrekansi;
    }

    public void setKullanimFrekansi(double kullanimFrekansi) {
        this.kullanimFrekansi = kullanimFrekansi;
    }

}


