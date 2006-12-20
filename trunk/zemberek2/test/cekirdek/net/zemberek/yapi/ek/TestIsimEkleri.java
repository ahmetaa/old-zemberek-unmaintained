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
 *  The Original Code is Zemberek Do?al Dil ??leme Kütüphanesi.
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Ak?n, Mehmet D. Ak?n.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberek.yapi.ek;

/**
 */
public class TestIsimEkleri extends BaseTestEkler {
 /*   public void testIsimCogul() {
        String[] strs = {"balon", "kale", "armut", "erik"};
        String[] gercekEkler = {"lar", "ler", "lar", "ler"};
        olusanEkKontrol(strs, gercekEkler, Ekler.ISIM_COGUL);
        assertTrue(Ekler.ISIM_COGUL.ilkHarfUygunmu(TurkceAlfabe.HARF_l));
    }

    public void testIsimHalE() {
        String[] strs = {"balon", "kale", "armut", "erik"};
        String[] gercekEkler = {"a", "ye", "a", "e"};
        olusanEkKontrol(strs, gercekEkler, Ekler.ISIM_HAL_E);
    }

    public void testIsimHalDe() {
        String[] strs = {"balon", "kale", "armut", "erik"};
        String[] gercekEkler = {"da", "de", "ta", "te"};
        olusanEkKontrol(strs, gercekEkler, Ekler.ISIM_HAL_DE);
    }

    public void testIsimHalI() {
        String[] strs = {"balon", "kale", "armut", "erik"};
        String[] gercekEkler = {"u", "yi", "u", "i"};
        olusanEkKontrol(strs, gercekEkler, Ekler.ISIM_HAL_I);
    }

    public void testIsimCegiz() {
        String[] strs = {"armut", "kalem"};
        String[] gercekEkler = {"ca\u011f\u0131z", "ce\u011fiz"};
        olusanEkKontrol(strs, gercekEkler, Ekler.ISIM_CEGIZ);
    }

    public void testIsimSahiplikBen() {
        String[] strs = {"sandalye", "kalem", "armut", "yast\u0131k"};
        String[] gercekEkler = {"m", "im", "um", "\u0131m"};
        olusanEkKontrol(strs, gercekEkler, Ekler.ISIM_SAHIPLIK_BEN);
    }

    public void testIsimSahiplikSen() {
        String[] strs = {"sandalye", "kalem", "armut", "yast\u0131k"};
        String[] gercekEkler = {"n", "in", "un", "\u0131n"};
        olusanEkKontrol(strs, gercekEkler, Ekler.ISIM_SAHIPLIK_SEN);
    }

    public void testIsimSahiplikO() {
        String[] strs = {"sandalye", "kalem", "armut", "yast\u0131k"};
        String[] gercekEkler = {"si", "i", "u", "\u0131"};
        olusanEkKontrol(strs, gercekEkler, Ekler.ISIM_SAHIPLIK_O);
    }

    public void testIsimSahiplikBiz() {
        String[] strs = {"sandalye", "kalem", "armut", "yast\u0131k"};
        String[] gercekEkler = {"miz", "imiz", "umuz", "\u0131m\u0131z"};
        olusanEkKontrol(strs, gercekEkler, Ekler.ISIM_SAHIPLIK_BIZ);
    }

    public void testIsimSahiplikSiz() {
        String[] strs = {"sandalye", "kalem", "armut", "yast\u0131k"};
        String[] gercekEkler = {"niz", "iniz", "unuz", "\u0131n\u0131z"};
        olusanEkKontrol(strs, gercekEkler, Ekler.ISIM_SAHIPLIK_SIZ);
    }


    public void testIsimKucultme() {
        Ek ek = Ekler.ISIM_KUCULTME;
        String[] strs = {"sandalye", "kalem", "armut", "yast\u0131k"};
        String[] gercekEkler = {"cik", "cik", "\u00e7uk", "\u00e7\u0131k"};
        olusanEkKontrol(strs, gercekEkler, ek);
        //ozel durum testi
        HarfDizisi giris = new HarfDizisi("kedici\u011fin");
        Kelime kelime = new Kelime(new Kok("kedi"));
        ek.ekOlustur(kelime, giris);
        String sonuc = ek.icerik().toString();
        assertEquals(sonuc, "ci\u011f");
        assertTrue(ek.ilkHarfUygunmu(TurkceAlfabe.HARF_c) && ek.ilkHarfUygunmu(TurkceAlfabe.HARF_ch));
    }

    public void testIsimLik() {
        Ek ek = Ekler.ISIM_LIK;
        String[] strs = {"sandalye", "kalem", "armut", "yast\u0131k"};
        String[] gercekEkler = {"lik", "lik", "luk", "l\u0131k"};
        olusanEkKontrol(strs, gercekEkler, ek);
        //ozel durum testi
        HarfDizisi giris = new HarfDizisi("kedili\u011fin");
        Kelime kelime = new Kelime(new Kok("kedi"));
        ek.ekOlustur(kelime);
        ek.icerik().sonHarfYumusat();
        String sonuc = ek.icerik().toString();
        assertEquals(sonuc, "li\u011f");
        assertTrue(ek.ilkHarfUygunmu(TurkceAlfabe.HARF_l));
    }

    public void testIsimDurumLuk() {
        Ek ek = Ekler.ISIM_DURUM_LUK;
        String[] strs = {"sandalye", "kalem", "armut", "yast\u0131k"};
        String[] gercekEkler = {"lik", "lik", "luk", "l\u0131k"};
        olusanEkKontrol(strs, gercekEkler, ek);
        //ozel durum testi
        HarfDizisi giris = new HarfDizisi("kedili\u011fin");
        Kelime kelime = new Kelime(new Kok("kedi"));
        ek.ekOlustur(kelime);
        ek.icerik().sonHarfYumusat();
        String sonuc = ek.icerik().toString();
        assertEquals(sonuc, "li\u011f");
        assertTrue(ek.ilkHarfUygunmu(TurkceAlfabe.HARF_l));
    }


    public void testIsimLi() {
        Ek ek = Ekler.ISIM_LI;
        String[] strs = {"sandalye", "kalem", "armut", "yast\u0131k"};
        String[] gercekEkler = {"li", "li", "lu", "l\u0131"};
        olusanEkKontrol(strs, gercekEkler, ek);
        assertTrue(ek.ilkHarfUygunmu(TurkceAlfabe.HARF_l));
    }

    public void testIsimKi() {
        Ek ek = Ekler.ISIM_KI;
        String[] strs = {"sandalyede", "kalemde", "armutta", "yast\u0131kta"};
        String[] gercekEkler = {"ki", "ki", "ki", "ki"};
        olusanEkKontrol(strs, gercekEkler, ek);
        assertTrue(ek.ilkHarfUygunmu(TurkceAlfabe.HARF_k));
    }

    public void testIsimSal() {
        Ek ek = Ekler.ISIM_SAL;
        String[] strs = {"ili\u015fki", "para"};
        String[] gercekEkler = {"sel", "sal"};
        olusanEkKontrol(strs, gercekEkler, ek);
        assertTrue(ek.ilkHarfUygunmu(TurkceAlfabe.HARF_s));
    }

    public void testIsimYokluk() {
        Ek ek = Ekler.ISIM_YOKLUK;
        String[] strs = {"sandalye", "kalem", "armut", "yast\u0131k"};
        String[] gercekEkler = {"siz", "siz", "suz", "s\u0131z"};
        olusanEkKontrol(strs, gercekEkler, ek);
        assertTrue(ek.ilkHarfUygunmu(TurkceAlfabe.HARF_s));
    }

    public void testIsimImsi() {
        Ek ek = Ekler.ISIM_IMSI;
        String[] strs = {"sandalye", "kalem", "armut", "yast\u0131k"};
        String[] gercekEkler = {"msi", "imsi", "umsu", "\u0131ms\u0131"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }

    public void testIsimDi() {
        Ek ek = Ekler.ISIM_DI;
        String[] strs = {"sandalye", "kalem", "armut", "yast\u0131k"};
        String[] gercekEkler = {"ydi", "di", "tu", "t\u0131"};
        olusanEkKontrol(strs, gercekEkler, ek);
        assertTrue(ek.ilkHarfUygunmu(TurkceAlfabe.HARF_y) &&
                ek.ilkHarfUygunmu(TurkceAlfabe.HARF_d) &&
                ek.ilkHarfUygunmu(TurkceAlfabe.HARF_t));
    }

    public void testIsimSahisBen() {
        Ek ek = Ekler.ISIM_SAHIS_BEN;
        String[] strs = {"sandalye", "kalem", "armut", "yast\u0131k"};
        String[] gercekEkler = {"yim", "im", "um", "\u0131m"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }

    public void testIsimSahisSen() {
        Ek ek = Ekler.ISIM_SAHIS_SEN;
        String[] strs = {"sandalye", "kalem", "armut", "yast\u0131k"};
        String[] gercekEkler = {"sin", "sin", "sun", "s\u0131n"};
        olusanEkKontrol(strs, gercekEkler, ek);
        assertTrue(ek.ilkHarfUygunmu(TurkceAlfabe.HARF_s));
    }

    public void testIsimSahisBiz() {
        Ek ek = Ekler.ISIM_SAHIS_BIZ;
        String[] strs = {"sandalye", "kalem", "armut", "yast\u0131k"};
        String[] gercekEkler = {"yiz", "iz", "uz", "\u0131z"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }

    public void testIsimImekBen() {
        Ek ek = Ekler.ISIM_IMEK_BEN;
        Ek ekIdi = Ekler.ISIM_DI;
        String[] strs = {"sandalyeydi", "kalemse"};
        String[] gercekEkler = {"m", "m"};
        olusanEkKontrol(strs, gercekEkler, ek);

        Kok kok = new Kok("kedi", KelimeTipi.ISIM);
        Kelime kelime = new Kelime(kok);
        ekIdi.ekOlustur(kelime);
        kelime.ekEkle(ekIdi);
        ek.ekOlustur(kelime);
        kelime.ekEkle(ek);
        String sonuc = "kediydim";
        assertEquals(sonuc, kelime.icerikStr());
        assertTrue(ek.ilkHarfUygunmu(TurkceAlfabe.HARF_m));
    }

    public void testIsimImekSen() {
        Ek ek = Ekler.ISIM_IMEK_SEN;
        Ek ekIdi = Ekler.ISIM_DI;
        String[] strs = {"sandalyeydi", "kalemse"};
        String[] gercekEkler = {"n", "n"};
        olusanEkKontrol(strs, gercekEkler, ek);

        Kok kok = new Kok("kedi", KelimeTipi.ISIM);
        Kelime kelime = new Kelime(kok);
        ekIdi.ekOlustur(kelime);
        kelime.ekEkle(ekIdi);
        ek.ekOlustur(kelime);
        kelime.ekEkle(ek);
        String sonuc = "kediydin";
        assertEquals(sonuc, kelime.icerikStr());
        assertTrue(ek.ilkHarfUygunmu(TurkceAlfabe.HARF_n));
    }

    public void testIsimImekBiz() {
        Ek ek = Ekler.ISIM_IMEK_BIZ;
        Ek ekIdi = Ekler.ISIM_DI;
        String[] strs = {"sandalyeydi", "kalemse"};
        String[] gercekEkler = {"k", "k"};
        olusanEkKontrol(strs, gercekEkler, ek);

        Kok kok = new Kok("kedi", KelimeTipi.ISIM);
        Kelime kelime = new Kelime(kok);
        ekIdi.ekOlustur(kelime);
        kelime.ekEkle(ekIdi);
        ek.ekOlustur(kelime);
        kelime.ekEkle(ek);
        String sonuc = "kediydik";
        assertEquals(sonuc, kelime.icerikStr());
        assertTrue(ek.ilkHarfUygunmu(TurkceAlfabe.HARF_k));
    }

    public void testIsimImekSiz() {
        Ek ek = Ekler.ISIM_IMEK_SIZ;
        Ek ekIdi = Ekler.ISIM_DI;
        String[] strs = {"sandalyeydi", "kalemse"};
        String[] gercekEkler = {"niz", "niz"};
        olusanEkKontrol(strs, gercekEkler, ek);

        Kok kok = new Kok("kedi", KelimeTipi.ISIM);
        Kelime kelime = new Kelime(kok);
        ekIdi.ekOlustur(kelime);
        kelime.ekEkle(ekIdi);
        ek.ekOlustur(kelime);
        kelime.ekEkle(ek);
        String sonuc = "kediydiniz";
        assertEquals(sonuc, kelime.icerikStr());
        assertTrue(ek.ilkHarfUygunmu(TurkceAlfabe.HARF_n));
    }

    public void testIsimImekOnlar() {
        Ek ek = Ekler.ISIM_IMEK_ONLAR;
        Ek ekIdi = Ekler.ISIM_DI;
        String[] strs = {"sandalyeydi", "kalemse"};
        String[] gercekEkler = {"ler", "ler"};
        olusanEkKontrol(strs, gercekEkler, ek);

        Kok kok = new Kok("kedi", KelimeTipi.ISIM);
        Kelime kelime = new Kelime(kok);
        ekIdi.ekOlustur(kelime);
        kelime.ekEkle(ekIdi);
        ek.ekOlustur(kelime);
        kelime.ekEkle(ek);
        String sonuc = "kediydiler";
        assertEquals(sonuc, kelime.icerikStr());
        assertTrue(ek.ilkHarfUygunmu(TurkceAlfabe.HARF_l));
    }

    public void testIsimIn() {
        Ek ek = Ekler.ISIM_IN;
        String[] strs = {"sandalye", "kalem", "armut", "yast\u0131k"};
        String[] gercekEkler = {"nin", "in", "un", "\u0131n"};
        olusanEkKontrol(strs, gercekEkler, ek);
    }

    public void testIsimGibiCe() {
        Ek ek = Ekler.ISIM_GIBI_CE;
        String[] strs = {"insan", "beyaz", "mavi"};
        String[] gercekEkler = {"ca", "ca", "ce"};
        olusanEkKontrol(strs, gercekEkler, ek);
        assertTrue(ek.ilkHarfUygunmu(TurkceAlfabe.HARF_c) && ek.ilkHarfUygunmu(TurkceAlfabe.HARF_ch));
    }

    public void testIsimSe() {
        Ek ek = Ekler.ISIM_SE;
        String[] strs = {"sandalye", "kalem", "armut", "yast\u0131k"};
        String[] gercekEkler = {"yse", "se", "sa", "sa"};
        olusanEkKontrol(strs, gercekEkler, ek);
        assertTrue(ek.ilkHarfUygunmu(TurkceAlfabe.HARF_s));
    }

    public void testIsimCi() {
        Ek ek = Ekler.ISIM_CI;
        String[] strs = {"sandalye", "kalem", "armut", "yast\u0131k"};
        String[] gercekEkler = {"ci", "ci", "\u00e7u", "\u00e7\u0131"};
        olusanEkKontrol(strs, gercekEkler, ek);
        assertTrue(ek.ilkHarfUygunmu(TurkceAlfabe.HARF_c) && ek.ilkHarfUygunmu(TurkceAlfabe.HARF_ch));
    }

    public void testIsimMis() {
        Ek ek = Ekler.ISIM_MIS;
        String[] strs = {"sandalye", "kalem", "armut", "yast\u0131k"};
        String[] gercekEkler = {"ymi\u015f", "mi\u015f", "mu\u015f", "m\u0131\u015f"};
        olusanEkKontrol(strs, gercekEkler, ek);
        assertTrue(ek.ilkHarfUygunmu(TurkceAlfabe.HARF_y) && ek.ilkHarfUygunmu(TurkceAlfabe.HARF_m));
    }

    public void testIsimLe() {
        Ek ek = Ekler.ISIM_LE_DONUSUM;
        String[] strs = {"sandalye", "kalem", "armut", "yast\u0131k"};
        String[] gercekEkler = {"le", "le", "la", "la"};
        olusanEkKontrol(strs, gercekEkler, ek);
        //ozel durum testi
        HarfDizisi giris = new HarfDizisi("odunluyor");
        Kelime kelime = new Kelime(new Kok("odun"));
        ek.ekOlustur(kelime, giris);

        String sonuc = ek.icerik().toString();
        assertEquals(sonuc, "la");
        assertTrue(ek.ilkHarfUygunmu(TurkceAlfabe.HARF_l));
    }*/

}
