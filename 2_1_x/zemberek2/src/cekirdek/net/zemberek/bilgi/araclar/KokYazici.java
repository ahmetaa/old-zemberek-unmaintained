/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.bilgi.araclar;

import java.io.IOException;
import java.util.List;

import net.zemberek.yapi.Kok;

public interface KokYazici {

    void yaz(List<Kok> kokler) throws IOException;

}
