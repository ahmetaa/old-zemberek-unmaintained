package net.zemberek.islemler;

import net.zemberek.TemelTest;
import net.zemberek.erisim.Zemberek;
import net.zemberek.istatistik.BasitKokIstatistikBilgisi;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.Kok;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: ahmet
 * Date: Dec 10, 2005
 */
public class TestKelimeKokFrekansKiyaslayici extends TemelTest {
    Zemberek zembrek = new Zemberek(new TurkiyeTurkcesi());
    public void testKiyaslayici()
    {
        Kok kok1 = new Kok("alo");
        kok1.setFrekans(10);
        Kelime k1 = new Kelime(kok1, alfabe);

        Kok kok2 = new Kok("merhaba");
        kok2.setFrekans(20);
        Kelime k2 = new Kelime(kok2, alfabe);

        List<Kelime> kel = new ArrayList();
        kel.add(k1);
        kel.add(k2);
        Collections.sort(kel, new KelimeKokFrekansKiyaslayici());

        KelimeKokFrekansKiyaslayici kiyaslayici = new KelimeKokFrekansKiyaslayici();
        assertTrue(kiyaslayici.compare(k1, k2) > 0);
        assertTrue(kiyaslayici.compare(k2, k1) < 0);
        kok2.setFrekans(10);
        assertTrue(kiyaslayici.compare(k2, k1) == 0);
        assertTrue(kiyaslayici.compare(null, k1) < 0);
        assertTrue(kiyaslayici.compare(k2, null) < 0);

    }

}
