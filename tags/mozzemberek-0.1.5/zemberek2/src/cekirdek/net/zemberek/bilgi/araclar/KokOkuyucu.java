/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.bilgi.araclar;

import java.io.IOException;
import java.util.List;

import net.zemberek.yapi.Kok;

public interface KokOkuyucu {

    List<Kok> hepsiniOku() throws IOException;

    Kok oku() throws IOException;

}
