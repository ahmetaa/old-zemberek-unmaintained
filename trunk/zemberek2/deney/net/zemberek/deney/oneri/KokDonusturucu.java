/*
 *  ***** BEGIN LICENSE BLOCK *****
 *
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
 *  The Original Code is "Zemberek Dogal Dil Isleme Kutuphanesi"
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akin, Mehmet D. Akin.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberek.deney.oneri;

import net.zemberek.yapi.*;
import net.zemberek.islemler.cozumleme.KelimeCozumleyici;
import net.zemberek.islemler.cozumleme.CozumlemeSeviyesi;
import net.zemberek.islemler.cozumleme.StandartCozumleyici;
import net.zemberek.islemler.cozumleme.KesinHDKiyaslayici;
import net.zemberek.islemler.KelimeUretici;
import net.zemberek.bilgi.kokler.Sozluk;
import net.zemberek.bilgi.kokler.AgacSozluk;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

public class KokDonusturucu {

    private KelimeCozumleyici cozumleyici;
    private KelimeUretici uretici;
    private Map<Kok, Kok> kokTablosu = new HashMap<Kok, Kok>();


    public KokDonusturucu(KelimeCozumleyici cozumleyici,
                          KelimeUretici uretici,
                          Map<Kok, Kok> kokTablosu) {
        this.cozumleyici = cozumleyici;
        this.uretici = uretici;
        this.kokTablosu = kokTablosu;
    }

    public String donustur(String giris) {
        Kelime[] cozum = cozumleyici.cozumle(giris, CozumlemeSeviyesi.TEK_KOK);
        if (cozum.length == 0)
            return giris;
        Kelime kel = cozum[0];
        Kok karsilik = kokTablosu.get(kel.kok());
        if (karsilik == null) return giris;
        return uretici.kelimeUret(karsilik, kel.ekler());
    }

    public static void main(String[] args) {

        DilBilgisi dilBilgisi = new TurkceDilBilgisi(new TurkiyeTurkcesi());
        Sozluk kokler = dilBilgisi.kokler();

        // yabanci kelimeler
        List<Kok> yabanciKokler = Arrays.asList(
                new Kok("fixle", KelimeTipi.FIIL),
                new Kok("show", KelimeTipi.ISIM),
                new Kok("format", KelimeTipi.ISIM)
        );

        // donusum tablosu
        Map<Kok, Kok> kokTablosu = new HashMap<Kok, Kok>();
        kokTablosu.put(yabanciKokler.get(0), kokler.kokBul("sabitle", KelimeTipi.FIIL));
        kokTablosu.put(yabanciKokler.get(1), kokler.kokBul("gösteri", KelimeTipi.ISIM));
        kokTablosu.put(yabanciKokler.get(2), kokler.kokBul("biçem", KelimeTipi.ISIM));

        // yabanci cozumleyici
        Sozluk yabanciSozluk = new AgacSozluk(yabanciKokler, dilBilgisi.alfabe(), dilBilgisi.kokOzelDurumlari());
        KelimeCozumleyici cozumleyici = new StandartCozumleyici(
                yabanciSozluk.getKokBulucuFactory().getKesinKokBulucu(),
                new KesinHDKiyaslayici(),
                dilBilgisi.alfabe(),
                dilBilgisi.ekler(),
                dilBilgisi.cozumlemeYardimcisi());

        // kelime uretici
        KelimeUretici kelimeUretici = new KelimeUretici(dilBilgisi.alfabe(), dilBilgisi.cozumlemeYardimcisi());

        // donusturucu
        KokDonusturucu donusturucu = new KokDonusturucu(cozumleyici, kelimeUretici, kokTablosu);

        String[] girisler = {"showumuzun", "fixleseydik", "formatlardan"};
        for (String s : girisler) {
            System.out.println("giris:" + s);
            System.out.println("oneri:" + donusturucu.donustur(s));
        }
    }


}
