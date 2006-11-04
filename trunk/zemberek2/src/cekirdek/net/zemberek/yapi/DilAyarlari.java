package net.zemberek.yapi;

import net.zemberek.yapi.ek.EkOzelDurumUretici;
import net.zemberek.yapi.ek.EkUretici;

import java.util.Map;
import java.util.Locale;

/**
 * Bir dilin gerceklenmesi sirasinda kullanilaca sinif ve cesitli bilgilere erisimi saglar.
 * User: ahmet
 * Date: Sep 20, 2006
 */
public interface DilAyarlari {

    Locale locale();

    Class alfabeSinifi();

    Class ekYoneticiSinifi();

    Class heceBulucuSinifi();

    Class kokOzelDurumBilgisiSinifi();

    Class cozumlemeYardimcisiSinifi();

    String[] duzYaziKokDosyalari();

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
