/*
 * Created on 09.Eki.2004
 */
package net.zemberek.istatistik;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author MDA
 */
public class IkiliIstatistikleri implements Istatistik{
    public static final int MAX_KELIME_ZINCIRI = 500000;
    private String oncekiKelime = null;
    private HashMap<String, KelimeZinciri> kelimeZincirleri = new HashMap<String, KelimeZinciri>(100);
    private ArrayList<KelimeZinciri> siraliKelimeZincirleri = new ArrayList<KelimeZinciri>(100);

    private static long memSaver = 0;

    public void sonucGuncelle(String giris) {
        memSaver++;
        // Kelime zincirleri
        if (oncekiKelime == null) {
            oncekiKelime = giris;
        } else {
            String key = oncekiKelime + "-" + giris;
            KelimeZinciri zincir = kelimeZincirleri.get(key);
            oncekiKelime = giris;
            if (zincir == null) {
                if (memSaver < MAX_KELIME_ZINCIRI) {
                    zincir = new KelimeZinciri(key);
                    kelimeZincirleri.put(key, zincir);
                }
            } else {
                zincir.hit();
            }
        }
    }

    public void guncelle() {
    }

    @SuppressWarnings("unchecked")
	public void tamamla() {
        siraliKelimeZincirleri.addAll(kelimeZincirleri.values());
        Collections.sort(siraliKelimeZincirleri);
    }

    public ArrayList getSiraliKelimeZincirleri() {
        return siraliKelimeZincirleri;
    }


}
