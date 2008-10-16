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

  public void testCompactionDoesNotEffectEmptyTrie() {
    CompactStringTrie cst = new CompactStringTrie();
    cst.compress();
    assertEquals("#:.|", cst.getRoot().dump(true));
    System.out.println(cst.getRoot().dump(false));
  }
  
  public void testCompactionDoesNotEffectSingleCharTrie() {
    CompactStringTrie cst = new CompactStringTrie();
    cst.add("a");
    cst.compress();
    assertEquals("#:(a)|a:.*|", cst.getRoot().dump(true));
    System.out.println(cst.getRoot().dump(false));
  }  
  
  public void testCompactionDoesNotEffectUncompactableTrie() {
    CompactStringTrie cst = new CompactStringTrie();
    cst.add("a");
    cst.add("ab");
    cst.add("c");
    cst.compress();
    assertEquals ("#:(ac)|a:(b)*|b:.*|c:.*|" , cst.getRoot().dump(true));
    System.out.println(cst.getRoot().dump(false));
  }
  
  public void testCompactionSimpleCase() {
    CompactStringTrie cst = new CompactStringTrie();
    cst.add("ab");
    System.out.println(cst.getRoot().dump(false));
    cst.compress();
    assertEquals ("#:(a)|ab:.*|" , cst.getRoot().dump(true));
    System.out.println(cst.getRoot().dump(false));
  } 
  
  public void testCompactionSimpleCase2() {
    CompactStringTrie cst = new CompactStringTrie();
    cst.add("patlak");
    System.out.println(cst.getRoot().dump(false));
    cst.compress();
    assertEquals ("#:(p)|patlak:.*|" , cst.getRoot().dump(true));
    System.out.println(cst.getRoot().dump(false));
  }
  
  public void testCompactionLongChain() {
    CompactStringTrie cst = new CompactStringTrie();
    cst.add("cobansumbulu");
    System.out.println(cst.getRoot().dump(false));
    cst.compress();
    assertEquals ("#:(c)|cobansumbu:(l)|lu:.*|" , cst.getRoot().dump(true));
    System.out.println(cst.getRoot().dump(false));
  }  
  
  public void testCompactionSimpleCase3() {
    CompactStringTrie cst = new CompactStringTrie();
    cst.add("abc");
    cst.add("vyz");
    cst.compress();
    assertEquals ("#:(av)|abc:.*|vyz:.*|" , cst.getRoot().dump(true));
    System.out.println(cst.getRoot().dump(false));
  }    
  
  public void testCompactionTwoChains() {
    CompactStringTrie cst = new CompactStringTrie();
    cst.add("ela");
    cst.add("elmas");
    System.out.println(cst.getRoot().dump(false));
    cst.compress();
    assertEquals ("#:(e)|el:(am)|a:.*|mas:.*|" , cst.getRoot().dump(true));
    System.out.println(cst.getRoot().dump(false));
  }   
  
}
