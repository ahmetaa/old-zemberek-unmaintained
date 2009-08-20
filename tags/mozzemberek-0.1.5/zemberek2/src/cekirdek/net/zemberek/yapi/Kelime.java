/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi;

import java.util.ArrayList;
import java.util.List;

import net.zemberek.yapi.ek.Ek;

public class Kelime implements Cloneable {

    private static final HarfDizisi BOS_ICERIK = new HarfDizisi(0);
    private HarfDizisi icerik = BOS_ICERIK;
    private Kok kok;
    private List<Ek> ekler = new ArrayList<Ek>(3);
    private KelimeTipi tip;

    public Kelime() {
    }

    public Ek[] ekDizisi() {
        return ekler.toArray(new Ek[ekler.size()]);
    }

    @Override
    public Kelime clone() {
		try {
			Kelime kopya = (Kelime) super.clone();
			kopya.icerik = new HarfDizisi(icerik);
			kopya.ekler = new ArrayList<Ek>(ekler);
			return kopya;
		} catch (CloneNotSupportedException e) {
			throw new Error("Clone is supported, this can not happen " + e.getMessage());
		}
	}

    public List<Ek> ekler() {
        return ekler;
    }

    /**
     * Gosterim amacli bir metod. ek zincirinin anlasilir sekilde yazilmasini saglar.
     *
     * @return ek zinciri dizisi yazimi.
     */
    public String ekZinciriStr() {
        StringBuilder buf = new StringBuilder();
        for (Ek ek : ekler) {
            buf.append(ek.ad()).append(", ");
        }
        if (buf.length() > 2)
            buf.delete(buf.length() - 2, buf.length());
        return buf.toString();
    }

    public Kelime(Kok kok, Alfabe alfabe) {
        this.kok = kok;
        icerik = new HarfDizisi(kok.icerik(), alfabe);
        tip = kok.tip();
    }

    public Kelime(Kok kok, HarfDizisi dizi) {
        this.kok = kok;
        this.icerik = dizi;
        tip = kok.tip();
    }

    public void setIcerik(HarfDizisi icerik) {
        this.icerik = icerik;
    }

    public int ekSayisi() {
        return ekler.size();
    }

    public TurkceHarf sonHarf() {
        return icerik.sonHarf();
    }

    public HarfDizisi icerik() {
        return icerik;
    }

    public int boy() {
        return icerik.length();
    }

    public Ek sonEk() {
        return ekler.get(ekler.size() - 1);
    }

    public String icerikStr() {
        return icerik.toString();
    }

    public void ekEkle(Ek ek) {
        ekler.add(ek);
    }

    public boolean ekVarmi(String ekAdi) {
        for (Ek ek : ekler) {
            if (ek.ad().equalsIgnoreCase(ekAdi))
                return true;
        }
        return false;
    }

    public Kok kok() {
        return kok;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(" [ Kok: " + kok.icerik() + ", " + kok.tip() + " ] ");
        if (ekler.size() > 1)
            str.append(" Ekler: ");
        for (int i = 1; i < ekler.size(); i++) {
            str.append(ekler.get(i).ad());
            if (i < ekler.size() - 1)
                str.append(" + ");
        }
        return str.toString();
    }

    public void icerikEkle(HarfDizisi eklenecek) {
        icerik.ekle(eklenecek);
    }

    /**
     * Kelime icerisinde sadece kok ya da kok tipini belirten baslangic eki var ise bu metod
     * true dondurur. Eger baska bir ek eklenmis ise true doner.
     *
     * @return
     */
    public boolean gercekEkYok() {
        return ekler.size() < 2;
    }
}
