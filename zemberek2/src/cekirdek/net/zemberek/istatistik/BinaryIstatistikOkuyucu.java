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

/*
 * Created on 22.Tem.2005
 *
 */
package net.zemberek.istatistik;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;

import net.zemberek.bilgi.kokler.Sozluk;
import net.zemberek.yapi.Kok;

public class BinaryIstatistikOkuyucu {

    protected FileInputStream is = null;

    DataInputStream dis;

    public void initialize(String fileName) throws IOException {
        //reader = new KaynakYukleyici("UTF-8").getReader(fileName);
    	dis = new DataInputStream(new FileInputStream(fileName));
    }

    public void oku(Sozluk sozluk) throws IOException {
    	int sayac = 0;
        try {
            while (true) {
                String kokStr = dis.readUTF();
                int frekans = dis.readInt();
                //System.out.println("Kok : " + kokStr + " Freq : " + frekans);
                Collection<Kok> col = sozluk.kokBul(kokStr);
                if (col == null) {
                	// sonraki koke geç.
                	continue;
                }
                sayac++;
                // Simdilik tÜm es seslilere ayni frekansi veriyoruz.
                for (Kok kok: col) {
                    kok.setFrekans(frekans);
                }
            }
        }
       catch(EOFException e){
            	System.out.println("Bitti. Frekansı yazılan kök sayısı: " + sayac);
            }
       finally {
            if (dis != null)
                dis.close();
        }
    }

}
