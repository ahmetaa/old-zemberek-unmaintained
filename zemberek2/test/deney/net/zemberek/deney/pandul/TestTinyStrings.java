package net.zemberek.deney.pandul;

import org.junit.Test;
import junit.framework.Assert;
import static junit.framework.Assert.assertEquals;
import static net.zemberek.deney.pandul.TinyStrings.*;

public class TestTinyStrings {

  @Test
  public void testLongCreate() {
    // 00|0001-0001
    assertEquals(1, length(create(0x11L)));
    // 000010-00|0001-0010
    assertEquals(2, length(create(0x812L)));
    // 000010-00|0001-0010
    assertEquals("bc", asString(create(0x812L)));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testLongCreateException1() {
    create(0xffff);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLongCreateException2() {
    // 11|1111-0001
    create(0x03f1L);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testStringCreateException1() {
    create("abcdegfhijk");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStringCreateException2() {
    create("x");
  }

  @Test
  public void testCharAt() {
    long t = create("abcdef");
    assertEquals('a', charAt(t, 0));
    assertEquals('b', charAt(t, 1));
    assertEquals('f', charAt(t, 5));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testCharAtException1() {
    charAt(create("abcdegf"), -1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testCharAtException2() {
    charAt(create("abc"), 3);
  }

  @Test
  public void testCharCreate() {
    assertEquals("a", asString(create('a')));
    assertEquals(1, length(create('a')));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCharCreateException1() {
    create('$');
  }

  String[] strs = {"blah", "", "a", "abcde", "abcdef"};

  @Test
  public void testAsString() {
    Assert.assertEquals("", TinyStrings.asString(TinyStrings.create(null)));
    for (String str : strs) {
      assertEquals("not working for:" + str, str, asString(create(str)));
    }
  }

  @Test
  public void testLength() {
    for (String str : strs) {
      assertEquals(str.length(), length(create(str)));
    }
  }

  @Test
  public void testAdd() {
    assertEquals("abcdefg", asString(addChar(create("abcdef"),'g')));
    assertEquals("bd", asString(addChar(create("b"),'d')));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testAddException1() {
    addChar(create("abcdegfhij"),'k');
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddException2() {
    addChar(create("abc"),'x');
  }

}
