/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.islemler;

import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.TurkceHarf;

/**
 */
public class AsciiDonusturucu {
    Alfabe alfabe;

    public AsciiDonusturucu(Alfabe alfabe) {
        this.alfabe = alfabe;
    }

    public String toAscii(String giris) {
        char[] chars = giris.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            TurkceHarf harf = alfabe.harf(chars[i]);
            if (harf != null && harf != Alfabe.TANIMSIZ_HARF)
                if (harf.asciiDonusum() != null)
                    chars[i] = harf.asciiDonusum().charDeger();
        }
        return String.valueOf(chars);
    }
}
