/*
 *  ***** BEGIN LICENSE BLOCK *****
 *
 *  Version: MPL 1.1
 *
 *  The contents of this file are subject to the Mozilla Public License Version
 *  1.1 (the "License"); you may not use this file except in compliance with
 *  the License. You may obtain a copy of the License at
 *  http://www.mozilla.org/MPL/
 *
 *  Software distributed under the License is distributed on an "AS IS" basis,
 *  WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 *  for the specific language governing rights and limitations under the
 *  License.
 *
 *  The Original Code is "Zemberek Dogal Dil Isleme Kutuphanesi"
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akin, Mehmet D. Akin.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

/*
 * Created on 09.Eki.2004
 */
package net.zemberek.istatistik;

import net.zemberek.araclar.IstatistikAraclari;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

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
    public ArrayList getHeceListesi() {
        return heceListesi;
    }

    public String toString() {
        String str = "";
        long araToplam = 0;
        for (int i = 0; i < heceListesi.size(); i++) {
            Hece hece = (Hece) heceListesi.get(i);
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
                Hece hece = (Hece) heceListesi.get(i);
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
