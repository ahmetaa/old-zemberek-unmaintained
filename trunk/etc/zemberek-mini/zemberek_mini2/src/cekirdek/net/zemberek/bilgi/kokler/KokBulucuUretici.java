/*
 * Created on 16.Kas.2004
 *
 */
package net.zemberek.bilgi.kokler;

/**
 * @author MDA
 */
public interface KokBulucuUretici {
    public KokBulucu getKesinKokBulucu();

    public KokBulucu getToleransliKokBulucu(int tolerans);

    public KokBulucu getAsciiKokBulucu();
}
