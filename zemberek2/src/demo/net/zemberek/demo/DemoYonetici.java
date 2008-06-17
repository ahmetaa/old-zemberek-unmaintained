/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.demo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import net.zemberek.araclar.Kayitci;
import net.zemberek.araclar.turkce.YaziBirimi;
import net.zemberek.araclar.turkce.YaziBirimiTipi;
import net.zemberek.araclar.turkce.YaziIsleyici;
import net.zemberek.erisim.Zemberek;
import net.zemberek.yapi.DilBilgisi;
import net.zemberek.yapi.Kelime;

/**
 */
public class DemoYonetici {

    private static Logger logger = Kayitci.kayitciUret(DemoYonetici.class);
    private Zemberek zemberek;
    private DilBilgisi dilBilgisi;
    private Map<TurkDiliTuru, Zemberek> zemberekler = new HashMap<TurkDiliTuru, Zemberek>();


    public DemoYonetici() {
        try {
            dilSec(TurkDiliTuru.TURKIYE);
        } catch (ClassNotFoundException e) {
            logger.warning("ilgili dil ayarlari sinifi " + TurkDiliTuru.TURKIYE.sinif() + " icin bulunamiyor..");
        }
    }

    public void dilSec(TurkDiliTuru dilTuru) throws ClassNotFoundException {
        if (zemberekler.get(dilTuru) == null) {
            zemberekler.put(dilTuru, dilTuru.zemberekUret());
            this.zemberek = zemberekler.get(dilTuru);
            this.dilBilgisi = zemberek.dilBilgisi();
        }
    }

    public char[] ozelKarakterDizisiGetir() {
        return dilBilgisi.alfabe().asciiDisiHarfler();
    }

    public String islemUygula(String islemTipi, String giris) {

        IslemTipi islem;
        try {
            islem = IslemTipi.valueOf(islemTipi);
            return islemUygula(islem, giris);
        } catch (IllegalArgumentException e) {
            logger.severe("istenilen islem:" + islemTipi + " mevcut degil");
            return "";
        }
    }

    public String islemUygula(IslemTipi islemTipi, String giris) {
        switch (islemTipi) {
            case YAZI_DENETLE:
                return yaziDenetle(giris);
            case YAZI_COZUMLE:
                return yaziCozumle(giris);
            case ASCII_TURKCE:
                return asciiToTurkceTahmin(giris);
            case TURKCE_ASCII:
                return turkceToAscii(giris);
            case HECELE:
                return hecele(giris);
            case ONER:
                return oner(giris);
            default:
                return "";
        }
    }

    public String yaziDenetle(String giris) {
        List<YaziBirimi> analizDizisi = YaziIsleyici.analizDizisiOlustur(giris);
        StringBuffer sonuc = new StringBuffer();
        for (YaziBirimi birim : analizDizisi) {
            if (birim.tip == YaziBirimiTipi.KELIME) {
                if (!zemberek.kelimeDenetle(birim.icerik))
                    birim.icerik = "#" + birim.icerik;
            }
            sonuc.append(birim.icerik);
        }
        return sonuc.toString();
    }

    public String yaziCozumle(String giris) {
        List<YaziBirimi> analizDizisi = YaziIsleyici.analizDizisiOlustur(giris);
        StringBuffer sonuc = new StringBuffer();
        for (YaziBirimi birim : analizDizisi) {
            if (birim.tip == YaziBirimiTipi.KELIME) {
                Kelime[] cozumler = zemberek.kelimeCozumle(birim.icerik);
                sonuc.append(birim.icerik).append('\n');
                if (cozumler.length == 0)
                    sonuc.append(" :cozulemedi\n");
                else {
                    for (Kelime cozum : cozumler)
                        sonuc.append(cozum).append("\n");
                }
            }
        }
        return sonuc.toString();
    }


    public String asciiToTurkce(String giris) {
        List<YaziBirimi> analizDizisi = YaziIsleyici.analizDizisiOlustur(giris);
        StringBuffer sonuc = new StringBuffer();
        for (YaziBirimi birim : analizDizisi) {
            if (birim.tip == YaziBirimiTipi.KELIME) {
                Kelime[] sonuclar = zemberek.asciiCozumle(birim.icerik);
                Set<String> tekilSonuclar = new HashSet<String>(2);
                for (Kelime s : sonuclar) {
                    tekilSonuclar.add(s.icerik().toString());
                }

                if (tekilSonuclar.size() == 0)
                    birim.icerik = "#" + birim.icerik;
                else if (tekilSonuclar.size() == 1)
                    birim.icerik = tekilSonuclar.iterator().next();
                else {
                    StringBuffer bfr = new StringBuffer("[ ");
                    for (String aTekilSonuclar : tekilSonuclar) {
                        bfr.append(aTekilSonuclar).append(" ");
                    }
                    bfr.append("]");
                    birim.icerik = bfr.toString();
                }
            }
            sonuc.append(birim.icerik);
        }
        return sonuc.toString();
    }


    public String asciiToTurkceTahmin(String giris) {
        List<YaziBirimi> analizDizisi = YaziIsleyici.analizDizisiOlustur(giris);
        StringBuffer sonuc = new StringBuffer();
        for (YaziBirimi birim : analizDizisi) {
            if (birim.tip == YaziBirimiTipi.KELIME) {
                Kelime[] sonuclar = zemberek.asciiCozumle(birim.icerik);
                if (sonuclar.length > 0)
                    sonuc.append(applyCase(birim.icerik, sonuclar[0].icerik().toString()));
                else
                    sonuc.append(birim.icerik);
            } else
                sonuc.append(birim.icerik);
        }
        return sonuc.toString();
    }

    private String applyCase(String caseStr, String source) {
        char[] chrs = caseStr.toCharArray();
        char[] chrz = source.toCharArray();
        for (int i = 0; i < chrs.length; i++) {
            if (Character.isUpperCase(chrs[i]))
                chrz[i] = dilBilgisi.alfabe().buyukHarf(chrz[i]).charDeger();
        }
        return new String(chrz);
    }


    public String turkceToAscii(String giris) {
        List<YaziBirimi> analizDizisi = YaziIsleyici.analizDizisiOlustur(giris);
        StringBuffer sonuc = new StringBuffer();
        for (YaziBirimi anAnalizDizisi : analizDizisi) {
            YaziBirimi birim =  anAnalizDizisi;
            if (birim.tip == YaziBirimiTipi.KELIME)
                birim.icerik = zemberek.asciiyeDonustur(birim.icerik);
            sonuc.append(birim.icerik);
        }
        return sonuc.toString();
    }

    public String hecele(String giris) {
        List<YaziBirimi> analizDizisi = YaziIsleyici.analizDizisiOlustur(giris);
        StringBuffer sonuc = new StringBuffer();
        for (YaziBirimi anAnalizDizisi : analizDizisi) {
            YaziBirimi birim = anAnalizDizisi;
            if (birim.tip == YaziBirimiTipi.KELIME) {
                birim.icerik = dilBilgisi.alfabe().ayikla(birim.icerik);
                if (!dilBilgisi.alfabe().cozumlemeyeUygunMu(birim.icerik))
                    birim.icerik = "#" + birim.icerik;
                else {
                    String[] sonuclar = zemberek.hecele(birim.icerik);
                    if (sonuclar.length == 0)
                        birim.icerik = "#" + birim.icerik;
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
        List<YaziBirimi> analizDizisi = YaziIsleyici.analizDizisiOlustur(giris);
        StringBuffer sonuc = new StringBuffer();
        for (YaziBirimi birim : analizDizisi) {
            if (birim.tip == YaziBirimiTipi.KELIME) {
                String[] cozumler = zemberek.oner(birim.icerik);
                if (cozumler.length == 0)
                    birim.icerik = "#" + birim.icerik;
                else if (cozumler.length == 1)
                    birim.icerik = cozumler[0];
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
}
