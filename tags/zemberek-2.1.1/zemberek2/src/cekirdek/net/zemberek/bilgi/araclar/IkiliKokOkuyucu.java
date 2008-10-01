/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.bilgi.araclar;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.zemberek.bilgi.KaynakYukleyici;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.Kisaltma;
import net.zemberek.yapi.kok.KokOzelDurumBilgisi;
import net.zemberek.yapi.kok.KokOzelDurumu;

public class IkiliKokOkuyucu implements KokOkuyucu {

    private DataInputStream dis;
    private KokOzelDurumBilgisi ozelDurumlar;

    public IkiliKokOkuyucu(InputStream is, KokOzelDurumBilgisi ozelDurumlar) {
        dis = new DataInputStream(new BufferedInputStream(is));
        this.ozelDurumlar = ozelDurumlar;
    }

    public IkiliKokOkuyucu(String dosyaAdi, KokOzelDurumBilgisi ozelDurumlar) throws IOException {
        InputStream fis = new KaynakYukleyici("UTF-8").getStream(dosyaAdi);
        dis = new DataInputStream(new BufferedInputStream(fis));
        this.ozelDurumlar = ozelDurumlar;
    }

    /**
     * Sözlükteki Tüm kökleri okur ve bir ArrayList olarak döndürür.
     */
    public List<Kok> hepsiniOku() throws IOException {
        ArrayList<Kok> list = new ArrayList<Kok>();
        Kok kok;
        while ((kok = oku()) != null) {
            list.add(kok);
        }
        dis.close();
        return list;
    }

    /**
     * İkili (Binary) sözlükten bir kök okur. çağrıldıkça bir sonraki kökü alır.
     *
     * @return bir sonraki kök. Eğer okunacak kök kalmamışsa null
     */
    public Kok oku() throws IOException {

        String icerik;
        //kok icerigini oku. eger dosya sonuna gelinmisse (EOFException) null dondur.
        try {
            icerik = dis.readUTF();
        } catch (EOFException e) {
            dis.close();
            return null;
        }
        String asil = dis.readUTF();

        // Tip bilgisini oku (1 byte)
        KelimeTipi tip = KelimeTipi.getTip(dis.read());

        Kok kok;
        if (tip == KelimeTipi.KISALTMA)
            kok = new Kisaltma(icerik);
        else
            kok = new Kok(icerik, tip);

        if (asil.length() != 0)
            kok.setAsil(asil);

        char kisaltmaSeslisi = dis.readChar();
        if (kisaltmaSeslisi != '#')
            ((Kisaltma) kok).setKisaltmaSonSeslisi(kisaltmaSeslisi);

        // Özel durum sayısını (1 byte) ve ozel durumlari oku.
        int ozelDurumSayisi = dis.read();
        for (int i = 0; i < ozelDurumSayisi; i++) {
            int ozelDurum = dis.read();
            KokOzelDurumu oz = ozelDurumlar.ozelDurum(ozelDurum);
            kok.ozelDurumEkle(oz);
        }
        int frekans = dis.readInt();
        if (frekans != 0) {
            kok.setFrekans(frekans);
        }
        return kok;
    }
}
