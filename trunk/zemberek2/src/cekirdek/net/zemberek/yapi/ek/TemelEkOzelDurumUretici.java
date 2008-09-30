/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi.ek;

import java.util.logging.Logger;

import net.zemberek.araclar.Kayitci;
import net.zemberek.yapi.Alfabe;

/**
 * User: ahmet
 * Date: Sep 16, 2006
 */
public abstract class TemelEkOzelDurumUretici implements EkOzelDurumUretici {

    protected static Logger logger = Kayitci.kayitciUret(TemelEkOzelDurumUretici.class);

    protected Alfabe alfabe;

    public enum TemelEkOzelDurumuTipi implements EkOzelDurumTipi {
        OLDURGAN,
        ON_EK,
        ZAMAN_KI;

        public String ad() {
            return name();
        }
    }

    public EkOzelDurumu uret(String ad) {
        if (!mevcut(TemelEkOzelDurumuTipi.values(), ad))
            return null;
        else
            switch (TemelEkOzelDurumuTipi.valueOf(ad)) {
                case OLDURGAN:
                    return new OldurganEkOzelDurumu(alfabe);
                case ON_EK:
                    return new OnEkOzelDurumu();
                case ZAMAN_KI:
                    return new ZamanKiOzelDurumu();
                default:
                    return null;
            }
    }

    /**
     * efektif olmayan bir tip denetimi.
     *
     * @param tipler
     * @param ad
     * @return eger kisaAd ile belirtilen tip var ise true.
     */
    protected boolean mevcut(EkOzelDurumTipi[] tipler, String ad) {
        for (EkOzelDurumTipi tip : tipler) {
            if (tip.ad().equals(ad))
                return true;
        }
        return false;
    }


}
