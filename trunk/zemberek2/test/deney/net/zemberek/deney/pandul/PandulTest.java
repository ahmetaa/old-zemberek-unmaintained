package net.zemberek.deney.pandul;

import junit.framework.TestCase;

public class PandulTest extends TestCase {

  public void testCharToIndexForIllegalChars(){
    assertEquals(-1, TurkishAlphabet.getIndex('+'));
    assertEquals(-1, TurkishAlphabet.getIndex('?'));
    assertEquals(-1, TurkishAlphabet.getIndex('q'));
    assertEquals(-1, TurkishAlphabet.getIndex(' '));
  }

 public void testCharToIndex(){
   assertEquals(0, TurkishAlphabet.getIndex('a'));
   assertEquals(1, TurkishAlphabet.getIndex('b'));
   assertEquals(28, TurkishAlphabet.getIndex('z'));
 }

 public void testIndexToChar() {
   assertEquals('a', TurkishAlphabet.getChar(0));
   assertEquals('b', TurkishAlphabet.getChar(1));
   assertEquals('z', TurkishAlphabet.getChar(28));
 }
 
}