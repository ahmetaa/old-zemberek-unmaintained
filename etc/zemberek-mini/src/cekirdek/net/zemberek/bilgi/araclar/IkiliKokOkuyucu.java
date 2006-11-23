package net.zemberek.bilgi.araclar;

import net.zemberek.bilgi.KaynakYukleyici;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.kok.KokOzelDurumBilgisi;
import net.zemberek.yapi.kok.KokOzelDurumu;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: ahmet
 * Date: Jan 15, 2006
 */
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
    public List hepsiniOku() throws IOException {
        ArrayList list = new ArrayList();
        Kok kok = null;
        while ((kok = oku()) != null) {
            list.add(kok);
        }
        dis.close();
        return list;
    }

    /**
     * Ýkili (Binary) sözlükten bir kök okur. Çaðrýldýkça bir sonraki kökü alýr.
     *
     * @return bir sonraki kök. Eðer okunacak kök kalmamýþsa null
     */
    public Kok oku() throws IOException {

        String icerik = null;
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
        Kok kok = new Kok(icerik, tip);

        if (asil.length() != 0)
            kok.setAsil(asil);

        kok.setKisaltmaSonSeslisi(dis.readChar());

        // Özel durum sayýsýný (1 byte) ve ozel durumlari oku.
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
