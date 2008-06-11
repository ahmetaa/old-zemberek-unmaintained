/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.bilgi.araclar;

import net.zemberek.yapi.Kok;

import java.io.IOException;
import java.util.List;

public interface KokYazici {

    void yaz(List<Kok> kokler) throws IOException;

}
