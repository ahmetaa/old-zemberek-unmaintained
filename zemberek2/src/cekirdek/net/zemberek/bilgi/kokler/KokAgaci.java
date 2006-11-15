/*
 * Created on 28.Eyl.2004
 */
package net.zemberek.bilgi.kokler;

import net.zemberek.yapi.Alfabe;
import net.zemberek.yapi.Kok;
import net.zemberek.araclar.Kayitci;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Kök ağacı zemberek sisteminin temel veri taşıyıcılarından biridir. Kök 
 * sözlüğünden okunan tüm kökler bu ağaca yerleştirilirler. Ağacın oluşumundan 
 * AgacSozluk sınıfı sorumludur.
 * Kök ağacı kompakt DAWG (Directed Acyclic Word Graph) ve Patricia tree benzeri
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
 * Kod içerisinde hangi durumda nasıl düğüm oluşturulduğu detaylarıyla 
 * anlatılmıştır.
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
        char[] hd = icerik.toCharArray();
        KokDugumu node = baslangicDugumu;
        KokDugumu oncekiDugum = null;
        int idx = 0;
        // null alt düğüm bulana dek veya kelimenin sonuna dek alt düğümlerde ilerle
        while (idx < hd.length) {
            oncekiDugum = node;
            node = node.altDugumGetir(hd[idx]);
            if (node == null) break;
            idx++;
        }
        /**
         * Ağaç üzerinde ilerlerken kelimemizin sonuna kadar gitmişiz.
         * kelimemizi bu düğüme ekleriz.
         * �rne�in
         * i-s-t-->istim �eklindeki dala "is" kelimesini eklemek gibi.
         * i-s-t-->istim
         *   |-->is
         *
         * veya
         *
         * i-s-->istim e is gelirse de
         * i-s-t-->istim
         *   |-->is
         *
         * i-s-->is  e "is" gelirse
         * i-s-->is(2) olmalı.
         *
         */
        if (idx == hd.length) {
            if (node.altDugumVarMi()) {
                node.kokEkle(kok);
                node.setKelime(icerik);
            }
            // Eş sesli!
            else if (node.getKelime().equals(icerik)) {
                node.kokEkle(kok);
                return;
            } else if (node.getKok() != null) {
                KokDugumu newNode = node.addNode(new KokDugumu(node.getKelime().charAt(idx)));
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
        if (oncekiDugum.getKok() == null && idx < hd.length) {
            oncekiDugum.addNode(new KokDugumu(hd[idx], icerik, kok));
            return;
        }

        if (oncekiDugum.getKelime().equals(icerik)) {
            oncekiDugum.kokEkle(kok);
            return;
        }

        /**
         * Düğümde duran "istimlak" ve gelen kök = "istimbot" için,
         * i-s-t-i-m
         * e kadar ilerler. daha sonra "istimlak" için "l" düğümünü oluşturup kökü bağlar
         * i-s-t-i-m-l-->istimlak
         * sonra da diğer düğüm için "b" düğümünü oluşturup gene "m" düğümüne bağlar
         * i-s-t-i-m-l-->istimlak
         *         |-b-->istimbot
         *
         * Eğer istimlak düğümü bağlanmışsa ve "istim" düğümü eklenmek üzere 
         * elimize gelmişe
         * i-s-t-i-m-l-->istimlak
         * tan sonra istim, "m" düğümüne doğrudan bağlanır.
         * i-s-t-i-m-l-->istimlak
         *         |-->istim
         *
         */
        char[] nodeHd = ((String)oncekiDugum.getKelime()).toCharArray();
        //char[] nodeChars = ((String) oncekiDugum.getKelime()).toCharArray();
        KokDugumu newNode = oncekiDugum;

        if (idx == nodeHd.length) {
            newNode.addNode(new KokDugumu(hd[idx], icerik, kok));
            return;
        }

        if (oncekiDugum.getKelime().length() == idx) {
            newNode.addNode(new KokDugumu(hd[idx], icerik, kok));
            return;
        }

        if (nodeHd.length <= hd.length) {
            while (idx < nodeHd.length && nodeHd[idx] == hd[idx]) {
                newNode = newNode.addNode(new KokDugumu(nodeHd[idx]));
                idx++;
            }

            // Kisa dugumun eklenmesi.
            if (idx < nodeHd.length) {
                KokDugumu temp = newNode.addNode(new KokDugumu(nodeHd[idx]));
                temp.kopyala(oncekiDugum);
            } else {
                newNode.kopyala(oncekiDugum);
            }

            // Uzun olan dugumun (yeni gelen) eklenmesi, es anlamlilari kotar
            newNode.addNode(new KokDugumu(hd[idx], icerik, kok));
            oncekiDugum.temizle();
            return;
        }

        /**
         *
         * Eğer köke önce "istimlak" ve sonra "istifa" gelirse
         * i-s-t-i-m-l-->istimlak
         * daha sonra gene son ortak harf olan "i" ye "f" karakterli düğümü
         * oluşturup istifayı bağlar
         * istimlak ta "m" düğümüne bağlı kalır.
         * i-s-t-i-m-->istimlak
         *       |-f-->istifa
         *
         */

        else {
            while (idx < hd.length && hd[idx] == nodeHd[idx]) {
                newNode = newNode.addNode(new KokDugumu(hd[idx]));
                idx++;
            }
            // Kisa dugumun eklenmesi.
            if (idx < hd.length) {
                newNode.addNode(new KokDugumu(hd[idx], icerik, kok));
            } else {
                newNode.kokEkle(kok);
                newNode.setKelime(icerik);
            }

            // Uzun olan dugumun (yeni gelen) eklenmesi.
            newNode = newNode.addNode(new KokDugumu(nodeHd[idx]));
            newNode.kopyala(oncekiDugum);
            // Es seslileri tasi.
            oncekiDugum.temizle();
            return;
        }
    }

    /**
     * Aranan bir kök düğümünü bulur.
     *
     * @param str
     * @return Aranan kök ve eş seslilerini taşıyan liste, bulunamazsa null.
     */
    public List<Kok> find(String str) {
        char[] girisChars = str.toCharArray();
        int girisIndex = 0;
        // Basit bir tree traverse algoritması ile kelime bulunur.
        KokDugumu node = baslangicDugumu;
        while (node != null && girisIndex < girisChars.length) {
            if (node.getKelime() != null && node.getKelime().equals(str)) {
                break;
            }
            if (log.isLoggable(Level.FINE)) 
            	log.fine("Harf: " + node.getHarf()+ " Taranan Kelime: " + node.getKelime());
            node = node.altDugumGetir(girisChars[girisIndex++]);
        }
        if (node != null) {
            return node.tumKokleriGetir();
        }
        return null;
    }

    public String toString() {
        return baslangicDugumu.toString();
    }
}
