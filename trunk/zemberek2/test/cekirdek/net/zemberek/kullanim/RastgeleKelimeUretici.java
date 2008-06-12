/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.kullanim;

import net.zemberek.islemler.KelimeUretici;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.*;
import net.zemberek.yapi.ek.Ek;
import net.zemberek.yapi.ek.EkYonetici;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class RastgeleKelimeUretici {

    private static Random random = new Random();
    ArrayList isimler = new ArrayList(100);
    ArrayList sifatlar = new ArrayList(100);
    ArrayList fiiller = new ArrayList(100);
    EkYonetici ekYonetici;
    KelimeUretici kelimeUretici;
    Alfabe alfabe;


    public RastgeleKelimeUretici() {

        DilBilgisi db = new TurkceDilBilgisi(new TurkiyeTurkcesi());
        alfabe = db.alfabe();
        ekYonetici = db.ekler();
        kelimeUretici = new KelimeUretici(alfabe, ekYonetici, db.cozumlemeYardimcisi());

        for (Kok kok : db.kokler().tumKokler()) {
            if (kok.tip() == KelimeTipi.ISIM) {
                isimler.add(kok);
            } else if (kok.tip() == KelimeTipi.FIIL) {
                fiiller.add(kok);
            } else if (kok.tip() == KelimeTipi.SIFAT) {
                sifatlar.add(kok);
            }
        }
    }

    public Kok isimSec() {
        int r = random.nextInt(isimler.size());
        return (Kok) isimler.get(r);
    }

    public Kok fiilSec() {
        int r = random.nextInt(fiiller.size());
        return (Kok) fiiller.get(r);
    }

    public Kok sifatSec() {
        int r = random.nextInt(sifatlar.size());
        return (Kok) sifatlar.get(r);
    }

    public List rastgeleEkListesiGetir(List ekler, int limit) {
        if (ekler.size() == limit) {
            return ekler;
        }
        Ek sonEk = (Ek) ekler.get(ekler.size() - 1);
        List olasiArdisilEkler = sonEk.ardisilEkler();
        if (olasiArdisilEkler == null || olasiArdisilEkler.size() == 0) {
            return ekler;
        }
        Ek rastgeleEk = (Ek) olasiArdisilEkler.get(random.nextInt(olasiArdisilEkler.size()));
        ekler.add(rastgeleEk);
        return rastgeleEkListesiGetir(ekler, limit);
    }

    public String rastgeleKelimeOlustur(Kok kok, int maxEkSayisi) {
        Kelime kelime = kelimeUret(kok);
        ArrayList girisEkListesi = new ArrayList();
        girisEkListesi.add(kelime.sonEk());
        List rastgeleEkler = rastgeleEkListesiGetir(girisEkListesi, maxEkSayisi);
        return kelimeUretici.kelimeUret(kok, rastgeleEkler);
    }

    private Kelime kelimeUret(Kok kok) {
        Kelime kelime = new Kelime(kok, alfabe);
        kelime.ekEkle(ekYonetici.ilkEkBelirle(kelime.kok()));
        return kelime;
    }

    public static void main(String[] args) throws IOException {
        RastgeleKelimeUretici r = new RastgeleKelimeUretici();
        for (int i = 0; i < 30; i++) {
            System.out.print(r.rastgeleKelimeOlustur(r.sifatSec(), 1) + " ");
            System.out.print(r.rastgeleKelimeOlustur(r.isimSec(), random.nextInt(3) + 1) + " ");
            System.out.print(r.rastgeleKelimeOlustur(r.fiilSec(), random.nextInt(3) + 1) + " ");
            System.out.println("");
        }

    }
}