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
 *  The Original Code is "Zemberek Dogal Dil Isleme Kutuphanesi"
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akin, Mehmet D. Akin.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

package net.zemberek.deney;

import java.util.ArrayList;
import java.util.List;

import net.zemberek.araclar.MetinAraclari;
import net.zemberek.araclar.TimeTracker;
import net.zemberek.yapi.Alfabe;

public class MetinAraclari2 {

    private static int duzeltmeMesafesi(String s, String t, int limit)
    {
        int n = s.length(); //length of s
        int m = t.length(); //length of t
        int[][] d = new int[n + 1][m + 1]; // matrix
        int cost; // cost
        // Step 1
        if (n == 0) return m;
        if (m == 0) return n;
        // Step 2
        for (int i = 0; i <= n; d[i][0] = i++) ;
        for (int j = 0; j <= m; d[0][j] = j++) ;
        // Step 3
        for (int i = 1; i <= n; i++)
        {
            //Step 4
            for (int j = 1; j <= m; j++)
            {
                // Step 5
                cost = (t.charAt(j-1) == s.charAt(i-1) ? 0 : 1);
                // Step 6
                d[i][j] = Math.min(Math.min(d[i-1][j] + 1, d[i][j-1] + 1), d[i-1][j-1] + cost);
                // Step 6A
                if (i > 1 && j > 1)
                {
                    int trans = d[i-2][j-2] + 1;
                    if (s.charAt(i-2) != t.charAt(j-1)) trans++;
                    if (s.charAt(i-1) != t.charAt(j-2)) trans++;
                    if (d[i][j] > trans) d[i][j] = trans;
                }
            }
        }
        // Step 7
        return d[n][m] > limit ? limit + 1 : d[n][m];
    }

  public static boolean duzeltmeMesafesiIcinde(String source, String target, int dist)
  {
      return (duzeltmeMesafesi(source, target, dist) <= dist);
  }

  public static boolean ParcasiDuzeltmeMesafesiIcinde(String s1, String s2, int distance)
  {
      if (s2.length() < (s1.length() - distance))
          return false;

      if (s2.length() >= s1.length())
      {
          String test = s2.substring(0, s1.length());
          if (duzeltmeMesafesiIcinde(s1, test, distance)) return true;
          test = s2.substring(0, s1.length() - 1);
          if (duzeltmeMesafesiIcinde(s1, test, distance)) return true;
          if (s2.length() >= s1.length() + 1)
          {
              test = s2.substring(0, s1.length() + 1);
              if (duzeltmeMesafesiIcinde(s1, test, distance)) return true;
          }
      }
      else
      {
          if (duzeltmeMesafesiIcinde(s1, s2, distance)) return true;
      }
      return false;
  }

  
  public static List<String> adaylariBul(String s, char[] alfabe){
	  List<String> adaylar = new ArrayList<String>(20);
	  // Tek harf Eksik olanları bul
	  for(int i=0; i< s.length(); i++){
		  adaylar.add( s.substring(0 ,i) + s.substring(i+1) ); 
 	  }
	  // Degisiklikleri ve eklemeleri bul
	  for(char c : alfabe){
		  for(int i=0; i< s.length(); i++){
			  adaylar.add( s.substring(0 ,i) + c + s.substring(i+1) ); 
	 	  }
		  for(int i=0; i< s.length(); i++){
			  adaylar.add( s.substring(0 ,i) + c + s.substring(i) );
	 	  }
	  }
	  // Transpozisyonları bul
	  for(int i=0; i< s.length()-1; i++){
		  adaylar.add( s.substring(0 ,i) + s.charAt(i+1) + s.charAt(i) + s.substring(i+2) ); 
 	  }
	  return adaylar;
  }

//
//    /// <summary>
//    /// Degistirilmis Levenshtein Edit Dist. algoritması. transpozisyonları da 1 düzeltme mesafesi olarak hesaplar.
//    /// </summary>
//    /// <param name="source"></param>
//    /// <param name="target"></param>
//    /// <returns>iki kelime arasindaki mesafe, tamsayi cinsinden. kucuk rakamlar daha buyuk benzerligi gosterir</returns>
    public static int duzeltmeMesafesi(String source, String target)
    {
        int maxDif = Math.max(source.length(), target.length());
        return duzeltmeMesafesi(source, target, maxDif);
    }

    public static void main(String[] args) {
    	List<String> adaylar = adaylariBul("merhaba", "abcdefghijklmnoprstuvyz".toCharArray());
    	System.out.println("Adaylar (" + adaylar.size() + ") :" + adaylar);

//    	boolean s = ParcasiDuzeltmeMesafesiIcinde("ebm", "elma", 1);
//    	if (s) System.out.println("ok");
//    	int iter = 100000;
//    	TimeTracker.startClock("l1");
//    	for(int i=0; i<iter; i++){
//    		MetinAraclari.editDistance("elma", "e");
//    		MetinAraclari.editDistance("elma", "el");
//    		MetinAraclari.editDistance("elma", "elm");
//    		MetinAraclari.editDistance("elma", "elma");
//    		MetinAraclari.editDistance("elma", "d");
//    		MetinAraclari.editDistance("elma", "ed");
//    		MetinAraclari.editDistance("elma", "exb");
//    		MetinAraclari.editDistance("elma", "eplo");
//    	}
//
//    	System.out.println(TimeTracker.getElapsedTimeString("l1") + " Saniyede: " + TimeTracker.getItemsPerSecond("l1", iter));
//
//    	TimeTracker.startClock("l2");
//    	for(int i=0; i<iter; i++){
//    		duzeltmeMesafesi("elma", "e");
//    		duzeltmeMesafesi("elma", "el");
//    		duzeltmeMesafesi("elma", "elm");
//    		duzeltmeMesafesi("elma", "elma");
//    		duzeltmeMesafesi("elma", "d");
//    		duzeltmeMesafesi("elma", "ed");
//    		duzeltmeMesafesi("elma", "exb");
//    		duzeltmeMesafesi("elma", "eplo");
//    	}
//    	System.out.println(TimeTracker.getElapsedTimeString("l2") + " Saniyede: " + TimeTracker.getItemsPerSecond("l2", iter));


	}
}
