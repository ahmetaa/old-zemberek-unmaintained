package net.zemberek.az.yapi.kok;

import net.zemberek.yapi.kok.KokOzelDurumlari;
import net.zemberek.yapi.kok.KokOzelDurumu;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.KelimeTipi;

/**
 * User: ahmet
 * Date: Jun 18, 2006
 */
public class AzericeKokOzelDurumlari implements KokOzelDurumlari {

    public KokOzelDurumu ozelDurum(String ozelDurumAdi) {
        return null;
    }

    public KokOzelDurumu ozelDurum(int indeks) {
        return null;
    }

    public String[] ozelDurumUygula(Kok kok) {
        return new String[0];
    }

    public void ozelDurumBelirle(Kok kok) {
    }

    public void duzyaziOzelDurumOku(Kok kok, String okunanIcerik, String[] parcalar) {
    }

    public void kokIcerikBelirle(Kok kok, KelimeTipi tip, String icerik) {
    }

    public static KokOzelDurumlari ref() {
        return null;
    }
}
