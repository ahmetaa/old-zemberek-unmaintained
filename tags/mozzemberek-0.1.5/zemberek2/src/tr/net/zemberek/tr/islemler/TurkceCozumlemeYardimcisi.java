/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.tr.islemler;

import java.util.List;
import java.util.Locale;

import net.zemberek.islemler.cozumleme.CozumlemeYardimcisi;
import net.zemberek.tr.yapi.kok.TurkceKokOzelDurumTipleri;
import net.zemberek.yapi.*;
import net.zemberek.yapi.ek.Ek;

/**
 * Bu sinif Turkiye Turkcesine ozel cesitli islemleri icerir.
 * User: ahmet
 * Date: Sep 11, 2005
 */
public class TurkceCozumlemeYardimcisi implements CozumlemeYardimcisi {

    private Alfabe alfabe;
    Locale TR = new Locale("tr");

    public TurkceCozumlemeYardimcisi(Alfabe alfabe) {
        this.alfabe = alfabe;
    }

    public void kelimeBicimlendir(Kelime kelime) {
        Kok kok = kelime.kok();
        HarfDizisi olusan = kelime.icerik();
        if (kok instanceof Kisaltma) {
            //cozumleme sirasinda eklenmis harf varsa onlari sil.
            int silinecek = kok.icerik().length();
            if (kok.ozelDurumIceriyormu(TurkceKokOzelDurumTipleri.KISALTMA_SON_SESSIZ))
                silinecek += 2;
            if (kok.ozelDurumIceriyormu(TurkceKokOzelDurumTipleri.KISALTMA_SON_SESLI))
                silinecek++;
            //kelimenin olusan kismindan kokun icereigini sil.
            olusan.harfSil(0, silinecek);
            //simdi kokun orjinal halini ekle.
            olusan.ekle(0, new HarfDizisi(kok.asil(), alfabe));

            if (olusan.length() == kok.asil().length())
                return;
            //eger gerekiyorsa kesme isareti koy.
            if (!olusan.harf(kok.asil().length() - 1).equals(alfabe.harf('.')))
                olusan.ekle(kok.asil().length(), alfabe.harf('\''));

        } else if (kok.tip() == KelimeTipi.OZEL) {
            olusan.harfDegistir(0, alfabe.buyukHarf(olusan.ilkHarf()));
            if (kok.ozelDurumIceriyormu(TurkceKokOzelDurumTipleri.KESMESIZ))
                return;
            List<Ek> ekler = kelime.ekler();
            if (ekler.size() > 1) {
                Ek ek = ekler.get(1);
                if (ek.iyelikEkiMi() || ek.halEkiMi()) {
                    int kesmePozisyonu = kok.icerik().length();
                    olusan.ekle(kesmePozisyonu, alfabe.harf('\''));
                }
            }
        }
        // ozel ic karakter iceren kokler icin bicimleme
        else if (kok.ozelDurumIceriyormu(TurkceKokOzelDurumTipleri.OZEL_IC_KARAKTER)) {
            //olusan ksimdan koku sil
            int silinecek = kok.icerik().length();
            olusan.harfSil(0, silinecek);
            //simdi kokun orjinal halini ekle.
            olusan.ekle(0, new HarfDizisi(kok.asil(), alfabe));
        }
    }

    public boolean kelimeBicimiDenetle(Kelime kelime, String giris) {
        if (giris.length() == 0) return false;
        Kok kok = kelime.kok();
        if (kok.tip().equals(KelimeTipi.KISALTMA)) {
            // eger giriskokun orjinal hali ile baslamiyorsa hatali demektir.
            String as = kok.asil();
            if (!giris.startsWith(as))
                return false;
            if (giris.equals(as))
                return true;
            //burada farkli kisaltma turleri icin kesme ve nokta isaretlerinin
            // dogru olup olmadigina bakiliyor.
            String kalan = giris.substring(as.length());
            if (as.charAt(as.length() - 1) == '.') {
                return kalan.charAt(0) != '\'';
            }
            return kalan.charAt(0) == '\'';
        } else if (kelime.kok().tip() == KelimeTipi.OZEL) {
            if (Character.isLowerCase(giris.charAt(0)))
                return false;
            if (kelime.kok().ozelDurumIceriyormu(TurkceKokOzelDurumTipleri.KESMESIZ))
                return true;
            List<Ek> ekler = kelime.ekler();
            if (ekler.size() > 1) {
                Ek ek = ekler.get(1);
                if (ek.iyelikEkiMi() || ek.halEkiMi()) {
                    int kesmePozisyonu = kelime.kok().icerik().length();
                    return kesmePozisyonu <= giris.length() && giris.charAt(kesmePozisyonu) == '\'';
                }
            }
        }
        // ozel ic karakter iceren kokler icin bicimleme
/*        if (kok.ozelDurumlar().contains(TurkceKokOzelDurumlari.OZEL_IC_KARAKTER)) {
            //olusan ksimdan koku sil
            String as = kok.asil();
            if (!giris.startsWith(as))
              return false;
        }*/
        return true;
    }

    public boolean kokGirisDegismiVarsaUygula(Kok kok, HarfDizisi kokDizi, HarfDizisi girisDizi) {
        //turkce'de sadece kisaltmalarda bu metoda ihtiyacimiz var.
        if (girisDizi != null && girisDizi.length() == 0) return false;
        if (kok instanceof Kisaltma) {
            char c = ((Kisaltma) kok).getKisaltmaSonSeslisi();
            if (c == 0) return false;
            TurkceHarf h = alfabe.harf(c);
            kokDizi.ekle(h);
            //toleransli cozumleyicide kok giristen daha uzun olabiliyor.
            // o nedenle asagidaki kontrolun yapilmasi gerekiyor.
            int kokBoyu = kok.icerik().length();

            if (girisDizi != null) {
                if (kokBoyu <= girisDizi.length()) {
                    girisDizi.ekle(kokBoyu, h);
                } else {
                    girisDizi.ekle(h);
                }
            }
            if (kok.ozelDurumIceriyormu(TurkceKokOzelDurumTipleri.KISALTMA_SON_SESSIZ)) {
                //gene toleransli cozumleyicinin hata vermemesi icin asagidaki kontrole ihtiyacimiz var
                kokDizi.ekle(alfabe.harf('b'));

                if (girisDizi != null) {
                    if (kokBoyu < girisDizi.length()) {
                        girisDizi.ekle(kokBoyu + 1, alfabe.harf('b'));
                    } else {
                        girisDizi.ekle(alfabe.harf('b'));
                    }
                }
            }
            return true;
        }
        return false;
    }
}
