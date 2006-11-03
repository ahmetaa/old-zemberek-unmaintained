package net.zemberek.yapi;

import java.io.IOException;


public class DilBilgisiUretici {


    public static final String TR_SINIF = "net.zemberek.tr.yapi.TurkiyeTurkcesi";
    public static final String TM_SINIF = "net.zemberek.tm.yapi.Turkmence";

    public static void main(String[] args) throws IOException {
        if (args.length > 0) {
            String dilAdi = args[0].toLowerCase().trim();
            uret(dilAdi);
            System.exit(0);

        } else {
            System.out.println("Dil adi girmelisiniz (tr,tm,az gibi)");
            System.exit(1);
        }
    }

    public static void uret(String dilAdi) throws IOException {
        DilAyarlari dilAyari = null;

        if (dilAdi.equals("tr"))
            dilAyari = dilAyarUret(TR_SINIF);
        else if (dilAdi.equals("tm"))
            dilAyari = dilAyarUret(TM_SINIF);
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


