package net.zemberek.tr.yapi;

import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.HeceBulucu;
import net.zemberek.yapi.TurkceHarf;

import java.util.HashSet;
import java.util.Set;

/**
 * User: ahmet
 * Date: Sep 10, 2005
 */
public class TurkceHeceBulucu implements HeceBulucu {

    private Set<TurkceHarf> harfSet = new HashSet();

    public TurkceHeceBulucu(Alfabe alfabe) {
        // bu dizi bazi ozel hecelerin hesaplanmasinda kullanilir.
        // kalp, fark, ebeveyn, kask, cenk, rapt gibi.
        char[] ikinciKarakterler = {'l', 'r', 'y', 'p', 'n', 's'};
        for (char c : ikinciKarakterler) {
            harfSet.add(alfabe.harf(c));
        }
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
    public int sonHeceHarfSayisi(HarfDizisi kelime) {

        int sira = kelime.length() - 1;
        TurkceHarf harf = kelime.harf(sira);
        TurkceHarf oncekiHarf = kelime.harf(sira - 1);

        if (kelime.length() == 0)
            return -1;

        if (harf.sesliMi()) {
            //kelime sadece sesli.
            if (kelime.length() == 1)
                return 1;
            //onceki harf sesli kelime="saa" ise son ek "a"
            if (oncekiHarf.sesliMi())
                return 1;
            //onceki harf sessiz ise ve kelime sadece 2 harf ise hece tum kelime. "ya"
            if (kelime.length() == 2)
                return 2;

            TurkceHarf ikiOncekiHarf = kelime.harf(sira - 2);
            if (!ikiOncekiHarf.sesliMi() && kelime.length() == 3) {
                //harf dizilim denetimi gerekebilir. (tr,kr,st)
                return 3;
            }
            return 2;
        } else {

            // tek sessiz ile hece olmaz.
            if (kelime.length() == 1)
                return -1;

            TurkceHarf ikiOncekiHarf = kelime.harf(sira - 2);
            if (oncekiHarf.sesliMi()) {

                //kelime iki harfli (el, al) ya da iki onceki harf sesli (saat)
                if (kelime.length() == 2 || ikiOncekiHarf.sesliMi())
                    return 2;

                TurkceHarf ucOncekiHarf = kelime.harf(sira - 3);
                // kelime uc harfli (kal, sel) ya da uc onceki harf sesli (kanat),
                if (kelime.length() == 3 || ucOncekiHarf.sesliMi())
                    return 3;

                //kelime dort harfli ise yukaridaki kurallari gecmesi nedeniyle hecelenemez.
                // ornegin tren kelimesini hecelenemez sayiyoruz.
                if (kelime.length() == 4)
                    return -1;

                TurkceHarf dortOncekiHarf = kelime.harf(sira - 4);
                if (!dortOncekiHarf.sesliMi())
                    return 3;
                return 3;
            } else {
                // burada "tu[n]c", "ka[l]p", "ebeve[y]n" turu kelimelerdeki son hece denetimi yapiliyor
                // sadece bazi sessizler icin bu tur hece olusumuna izin veriliyor.
                if (harfSet.contains(oncekiHarf)) {

                    if (kelime.length() == 2 || !ikiOncekiHarf.sesliMi())
                        return -1;
                    TurkceHarf ucOncekiHarf = kelime.harf(sira - 3);
                    if (kelime.length() > 3 && !ucOncekiHarf.sesliMi())
                        return 4;
                    return 3;
                }
            }
        }
        return -1;
    }
}
