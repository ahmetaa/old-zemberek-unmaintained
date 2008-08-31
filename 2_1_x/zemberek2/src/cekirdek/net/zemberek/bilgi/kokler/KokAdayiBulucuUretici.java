/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

/*
 * Created on 16.Kas.2004
 *
 */
package net.zemberek.bilgi.kokler;

public interface KokAdayiBulucuUretici {
    public KokAdayiBulucu kesinKokBulucu();

    public KokAdayiBulucu toleransliKokBulucu(int tolerans);

    public KokAdayiBulucu asciiKokBulucu();
}
