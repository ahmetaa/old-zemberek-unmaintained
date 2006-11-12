/*
 * Created on 18.Haz.2005
 *
 */
package net.zemberek.istatistik;

import net.zemberek.yapi.Kok;

import java.io.*;
import java.util.ArrayList;

public class BinaryIstatistikYazici extends TemelIstatistikYazici implements IstatistikYazici {

    public static final int ISTATISTIGI_TUTLACAK_KOK_SAYISI=12000;
    public void initialize(String fileName) {
        try {
            outputFile = new FileOutputStream(fileName);
            OutputStreamWriter outputStream = new OutputStreamWriter(outputFile, "UTF-8");
            writer = new BufferedWriter(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    public void yaz(Istatistikler ist) {
        KokIstatistikleri kokIst = ist.getKokIstatistikleri();
        ArrayList list = kokIst.getKokListesi();
        try {
            int sinir = ISTATISTIGI_TUTLACAK_KOK_SAYISI;
            if(list.size()<ISTATISTIGI_TUTLACAK_KOK_SAYISI)
              sinir = list.size();
            for (int i = 0; i < sinir; i++) {
                GenelKokIstatistikBilgisi bilgi = (GenelKokIstatistikBilgisi) list.get(i);
                Kok kok = bilgi.getKok();
                // boy yaz
                writer.write(kok.icerik().length());
                // kok'u yaz.
                writer.write(kok.icerik());
                // Frekans (Bir milyon ile çarp)
                System.out.println("Kok:" + kok.icerik() + ", indeks:"+ kok.getIndeks()+ ", Frekans: " + bilgi.getKullanimFrekansi());
                writer.write(bilgi.getKullanimFrekansi());
            }
            System.out.println("Istatistigi yazilan kok sayisi: " + sinir);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
