/*
 * Created on 26.Ara.2004
 *
 */
package net.zemberek.istatistik;

import net.zemberek.yapi.Kok;

/**
 * @author MDA
 */
public class KokCebi {

    private Kok[] cep = null;

    public KokCebi(int cepBoyu) {
        cep = new Kok[cepBoyu];
    }

    public Kok getKok(int indeks) {
        return cep[indeks];
    }

    public void ekle(Kok kok, int indeks) {
        cep[indeks] = kok;
        kok.setIndeks(indeks);
    }

}
