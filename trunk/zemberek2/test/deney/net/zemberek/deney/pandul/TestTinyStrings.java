package net.zemberek.deney.pandul;

import org.junit.Test;
import junit.framework.Assert;

public class TestTinyStrings {

  @Test
  public void testLongCreate() {
    // 00|0001-0001
    Assert.assertEquals(1, TinyStrings.length(TinyStrings.create(0x11L)));
    // 000010-00|0001-0010
    Assert.assertEquals(2, TinyStrings.length(TinyStrings.create(0x812L)));
    // 000010-00|0001-0010
    Assert.assertEquals("bc", TinyStrings.asString(TinyStrings.create(0x812L)));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testLongCreateException1() {
    TinyStrings.create(0xffff);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLongCreateException2() {
    // 11|1111-0001
    TinyStrings.create(0x03f1L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testStringCreateException1() {
    TinyStrings.create("abcdegfhijk");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStringCreateException2() {
    TinyStrings.create("x");
  }

  @Test
  public void testCharAt() {
    long t = TinyStrings.create("abcdef");
    Assert.assertEquals('a', TinyStrings.charAt(t, 0));
    Assert.assertEquals('b', TinyStrings.charAt(t, 1));
    Assert.assertEquals('f', TinyStrings.charAt(t, 5));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testCharAtException1() {
    TinyStrings.charAt(TinyStrings.create("abcdegf"), -1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testCharAtException2() {
    TinyStrings.charAt(TinyStrings.create("abc"), 3);
  }

  @Test
  public void testCharCreate() {
    Assert.assertEquals("a", TinyStrings.asString(TinyStrings.create('a')));
    Assert.assertEquals(1, TinyStrings.length(TinyStrings.create('a')));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCharCreateException1() {
    TinyStrings.create('$');
  }

  String[] strs = {"blah", "", "a", "abcde", "abcdef"};

  @Test
  public void testAsString() {
    //Assert.assertEquals("", TinyStrings.asString(TinyStrings.create(null)));
    for (String str : strs) {
      Assert.assertEquals("not working for:" + str, str, TinyStrings.asString(TinyStrings.create(str)));
    }
  }

  @Test
  public void testLength() {
    for (String str : strs) {
      Assert.assertEquals(str.length(), TinyStrings.length(TinyStrings.create(str)));
    }
  }

}
