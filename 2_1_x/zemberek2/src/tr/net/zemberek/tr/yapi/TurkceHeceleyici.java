/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tr.yapi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Heceleyici;
import net.zemberek.yapi.TurkceHarf;

public class TurkceHeceleyici implements Heceleyici {

    public List<String> hecele(HarfDizisi kelime) {
        List<String> list = new ArrayList<String>();
        while (kelime.length() > 0) {
            int index = sonHeceHarfSayisi(kelime);
            if (index < 0) {
                return Collections.emptyList();
            }
            int basla = kelime.length() - index;
            list.add(kelime.toString(basla));
            kelime.kirp(basla);
        }
        Collections.reverse(list);
        return list;
    }

    /**
     * Giren harf dizisinin sonunda mantikli olarak yer alan hecenin harf
     * sayisini dondurur.
     * Sistem, -trak ve benzeri harf dizilimine sahip kelimeleri hecelemiyor.
     *
     * @param kelime: turkce harf dizisi.
     * @return int, 1,2,3 ya da 4 donerse giris dizisinin dizinin sondan o
     *         kadarharfi heceyi temsil eder -1 donerse hecenin bulunamadigi
     *         anlamina gelir. sistem yabanci harf ya da isaretlerin oldugu ya
     *         da kural disi kelimeleri heceleyemez. (ornegin, three, what vs.)
     *         TODO: sistem su anda basta bulunan iki harf sessiz oldugu
     *         durumlari kabul etmekte ama buna kisitlama getirilmesi iyi olur.
     *         sadece "tr", "st", "kr" gibi girislere izin verilmeli
     */
    private int sonHeceHarfSayisi(HarfDizisi kelime) {

        final int boy = kelime.length();
        TurkceHarf harf = kelime.harf(boy - 1);
        TurkceHarf oncekiHarf = kelime.harf(boy - 2);

        if (boy == 0)
            return -1;

        if (harf.sesliMi()) {
            //kelime sadece sesli.
            if (boy == 1)
                return 1;
            //onceki harf sesli kelime="saa" ise son ek "a"
            if (oncekiHarf.sesliMi())
                return 1;
            //onceki harf sessiz ise ve kelime sadece 2 harf ise hece tum kelime. "ya"
            if (boy == 2)
                return 2;

            TurkceHarf ikiOncekiHarf = kelime.harf(boy - 3);

            //ste-tos-kop -> ste
            if (!ikiOncekiHarf.sesliMi() && boy == 3) {
                return 3;
            }
            return 2;
        } else {

            // tek sessiz ile hece olmaz.
            if (boy == 1)
                return -1;

            TurkceHarf ikiOncekiHarf = kelime.harf(boy - 3);
            if (oncekiHarf.sesliMi()) {

                //kelime iki harfli (el, al) ya da iki onceki harf sesli (saat)
                if (boy == 2 || ikiOncekiHarf.sesliMi())
                    return 2;

                TurkceHarf ucOncekiHarf = kelime.harf(boy - 4);
                // kelime uc harfli (kal, sel) ya da uc onceki harf sesli (kanat),
                if (boy == 3 || ucOncekiHarf.sesliMi())
                    return 3;

                //kelime dort harfli ise yukaridaki kurallari gecmesi nedeniyle hecelenemez sayiyoruz.
                // tren, strateji, krank, angstrom gibi kelimeler henuz hecelenmiyor.
                if (boy == 4)
                    return -1;

                TurkceHarf dortOncekiHarf = kelime.harf(boy - 5);
                if (!dortOncekiHarf.sesliMi())
                    return 3;
                return 3;

            } else {

                if (boy == 2 || !ikiOncekiHarf.sesliMi())
                    return -1;
                TurkceHarf ucOncekiHarf = kelime.harf(boy - 4);
                if (boy > 3 && !ucOncekiHarf.sesliMi())
                    return 4;
                return 3;
            }

        }
    }

}
