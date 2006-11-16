/*
 * Created on 30.Tem.2005
 *
 */
package net.zemberek.araclar;

import java.util.HashMap;

public class QTusTakimi {
    // Standart Türkçe Q Klavye haritası.
    public static char[][] qKlavyeHaritasi = new char[][]{
        {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '*', '-'},
        {'q', 'w', 'e', 'r', 't', 'y', 'u', '\u0131', 'o', 'p', '\u011f', '\u00fc'},
        {'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', '\u015f', 'i', ','},
        {'<', 'z', 'x', 'c', 'v', 'b', 'n', 'm', '\u00f6', '\u00e7', '.', '.'}};
    
    private static HashMap koordinatlar = new HashMap();
    static QTusTakimi instance = new QTusTakimi();
    static{
        for(int i=0; i<qKlavyeHaritasi.length; i++){
            for(int j=0; j<qKlavyeHaritasi[0].length; j++){
                char c = qKlavyeHaritasi[i][j];
                koordinatlar.put(new Character(c),
                        instance.new KarakterKoordinati(c,i,j)
                        );
            }
        }
    }
    
    /**
     * Verilen iki karakter arasındaki klavye mesafesini getirir.      
     * Mesafenin ölçümünde sadece karakterlerin koordinatları arasındaki mesafe 
     * sqrt((y2-y1)^2 + (x2-x1)^2))*10 şeklinde hesaplanıyor. Yani a-s arasındaki mesafe 
     * 10 iken s-q arasındaki mesafe 14, a-e arasında ise 22 olur. Karakterlerin 
     * klavyedeki yerleri arasındaki  mesafe arttıkça rakam büyür.
     * 
     * @param c1 birinci karakter
     * @param c2 ikinci karakter
     * @return  Q Klavye üzerinde verilen karakterler arasındaki fiziksel mesafenin 10 katı.
     * Eğer karakterlerden herhangi biri klavye haritasında yoksa -1 döner.
     *  
     */
    public static int mesafeHesapla(char c1, char c2){
        KarakterKoordinati k1 = (KarakterKoordinati)koordinatlar.get(new Character(c1));
        KarakterKoordinati k2 = (KarakterKoordinati)koordinatlar.get(new Character(c2));
        if(k1 == null || k2 == null){
            return -1;
        }
        double mesafeD = Math.sqrt((double)(k1.x - k2.x)*(k1.x - k2.x) + (k1.y - k2.y)*(k1.y - k2.y));
        int normalizeMesafe = (int)(mesafeD*10);
        return normalizeMesafe;
    }
    
    private class KarakterKoordinati{
        char c;
        int x, y;
        
        public KarakterKoordinati(char c, int x, int y) {
            this.c = c;
            this.x = x;
            this.y = y;
        }
    }
}
