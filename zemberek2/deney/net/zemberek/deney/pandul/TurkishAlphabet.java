package net.zemberek.deney.pandul;

/**
 * Helper class for Turkish alphabet.
 * 
 * @author mdakin
 *
 */
public class TurkishAlphabet implements Alphabet{
  
  // Turkce ozel
  public static final char CHAR_CC = '\u00c7'; // Kuyruklu buyuk c (ch)
  public static final char CHAR_cc = '\u00e7'; // Kuyruklu kucuk c (ch)
  public static final char CHAR_GG = '\u011e'; // Buyuk yumusak g
  public static final char CHAR_gg = '\u011f'; // Kucuk yumusak g
  public static final char CHAR_ii = '\u0131'; // Noktassiz kucuk i
  public static final char CHAR_II = '\u0130'; // Noktali buyuk i
  public static final char CHAR_OO = '\u00d6'; // Noktali buyuk o
  public static final char CHAR_oo = '\u00f6'; // Noktali kucuk o
  public static final char CHAR_SS = '\u015e'; // Kuyruklu buyuk s (sh)
  public static final char CHAR_ss = '\u015f'; // Kuyruklu kucuk s (sh)
  public static final char CHAR_UU = '\u00dc'; // Noktali buyuk u
  public static final char CHAR_uu = '\u00fc'; // Noktali kucuk u
  // Sapkali
  public static final char CHAR_SAPKALI_A = '\u00c2'; // sapkali buyuk A
  public static final char CHAR_SAPKALI_a = '\u00e2'; // sapkali kucuk A
  public static final char CHAR_SAPKALI_I = '\u00ce'; // sapkali buyuk noktasiz i
  public static final char CHAR_SAPKALI_i = '\u00ee'; // sapkali kucuk noktasiz i
  public static final char CHAR_SAPKALI_U = '\u00db'; // sapkali buyuk U
  public static final char CHAR_SAPKALI_u = '\u00fb'; // sapkali kucuk u
 
  public static char[] alphabet = {'a', 'b', 'c', CHAR_cc, 'd','e', 'f', 'g', CHAR_gg, 'h', CHAR_ii,
    'i', 'j', 'k', 'l', 'm', 'n', 'o', CHAR_oo, 'p', 'r', 's', CHAR_ss, 't', 'u', CHAR_uu, 'v',
    'y', 'z', CHAR_SAPKALI_a, CHAR_SAPKALI_i, CHAR_SAPKALI_u};

  public static final int ALPHABET_SIZE = alphabet.length;

  public static char[] alphabetCapital = {'A', 'B', 'C', CHAR_CC, 'D','E', 'F', 'G', CHAR_GG, 'h', 
    'I', CHAR_II, 'J', 'K', 'L', 'M', 'N', 'O', CHAR_OO, 'P', 'R', 'S', CHAR_SS, 'T', 'U', CHAR_UU, 
    'V', 'Y', 'Z', CHAR_SAPKALI_A, CHAR_SAPKALI_I, CHAR_SAPKALI_U};
  
  public static final int TURKISH_CHAR_MAP_SIZE = 610;
  public static int turkishLetters[] = new int[TURKISH_CHAR_MAP_SIZE];
  public static int toLower[] = new int[TURKISH_CHAR_MAP_SIZE];

  public static char[] vowels = {'a', 'e', CHAR_ii, 'i', 'o', CHAR_oo, 'u', CHAR_uu,
          CHAR_SAPKALI_a, CHAR_SAPKALI_i, CHAR_SAPKALI_u};
  public static char[] voiceless = {'p', CHAR_cc, 't', 'k'};

  public static boolean vowelLookup[] = new boolean[alphabet.length];
  public static boolean voicelessLookup[] = new boolean[alphabet.length];

 
  // Static initializer fills up lookup tables.
  static {
    for(int i=0; i < TURKISH_CHAR_MAP_SIZE; i++) {
      turkishLetters[i] = -1;
      toLower[i] = -1;
    }
    
    for(int i=0; i < alphabet.length; i++) {
      turkishLetters[alphabet[i]] = i;
      toLower[alphabetCapital[i]] = alphabet[i];
      vowelLookup[i] = false;
      voicelessLookup[i] = false;
    }

    for (char vowel : vowels) {
      vowelLookup[getIndex(vowel)] = true;
    }

    for (char c : voiceless) {
      voicelessLookup[getIndex(c)] = true;
    }
    
  }
 
  public static boolean isValid(char c) {
     return getIndex(c) != -1;
  }
  
  public static int getIndex(char c){
    if (c < 0 && c > TURKISH_CHAR_MAP_SIZE) {
      return -1;
    }
    return turkishLetters[c];
  }
 
  public static char getChar(int index) {
    assert(index > 0 && index < alphabet.length);
    return alphabet[index];
  }

  public static boolean isVowel(char c) {
    //TODO: should it be checked for validity?
    if (!isValid(c))
      throw new IllegalArgumentException("not a valid char:" + c);
    return vowelLookup[getIndex(c)];
  }

  public static boolean isVoiceless(char c) {
    //TODO: should it be checked for validity?
    if (!isValid(c))
      throw new IllegalArgumentException("not a valid char:" + c);
    return voicelessLookup[getIndex(c)];
  }

  
}