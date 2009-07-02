package net.zemberek.deney.pandul;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A String trie for representing a set of words (usually a dictionary)
 * 
 * @author mdakin
 *
 */
public class SimpleCompactStringTrie {
  private final Alphabet alphabet;
//  private TinyStrings stringsHelper;
  private Node root = new Node();
  

  public SimpleCompactStringTrie(Alphabet a) {
    this.alphabet = a;
//    stringsHelper = new TinyStrings(a);
  }
  
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
   * 
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
  
  public List<String> getMatchingRoots(String input) {
    Node node = root;
    int index = 0;
    String s = "";
    List<String> words = new ArrayList<String>();
    while (index < input.length()) {
      node = node.getChildNode(input.charAt(index));
      if (node == null) break;
      String nodeString = node.getString();
      s += nodeString;
      if (input.startsWith(s) && node.hasWord()) {
          words.add(s);
      }
      index += nodeString.length();
    }
    return words;
  }

  public Node getRoot() {
    return root;
  }

  public void save(OutputStream os) throws IOException {
    root.serialize(new DataOutputStream(os));
    os.close();
  }

  public void load(InputStream is) throws IOException {
    root.deserialize(new DataInputStream(is));
    is.close();
  }

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
//    private long strLong;
    private char[] str = new char[0];
    private int bitmap;
    private int attribute;
    private Node[] children;

    public Node() {
    }
    
    /**
     * @param c : A character. It must be in the alphabet
     * @throws IllegalArgumentException : If character is not defined in alphabet
     */
    public Node(char c) {
      if (!alphabet.isValid(c)) {
        throw new IllegalArgumentException("Illegal character: " + c);
      }
      appendToStr(c);
    }
    
    private final void appendToStr(char c){
      if(str.length == 0) {
        str = new char[1];
      }
      else {
        str = Arrays.copyOf(str, str.length + 1);
      }
      str[str.length - 1] = c;
    }

    private final void appendToStr(char[] c){
      if(str == null) {
        str = new char[1];
      }
      else {
        str = Arrays.copyOf(str, str.length + c.length);
      }
      System.arraycopy(c, 0, str, str.length - c.length, c.length);
    }
    
    
    /**
     * Returns child node identfied with char c.
     * @param c child node identifier.
     * @return the child node, identified by input char c, 
     * null if there is no child exist for given char.
     */
    public final Node getChildNode(char c) {
      int index = alphabet.getIndex(c);
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
     * @param c child node character
     * @throws IllegalArgumentException if c is not a legal character.
     * @return returns the the node created for the given character.
     */
    final Node addNodeFor(char c) {
      if (!alphabet.isValid(c)) {
        throw new IllegalArgumentException("Illegal character: " + c);
      }
      int index = alphabet.getIndex(c);
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
      if (str.length == 0) {
        return '#';
      }
      return str[0];
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
          /*&& chain.size() < TinyStrings.MAX_STRING_LENGTH - 2*/) {
        chain.add(node);
        node = node.children[0];
      }
      chain.add(node);
      if(chain.size() > 0) {
        for(Node n : chain) {
          appendToStr(n.getChar());
//          strLong = stringsHelper.addChar(strLong, n.getChar());
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
      if (str.length == 0) {
        return "#";
      }
      else return new String(str);
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
     * @param b string buffer to append.
     * @param level level of the operation
     */
    private void toDeepString(StringBuffer b, int level) {
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
     * @param b stringbuffer to append.
     */
    public final void toFlatString(StringBuffer b) {
        b.append(this.toString().replaceAll(" ", "")).append("|");
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
     * @param dos Data output stream
     * @throws IOException if something goes wrong during write.
     */
    public void serialize(DataOutputStream dos) throws IOException {
      dos.writeInt(str.length);
//      System.out.println("written len: " + str.length);
      for (char c: str) {
//        System.out.println("written char: " + c);
        dos.writeChar(c);
      }
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
      int len = dis.readInt();
//      System.out.println("read len: " + len );
      str = new char[len];
      for (int i=0; i<len; i++) {
        str[i] = dis.readChar();
//        System.out.println("read char: " + str[i] );
      }
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
          Node child = new Node(alphabet.getChar(i));
          children[getArrayIndex(i)] = child;
          child.deserialize(dis);
//          Node n = addNodeFor(TurkishAlphabet.getChar(i));
//          n.deserialize(dis);
        }
      }
    }

    public boolean hasWord() {
      return attribute != 0;
    }  
  }
  
}
