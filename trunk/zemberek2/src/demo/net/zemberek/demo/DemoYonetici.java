/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.demo;

import java.util.*;
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
                if (!zemberek.kelimeDenetle(birim.icerik)) {
                    sonuc.append(hataliKelimeBicimle(birim.icerik));
                } else sonuc.append(birim.icerik);
            } else sonuc.append(kelimeHariciBicimle(birim.icerik));
        }
        return sonuc.toString();
    }

    public String yaziCozumle(String giris) {
        List<YaziBirimi> analizDizisi = YaziIsleyici.analizDizisiOlustur(giris);
        StringBuffer sonuc = new StringBuffer();
        for (YaziBirimi birim : analizDizisi) {
            if (birim.tip == YaziBirimiTipi.KELIME) {
                Kelime[] cozumler = zemberek.kelimeCozumle(birim.icerik);
                sonuc.append(birim.icerik).append("<br>");
                if (cozumler.length == 0)
                    sonuc.append(" :cozulemedi<br>");
                else {
                    for (Kelime cozum : cozumler)
                        sonuc.append(cozum).append("<br>");
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
                    sonuc.append(hataliKelimeBicimle(birim.icerik));
                else if (tekilSonuclar.size() == 1)
                    sonuc.append(tekilSonuclar.iterator().next());
                else {
                    sonuc.append(koseliParantezStringDiziBicimle(tekilSonuclar, " "));
                }
            } else sonuc.append(kelimeHariciBicimle(birim.icerik));
        }
        return sonuc.toString();
    }

    private String kelimeHariciBicimle(String str) {
        if (str.equals("\n"))
            return "<br>";
        else
            return str;
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
            } else sonuc.append(kelimeHariciBicimle(birim.icerik));
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
        for (YaziBirimi birim : analizDizisi) {
            if (birim.tip == YaziBirimiTipi.KELIME)
                sonuc.append(zemberek.asciiyeDonustur(birim.icerik));
            else sonuc.append(kelimeHariciBicimle(birim.icerik));
        }
        return sonuc.toString();
    }

    public String hecele(String giris) {
        List<YaziBirimi> analizDizisi = YaziIsleyici.analizDizisiOlustur(giris);
        StringBuffer sonuc = new StringBuffer();
        for (YaziBirimi birim : analizDizisi) {
            if (birim.tip == YaziBirimiTipi.KELIME) {
                birim.icerik = dilBilgisi.alfabe().ayikla(birim.icerik);
                if (!dilBilgisi.alfabe().cozumlemeyeUygunMu(birim.icerik))
                    sonuc.append(hataliKelimeBicimle(birim.icerik));
                else {
                    String[] sonuclar = zemberek.hecele(birim.icerik);
                    if (sonuclar.length == 0)
                        sonuc.append(hataliKelimeBicimle(birim.icerik));
                    else {
                        sonuc.append(koseliParantezStringDiziBicimle(sonuclar, "-"));
                    }
                }
            } else
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
                    sonuc.append(hataliKelimeBicimle(birim.icerik));
                else if (cozumler.length == 1)
                    sonuc.append(cozumler[0]);
                else {
                    sonuc.append(koseliParantezStringDiziBicimle(cozumler, ", "));
                }
            } else
                sonuc.append(birim.icerik);
        }
        return sonuc.toString();
    }

    private String koseliParantezStringDiziBicimle(String[] cozumler, String ayrac) {
        StringBuilder bfr = new StringBuilder("[");
        for (int j = 0; j < cozumler.length; j++) {
            bfr.append(cozumler[j]);
            if (j < cozumler.length - 1)
                bfr.append(ayrac);
        }
        bfr.append("]");
        return bfr.toString();
    }

    private String koseliParantezStringDiziBicimle(Collection<String> cozumler, String ayrac) {
        return koseliParantezStringDiziBicimle(cozumler.toArray(new String[cozumler.size()]), ayrac);
    }

    private String hataliKelimeBicimle(String kelime) {
        return "<font color=\"#FF0033\">" + kelime + "</font>";
    }
}
