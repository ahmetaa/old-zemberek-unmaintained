package net.zemberek.yapi;

/**
 * User: aakin
 * Date: Jun 19, 2008,3:37:26 PM
 */
public class Kisaltma extends Kok{

    // bazi kisaltmalara ek eklenebilmesi icin kisaltmanin asil halinin son seslisine ihtiyac duyulur.
    private char kisaltmaSonSeslisi;


    public Kisaltma(String icerik) {
        super(icerik);
        this.tip=KelimeTipi.KISALTMA;
    }

    public char getKisaltmaSonSeslisi() {
        return kisaltmaSonSeslisi;
    }

    public void setKisaltmaSonSeslisi(char kisaltmaSonSeslisi) {
        this.kisaltmaSonSeslisi = kisaltmaSonSeslisi;
    }
}
