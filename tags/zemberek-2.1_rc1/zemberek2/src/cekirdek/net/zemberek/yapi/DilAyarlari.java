/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi;

import java.util.Locale;
import java.util.Map;

import net.zemberek.yapi.ek.EkKuralBilgisi;
import net.zemberek.yapi.ek.EkOzelDurumUretici;
import net.zemberek.yapi.ek.EkUretici;

/**
 * Bir dilin gerceklenmesi sirasinda kullanilacak sinif ve cesitli bilgilere erisimi saglar.
 * User: ahmet
 * Date: Sep 20, 2006
 */
public interface DilAyarlari {

    Locale locale();

    Class alfabeSinifi();

    Class ekYoneticiSinifi();

    Class heceleyiciSinifi();

    Class kokOzelDurumBilgisiSinifi();

    Class cozumlemeYardimcisiSinifi();

    String[] duzYaziKokDosyalari();

    EkKuralBilgisi ekKuralBilgisi();

    /**
     * Duz yazi ile belirtilen kok dosyalarinda kokun tipinin hangi kelime ile ifade
     * edilecegi bir Map icerisinde belirtilir.
     * @return kisaAd-tip ikililerini tasiyan Map
     */
    Map<String, KelimeTipi> kokTipiAdlari();

    EkUretici ekUretici(Alfabe alfabe);

    EkOzelDurumUretici ekOzelDurumUretici(Alfabe alfabe);

    Map<KelimeTipi, String> baslangiEkAdlari();

    String ad();
}
