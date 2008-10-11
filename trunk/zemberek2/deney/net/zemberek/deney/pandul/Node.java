package net.zemberek.deney.pandul;

/**
 * A compact implementation for trie nodes.  
 * @author mdakin
 *
 */
public class Node {
  private byte letter;
  private int bitmap;
  // Absolute compactness, sacrifice some performance 
  private Node[] children;
  
  public final Node getChildNode(char c) {
    int index = Letters.getIndex(c);
    assert (index != -1);
    if (hasChild(index)) {
      return children[getArrayIndex(index)];
    }
    return null;
  }
  
  public final void addNodeFor(char c) {
    int index = Letters.getIndex(c);
    if(hasChild(index)) {
      // Subnode with given character already exists.
      // Add as homonym
    }
    addChild(c, index);
  }
  
  void addChild(char c, int index) {
    // Create a new Node.
    Node child = new Node();
    if(children == null) {
      children = new Node[1];
      children[0] = child; 
    } else {
      // Expand the array, insert child to correct position.
      // TODO(mdakin): optimize for very common small arrays of len < 3
      Node[] tmp = new Node[children.length + 1];
      if(index < children.length) {
        System.arraycopy(children, 0, tmp, 0, index);
        children[index] = child;
        System.arraycopy(children, index, tmp, index, tmp.length - index);
      }
    }
    // update bitmap
    bitmap &= 1 << index;
  }
  
  boolean hasChild (int index) {
    return ((bitmap & (1 << index)) == 1);
  }
  
  int getArrayIndex(int index) {
    int zeros = Integer.numberOfLeadingZeros(index);
    return index - zeros; 
  }
  
  
}
