/*
 * Created on 14.Eki.2005
 *
 */
package net.zemberek.istatistik;

public class BasitKokIstatistikBilgisi implements KokIstatistikBilgisi{
    protected int kullanimFrekansi; // Gerçek kullaným frekansý * 1000000 

    public BasitKokIstatistikBilgisi(int frekansi) {
        kullanimFrekansi = frekansi;
    }

    public int getKullanimFrekansi() {
        return kullanimFrekansi;
    }

    public void setKullanimFrekansi(int kullanimFrekansi) {
        this.kullanimFrekansi = kullanimFrekansi;
    }
}
