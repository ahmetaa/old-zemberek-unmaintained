package net.zemberek.yapi;

import net.zemberek.yapi.ek.Ek;

import java.util.ArrayList;
import java.util.List;

public class Kelime implements Cloneable {

    private HarfDizisi icerik;
    private Kok kok;
    private List<Ek> ekler = new ArrayList<Ek>();
    private KelimeTipi tip;

    public Kelime() {
    }

    public Ek[] ekDizisi() {
        return (Ek[]) ekler.toArray();
    }

    public Kelime clone() {
        Kelime kopya = new Kelime();
        //kok'u kopyalamaya gerek yok. referans esitlemesi yeter
        kopya.kok = kok;
        kopya.icerik = new HarfDizisi(icerik);
        kopya.ekler = new ArrayList(ekler);
        kopya.tip = this.tip;
        return kopya;
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

    public HarfDizisi icerik() {
        return icerik;
    }

    public Ek sonEk() {
        return ekler.get(ekler.size() - 1);
    }

    public void ekEkle(Ek ek) {
        ekler.add(ek);
    }

    public Kok kok() {
        return kok;
    }

    public String toString() {
        StringBuilder ekStr = new StringBuilder();
        for (Ek ek : ekler) {
            ekStr.append(ek.ad()).append(" + ");
        }
        if (ekStr.length() > 3)
            ekStr.delete(ekStr.length() - 3, ekStr.length());
        return "{Icerik: " + icerik + " Kok: " + kok.icerik() + " tip:" + kok.tip() + "} " +
                " Ekler:" + ekStr;
    }

    public void icerikEkle(HarfDizisi eklenecek) {
        icerik.ekle(eklenecek);
    }

    /**
     * Kelime icerisinde sadece kok ya da kok tipini belirten baslangic eki var ise bu metod
     * true dondurur. Eger baska bir ek eklenmis ise true doner.
     * @return
     */
    public boolean gercekEkYok() {
        return ekler.size()<2;
    }
}
