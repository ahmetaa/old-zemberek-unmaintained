/*
 *  ***** BEGIN LICENSE BLOCK *****
 *
 *  Version: MPL 1.1
 *
 *  The contents of this file are subject to the Mozilla Public License Version
 *  1.1 (the "License"); you may not use this file except in compliance with
 *  the License. You may obtain a copy of the License at
 *  http://www.mozilla.org/MPL/
 *
 *  Software distributed under the License is distributed on an "AS IS" basis,
 *  WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 *  for the specific language governing rights and limitations under the
 *  License.
 *
 *  The Original Code is "Zemberek Sunucu"
 *
 *  The Initial Developer of the Original Code is
 *  Serkan Kaba.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberek.ws;

import javax.jws.*;
import javax.xml.ws.Endpoint;
import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
//import java.util.ArrayList;
import net.zemberek.yapi.Kelime;
//import net.zemberek.yapi.Kok;
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
    
    @WebMethod
    public String[] asciiCozumle(@WebParam(name="giris") String giris) {
        Kelime cozumlemeler[] = zemberek.asciiCozumle(giris);
        return kelimeArraytoStringArray(cozumlemeler);
    }
    
    @WebMethod
    public String[] asciidenTurkceye(@WebParam(name="giris") String giris) {
        return zemberek.asciidenTurkceye(giris);
    }
    
    @WebMethod
    public String asciiyeDonustur(@WebParam(name="giris") String giris) {
        return zemberek.asciiyeDonustur(giris);
    }
    
    @WebMethod
    public String[] hecele(@WebParam(name="giris") String giris) {
        return zemberek.hecele(giris);
    }
    
    /*
    @WebMethod
    public ArrayList<String[]> kelimeAyristir(@WebParam(name="giris") String giris) {
        return new ArrayList(zemberek.kelimeAyristir(giris));
    }
     */
    
    @WebMethod
    public String[] kelimeCozumle(@WebParam(name="giris") String giris) {
        Kelime cozumlemeler[] = zemberek.kelimeCozumle(giris);
        return kelimeArraytoStringArray(cozumlemeler);
    }
    
    @WebMethod
    public boolean kelimeDenetle(@WebParam(name="giris") String giris) {
        return zemberek.kelimeDenetle(giris);
    }
    
    @WebMethod
    public String[] oner(@WebParam(name="giris") String giris) {
        return zemberek.oner(giris);
    }
    
    /*
    public String kelimeUret(Kok giris, List ekler) {
        return zemberek.kelimeUret(giris,ekler);
    }
     */
    
    private String kelimeToString(Kelime k) {
        return
                "[ Kok:" +k.kok().icerik()+
                ", Tip:"+k.kok().tip()+
                " | Ekler:"+k.ekZinciriStr()+"]";
    }
    
    private String[] kelimeArraytoStringArray(Kelime k[]) {
        String kStr[] = new String[k.length];
        for(int i=0;i<k.length;i++)
            kStr[i]=kelimeToString(k[i]);
        return kStr;
    }
}
