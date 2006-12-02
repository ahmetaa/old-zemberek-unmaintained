package net.zemberek.bilgi.kokler;

import net.zemberek.TemelTest;
import net.zemberek.araclar.TimeTracker;
import net.zemberek.bilgi.araclar.DuzYaziKokOkuyucu;
import net.zemberek.bilgi.araclar.KokOkuyucu;
import net.zemberek.yapi.Kok;
import static org.junit.Assert.*;
import org.junit.Test;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 */
public class TestTreeSozluk extends TemelTest {

    KokOkuyucu okuyucu;
    Sozluk sozluk = null;

    public void once() throws IOException {
        super.once();
        okuyucu= getOkuyucu("kaynaklar/tr/test/test-sozluk.txt");
        sozluk = new AgacSozluk(okuyucu, alfabe, dilBilgisi.kokOzelDurumlari());
    }

    private KokOkuyucu getOkuyucu(String dosya) throws IOException {
        return  new DuzYaziKokOkuyucu(
                dosya,
                dilBilgisi.kokOzelDurumlari(),
                alfabe,
                dilAyarlari.kokTipiAdlari());
    }

    @Test
    public void testHatasizlik_binary() throws IOException {
        TimeTracker.startClock("x");
        KokOkuyucu okuyucu1 = getOkuyucu("kaynaklar/tr/bilgi/binary-kokler.bin");
        AgacSozluk testSozluk = new AgacSozluk(okuyucu1, alfabe,  dilBilgisi.kokOzelDurumlari());
        System.out.println("Time: " + TimeTracker.getElapsedTimeString("x"));
        TimeTracker.stopClock("x");
        KokOkuyucu okuyucu2 = getOkuyucu("kaynaklar/tr/bilgi/binary-kokler.bin");
        List<Kok> list = okuyucu2.hepsiniOku();
        for (Kok kok : list) {
            if (testSozluk.kokBul(kok.icerik()) == null) {
                fail("Kelime aðaçta bulunamadý: " + kok.icerik());
            }
        }
    }

    @Test
    public void testHatasizlik_() throws IOException {
        KokOkuyucu okuyucu1 = getOkuyucu("kaynaklar/tr/test/agactest-1.txt");
        AgacSozluk testSozluk = new AgacSozluk(okuyucu1, alfabe, dilBilgisi.kokOzelDurumlari());
        KokOkuyucu okuyucu2 = getOkuyucu("kaynaklar/tr/test/agactest-1.txt");
        List<Kok> list = okuyucu2.hepsiniOku();
        for (Kok kok : list) {         
            if (testSozluk.kokBul(kok.icerik()) == null) {
                fail("Kelime aðaçta bulunamadý: " + kok.icerik());
            }
        }
        Collection kokler = testSozluk.kokBul("imren");
        assertEquals(2, kokler.size());
    }

    @Test
    public void testHatasizlik() throws IOException {
        KokOkuyucu okuyucu1 = getOkuyucu("kaynaklar/tr/bilgi/duzyazi-kilavuz.txt");
        AgacSozluk testSozluk = new AgacSozluk(okuyucu1, alfabe, dilBilgisi.kokOzelDurumlari());
        KokOkuyucu okuyucu2 = getOkuyucu("kaynaklar/tr/bilgi/duzyazi-kilavuz.txt");
        List<Kok> list = okuyucu2.hepsiniOku();
        for (Kok kok : list) {
            if (testSozluk.kokBul(kok.icerik()) == null) {
                fail("Kelime aðaçta bulunamadý: " + kok.icerik());
            }
        }
        List kokler = testSozluk.kokBul("usul");
        assertEquals(2, kokler.size());
    }

    @Test
    public void testKokler() {
        Collection list = sozluk.kokBul("armut");
        System.out.println("list:" + list);
        assertNotNull(list);
        assertTrue(list.size() == 1);
        Kok kok = (Kok) list.iterator().next();
        assertEquals(kok.icerik(), "armut");
        list = sozluk.kokBul("armud");
        System.out.println("list:" + list);
        assertTrue(list.size() == 1);
        kok = (Kok) list.iterator().next();
        assertEquals(kok.icerik(), "armut");
    }

    @Test
    public void testEsSesliKokBul() {
        Collection kokler = sozluk.kokBul("devir");
        assertTrue(kokler.size() == 2);
        kokler = sozluk.kokBul("devr");
        assertTrue(kokler.size() == 2);
    }

    @Test
    public void testKokBul() {
        Collection kokler = sozluk.kokBul("bahset");
        assertTrue(kokler.size() == 1);
        kokler = sozluk.kokBul("bahsed");
        assertTrue(kokler.size() == 1);

        KokBulucu kb = sozluk.getKokBulucuFactory().getKesinKokBulucu();
        List<Kok> adaylar = kb.getAdayKokler("bahsetmek");
        assertTrue(adaylar.size()==1);
        assertEquals(adaylar.get(0).icerik(), "bahset");

        adaylar = kb.getAdayKokler("bahseden");
        assertTrue(adaylar.size()==1);
        assertEquals(adaylar.get(0).icerik(), "bahset");

    }

}
