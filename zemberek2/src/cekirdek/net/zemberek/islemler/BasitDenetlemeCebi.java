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
 *  The Original Code is Zemberek Do?al Dil ??leme Kütüphanesi.
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Ak?n, Mehmet D. Ak?n.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberek.islemler;

import net.zemberek.bilgi.KaynakYukleyici;

import java.util.HashSet;
import java.util.Set;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * User: ahmet
 * Date: Jun 12, 2006
 */
public class BasitDenetlemeCebi implements DenetlemeCebi {

    private final Set<String> cep;

    public BasitDenetlemeCebi(String dosyaAdi) throws IOException {
        BufferedReader rd = new KaynakYukleyici("UTF-8").getReader(dosyaAdi);
        cep = new HashSet<String>(2500);
        while (rd.ready()) {
            ekle(rd.readLine());
        }
        rd.close();
    }

    public boolean kontrol(String str) {
        return cep.contains(str);
    }

    public synchronized void ekle(String s) {
        cep.add(s);
    }

    public synchronized void sil(String s) {
        cep.remove(s);
    }
}
