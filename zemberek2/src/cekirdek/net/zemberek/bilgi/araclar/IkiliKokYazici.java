/*
 *  ***** BEGIN LICENSE BLOCK *****
 *
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
 *  The Original Code is "Zemberek Dogal Dil Isleme Kutuphanesi"
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akin, Mehmet D. Akin.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberek.bilgi.araclar;

import net.zemberek.yapi.Kok;
import net.zemberek.yapi.kok.KokOzelDurumu;

import java.io.*;
import java.util.List;

/**
 * User: ahmet
 * Date: Jan 16, 2006
 */
public class IkiliKokYazici implements KokYazici {

    DataOutputStream dos;

    public IkiliKokYazici(String dosyaAdi) throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(dosyaAdi);
        dos = new DataOutputStream(new BufferedOutputStream(fos));
    }

    public void yaz(List<Kok> kokler) throws IOException {

        for (Kok kok : kokler) {
            // Kök içerigi
            dos.writeUTF(kok.icerik());

            // asil icerik ozel karakterler barindiran koklerde olur. yoksa bos string yaz.
            if (kok.asil() != null) {
                // Kök asil içerigi
                dos.writeUTF(kok.asil());
            } else
                dos.writeUTF("");

            // Kök tipi
            dos.write(kok.tip().getIndeks());

            dos.writeChar(kok.getKisaltmaSonSeslisi());

            KokOzelDurumu[] ozd = kok.ozelDurumDizisi();
            dos.write(ozd.length);
            for (KokOzelDurumu ozelDurum : ozd) {
                dos.write(ozelDurum.indeks());
            }
            // kullanim frekansi
            dos.writeInt(kok.getFrekans());

        }
        dos.close();
    }

}