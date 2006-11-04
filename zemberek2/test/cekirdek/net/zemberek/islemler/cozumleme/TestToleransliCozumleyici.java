package net.zemberek.islemler.cozumleme;

import com.thoughtworks.xstream.XStream;
import net.zemberek.TemelTest;
import net.zemberek.araclar.turkce.YaziIsleyici;
import net.zemberek.bilgi.araclar.DuzYaziKokOkuyucu;
import net.zemberek.bilgi.araclar.KokOkuyucu;
import net.zemberek.bilgi.kokler.KokBulucu;
import net.zemberek.bilgi.kokler.Sozluk;
import net.zemberek.bilgi.kokler.AgacSozluk;
import net.zemberek.yapi.Kelime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 */
public class TestToleransliCozumleyici extends TemelTest {

    static KelimeCozumleyici cozumleyici;
    static KokOkuyucu kokOkuyucu;
    static KokBulucu kokBulucu = null;
    private List testKumesi = new ArrayList();

    public void setUp() throws IOException {
        super.setUp();
        KokOkuyucu kokOkuyucu = new DuzYaziKokOkuyucu(
                "kaynaklar/tr/test/test-kokler.txt",
                dilBilgisi.kokOzelDurumlari(),
                alfabe,
                dilAyarlari.kokTipiAdlari());
        Sozluk sozluk = new AgacSozluk(kokOkuyucu, alfabe, dilBilgisi.kokOzelDurumlari());
        //Normal denetleyici-cozumleyici olusumu
        KokBulucu kokBulucu = sozluk.getKokBulucuFactory().getToleransliKokBulucu(1);
        cozumleyici = new StandartCozumleyici(
                kokBulucu,
                new KesinHDKiyaslayici(),
                this.alfabe,
                dilBilgisi.ekler(),
                dilBilgisi.cozumlemeYardimcisi());

    }

    public void testToleransliCozumleyici() throws IOException {
        parse();
        for (int i = 0; i < testKumesi.size(); i++) {
            Uye uye = (Uye) testKumesi.get(i);
            System.out.println("giris:" + uye.giris);
            Kelime[] sonucz = cozumleyici.cozumle(uye.giris);
            List sonuclar = Arrays.asList(sonucz);
            for (int j = 0; j < sonuclar.size(); j++) {
                Kelime kelime = (Kelime) sonuclar.get(j);
                System.out.println("Sonuc " + j + " = " + kelime);
                assertTrue(uye.sonuclar.contains(kelime.icerik().toString()));
            }
        }
    }

    private void parse() throws IOException {
        String xmlStr = YaziIsleyici.yaziOkuyucu(getClass().getResource("/net/zemberek/islemler/cozumleme/toleransli-cozumleme-test.xml").getPath());
        XStream xstream = new XStream();
        xstream.alias("testKumesi", java.util.ArrayList.class);
        xstream.alias("uye", Uye.class);
        xstream.alias("sonuclar", java.util.ArrayList.class);
        xstream.alias("sonuc", String.class);
        testKumesi = (List) xstream.fromXML(xmlStr);
    }

    class Uye {

        public String giris;
        public List sonuclar = new ArrayList();
    }
}




