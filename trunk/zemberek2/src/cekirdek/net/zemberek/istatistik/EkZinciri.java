/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

/*
 * Created on 29.Haz.2004
 */
package net.zemberek.istatistik;

import java.util.ArrayList;
import java.util.List;

import net.zemberek.yapi.ek.Ek;
import net.zemberek.yapi.ek.EkYonetici;

/**
 * @author MDA & GBA
 */
public class EkZinciri implements Comparable<EkZinciri> {
    List<Ek> ekler = null;
    double kullanimFrekansi = 0.0d;
    int kullanimSayisi;
    String eklerStr = "";

    public EkZinciri() {
        ekler = new ArrayList<Ek>();
    }

    /**
     * @param ekler
     */
    public EkZinciri(List<Ek> ekler) {
        this.ekler = ekler;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ekler.size(); i++) {
            builder.append(ekler.get(i).ad());
            if (i < ekler.size()-1)
                builder.append('+');
        }
        eklerStr = builder.toString();
        kullanimSayisi = 0;
        kullanimFrekansi = 0;
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
                    this.ekler = new ArrayList<Ek>(2);
                }
                this.ekler.add(ek);
            }
            eklerStr += parsedEkler[i];
        }
        kullanimFrekansi = oran;
    }


    public int compareTo(EkZinciri o) {
        EkZinciri giris = o;
        return (giris.kullanimFrekansi - this.kullanimFrekansi > 0 ? 1 : -1);
    }

    public String getStringRep() {
       return eklerStr;
    }

    @Override
    public String toString() {
       return eklerStr;
    }

    public String getEklerStr() {
        return eklerStr;
    }

    public List<Ek> getEkler() {
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
