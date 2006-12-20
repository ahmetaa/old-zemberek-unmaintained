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
 * Created on 11.Tem.2004
 */
package net.zemberek.istatistik;


class KelimeZinciri implements Comparable {
    int kullanim = 1;
    String ikili = null;

    public KelimeZinciri(String ikili) {
        this.ikili = ikili;
    }

    public void hit() {
        kullanim++;
    }

    public int getKullanim() {
        return this.kullanim;
    }
    
    public String toString(){
        return ikili + " (" + kullanim + ")";
    }

    public int compareTo(Object o) {
        KelimeZinciri gelen = (KelimeZinciri) o;
        return gelen.kullanim - this.kullanim;
    }
}