/*
 *  ***** BEGIN LICENSE BLOCK *****
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
 *  The Original Code is Zemberek Doðal Dil Ýþleme Kütüphanesi.
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akýn, Mehmet D. Akýn.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  ***** END LICENSE BLOCK *****
 */

/*
 * Created on 19.Haz.2005
 *
 */
package net.zemberek.istatistik;

import net.zemberek.yapi.ek.Ek;

public class EkFrekansBilgisi implements Comparable {
    private int kullanim = 1;
    private double kullanimFrekansi = 0.0d;
    private Ek ek;

    public EkFrekansBilgisi(Ek ek) {
        this.ek = ek;
    }

    public void kullanimArttir() {
        kullanim++;
    }

    public int getKullanim() {
        return kullanim;
    }

    public int compareTo(Object o) {
        EkFrekansBilgisi giris = (EkFrekansBilgisi) o;
        return (giris.kullanim - this.kullanim > 0 ? 1 : -1);
    }

    public Ek getEk() {
        return ek;
    }

    public double getKullanimFrekansi() {
        return kullanimFrekansi;
    }

    public void setKullanimFrekansi(double kullanimFrekansi) {
        this.kullanimFrekansi = kullanimFrekansi;
    }

}


