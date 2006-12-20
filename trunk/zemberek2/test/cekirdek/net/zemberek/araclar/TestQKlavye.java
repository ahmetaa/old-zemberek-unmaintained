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
 *  The Original Code is Zemberek Doğal Dil İşleme Kütüphanesi.
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akın, Mehmet D. Akın.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

/*
 * Created on 30.Tem.2005
 *
 */
package net.zemberek.araclar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class TestQKlavye {

    @Test
    public void testQKlavye(){
        int mesafe = 0;
        int mesafe2 = 0;
        
        mesafe = TusTakimi.trQ().mesafeHesapla('a','a');
        assertEquals(" Aynı karakterin mesafesi sıfır olmalıydı.", 0, mesafe);

        mesafe = TusTakimi.trQ().mesafeHesapla('a','%');
        assertEquals(" Tanımsız karakter -1 döndürmeliydi.", -1, mesafe);

        mesafe = TusTakimi.trQ().mesafeHesapla('a','s');
        mesafe2 = TusTakimi.trQ().mesafeHesapla('a','e');
        System.out.println("a-s: " + mesafe + " a-e: " + mesafe2);
        assertTrue("a'nın s ye mesafesi e'ye olan mesafesinden az olmalıydı.", mesafe < mesafe2);

        mesafe = TusTakimi.trQ().mesafeHesapla('s','q');
        mesafe2 = TusTakimi.trQ().mesafeHesapla('s','e');
        System.out.println("s-q: " + mesafe + " s-e: " + mesafe2);
        assertTrue("s'nin q ya mesafesi ile e'ye olan mesafesi aynı olmalıydı.", mesafe == mesafe2);

        mesafe = TusTakimi.trQ().mesafeHesapla('s','q');
        mesafe2 = TusTakimi.trQ().mesafeHesapla('s','a');
        System.out.println("s-q: " + mesafe + " s-a: " + mesafe2);
        assertTrue("s'nin q ya mesafesi ile a'ya olan mesafesinden fazla olmalıydı.", mesafe > mesafe2);
    }
}
