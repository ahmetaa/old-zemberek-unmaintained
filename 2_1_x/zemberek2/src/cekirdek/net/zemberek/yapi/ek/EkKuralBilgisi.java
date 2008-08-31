/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi.ek;

import java.util.Map;
import java.util.Set;

public interface EkKuralBilgisi {
    Set<Character> sesliKuralKarakterleri();

    Set<Character> harfKuralKarakterleri();

    Map<Character, EkUretimKurali> karakterKuralTablosu();

    EkUretimKurali harfEklemeKurali();
}
