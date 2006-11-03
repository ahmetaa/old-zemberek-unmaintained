package net.zemberek.kullanim;

import net.zemberek.erisim.Zemberek;
import net.zemberek.islemler.TurkceYaziTesti;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;

public class TestTurkceMi {

    private static Zemberek zemberek;

    public static void turkceMi(String str) {
        System.out.print("Türkçe testi yapýlan metin: " + str + " Sonuç : ");
        int sonuc = zemberek.dilTesti(str);
        switch (sonuc) {
            case TurkceYaziTesti.KESIN:
                System.out.println("Türkçe");
                break;
            case TurkceYaziTesti.YUKSEK:
                System.out.println("Yüksek oranda Türkçe");
                break;
            case TurkceYaziTesti.ORTA:
                System.out.println("Kýsmen Türkçe");
                break;
            case TurkceYaziTesti.AZ:
                System.out.println("Çok az Türkçe kelime içeriyor");
                break;
            case TurkceYaziTesti.HIC:
                System.out.println("Türkçe deðil");
                break;
        }
    }

    public static void main(String[] args) {
        zemberek = new Zemberek(new TurkiyeTurkcesi());
        turkceMi("Bugün çok neþeliyim, hayat çok güzel");
        turkceMi("Abi yaptýðýmýz son commitler nasty regresyonlara yol açmýþ. we are doomed yani.");
        turkceMi("Bugun cok neseliyim, hayat cok guzel");
        turkceMi("Obviously Java kicks some serious butts nowadays.");
    }
}
