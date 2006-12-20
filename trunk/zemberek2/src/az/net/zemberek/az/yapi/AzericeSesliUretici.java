/*
 *  ***** BEGIN LICENSE BLOCK *****
 *
 *  Version: MPL 1.1
 *
 *  The contents of this file are subject to the Mozilla Public License Version
 *  1.1 (the "License"); you may not use this file except in compliance with
 *  the License. You may obtain a copy of the License at
 *  http://www.mozilla.org/MPL/
 *
 *  Software distributed under the License is distributed on an "AS IS" basis,
 *  WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 *  for the specific language governing rights and limitations under the
 *  License.
 *
 *  The Original Code is Zemberek Do?al Dil ??leme Kütüphanesi.
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Ak?n, Mehmet D. Ak?n.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

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
        return Alfabe.TANIMSIZ_HARF;
    }
}
