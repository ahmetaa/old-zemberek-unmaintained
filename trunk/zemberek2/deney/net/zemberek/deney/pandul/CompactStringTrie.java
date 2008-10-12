package net.zemberek.deney.pandul;

import java.util.List;

public class CompactStringTrie {
  private Node root = new Node();

  public void add(String s) {
    char[] chars = s.toCharArray();
    Node node = root;
    Node previousNode = null;
    int i = 0;
    // null alt düğüm bulana dek veya kelimenin sonuna gelene
    // dek kelimenin harflerini izleyerek alt düğümlerde ilerle
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
      while (i < chars.length) {
        node = node.addNodeFor(chars[i++]);
      }
      node.setAttribute(1);
    }

  }

  public List<String> get(String prefix) {
    return null;
  }

  public Node getRoot() {
    return root;
  }

}
