package net.zemberek.deney.pandul;

import junit.framework.TestCase;


public class CompactStringTrieTest extends TestCase {
  
  public void testSingleChar() {
    CompactStringTrie cst = new CompactStringTrie();
    cst.add("a");
    System.out.println(cst.getRoot());
  }

  public void testTwoStrings() {
    CompactStringTrie cst = new CompactStringTrie();
    cst.add("a");
    cst.add("b");
    System.out.println(cst.getRoot());
  }
  
  public void testLongString() {
    CompactStringTrie cst = new CompactStringTrie();
    cst.add("a");
    cst.add("ab");
    cst.add("c");
    
    System.out.println(cst.getRoot().dump());
  }

  public void testLongString2() {
    CompactStringTrie cst = new CompactStringTrie();
    cst.add("el");
    cst.add("ela");
    cst.add("elma");
    cst.add("elmas");
    cst.add("ek");
    System.out.println(cst.getRoot().dump());
  }
 
}
