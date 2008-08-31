/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.islemler.cozumleme;

import net.zemberek.yapi.Kelime;

/**
 */
public interface KelimeCozumleyici {

    public Kelime[] cozumle(String strGiris, CozumlemeSeviyesi seviye);

    public boolean cozumlenebilir(String giris);

}
