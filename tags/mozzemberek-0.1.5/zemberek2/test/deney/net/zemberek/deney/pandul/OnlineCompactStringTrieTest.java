package net.zemberek.deney.pandul;

import junit.framework.TestCase;
import java.util.Arrays;
import java.util.Random;
import static net.zemberek.deney.pandul.OnlineCompactStringTrie.*;

public class OnlineCompactStringTrieTest extends TestCase {

  public void testOnlineCompactTrieTest() {
    assertEquals(0, getSplitPoint("foo".toCharArray(), 0, "bar".toCharArray()));
    assertEquals(0, getSplitPoint("foo".toCharArray(), 0, "ar".toCharArray()));
    assertEquals(0, getSplitPoint("b".toCharArray(), 0, "x".toCharArray()));
    assertEquals(0, getSplitPoint("fo".toCharArray(), 0, "barx".toCharArray()));
  
    assertEquals(1, getSplitPoint("foo".toCharArray(), 0, "far".toCharArray()));
    assertEquals(1, getSplitPoint("far".toCharArray(), 0, "foo".toCharArray()));
    assertEquals(1, getSplitPoint("fo".toCharArray(), 0, "fxy".toCharArray()));
    assertEquals(1, getSplitPoint("f".toCharArray(), 0, "f".toCharArray()));

    assertEquals(3, getSplitPoint("foo".toCharArray(), 0, "foo".toCharArray()));
    assertEquals(3, getSplitPoint("foo".toCharArray(), 0, "foobar".toCharArray()));
    assertEquals(3, getSplitPoint("fool".toCharArray(), 0, "foo".toCharArray()));
  
    assertEquals(3, getSplitPoint("xyzfoo".toCharArray(), 3, "foo".toCharArray()));
    assertEquals(2, getSplitPoint("foo".toCharArray(), 1, "oobar".toCharArray()));
    assertEquals(3, getSplitPoint("bool".toCharArray(), 1, "ool".toCharArray()));  
  }
 
  public void testSimpleInsertion() {
    OnlineCompactStringTrie<String> ost = new OnlineCompactStringTrie<String>();
    ost.add("foo", "f");
    ost.add("foobar", "f2");
    ost.add("foobarjazz", "f3");  
    assertEquals("#:(f)|foo:(b)*|bar:(j)*|jazz:()*|", ost.toFlatString());
  }
 
  public void testSimpleInsertionSeperate() {
    OnlineCompactStringTrie<String> ost = new OnlineCompactStringTrie<String>();
    ost.add("foo", "f");
    ost.add("bar", "f2");
    ost.add("jazz", "f3");  
    assertEquals("#:(bfj)|bar:()*|foo:()*|jazz:()*|", ost.toFlatString());
  }
 
  public void testSimpleInsertionSimpleSplit() {
    OnlineCompactStringTrie<String> ost = new OnlineCompactStringTrie<String>();
    ost.add("foobar", "f");
    ost.add("foo", "f2");
  }
 
  public void testSimpleInsertionSimpleSplit2() {
    OnlineCompactStringTrie<String> ost = new OnlineCompactStringTrie<String>();
    ost.add("foobarjazz", "f");
    ost.add("foobar", "f2");
    ost.add("foo", "f3");
  }

  public void testSimpleInsertionComplexSplit() {
    OnlineCompactStringTrie<String> ost = new OnlineCompactStringTrie<String>();
    ost.add("foobar", "f");
    ost.add("fox", "f2");
  }

  public void testSimpleInsertionComplexSplit2() {
    OnlineCompactStringTrie<String> ost = new OnlineCompactStringTrie<String>();
    ost.add("foo", "f");
    ost.add("fx", "f2");
    ost.add("fa", "f2");
    ost.add("fax", "f2");
  } 
 
  public void testSimpleInsertionComplexSplit3() {
    OnlineCompactStringTrie<String> ost = new OnlineCompactStringTrie<String>();
    ost.add("foo", "f");
    ost.add("foobar", "f2");
    ost.add("foax", "f2");
    ost.add("foojazz", "f2");
    ost.add("f", "f2");
  }

  public void testSimpleInsertionComplexSplit5() {
    OnlineCompactStringTrie<String> ost = new OnlineCompactStringTrie<String>();
    ost.add("b", "f");
    ost.add("ab", "f2");
    ost.add("a", "f2");
  }

  public void testSimpleInsertionComplexSplit7() {
    OnlineCompactStringTrie<String> ost = new OnlineCompactStringTrie<String>();
    String[] testStr = {"aba", "abc", "a", "ab", "abba", "aaa", "baa"};
    for(String s : testStr) {
      ost.add(s, s);
    }
    for(int i = 0; i < testStr.length; i++ ) {
      OnlineCompactStringTrie<String> newOst = new OnlineCompactStringTrie<String>();
      for(int j = i; j < i + testStr.length; j++) {
        String s = testStr[j % testStr.length];
        newOst.add(s, s);
      }
      assertEquals(ost.toFlatString(), newOst.toFlatString());
    }
  }
 
  public void testSimpleInsertionComplexSplit6() {
    OnlineCompactStringTrie<String> ost = new OnlineCompactStringTrie<String>();
    String[] testStr = {"blahfoobar", "blahfoojazz", "blahjazz", "blahfoo"};
    for(String s : testStr) {
      ost.add(s, s);
    }
    System.out.println(ost.toFlatString());
  }
 
  public void testSimpleInsertionComplexSplitRandom() {
    OnlineCompactStringTrie<String> ost = new OnlineCompactStringTrie<String>();
    String[] testStrArray = new String[100];
    for (int i = 0; i < testStrArray.length; i++) {
      testStrArray[i] = createRandomStringFrom("0123456-", 10);
    }
    System.out.println(Arrays.toString(testStrArray));
    for(String s : testStrArray) {
      ost.add(s, s);
    }
    System.out.println(ost.toDeepString());
    System.out.println(ost.toFlatString());
    for(int i = 0; i < testStrArray.length; i++ ) {
      OnlineCompactStringTrie<String> newOst = new OnlineCompactStringTrie<String>();
      for(int j = i; j < i + testStrArray.length; j++) {
        String s = testStrArray[j % testStrArray.length];
        newOst.add(s, s);
      }
      assertEquals(ost.toFlatString(), newOst.toFlatString());
    }
  }
 
 
  private static Random r = new Random(1);
  private String createRandomStringFrom(String s, int maxSize) {
    char[] c = new char[r.nextInt(maxSize) + 1];
    char[] chars = s.toCharArray();
    for(int i=0; i < c.length ; i++) {
      c[i] = chars[r.nextInt(chars.length)];
    }
    return new String(c);
  }
}

