package net.zemberek.tm.yapi;

import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.TurkceHarf;
import net.zemberek.yapi.SesliUretici;

/**
 * User: ahmet
 * Date: Aug 29, 2005
 */
public class TurkmenceSesliUretici implements SesliUretici {


    private final TurkceHarf HARP_a;
    private final TurkceHarf HARP_aa;
    private final TurkceHarf HARP_e;
    private final TurkceHarf HARP_i;
    private final TurkceHarf HARP_y;
    private final TurkceHarf HARP_u;
    private final TurkceHarf HARP_uu;

    public TurkmenceSesliUretici(Alfabe alfabe) {
        HARP_a = alfabe.harf('a');
        HARP_aa = alfabe.harf(Alfabe.CHAR_aa);
        HARP_e = alfabe.harf('e');
        HARP_i = alfabe.harf('i');
        HARP_y = alfabe.harf('y');
        HARP_u = alfabe.harf('u');
        HARP_uu = alfabe.harf(Alfabe.CHAR_uu);
    }

    public TurkceHarf sesliBelirleIU(HarfDizisi dizi) {
        final TurkceHarf sonSesli = dizi.sonSesli();
        return sesliBelirleIU(sonSesli);
    }

    public TurkceHarf sesliBelirleIU(TurkceHarf sonSesli) {
        if (sonSesli.inceSesliMi() && sonSesli.duzSesliMi())
            return HARP_a;
        if (!sonSesli.inceSesliMi() && sonSesli.duzSesliMi())
            return HARP_y;
        if (!sonSesli.inceSesliMi() && sonSesli.yuvarlakSesliMi())
            return HARP_u;
        if (sonSesli.inceSesliMi() && sonSesli.yuvarlakSesliMi())
            return HARP_uu;
        return Alfabe.HARF_YOK;
    }

    public TurkceHarf sesliBelirleAE(HarfDizisi dizi) {
        return sesliBelirleAE(dizi.sonSesli());
    }

    public TurkceHarf sesliBelirleAE(TurkceHarf sonSesli) {
        if (sonSesli.inceSesliMi())
            return HARP_e;
        return HARP_a;
    }

    public TurkceHarf sesliBelirleAA(HarfDizisi dizi) {
        return sesliBelirleAA(dizi.sonSesli());
    }

    public TurkceHarf sesliBelirleAA(TurkceHarf sonSesli) {
        if (sonSesli.inceSesliMi())
            return HARP_aa;
        return HARP_a;
    }

    public TurkceHarf sesliBelirleII(HarfDizisi dizi) {
        if (dizi.sonSesli().inceSesliMi())
            return HARP_i;
        else
            return HARP_y;
    }

    public TurkceHarf sesliBelirleII(TurkceHarf harf) {
        if (harf.inceSesliMi())
            return HARP_i;
        else
            return HARP_y;
    }

}
