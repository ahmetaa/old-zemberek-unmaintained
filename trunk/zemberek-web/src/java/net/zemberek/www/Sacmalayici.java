package net.zemberek.www;

/**
 * User: ahmet
 * Date: Nov 14, 2005
 */

import net.zemberek.araclar.turkce.TurkceMetinOkuyucu;
import net.zemberek.bilgi.kokler.KokAgaci;
import net.zemberek.bilgi.kokler.Sozluk;
import net.zemberek.erisim.Zemberek;
import net.zemberek.islemler.KelimeUretici;
import net.zemberek.yapi.*;
import net.zemberek.yapi.ek.Ek;
import net.zemberek.yapi.ek.EkYonetici;
import net.zemberek.yapi.ek.TemelEkYonetici;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class Sacmalayici {
    
    private static Logger log = Logger.getLogger("KokBulucu.class");
    private static Random random = new Random();
    KokAgaci agac = null;
    ArrayList isimler = new ArrayList(100);
    ArrayList sifatlar = new ArrayList(100);
    ArrayList fiiller = new ArrayList(100);
    ArrayList zamirler = new ArrayList(100);
    ArrayList zamanlar = new ArrayList(100);
    Alfabe alfabe;
    EkYonetici ekYonetici;
    KelimeUretici kelimeUretici;
    Zemberek zemberek;
    
    
    public Sacmalayici() {
        this.zemberek = WebDemoYonetici.getRef().getZemberek();
        TurkceDilBilgisi dil = (TurkceDilBilgisi)zemberek.dilBilgisi();
        Sozluk kokler = dil.kokler();
        alfabe=dil.alfabe();
        kelimeUretici = new KelimeUretici(dil.alfabe(), dil.cozumlemeYardimcisi());
        ekYonetici = dil.ekler();
        
        Collection<Kok>koklist = kokler.tumKokler();
        for (Kok kok : koklist) {
            if (kok.tip() == KelimeTipi.ISIM) {
                isimler.add(kok);
            } else if (kok.tip() == KelimeTipi.FIIL) {
                fiiller.add(kok);
            } else if (kok.tip() == KelimeTipi.SIFAT) {
                sifatlar.add(kok);
            } else if (kok.tip() == KelimeTipi.ZAMIR) {
                zamirler.add(kok);
            } else if (kok.tip() == KelimeTipi.ZAMAN) {
                zamanlar.add(kok);
            }
        }
    }
    
    public Kok getRastgeleKok(KelimeTipi tip) {
        if (tip == KelimeTipi.ISIM) {
            return rastgeleSec(isimler);
        } else if (tip == KelimeTipi.FIIL) {
            return rastgeleSec(fiiller);
        } else if (tip == KelimeTipi.SIFAT) {
            return rastgeleSec(sifatlar);
        } else if (tip == KelimeTipi.ZAMIR) {
            return rastgeleSec(zamirler);
        } else if (tip == KelimeTipi.ZAMAN) {
            return rastgeleSec(zamanlar);
        }
        return null;
    }
    
    public Kok rastgeleSec(List kokler) {
        int r = random.nextInt(kokler.size());
        return (Kok) kokler.get(r);
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
        //kelime.kuralBelirle(alfabe);
        ArrayList girisEkListesi = new ArrayList();
        girisEkListesi.add(kelime.sonEk());
        List rastgeleEkler = rastgeleEkListesiGetir(girisEkListesi, maxEkSayisi);
        return kelimeUretici.kelimeUret(kok, rastgeleEkler);
    }
    
    private final Kelime kelimeUret(Kok kok) {
        Kelime kelime = new Kelime(kok, alfabe);
        kelime.ekEkle(ekYonetici.ilkEkBelirle(kelime.kok()));
        return kelime;
    }
    
    public void zirvala(String[] giris) {
        for (int i = 0; i < giris.length; i++) {
            if (i % 12 == 0) System.out.println("");
            Kelime[] cozumler = zemberek.kelimeCozumle(giris[i]);
            // Çözümlenememiþse
            if (cozumler.length == 0) {
                System.out.print(giris[i] + " ");
                continue;
            }
            Kok kok = cozumler[0].kok();
            Kok rastgeleKok = getRastgeleKok(kok.tip());
            // Uygun rastgele kök seçilememiþse
            if (rastgeleKok == null) {
                System.out.print(giris[i] + " ");
                continue;
            }
            // kökü deðiþtir ekleri sabit tut
            String zirva = kelimeUretici.kelimeUret(rastgeleKok, cozumler[0].ekler());
            System.out.print(zirva + " ");
        }
    }
    
    public void dosyaZirvala(String dosyaAdi) {
        TurkceMetinOkuyucu tmo = new TurkceMetinOkuyucu();
        String[] icerik = tmo.MetinOku(dosyaAdi);
        zirvala(icerik);
    }
    
}

