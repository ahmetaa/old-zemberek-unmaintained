package net.zemberek.az.yapi.ek;

import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.ek.EkOzelDurumu;
import net.zemberek.yapi.ek.TemelEkOzelDurumUretici;
import net.zemberek.yapi.ek.EkOzelDurumTipi;


public class AzericeEkOzelDurumUretici extends TemelEkOzelDurumUretici {

    public AzericeEkOzelDurumUretici(Alfabe alfabe) {
        this.alfabe = alfabe;
    }

    enum AzericeEkOzelDurumTipi implements EkOzelDurumTipi {
        //TODO: buraya ilgili ozel durumlar eklenmeli
        TEST_EK_OZEL_DURUMU;

        public String ad() {
            return name();
        }
    }

    public EkOzelDurumu uret(String ad) {

        EkOzelDurumu oz = super.uret(ad);
        if (oz != null)
            return oz;

        if (!mevcut(AzericeEkOzelDurumTipi.values(), ad)) {
            logger.severe("Ozel durum adina karsilik dusen ek ozel durum tipi bulunamadi:" + ad);
            return null;
        }

        switch (AzericeEkOzelDurumTipi.valueOf(ad)) {
            //TODO: buraya ilgili ozel durumlar eklenmeli
            case TEST_EK_OZEL_DURUMU:
                return null;
        }
        return oz;
    }

}
