/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.erisim;

import java.io.IOException;

import net.zemberek.yapi.DilAyarlari;
import net.zemberek.yapi.TurkceDilBilgisi;


public class DilBilgisiUretici {

    public static final String TR_SINIF = "net.zemberek.tr.yapi.TurkiyeTurkcesi";
    public static final String TM_SINIF = "net.zemberek.tk.yapi.Turkmence";
    public static final String AZ_SINIF = "net.zemberek.az.yapi.Azerice";
    public static final String TT_SINIF = "net.zemberek.tt.yapi.Tatarca";
    
    public static void main(String[] args) throws IOException {
        if (args.length > 0) {
            String dilAdi = args[0].toLowerCase().trim();
            uret(dilAdi);
            System.exit(0);

        } else {
            System.out.println("Dil adi girmelisiniz (tr,tk,az,tt gibi)");
            System.exit(1);
        }
    }

    public static void uret(String dilAdi) throws IOException {
        DilAyarlari dilAyari = null;

        if (dilAdi.equals("tr"))
            dilAyari = dilAyarUret(TR_SINIF);
        else if (dilAdi.equals("tk"))
            dilAyari = dilAyarUret(TM_SINIF);
        else if(dilAdi.equals("az"))
        	dilAyari=dilAyarUret(AZ_SINIF);
        else if(dilAdi.equals("tt"))
        	dilAyari=dilAyarUret(TT_SINIF);
        else {
            System.out.println("Dil sinifi bulunamiyor:" + dilAdi);
            System.exit(1);
        }

        new TurkceDilBilgisi(dilAyari).ikiliKokDosyasiUret();


    }

    public static DilAyarlari dilAyarUret(String sinifadi) {
        Class c = null;
        try {
            c = Class.forName(sinifadi);
        } catch (ClassNotFoundException e) {
            System.out.println("Sinif bulunamadi!:" + sinifadi);
            System.exit(1);
        }
        try {
            return (DilAyarlari) c.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}


