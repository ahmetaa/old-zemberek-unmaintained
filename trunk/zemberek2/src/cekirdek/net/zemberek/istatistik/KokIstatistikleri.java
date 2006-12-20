/*
 *  ***** BEGIN LICENSE BLOCK *****
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
 *  The Original Code is Zemberek Doðal Dil Ýþleme Kütüphanesi.
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akýn, Mehmet D. Akýn.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  ***** END LICENSE BLOCK *****
 */

/*
 * Created on 19.Ara.2004
 *
 */
package net.zemberek.istatistik;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import net.zemberek.araclar.IstatistikAraclari;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;

/**
 * @author MDA
 */
public class KokIstatistikleri implements Istatistik {
    private static HashMap kokler = new HashMap(100);
    private long toplamKelime = 0;

    private long toplamIsimKokSayisi = 0;
    private long toplamSifatKokSayisi = 0;
    private long toplamFiilKokSayisi = 0;
    private long toplamSayiKokSayisi = 0;

    private long toplamIsimSayisi = 0;
    private long toplamSifatSayisi = 0;
    private long toplamFiilSayisi = 0;
    private long toplamSayiSayisi = 0;

    private long toplamKelimeUzunlugu = 0;
    private long toplamIsimKelimeUzunlugu = 0;
    private long toplamSifatKelimeUzunlugu = 0;
    private long toplamFiilKelimeUzunlugu = 0;
    private long toplamSayiKelimeUzunlugu = 0;

    private double ortalamaKelimeUzunlugu = 0.0D;
    private double ortalamaIsimKokUzunlugu = 0.0D;
    private double ortalamaFiilKokUzunlugu = 0.0D;

    private double ortalamaKokUzunlugu = 0.0D;
    private double ortalamaIsimUzunlugu = 0.0D;
    private double ortalamaFiilUzunlugu = 0.0D;

    private long toplamKokSayisi = 0;
    private long toplamKelimeSayisi = 0;
    private int[] kontrolDizisi = new int[]{2, 5, 10, 20, 50, 100, 250, 500, 750, 1000, 1500, 2000, 3000, 5000, 7500, 10000, 15000, 20000};
    private long[] araToplamlar = new long[kontrolDizisi.length];
    private int araToplamSayaci = 0;
    // bu dizi en Kok kullanılan n kok'un tum metnin yuzde kacini kapsadigini 
    // incelemek icin kullanilan ornekleme araliklarini tutar.

    private int[] toplamEkUzunlukSayilari = new int[GenelKokIstatistikBilgisi.TABLO_MAX_EK_SAYISI];
    private double[] kokKapsamaYuzdeleri = new double[kontrolDizisi.length];

    private ArrayList kokListesi = null;

    public KokIstatistikleri() {

    }

    public void sonucGuncelle(Kok kok, Kelime giris) {
        GenelKokIstatistikBilgisi kokBilgisi = (GenelKokIstatistikBilgisi) kokler.get(kok.icerik());
        if (kokBilgisi == null) {
            kokBilgisi = new GenelKokIstatistikBilgisi(kok);
            kokler.put(kok.icerik(), kokBilgisi);
        }

        if (kokBilgisi.getKok().tip() == KelimeTipi.ISIM) {
            toplamIsimSayisi++;
            toplamIsimKelimeUzunlugu += giris.icerik().length();
            // Diger isim istatistikleri..
        } else if (kokBilgisi.getKok().tip() == KelimeTipi.FIIL) {
            toplamFiilSayisi++;
            toplamFiilKelimeUzunlugu += giris.icerik().length();
            // Diger Fiil istatistikleri
        } else if (kokBilgisi.getKok().tip() == KelimeTipi.SIFAT) {
            toplamSifatSayisi++;
            toplamSifatKelimeUzunlugu += giris.icerik().length();
            // Diger Sıfat istatistikleri
        } else if (kokBilgisi.getKok().tip() == KelimeTipi.SAYI) {
            toplamSayiSayisi++;
            toplamSayiKelimeUzunlugu += giris.icerik().length();
            // Diger Sıfat istatistikleri
        }
        kokBilgisi.guncelle(giris);
    }


    public void tamamla() {
        kokListesi = new ArrayList();
        araToplamSayaci = 0;

        // Kokleri bir listeye doldur (sIralamak iÇin)
        for (Iterator it = kokler.values().iterator(); it.hasNext();) {
            GenelKokIstatistikBilgisi kokBilgisi = (GenelKokIstatistikBilgisi) it.next();
            toplamKelime += kokBilgisi.getKullanimSayisi();
            if (kokBilgisi.getKok().tip() == KelimeTipi.ISIM) {
                toplamIsimKokSayisi++;
                // Diger isim istatistikleri..
            }
            if (kokBilgisi.getKok().tip() == KelimeTipi.FIIL) {
                toplamFiilKokSayisi++;
                // Diger Fiil istatistikleri
            }
            if (kokBilgisi.getKok().tip() == KelimeTipi.SIFAT) {
                toplamSifatKokSayisi++;
                // Diger S�fat istatistikleri
            }
            kokListesi.add(kokBilgisi);
        }
        Collections.sort(kokListesi, new KokMiktarKarsilastirici());

        long araToplam = 0;
        for (int i = 0; i < kokListesi.size(); i++) {
            GenelKokIstatistikBilgisi istatistik = (GenelKokIstatistikBilgisi) kokListesi.get(i);
            istatistik.setKullanimFrekansi((int)(((double)istatistik.getKullanimSayisi() / this.toplamKelime) * 1000000));
            //istatistik.setKullanimFrekansi((int)istatistik.getKullanimSayisi()); //(int)(((double)istatistik.getKullanimSayisi() / this.toplamKelime) * 1000000));
            araToplam += istatistik.getKullanimSayisi();
            if ((i + 1) == kontrolDizisi[araToplamSayaci]) {
                araToplamlar[araToplamSayaci] = araToplam;
                araToplamSayaci++;
            }
            int[] ekUzunlukSayilari = istatistik.getEkUzunlukSayilari();
            for (int j = 0; j < ekUzunlukSayilari.length; j++) {
                // iki uzunluklularin sayisi toplamEkUzunlukSayilari[2] ye vs.
                toplamEkUzunlukSayilari[j] += ekUzunlukSayilari[j];
            }
        }
//        this.evaluate();

        for (int i = 0; i < araToplamSayaci; i++) {
            kokKapsamaYuzdeleri[i] = IstatistikAraclari.yuzdeHesapla(araToplamlar[i], toplamKelime);
        }
        this.toplamKelimeSayisi = toplamKelime;
        this.toplamKokSayisi = kokListesi.size();
        this.ortalamaIsimUzunlugu = (double) toplamIsimKelimeUzunlugu / toplamIsimSayisi;
        this.ortalamaFiilUzunlugu = (double) toplamFiilKelimeUzunlugu / toplamFiilSayisi;

        for (int i = 0; i < toplamKokSayisi; i++) {
            GenelKokIstatistikBilgisi istatistik = (GenelKokIstatistikBilgisi) kokListesi.get(i);
            istatistik.duzenle();
            ArrayList ekler = istatistik.getEkListesi();
        }

    }

    class KokMiktarKarsilastirici implements Comparator {
        public int compare(Object o1, Object o2) {
            GenelKokIstatistikBilgisi ist1 = (GenelKokIstatistikBilgisi) o1;
            GenelKokIstatistikBilgisi ist2 = (GenelKokIstatistikBilgisi) o2;
            return (int) (ist2.getKullanimSayisi() - ist1.getKullanimSayisi());
        }
    }

    public long getToplamKelimeSayisi() {
        return toplamKelimeSayisi;
    }

    public ArrayList getKokListesi() {
        return kokListesi;
    }

    public long getToplamKokSayisi() {
        return toplamKokSayisi;
    }


    public void setToplamKokSayisi(long toplamKokSayisi) {
        this.toplamKokSayisi = toplamKokSayisi;
    }


    public static HashMap getKokler() {
        return kokler;
    }


    public long[] getAraToplamlar() {
        return araToplamlar;
    }


    public int getAraToplamSayaci() {
        return araToplamSayaci;
    }


    public double[] getKokKapsamaYuzdeleri() {
        return kokKapsamaYuzdeleri;
    }


    public int[] getKontrolDizisi() {
        return kontrolDizisi;
    }


    public double getOrtalamaFiilKokUzunlugu() {
        return ortalamaFiilKokUzunlugu;
    }


    public double getOrtalamaFiilUzunlugu() {
        return ortalamaFiilUzunlugu;
    }


    public double getOrtalamaIsimKokUzunlugu() {
        return ortalamaIsimKokUzunlugu;
    }


    public double getOrtalamaIsimUzunlugu() {
        return ortalamaIsimUzunlugu;
    }


    public double getOrtalamaKelimeUzunlugu() {
        return ortalamaKelimeUzunlugu;
    }


    public double getOrtalamaKokUzunlugu() {
        return ortalamaKokUzunlugu;
    }


    public int[] getToplamEkUzunlukSayilari() {
        return toplamEkUzunlukSayilari;
    }


    public long getToplamFiilKelimeUzunlugu() {
        return toplamFiilKelimeUzunlugu;
    }


    public long getToplamFiilKokSayisi() {
        return toplamFiilKokSayisi;
    }


    public long getToplamFiilSayisi() {
        return toplamFiilSayisi;
    }


    public long getToplamIsimKelimeUzunlugu() {
        return toplamIsimKelimeUzunlugu;
    }


    public long getToplamIsimKokSayisi() {
        return toplamIsimKokSayisi;
    }


    public long getToplamIsimSayisi() {
        return toplamIsimSayisi;
    }


    public long getToplamKelime() {
        return toplamKelime;
    }


    public long getToplamKelimeUzunlugu() {
        return toplamKelimeUzunlugu;
    }


    public long getToplamSayiKelimeUzunlugu() {
        return toplamSayiKelimeUzunlugu;
    }


    public long getToplamSayiKokSayisi() {
        return toplamSayiKokSayisi;
    }


    public long getToplamSayiSayisi() {
        return toplamSayiSayisi;
    }


    public long getToplamSifatKelimeUzunlugu() {
        return toplamSifatKelimeUzunlugu;
    }


    public long getToplamSifatKokSayisi() {
        return toplamSifatKokSayisi;
    }


    public long getToplamSifatSayisi() {
        return toplamSifatSayisi;
    }

    public void guncelle() {
    }

}
