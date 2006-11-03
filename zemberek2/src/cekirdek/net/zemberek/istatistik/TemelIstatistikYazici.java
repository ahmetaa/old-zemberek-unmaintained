/*
 * Created on 22.Tem.2005
 *
 */
package net.zemberek.istatistik;

import java.io.*;

public class TemelIstatistikYazici {
    protected FileOutputStream outputFile = null;

    protected BufferedWriter writer = null;

    public void initialize(String fileName) {
        this.initialize(fileName, "UTF-8");
    }

    public void initialize(String fileName, String encoding) {
        try {
            outputFile = new FileOutputStream(fileName);
            OutputStreamWriter outputStream = new OutputStreamWriter(outputFile, encoding);
            writer = new BufferedWriter(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
