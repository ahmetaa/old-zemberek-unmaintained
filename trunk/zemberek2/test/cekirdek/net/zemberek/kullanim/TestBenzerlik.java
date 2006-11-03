package net.zemberek.kullanim;

import net.zemberek.araclar.JaroWinkler;
import net.zemberek.araclar.MetinAraclari;

import java.text.DecimalFormat;

public class TestBenzerlik {

    private static JaroWinkler jaro = new JaroWinkler();
    public static DecimalFormat df = new DecimalFormat("#0.000");

    public static void levenshtein(String str1, String str2) {
        System.out.println("Levenshtein düzeltme mesafesi (" + str1 + " ve " + str2 + ") = " +
                MetinAraclari.editDistance(str1, str2));
    }

    public static void jaro(String str1, String str2) {
        System.out.println("Jaro-Winkler benzerlik oraný (" + str1 + " ve " + str2 + ") = " +
                df.format(jaro.benzerlikOrani(str1, str2)));
    }

    public static void main(String[] args) {
        levenshtein("elma", "lma");
        levenshtein("elma", "kelma");
        levenshtein("elma", "elpa");
        levenshtein("elma", "emla");
        levenshtein("elma", "elti");
        System.out.println();
        jaro("elma", "lma");
        jaro("elma", "kelma");
        jaro("elma", "elpa");
        jaro("elma", "emla");
        jaro("elma", "elti");
    }
}
