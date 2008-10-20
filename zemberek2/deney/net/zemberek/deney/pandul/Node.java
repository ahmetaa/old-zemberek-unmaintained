package net.zemberek.deney.pandul;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A compact implementation for trie nodes for memory limited systems.  
 * 
 * @TODO(mdakin): Alphabet access should be through an Alphabet singleton 
 * to able to use different alphabets (languages) 
 *  
 * @author mdakin
 *
 */
public class Node {
  private long strLong;
  private int bitmap;
  private int attribute;
  private Node[] children;

  public Node() {
    this.strLong = 0;
  }
  
  /**
   * @param c : A character. It must be in the alphabet
   * @throws IllegalArgumentException : If character is not defined in alphabet
   */
  public Node(char c) {
    if (!TurkishAlphabet.isValid(c)) {
      throw new IllegalArgumentException("Illegal character: " + c);
    }
    this.strLong = TinyStrings.create(c);
  }
  
  /**
   * Returns child node identfied with char c.
   * @param c
   * @return the child node, identified by input char c, 
   * null if there is no child exist for given char.
   */
  public final Node getChildNode(char c) {
    int index = TurkishAlphabet.getIndex(c);
    assert (index != -1);
    if (hasChild(index)) {
      return children[getArrayIndex(index)];
    }
    return null;
  }
  
  Node[] getChildren() {
    return children;
  }
  
  /**
   * Creates a child node identified by character c and attaches it 
   * to children list.
   * 
   * @param c
   * @throws IllegalArgumentException if c is not a legal character.
   */
  final Node addNodeFor(char c) {
    if (!TurkishAlphabet.isValid(c)) {
      throw new IllegalArgumentException("Illegal character: " + c);
    }
    int index = TurkishAlphabet.getIndex(c);
    if (hasChild(index)) {
      
      // Subnode with given character already exists.
    }
    return addChild(c, index);
  }
  
  final Node addChild(char c, int index) {
    // Create a new Node.
    Node child = new Node(c);
    if(children == null) {
      children = new Node[1];
      children[0] = child; 
    } else {
      // Expand the array, insert child to correct position.
      // TODO(mdakin): optimize for very common small arrays of len < 3
      int aindex = getArrayIndex(index);
      Node[] tmp = new Node[children.length + 1];
      if(aindex < children.length) {
        System.arraycopy(children, 0, tmp, 0, aindex);
        tmp[aindex] = child;
        System.arraycopy(children, aindex, tmp, aindex + 1, tmp.length - aindex - 1);
      } else {
        System.arraycopy(children, 0, tmp, 0, children.length);
        tmp[aindex] = child; 
      }
      children = tmp;
    }
    // update bitmap
    bitmap |= (1 << index);
    return child;
  }
  
  final boolean hasChild (int index) {
    return ((bitmap & (1 << index)) != 0);
  }
  
  final int getArrayIndex(int index) {
    if (index == 0){
      return 0;
    }
    return Integer.bitCount(bitmap & (-1 >>> 32 - index));
  }
  
  /**
   * @return the representative char. or # if node is root
   */
  final char getChar(){
    //Empty node?
    if (strLong == 0) {
      return '#';
    }
    return TinyStrings.charAt(strLong, 0);
  }
  
  /**
   * Merges this node with all suitable subnodes
   */
  final void mergeDown(){
    if (!isChainNode()) {
      return;
    }
    // walk down until a leaf or junction, merges all the way.
    List<Node> chain = new ArrayList<Node>();
    Node node = this.children[0];
    while(node.isChainNode() && !node.isLeaf() 
        && chain.size() < TinyStrings.MAX_STRING_LENGTH - 2) {
      chain.add(node);
      node = node.children[0];
    }
    chain.add(node);
    if(chain.size() > 0) {
      for(Node n : chain) {
        strLong = TinyStrings.addChar(strLong, n.getChar());
      }
      // child is either leaf or last node of chain.
      this.attribute = node.attribute; 
      // link last node to first, thus remove all nodes in between.
      this.bitmap = node.bitmap;
      this.children = node.children;
    }
  }
 
  public boolean isLeaf() {
    return children == null;
  }
 
  public boolean isChainNode() {
    return (children != null && children.length == 1 && attribute == 0);
  }
  
  public String getString() {
    if (strLong == 0) {
      return "#";
    }
    else return TinyStrings.asString(this.strLong);
  }
  
  @Override
  public String toString() {
    String s = getString() + " : ";
    if (children != null) {
      s += "( ";
      for (Node node : children) {
       s += node.getChar() + " "; 
      }
      s += ")";
    } else {
      s += ".";
    }
    if (attribute != 0) {
      s += " * ";
    }
    return s;
  }
  
  /**
   * Returns string representation of node and all child nodes until leafs.
   *
   * @param level
   */
  private final void toDeepString(StringBuffer b, int level) {
    char[] indentChars = new char[level * 2];
    for (int i = 0; i < indentChars.length; i++)
      indentChars[i] = ' ';
    b.append(indentChars).append(this.toString());
    b.append("\n");
    if (children != null) {
      for (Node subNode : this.children) {
        subNode.toDeepString(b, level + 1);
      }
    }
  }

  /**
   * 
   * Flat string representation of node and all child nodes.
   * Used for testing purposes only. Given a tree like this:
   * 
   *      a
   *     / \
   *    b   c*
   *   /
   *  e* 
   *   
   * This method returns: a:(bc)|b:(e)|e:(.)*|c:(.)*
   *  
   */
  public final void toFlatString(StringBuffer b) {
    b.append(this.toString().replaceAll(" ", "") + "|");
    if (children != null) {
      for (Node subNode : this.children) {
        subNode.toFlatString(b);
      }
    }
  }
  
  /**
   * Returns string representation of Node (and subnodes) for testing.
   * 
   * @param flat : if true, returns a flat version of node and all sub nodes
   * using a depth first traversal. if false, returns multiline, indented
   * version of node tree.
   * @return a flat or tree string representation of trie.
   */
  public final String dump(boolean flat) {
    StringBuffer b = new StringBuffer();
    if (flat) {
      toFlatString(b);
    } else {
      toDeepString(b, 0);
    }
    return b.toString();
  }

  public int getAttribute() {
    return attribute;
  }

  public void setAttribute(int attribute) {
    this.attribute = attribute;
  }
  
  /**
   * Writes content of node and all sub nodes recursively to data output stream.
   * TODO(mdakin): Serialized size could be improved by writing less for nodes
   * containing less chars.
   * @param dos
   * @throws IOException
   */
  public void serialize(DataOutputStream dos) throws IOException {
    dos.writeLong(strLong);
    dos.writeInt(attribute);
    dos.writeInt(bitmap);
    if(children == null) {
      return;
    }
    for(Node child: children) {
      child.serialize(dos);
    }    
  }
  
  public void deserialize(DataInputStream dis) throws IOException {
    strLong = dis.readLong();
    attribute = dis.readInt();
    bitmap = dis.readInt();
    if (bitmap == 0) {
      return;
    } else {
      // Create the node list in advance to gain performance. We know that it 
      // will contain "number of one bits in the bitmap" count of sub nodes.
      // So we will not bother expanding the bitmap
      children = new Node[Integer.bitCount(bitmap)];
    }
    int limit = Integer.numberOfLeadingZeros(bitmap);
    for (int i = Integer.numberOfTrailingZeros(bitmap); i < 32 - limit; i++) {
      if (hasChild(i)) {
        Node child = new Node(TurkishAlphabet.getChar(i));
        children[getArrayIndex(i)] = child;
        child.deserialize(dis);
//        Node n = addNodeFor(TurkishAlphabet.getChar(i));
//        n.deserialize(dis);
      }
    }
  }

  public boolean hasWord() {
    return attribute != 0;
  }  
}
