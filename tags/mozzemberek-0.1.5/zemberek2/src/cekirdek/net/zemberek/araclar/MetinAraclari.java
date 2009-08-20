/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.araclar;

/**
 * Genel metin araçları. String benzerliği, Q klavye mesafesi gibi fonksiyonları barındırır.
 * @author MDA
 */
public class MetinAraclari {

    private static JaroWinkler jaroWinkler = new JaroWinkler();

    /**
     * Degistirilmis Levenshtein Edit Dist. algoritması. transpozisyonları da 1 düzeltme mesafesi
     * olarak hesaplar. 
     * TODO: Alphan'ın mesafe aşımını sezip kısa kesme kodunu ekle.
     * @param s kaynak
     * @param t hedef
     * @param limit sinir degeri
     * @return iki kelime arasindaki mesafe, tamsayi cinsinden. kucuk rakamlar daha buyuk benzerligi gosterir.
     */
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

  /**
   * Verilen s1 stringinin verilen distance düzeltme mesafesi çerçevesinde
   * s2 stringinin alt stringi olup olmadığını döndürürr. Örneğin:
   * <pre>
   * isInSubStringLevenshteinDistance("elma","ekmalar",1) -> true
   * isInSubStringLevenshteinDistance("elma","emalar",1) -> true
   * isInSubStringLevenshteinDistance("elma","eksalar",1) -> false (substring min dist=2)
   * </pre>
   *
   * @param s1       :
   * @param s2       : s1'i distance düzeltme mesafesi içinde kapsayıp kapsamadığı araştırılan String
   * @param distance : düzeltme mesafesi
   * @return eger istenilen mesafede is true.
   */
  public static boolean parcasiDuzeltmeMesafesiIcinde(String s1, String s2, int distance)
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

    public static int duzeltmeMesafesi(String source, String target)
    {
        int maxDif = Math.max(source.length(), target.length());
        return duzeltmeMesafesi(source, target, maxDif);
    }

    /**
     * s1 ile s2'nin benzerlik oranini hesaplar.
     *
     * @param s1
     * @param s2
     * @return 0-1.0 arasi bir deger. Buyuk rakamlar kelimelerin daha benzer oldugunu gosterir.
     */
    public static double sozcukBenzerlikOrani(String s1, String s2) {
        return jaroWinkler.benzerlikOrani(s1, s2);
    }

    /**
     * s1 ile s2'nin enazBenzerlik degeri kadar ya da daha benzer olup olmadigini test eder.
     *
     * @param s1
     * @param s2
     * @param enazBenzerlik
     * @return eger benzerlik orani enazBenzerlik'na es ya da buyukse true
     */
    public static boolean sozcukBenzerlikTesti(String s1, String s2, double enazBenzerlik) {
        return (jaroWinkler.benzerlikOrani(s1, s2) >= enazBenzerlik);
    }
    
}
