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
