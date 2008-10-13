package net.zemberek.deney.pandul;

import junit.framework.TestCase;
import static net.zemberek.deney.pandul.TurkishAlphabet.*;

public class PandulTest extends TestCase {

  public void testCharToIndexForIllegalChars() {
    assertEquals(-1, TurkishAlphabet.getIndex('+'));
    assertEquals(-1, TurkishAlphabet.getIndex('?'));
    assertEquals(-1, TurkishAlphabet.getIndex('q'));
    assertEquals(-1, TurkishAlphabet.getIndex(' '));
  }

  public void testCharToIndex() {
    assertEquals(0, TurkishAlphabet.getIndex('a'));
    assertEquals(1, TurkishAlphabet.getIndex('b'));
    assertEquals(28, TurkishAlphabet.getIndex('z'));
  }

  public void testIndexToChar() {
    assertEquals('a', TurkishAlphabet.getChar(0));
    assertEquals('b', TurkishAlphabet.getChar(1));
    assertEquals('z', TurkishAlphabet.getChar(28));
  }

  char[] vowels = {'a', 'e', CHAR_ii, 'i', 'o', CHAR_oo, 'u', CHAR_uu};
  String someConsonants = "rtypsdfghjklzcvbnm";

  public void testVowels() {
    for (char vowel : vowels) {
      assertTrue(TurkishAlphabet.isVowel(vowel));
    }
    for (char c : someConsonants.toCharArray()) {
      assertFalse(TurkishAlphabet.isVowel(c));
    }
  }

  String voiceless = "ptk" + String.valueOf(CHAR_cc);
  String someLettersWithVoice = "eryuioasdfghjlzcvbnm";

  public void testVioceless() {
    for (char c : voiceless.toCharArray()) {
      assertTrue(TurkishAlphabet.isVoiceless(c));
    }
    for (char c : someLettersWithVoice.toCharArray()) {
      assertFalse(TurkishAlphabet.isVoiceless(c));
    }
  }

}