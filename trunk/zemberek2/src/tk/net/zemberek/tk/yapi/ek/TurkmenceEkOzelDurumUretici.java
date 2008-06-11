/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tk.yapi.ek;

import net.zemberek.tk.yapi.TurkmenceSesliUretici;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.ek.EkOzelDurumTipi;
import net.zemberek.yapi.ek.EkOzelDurumu;
import net.zemberek.yapi.ek.TemelEkOzelDurumUretici;

/**
 * User: ahmet
 * Date: Sep 22, 2006
 */
public class TurkmenceEkOzelDurumUretici extends TemelEkOzelDurumUretici {

    public TurkmenceEkOzelDurumUretici(Alfabe alfabe) {
        this.alfabe = alfabe;
    }

    enum TurkmenceEkOzelDurumTipi implements EkOzelDurumTipi {

        ME_ISLIK,
        SON_SESLI_DUSUM,
        KI_YERGORKEZYAN,
        EDILGEN;

        public String ad() {
            return name();
        }
    }

    @Override
    public EkOzelDurumu uret(String ad) {

        EkOzelDurumu oz = super.uret(ad);
        if (oz != null)
            return oz;

        if (!mevcut(TurkmenceEkOzelDurumTipi.values(), ad)) {
            logger.severe("Ozel durum adina karsilik dusen ek ozel durum tipi bulunamadi:" + ad);
            return null;
        }

        switch (TurkmenceEkOzelDurumTipi.valueOf(ad)) {
            case ME_ISLIK:
                return new MeEkOzelDurumu(alfabe, new TurkmenceSesliUretici(alfabe));
            case SON_SESLI_DUSUM:
                return new SonHarfDusumOzelDurumu();
            case KI_YERGORKEZYAN:
                return new KiEkOzelDurumu(alfabe);
            case EDILGEN:
                return new EdilgenOzelDurumu(alfabe);
        }
        return oz;
    }

}
