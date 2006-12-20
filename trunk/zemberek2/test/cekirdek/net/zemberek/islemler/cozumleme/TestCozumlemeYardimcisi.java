/*
 *  ***** BEGIN LICENSE BLOCK *****
 *  Version: MPL 1.1
 *
 *  The contents of this file are subject to the Mozilla Public License Version
 *  1.1 (the "License"); you may not use this file except in compliance with
 *  the License. You may obtain a copy of the License at
 *  http://www.mozilla.org/MPL/
 *
 *  Software distributed under the License is distributed on an "AS IS" basis,
 *  WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 *  for the specific language governing rights and limitations under the
 *  License.
 *
 *  The Original Code is Zemberek Doðal Dil Ýþleme Kütüphanesi.
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akýn, Mehmet D. Akýn.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberek.islemler.cozumleme;

import net.zemberek.TemelTest;
import net.zemberek.tr.islemler.TurkceCozumlemeYardimcisi;
import net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;
import static org.junit.Assert.assertEquals;
import org.junit.Test;


/**
 * User: ahmet
 * Date: Oct 29, 2005
 */
public class TestCozumlemeYardimcisi extends TemelTest{

    @Test
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
