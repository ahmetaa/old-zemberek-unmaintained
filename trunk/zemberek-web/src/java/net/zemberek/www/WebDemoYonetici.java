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
 *  The Original Code is "Zemberek Web"
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akin, Mehmet D. Akin.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *   Serkan Kaba
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberek.www;

import net.zemberek.araclar.turkce.YaziBirimi;
import net.zemberek.araclar.turkce.YaziBirimiTipi;
import net.zemberek.araclar.turkce.YaziIsleyici;
import net.zemberek.erisim.Zemberek;
import net.zemberek.islemler.TurkceYaziTesti;
import net.zemberek.yapi.*;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import java.util.*;

public final class WebDemoYonetici {

    TurkiyeTurkcesi dil = new TurkiyeTurkcesi();
    Zemberek zemberek = new Zemberek(dil);
    static WebDemoYonetici yonetici = new WebDemoYonetici();
    Random random = new Random();

    public TurkiyeTurkcesi getDil() {
        return dil;
    }

    public Zemberek getZemberek() {
        return zemberek;
    }

    private WebDemoYonetici() {
    }

    public static WebDemoYonetici getRef() {
        return yonetici;
    }

    public String islemUygula(String islemTipi, String giris) {
        if (islemTipi.equals("YAZI_DENETLE"))
            return yaziDenetle(giris);
        if (islemTipi.equals("YAZI_COZUMLE"))
            return yaziCozumle(giris);
        if (islemTipi.equals("ASCII_TURKCE"))
            return asciiToTurkce(giris);
        if (islemTipi.equals("TURKCE_ASCII"))
            return turkceToAscii(giris);
        if (islemTipi.equals("HECELE"))
            return hecele(giris);
        if (islemTipi.equals("ONER"))
            return oner(giris);
        if (islemTipi.equals("SACMALA"))
            return sacmala();
        return "";
    }
       
    public String sacmala() {
        Sacmalayici r = new Sacmalayici();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 30; i++) {
            buffer.append(r.rastgeleKelimeOlustur(r.getRastgeleKok(KelimeTipi.SIFAT), 1)).append(" ");
            buffer.append(r.rastgeleKelimeOlustur(r.getRastgeleKok(KelimeTipi.ISIM), random.nextInt(3) + 1)).append(" ");
            buffer.append(r.rastgeleKelimeOlustur(r.getRastgeleKok(KelimeTipi.FIIL), random.nextInt(3) + 1));
            buffer.append(". ");
        }
        return buffer.toString();
    }

    public String yaziDenetle(String giris) {
        List analizDizisi = YaziIsleyici.analizDizisiOlustur(giris);
        StringBuffer sonuc = new StringBuffer();
        for (int i = 0; i < analizDizisi.size(); i++) {
            YaziBirimi birim = (YaziBirimi) analizDizisi.get(i);
            if (birim.tip == YaziBirimiTipi.KELIME) {
                if (zemberek.kelimeDenetle(birim.icerik) == false)
                    sonuc.append("<font color=\"#FF0033\">").append(birim.icerik).append("</font>");
                else
                    sonuc.append(birim.icerik);
            } else
                sonuc.append(birim.icerik);
        }
        return sonuc.toString();
    }

    public String yaziCozumle(String giris) {
        List analizDizisi = YaziIsleyici.analizDizisiOlustur(giris);
        StringBuffer sonuc = new StringBuffer();
        for (int i = 0; i < analizDizisi.size(); i++) {
            YaziBirimi birim = (YaziBirimi) analizDizisi.get(i);
            if (birim.tip == YaziBirimiTipi.KELIME) {
                Kelime[] cozumler = zemberek.kelimeCozumle(birim.icerik);
                if (cozumler.length == 0)
                    sonuc.append(birim.icerik).append(" :cozulemedi<br></br>");
                else {
                    sonuc.append(birim.icerik).append(": <br></br>");
                    for (Kelime kelime : cozumler) {
                        sonuc.append("[ Kok:").append(kelime.kok().icerik()).append(", ");
                        sonuc.append(" Tip:").append(kelime.kok().tip()).append(" | ");
                        sonuc.append(" Ekler:").append(kelime.ekZinciriStr()).append("] ");
                        sonuc.append("<br></br>");
                    }

                }
            }
        }
        return sonuc.toString();
    }


    class KokFrekansKiyaslayici implements Comparator<Kelime> {

        public int compare(Kelime o1, Kelime o2) {
            Kok k1 = o1.kok();
            Kok k2 = o2.kok();
            return k1.getFrekans() - k2.getFrekans();
        }
    }

    public String asciiToTurkce(String giris) {
        List analizDizisi = YaziIsleyici.analizDizisiOlustur(giris);
        StringBuffer sonuc = new StringBuffer();
        for (int i = 0; i < analizDizisi.size(); i++) {
            YaziBirimi birim = (YaziBirimi) analizDizisi.get(i);
            if (birim.tip == YaziBirimiTipi.KELIME) {
                boolean buyukHarf = Character.isUpperCase(birim.icerik.charAt(0));
                String[] sonuclar = zemberek.asciidenTurkceye(birim.icerik);
                if (sonuclar.length == 0)
                    birim.icerik = "<font color=\"#FF0033\">" + birim.icerik + "</font>";
                else if (sonuclar.length == 1) {
                    birim.icerik = sonuclar[0];
                    if (buyukHarf) {
                        birim.icerik = Character.toUpperCase(birim.icerik.charAt(0)) +
                                birim.icerik.substring(1);
                    }
                } else {
                    birim.icerik = "<font color=\"#33AA33\">" + sonuclar[0]  + "</font>";
                }
            }
            sonuc.append(birim.icerik);
        }
        return sonuc.toString();
    }


    public String turkceToAscii(String giris) {
        List analizDizisi = YaziIsleyici.analizDizisiOlustur(giris);
        StringBuffer sonuc = new StringBuffer();
        for (int i = 0; i < analizDizisi.size(); i++) {
            YaziBirimi birim = (YaziBirimi) analizDizisi.get(i);
            if (birim.tip == YaziBirimiTipi.KELIME)
                birim.icerik = zemberek.asciiyeDonustur(birim.icerik);
            sonuc.append(birim.icerik);
        }
        return sonuc.toString();
    }

    public String hecele(String giris) {
        List analizDizisi = YaziIsleyici.analizDizisiOlustur(giris);
        StringBuffer sonuc = new StringBuffer();
        for (int i = 0; i < analizDizisi.size(); i++) {
            YaziBirimi birim = (YaziBirimi) analizDizisi.get(i);
            if (birim.tip == YaziBirimiTipi.KELIME) {
                birim.icerik = zemberek.dilBilgisi().alfabe().ayikla(birim.icerik);
                if (zemberek.dilBilgisi().alfabe().cozumlemeyeUygunMu(birim.icerik) == false)
                    birim.icerik = "<font color=\"#FF0033\">" + birim.icerik + "</font>";
                else {
                    String[] sonuclar = zemberek.hecele(birim.icerik);
                    if (sonuclar.length == 0)
                        birim.icerik = "<font color=\"#FF0033\">" + birim.icerik + "</font>";
                    else {
                        StringBuffer bfr = new StringBuffer("[");
                        for (int j = 0; j < sonuclar.length - 1; j++)
                            bfr.append(sonuclar[j]).append("-");
                        bfr.append(sonuclar[sonuclar.length - 1]).append("]");
                        birim.icerik = bfr.toString();
                    }
                }
            }
            sonuc.append(birim.icerik);
        }
        return sonuc.toString();
    }

    public String oner(String giris) {
        List analizDizisi = YaziIsleyici.analizDizisiOlustur(giris);
        StringBuffer sonuc = new StringBuffer();
        for (int i = 0; i < analizDizisi.size(); i++) {
            YaziBirimi birim = (YaziBirimi) analizDizisi.get(i);
            if (birim.tip == YaziBirimiTipi.KELIME) {
                String[] cozumler = zemberek.oner(birim.icerik);
                if (cozumler.length == 0)
                    birim.icerik = "<font color=\"#FF0033\">" + birim.icerik + "</font>";
                else if (cozumler.length == 1)
                    birim.icerik = (cozumler[0]).toString();
                else {
                    StringBuffer bfr = new StringBuffer("[ ");
                    for (int j = 0; j < cozumler.length; j++) {
                        bfr.append(cozumler[j]);
                        if (j < cozumler.length - 1)
                            bfr.append(", ");
                    }
                    bfr.append("]");
                    birim.icerik = bfr.toString();
                }
            }
            sonuc.append(birim.icerik);
        }
        return sonuc.toString();
    }
    
    public String turkceTest(String giris) {
        int sonuc = zemberek.dilTesti(giris);
        if (sonuc == TurkceYaziTesti.HIC)
            return "Turkce degil";
        if (sonuc == TurkceYaziTesti.AZ)
            return "Yazi Turkce degil ama turkce olabilecek kelimeler iceriyor";
        if (sonuc == TurkceYaziTesti.ORTA)
            return "Turkce. Cok miktarda yanlis yazilmis ya da yabanci kelime iceriyor ";
        if (sonuc == TurkceYaziTesti.YUKSEK)
            return "Turkce. Yanlis yazilmis ya da yabanci kelimeler iceriyor";
        return "Turkce";
    }
}
