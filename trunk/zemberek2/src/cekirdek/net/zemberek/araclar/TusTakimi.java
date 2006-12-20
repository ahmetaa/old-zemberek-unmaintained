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
 *  The Original Code is Zemberek Doğal Dil İşleme Kütüphanesi.
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akın, Mehmet D. Akın.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

/*
 * Created on 30.Tem.2005
 *
 */
package net.zemberek.araclar;

import java.util.HashMap;

public class TusTakimi {
    // Standart Türkçe Q Klavye haritası.
    public static char[][] qKlavyeHaritasi = new char[][]{
        {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '*', '-'},
        {'q', 'w', 'e', 'r', 't', 'y', 'u', '\u0131', 'o', 'p', '\u011f', '\u00fc'},
        {'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', '\u015f', 'i', ',', '#'},
        {'z', 'x', 'c', 'v', 'b', 'n', 'm', '\u00f6', '\u00e7', '.', '.','.'}};
    
    // Standart Türkçe F Klavye haritası.
    public static char[][] fKlavyeHaritasi = new char[][]{
        {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '*', '-'},
        {'f', 'g', '\u011f', '\u0131', 'o', 'd', 'r', 'n', 'h', 'p', 'q', 'w'},
        {'u', 'i', 'e', 'a', '\u00fc', 't', 'k', 'm', 'l', 'y','\u015f', 'x'},
        {'j', '\u00f6', 'v', 'c', '\u00e7', 'z', 's', 'b', '.', ','}};
    
    private char[][] klavyeHaritasi = null;
    private HashMap<Character, KarakterKoordinati> koordinatlar = new HashMap<Character, KarakterKoordinati>();
    
    private static TusTakimi q = new TusTakimi(qKlavyeHaritasi);
    private static TusTakimi f = new TusTakimi(fKlavyeHaritasi);
    
    public TusTakimi(char[][] klavyeHaritasi){
    	this.klavyeHaritasi = klavyeHaritasi;
        for(int i=0; i<klavyeHaritasi.length; i++){
            for(int j=0; j<klavyeHaritasi[i].length; j++){
                char c = klavyeHaritasi[i][j];
                koordinatlar.put(c, new KarakterKoordinati(c,i,j));
            }
        }
    }
    
    public static TusTakimi trQ(){
    	return q;
    }
    
    public static TusTakimi trF(){
    	return f;
    }
    
    public KarakterKoordinati koordinat(char c){
    	return koordinatlar.get(c);
    }
    
    public KarakterKoordinati koordinat(int i, int j){
    	return koordinatlar.get(klavyeHaritasi[i][j]);
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
    public int mesafeHesapla(char c1, char c2){
        KarakterKoordinati k1 = koordinatlar.get(new Character(c1));
        KarakterKoordinati k2 = koordinatlar.get(new Character(c2));
        if(k1 == null || k2 == null){
            return -1;
        }
        double mesafeD = Math.sqrt((double)(k1.x - k2.x)*(k1.x - k2.x) + (k1.y - k2.y)*(k1.y - k2.y));
        int normalizeMesafe = (int)(mesafeD*10);
        return normalizeMesafe;
    }
    
    public String toStirng(){
    	return koordinatlar.toString();
    }
    
    public class KarakterKoordinati{
        char c;
        int x, y;
        
        public KarakterKoordinati(char c, int x, int y) {
            this.c = c;
            this.x = x;
            this.y = y;
        }
        
        public String toString(){
        	return "(" + x + "," + y + ") " + c; 
        }
    }

	public char karakter(int i, int j) {
		// TODO Auto-generated method stub
		return klavyeHaritasi[i][j];
	}
}
