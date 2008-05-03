/*
 * Created on 07.Oca.2005
 *
 */
package net.zemberek.ooo;

import net.zemberek.araclar.TimeTracker;
import net.zemberek.erisim.Zemberek;
import net.zemberek.islemler.HeceIslemleri;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;

/**
 * @author MDA
 */
public class ZemberekLibrary {

    private static Zemberek zemberek;
    private static boolean initialized = false;
    private boolean useServer = false;
    private boolean checkZemberekServer = false;
    private ZemberekClient client;
    static private HeceIslemleri heceleyici = null;

    public ZemberekLibrary() {
        initialize();
    }

    public synchronized void initialize() {
        if (initialized == true) return;
        if(checkZemberekServer == false){
            loadZemberekLibrary();
            initialized = true;
            return;
        }
        // Şimdilik sadece yerel kütüphane yüklensin.
        useServer = false;
        loadZemberekLibrary();
//        
//        // Önce Zemberek Server'e bağlanmaya çalışıyoruz.
//        try {
//            client = new ZemberekClient();
//            denetle("merhaba");
//            useServer = true;
//            initialized = true;
//        } catch (Exception e1) {
//            e1.printStackTrace();
//            System.out.println("Zemberek sunucusuna bağlanılamadı. Zemberek kütüphanesi yükleniliyor.");
//            useServer = false;
//            loadZemberekLibrary();
//        }
    }


    private void loadZemberekLibrary() {
        try {
            TimeTracker.startClock("z");
            zemberek = new Zemberek(new TurkiyeTurkcesi());
            heceleyici = zemberek.heceleyici();
            System.out.println("Zemberek kütüphanesi yüklenme süresi: " + TimeTracker.getElapsedTimeString("z"));
            initialized = true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println("Runtime Exception! oops..");
        }
    }

    public boolean denetle(String kelime) {
        if (useServer == true) {
            try {
                String kodlanmisMesaj = "* " + kelime;
                client.sendMessage(client.mesajKodla(kodlanmisMesaj));
                String cevap = client.readMessage();
                // cevabı değerlendir.
                if (cevap.equals("*")) return true;
                else
                    return false;
            } catch (Exception e) {
                e.printStackTrace();
                useServer = false;
                client.disconnect();
                loadZemberekLibrary();
                return denetle(kelime);
            }
        } else {
            return zemberek.kelimeDenetle(kelime);
        }
    }

    public String[] oner(String kelime) {
        if (useServer == true) {
            try {
                String kodlanmisMesaj = "& " + kelime;
                client.sendMessage(client.mesajKodla(kodlanmisMesaj));
                String cevap = client.readMessage();
                // cevabı değerlendir.
                return onerileriGetir(cevap);
                //if(cevap.equals("*")) return true;
            } catch (Exception e) {
                e.printStackTrace();
                useServer = false;
                client.disconnect();
                loadZemberekLibrary();
                return oner(kelime);
            }
        } else {
            return zemberek.oner(kelime);
        }
    }

    private String[] onerileriGetir(String cevap) {
        int start = cevap.indexOf('(');
        int end = cevap.lastIndexOf(')');
        if (start == -1 || end == -1 || end <= start) {
            return null;
        }
        String hamOneriler = cevap.substring(start + 1, end);
        return hamOneriler.split(",");
    }

    public static int[] hecele(String kelime) {
    	if(initialized == false) return null;
        kelime = zemberek.dilBilgisi().alfabe().ayikla(kelime);
        return heceleyici.heceIndeksleriniBul(kelime);
    }
}
