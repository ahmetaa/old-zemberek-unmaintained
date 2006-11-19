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
