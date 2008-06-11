/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tr.yapi;

import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.SesliUretici;
import net.zemberek.yapi.TurkceHarf;

/**
 * User: ahmet
 * Date: Aug 29, 2005
 */
public class TurkceSesliUretici implements SesliUretici {

    public final TurkceHarf HARF_a;
    public final TurkceHarf HARF_e;
    public final TurkceHarf HARF_i;
    public final TurkceHarf HARF_ii;
    public final TurkceHarf HARF_u;
    public final TurkceHarf HARF_uu;

    public TurkceSesliUretici(Alfabe alfabe) {
        HARF_a = alfabe.harf('a');
        HARF_e = alfabe.harf('e');
        HARF_i = alfabe.harf('i');
        HARF_ii = alfabe.harf(Alfabe.CHAR_ii);
        HARF_u = alfabe.harf('u');
        HARF_uu = alfabe.harf(Alfabe.CHAR_uu);
    }

    public TurkceHarf sesliBelirleIU(HarfDizisi dizi) {
        final TurkceHarf sonSesli = dizi.sonSesli();
        return sesliBelirleIU(sonSesli);
    }

    public TurkceHarf sesliBelirleIU(TurkceHarf sonSesli) {
        if (sonSesli.inceSesliMi() && sonSesli.duzSesliMi())
            return HARF_i;
        if (!sonSesli.inceSesliMi() && sonSesli.duzSesliMi())
            return HARF_ii;
        if (!sonSesli.inceSesliMi() && sonSesli.yuvarlakSesliMi())
            return HARF_u;
        if (sonSesli.inceSesliMi() && sonSesli.yuvarlakSesliMi())
            return HARF_uu;
        return Alfabe.TANIMSIZ_HARF;
    }

    public TurkceHarf sesliBelirleAE(HarfDizisi dizi) {
        return sesliBelirleAE(dizi.sonSesli());
    }

    public final TurkceHarf sesliBelirleAE(TurkceHarf sonSesli) {
        if (sonSesli.inceSesliMi())
            return HARF_e;
        else
            return HARF_a;
    }    
}
