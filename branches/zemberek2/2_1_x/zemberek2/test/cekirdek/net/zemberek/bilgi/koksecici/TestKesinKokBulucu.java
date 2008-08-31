/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

/*
 * Created on 18.Eki.2004
 */
package net.zemberek.bilgi.koksecici;

import static java.lang.System.out;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import net.zemberek.TemelTest;
import net.zemberek.TestUtils;
import net.zemberek.araclar.TimeTracker;
import net.zemberek.bilgi.araclar.DuzYaziKokOkuyucu;
import net.zemberek.bilgi.araclar.IkiliKokOkuyucu;
import net.zemberek.bilgi.araclar.KokOkuyucu;
import net.zemberek.bilgi.kokler.AgacSozluk;
import net.zemberek.bilgi.kokler.KesinKokAdayiBulucu;
import net.zemberek.bilgi.kokler.KokAdayiBulucu;
import net.zemberek.bilgi.kokler.ToleransliKokAdayiBulucu;
import net.zemberek.yapi.Kok;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author MDA & GBA
 */
public class TestKesinKokBulucu extends TemelTest {
    AgacSozluk sozluk = null;
    KokAdayiBulucu bulucu;
    String[] kelimeler;
    KokOkuyucu okuyucu;

    @Before
    public void once() throws IOException {
        super.once();
        sozluk = sozlukUret("kaynaklar/tr/test/test-sozluk.txt");
    }

    @Ignore("Bilgi gosterimi amacli test.")
    @Test
    public void testWordTreeKokSecici() {
        bulucu = new KesinKokAdayiBulucu(sozluk.getAgac());
        out.println("Agac:" + sozluk.getAgac().getKokDugumu().goster(2));
        List<Kok> list = bulucu.adayKokleriBul("karalar");
        TestUtils.printList(list);
        out.println("Tamamlanma s�resi: " + TimeTracker.stopClock("x"));
    }

    @Test
    public void testToleransliKokBulBasit() {
        bulucu = new ToleransliKokAdayiBulucu(sozluk.getAgac(), 1);
        out.println("Agac:" + sozluk.getAgac().getKokDugumu().goster(2));
        List<Kok> list = bulucu.adayKokleriBul("deniz");
        TestUtils.printList(list);
        out.println("Tamamlanma s�resi: " + TimeTracker.stopClock("x"));
    }

    @Test
    public void testKokSeciciTumSozluk() throws IOException {
        okuyucu = new IkiliKokOkuyucu("kaynaklar/tr/bilgi/kokler_tr.bin", dilBilgisi.kokOzelDurumlari());
        sozluk = new AgacSozluk(okuyucu, alfabe, dilBilgisi.kokOzelDurumlari());
        bulucu = new KesinKokAdayiBulucu(sozluk.getAgac());
        List<Kok> list = bulucu.adayKokleriBul("etkiler");
        out.println(list);
    }


    @Test
    public void ozelKokAgacTest() throws IOException {
        sozluk=sozlukUret("kaynaklar/tr/test/agac-kokler-2.txt");
        bulucu = new KesinKokAdayiBulucu(sozluk.getAgac());
        assertTrue("tek sonuc bekleniyordu", bulucu.adayKokleriBul("atoller").size()==1);
    }

    private AgacSozluk sozlukUret(String duzyaziDosya) throws IOException {
        okuyucu = new DuzYaziKokOkuyucu(
                duzyaziDosya,
                dilBilgisi.kokOzelDurumlari(),
                alfabe,
                dilAyarlari.kokTipiAdlari());
        AgacSozluk s = new AgacSozluk(okuyucu, alfabe, dilBilgisi.kokOzelDurumlari());
        return s;
    }





}
