/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tt.yapi;

import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.TurkceHarf;

public class TatarcaSesliUretici {

    private final TurkceHarf HARF_a;
    private final TurkceHarf HARF_aa;
    private final TurkceHarf HARF_e;
    private final TurkceHarf HARF_ii;


    public TatarcaSesliUretici(Alfabe alfabe) {
        HARF_a = alfabe.harf('a');
        HARF_aa = alfabe.harf(Alfabe.CHAR_aa);
        HARF_e = alfabe.harf('e');
        HARF_ii = alfabe.harf(Alfabe.CHAR_ii);
    }

    public TurkceHarf sesliBelirleEI(HarfDizisi dizi) {
        final TurkceHarf sonSesli = dizi.sonSesli();
        return sesliBelirleEI(sonSesli);
    }

    public TurkceHarf sesliBelirleEI(TurkceHarf sonSesli) {
        if (sonSesli.inceSesliMi())
            return HARF_e;
        else
            return HARF_ii;
    }

    public TurkceHarf sesliBelirleAA(HarfDizisi dizi) {
        return sesliBelirleAA(dizi.sonSesli());
    }

    public TurkceHarf sesliBelirleAA(TurkceHarf sonSesli) {
        if (sonSesli.inceSesliMi())
            return HARF_aa;
        return HARF_a;
    }

}
