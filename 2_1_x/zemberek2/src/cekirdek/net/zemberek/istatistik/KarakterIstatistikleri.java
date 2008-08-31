/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

/*
 * Created on 09.Eki.2004
 */
package net.zemberek.istatistik;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.zemberek.araclar.IstatistikAraclari;

/**
 * @author MDA & GBA
 */
public class KarakterIstatistikleri {
    private int karakterSayisi = 0;
    private int harfSayisi = 0;
    private int kelimeSayisi = 0;
    private int cumleSayisi = 0;
    private int rakamSayisi = 0;
    private int boslukSayisi = 0;
    private int virgulSayisi = 0;
    private int noktaSayisi = 0;
    private int unlemSayisi = 0;
    private int ikinoktaSayisi = 0;
    private int noktaliVirgulSayisi;
    private int buyukharfSayisi;
    private int[] harfDagilimi = new int[500];
    private int[] rakamDagilimi = new int[10];

    private boolean kelimeBasi = false;
    private boolean cumleBasi = false;

    private double ortalamaKelimeBoyu = 0.0;
    private double ortalamaCumleBoyu = 0.0;

    public void processChar(char ch) {
        karakterSayisi++;
        // Gelen karakter harf mi?
        if (Character.isLetter(ch)) {
            if (Character.isUpperCase(ch)) {
                buyukharfSayisi++;
            }
            kelimeBasi = true;
            cumleBasi = true;
            harfSayisi++;
            harfDagilimi[(int) ch]++;
        } else if (Character.isWhitespace(ch)) {
            boslukSayisi++;
            if (kelimeBasi) {
                kelimeSayisi++;
            }
            kelimeBasi = false;
        } else if (ch == '.' || ch == '!' || ch == '?') {
            noktaSayisi++;
            if (cumleBasi) {
                cumleSayisi++;
            }
            cumleBasi = false;
        } else if (Character.isDigit(ch)) {
            rakamSayisi++;
            rakamDagilimi[Character.digit(ch, 10)]++;
        }
    }

    public void tamamla() {
        if (kelimeSayisi > 0)
            ortalamaKelimeBoyu = (double) harfSayisi / kelimeSayisi;
        if (cumleSayisi > 0)
            ortalamaCumleBoyu = (double) kelimeSayisi / cumleSayisi;
    }

    public String toString() {
        String str =
                "\nKarakter Sayisi     : " + karakterSayisi
                + "\nHarf Sayisi         : " + harfSayisi
                + "\nKelime Sayisi       : " + kelimeSayisi
                + "\nCumle Sayisi        : " + cumleSayisi
                + "\nBuyuk harf sayisi   : " + buyukharfSayisi
                + "\nBoşluk sayısı       : " + boslukSayisi
                + "\nNokta Sayisi        : " + noktaSayisi
                + "\nRakam Sayisi        : " + rakamSayisi
                + "\nOrtalama Kelime uz. : " + ortalamaKelimeBoyu
                + "\nOrtalama Cümle uz.  : " + ortalamaCumleBoyu
                ;

        long toplamHarf = 0;
        for (int miktar : harfDagilimi)
            toplamHarf += miktar;
        
        str += "\n\nHarf Dagilimi";

        List<CharFreq> list = new ArrayList<CharFreq>();
        for (int i = 0; i < harfDagilimi.length; i++) {
            if( harfDagilimi[i] >0 )
              list.add(new CharFreq((char) i, harfDagilimi[i]));
        }
        Collections.sort(list);

        for (CharFreq charFreq : list) {
            str = str +"\nHarf " + charFreq.c + ": " + charFreq.i
                + " %" + IstatistikAraclari.yuzdeHesaplaStr(charFreq.i , toplamHarf);

        }

        str+="\n";
        return str;
    }

    private class CharFreq implements Comparable<CharFreq> {
        char c;
        int i;

        private CharFreq(char c, int i) {
            this.c = c;
            this.i = i;
        }

        public int compareTo(CharFreq charFreq) {
            if (i < charFreq.i) return 1;
            else if (i > charFreq.i) return -1;
            return 0;
        }
    }


}
