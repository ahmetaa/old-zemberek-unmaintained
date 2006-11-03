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
public interface KokBulucu {
    /**
     * @param giris: Uzerinde aday kok aramasi yapilacak giris kelimesi.
     * @return Aday kok dizisi
     */
    public List<Kok> getAdayKokler(String giris);
}
