package net.zemberek.yapi;

import net.zemberek.yapi.ek.EkOzelDurumUretici;
import net.zemberek.yapi.ek.EkUretici;

import java.util.Locale;
import java.util.Map;

/**
 * Bir dilin gerceklenmesi sirasinda kullanilaca sinif ve cesitli bilgilere erisimi saglar.
 * User: ahmet
 * Date: Sep 20, 2006
 */
public interface DilAyarlari {

    Locale locale();

    String[] duzYaziKokDosyalari();

    /**
     * Duz yazi ile belirtilen kok dosyalarinda kokun tipinin hangi kelime ile ifade
     * edilecegi bir Map icerisinde belirtilir.
     *
     * @return kisaAd-tip ikililerini tasiyan Map
     */
    Map<String, KelimeTipi> kelimeTipiAdlari();

    EkUretici ekUretici(Alfabe alfabe);

    EkOzelDurumUretici ekOzelDurumUretici(Alfabe alfabe);

    Map<KelimeTipi, String> baslangiEkAdlari();

    String ad();
}
