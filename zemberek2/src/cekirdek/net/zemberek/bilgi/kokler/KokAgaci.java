/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

/*
 * Created on 28.Eyl.2004
 */
package net.zemberek.bilgi.kokler;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.zemberek.araclar.Kayitci;
import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.Kok;

/**
 * Kök ağacı zemberek sisteminin temel veri taşıyıcılarından biridir. Kök 
 * sözlüğünden okunan tüm kökler bu ağaca yerleştirilirler. Ağacın oluşumundan 
 * AgacSozluk sınıfı sorumludur.
 * Kök ağacı kompakt DAWG (Directed Acyclic Word Graph) benzeri
 * bir yapıya sahiptir. Ağaca eklenen her kök harflerine göre bir ağaç oluşturacak
 * şekilde yerleştirilir. Bir kökü bulmak için ağacın başından itibaren kökü 
 * oluşturan harfleri temsil eden düğümleri izlemek yeterlidir. 
 * Eğer bir kökü ararken erişmek istediğimiz harfe ait bir alt düğüme
 * gidemiyorsak kök ağaçta yok demektir.
 * <p/>
 * Ağacın bir özelliği de boşuna düğüm oluşturmamasıdır. Eğer bir kökün altında
 * başka bir kök olmayacaksa tüm harfleri için ayrı ayrı değil, sadece gerektiği
 * kadar düğüm oluşturulur.
 * <p/>
 *
 * @author MDA
 */
public class KokAgaci {
    private static Logger log = Kayitci.kayitciUret(KokAgaci.class);
    private KokDugumu baslangicDugumu = null;
    private int nodeCount = 0;
    private Alfabe alfabe;

    public KokAgaci(KokDugumu baslangicDugumu, Alfabe alfabe) {
        this.baslangicDugumu = baslangicDugumu;
        this.alfabe = alfabe;
    }

    public KokDugumu getKokDugumu() {
        return baslangicDugumu;
    }

    public Alfabe getAlfabe() {
        return alfabe;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    /**
     * Verilen kök icerigini ağaca ekler.
     *
     * @param icerik
     * @param kok
     */
    public void ekle(String icerik, Kok kok) {
        //System.out.println("Kelime: " + icerik);
        char[] giris = icerik.toCharArray();
        KokDugumu node = baslangicDugumu;
        KokDugumu oncekiDugum = null;
        int idx = 0;
        // null alt düğüm bulana dek veya kelimenin sonuna gelene 
        // dek kelimenin harflerini izleyerek alt düğümlerde ilerle
        while (idx < giris.length) {
            oncekiDugum = node;
            node = node.altDugumBul(giris[idx]);
            if (node == null) break;
            idx++;
        }
        /**
         * Ağaç üzerinde ilerlerken giriş kelimemizin sonuna kadar gitmişiz. 
         * Yani ağacın sonuna (yapraklarına) ulaşmadan önce girişe erişmişiz.  
         * 
         * Aşağıdaki drumlar bu durum çerçevesinde çözülür:
         * 
         * (1)
         * i-s-t-->istim şeklindeki dala "is" kelimesini eklemek.
         * i-s-t-->istim
         *   |-->is
         *
         * veya
         * 
         * (2) Geldiğimiz düğüme eklemek için yeni bir düğüm oluşturma zorunluluğu var.
         * i-s-->istim e is gelirse de
         * i-s-t-->istim
         *   |-->is
         *
         * (3) Eş seslilik durumu:
         * 
         * a-l-->al  a "al" gelirse
         * a-l-->al(2) olur.
         *
         */
        if (idx == giris.length) {
        	// (1) durumu
            if (node.altDugumVarMi()) {
                node.kokEkle(kok);
                node.setKelime(icerik);
            }
            // Eş seslilik durumu.
            else if (node.kelime().equals(icerik)) {
                node.kokEkle(kok);
            }
            // (2) durumu
            else if (node.kok() != null) {
                KokDugumu newNode = node.dugumEkle(new KokDugumu(node.kelime().charAt(idx)));
                newNode.kopyala(node);
                node.temizle();
                node.kokEkle(kok);
                node.setKelime(icerik);
            }
            return;
        }

        /**
         * Kaldığımız düğüme bağlı bir kök yoksa bu kök için bir düğüm oluşturup ekleriz.
         */
        if (oncekiDugum.kok() == null || idx == oncekiDugum.kelime().length()) {
            oncekiDugum.dugumEkle(new KokDugumu(giris[idx], icerik, kok));
            return;
        }
        
        if (oncekiDugum.kelime().equals(icerik)) {
            oncekiDugum.kokEkle(kok);
            return;
        }

        /**
         * Düğümde duran "istimlak" ve gelen kök = "istim" için,
         * i-s-istimlak
         *
         * Önce  i-s-t-i-m-l düğümleri oluşturulur ve istimlak en uca, istim de bir 
         * gerisindeki m düğümüne bağlanır.
         * 
         * i-s-t-i-m-l-->istimlak
         *         |-->istim
         */
        char[] nodeHd = ((String)oncekiDugum.kelime()).toCharArray();
        KokDugumu newNode = oncekiDugum;

        if (nodeHd.length <= giris.length) {
            while (idx < nodeHd.length && nodeHd[idx] == giris[idx]) {
                newNode = newNode.dugumEkle(new KokDugumu(nodeHd[idx]));
                idx++;
            }

            // Kisa dugumun eklenmesi.
            if (idx < nodeHd.length) {
                KokDugumu temp = newNode.dugumEkle(new KokDugumu(nodeHd[idx]));
                temp.kopyala(oncekiDugum);
            } else {
                newNode.kopyala(oncekiDugum);
            }

            // Uzun olan dugumun (yeni gelen) eklenmesi
            newNode.dugumEkle(new KokDugumu(giris[idx], icerik, kok));
            oncekiDugum.temizle();
        }

        /**
         * Eğer köke önce "istimlak" i-s ye bnğlanmışsa ve sonra "istifa" gelirse
         * i-s-->istimlak
         *
         * t-i düğümleri oluşturulur, daha sonra istifa için f istimlak için de m
         * oluşturulur ve düğümler uygun şekilde bağlanır.
         * 
         * i-s-t-i-m-->istimlak
         *       |-f-->istifa
         *
         */

        else {
            while (idx < giris.length && giris[idx] == nodeHd[idx]) {
                newNode = newNode.dugumEkle(new KokDugumu(giris[idx]));
                idx++;
            }
            // Kisa dugumun eklenmesi.
            if (idx < giris.length) {
                newNode.dugumEkle(new KokDugumu(giris[idx], icerik, kok));
            } else {
                newNode.kokEkle(kok);
                newNode.setKelime(icerik);
            }

            // Uzun olan dugumun (yeni gelen) eklenmesi.
            newNode = newNode.dugumEkle(new KokDugumu(nodeHd[idx]));
            newNode.kopyala(oncekiDugum);
            // Es seslileri tasi.
            oncekiDugum.temizle();
        }
    }

    /**
     * Aranan bir kök düğümünü bulur.
     *
     * @param str
     * @return Aranan kök ve eş seslilerini taşıyan liste, bulunamazsa sifir uzunluklu liste.
     */
    public List<Kok> bul(String str) {
        char[] girisChars = str.toCharArray();
        int girisIndex = 0;
        // Basit bir tree traverse algoritması ile kelime bulunur.
        KokDugumu node = baslangicDugumu;
        while (node != null && girisIndex < girisChars.length) {
            if (node.kelime() != null && node.kelime().equals(str)) {
                break;
            }
            if (log.isLoggable(Level.FINE)) 
            	log.fine("Harf: " + node.harf()+ " Taranan Kelime: " + node.kelime());
            node = node.altDugumBul(girisChars[girisIndex++]);
        }
        if (node != null) {
            return node.tumKokler(str);
        }
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return baslangicDugumu.toString();
    }
}
