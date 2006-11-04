package net.zemberek.demo;

import net.zemberek.erisim.Zemberek;
import net.zemberek.yapi.DilAyarlari;

/**
 * User: ahmet
 * Date: Jun 23, 2006
 */
public enum TurkDiliTuru {

    TURKIYE("net.zemberek.tr.yapi.TurkiyeTurkcesi"),
    TURKMEN("net.zemberek.tm.yapi.Turkmence"),
    AZERI("net.zemberek.az.yapi.Azerice");

    //KAZAK("kk"),
    //OZBEK("uz"),
    //TATAR("tt"),
    //UYGUR("ug");

    TurkDiliTuru(String sinif) {
        this.sinif = sinif;
    }

    private String sinif;

    public String sinif() {
        return sinif;
    }

    public Zemberek zemberekUret() throws ClassNotFoundException {
        try {
            DilAyarlari da = (DilAyarlari) Class.forName(sinif).newInstance();
            return new Zemberek(da);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean sinifVarmi() throws ClassNotFoundException {
        try {
            this.getClass().getClassLoader().loadClass(sinif);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}