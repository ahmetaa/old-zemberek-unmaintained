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

/*
 * Created on 18.Haz.2005
 *
 */
package net.zemberek.istatistik;

import net.zemberek.yapi.Kok;

import java.io.*;
import java.util.ArrayList;

public class BinaryIstatistikYazici extends TemelIstatistikYazici implements IstatistikYazici {

    public static final int ISTATISTIGI_TUTLACAK_KOK_SAYISI = 20000;
    DataOutputStream dos ;
    public void initialize(String fileName) {
        try {
            outputFile = new FileOutputStream(fileName);
            dos = new DataOutputStream(outputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void yaz(Istatistikler ist) {
        KokIstatistikleri kokIst = ist.getKokIstatistikleri();
        ArrayList list = kokIst.getKokListesi();
        try {
            int sinir = ISTATISTIGI_TUTLACAK_KOK_SAYISI;
            if (list.size() < ISTATISTIGI_TUTLACAK_KOK_SAYISI)
				sinir = list.size();
            for (int i = 0; i < sinir; i++) {
                GenelKokIstatistikBilgisi bilgi = (GenelKokIstatistikBilgisi) list.get(i);
                Kok kok = bilgi.getKok();
                if(kok == null){
                	System.out.println("Kok null?? " + i);
                	break;
                }
                // kok'u yaz.
                dos.writeUTF(kok.icerik());
                // Frekans
                System.out.println("Kok:" + kok.icerik() + ", indeks:"+ kok.getIndeks()+ ", Frekans: " + bilgi.getKullanimFrekansi());
                dos.writeInt(bilgi.getKullanimFrekansi());
            }
            System.out.println("Istatistigi yazilan kok sayisi: " + sinir);
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
