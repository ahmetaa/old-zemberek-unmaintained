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
        kelimeUretici = new KelimeUretici(alfabe, db.cozumlemeYardimcisi());

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