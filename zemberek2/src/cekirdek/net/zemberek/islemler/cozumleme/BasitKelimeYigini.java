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

package net.zemberek.islemler.cozumleme;

import net.zemberek.yapi.Kelime;

import java.util.LinkedList;


public class BasitKelimeYigini {

    private LinkedList<YiginKelime> yigin = new LinkedList<YiginKelime>();

    public YiginKelime al() {
        return yigin.removeFirst();
    }

    public boolean bosMu() {
        return yigin.isEmpty();
    }

    public void temizle() {
        yigin.clear();
    }

    public void koy(Kelime kelime, int ardisilEkSirasi) {
        yigin.addFirst(new YiginKelime(kelime, ardisilEkSirasi));
    }

    public static final class YiginKelime {

        private final Kelime kelime;
        private final int ekSirasi;

        public YiginKelime(Kelime kel, int index) {
            this.kelime = kel;
            this.ekSirasi = index;
        }

        public Kelime getKelime() {
            return kelime;
        }

        public int getEkSirasi() {
            return ekSirasi;
        }

        public String toString() {
            return " olusan: " + kelime.icerikStr()
                    + " sonEk: " + kelime.sonEk().toString()
                    + " ekSira: " + ekSirasi;
        }
    }
}
