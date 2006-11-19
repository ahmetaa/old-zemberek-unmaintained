/*
 * Created on 24.Haz.2004
 */
package net.zemberek.istatistik;


import java.io.File;
import java.io.FileFilter;

import net.zemberek.araclar.TimeTracker;
import net.zemberek.araclar.turkce.TurkceMetinOkuyucu;
import net.zemberek.erisim.Zemberek;
import net.zemberek.islemler.cozumleme.KelimeCozumleyici;
import net.zemberek.yapi.DilAyarlari;
import net.zemberek.yapi.DilBilgisi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.TurkceDilBilgisi;


/**
 * @author MDA
 */
public class IstatistikToplayici {

    private KelimeCozumleyici cozumleyici;
    private Istatistikler istatistikler;
    private long toplamKelime = 0;

    public IstatistikToplayici(KelimeCozumleyici cozumleyici, Istatistikler istatistikler) {
        this.cozumleyici = cozumleyici;
        this.istatistikler = istatistikler;
    }

    public void metinIsle(String dosyaAdi) {
        System.out.println("Taranan dosya: " + dosyaAdi);
        TurkceMetinOkuyucu tmo = new TurkceMetinOkuyucu();
        tmo.setStatistics(istatistikler);
        String[] tumKelimeler = tmo.MetinOku(dosyaAdi);
        System.out.println("Okunan kelime sayısı: " + tumKelimeler.length);
        for (int i = 0; i < tumKelimeler.length; i++) {
            try {
            	// Ekrana ilerleme durumunu yaz.
            	if (i>0 && i % 500 == 0) {
            		//System.out.print(".");
            		if (i % 20000 == 0) System.out.println( i );
            	}
                Kelime[] kelimeler = cozumleyici.cozumle(tumKelimeler[i]);
                if(kelimeler == null) continue;
                toplamKelime++;
                istatistikler.hepsiniGuncelle(tumKelimeler[i], kelimeler);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(tumKelimeler[i] + " yi çözümlerken beklenmeyen hata. " + e.getMessage());
            }
        }
        System.out.println("Bitti.");
    }

    public void publish() {
        istatistikler.sonlandir();
    }

    /**
     * 
     * @param yol
     * Sadece txt soyadlı dosyalar işleniyor. 
     */
    public void klasorIsle(String yol) {
        File dir = new File(yol);
        File[] dosyalar = dir.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                if (pathname.getName().endsWith(".txt")) return true;
                return false;
            }
        });

        for (File dosya : dosyalar) {
            metinIsle(dosya.getAbsolutePath());
        }
    }

    public void sonlandir() {
        istatistikler.sonlandir();
//        KonsolRaporlayici konsolRaporlayici = new KonsolRaporlayici(istatistikler);
//        konsolRaporlayici.raporla(System.out, "UTF-8");
        BinaryIstatistikYazici y = new BinaryIstatistikYazici();
        y.initialize("kaynaklar/tr/bilgi/kok_istatistik_tr.bin");
        y.yaz(istatistikler);
        istatistikler.setLimit(5000, 5000, 20000);
        DosyaRaporlayici dr = new DosyaRaporlayici(istatistikler, "istatistik.txt");
        dr.raporla();
    }

    public Istatistikler getIstatistikler() {
        return istatistikler;
    }
    
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class c = Class.forName("net.zemberek.tr.yapi.TurkiyeTurkcesi");
        DilBilgisi turkce = new TurkceDilBilgisi((DilAyarlari) c.newInstance());
    	Zemberek zemberek = new Zemberek((DilAyarlari) c.newInstance());
    	IstatistikToplayici top = new IstatistikToplayici(zemberek.cozumleyici(),
    			new Istatistikler(turkce));
    	TimeTracker.startClock("i");
    	//top.metinIsle("/home/mdakin/books/yonetim_mizah_kitap.txt");
    	top.klasorIsle("/home/mdakin/books/secme");
    	top.sonlandir();
    	System.out.println("Saniyede islenen ortalama kelime sayisi: " + TimeTracker.getItemsPerSecond("i", top.toplamKelime));
    	System.out.println("Toplam sure: " + TimeTracker.stopClock("i"));
	}

}
