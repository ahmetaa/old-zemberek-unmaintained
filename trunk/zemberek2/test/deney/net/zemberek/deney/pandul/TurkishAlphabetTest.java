package net.zemberek.deney.pandul;

import junit.framework.TestCase;

public class TurkishAlphabetTest extends TestCase {

  TurkishAlphabet alphabet = new TurkishAlphabet(); 
  
  public void testCharToIndexForIllegalChars() {
    assertEquals(-1, alphabet.getIndex('+'));
    assertEquals(-1, alphabet.getIndex('?'));
    assertEquals(-1, alphabet.getIndex('q'));
    assertEquals(-1, alphabet.getIndex(' '));
  }

  public void testCharToIndex() {
    assertEquals(0, alphabet.getIndex('a'));
    assertEquals(1, alphabet.getIndex('b'));
    assertEquals(28, alphabet.getIndex('z'));
  }

  public void testIndexToChar() {
    assertEquals('a', alphabet.getChar(0));
    assertEquals('b', alphabet.getChar(1));
    assertEquals('z', alphabet.getChar(28));
  }

  char[] vowels = {'a', 'e', TurkishAlphabet.CHAR_ii, 'i', 'o', TurkishAlphabet.CHAR_oo, 'u', 
      TurkishAlphabet.CHAR_uu};
  String someConsonants = "rtypsdfghjklzcvbnm";

  public void testVowels() {
    for (char vowel : vowels) {
      assertTrue(alphabet.isVowel(vowel));
    }
    for (char c : someConsonants.toCharArray()) {
      assertFalse(alphabet.isVowel(c));
    }
  }

  String voiceless = "ptk" + String.valueOf(TurkishAlphabet.CHAR_cc);
  String someLettersWithVoice = "eryuioasdfghjlzcvbnm";

  public void testVioceless() {
    for (char c : voiceless.toCharArray()) {
      assertTrue(alphabet.isVoiceless(c));
    }
    for (char c : someLettersWithVoice.toCharArray()) {
      assertFalse(alphabet.isVoiceless(c));
    }
  }

}