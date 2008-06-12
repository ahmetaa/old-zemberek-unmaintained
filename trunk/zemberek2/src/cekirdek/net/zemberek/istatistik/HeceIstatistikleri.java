/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

/*
 * Created on 09.Eki.2004
 */
package net.zemberek.istatistik;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import net.zemberek.araclar.IstatistikAraclari;

/**
 * @author MDA & GBA
 */
public class HeceIstatistikleri implements Istatistik {
    public static final int MAX_HECE = 5000;
    public static final int MAX_HECE_GOSTERIM = 3000;

    private HashMap<String, Hece> heceMap = new HashMap<String, Hece>(100);
    private ArrayList<Hece> heceListesi = new ArrayList<Hece>(100);
    private long heceSayisi = 0;
    private long toplamHeceBoyu = 0;

    public HeceIstatistikleri() {
    }

    public void guncelle(String gelenHece) {
        heceSayisi++;
        toplamHeceBoyu += gelenHece.length();
        Hece hece = heceMap.get(gelenHece);
        if (hece == null) {
            if (heceMap.size() < MAX_HECE) {
                heceMap.put(gelenHece, new Hece(gelenHece));
            }
        } else
            hece.arttir();
    }

    public void tamamla() {
    	heceListesi.addAll(heceMap.values());
        Collections.sort(heceListesi);
    }

    /**
     * @return Returns the heceSayisi.
     */
    public long getHeceSayisi() {
        return heceSayisi;
    }

    /**
     * @return Returns the heceListesi.
     */
    public List<Hece> getHeceListesi() {
        return heceListesi;
    }

    public String toString() {
        String str = "";
        long araToplam = 0;
        for (int i = 0; i < heceListesi.size(); i++) {
            Hece hece = heceListesi.get(i);
            araToplam += hece.getKullanim();
            str += i + ". " + hece.getHece() + " [" + hece.getKullanim() + "] Oran(%): " + IstatistikAraclari.yuzdeHesaplaStr(hece.getKullanim(), heceSayisi)
                    + " AraToplam(%): " + IstatistikAraclari.yuzdeHesaplaStr(araToplam, heceSayisi) + "\n";
            if (i > MAX_HECE_GOSTERIM)
                return str;
        }
        return str;
    }

    public void raporYaz() {
        String str = "";
        long araToplam = 0;

        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(new File("heceler.txt")), "ISO-8859-9");
            BufferedWriter writer = new BufferedWriter(osw);
            writer.write("Toplam Hece Sayisi:" + heceSayisi + "\n");
            writer.write("Toplam Ayrik Hece Sayisi:" + heceListesi.size() + "\n");
            for (int i = 0; i < heceListesi.size(); i++) {
                Hece hece = heceListesi.get(i);
                araToplam += hece.getKullanim();
                str = i + ". " + hece.getHece() + "  Oran: %" + IstatistikAraclari.onbindeHesaplaStr(hece.getKullanim(), heceSayisi)
                        + " AraToplam: %" + IstatistikAraclari.onbindeHesaplaStr(araToplam, heceSayisi) + " [" + hece.getKullanim() + "]\n";
                writer.write(str);
            }
            writer.close();
            System.out.println("Rapor Tamam.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void guncelle() {
    }
}
