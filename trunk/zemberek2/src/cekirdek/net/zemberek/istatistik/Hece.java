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
 * Created on 11.Ara.2004
 *
 */
package net.zemberek.istatistik;

/**
 * @author MDA
 */
public class Hece implements Comparable {
    private String hece = null;
    private long kullanim = 1;

    public Hece(String hece) {
        this.hece = hece;
    }

    public void arttir() {
        kullanim++;
    }

    public int compareTo(Object o) {
        return (int) (((Hece) o).kullanim - this.kullanim);
    }

    /**
     * @return Returns the kullanim.
     */
    public long getKullanim() {
        return kullanim;
    }

    /**
     * @return Returns the sonHeceHarfSayisi.
     */
    public String getHece() {
        return hece;
    }

    public String toString() {
        return hece + ":" + kullanim;
    }
}
