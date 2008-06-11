/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tt.yapi.kok;

import net.zemberek.yapi.kok.KokOzelDurumTipi;

/**
 * Created by IntelliJ IDEA.
 * User: ahmet
 * Date: Mar 19, 2007
 * Time: 10:19:26 PM
 * To change this template use File | Settings | File Templates.
 */
public enum TatarcaKokOzelDurumTipleri implements KokOzelDurumTipi {

    SESSIZ_YUMUSAMASI("YUM");

    private String kisaAd;
    private String[] ekAdlari = new String[0];

    TatarcaKokOzelDurumTipleri(String kisaAd, String... ekAdlari) {
        this.kisaAd = kisaAd;
        if (this.ekAdlari != null) {
            this.ekAdlari = ekAdlari;
        }
    }

    public String ad() {
        return this.name();
    }

    public String kisaAd() {
        return kisaAd;
    }

    public int indeks() {
        return ordinal();
    }

    public String[] ekAdlari() {
        return ekAdlari;
    }
}
