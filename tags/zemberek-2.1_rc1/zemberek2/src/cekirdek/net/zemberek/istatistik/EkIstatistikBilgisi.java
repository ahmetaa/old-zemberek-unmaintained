/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

/*
 * Created on 19.Haz.2005
 *
 */
package net.zemberek.istatistik;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.zemberek.araclar.IstatistikAraclari;
import net.zemberek.yapi.ek.Ek;

public class EkIstatistikBilgisi {
    public static long toplamKullanim = 0;
    private Ek ek;
    private int kullanimSayisi = 0;
    private HashMap<String, EkFrekansBilgisi>  ardisilEkler= new HashMap<String, EkFrekansBilgisi>();
    List<EkFrekansBilgisi> ardisilEkListesi = new ArrayList<EkFrekansBilgisi>();

    public EkIstatistikBilgisi(Ek ek) {
        this.ek = ek;
    }

    public void ardisilEkEkle(Ek ardisilEk) {
        EkFrekansBilgisi ekFrek = ardisilEkler.get(ardisilEk.ad());
        if (ekFrek == null) {
            ekFrek = new EkFrekansBilgisi(ardisilEk);
            if (ardisilEk.ad() == null) {
                System.out.println("null?");
            }
            ardisilEkler.put(ardisilEk.ad(), ekFrek);
        } else {
            ekFrek.kullanimArttir();
        }
        kullanimSayisi++;
        EkIstatistikBilgisi.toplamKullanim++;
    }

    public void duzenle() {
        ardisilEkListesi = new ArrayList<EkFrekansBilgisi>();
        for (EkFrekansBilgisi ekFreq : ardisilEkler.values()) {
            ekFreq.setKullanimFrekansi(IstatistikAraclari.yuzdeHesapla(ekFreq.getKullanim(), this.kullanimSayisi));
            ardisilEkListesi.add(ekFreq);

        }
        Collections.sort(ardisilEkListesi);
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(ek.ad() + " Kullanim : %" + IstatistikAraclari.onbindeHesaplaStr(this.kullanimSayisi, toplamKullanim) + "\n");
        for (Iterator<EkFrekansBilgisi> i = ardisilEkListesi.iterator(); i.hasNext();) {
            EkFrekansBilgisi freq = i.next();
            buffer.append("  " + freq.getEk().ad());
            buffer.append("  %" + IstatistikAraclari.onbindeHesaplaStr(freq.getKullanim(), this.kullanimSayisi));
            buffer.append("  (" + freq.getKullanim() + ")\n");
        }
        buffer.append("\n");
        return buffer.toString();
    }

    public List<EkFrekansBilgisi> getArdisilEkListesi() {
        return ardisilEkListesi;
    }

}
