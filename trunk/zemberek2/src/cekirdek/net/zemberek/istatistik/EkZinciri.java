/*
 * Created on 29.Haz.2004
 */
package net.zemberek.istatistik;

import net.zemberek.yapi.ek.Ek;
import net.zemberek.yapi.ek.EkYonetici;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MDA & GBA
 */
public class EkZinciri implements Comparable {
    List ekler = null;
    double kullanimFrekansi = 0.0d;
    int kullanimSayisi;
    String eklerStr = "";

    public EkZinciri() {
        ekler = new ArrayList();
    }

    /**
     * @param ekler
     */
    public EkZinciri(List ekler) {
        StringBuffer buf = new StringBuffer();
        this.ekler = ekler;
        for (int i = 0; i < ekler.size(); i++) {
            buf.append(((Ek) ekler.get(i)).ad());
            //buf.append(' ');
        }
        kullanimSayisi = 0;
        kullanimFrekansi = 0;
        eklerStr = buf.toString();
    }

    /**
     * Strin olarak verilen bir Ek listesinden EkZinciri nesnesi olu�turur.
     *
     * @param eklerStrRep : Boşluyklarla ayrılmış Ek listesi..
     *                    FIIL_YALIN FIIL_DIK ISIM_SAHIPLIK_SEN ISIM_HAL_I
     *                    gibi.
     *                    <p/>
     *                    Buy constructor istatistik dosyalar� okunurken kullan�l�r.
     */
    public EkZinciri(EkYonetici ekYonetici, String eklerStrRep, double oran) {
        // �nce bo�luklarla ayr�lm�� ek listesini alal�m.
        // FIIL_YALIN FIIL_DIK ISIM_SAHIPLIK_SEN ISIM_HAL_I 
        // gibi.
        String[] parsedEkler = eklerStrRep.trim().split(" ");
        for (int i = 0; i < parsedEkler.length; i++) {
            Ek ek = ekYonetici.ek(parsedEkler[i]);
            if (ek != null) {
                // Liste henüz oluşturulmamışsa oluştur.
                if (this.ekler == null) {
                    this.ekler = new ArrayList(2);
                }
                this.ekler.add(ek);
            }
            eklerStr += parsedEkler[i];
        }
        kullanimFrekansi = oran;
    }


    public int compareTo(Object o) {
        EkZinciri giris = (EkZinciri) o;
        return (giris.kullanimFrekansi - this.kullanimFrekansi > 0 ? 1 : -1);
    }

    public String getStringRep() {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < ekler.size(); i++) {
            s.append(((Ek) ekler.get(i)).ad());
            s.append(' ');
        }
        return s.toString();
    }

    public String toString() {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < ekler.size()-1; i++) {
            s.append(((Ek) ekler.get(i)).ad());
            s.append(' ');
        }
        return s.toString();
//
//        String res = eklerStr + " Frekans: " + kullanimFrekansi;
//        return res;
    }

    public String getEklerStr() {
        return eklerStr;
    }

    public List getEkler() {
        return ekler;
    }

    public void ekEkle(Ek ek) {
        ekler.add(ek);
    }

    public double getKullanimFrekansi() {
        return kullanimFrekansi;
    }

    public int getKullanimSayisi() {
        return kullanimSayisi;
    }
}
