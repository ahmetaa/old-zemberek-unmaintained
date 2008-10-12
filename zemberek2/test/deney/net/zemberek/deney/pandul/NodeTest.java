package net.zemberek.deney.pandul;

import junit.framework.TestCase;

public class NodeTest extends TestCase{
  
  public void setUp() {
  }
  
  private void testChar( char c){
    Node node = new Node();
    node.addNodeFor(c);
    Node n = node.getChildNode(c);
    System.out.println(c);
    assertNotNull(n);
    assertEquals(n.getChar(), c);
  }

  private void testTwoCharacter( char c1, char c2){
    Node node = new Node();
    node.addNodeFor(c1);
    node.addNodeFor(c2);
    Node n1 = node.getChildNode(c1);
    Node n2 = node.getChildNode(c2);
    assertNotNull(n1);
    assertNotNull(n2);
    assertEquals(n1.getChar(), c1);
    assertEquals(n2.getChar(), c2);
    System.out.println(node);
  }
  
  private void testForCharArray(char[] ca){
    Node node = new Node();
    for (char c : ca) {
      node.addNodeFor(c);
    }
    for (char c : ca) {
      Node n = node.getChildNode(c);
      assertNotNull(n);
      assertEquals(n.getChar(), c);
      System.out.println(node);
    }
  }
  
  public void testAddNodeSingleChar() {
    testChar('a');
    testChar('b');
    testChar('z');
    // Last char (32th)
    testChar(Letters.alphabet[Letters.alphabet.length - 1]);
  }
  
  public void testAddNodAll() {
    for(char c : Letters.alphabet) {
      testChar(c);
    }
  }

  public void testAddNodeTwoChars() {
    testTwoCharacter('a', 'b');
    testTwoCharacter('b', 'a');
    // Last char (32th)
    testTwoCharacter('a', Letters.alphabet[Letters.alphabet.length - 1]);
  }
 
  public void testAddTwoNodeAll() {
    for(char c1 : Letters.alphabet) {
      for(char c2 : Letters.alphabet) {
        testTwoCharacter(c1, c2);
        testTwoCharacter(c2, c1);
      }
    }
  }
  
  public void testManyChars() {
    char[] ca = {'a', 'b', 'c', 'd'};
    testForCharArray(ca);
    ca = new char[]{'d', 'c', 'b', 'a'};
    testForCharArray(ca);
  }
  
}
