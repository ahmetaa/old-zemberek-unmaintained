/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.bilgi.araclar;

import net.zemberek.yapi.Kisaltma;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.kok.KokOzelDurumu;

import java.io.*;
import java.util.List;

public class IkiliKokYazici implements KokYazici {

    DataOutputStream dos;

    public IkiliKokYazici(String dosyaAdi) throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(dosyaAdi);
        dos = new DataOutputStream(new BufferedOutputStream(fos));
    }

    public void yaz(List<Kok> kokler) throws IOException {

        for (Kok kok : kokler) {
            // Kök içerigi
            dos.writeUTF(kok.icerik());

            // asil icerik ozel karakterler barindiran koklerde olur. yoksa bos string yaz.
            if (kok.asil() != null) {
                // Kök asil içerigi
                dos.writeUTF(kok.asil());
            } else
                dos.writeUTF("");

            // Kök tipi
            dos.write(kok.tip().getIndeks());

            if (kok instanceof Kisaltma)
                dos.writeChar(((Kisaltma) kok).getKisaltmaSonSeslisi());
            else
                dos.writeChar('#');

            KokOzelDurumu[] ozd = kok.ozelDurumDizisi();
            dos.write(ozd.length);
            for (KokOzelDurumu ozelDurum : ozd) {
                dos.write(ozelDurum.indeks());
            }
            // kullanim frekansi
            dos.writeInt(kok.getFrekans());

        }
        dos.close();
    }

}