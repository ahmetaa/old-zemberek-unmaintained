/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.bilgi.kokler;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.zemberek.yapi.Kok;

/**
 * Bu seçici Deasciifier için kullanılır. Verilen kelime için ağaçta ilerlerken
 * Türkçedeki karşılıkları birden fazla olabilecek harfler için (u-ü i-ı o-ö vs.)
 * alternatif dallarda da ilerlenerek yol Üzerinde rastlanan tüm kökler toplanır.
 *
 * @author MDA
 */
public class AsciiKokAdayiBulucu implements KokAdayiBulucu {

    private static Logger log = Logger.getLogger(AsciiKokAdayiBulucu.class.getName());
    KokAgaci agac = null;
    private int walkCount = 0;
    private String giris;
    private String asciiGiris;
    private List<Kok> adaylar = null;

    public AsciiKokAdayiBulucu(KokAgaci agac) {
        this.agac = agac;
    }

    public int getYurumeSayisi() {
        return walkCount;
    }

    public List<Kok> adayKokleriBul(String giris) {
        this.giris = giris;
        asciiGiris = agac.getAlfabe().asciifyString(giris);
        adaylar = new ArrayList<Kok>(4);
        yuru(agac.getKokDugumu(), "");
        return adaylar;
    }

    /**
     * Verilen iki string'in asciified versiyonlarını karşılaştırır.
     *
     * @param aday
     * @param giris
     * @return aday ve giris degerlerinin ascii karsiliklari aynıysa true, 
     * 	       değilse false. Örneğin:
     * <pre>
     * asciiTolaransliKarsilastir("siraci", "şıracı") --> true 
     * </pre>
     */
    public boolean asciiTolaransliKarsilastir(String aday, String giris) {
        if (aday.length() > giris.length()) return false;
        String clean = agac.getAlfabe().asciifyString(aday);
        return asciiGiris.startsWith(clean);
    }

    /**
     * Ağaç üzerinde  yürüyerek ASCII toleranslı karşılaştırma ile
     * kök adaylarını bulur. Rekürsiftir.
     *
     * @param dugum  : başlangıç düğümü
     * @param olusan : Yürüme sırasında oluşan kelime (düğümlerin karakter değerlerinden)
     */
    public void yuru(KokDugumu dugum, String olusan) {
        String tester = (olusan + dugum.harf()).trim();
        walkCount++;
        if (dugum.kok() != null) {
            if (log.isLoggable(Level.FINEST)) log.finest("Kok : " + dugum.kelime());
            if (asciiTolaransliKarsilastir((String) dugum.kelime(), giris)) {
            	// Aday kok bulundu.
                dugum.tumKokleriEkle(adaylar);
            } else {
                return;
            }
        } else {
            if (!asciiTolaransliKarsilastir(tester, giris)) {
                return;
            }
        }

       int seviye = tester.length();
       if(seviye == giris.length()) return;
       // Uygun tüm alt dallarda yürü
       for (KokDugumu altDugum : dugum.altDugumDizisi()) {
           if (altDugum != null) {
               if (agac.getAlfabe().asciiToleransliKiyasla(altDugum.harf(), giris.charAt(seviye)))
                   this.yuru(altDugum, tester);
           }
       }
    }
}