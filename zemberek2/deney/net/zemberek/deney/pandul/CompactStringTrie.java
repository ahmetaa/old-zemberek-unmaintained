package net.zemberek.deney.pandul;

import java.util.List;

public class CompactStringTrie {
  private Node root;
 
  public void add(String s) {
    char[] giris = s.toCharArray();
    Node node = root;
    Node previousNode = null;
    int idx = 0;
    // null alt düğüm bulana dek veya kelimenin sonuna gelene
    // dek kelimenin harflerini izleyerek alt düğümlerde ilerle
    while (idx < giris.length) {
      previousNode = node;
      node = node.getChildNode(giris[idx]);
      if (node == null) break;
      idx++;
    }
  }
  
  public List<String> get(String prefix) {
    return null;
  }
   
}
