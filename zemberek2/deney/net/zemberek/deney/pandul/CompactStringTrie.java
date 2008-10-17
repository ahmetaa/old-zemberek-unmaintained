package net.zemberek.deney.pandul;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
    walkAndMerge(root);
  }
  
  // Depth first traversal, merge chains during the walk.
  private void walkAndMerge(Node node) {
      Node[] children = node.getChildren();
      if(children == null) {
        return;
      }
      for(Node child: children) {
        child.mergeDown();
        walkAndMerge(child);
      }
  }
  
  public List<String> get(String prefix) {
    return null;
  }

  public Node getRoot() {
    return root;
  }

  public void save(DataOutputStream writer) throws IOException {
    root.serialize(writer);
    writer.close();
  }

  public void load(DataInputStream reader) throws IOException {
    root.deserialize(reader);
    reader.close();
  }


  
}
