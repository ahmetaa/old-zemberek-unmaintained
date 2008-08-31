/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.islemler;

import net.zemberek.yapi.Kok;

/**
 * Kok bulma arayuzu (stemmer)
 */
public interface KokBulucu {

    Kok[] kokBul(String giris);

    String[] stringKokBul(String giris);
}
