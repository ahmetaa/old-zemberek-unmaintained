/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tr.yapi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.Heceleyici;

/**
 * Mert'in algoritmasi.
 * Date: Sep 10, 2005
 */
public class DenemeTurkceHeceleyici implements Heceleyici {

    /**
     * Henuz hecelenememe durumu cozumsuz..
     * @param kelime
     * @return
     */

    public List<String> hecele(HarfDizisi kelime) {
        ArrayList<String> heceler = new ArrayList<String>();
        final int sesliSayisi = kelime.sesliSayisi();
        Set<Integer> heceyapicilar = new HashSet<Integer>(sesliSayisi);

        final int boy = kelime.length();
        for (int i = 0; i < boy - 1; i++) {
            if ((!kelime.harf(i).sesliMi() && kelime.harf(i + 1).sesliMi())
                    || (kelime.harf(i).sesliMi() && i > 0 && kelime.harf(i - 1).sesliMi())) {
                heceyapicilar.add(i);
            }
        }

        int sonNokta = boy;
        for (int i = boy - 1; i > -1; i--) {
            if (heceyapicilar.contains(i)) {
                heceyapicilar.remove(i);
                if (heceyapicilar.size() == 0 && kelime.araDizi(0, boy - (boy - i)).sesliSayisi() == 0) {
                    heceler.add(kelime.subSequence(0, boy - (boy - sonNokta)).toString());
                    sonNokta = 0;
                } else {
                    heceler.add(kelime.subSequence(i, boy - (boy - sonNokta)).toString());
                    sonNokta = i;
                }
            }
        }
        if (sonNokta != 0) {
            heceler.add(kelime.subSequence(0, boy - (boy - sonNokta)).toString());
        }
        Collections.reverse(heceler);
        return heceler;
    }


}
