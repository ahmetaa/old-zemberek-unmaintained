package net.zemberek;

import net.zemberek.bilgi.KaynakYukleyici;
import net.zemberek.erisim.Zemberek;
import net.zemberek.yapi.TurkiyeTurkcesi;

import java.io.IOException;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;


public class ZemberekTest {

    Zemberek zemberek;

    public ZemberekTest() {
       zemberek = new Zemberek(new TurkiyeTurkcesi());
    }

    /**
     * basit satir okuyucu.
     * @param dosya
     * @return
     * @throws IOException
     */
    public static List<String> satirlariOku(String dosya) throws IOException {
        BufferedReader reader = new KaynakYukleyici("UTF-8").getReader(dosya);
        List<String> satirlar = new ArrayList();
        String s;
        while ((s = reader.readLine()) != null) {
            if (s.startsWith("#") || s.trim().length() == 0) continue;
            satirlar.add(s.trim());
        }
        reader.close();
        return satirlar;
    }

    /**
     * fonksiyonel cozumleme testi. dogru ve yanlis yazilmis kelime dosyalarini okuyup test eder.
     *
     * @throws java.io.IOException
     */
    public void denetleDogruYanlis() throws IOException {
        List<String> dogrular = satirlariOku("kaynaklar/tr/test/hepsi-dogru.txt");
        for (String s : dogrular) {
            testTrue(zemberek.kelimeDenetle(s), "denetleme hatasi:" + s);
        }
        List<String> yanlislar = satirlariOku("kaynaklar/tr/test/hepsi-yanlis.txt");
        for (String s : yanlislar) {
            testTrue(!zemberek.kelimeDenetle(s), "denetleme hatasi:" + s);
        }
    }

    public void testTrue(boolean result, String mesaj) {
        if (!result) {
            System.out.println(mesaj);
            System.exit(1);
        }
    }

    public static void main(String[] args) throws IOException {
        ZemberekTest test = new ZemberekTest();
        test.denetleDogruYanlis();
        System.out.println("test basarili..");
    }
}
