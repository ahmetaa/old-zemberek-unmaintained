/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi.kok;

import net.zemberek.yapi.HarfDizisi;

/**
 * Bir harf dizisi uzerinde yapilabilecek islemi ifade eder. Bu arayuz genellikle
 * kok yapisi uzerinde degisiklige neden olan ozel durumlarin tanimlanmasinda kullanilir.
 */
public interface HarfDizisiIslemi {

    /**
     * dizi uzerinde degisiklik yapacak metod.
     * @param dizi uzerinde islem yapilacak olan dizi. visitor deseni.
     */
    void uygula(HarfDizisi dizi);

}
