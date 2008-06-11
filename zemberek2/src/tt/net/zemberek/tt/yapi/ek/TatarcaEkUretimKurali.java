/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tt.yapi.ek;

import net.zemberek.yapi.ek.EkUretimKurali;
import net.zemberek.yapi.ek.EkKuralBilgisi;

import java.util.*;

public enum TatarcaEkUretimKurali implements EkUretimKurali {

    SESLI_EI(true),
    SESLI_AA(true),
    SERTLESTIR(false),
    KAYNASTIR(false),
    N_DONUSUMU(false),
    HARF(false);

    private final boolean sesliUretimKurali;

    TatarcaEkUretimKurali(boolean sesliUretimKurali) {
        this.sesliUretimKurali = sesliUretimKurali;
    }

    public boolean isSesliUretimKurali() {
        return sesliUretimKurali;
    }


    public static class KarakterBilgisi implements EkKuralBilgisi {

        public Set<Character> sesliKuralKarakterleri() {
            return new HashSet<Character>(Arrays.asList('A', 'E'));
        }

        public Set<Character> harfKuralKarakterleri() {
            return new HashSet<Character>(Arrays.asList('+', '>', '@'));
        }

        public Map<Character, EkUretimKurali> karakterKuralTablosu() {
            final Map<Character, EkUretimKurali> kuralTablosu = new HashMap();
            kuralTablosu.put('E', SESLI_EI);
            kuralTablosu.put('A', SESLI_AA);
            kuralTablosu.put('>', SERTLESTIR);
            kuralTablosu.put('+', KAYNASTIR);
            kuralTablosu.put('@', N_DONUSUMU);
            return kuralTablosu;
        }

        public EkUretimKurali harfEklemeKurali() {
            return HARF;
        }
    }

}
