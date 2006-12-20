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

import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.Kok;

import static java.lang.System.out;
import java.util.Arrays;
import java.util.List;

public class ZemberekDeneme {

    public static void main(String[] args) {

        // bir adet Zemberek Nesnesi olusturalim.
        Zemberek zemberek = new Zemberek(new TurkiyeTurkcesi());

        String giris = "kedilerim";
        out.println("Giris:" + giris);

        // denetleme
        if (!zemberek.kelimeDenetle(giris)) {
            out.println(giris + " kelimesi dogru yazilmamis");
            System.exit(1);
        }
        out.println(giris + " kelimesi dogru yazilmis.\n");

        // cozumleme
        Kelime[] cozumler = zemberek.kelimeCozumle(giris);
        out.println("cozumlemeler:");
        for (Kelime kelime : cozumler)
            System.out.println(kelime);

        //ayristirma
        out.println("\nayristirma sonuclari:");
        List<List<String>> ayrisimlar = zemberek.kelimeAyristir(giris);
        for (List<String> strings : ayrisimlar)
            out.println(strings);

        // kelime uretme.
        //kedi'yi koyun ile degistirelim.. koyun kokunu Kok kok = new Kok("koyun", KelimeTipi.ISIM);
        //seklinde olusturabilirsik, ama sistemden almak daha dogru
        Kelime kelime = cozumler[0];
        Kok kok = (Kok) zemberek.dilBilgisi().kokler().kokBul("koyun").get(0);
        String yeni = zemberek.kelimeUret(kok, kelime.ekler());
        out.println("\nkok degisimi sonrasi yeni kelime: " + yeni);

        //ascii donusum cozumleme
        String asciiGiris = "koyun";
        out.println('\n' + asciiGiris + " icin ascii ayristirma sonuclari:");
        Kelime[] asciiCozumler = zemberek.asciiCozumle(asciiGiris);
        for (Kelime kelime1 : asciiCozumler)
            System.out.println("olasi cozum: " + kelime1);

        //ascii donusum islemini dogrudan String[] donecek sekilde de kullanabiliriz.
        out.println("\n 'koyun' icin ascii donusum sonuclari:");
        String[] sonuclar = zemberek.asciidenTurkceye("koyun");
        for (String s : sonuclar)
            System.out.println("olasi cozum: " + s);

        //heceleme.
        String[] heceler = zemberek.hecele(giris);
        out.println("\nheceleme sonucu:" + Arrays.toString(heceler));
    }
}
