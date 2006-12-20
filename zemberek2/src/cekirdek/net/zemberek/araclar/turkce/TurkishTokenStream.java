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

/*
 * Created on 15.Mar.2004
 */
package net.zemberek.araclar.turkce;

import net.zemberek.istatistik.Istatistikler;

import java.io.*;

/**
 * TurkishTokenStream
 * Verilen bir doayadan veya herhangi bir stream'dan Türkce kelimeleri
 * sirayla almak için kullanilir. İki constructor'u vardır, istenirse verilen bir
 * dosyayi istenirse de herhangi bir inputstream'ı isleyebilir.
 * Biraz optimizasyona ihtiyaci var ,ama corpus.txt deki tüm kelimeleri tek tek
 * nextWord() ile cekmek yaklasik 0.8 saniye aldi. (Athlon 900)
 *
 * @author MDA & GBA
 */
public class TurkishTokenStream {
    BufferedReader bis = null;
    Istatistikler statistics = null;
    char[] buffer = new char[1000];
    int index = 0;

    /**
     * Dosyadan kelime okuyan TurkishTokenStream oluşturur
     *
     * @param fileName
     * @param encoding: default için null verin
     */
    public TurkishTokenStream(String fileName, String encoding) {
        try {
            FileInputStream fis = new FileInputStream(new File(fileName));
            setupReader(fis, encoding);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Herhangibir input Streaminden'den kelime okuyan TurkishTokenStream oluşturur.
     *
     * @param is
     * @param encoding : default için null verin
     */
    public TurkishTokenStream(InputStream is, String encoding) {
        setupReader(is, encoding);
    }

    private void setupReader(InputStream is, String encoding) {
        if (encoding == null)
            bis = new BufferedReader(new InputStreamReader(is));
        else
            try {
                bis = new BufferedReader(new InputStreamReader(is, encoding));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
    }

    public static int MAX_KELIME_BOY = 256;
    private char[] kelimeBuffer = new char[MAX_KELIME_BOY];

    /**
     * Metindeki veya stream'deki bir sonraki kelimeyi getirir
     * - Büyük harfleri küçültür
     * - Noktalama işaretlerini yutar.
     *
     * @return Sonraki kelime, eğer kelime kalmamışsa null
     */
    public String nextWord() {
        char ch;
        int readChar;
        boolean kelimeBasladi = false;
        int kelimeIndex = 0;
        boolean hypen = false;
        try {
            while ((readChar = bis.read()) != -1) {
                ch = (char) readChar;
                if (statistics != null) {
                    statistics.processChar(ch);
                }
                if (ch == '-') {
                    hypen = true;
                    continue;
                }
                if (Character.isLetter(ch) || (kelimeBasladi == true & ( ch == '\'' || ch == '-')) ) {
                    kelimeBasladi = true;
                    hypen = false;
                    if (kelimeIndex < MAX_KELIME_BOY)
                        kelimeBuffer[kelimeIndex++] = ch;
                    else
                        System.out.println("maksimum kelime boyu aşıldı. " + ch);
                    continue;
                }

                if (Character.isWhitespace(ch)) {
                    if (hypen) continue;

                    if (kelimeBasladi) {
                        return new String(kelimeBuffer, 0, kelimeIndex);
                    }
                    kelimeBasladi = false;
                    kelimeIndex = 0;
                    continue;
                } 
            }
            
            // Tüm karakterler bitti, son kalan kelime varsa onu da getir.
            if (kelimeBasladi) {
                return new String(kelimeBuffer, 0, kelimeIndex);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static int MAX_CUMLE_BOY = 4000;
    private char[] cumleBuffer = new char[MAX_CUMLE_BOY];

    /**
     * Metindeki veya stream'deki bir sonraki cümleyi getirir
     * @return Sonraki cümle, eğer kalmamışsa null
     */
    public String nextSentence() {
        char ch;
        int readChar;
        boolean cumleBasladi = false;
        int cumleIndex = 0;
        try {
            // TODO: bir char buffer'e toptan okuyup islemek hız kazandirir mi? (sanmam)
            while ((readChar = bis.read()) != -1) {
                ch = (char) readChar;

                //System.out.println("Char: "+ ch);
                if (Character.isLetter(ch) || ch == '\'' || ch == '-' ) {
                    cumleBasladi = true;
//                    switch (ch) {
//                        case 'I':
//                            ch = '\u0131';
//                            break; // dotless small i
//                            // Buraya sapkalı a vs. gibi karakter donusumlari de eklenebilir.
//                        default  :
//                            ch = Character.toLowerCase(ch);
//                    }
                    if (cumleIndex < MAX_CUMLE_BOY)
                        cumleBuffer[cumleIndex++] = ch;
                    else
                        System.out.println("Lagim tasti " + ch);
                    continue;
                }

                // harfimiz bir cumle sinirlayici
                if (isSentenceDelimiter(ch)) {
                    if (cumleBasladi) {
                        return new String(cumleBuffer, 0, cumleIndex);
                    }
                    continue;
                }

                if (cumleIndex < MAX_CUMLE_BOY)
                    cumleBuffer[cumleIndex++] = ch;
                else {
                    System.out.println("Lagim tasti " + ch);
                    return null;
                }

            }
            
            // Tüm karakterler bitti, son kalan kelime varsa onu da getir.
            if (cumleBasladi) {
                return new String(kelimeBuffer, 0, cumleIndex);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public char harfIsle(char chIn) {
        char ch;
        switch (chIn) {
            case 'I':
                ch = '\u0131';
                break; // dotless small i
                // Buraya sapkalı a vs. gibi karakter donusumlari de eklenebilir.
            default  :
                ch = Character.toLowerCase(chIn);
        }
        return ch;
    }

    public boolean isSentenceDelimiter(char ch) {
        if (ch == '.' ||
                ch == ':' ||
                ch == '!' ||
                ch == '?'
        )
            return true;
        return false;
    }
    
    public void setStatistics(Istatistikler statistics) {
        this.statistics = statistics;
    }
}