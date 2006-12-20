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

/*
 * Created on 09.Eki.2004
 */
package net.zemberek.istatistik;

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
        for (int i = 0; i < harfDagilimi.length; i++) toplamHarf+= harfDagilimi[i];
        
        str += "\n\nHarf Dagilimi";
        for (int i = 0; i < harfDagilimi.length; i++) {
            if (harfDagilimi[i] > 0){
                str = str +"\nHarf " + (char) i + ": " + harfDagilimi[i] 
                    + " %" + IstatistikAraclari.yuzdeHesaplaStr(harfDagilimi[i] , toplamHarf);
            }
        }

//        str += "\nRakam Dagilimi";
//        for (int i = 0; i < rakamDagilimi.length; i++) {
//            str += "\nRakam " + i + " : " + rakamDagilimi[i];
//        }
        str+="\n";
        return str;
    }


}
