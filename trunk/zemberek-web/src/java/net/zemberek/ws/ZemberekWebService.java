/*
 * ZemberekWebService.java
 *
 * Created on 04 Şubat 2007 Pazar, 10:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.zemberek.ws;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import java.util.List;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.Kok;
/**
 *
 * @author firari
 */
@WebService
public class ZemberekWebService {
    
    private Zemberek zemberek;
    
    public ZemberekWebService() {
        zemberek=new Zemberek(new TurkiyeTurkcesi());
    }
    
    /*
    public Kelime[] asciiCozumle(String giris) {
        return zemberek.asciiCozumle(giris);
    }
    */
    
    public String[] asciidenTurkceye(String giris) {
        return zemberek.asciidenTurkceye(giris);
    }
    
    public String asciiyeDonustur(String giris) {
        return zemberek.asciiyeDonustur(giris);
    }
    
    public String[] hecele(String giris) {
        return zemberek.hecele(giris);
    }
    
    /*
    public HashSet<ArrayList<String>> kelimeAyristir(String giris) {
        return (HashSet<ArrayList<String>>) zemberek.kelimeAyristir(giris);
    }
    */
    
    /*
    public Kelime[] kelimeCozumle(String giris) {
        return zemberek.kelimeCozumle(giris);
    }
    */
    
    public boolean kelimeDenetle(String giris) {
        return zemberek.kelimeDenetle(giris);
    }
    
    
    public String[] oner(String giris) {
        return zemberek.oner(giris);
    }
    
    public String temizle(String giris) {
        return zemberek.temizle(giris);
    }
    
    /*
    public String kelimeUret(Kok giris, List ekler) {
        return zemberek.kelimeUret(giris,ekler);
    }
    */
    
}
