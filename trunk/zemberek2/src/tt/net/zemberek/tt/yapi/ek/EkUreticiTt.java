/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tt.yapi.ek;

import net.zemberek.tt.yapi.TatarcaSesliUretici;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.TurkceHarf;
import net.zemberek.yapi.ek.Ek;
import net.zemberek.yapi.ek.EkUretici;
import net.zemberek.yapi.ek.EkUretimBileseni;

import java.util.List;
import java.util.Set;

public class EkUreticiTt implements EkUretici {

    private TatarcaSesliUretici sesliUretici;


    private final TurkceHarf HARF_m;
    private final TurkceHarf HARF_n;
    private final TurkceHarf HARF_nn;

    public EkUreticiTt(Alfabe alfabe) {
        this.sesliUretici = new TatarcaSesliUretici(alfabe);
        HARF_m = alfabe.harf('m');
        HARF_n = alfabe.harf('n');
        HARF_nn = alfabe.harf(Alfabe.CHAR_TT_n);
    }

    public HarfDizisi cozumlemeIcinEkUret(HarfDizisi ulanacak, HarfDizisi giris, List<EkUretimBileseni> bilesenler) {
        HarfDizisi sonuc = new HarfDizisi(4);
        TurkceHarf sonSesli = ulanacak.sonSesli();
        for (int i = 0; i < bilesenler.size(); i++) {
            EkUretimBileseni ekUretimBileseni = bilesenler.get(i);
            final TurkceHarf harf = ekUretimBileseni.harf;
            final TurkceHarf sonHarf = ulanacak.sonHarf();
            switch ((TatarcaEkUretimKurali)ekUretimBileseni.kural) {
                case HARF:
                    sonuc.ekle(harf);
                    break;
                case KAYNASTIR:
                    if (sonHarf.sesliMi())
                        sonuc.ekle(harf);
                    break;
                case SERTLESTIR:
                    if (sonHarf.sertMi())
                        sonuc.ekle(harf.sertDonusum());
                    else
                        sonuc.ekle(harf);
                    break;
                case N_DONUSUMU:
                    if (sonHarf == HARF_m || sonHarf == HARF_n || sonHarf == HARF_nn)
                        sonuc.ekle(HARF_n);
                    else {
                        if (sonHarf.sertMi() && harf.sertDonusum()!=null)
                            sonuc.ekle(harf.sertDonusum());
                        else
                            sonuc.ekle(harf);
                    } break;
                case SESLI_AA:
                    if (i == 0 && sonHarf.sesliMi())
                        break;
                    else {
                        sonSesli = sesliUretici.sesliBelirleAA(sonSesli);
                        sonuc.ekle(sonSesli);
                    }
                    break;
                case SESLI_EI:
                    if (i == 0 && sonHarf.sesliMi())
                        break;
                    else {
                        sonSesli = sesliUretici.sesliBelirleEI(sonSesli);
                        sonuc.ekle(sonSesli);
                    }
                    break;
            }
        }
        return sonuc;
    }

    public HarfDizisi olusumIcinEkUret(HarfDizisi ulanacak, Ek sonrakiEk, List<EkUretimBileseni> bilesenler) {
        return null;
    }

    public Set<TurkceHarf> olasiBaslangicHarfleri(List<EkUretimBileseni> bilesenler) {
        return null;
    }

    public boolean sesliIleBaslayabilir(List<EkUretimBileseni> bilesenler) {
        for (EkUretimBileseni bilesen : bilesenler) {
            if (bilesen.kural == TatarcaEkUretimKurali.KAYNASTIR)
                continue;
            return bilesen.harf.sesliMi() || bilesen.kural.isSesliUretimKurali();
        }
        return false;
    }
}
