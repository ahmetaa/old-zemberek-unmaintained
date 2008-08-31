/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
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
            	System.out.println("Bitti. Frekansi yazilan kök sayisi: " + sayac);
            }
       finally {
            if (dis != null)
                dis.close();
        }
    }

}
