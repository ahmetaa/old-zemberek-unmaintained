/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.bilgi.araclar;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.kok.KokOzelDurumu;

/**
 * Verilen bir sözlüğün düzyazı olarak yazılmasını sağlar.
 *
 * @author MDA
 */
public class DuzYaziKokYazici implements KokYazici {

    BufferedWriter writer;
    private Map<KelimeTipi, String> tipAdlari = new HashMap<KelimeTipi, String>();

    public DuzYaziKokYazici(String dosyaAdi, Map<String, KelimeTipi> kokAdTipMap) throws IOException {
        for (Map.Entry<String, KelimeTipi> entry : kokAdTipMap.entrySet()) {
            tipAdlari.put(entry.getValue(), entry.getKey());
        }
        FileOutputStream fos = new FileOutputStream(dosyaAdi);
        writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
    }


    public void yaz(List<Kok> kokler) throws IOException {
        writer.write("#-------------------------\n");
        writer.write("# ZEMBEREK DUZ YAZI SOZLUK \n");
        writer.write("#-------------------------\n");
        writer.write("#v0.1\n");
        for (Kok kok : kokler) {
            writer.write(getDuzMetinSozlukForm(kok));
            writer.newLine();
        }
        writer.close();
    }

    private String getDuzMetinSozlukForm(Kok kok) {

        //icerik olarak iceriign varsa asil halini yoksa normal kok icerigini al.
        String icerik = kok.icerik();
        if (kok.asil() != null)
            icerik = kok.asil();

        StringBuilder res = new StringBuilder(icerik).append(" ");

        // Tipi ekleyelim.
        if (kok.tip() == null) {
            System.out.println("tipsiz kok:" + kok);
            return res.toString();
        }

        if (!tipAdlari.containsKey(kok.tip())) {
            System.out.println("tip icin dile ozel kisa ad bulunamadi.:" + kok.tip().name());
            return "#" + kok.icerik();
        }

        res.append(tipAdlari.get(kok.tip()))
                .append(" ")
                .append(getOzellikString(kok.ozelDurumDizisi()));
        return res.toString();
    }

    private String getOzellikString(KokOzelDurumu[] ozelDurumlar) {
        String oz = "";
        for (KokOzelDurumu ozelDurum : ozelDurumlar) {
            if (!ozelDurum.otomatikbelilenir())
                oz = oz + ozelDurum.kisaAd() + " ";
        }
        return oz;
    }
}