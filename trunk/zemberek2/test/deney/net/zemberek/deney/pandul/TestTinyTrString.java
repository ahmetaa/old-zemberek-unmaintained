package net.zemberek.deney.pandul;

import junit.framework.Assert;
import org.junit.Test;


public class TestTinyTrString {

  @Test
  public void testLongConstructor() {
    Assert.assertEquals(1, new TinyTrString(0x0101L).length());
    Assert.assertEquals(2, new TinyTrString(0x020102L).length());
    Assert.assertEquals("ab", new TinyTrString(0x010002L).asString());
    Assert.assertEquals("cbcb", new TinyTrString(0x0102010204L).asString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLongConstructorException1() {
    new TinyTrString(0xffff);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLongConstructorException2() {
    new TinyTrString(0xff01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStringConstructorException1() {
    new TinyTrString("abcdegfh");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStringConstructorException2() {
    new TinyTrString("x");
  }

  String[] strs = {"", "blah", "a", "abcde", "abcdef"};

  @Test
  public void testAsString() {
    Assert.assertEquals("", new TinyTrString(null).asString());
    for (String str : strs) {
      Assert.assertEquals(str, new TinyTrString(str).asString());
    }
  }

  @Test
  public void testLength() {
    for (String str : strs) {
      Assert.assertEquals(str.length(), new TinyTrString(str).length());
    }
  }


}
