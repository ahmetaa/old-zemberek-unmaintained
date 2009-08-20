package net.zemberek.deney.pandul;

import org.junit.Test;
import static junit.framework.Assert.assertEquals;

public class TestTinyStrings {
  TinyStrings ts = new TinyStrings(new TurkishAlphabet());
  @Test
  public void testLongCreate() {
    // 00|0001-0001
    assertEquals(1, ts.length(ts.create(0x11L)));
    // 000010-00|0001-0010
    assertEquals(2, ts.length(ts.create(0x812L)));
    // 000010-00|0001-0010
    assertEquals("bc", ts.asString(ts.create(0x812L)));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testLongCreateException1() {
    ts.create(0xffff);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLongCreateException2() {
    // 11|1111-0001
    ts.create(0x03f1L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testStringCreateException1() {
    ts.create("abcdegfhijk");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStringCreateException2() {
    ts.create("x");
  }

  @Test
  public void testCharAt() {
    long t = ts.create("abcdef");
    assertEquals('a', ts.charAt(t, 0));
    assertEquals('b', ts.charAt(t, 1));
    assertEquals('f', ts.charAt(t, 5));
  }
  
  @Test
  public void testCharAt2() {
    long t = ts.create('b');
    assertEquals('b', ts.charAt(t, 0));
  }
  
  @Test(expected = IndexOutOfBoundsException.class)
  public void testCharAtException1() {
    ts.charAt(ts.create("abcdegf"), -1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testCharAtException2() {
    ts.charAt(ts.create("abc"), 3);
  }

  @Test
  public void testCharCreate() {
    assertEquals("a", ts.asString(ts.create('a')));
    assertEquals(1, ts.length(ts.create('a')));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCharCreateException1() {
    ts.create('$');
  }

  String[] strs = {"blah", "", "a", "abcde", "abcdef"};

  @Test
  public void testAsString() {
    assertEquals("", ts.asString(ts.create(null)));
    for (String str : strs) {
      assertEquals("not working for:" + str, str, ts.asString(ts.create(str)));
    }
  }

  @Test
  public void testLength() {
    for (String str : strs) {
      assertEquals(str.length(), ts.length(ts.create(str)));
    }
  }

  @Test
  public void testAdd() {
    assertEquals("abcdefg", ts.asString(ts.addChar(ts.create("abcdef"), 'g')));
    assertEquals("bd", ts.asString(ts.addChar(ts.create("b"), 'd')));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testAddException1() {
    ts.addChar(ts.create("abcdegfhij"), 'k');
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddException2() {
    ts.addChar(ts.create("abc"), 'x');
  }

}
