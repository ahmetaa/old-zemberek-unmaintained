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

package net.zemberek.yapi.obek;

import java.util.Arrays;

public class KelimeObegi implements Kavram {

    private final KelimeObekBileseni[] bilesenler;

    public KelimeObegi(KelimeObekBileseni[] bilesenler) {
        this.bilesenler = bilesenler;
    }

    public int kelimeSayisi() {
        return bilesenler.length;
    }

    public KelimeObekBileseni[] bilesenler() {
        return bilesenler;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KelimeObegi that = (KelimeObegi) o;

        if (bilesenler != null ? !Arrays.deepEquals(bilesenler, that.bilesenler) : that.bilesenler != null)
            return false;

        return true;
    }

    public int hashCode() {
        return (bilesenler != null ? Arrays.deepHashCode(bilesenler) : 0);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < bilesenler.length; i++) {
            b.append(bilesenler[i].kok().icerik());
            if (i < bilesenler.length - 1)
                b.append("|");
        }
        return b.toString();
    }

}
