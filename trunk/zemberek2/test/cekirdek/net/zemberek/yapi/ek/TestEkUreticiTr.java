package net.zemberek.yapi.ek;

import net.zemberek.TemelTest;
import net.zemberek.bilgi.KaynakYukleyici;
import net.zemberek.tr.yapi.ek.EkUreticiTr;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Fonksiyonel test. ek uretimlerinin dogrulugunu denetler.
 * User: ahmet
 * Date: Aug 23, 2005
 */
public class TestEkUreticiTr extends TemelTest {

@Test
    public void testUretici() throws IOException {
        EkUretici uretici = new EkUreticiTr(alfabe);

        BufferedReader reader = new KaynakYukleyici().getReader("kaynaklar/tr/test/ek_olusum.txt");
        String s;
        while ((s = reader.readLine()) != null) {
            if (s.startsWith("#") || s.trim().length() == 0) continue;
            String kuralKelimesi = s.substring(0, s.indexOf(':'));
            String olusumlar[] = s.substring(s.indexOf(':') + 1).trim().split(" ", -1);
            System.out.println("okunan kelime:" + kuralKelimesi + " olusumlar:" + Arrays.toString(olusumlar));
            olusumTesti(kuralKelimesi, olusumlar);
        }
        reader.close();
    }

    public void testKuralCozumleyici() {
         
    }

    private void olusumTesti(String kural, String[] olusumlar) {
/*        Set beklenen = new HashSet();
        for (String s : olusumlar)
            beklenen.add(new HarfDizisi(s, TurkceAlfabe.ref()));
        Set gelen = new HashSet(uretici.ekOlusumlariniUret(kural).values());
        assertEquals("beklenmedik ya da eksik olusum!", gelen, beklenen);*/
    }
}
