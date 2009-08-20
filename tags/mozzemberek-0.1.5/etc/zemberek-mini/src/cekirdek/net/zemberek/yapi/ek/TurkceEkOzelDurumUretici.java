package net.zemberek.yapi.ek;

import net.zemberek.yapi.Alfabe;

/**
 * User: ahmet
 * Date: Sep 16, 2006
 */
public class TurkceEkOzelDurumUretici implements EkOzelDurumUretici {

    protected Alfabe alfabe;


    public TurkceEkOzelDurumUretici(Alfabe alfabe) {
        this.alfabe = alfabe;
    }

    public enum TurkceEkOzelDurumTipi {

        SON_HARF_YUMUSAMA,
        OLDURGAN,
        ON_EK,
        BERABERLIK_IS,
        EDILGEN,
        GENIS_ZAMAN,
        SIMDIKI_ZAMAN,
        ZAMAN_KI;

        public String ad() {
            return name();
        }
    }

    public EkOzelDurumu uret(String ad) {
        if (!mevcut(TurkceEkOzelDurumTipi.values(), ad))
            return null;
        else
            switch (TurkceEkOzelDurumTipi.valueOf(ad)) {
                case SON_HARF_YUMUSAMA:
                    return new SonHarfYumusamaOzelDurumu();
                case OLDURGAN:
                    return new OldurganEkOzelDurumu(alfabe);
                case ON_EK:
                    return new OnEkOzelDurumu();
                case ZAMAN_KI:
                    return new ZamanKiOzelDurumu();
                case BERABERLIK_IS:
                    return new BeraberlikIsOzelDurumu();
                case EDILGEN:
                    return new EdilgenOzelDurumu(alfabe);
                case GENIS_ZAMAN:
                    return new GenisZamanEkOzelDurumuTr();
                case SIMDIKI_ZAMAN:
                    return new SimdikiZamanEkOzelDurumuTr(alfabe);
                default:
                    System.out.println(ad + " ek ozle durumu bulunamiyor!!");
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
    protected boolean mevcut(TurkceEkOzelDurumTipi[] tipler, String ad) {
        for (TurkceEkOzelDurumTipi tip : tipler) {
            if (tip.ad().equals(ad))
                return true;
        }
        return false;
    }


}
