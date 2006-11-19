/*
 * Created on 26.Haz.2004
 */
package net.zemberek.istatistik;

import net.zemberek.araclar.IstatistikAraclari;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.ek.Ek;

import java.util.*;

/**
 * @author MDA 
 * TODO: Az kullanılacak istatistikleri farklı bir sınıfa al 
 */
public class GenelKokIstatistikBilgisi implements KokIstatistikBilgisi {
    // Kullanım frekansı
    private Kok kok = null;
    private long kullanimSayisi = 1;
    protected int kullanimFrekansi = 1;  
    private int yalinHal = 0;
    private double ortalamaKelimeUzunlugu = 0;
    private long toplamUzunluk = 0;
    private int ortalamaEkSayisi = 0;
    private ArrayList ekListesi = null;

    //Ek istatistikleri, bu kelime kökü için kullanılan ek zincirlerinden
    //en sık kullanılan "n" adedini tutar. Her bir zincir bir Ek dizisi ve kullanım frekansı
    // bilgilerini taşır.
    // zincir bilgileri için max ek zinciri uzunlugu
    public static final int TABLO_MAX_EK_ZINCIR_BOYU = 12;
    // ek sayilari icin max ek zincir boyu
    public static final int TABLO_MAX_EK_SAYISI = 15;

    private HashMap ekZincirleri = new HashMap();
    int[] ekUzunlukSayilari = new int[TABLO_MAX_EK_SAYISI];

    public GenelKokIstatistikBilgisi() {
    }

    public GenelKokIstatistikBilgisi(Kok kok) {
        this.kok = kok;
    }

    /**
     * Guncelle metodu, kelimenin çözümleme işleminden sonra çağrılır.
     *
     * @param kelime
     */
    public void guncelle(Kelime kelime) {
        //ekDizileri.put(kelime, kelime.ekler());
        kullanimSayisi++;
        toplamUzunluk += kelime.boy();
        ortalamaKelimeUzunlugu = (double) toplamUzunluk / kullanimSayisi;
        // Ek frekans listelerini güncelle. Sadece belli sayıdaki ekler için yapıyoruz
        ekZincirleriniGuncelle(kelime.ekler());
    }

    public void ekZincirleriniGuncelle(List ekler) {
        int ekSayisi = ekler.size();
        if (ekSayisi > 0 && ekSayisi < TABLO_MAX_EK_SAYISI) {
            // yal�n kelimelerde de bir ek bulunuyor.
            ekUzunlukSayilari[ekler.size() - 1]++;
        }

        if (ekSayisi > 0 && ekSayisi <= TABLO_MAX_EK_ZINCIR_BOYU) {
            String ekAnahtari = "";
            for (Iterator i = ekler.iterator(); i.hasNext();) {
                ekAnahtari += ((Ek) i.next()).ad();
            }
            EkZinciri zincir = (EkZinciri) ekZincirleri.get(ekAnahtari);
            if (zincir == null) {
                //System.out.println("EkAnahtarı: " + ekAnahtari);
                zincir = new EkZinciri(ekler);
                ekZincirleri.put(ekAnahtari, zincir);
            }
            zincir.kullanimSayisi++;
        }
    }


    public String toString() {
        String res;
        res = ""
                + "Kulllanim Frekansi : " + kullanimSayisi
                + "Yalin Kullanim :" + yalinHal;
        return res;
    }

    public HashMap getEkZincirleri() {
        return ekZincirleri;
    }

    public long getKullanimSayisi() {
        return kullanimSayisi;
    }

    public int getOrtalamaEkSayisi() {
        return ortalamaEkSayisi;
    }

    public double getOrtalamaKelimeUzunlugu() {
        return ortalamaKelimeUzunlugu;
    }

    public int getYalinHal() {
        return yalinHal;
    }

    public void duzenle() {
        if(ekListesi == null) {
            ekListesi = new ArrayList();
        }
        ekListesi.clear();
        for (Iterator it = ekZincirleri.values().iterator(); it.hasNext();) {
            EkZinciri zincir = (EkZinciri) it.next();
            zincir.kullanimFrekansi = IstatistikAraclari.yuzdeHesapla(zincir.getKullanimSayisi(), this.kullanimSayisi);
            ekListesi.add(zincir);

        }
        Collections.sort(ekListesi);
    }

    public ArrayList getEkListesi() {
        return ekListesi;
    }

    public Kok getKok() {
        return kok;
    }

    public int[] getEkUzunlukSayilari() {
        return ekUzunlukSayilari;
    }

    public int getKullanimFrekansi() {
        return kullanimFrekansi;
    }

    public void setKullanimFrekansi(int kullanimFrekansi) {
        this.kullanimFrekansi = kullanimFrekansi;
    }

    public void ekZinciriEkle(EkZinciri zincir) {
        ekZincirleri.put(zincir.getEklerStr(), zincir);
    }

    public EkZinciri getEkZinciri(String ekZinciriStr) {
        return (EkZinciri) ekZincirleri.get(ekZinciriStr);
    }
}


