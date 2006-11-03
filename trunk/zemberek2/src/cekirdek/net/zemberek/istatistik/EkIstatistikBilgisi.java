/*
 * Created on 19.Haz.2005
 *
 */
package net.zemberek.istatistik;

import net.zemberek.araclar.IstatistikAraclari;
import net.zemberek.yapi.ek.Ek;

import java.util.*;

public class EkIstatistikBilgisi {
    public static long toplamKullanim = 0;
    private Ek ek;
    private int kullanimSayisi = 0;
    private HashMap ardisilEkler = new HashMap();
    List ardisilEkListesi = new ArrayList();

    public EkIstatistikBilgisi(Ek ek) {
        this.ek = ek;
    }

    public void ardisilEkEkle(Ek ardisilEk) {
        EkFrekansBilgisi ekFrek = (EkFrekansBilgisi) ardisilEkler.get(ardisilEk.ad());
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
        ardisilEkListesi = new ArrayList();
        for (Iterator it = ardisilEkler.values().iterator(); it.hasNext();) {
            EkFrekansBilgisi ekFreq = (EkFrekansBilgisi) it.next();
            ekFreq.setKullanimFrekansi(IstatistikAraclari.yuzdeHesapla(ekFreq.getKullanim(), this.kullanimSayisi));
            ardisilEkListesi.add(ekFreq);

        }
        Collections.sort(ardisilEkListesi);
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(ek.ad() + " Kullanim : %" + IstatistikAraclari.onbindeHesaplaStr(this.kullanimSayisi, toplamKullanim) + "\n");
        for (Iterator i = ardisilEkListesi.iterator(); i.hasNext();) {
            EkFrekansBilgisi freq = (EkFrekansBilgisi) i.next();
            buffer.append("  " + freq.getEk().ad());
            buffer.append("  %" + IstatistikAraclari.onbindeHesaplaStr(freq.getKullanim(), this.kullanimSayisi));
            buffer.append("  (" + freq.getKullanim() + ")\n");
        }
        buffer.append("\n");
        return buffer.toString();
    }

    public List getArdisilEkListesi() {
        return ardisilEkListesi;
    }

}
