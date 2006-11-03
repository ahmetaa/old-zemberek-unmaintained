/*
 * Created on 22.Tem.2005
 *
 */
package net.zemberek.istatistik;

import net.zemberek.bilgi.KaynakYukleyici;
import net.zemberek.bilgi.kokler.Sozluk;
import net.zemberek.yapi.Kok;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;

public class BinaryIstatistikOkuyucu implements IstatistikOkuyucu {

    protected FileInputStream is = null;

    protected BufferedReader reader = null;

    public void initialize(String fileName) throws IOException {
        reader = new KaynakYukleyici("UTF-8").getReader(fileName);
    }

    public KokIstatistikBilgisi oku(Sozluk sozluk) throws IOException {
        try {
            while (true) {
                // önce kök boyunu oku
                int len = reader.read();
                if (len == -1) {
                    //System.out.println("EOF. ending.");
                    reader.close();
                    return null;
                }
                char[] buf = new char[len];
                reader.read(buf);
                String kokStr = new String(buf);
                Collection<Kok> col = sozluk.kokBul(kokStr);
                if (col == null) {
                    //System.out.println("Isatistik bilgisi verilen kok sozlukte yok? Kok: " + kokStr);
                    // frekansý da oku.
                    reader.read();
                    // sonraki koke geç.
                    continue;
                }
                int frekans = reader.read();
                // Simdilik tüm es seslilere ayni frekansi veriyoruz.
                for (Kok kok: col) {
                    kok.setFrekans(frekans);
                }
            }
        } finally {
            if (reader != null)
                reader.close();
        }
    }

}
