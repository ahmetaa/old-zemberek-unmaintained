package net.zemberek.az.yapi;

import net.zemberek.yapi.TurkceHarf;
import net.zemberek.yapi.Alfabe;

/**
 * User: ahmet
 * Date: Aug 29, 2005
 */
public class AzericeSesliUretici {

    private final TurkceHarf HARF_a,HARF_e,HARF_ee,HARF_i,HARF_ii,HARF_u,HARF_uu;

    public AzericeSesliUretici(Alfabe alfabe) {
        HARF_a = alfabe.harf('a');
        HARF_e = alfabe.harf('e');
        HARF_ee = alfabe.harf(Alfabe.CHAR_ee);
        HARF_i = alfabe.harf('i');
        HARF_ii = alfabe.harf(Alfabe.CHAR_ii);
        HARF_u = alfabe.harf('u');
        HARF_uu = alfabe.harf(Alfabe.CHAR_uu);
    }

    public TurkceHarf sesliBelirleAE(TurkceHarf sonSesli) {
        if (sonSesli == HARF_ee)
            return HARF_ee;
        if (sonSesli.inceSesliMi())
            return HARF_e;
        return HARF_a;
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
        return Alfabe.HARF_YOK;
    }
}
