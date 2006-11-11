/*
 * Created on 06.Nis.2004
 */
package net.zemberek.bilgi.araclar;

import net.zemberek.yapi.Kok;
import net.zemberek.yapi.kok.KokOzelDurumu;

import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * Verilen bir sözlüğün düzyazı olarak yazılmasını sağlar.
 * @author MDA
 */
public class DuzYaziKokYazici implements KokYazici {

    BufferedWriter writer;

    public DuzYaziKokYazici(String dosyaAdi) throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(dosyaAdi);
        writer = new BufferedWriter(new OutputStreamWriter(fos));
    }


    public void yaz(List kelimeler) throws IOException {
        writer.write("#-------------------------\n");
        writer.write("# TSPELL DUZ METIN SOZLUK \n");
        writer.write("#-------------------------\n");
        writer.write("#v0.1\n");
        for (Iterator it = kelimeler.iterator(); it.hasNext();) {
            Kok kelime = (Kok) it.next();
            writer.write(getDuzMetinSozlukForm(kelime));
            writer.newLine();
        }
        writer.close();
    }

    private String getDuzMetinSozlukForm(Kok kok) {

        //icerik olarak iceriign varsa asil halini yoksa normal kok icerigini al.
        String icerik = kok.icerik();
        if (kok.asil() != null)
            icerik = kok.asil();

        StringBuilder res = new StringBuilder(icerik);
        res.append(" ");
        // Tipi ekleyelim.
        if (kok.tip() == null) {
            System.out.println("tipsiz kok:" + kok);
            return res.toString();
        }

        res.append(kok.tip().toString());
        res.append(" ");
        res.append(getOzellikString(kok.ozelDurumDizisi()));
        return res.toString();
    }

    private String getOzellikString(KokOzelDurumu[] ozelDurumlar) {
        String oz = "";
        for (KokOzelDurumu ozelDurum : ozelDurumlar) {
            oz = oz + ozelDurum.kisaAd() + " ";
        }
        return oz;
    }
}