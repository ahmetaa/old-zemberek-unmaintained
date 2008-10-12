package net.zemberek.deney.pandul;

import junit.framework.TestCase;

public class NodeTest extends TestCase{
  
  Node node;
  
  @Override
  public void setUp() {
    node = new Node();
  }
  
  public void testAddNode() {
    node.addNodeFor('a');
    assertNotNull(node.getChildNode('a'));
  }
  
}
