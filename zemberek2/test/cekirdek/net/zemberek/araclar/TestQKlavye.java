/*
 * Created on 30.Tem.2005
 *
 */
package net.zemberek.araclar;

import junit.framework.TestCase;

public class TestQKlavye extends TestCase {
    public void testQKlavye(){
        int mesafe = 0;
        int mesafe2 = 0;
        mesafe = QTusTakimi.mesafeHesapla('a','a');
        assertEquals(" Aynı karakterin mesafesi sıfır olmalıydı.", 0, mesafe);

        mesafe = QTusTakimi.mesafeHesapla('a','%');
        assertEquals(" Tanımsız karakter -1 döndürmeliydi.", -1, mesafe);

        mesafe = QTusTakimi.mesafeHesapla('a','s');
        mesafe2 = QTusTakimi.mesafeHesapla('a','e');
        System.out.println("a-s: " + mesafe + " a-e: " + mesafe2);
        assertTrue("a'nın s ye mesafesi e'ye olan mesafesinden az olmalıydı.", mesafe < mesafe2);

        mesafe = QTusTakimi.mesafeHesapla('s','q');
        mesafe2 = QTusTakimi.mesafeHesapla('s','e');
        System.out.println("s-q: " + mesafe + " s-e: " + mesafe2);
        assertTrue("s'nın q ye mesafesi ile e'ye olan mesafesi aynı olmalıydı.", mesafe == mesafe2);

        mesafe = QTusTakimi.mesafeHesapla('s','q');
        mesafe2 = QTusTakimi.mesafeHesapla('s','a');
        System.out.println("s-q: " + mesafe + " s-a: " + mesafe2);
        assertTrue("s'nın q ye mesafesi ile a'ye olan mesafesinden fazla olmalıydı.", mesafe > mesafe2);
    }
}
