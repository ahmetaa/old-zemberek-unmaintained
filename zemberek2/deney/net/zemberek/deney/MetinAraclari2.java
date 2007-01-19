package net.zemberek.deney;

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

//    /// <summary>
//    /// Verilen s1 stringinin verilen distance duzeltme mesafesi cercevesinde 
//    /// s2 stringinin alt stringi olup olmadigini dondurur. Ornegin:
//    ///   isInSubStringLevenshteinDistance("elma","ekmalar",1) -> true
//    ///   isInSubStringLevenshteinDistance("elma","emalar",1) -> true
//    ///   isInSubStringLevenshteinDistance("elma","eksalar",1) -> false (substring min dist=2)
//    /// </summary>
//    /// <param name="s1"></param>
//    /// <param name="s2">s1'i distance duzeltme mesafesi icinde kapsayıp kapsamadigi arastirilan String</param>
//    /// <param name="distance">duzeltme mesafesi</param>
//    /// <returns></returns>
//    public static bool ParcasiDuzeltmeMesafesiIcinde(String s1, String s2, int distance)
//    {
//        if (s2.Length < (s1.Length - distance))
//            return false;
//
//        if (s2.Length >= s1.Length)
//        {
//            String test = s2.Substring(0, s1.Length);
//            if (DuzeltmeMesafesiIcinde(s1, test, distance)) return true;
//            test = s2.Substring(0, s1.Length - 1);
//            if (DuzeltmeMesafesiIcinde(s1, test, distance)) return true;
//            if (s2.Length >= s1.Length + 1)
//            {
//                test = s2.Substring(0, s1.Length + 1);
//                if (DuzeltmeMesafesiIcinde(s1, test, distance)) return true;
//            }
//        }
//        else
//        {
//            if (DuzeltmeMesafesiIcinde(s1, s2, distance)) return true;
//        }
//        return false;
//    }
//    /// <summary>
//    /// Degistirilmis Levenshtein Edit Dist. algoritması. transpozisyonları da 1 düzeltme mesafesi olarak hesaplar.
//    /// </summary>
//    /// <param name="source"></param>
//    /// <param name="target"></param>
//    /// <returns>eger istenilen mesafede ise true</returns>
//    public static bool DuzeltmeMesafesiIcinde(string source, string target, int dist)
//    {
//        return (DuzeltmeMesafesi(source, target, dist) <= dist);
//    }
//  /// <summary>
//    /// Degistirilmis Levenshtein Edit Distance Algoritmasi. 
//    /// Transpozisyonları da 1 düzeltme mesafesi olarak hesapliyor.
//    /// </summary>
//    /// <param name="s">kaynak</param>
//    /// <param name="t">hedef</param>
//    /// <param name="limit">uzaklik</param>
//    /// <returns></returns>
//    private static int DuzeltmeMesafesi(string s, string t, int limit)
//    {
//        int n = s.Length; //length of s
//        int m = t.Length; //length of t
//        int[,] d = new int[n + 1, m + 1]; // matrix
//        int cost; // cost
//        // Step 1
//        if (n == 0) return m;
//        if (m == 0) return n;
//        // Step 2
//        for (int i = 0; i <= n; d[i, 0] = i++) ;
//        for (int j = 0; j <= m; d[0, j] = j++) ;
//        // Step 3
//        for (int i = 1; i <= n; i++)
//        {
//            //Step 4
//            for (int j = 1; j <= m; j++)
//            {
//                // Step 5
//                cost = (t.Substring(j - 1, 1) == s.Substring(i - 1, 1) ? 0 : 1);
//                // Step 6
//                d[i, j] = System.Math.Min(System.Math.Min(d[i - 1, j] + 1, d[i, j - 1] + 1), d[i - 1, j - 1] + cost);
//                // Step 6A
//                d[i, j] = TranspozisyonlariKontrolEt(s, t, i, j, d);
//            }
//        }
//        // Step 7
//        return d[n, m] > limit ? limit + 1 : d[n, m];
//    }
//
//    private static int TranspozisyonlariKontrolEt(string s, string t, int i, int j, int[,] d)
//    {
//        if (i > 1 && j > 1)
//        {
//            int trans = d[i - 2, j - 2] + 1;
//            if (s[i - 2] != t[j - 1])
//                trans++;
//            if (s[i - 1] != t[j - 2])
//                trans++;
//            if (d[i, j] > trans) d[i, j] = trans;
//        }
//        return d[i, j];
//    }    
//    
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
    	System.out.println("Mesafe: " + duzeltmeMesafesi("elma", "emla"));
	}
}
