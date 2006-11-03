/*
 * Created on 19.Haz.2005
 *
 */
package net.zemberek.istatistik;

import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.ek.Ek;
import net.zemberek.yapi.ek.TemelEkYonetici;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class EkIstatistikleri implements Istatistik {

    private HashMap ekIstatistikBilgileri = new HashMap();

    public void istatistikGuncelle(Kelime kelime) {
        List ekler = kelime.ekler();
        // kelime sonu için bir null ek ekliyoruz.
        ekler.add(TemelEkYonetici.BOS_EK);
        for (int i = 0; i < ekler.size() - 1; i++) {
            Ek ek = (Ek) ekler.get(i);
            EkIstatistikBilgisi ekBilgisi = (EkIstatistikBilgisi) ekIstatistikBilgileri.get(ek.ad());
            if (ekBilgisi == null) {
                ekBilgisi = new EkIstatistikBilgisi(ek);
                ekIstatistikBilgileri.put(ek.ad(), ekBilgisi);
            }
            Ek ardisilEk = (Ek) ekler.get(i + 1);
            ekBilgisi.ardisilEkEkle(ardisilEk);
        }
    }

    public void tamamla() {
        for (Iterator i = ekIstatistikBilgileri.values().iterator(); i.hasNext();) {
            EkIstatistikBilgisi ekBilgisi = (EkIstatistikBilgisi) i.next();
            ekBilgisi.duzenle();
        }
    }

    public String toString() {
        StringBuffer b = new StringBuffer();
        for (Iterator i = ekIstatistikBilgileri.values().iterator(); i.hasNext();) {
            EkIstatistikBilgisi ekBilgisi = (EkIstatistikBilgisi) i.next();
            b.append(ekBilgisi.toString());
        }
        return b.toString();
    }

    public void guncelle() {
    }


}
