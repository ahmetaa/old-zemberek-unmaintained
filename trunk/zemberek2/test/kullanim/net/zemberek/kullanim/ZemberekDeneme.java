/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.kullanim;

import static java.lang.System.out;

import java.util.Arrays;
import java.util.List;

import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.KelimeTipi;

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
        List<String[]> ayrisimlar = zemberek.kelimeAyristir(giris);
        for (String[] strings : ayrisimlar)
            out.println(Arrays.toString(strings));

        // kelime uretme.
        //kedi'yi koyun ile degistirelim.. koyun kokunu Kok kok = new Kok("koyun", KelimeTipi.ISIM);
        //seklinde olusturabilirsik, ama sistemden almak daha dogru
        Kelime kelime = cozumler[0];
        List<Kok> kokler = zemberek.dilBilgisi().kokler().kokBul("on");
        System.out.println("kokler = " + kokler);
        Kok kok = zemberek.dilBilgisi().kokler().kokBul("koyun", KelimeTipi.ISIM);
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
