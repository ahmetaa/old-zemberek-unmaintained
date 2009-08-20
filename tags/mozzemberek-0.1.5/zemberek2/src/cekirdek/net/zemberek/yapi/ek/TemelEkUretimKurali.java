/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi.ek;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Turk dilleri icin cesitli uretim kurallarini belirler. Bazi kurallar sadece belli dillerde
 * kullanilir.
 */
public enum TemelEkUretimKurali implements EkUretimKurali {

    SESLI_AE(true),
    SESLI_AA(true),
    SESLI_IU(true),
    SESSIZ_Y(false),
    SERTLESTIR(false),
    KAYNASTIR(false),
    HARF(false),
    YUMUSAT(false);

    final boolean sesliUretimKurali;


    TemelEkUretimKurali(boolean sesliUretimKurali) {
        this.sesliUretimKurali = sesliUretimKurali;
    }

    public boolean isSesliUretimKurali() {
        return sesliUretimKurali;
    }

    //TODO: asagidaki kodlar efektif degil.
    public static class TemelKuralBilgisi implements EkKuralBilgisi {

        public Set<Character> sesliKuralKarakterleri() {
            return  new HashSet<Character>(Arrays.asList('A', 'I', 'E', 'Y'));
        }

        public Set<Character> harfKuralKarakterleri() {
            return  new HashSet<Character>(Arrays.asList('+', '>', '~'));
        }

        public Map<Character, EkUretimKurali> karakterKuralTablosu() {
            final Map<Character, EkUretimKurali> kuralTablosu = new HashMap<Character, EkUretimKurali>();
            kuralTablosu.put('A', SESLI_AE);
            kuralTablosu.put('I', SESLI_IU);
            kuralTablosu.put('E', SESLI_AA);
            kuralTablosu.put('Y', SESSIZ_Y);
            kuralTablosu.put('+', KAYNASTIR);
            kuralTablosu.put('>', SERTLESTIR);
            kuralTablosu.put('~', YUMUSAT);
            return kuralTablosu;

        }

        public EkUretimKurali harfEklemeKurali() {
            return HARF;
        }
    }

}
