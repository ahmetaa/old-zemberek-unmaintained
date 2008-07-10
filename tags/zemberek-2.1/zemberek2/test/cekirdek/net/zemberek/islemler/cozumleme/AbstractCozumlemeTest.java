/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.islemler.cozumleme;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 */
public class AbstractCozumlemeTest {
    protected static String readXMLFile(String fileName) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader in =
                new BufferedReader(new FileReader(fileName));
        String s;
        while ((s = in.readLine()) != null) {
            sb.append(s);
        }
        in.close();
        return sb.toString();
    }
}
