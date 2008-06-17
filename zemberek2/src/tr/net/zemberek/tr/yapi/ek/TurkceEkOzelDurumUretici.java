/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tr.yapi.ek;

import java.util.logging.Logger;

import net.zemberek.araclar.Kayitci;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.ek.EkOzelDurumTipi;
import net.zemberek.yapi.ek.EkOzelDurumu;
import net.zemberek.yapi.ek.TemelEkOzelDurumUretici;

/**
 * User: ahmet
 * Date: Sep 16, 2006
 */
public class TurkceEkOzelDurumUretici extends TemelEkOzelDurumUretici {

    private static Logger logger = Kayitci.kayitciUret(TurkceEkOzelDurumUretici.class);

    public TurkceEkOzelDurumUretici(Alfabe alfabe) {
        this.alfabe = alfabe;
    }

    enum TurkceEkOzelDurumTipi implements EkOzelDurumTipi {

        BERABERLIK_IS,
        EDILGEN,
        GENIS_ZAMAN,
        SIMDIKI_ZAMAN,
        SU;

        public String ad() {
            return name();
        }
    }

    @Override
    public EkOzelDurumu uret(String ad) {
        EkOzelDurumu oz = super.uret(ad);
        if (oz != null)
            return oz;

        if (!mevcut(TurkceEkOzelDurumTipi.values(), ad)) {
            logger.severe("Ozel durum adina karsilik dusen ek ozel durum tipi bulunamadi:" + ad);
            return null;
        }

        switch (TurkceEkOzelDurumTipi.valueOf(ad)) {
            case BERABERLIK_IS:
                return new BeraberlikIsOzelDurumu();
            case EDILGEN:
                return new EdilgenOzelDurumu(alfabe);
            case GENIS_ZAMAN:
                return new GenisZamanEkOzelDurumuTr();
            case SIMDIKI_ZAMAN:
                return new SimdikiZamanEkOzelDurumuTr(alfabe);
            case SU:
                return new SuOzelDurumu();

        }
        return oz;
    }
}
