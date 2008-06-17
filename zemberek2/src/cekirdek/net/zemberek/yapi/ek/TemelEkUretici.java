/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi.ek;

import static net.zemberek.yapi.ek.TemelEkUretimKurali.KAYNASTIR;

import java.util.List;
import java.util.Set;

import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.TurkceHarf;


public abstract class TemelEkUretici implements EkUretici {

    public boolean sesliIleBaslayabilir(List<EkUretimBileseni> bilesenler) {
        for (EkUretimBileseni bilesen : bilesenler) {
            if (bilesen.kural == KAYNASTIR) continue;
            return bilesen.harf.sesliMi() || bilesen.kural.isSesliUretimKurali();
        }
        return false;
    }

    public HarfDizisi olusumIcinEkUret(HarfDizisi ulanacak, Ek sonrakiEk, List<EkUretimBileseni> bilesenler) {
        //TODO: gecici olarak bu sekilde
        return cozumlemeIcinEkUret(ulanacak, null, bilesenler);
    }

    public Set<TurkceHarf> olasiBaslangicHarfleri(List<EkUretimBileseni> bilesenler) {
        return null;
    }

}
