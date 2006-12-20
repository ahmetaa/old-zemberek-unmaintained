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

package net.zemberek.araclar.turkce;

import java.io.*;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Metinler uzerinde ayristirma islemlerini kolaylastirmak icin yazilmis bir sinif. Static methodlari kullanarak
 * metin icerisindeki kelimelere, cumlelere ulasim sagliyor.
 */
public class YaziIsleyici {
    public static BreakIterator kelimeIt = BreakIterator.getWordInstance(new Locale("tr"));
    public static BreakIterator cumleIt = BreakIterator.getSentenceInstance(new Locale("tr"));

    /**
     * Verilen metni {@link java.text.BreakIterator} kullanarak kelimelerine ayirir. Noktalama isaretleri filtrelenir
     *
     * @param target
     * @return metin kelimeleri liste icerisinde String olarak dondurulur.
     */
    public static List kelimeAyikla(String target) {
        kelimeIt.setText(target);
        int start = kelimeIt.first();
        int end = kelimeIt.next();
        List kelimeList = new ArrayList();

        while (end != BreakIterator.DONE) {
            String word = target.substring(start, end);
            if (Character.isLetterOrDigit(word.charAt(0))) {
                kelimeList.add(word);
            }
            start = end;
            end = kelimeIt.next();
        }
        return kelimeList;
    }

    /**
     * @param target
     * @return
     */
    /*TODO: Please review if this method needs to be here
    */

    public static List analizIcinKelimeAyikla(String target) {
        List cumleler = cumleAyikla(target);
        List tumKelimeler = new ArrayList();
        for (int i = 0; i < cumleler.size(); i++) {
            String cumle = (String) cumleler.get(i);
            List kelimeler = kelimeAyikla(cumle);
            for (int j = 0; j < kelimeler.size(); j++) {
                String kelime = (String) kelimeler.get(j);
                if (Character.isLowerCase(kelime.charAt(0))) {
                    char[] chrs = kelime.toCharArray();
                    boolean nokta = false;
                    for (int k = 0; k < chrs.length; k++) {
                        if (chrs[k] == '.' || chrs[k] == '-')
                            nokta = true;
                    }
                    if (!nokta)
                        tumKelimeler.add(kelime);
                }
            }
        }
        return tumKelimeler;
    }

    public static List cumleAyikla(String target) {
        cumleIt.setText(target);
        int start = cumleIt.first();
        int end = cumleIt.next();
        List cumleList = new ArrayList();

        while (end != BreakIterator.DONE) {
            String cumle = target.substring(start, end);
            cumleList.add(cumle);
            start = end;
            end = cumleIt.next();
        }
        return cumleList;
    }

    /**
     * Verilen metnin icinde gecen kelimeler {@link YaziBirimi} listesi  halinde dondururlur.
     *
     * @param target
     * @return
     */
    public static List<YaziBirimi> analizDizisiOlustur(String target) {
        kelimeIt.setText(target);
        int start = kelimeIt.first();
        int end = kelimeIt.next();
        int boslukBasla = 0;

        List<YaziBirimi> yaziBirimleri = new ArrayList();

        while (end != BreakIterator.DONE) {
            String word = target.substring(start, end);
            if (Character.isLetterOrDigit(word.charAt(0))) {
                if (boslukBasla < start)
                    yaziBirimleri.add(new YaziBirimi(YaziBirimiTipi.DIGER, target.substring(boslukBasla, start)));
                yaziBirimleri.add(new YaziBirimi(YaziBirimiTipi.KELIME, word));
                boslukBasla = end;
            }
            start = end;
            end = kelimeIt.next();
        }

        if (boslukBasla < target.length())
            yaziBirimleri.add(new YaziBirimi(YaziBirimiTipi.DIGER, target.substring(boslukBasla, target.length())));

        return yaziBirimleri;
    }

    public static String yaziOkuyucu(String fileName) throws IOException {
        StringBuffer sb = new StringBuffer();
        FileInputStream fis = new FileInputStream(new File(fileName));
        BufferedReader bis = new BufferedReader(new InputStreamReader(fis, "ISO-8859-9"));
        String s;
        while ((s = bis.readLine()) != null) {
            sb.append(s);
            sb.append("\n");
        }
        bis.close();
        return sb.toString();
    }
}
