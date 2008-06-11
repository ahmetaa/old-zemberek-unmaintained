/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi;

import java.util.List;

/**
 * User: ahmet
 * Date: Sep 10, 2005
 */
public interface Heceleyici {

    /**
     * Giris dizisini hecelerine ayirir. Eger hecelenemezse liste boyutu sifir.
     * @param dizi
     * @return heceler bir Strign listesi icerisinde doner. eger hecelenemezse liste
     * boyu sifir.
     */
     List<String> hecele(HarfDizisi dizi);
}
