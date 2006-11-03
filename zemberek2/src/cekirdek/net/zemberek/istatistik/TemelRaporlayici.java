/*
 * Created on 20.Mar.2005
 *
 */
package net.zemberek.istatistik;

import net.zemberek.araclar.IstatistikAraclari;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public abstract class TemelRaporlayici implements IstatistikRaporlayici {
    protected Istatistikler istatistikler = null;

    public void raporla(OutputStream out, String encoding) {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, encoding));
            KokIstatistikleri kokIst = istatistikler.getKokIstatistikleri();
            HeceIstatistikleri heceIst = istatistikler.getHeceIstatistikleri();
            EkIstatistikleri ekIst = istatistikler.getEkIstatistikleri();
            KarakterIstatistikleri karIst = istatistikler.getKarakterIstatistikleri();
            KelimeIstatistikleri kelimeIst = istatistikler.getKelimeIstatistikleri();
            IkiliIstatistikleri ikiliIst = istatistikler.getIkiliIstatistikleri();
            IkiliIstatistikleri kokIkiliIst = istatistikler.getKokIkiliIstatistikleri();
            IkiliIstatistikleri heceIkiliIst = istatistikler.getHeceIkiliIstatistikleri();
            KelimeIstatistikleri ikiliHarfIst = istatistikler.getIkiliHarfIstatistikleri();
            
            List kokListesi = kokIst.getKokListesi();

            if (kokListesi == null) {
                return;
            }
            writer.write("Karakter istatistikleri:\n");
            writer.write("------------------------");
            
            writer.write(karIst.toString());

            writer.write("\nGenel istatistikler:\n");
            writer.write("------------------------\n");
            
            for (int i = 0; i < kokIst.getAraToplamSayaci(); i++) {
                writer.write("-> ilk " + kokIst.getKontrolDizisi()[i]
                        + " kok, toplamın %" + kokIst.getKokKapsamaYuzdeleri()[i] + "\n");
            }
            writer.write("\n");

            int[] toplamEkUzunlukSayilari = kokIst.getToplamEkUzunlukSayilari();
            long toplamKelime = kokIst.getToplamKelime();
            long toplamKokSayisi = kokIst.getToplamKokSayisi();
            for (int i = 0; i < toplamEkUzunlukSayilari.length; i++) {
                writer.write(i + " adet ek'e sahip kelime sayısı: " + toplamEkUzunlukSayilari[i]
                        + " Oran: %" + IstatistikAraclari.yuzdeHesaplaStr(toplamEkUzunlukSayilari[i], toplamKelime)
                        + "\n");
            }
            writer.write("\n");
            writer.write("Toplam kelime sayısı: " + toplamKelime + "\n");
            writer.write("Toplam isim sayisi: " + kokIst.getToplamIsimSayisi()
                    + "  Oran: %" + IstatistikAraclari.yuzdeHesaplaStr(kokIst.getToplamIsimSayisi(), toplamKelime)
                    + " Ortalama Boy : " + IstatistikAraclari.df.format(kokIst.getOrtalamaIsimUzunlugu()) + "\n");
            writer.write("Toplam sıfat sayisi: " + kokIst.getToplamSifatSayisi()
                    + "  Oran: %" + IstatistikAraclari.yuzdeHesaplaStr(kokIst.getToplamSifatSayisi(), toplamKelime) + "\n");
            writer.write("Toplam fiil sayisi: " + kokIst.getToplamFiilSayisi()
                    + "  Oran: %" + IstatistikAraclari.yuzdeHesaplaStr(kokIst.getToplamFiilSayisi(), toplamKelime)
                    + " Ortalama Boy : " + IstatistikAraclari.df.format(kokIst.getOrtalamaFiilUzunlugu()) + "\n");
            writer.write("Toplam Sayi sayisi: " + kokIst.getToplamSayiSayisi()
                    + "  Oran: %" + IstatistikAraclari.yuzdeHesaplaStr(kokIst.getToplamSayiSayisi(), toplamKelime) + "\n");
            writer.write("Toplam kok sayisi: " + kokIst.getToplamKokSayisi());
            writer.write("\nToplam isim kök sayisi: " + kokIst.getToplamIsimKokSayisi()
                    + "  Oran: %" + IstatistikAraclari.yuzdeHesaplaStr(kokIst.getToplamIsimKokSayisi(), toplamKokSayisi) + "\n");
            writer.write("Toplam sıfat kök sayisi: " + kokIst.getToplamSifatKokSayisi()
                    + "  Oran: %" + IstatistikAraclari.yuzdeHesaplaStr(kokIst.getToplamSifatKokSayisi(), toplamKokSayisi) + "\n");
            writer.write("Toplam fiil kök sayisi: " + kokIst.getToplamFiilKokSayisi()
                    + "  Oran: %" + IstatistikAraclari.yuzdeHesaplaStr(kokIst.getToplamFiilKokSayisi(), toplamKokSayisi) + "\n");
            writer.write("\n\n");
            
            int sayac = 1;
            int toplam = 0;
            List ikiliHarfler = kelimeIst.getKelimeListesi();
            int limit = istatistikler.getKelimeLimit() > ikiliHarfler.size() ? ikiliHarfler.size():istatistikler.getKokLimit(); 
            for (int i = 0; i < limit; i++) {
            	KelimeBilgisi kelime = (KelimeBilgisi) ikiliHarfler.get(i);
            	toplam += kelime.miktar;
            	writer.write((sayac++) + ". "
            			+ "[ " + kelime.kelime + " ]"
            			+ "  Kullanim: " + kelime.miktar
            			+ " Oran: % " + IstatistikAraclari.yuzdeHesaplaStr(kelime.miktar, toplamKelime)
            			+ " Kapsama: % " + IstatistikAraclari.yuzdeHesaplaStr(toplam, toplamKelime)
            			+ "\n"
            			);
			}
            
            writer.write("\n\n");
            sayac = 1;
            limit = istatistikler.getKokLimit() > kokListesi.size() ? kokListesi.size():istatistikler.getKokLimit(); 
            for (int i = 0; i < limit ; i++) {
                String report = "";
                GenelKokIstatistikBilgisi istatistik = (GenelKokIstatistikBilgisi) kokListesi.get(i);
                ArrayList ekler = istatistik.getEkListesi();
                writer.write((sayac++) + ". "
                        + "<" + istatistik.getKok().icerik() + ">"
                        + " Kullanım : " + istatistik.getKullanimSayisi()
                        + " Oran : %" + IstatistikAraclari.yuzdeHesaplaStr(istatistik.getKullanimSayisi(), toplamKelime)
                        + " Ortalama kelime uzunluğu : " + IstatistikAraclari.df.format(istatistik.getOrtalamaKelimeUzunlugu())
                        + "\n");

                double ekKapsam = 0.0D;
                for (int j = 0; j < ekler.size(); j++) {
                    EkZinciri zincir = (EkZinciri) ekler.get(j);

                    if (zincir.getKullanimFrekansi() > 1d) {
                        ekKapsam += zincir.getKullanimFrekansi();
                        writer.write(j + ". -- " + zincir + "   Oran : %" + IstatistikAraclari.df.format(zincir.getKullanimFrekansi())
                                + " Toplam: %" + IstatistikAraclari.df.format(ekKapsam) + "\n");
                    }
                }
                writer.write("----> Kullanım oranı %0.1'den fazla olan ek zincirlerinin kapsam oran�: %"
                        + IstatistikAraclari.df.format(ekKapsam) + "\n\n");
                writer.write(report);
            }
            
            // Ek istatistikleri
            writer.write(ekIst.toString());
            
            // Ikili istatistikleri
            writer.write("\n\n");
            List list = ikiliIst.getSiraliKelimeZincirleri();
            limit = list.size() < istatistikler.getKokLimit() ? list.size() : istatistikler.getKokLimit();
            for(int i=0; i<limit; i++){
                KelimeZinciri zincir = (KelimeZinciri) list.get(i);
                writer.write( (i+1)  + ". " + zincir.toString() + "\n");
            }

            // Kok Ikili istatistikleri
            writer.write("\n\n");
            list = kokIkiliIst.getSiraliKelimeZincirleri();
            limit = list.size() < istatistikler.getKokLimit() ? list.size() : istatistikler.getKokLimit();
            for(int i=0; i<limit; i++){
                KelimeZinciri zincir = (KelimeZinciri) list.get(i);
                writer.write((i+1)  + ". " + zincir.toString() + "\n");
            }

            // Hece Ikili istatistikleri
            writer.write("\n\n");
            list = heceIkiliIst.getSiraliKelimeZincirleri();
            limit = list.size() < istatistikler.getKokLimit() ? list.size() : istatistikler.getKokLimit();
            for(int i=0; i<limit; i++){
                KelimeZinciri zincir = (KelimeZinciri) list.get(i);
                writer.write((i+1)  + ". " + zincir.toString() + "\n");
            }
            
            
            // Hece Istaistikleri
            long araToplam = 0;
            for (int i = 0; i < heceIst.getHeceListesi().size(); i++) {
                Hece hece = (Hece) heceIst.getHeceListesi().get(i);
                araToplam += hece.getKullanim();
                writer.write(i + ". " + hece.getHece() + " [" + hece.getKullanim() + "] Oran(%): "
                        + IstatistikAraclari.yuzdeHesaplaStr(hece.getKullanim(), heceIst.getHeceSayisi())
                        + " AraToplam(%): " + IstatistikAraclari.yuzdeHesaplaStr(araToplam, heceIst.getHeceSayisi()) + "\n");
                if (i > HeceIstatistikleri.MAX_HECE_GOSTERIM)
                    break;
            }
            
            sayac = 1;
            toplam = 0;
            ikiliHarfler = ikiliHarfIst.getKelimeListesi(); 
            for (int i = 0; i < ikiliHarfler.size(); i++) {
            	KelimeBilgisi kelime = (KelimeBilgisi) ikiliHarfler.get(i);
            	toplam += kelime.miktar;
            	writer.write((sayac++) + ". "
            			+ "[ " + kelime.kelime + " ]"
            			+ " Kullanim: " + kelime.miktar
            			+ "\n"
            			);
			}

            
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
