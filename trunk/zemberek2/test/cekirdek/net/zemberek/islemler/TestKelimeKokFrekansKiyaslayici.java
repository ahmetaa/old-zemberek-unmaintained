/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.islemler;

import net.zemberek.TemelTest;
import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.Kok;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

/**
 * User: ahmet
 * Date: Dec 10, 2005
 */
public class TestKelimeKokFrekansKiyaslayici extends TemelTest {

    @Test
    public void testKiyaslayici()
    {
        Kok kok1 = new Kok("alo");
        kok1.setFrekans(10);
        Kelime k1 = new Kelime(kok1, alfabe);

        Kok kok2 = new Kok("merhaba");
        kok2.setFrekans(20);
        Kelime k2 = new Kelime(kok2, alfabe);

        List<Kelime> kel = Arrays.asList(k1, k2);
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
