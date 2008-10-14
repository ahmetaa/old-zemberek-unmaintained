package net.zemberek.deney.pandul;


/**
 * A compact implementation for trie nodes.  
 * 
 * @author mdakin
 *
 */
public class Node {
  private byte letter;
  private int bitmap;
  private int attribute;
  // For compactness, sacrifice some construction performance 
  // (Using an arraylist would require an extra int) 
  private Node[] children;

  public Node() {
    letter = -1;
  }
  
  /**
   * @param c : A Turkish character
   * 
   */
  public Node(char c) {
    if (!TurkishAlphabet.isValid(c)) {
      throw new IllegalArgumentException("Illegal character: " + c);
    }
    this.letter = (byte) TurkishAlphabet.getIndex(c);
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
  public Node addNodeFor(char c) {
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
  public char getChar(){
    //Empty node?
    if (letter == -1) {
      return '#';
    }
    return TurkishAlphabet.getChar(letter);
  }
  
  @Override
  public String toString() {
    String s = getChar() + " : ";
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
   * @return 
   */
  public final void toDeepString(StringBuffer b, int level) {
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
   * Returns flat string representation of node and all child nodes.
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
   * @return 
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
   * @param flat : if true, returns a flat version of node and all subnodes
   * using a depth first traversal. if false, returns multi line, indented
   * version of node tree.
   * @return
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
}
