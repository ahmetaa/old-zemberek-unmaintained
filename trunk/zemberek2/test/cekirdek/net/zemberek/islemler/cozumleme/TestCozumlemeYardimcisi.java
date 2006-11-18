package net.zemberek.islemler.cozumleme;

import net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri;
import net.zemberek.tr.islemler.TurkceCozumlemeYardimcisi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;
import net.zemberek.TemelTest;


/**
 * User: ahmet
 * Date: Oct 29, 2005
 */
public class TestCozumlemeYardimcisi extends TemelTest {


    public void testKelimeBicimlendir() {
        CozumlemeYardimcisi yardimci = new TurkceCozumlemeYardimcisi(
                alfabe,
                null);
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

        kelime.setIcerik(hd("prof" + alfabe + "bler"));
        yardimci.kelimeBicimlendir(kelime);
        assertEquals(kelime.icerikStr(), "Prof.ler");

    }
}
