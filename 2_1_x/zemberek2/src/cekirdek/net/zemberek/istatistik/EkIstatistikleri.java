/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

/*
 * Created on 19.Haz.2005
 *
 */
package net.zemberek.istatistik;

import java.util.HashMap;
import java.util.List;

import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.ek.Ek;
import net.zemberek.yapi.ek.TemelEkYonetici;


public class EkIstatistikleri implements Istatistik {

    private HashMap<String, EkIstatistikBilgisi> ekIstatistikBilgileri = new HashMap<String, EkIstatistikBilgisi>();

    public void istatistikGuncelle(Kelime kelime) {
        List<Ek> ekler = kelime.ekler();
        // kelime sonu için bir null ek ekliyoruz.
        ekler.add(TemelEkYonetici.BOS_EK);
        for (int i = 0; i < ekler.size() - 1; i++) {
            Ek ek = ekler.get(i);
            EkIstatistikBilgisi ekBilgisi = ekIstatistikBilgileri.get(ek.ad());
            if (ekBilgisi == null) {
                ekBilgisi = new EkIstatistikBilgisi(ek);
                ekIstatistikBilgileri.put(ek.ad(), ekBilgisi);
            }
            Ek ardisilEk = ekler.get(i + 1);
            ekBilgisi.ardisilEkEkle(ardisilEk);
        }
    }

    public void tamamla() {
        for (EkIstatistikBilgisi ekIstatistikBilgisi : ekIstatistikBilgileri.values()) {
            ekIstatistikBilgisi.duzenle();
        }
    }

    public String toString() {
        StringBuffer b = new StringBuffer();
        for (EkIstatistikBilgisi ekIstatistikBilgisi : ekIstatistikBilgileri.values()) {
            b.append(ekIstatistikBilgisi.toString());
        }
        return b.toString();
    }

    public void guncelle() {
    }


}
