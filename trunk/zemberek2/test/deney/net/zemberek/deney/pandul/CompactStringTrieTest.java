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

  public void testSingleWord() {
    CompactStringTrie cst = new CompactStringTrie();
    cst.add("a");
    assertEquals ("#:(a)|a:.*|" , cst.getRoot().dump(true));
    cst = new CompactStringTrie();
    cst.add("a");
    cst.add("b");
    assertEquals ("#:(ab)|a:.*|b:.*|" , cst.getRoot().dump(true));
  }
  
  public void testLongString() {
    CompactStringTrie cst = new CompactStringTrie();
    cst.add("a");
    cst.add("ab");
    cst.add("c");
    assertEquals ("#:(ac)|a:(b)*|b:.*|c:.*|" , cst.getRoot().dump(true));
    System.out.println(cst.getRoot().dump(false));
  }

  public void testLongString2() {
    CompactStringTrie cst = new CompactStringTrie();
    cst.add("ela");
    cst.add("elma");
    cst.add("elmas");
    cst.add("ek");
    cst.add("et");
    assertEquals ("#:(e)|e:(klt)|k:.*|l:(am)|a:.*|m:(a)|a:(s)*|s:.*|t:.*|", cst.getRoot().dump(true));
    System.out.println(cst.getRoot().dump(false));
  }
 
}
