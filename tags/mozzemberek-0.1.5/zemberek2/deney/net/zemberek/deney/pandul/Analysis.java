package net.zemberek.deney.pandul;

import net.zemberek.bilgi.KaynakYukleyici;
import net.zemberek.deney.pandul.SimpleCompactStringTrie.Node;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
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

  static class Stats {
    int totalNodes;
    int totalLeafNodes;
    int totalFragmentSize;
    int totalFragmentSizeUTF8;
    int totalSubNodes;
    int maxLevel;
    int[] childCounts = new int[40];
    int[] chainLengths = new int[40];
    int[] strLengths = new int[40];
  }
  
  /**
   * Recursively walks the trie and updates statistics.
   *
   * @param node a Node
   * @param chainLen : A chain is nodes with single childs without
   *                 a word mark. A leaf node can be part of chain as well.
   */
  static Charset UTF_8 = Charset.forName("UTF-8");
  public static void walk(Node node, int chainLen, int level, Stats stats) {
    stats.totalNodes++;
    level++;
    stats.maxLevel = level > stats.maxLevel ? level : stats.maxLevel;
    Node[] children = node.getChildren();
    String fragment = node.getString();
    stats.strLengths[fragment.length()]++;
    stats.totalFragmentSize += fragment.length();
    stats.totalFragmentSizeUTF8 += fragment.getBytes(UTF_8).length;
    chainLen++;
    if (children == null) {
      stats.chainLengths[chainLen]++;
      stats.totalLeafNodes++;
      return;
    }
    stats.childCounts[children.length]++;
    stats.totalSubNodes += children.length;
    if (children.length > 1 || node.getAttribute() != 0) {
      stats.chainLengths[chainLen]++;
      chainLen = 0;
    }
    for (Node childNode : children) {
      walk(childNode, chainLen, level, stats);
    }
  }
  
  static int arrIndex = 0;
  static byte[] array = new byte[1000000];
  static int[] offsetHistogram = new int[100];
  static DataOutputStream dos = new DataOutputStream(new ByteArrayOutputStream(4096));
    
  public static int createFlatTrie(ByteBuffer rawArray, int startIdx, Node node) throws IOException {
//    System.out.println(node.getString() + " current index: " + arrIndex );
    String fragment = node.getString();
    int startIndex = arrIndex;
    rawArray.put(arrIndex++, (byte)fragment.length());
    for(int i=0; i<fragment.length(); i++) {
      rawArray.putChar(arrIndex, fragment.charAt(i));
      arrIndex+=2;
    }
    Node[] children = node.getChildren();
    int childCount = children != null ? children.length : 0;
    // Put the attribute
    rawArray.put(arrIndex++, (byte)1);
    // Put childCount
    rawArray.put(arrIndex++, (byte)childCount);
    // Increase ArrIndex by childCount * 4
    int startOfChildOffets = arrIndex;
    arrIndex += childCount * 4;
    //    System.out.println(node.getString());
//    int size = node.getString().length() * 2 + childCount * 4 + 5;
//    arrIndex += size;
    int[] indexes = new int[childCount];
    int i = 0;
    if(children != null) {
      dos.writeByte(children.length);
      for(Node child : children) {
        indexes[i] = createFlatTrie(rawArray, startIndex, child);
        rawArray.putInt(startOfChildOffets + (4 * i), indexes[i]);
        if (indexes[i] - startIdx < 128) offsetHistogram[1] ++;
        else if (indexes[i] - startIdx < 16000) offsetHistogram[2] ++;
        else offsetHistogram[4] ++;
//        System.out.println(node.getString() + " Index " + i + " : " + indexes[i] + "  " + child.getString() );
        i++;
      }
      
    }
    return startIndex;
  }

  static int calculateRawArraySizeCompact (Stats s) {
    int totalSize = 0;
    // 1 byte for fragment length.
    totalSize += s.totalNodes;
    // 2 byte per char for fragments.
    totalSize += s.totalFragmentSize;
    // 1 byte per node for attributes)
    totalSize += s.totalNodes;
    // 1 byte per node for child count, (value is -1 for leaf nodes.)
    totalSize += s.totalNodes - s.totalLeafNodes;
    // 4 byte for subNodeOffsets
    totalSize += s.totalSubNodes * 4;
    // 1 child means it is adjacent.
    totalSize -= s.childCounts[1] * 5;
    return  totalSize; 
  }
  
  static int calculateRawArraySize (Stats s) {
    int totalSize = 0;
    // 1 byte for fragment length.
    totalSize += s.totalNodes;
    // 2 byte per char for fragments.
    totalSize += s.totalFragmentSize * 2;
    // 1 byte per node for attributes)
    totalSize += s.totalNodes;
    // 1 byte per node for child count, (value is -1 for leaf nodes.)
    totalSize += s.totalNodes;
    // 4 byte for subNodeOffsets
    totalSize += s.totalSubNodes * 4;
    return  totalSize; 
  }
  
  public static void report(Stats stats) {
    System.out.println("Total Nodes: " + stats.totalNodes);
    System.out.println("Total Leaf Nodes: " + stats.totalLeafNodes);
    System.out.println("Total ratio: " + (stats.totalLeafNodes * 100.0 / stats.totalNodes));
    
    int interimNodes = stats.totalNodes - stats.totalLeafNodes;
    System.out.println("Total interim Nodes: " + interimNodes);
    System.out.println("Max Depth: " + stats.maxLevel);
    
    int totalChildLinks = 0;
    for (int i = 0; i < stats.childCounts.length; i++) {
      if (stats.childCounts[i] == 0) continue;
      totalChildLinks += stats.childCounts[i] * i;
      System.out.println("Nodes with " + i + " children: " + stats.childCounts[i]);
    }
    
    System.out.println("Average fanout = " + ((double)totalChildLinks / interimNodes));
    
    for (int i = 0; i < stats.chainLengths.length; i++) {
      if (stats.chainLengths[i] == 0) continue;
      System.out.println("Chains of length " + i + " : " + stats.chainLengths[i]);
    }
    
    for (int i = 0; i < stats.strLengths.length; i++) {
      if (stats.strLengths[i] == 0) continue;
      System.out.println("String fragment of length " + i + " : " + stats.strLengths[i]);
    }
    
    System.out.println("Total string length: " + stats.totalFragmentSize);
    System.out.printf("Total string size(UTF-16): %d (UTF-8): %d \n", stats.totalFragmentSize * 2, stats.totalFragmentSizeUTF8 );
    //System.out.println("Average compression: %" + (100 - (totalStringLength * 100 / stats.totalFragmentSize)));
    System.out.println("Average str fragment length = " + ((double) stats.totalFragmentSize / stats.totalNodes));
    
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
    int rawArraySize;
    Stats s;
    SimpleCompactStringTrie cst = new SimpleCompactStringTrie(new TurkishAlphabet());
//    cst.add("ak");
//    cst.add("akasya");
//    cst.add("el");
//    cst.add("ela");
//    cst.add("elmas");
//    cst.add("elma");
//    cst.compress();
//    s = new Stats();
//    walk(cst.getRoot(), 0, 0, s);
//    report(s);
//    rawArraySize = calculateRawArraySize(s);
//    System.out.println("Total raw array size: " + rawArraySize);
//    ByteBuffer ra = ByteBuffer.allocate(rawArraySize);
//    createFlatTrie(ra, 0, cst.getRoot());
//    ra.flip();
//    System.out.println("Remaining: " + ra.remaining());
//    System.exit(0);
    
    Map<String, Integer> attributes = new TreeMap<String, Integer>();
    Map<String, Integer> attributeGroups = new TreeMap<String, Integer>();
        
    BufferedReader reader = new KaynakYukleyici("utf-8").getReader("kaynaklar/tr/bilgi/duzyazi-kilavuz.txt");
//    BufferedReader reader = new KaynakYukleyici("utf-8").getReader("kelime-frekans-chrome.txt");    
    String line;
    int total = 0;
    int totalChars = 0;
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
    
    s = new Stats();
    walk(cst.getRoot(), 0, 0, s);
    report(s);
    long time = System.currentTimeMillis();
    cst.compress();
    cst.save(new BufferedOutputStream(new FileOutputStream("tr.dic")));
    long delta = System.currentTimeMillis() - time;
    System.out.println("Compression time:" + delta);
    s = new Stats();
    walk(cst.getRoot(), 0, 0, s);
    report(s);
    rawArraySize = calculateRawArraySize(s);
    int compactArraySize = calculateRawArraySizeCompact(s);
    System.out.printf("Total raw array size: %d Compact version: %d \n", rawArraySize, compactArraySize);
    ByteBuffer rawArray = ByteBuffer.allocate(rawArraySize);
    
    
    delta = System.currentTimeMillis() - time;
    System.out.println("time:" + delta);
    SimpleCompactStringTrie cst2 = new SimpleCompactStringTrie(new TurkishAlphabet());
    cst2.load(new BufferedInputStream(new FileInputStream("tr.dic")));
    delta = System.currentTimeMillis() - time;
    System.out.println("Load time:" + delta + "ms");
    
    createFlatTrie(rawArray, 0, cst2.getRoot());
    System.out.println("ArraySize: " + arrIndex);

    for (int i = 0; i < offsetHistogram.length; i++) {
      if (offsetHistogram[i] > 0) System.out.println( i  + " : " + offsetHistogram[i]);
    }
  }
}
