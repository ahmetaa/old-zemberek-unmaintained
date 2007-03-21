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
 *  The Original Code is "Zemberek Dogal Dil Isleme Kutuphanesi"
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akin, Mehmet D. Akin.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberek.tt.yapi;

import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.TurkceHarf;
import net.zemberek.yapi.HarfDizisi;

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
