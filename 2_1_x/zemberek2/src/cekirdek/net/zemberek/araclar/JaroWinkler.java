package net.zemberek.araclar;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * 
 * Winkler's reweighting scheme for distance metrics.  In the
 * literature, this was applied to the Jaro metric ('An Application of
 * the Fellegi-Sunter Model of Record Linkage to the 1990
 * U.S. Decennial Census' by William E. Winkler and Yves Thibaudeau.)
 */

public class JaroWinkler {

    /**
     * Verilen iki String'in benzerlik oranını Jaro-Winkler algortiması kullanarak hesaplar.
     * Oluşan sonuç 0-1 arasındadır, 1'e yaklaştıkça benzerlik artar, aynı stringler için "1" dir
     * @param source
     * @param target
     * @return Stringlerin benzerlik oranı. stringler aynı ise 1
     */
    public double benzerlikOrani(String source, String target) {
        double dist = jaroBenzerlikOrani(source, target);
        if (dist < 0 || dist > 1)
            throw new IllegalArgumentException("innerDistance should produce scores between 0 and 1");
        int prefLength = commonPrefixLength(4, source, target);
        dist = dist + prefLength * 0.1 * (1 - dist);
        return dist;
    }

    private double jaroBenzerlikOrani(String source, String target) {
        int halflen = halfLengthOfShorter(source, target);
        String common1 = commonChars(source, target, halflen);
        String common2 = commonChars(target, source, halflen);
        if (common1.length() != common2.length()) return 0;
        if (common1.length() == 0 || common2.length() == 0) return 0;

        int transpositions = transpositions(common1, common2);
        double dist =
                (common1.length() / ((double) source.length()) +
                common2.length() / ((double) target.length()) +
                (common1.length() - transpositions) / ((double) common1.length())) / 3.0;
        return dist;
    }

    private int halfLengthOfShorter(String source, String target) {
        return (source.length() > target.length()) ? target.length() / 2 + 1 : source.length() / 2 + 1;
    }

    private String commonChars(String s, String t, int halflen) {
        StringBuilder common = new StringBuilder();
        StringBuilder copy = new StringBuilder(t);
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            boolean foundIt = false;
            for (int j = max(0, i - halflen); !foundIt && j < min(i + halflen, t.length()); j++) {
                if (copy.charAt(j) == ch) {
                    foundIt = true;
                    common.append(ch);
                    copy.setCharAt(j, '*');
                }
            }
        }
        return common.toString();
    }

    private int transpositions(String common1, String common2) {
        int transpositions = 0;
        for (int i = 0; i < common1.length(); i++) {
            if (common1.charAt(i) != common2.charAt(i))
                transpositions++;
        }
        transpositions /= 2;
        return transpositions;
    }

    private static int commonPrefixLength(int maxLength, String common1, String common2) {
        int n = min(maxLength, min(common1.length(), common2.length()));
        for (int i = 0; i < n; i++) {
            if (common1.charAt(i) != common2.charAt(i)) return i;
        }
        return n; // first n characters are the same
    }

}
