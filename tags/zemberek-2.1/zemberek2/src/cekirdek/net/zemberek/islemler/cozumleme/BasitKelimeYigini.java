/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.islemler.cozumleme;

import java.util.LinkedList;

import net.zemberek.yapi.Kelime;


public class BasitKelimeYigini {

    private LinkedList<YiginKelime> yigin = new LinkedList<YiginKelime>();

    public YiginKelime al() {
        return yigin.removeFirst();
    }

    public boolean bosMu() {
        return yigin.isEmpty();
    }

    public void temizle() {
        yigin.clear();
    }

    public void koy(Kelime kelime, int ardisilEkSirasi) {
        yigin.addFirst(new YiginKelime(kelime, ardisilEkSirasi));
    }

    public static final class YiginKelime {

        private final Kelime kelime;
        private final int ekSirasi;

        public YiginKelime(Kelime kel, int index) {
            this.kelime = kel;
            this.ekSirasi = index;
        }

        public Kelime getKelime() {
            return kelime;
        }

        public int getEkSirasi() {
            return ekSirasi;
        }

        public String toString() {
            return " olusan: " + kelime.icerikStr()
                    + " sonEk: " + kelime.sonEk().toString()
                    + " ekSira: " + ekSirasi;
        }
    }
}
