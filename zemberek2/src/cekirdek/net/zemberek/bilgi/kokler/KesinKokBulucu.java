/*
 * Created on 18.Eki.2004
 */
package net.zemberek.bilgi.kokler;

import java.util.ArrayList;
import java.util.List;

import net.zemberek.yapi.Kok;

/**
 * Çözümleyicinin verilen bir kelime için aday kökleri bulması için kullanılır.
 * Giriş kelimesinin ilk harfinden başlanarak ağaçta ilerlenir. �lerleyecek
 * İlerlenecek yer kalmayana veya kelime bitene dek ağaçta yürünür, 
 * ve rastlanan tüm kökler aday olarak toplanır.
 * 
 * Bu seçici, Balerinler kelimesi için "bal, bale ve balerin" köklerini taşıyan
 * bir liste döndürür.
 *
 * @author MDA
 */
public class KesinKokBulucu implements KokBulucu {
    KokAgaci agac = null;

    public KesinKokBulucu(KokAgaci agac) {
        this.agac = agac;
    }

    public List<Kok> getAdayKokler(final String giris) {
        List<Kok> adaylar = new ArrayList<Kok>(3);
        int girisIndex = 0;
        KokDugumu node = agac.getKokDugumu();

        while (girisIndex < giris.length()) {
            node = node.altDugumGetir(giris.charAt(girisIndex));
            if (node == null) break;
            if (node.getKok() != null) {
                // buradaki kodu daha basit ama biraz yavas hale getirdim.
                if (giris.startsWith((String) node.getKelime())) {
                    node.tumKokleriEkle(adaylar);
                }
            }
            girisIndex++;
        }
        return adaylar;
    }
}

