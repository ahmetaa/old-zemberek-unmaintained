package net.zemberek.deney.pandul;

import junit.framework.TestCase;

public class PandulTest extends TestCase {

  public void testCharToIndexForIllegalChars(){
    assertEquals(-1, Letters.getIndex('+'));
    assertEquals(-1, Letters.getIndex('?'));
    assertEquals(-1, Letters.getIndex('q'));
    assertEquals(-1, Letters.getIndex(' '));
  }

 public void testCharToIndex(){
   assertEquals(0, Letters.getIndex('a'));
   assertEquals(1, Letters.getIndex('b'));
   assertEquals(28, Letters.getIndex('z'));
 }

 public void testIndexToChar() {
   assertEquals('a', Letters.getChar(0));
   assertEquals('b', Letters.getChar(1));
   assertEquals('z', Letters.getChar(28));
 }
 
}