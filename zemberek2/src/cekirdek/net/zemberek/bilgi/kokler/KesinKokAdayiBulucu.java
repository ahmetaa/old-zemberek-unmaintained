/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

/*
 * Created on 18.Eki.2004
 */
package net.zemberek.bilgi.kokler;

import java.util.ArrayList;
import java.util.List;

import net.zemberek.yapi.Kok;

/**
 * Çözümleyicinin verilen bir kelime için aday kökleri bulması için kullanılır.
 * Giriş kelimesinin ilk harfinden başlanarak ağaçta ilerlenir. ilerleyecek
 * yer kalmayana veya kelime bitene dek ağaçta yürünürve rastlanan tüm kökler
 * aday olarak toplanır.
 * 
 * Örneğin, "Balerinler" kelimesi için "bal, bale ve balerin" köklerini taşıyan
 * bir liste döndürür.
 *
 * @author MDA
 */
public class KesinKokAdayiBulucu implements KokAdayiBulucu {
    KokAgaci agac = null;

    public KesinKokAdayiBulucu(KokAgaci agac) {
        this.agac = agac;
    }

    public List<Kok> adayKokleriBul(final String giris) {
        List<Kok> adaylar = new ArrayList<Kok>(3);
        int girisIndex = 0;
        KokDugumu node = agac.getKokDugumu();

        while (girisIndex < giris.length()) {
            node = node.altDugumBul(giris.charAt(girisIndex));
            if (node == null) break;
            if (node.kok() != null) {
                if (giris.startsWith((String) node.kelime())) {
                    node.tumKokleriEkle(adaylar);
                }
            }
            girisIndex++;
        }
        return adaylar;
    }
}

