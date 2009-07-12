package net.zemberek.deney.pandul;

import net.zemberek.bilgi.KaynakYukleyici;
import net.zemberek.deney.pandul.SimpleCompactStringTrie.Node;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * Simple test application to see the performance - footprint of trie
 *
 * @author mdakin
 */
public class Analysis {

  static int totalNodes = 0;
  static int[] childCounts = new int[40];
  static int[] chainLengths = new int[40];
  static int[] strLengths = new int[40];
  static int leafNodes = 0;
  static int maxLevel = 0;
  static int totalChars = 0;

  /**
   * Recursively walks the trie and updates statistics.
   *
   * @param node a Node
   * @param chainLen : A chain is nodes with single childs without
   *                 a word mark. A leaf node can be part of chain as well.
   */
  public static void walk(Node node, int chainLen, int level) {
    totalNodes++;
    level++;
    maxLevel = level > maxLevel ? level : maxLevel;
    Node[] children = node.getChildren();
    strLengths[node.getString().length()]++;
    chainLen++;
    if (children == null) {
      chainLengths[chainLen]++;
      leafNodes++;
      return;
    }
    childCounts[children.length]++;
    if (children.length > 1 || node.getAttribute() != 0) {
      chainLengths[chainLen]++;
      chainLen = 0;
    }
    for (Node childNode : children) {
      walk(childNode, chainLen, level);
    }
  }

  public static void reset(){
    totalNodes = 0;
    childCounts = new int[40];
    chainLengths = new int[40];
    strLengths = new int[40];
    leafNodes = 0;
    maxLevel = 0;
  }
  
  public void createFlatTrie(Node node, Node[] children) {
    if (children == null) {
      return;
    }
  }
  
  public static void report(SimpleCompactStringTrie cst) {
    System.out.println("Total Nodes: " + totalNodes);
    System.out.println("Total Leaf Nodes: " + leafNodes);
    System.out.println("Total ratio: " + (leafNodes * 100.0 / totalNodes));
    
    int interimNodes = totalNodes - leafNodes;
    System.out.println("Total interim Nodes: " + interimNodes);
    System.out.println("Max Depth: " + maxLevel);
    
    int totalChildLinks = 0;
    for (int i = 0; i < childCounts.length; i++) {
      if (childCounts[i] == 0) continue;
      totalChildLinks += childCounts[i] * i;
      System.out.println("Nodes with " + i + " children: " + childCounts[i]);
    }
    
    System.out.println("Average fanout = " + ((double)totalChildLinks / interimNodes));
    
    for (int i = 0; i < chainLengths.length; i++) {
      if (chainLengths[i] == 0) continue;
      System.out.println("Chains of length " + i + " : " + chainLengths[i]);
    }
    
    int totalStringLength = 0;
    for (int i = 0; i < strLengths.length; i++) {
      if (strLengths[i] == 0) continue;
      totalStringLength += strLengths[i] * i;
      System.out.println("String fragment of length " + i + " : " + strLengths[i]);
    }
    System.out.println("Total string length: " + totalStringLength);
    System.out.println("Average compression: %" + (100 - (totalStringLength * 100 / totalChars)));
    System.out.println("Average str fragment length = " + ((double) totalStringLength / totalNodes));
    
  }
  
  private static List<Entry<String, Integer>> getSortedCopy(Set<Entry<String, Integer>> set) {
    List<Entry<String, Integer>> entries = new ArrayList<Entry<String, Integer>>(set);
    Collections.sort(entries, new Comparator<Entry<String, Integer>>() {
      public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
        return o2.getValue().compareTo(o1.getValue());
      }
    });
   return entries; 
  }

  public static void main(String[] args) throws IOException {
    SimpleCompactStringTrie cst = new SimpleCompactStringTrie(new TurkishAlphabet());
    Map<String, Integer> attributes = new TreeMap<String, Integer>();
    Map<String, Integer> attributeGroups = new TreeMap<String, Integer>();
        
    BufferedReader reader = new KaynakYukleyici("utf-8").getReader("kaynaklar/tr/bilgi/duzyazi-kilavuz.txt");
//    BufferedReader reader = new KaynakYukleyici("utf-8").getReader("kelime-frekans-chrome.txt");    
    String line;
    int total = 0;
    while ((line = reader.readLine()) != null) {
      line = line.toLowerCase().trim();
      if (line.startsWith("#")) {
        continue;
      }
//      String[] parts = line.split("\\|");
      String[] parts = line.split(" ");
      if (parts.length == 0) {
        continue;
      }
      line = parts[0].replaceAll("['.`qwx-]", "").trim();
      totalChars += line.length();
//      System.out.println(line);
//      String attributeGroup = "";
//      for (int i = 1; i < parts.length ; i++) {
//        if (parts[i].trim().length() == 0) continue;
//        Integer c = attributes.get(parts[i]);
//        if (c == null) {
//          attributes.put(parts[i], 1);
//        } else {
//          attributes.put(parts[i], c.intValue() + 1);
//        }
//        attributeGroup += parts[i] + "-";
//      }
//      if (attributeGroup.trim().length() != 0) {
//        Integer c = attributeGroups.get(attributeGroup);
//        if (c == null) {
//          attributeGroups.put(attributeGroup, 1);
//        } else {
//          attributeGroups.put(attributeGroup, c.intValue() + 1);
//        }
//      }
      total++;
      if(total > 100000) break;
      cst.add(line);
    }
    System.out.println("Total entries in the dictionary: " + total);
    System.out.println("Total chars in the dictionary: " + totalChars);
    System.out.println("Average Length: " + ((double)totalChars / total));

    System.out.println("Unique Attributes Total:" + attributes.size());
    
    for (Entry<String, Integer> entry : getSortedCopy(attributes.entrySet())) {
      System.out.println(entry.getKey() + " : " + entry.getValue());
    }
    System.out.println();
    System.out.println("Attribute Groups Total: " + attributeGroups.size());
    for (Entry<String, Integer> entry : getSortedCopy(attributeGroups.entrySet())) {
      System.out.println(entry.getKey() + " : " + entry.getValue());
    }
    
    walk(cst.getRoot(), 0, 0);
    report(cst);
    reset();
    long time = System.currentTimeMillis();
    cst.compress();
    cst.save(new BufferedOutputStream(new FileOutputStream("tr.dic")));
    long delta = System.currentTimeMillis() - time;
    System.out.println("Compression time:" + delta);
    walk(cst.getRoot(), 0, 0);
    report(cst);
    
    delta = System.currentTimeMillis() - time;
    System.out.println("time:" + delta);
    SimpleCompactStringTrie cst2 = new SimpleCompactStringTrie(new TurkishAlphabet());
    cst2.load(new BufferedInputStream(new FileInputStream("tr.dic")));
    delta = System.currentTimeMillis() - time;
    System.out.println("Load time:" + delta + "ms");
    
  }
}
