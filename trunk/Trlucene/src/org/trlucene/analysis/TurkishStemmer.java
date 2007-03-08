package org.trlucene.analysis;

import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;

public class TurkishStemmer {
    
    Zemberek zemberek = null;
    public TurkishStemmer(){
        // Create a zemberek turkish NLP lib instance.
        zemberek = new Zemberek(new TurkiyeTurkcesi());
    }
    
    public String stem(String token) {
        String[] stems = zemberek.kokBulucu().stringKokBul(token);
        // Now we should disambiguate alternatives, but we dont have a decent disambiguator yet.
        if (stems.length != 0){
            return stems[0];
        }
        return token;
    }

}
