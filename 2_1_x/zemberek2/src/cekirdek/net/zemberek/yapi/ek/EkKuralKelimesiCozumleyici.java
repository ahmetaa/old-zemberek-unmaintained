/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.yapi.ek;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import net.zemberek.yapi.Alfabe;

/**
 * Ek bilgi dosyasinda yer alan ek kural kelimeinin cozumlenmesinde kullanilir.
 *
 * Basit bir tokenizer. Kural kelimesini dile gore ozel kural bilgilerini kullanarak
 * EkUretimBileseni listesine donusturur.
 */
public class EkKuralKelimesiCozumleyici {

    private Alfabe alfabe;
    /**
     * Ek kural bilgisi nesnesi dile ozel ek kural kelime enum sinifindan elde edilir.
     */
    private EkKuralBilgisi ekKuralBilgisi;


    public EkKuralKelimesiCozumleyici(Alfabe alfabe, EkKuralBilgisi ekKuralBilgisi) {
        this.alfabe = alfabe;
        this.ekKuralBilgisi = ekKuralBilgisi;
    }

    public List<EkUretimBileseni> cozumle(String uretimKelimesi) {
        if (uretimKelimesi == null || uretimKelimesi.length() == 0)
            return Collections.emptyList();
        List<EkUretimBileseni> bilesenler = new ArrayList<EkUretimBileseni>();
        Iterator<EkUretimBileseni> it = new BilesenIterator(uretimKelimesi.trim().replaceAll("[ ]", ""));
        while (it.hasNext())
            bilesenler.add(it.next());
        return bilesenler;
    }

    class BilesenIterator implements Iterator<EkUretimBileseni> {

        private int pointer;
        private final String uretimKelimesi;


        public BilesenIterator(String uretimKelimesi) {
            this.uretimKelimesi = uretimKelimesi;
        }

        public boolean hasNext() {
            return uretimKelimesi != null && pointer < uretimKelimesi.length();
        }

        public EkUretimBileseni next() {
            if (!hasNext()) {
                throw new NoSuchElementException("bilesen kalmadi!");
            }
            char p = uretimKelimesi.charAt(pointer++);
            //ardisil harf ile iliskili kuralmi
            if (ekKuralBilgisi.harfKuralKarakterleri().contains(p)) {
                if (pointer == uretimKelimesi.length())
                    throw new IllegalArgumentException(p + " kuralindan sonra normal harf bekleniyordu!");
                char h = uretimKelimesi.charAt(pointer++);
                if (ekKuralBilgisi.sesliKuralKarakterleri().contains(h))
                    throw new IllegalArgumentException(p + " kuralindan sonra sesli uretim kurali gelemez:" + h);
                return new EkUretimBileseni(ekKuralBilgisi.karakterKuralTablosu().get(p), alfabe.harf(h));
            } else if (ekKuralBilgisi.sesliKuralKarakterleri().contains(p)) {
                return new EkUretimBileseni(ekKuralBilgisi.karakterKuralTablosu().get(p), Alfabe.TANIMSIZ_HARF);
            } else if (alfabe.harf(p) != null && Character.isLowerCase(p)) {
                return new EkUretimBileseni(ekKuralBilgisi.harfEklemeKurali(), alfabe.harf(p));
            } else {
                throw new IllegalArgumentException(p + "  simgesi cozumlenemiyor.. kelime:" + uretimKelimesi);
            }
        }

        public void remove() {
        }
    }
}