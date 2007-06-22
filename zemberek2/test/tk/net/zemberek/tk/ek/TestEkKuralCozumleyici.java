package net.zemberek.tm.ek;

import junit.framework.TestCase;
import net.zemberek.yapi.HarfDizisi;
import net.zemberek.yapi.ek.EkUretici;
import net.zemberek.tm.yapi.ek.EkUreticiTm;
import net.zemberek.yapi.ek.EkUretimBileseni;
import static net.zemberek.yapi.ek.EkUretimKurali.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: ahmet
 * Date: May 23, 2006
 */
public class TestEkKuralCozumleyici extends TestCase {

     EkKuralCozumleyici cozumleyici = new EkKuralCozumleyici(TurkmenceAlfabe.ref(),TurkmenceEkYonetici.getOzelDurumTablosu());

    public void setUp() {

    }

    public void testKuralAyristirma() {
        String kural = "^d+(I)+m";
        EkUretimBileseni[] bilesenlerD = {
                new EkUretimBileseni(SERTLESTIR, TurkmenceAlfabe.HARP_d),
                new EkUretimBileseni(SESLI_IU, null),
                new EkUretimBileseni(HARF, TurkmenceAlfabe.HARP_m)
        };
        List<EkUretimBileseni> bilesenler = new ArrayList(Arrays.asList(bilesenlerD));
        assertEquals(bilesenler, cozumleyici.kuralKelimesiAyristir(kural));
    }

    public void testCozumlemeIcinUretIU() {
        String[] olusumlar = {"buk", "bu", "buku" };
        String giris1 = "buktum";
        String giris2 = "bukty";
        String[] beklenen1 = {"tu", "du", "dy" };
        String[] beklenen2 = {"ty", "dy", "dy" };

        EkUretici cozucu = new EkUreticiTm();
        String kural = "^d+(I)";
        List<EkUretimBileseni> bilesenler = cozumleyici.kuralKelimesiAyristir(kural);
        for (int i = 0; i < olusumlar.length; i++) {
            String s = olusumlar[i];
            String sonuc1 = cozucu.cozumlemeIcinEkUret(
                    new HarfDizisi(s, TurkmenceAlfabe.ref()),
                    new HarfDizisi(giris1, TurkmenceAlfabe.ref()),
                    bilesenler).toString();
            assertEquals(beklenen1[i], sonuc1);

            String sonuc2 = cozucu.cozumlemeIcinEkUret(
                    new HarfDizisi(s, TurkmenceAlfabe.ref()),
                    new HarfDizisi(giris2, TurkmenceAlfabe.ref()),
                    bilesenler).toString();
            assertEquals(beklenen2[i], sonuc2);
        }
    }


}
