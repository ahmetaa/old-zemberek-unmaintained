/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.islemler;

import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.Kok;

import java.util.Comparator;

/**
 * iki kelimeyi kok kullanim frekansina gore kiyaslar. Sonucta o1 frekansi yuksek ise NEGATIF
 * aksi halde pozitif doner. azalan siralamada kullanilir.
 * User: ahmet
 * Date: Dec 10, 2005
 */
public class KelimeKokFrekansKiyaslayici implements Comparator<Kelime> {

    public int compare(Kelime o1, Kelime o2) {
        if (o1 == null || o2 == null) return -1;
        final Kok k1 = o1.kok();
        final Kok k2 = o2.kok();
        return k2.getFrekans() - k1.getFrekans();
    }
}
