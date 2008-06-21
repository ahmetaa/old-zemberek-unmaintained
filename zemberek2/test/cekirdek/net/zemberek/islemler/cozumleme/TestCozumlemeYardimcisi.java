/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.islemler.cozumleme;

import static org.junit.Assert.assertEquals;
import net.zemberek.TemelTest;
import net.zemberek.tr.islemler.TurkceCozumlemeYardimcisi;
import net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri;
import net.zemberek.yapi.*;

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
        Kisaltma kisaltma = new Kisaltma("tdk");
        kisaltma.setAsil("TDK");
        kisaltma.setKisaltmaSonSeslisi('e');
        kisaltma.ozelDurumEkle(dilBilgisi.kokOzelDurumlari().ozelDurum(TurkceKokOzelDurumTipleri.KISALTMA_SON_SESLI));
        //cozumleme sirasinda "tdk" "tdke" haline donusur.
        Kelime kelime = new Kelime(kisaltma, hd("tdkeye"));
        yardimci.kelimeBicimlendir(kelime);
        assertEquals(kelime.icerikStr(), "TDK'ye");

        kelime.setIcerik(hd("tdk"));
        yardimci.kelimeBicimlendir(kelime);
        assertEquals(kelime.icerikStr(), "TDK");

        kisaltma.setAsil("Prof.");
        kisaltma.setIcerik("prof");
        kisaltma.ozelDurumCikar(TurkceKokOzelDurumTipleri.KISALTMA_SON_SESLI);
        kisaltma.ozelDurumEkle(dilBilgisi.kokOzelDurumlari().ozelDurum(TurkceKokOzelDurumTipleri.KISALTMA_SON_SESSIZ));

        kelime.setIcerik(hd("prof" + Alfabe.CHAR_oo + "bler"));
        yardimci.kelimeBicimlendir(kelime);
        assertEquals(kelime.icerikStr(), "Prof.ler");

    }
}
