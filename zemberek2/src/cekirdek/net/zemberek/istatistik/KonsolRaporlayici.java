/*
 * Created on 20.Mar.2005
 *
 */
package net.zemberek.istatistik;

public class KonsolRaporlayici extends TemelRaporlayici implements IstatistikRaporlayici {

    public KonsolRaporlayici(Istatistikler istatistikler) {
        this.istatistikler = istatistikler;
    }

    public void raporla() {
        this.raporla(System.out, "UTF-8");
    }

}
