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
 *  The Original Code is Zemberek Doğal Dil İşleme Kütüphanesi.
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akın, Mehmet D. Akın.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

/*
 * Created on 06.Nis.2004
 */
package net.zemberek.bilgi.araclar;

import net.zemberek.yapi.Kok;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.kok.KokOzelDurumu;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Verilen bir sözlüğün düzyazı olarak yazılmasını sağlar.
 *
 * @author MDA
 */
public class DuzYaziKokYazici implements KokYazici {

    BufferedWriter writer;
    private Map<KelimeTipi, String> tipAdlari = new HashMap();

    public DuzYaziKokYazici(String dosyaAdi, Map<String, KelimeTipi> kokAdTipMap) throws IOException {
        for (Map.Entry<String, KelimeTipi> entry : kokAdTipMap.entrySet()) {
            tipAdlari.put(entry.getValue(), entry.getKey());
        }
        FileOutputStream fos = new FileOutputStream(dosyaAdi);
        writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
    }


    public void yaz(List<Kok> kokler) throws IOException {
        writer.write("#-------------------------\n");
        writer.write("# ZEMBEREK DUZ YAZI SOZLUK \n");
        writer.write("#-------------------------\n");
        writer.write("#v0.1\n");
        for (Kok kok : kokler) {
            writer.write(getDuzMetinSozlukForm(kok));
            writer.newLine();
        }
        writer.close();
    }

    private String getDuzMetinSozlukForm(Kok kok) {

        //icerik olarak iceriign varsa asil halini yoksa normal kok icerigini al.
        String icerik = kok.icerik();
        if (kok.asil() != null)
            icerik = kok.asil();

        StringBuilder res = new StringBuilder(icerik).append(" ");

        // Tipi ekleyelim.
        if (kok.tip() == null) {
            System.out.println("tipsiz kok:" + kok);
            return res.toString();
        }

        if (!tipAdlari.containsKey(kok.tip())) {
            System.out.println("tip icin dile ozel kisa ad bulunamadi.:" + kok.tip().name());
            return "#" + kok.icerik();
        }

        res.append(tipAdlari.get(kok.tip()))
                .append(" ")
                .append(getOzellikString(kok.ozelDurumDizisi()));
        return res.toString();
    }

    private String getOzellikString(KokOzelDurumu[] ozelDurumlar) {
        String oz = "";
        for (KokOzelDurumu ozelDurum : ozelDurumlar) {
            if (!ozelDurum.otomatikbelilenir())
                oz = oz + ozelDurum.kisaAd() + " ";
        }
        return oz;
    }
}