package net.zemberek.az.yapi;

import net.zemberek.yapi.TurkceHarf;
import net.zemberek.yapi.Alfabe;

/**
 * User: ahmet
 * Date: Aug 29, 2005
 */
public class AzericeSesliUretici {

    Alfabe alfabe;

    public AzericeSesliUretici(Alfabe alfabe) {
        this.alfabe = alfabe;
    }

    public TurkceHarf sesliBelirleAE(TurkceHarf sonSesli) {
        if (sonSesli == alfabe.harf(Alfabe.CHAR_ee))
            return alfabe.harf(Alfabe.CHAR_ee);
        if (sonSesli.inceSesliMi())
            return alfabe.harf('e');
        return alfabe.harf('a');
    }

    public TurkceHarf sesliBelirleIU(TurkceHarf sonSesli) {
        if (sonSesli.inceSesliMi() && sonSesli.duzSesliMi())
            return alfabe.harf('i');
        if (!sonSesli.inceSesliMi() && sonSesli.duzSesliMi())
            return alfabe.harf(Alfabe.CHAR_ii);
        if (!sonSesli.inceSesliMi() && sonSesli.yuvarlakSesliMi())
            return alfabe.harf('u');
        if (sonSesli.inceSesliMi() && sonSesli.yuvarlakSesliMi())
            return alfabe.harf('u');
        return Alfabe.HARF_YOK;
    }
}
