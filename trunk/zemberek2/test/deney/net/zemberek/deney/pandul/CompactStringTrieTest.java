package net.zemberek.deney.pandul;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

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
  
  public void testSave() throws IOException {
    ByteArrayOutputStream bo = new ByteArrayOutputStream();
    CompactStringTrie cst = new CompactStringTrie();
    cst.add("a");
    cst.add("b");
    cst.add("elma");
    cst.compress();
    String s1 = cst.getRoot().dump(true);
    cst.save(new BufferedOutputStream(bo));
    byte[] b = bo.toByteArray();
    assertNotNull(b);
    assertTrue(b.length > 0);
    System.out.println(b.length);
    ByteArrayInputStream bi = new ByteArrayInputStream(b);
    CompactStringTrie cst2 = new CompactStringTrie();
    cst2.load(new BufferedInputStream(bi));
    String s2 = cst.getRoot().dump(true);
    assertEquals(s1, s2);
  }
  
  public void testFindPrefixMatches(){
    CompactStringTrie cst = new CompactStringTrie();
    cst.add("el");
    cst.add("elma");
    cst.add("ela");
    cst.add("elmas");
    cst.add("et");
    cst.compress();
    System.out.println(cst.getRoot().dump(false));
    List<String> roots = cst.getMatchingRoots("elmaslara");
    assertEquals(3, roots.size());
    assertEquals("el", roots.get(0));
    assertEquals("elma", roots.get(1));
    assertEquals("elmas", roots.get(2));
  }
  
}
