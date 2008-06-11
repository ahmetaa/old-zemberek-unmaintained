/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

/*
 * Created on 06.Mar.2004
 */
package net.zemberek.bilgi.kokler;

import java.util.List;

import net.zemberek.yapi.Kok;

/**
 * Kok secme islemi yazim denetleme isleminin ilk asamalarindandir. Bu arayuz sayesinde
 * farkli Kok secim algoritmalari ve veri yapilari denenebilir.
 *
 * @author MDA
 */
public interface KokAdayiBulucu {
    /**
     * @param giris: Uzerinde aday kok aramasi yapilacak giris kelimesi.
     * @return Aday kok dizisi
     */
    public List<Kok> adayKokleriBul(String giris);
}
