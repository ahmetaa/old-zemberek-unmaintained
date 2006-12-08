/*
 * Created on 18.Eki.2004
 */
package net.zemberek.bilgi.koksecici;

import net.zemberek.TemelTest;
import net.zemberek.TestUtils;
import net.zemberek.araclar.TimeTracker;
import net.zemberek.bilgi.araclar.DuzYaziKokOkuyucu;
import net.zemberek.bilgi.araclar.IkiliKokOkuyucu;
import net.zemberek.bilgi.araclar.KokOkuyucu;
import net.zemberek.bilgi.kokler.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

/**
 * @author MDA & GBA
 */
public class TestKesinKokBulucu extends TemelTest {
    Sozluk sozluk = null;
    KokBulucu bulucu;
    String[] kelimeler;
    KokOkuyucu okuyucu;

    @Before
    public void once() throws IOException {
        super.once();
        sozluk = sozlukUret("kaynaklar/tr/test/test-sozluk.txt");
    }

    @Test
    public void testWordTreeKokSecici() {
        bulucu = new KesinKokBulucu(sozluk.getAgac());
        System.out.println("Agac:" + sozluk.getAgac().getKokDugumu().getStringRep(2));
        List list = bulucu.getAdayKokler("karalar");
        TestUtils.printList(list);
        System.out.println("Tamamlanma s�resi: " + TimeTracker.stopClock("x"));
    }

    @Test
    public void testToleransliKokBulBasit() {
        bulucu = new ToleransliKokBulucu(sozluk.getAgac(), 1);
        System.out.println("Agac:" + sozluk.getAgac().getKokDugumu().getStringRep(2));
        List list = bulucu.getAdayKokler("deniz");
        TestUtils.printList(list);
        System.out.println("Tamamlanma s�resi: " + TimeTracker.stopClock("x"));
    }

    @Test
    public void testKokSeciciTumSozluk() throws IOException {
        okuyucu = new IkiliKokOkuyucu("kaynaklar/tr/bilgi/binary-kokler.bin", dilBilgisi.kokOzelDurumlari());
        sozluk = new AgacSozluk(okuyucu, alfabe, dilBilgisi.kokOzelDurumlari());
        bulucu = new KesinKokBulucu(sozluk.getAgac());
        List list = bulucu.getAdayKokler("etkiler");
        System.out.println(list);
    }


    @Test
    public void ozelKokAgacTest() throws IOException {
        sozluk=sozlukUret("kaynaklar/tr/test/agac-kokler-2.txt");
        bulucu = new KesinKokBulucu(sozluk.getAgac());
        assertTrue("tek sonuc bekleniyordu", bulucu.getAdayKokler("atoller").size()==1);
    }

    private Sozluk sozlukUret(String duzyaziDosya) throws IOException {
        okuyucu = new DuzYaziKokOkuyucu(
                duzyaziDosya,
                dilBilgisi.kokOzelDurumlari(),
                alfabe,
                dilAyarlari.kokTipiAdlari());
        Sozluk s = new AgacSozluk(okuyucu, alfabe, dilBilgisi.kokOzelDurumlari());
        return s;
    }





}
