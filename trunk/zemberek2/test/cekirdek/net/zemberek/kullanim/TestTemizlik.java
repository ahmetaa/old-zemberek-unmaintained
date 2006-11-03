package net.zemberek.kullanim;

import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;

public class TestTemizlik {

    private static Zemberek zemberek;

    public static void temizle(String str) {
        System.out.println("�nceki hali: " + str);
        System.out.println("Temizlenmi� hali: " + zemberek.temizle(str));
    }

    public static void main(String[] args) {
        zemberek = new Zemberek(new TurkiyeTurkcesi());
        String str = "Bu yazıyı windows cp-1254 isimli ucube kod sayfasını kullanarak "
                + "yazıyorum. Bakalım kod sayfası farklarından kaynaklanan "
                + " problemler için güzel bir örnek oluşturabilecek mi?";
        temizle(str);
    }

}
