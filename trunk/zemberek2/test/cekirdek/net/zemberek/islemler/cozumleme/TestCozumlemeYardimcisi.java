/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.islemler.cozumleme;

import net.zemberek.TemelTest;
import net.zemberek.tr.islemler.TurkceCozumlemeYardimcisi;
import net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.Alfabe;
import static org.junit.Assert.assertEquals;
import org.junit.Test;


/**
 * User: ahmet
 * Date: Oct 29, 2005
 */
public class TestCozumlemeYardimcisi extends TemelTest {

    @Test
    public void testKelimeBicimlendir() {
        CozumlemeYardimcisi yardimci = new TurkceCozumlemeYardimcisi(
                alfabe);
        Kok kok = new Kok("tdk", KelimeTipi.KISALTMA);
        kok.setAsil("TDK");
        kok.setKisaltmaSonSeslisi('e');
        kok.ozelDurumEkle(dilBilgisi.kokOzelDurumlari().ozelDurum(TurkceKokOzelDurumTipleri.KISALTMA_SON_SESLI));
        //cozumleme sirasinda "tdk" "tdke" haline donusur.
        Kelime kelime = new Kelime(kok, hd("tdkeye"));
        yardimci.kelimeBicimlendir(kelime);
        assertEquals(kelime.icerikStr(), "TDK'ye");

        kelime.setIcerik(hd("tdk"));
        yardimci.kelimeBicimlendir(kelime);
        assertEquals(kelime.icerikStr(), "TDK");

        kok.setAsil("Prof.");
        kok.setIcerik("prof");
        kok.ozelDurumCikar(TurkceKokOzelDurumTipleri.KISALTMA_SON_SESLI);
        kok.ozelDurumEkle(dilBilgisi.kokOzelDurumlari().ozelDurum(TurkceKokOzelDurumTipleri.KISALTMA_SON_SESSIZ));

        kelime.setIcerik(hd("prof" + Alfabe.CHAR_oo + "bler"));
        yardimci.kelimeBicimlendir(kelime);
        assertEquals(kelime.icerikStr(), "Prof.ler");

    }
}
