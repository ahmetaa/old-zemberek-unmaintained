/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi.ek;

/** *
 * User: ahmet
 * Date: Sep 16, 2006
 */
public interface EkOzelDurumUretici {

    /**
     * veirlen adli ozel durumu uretir.
     * @param ad
     * @return bleirtile ada sahip ek ozel durumu.
     */
    EkOzelDurumu uret(String ad);
}
