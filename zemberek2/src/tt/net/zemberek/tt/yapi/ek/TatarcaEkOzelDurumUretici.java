/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tt.yapi.ek;

import net.zemberek.yapi.ek.TemelEkOzelDurumUretici;
import net.zemberek.yapi.ek.EkOzelDurumTipi;
import net.zemberek.yapi.ek.EkOzelDurumu;
import net.zemberek.yapi.Alfabe;

public class TatarcaEkOzelDurumUretici extends TemelEkOzelDurumUretici {

    public TatarcaEkOzelDurumUretici(Alfabe alfabe) {
        this.alfabe = alfabe;
    }

    enum TatarcaEkOzelDurumTipi implements EkOzelDurumTipi {
        //TODO: buraya ilgili ozel durumlar eklenmeli
        TEST_EK_OZEL_DURUMU;

        public String ad() {
            return name();
        }
    }

    @Override
    public EkOzelDurumu uret(String ad) {

        EkOzelDurumu oz = super.uret(ad);
        if (oz != null)
            return oz;

        if (!mevcut(TatarcaEkOzelDurumTipi.values(), ad)) {
            logger.severe("Ozel durum adina karsilik dusen ek ozel durum tipi bulunamadi:" + ad);
            return null;
        }

        switch (TatarcaEkOzelDurumTipi.valueOf(ad)) {
            //TODO: buraya ilgili ozel durumlar eklenmeli
            case TEST_EK_OZEL_DURUMU:
                return null;
        }
        return oz;
    }
}
