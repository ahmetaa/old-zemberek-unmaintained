package net.zemberek.deney.pandul;

import java.util.ArrayList;
import java.util.List;

/**
 * A String trie for representing a set of words (usually a dictionary)
 * 
 * @author mdakin
 *
 */
public class CompactStringTrie {
  private Node root = new Node();

  public void add(String s) {
    char[] chars = s.toCharArray();
    Node node = root;
    Node previousNode = null;
    int i = 0;
    while (i < chars.length) {
      previousNode = node;
      node = node.getChildNode(chars[i]);
      if (node == null)
        break;
      i++;
    }

    if (i < chars.length) {
      // Start from the parent.
      node = previousNode;
      // Add one node for each char.
      while (i < chars.length) {
        node = node.addNodeFor(chars[i++]);
      }
      node.setAttribute(1);
    }
  }
  
  /**
   * Compresses a trie by collapsing long chains of nodes into one node.
   * for example after adding "ela" and "elmas" tree would be something like this:
   * 
   * # : ( e )
   *   e : ( l )
   *     l : ( a m )
   *       a : . * 
   *       m : ( a )
   *         a : ( s )
   *           s : . * 
   *           
   * After compaction, it becomes:
   *         
   * # : ( e )
   *   el : (a m)
   *     a : . * 
   *     mas : . * 
   */
  public void compress(){
    walk(root, new ArrayList<Node>());
  }
  
  // Depth first traversal to find and compact node chains.
  private void walk(Node node, List<Node> chain) {
//    Node[] children = node.getChildren();
//    chain.add(node);
//    if (children == null) {
//      if (chain.size() > 2) {
//        // We have a long chain, now compress it.
//        System.out.println("Identified chain:" + chain);
//      }      
//      chain.clear();
//      return;
//    }
//    if (children.length > 1 || node.getAttribute() != 0) {
//      if (chain.size() > 2) {
//        // We have a long chain, now compress it.
//        System.out.println("Identified chain:" + chain);
//        // merge nodes
//      }
//      chain.clear();
//      for(Node child: children) {
//        walk(child, chain);
//      }
//    } else {
//      walk(children[0], chain);
//    }
  }
  
  private void checkChain() {
    
  }

  public List<String> get(String prefix) {
    return null;
  }

  public Node getRoot() {
    return root;
  }

}
