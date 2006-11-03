package net.zemberek.yapi.kok;

import net.zemberek.yapi.HarfDizisi;

/**
 * Bir harf dizisi uzerinde yapilabilecek islemi ifade eder. Bu arayuz genellikle
 * kok yapisi uzerinde degisiklige nedenn olan ozel durumlarin tanimlanmasinda kullanilir.
 */
public interface HarfDizisiIslemi {

    /**
     * dizi uzerinde degisiklik yapacak metod.
     * @param dizi
     */
    void uygula(HarfDizisi dizi);

}
