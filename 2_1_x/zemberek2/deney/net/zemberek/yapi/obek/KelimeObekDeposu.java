package net.zemberek.yapi.obek;

import java.util.List;

import net.zemberek.yapi.Kok;

/**
 * Farkli Kelime obek gerceklemeleri icin ortak arayuz.
 */
public interface KelimeObekDeposu {

    /**
     * depoya kelime obegi ekler.
     * @param obek
     * @return
     */
    KelimeObekDeposu ekle(KelimeObegi obek);

    /**
     * depoya bir ya da birden fazla kelime obegi ekler.
     * @param obekler
     * @return
     */
    KelimeObekDeposu ekle(KelimeObegi... obekler);

    /**
     * depodan istenen koklerin gectigi kelime obeklerinin listesini dondurur.
     * @param kokler
     * @return
     */
    List<KelimeObegi> obekAra(Kok... kokler);

    /**
     * bir kelime obeginin depoda olup olmadigini dondurur
     * @param obek
     * @return
     */
    boolean var(KelimeObegi obek);

}
