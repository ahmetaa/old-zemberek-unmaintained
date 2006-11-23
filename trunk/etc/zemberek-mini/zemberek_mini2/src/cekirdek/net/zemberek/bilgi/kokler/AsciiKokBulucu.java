package net.zemberek.bilgi.kokler;

import net.zemberek.yapi.Kok;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Bu seçici Deasciifier için kullanılır. Verilen kelime için ağaçta ilerlerken
 * Türkçedeki karşılıkları birden fazla olabilecek harfler için (u-ü i-ı o-ö vs.)
 * alternatif dallarda da ilerlenerek yol �zerinde rastlanan tüm kökler toplanır.
 *
 * @author MDA
 */
public class AsciiKokBulucu implements KokBulucu {

    private static Logger log = Logger.getLogger(AsciiKokBulucu.class.getName());
    KokAgaci agac = null;
    private int walkCount = 0;
    private String giris;
    private String asciiGiris;
    private List<Kok> adaylar = null;

    public AsciiKokBulucu(KokAgaci agac) {
        this.agac = agac;
    }

    public int getWalkCount() {
        return walkCount;
    }

    public List<Kok> getAdayKokler(String giris) {
        this.giris = giris;
        asciiGiris = agac.getAlfabe().asciifyString(giris);
        adaylar = new ArrayList<Kok>(4);
        walk(agac.getKokDugumu(), "");
        return adaylar;
    }

    /**
     * Verilen iki string'in asciified versiyonlarını karşılaştırır.
     *
     * @param aday
     * @param giris
     * @return aday ve giris degerlerinin ascii karsiliklari aynıysa true,
     *         değilse false. �rne�in
     *         <pre>
     *         asciiTolaransliKarsilastir("siraci", "şıracı") --> true
     *         </pre>
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
    public void walk(KokDugumu dugum, String olusan) {
        String tester = (olusan + dugum.getHarf()).trim();
        walkCount++;
        if (dugum.getKok() != null) {
            if (log.isLoggable(Level.FINEST)) log.finest("Kok : " + dugum.getKelime());
            if (asciiTolaransliKarsilastir((String) dugum.getKelime(), giris)) {
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
        if (seviye == giris.length()) return;
        // Uygun tüm alt dallarda yürü
        for (KokDugumu altDugum : dugum.altDugumDizisiGetir()) {
            if (altDugum != null) {
                if (agac.getAlfabe().asciiToleransliKiyasla(altDugum.getHarf(), giris.charAt(seviye))) ;
                this.walk(altDugum, tester);
            }
        }
    }
}