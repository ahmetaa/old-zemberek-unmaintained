package net.zemberek.deney.pandul;

public class TinyStrings {
  /**
   * long based constructor. checks if input parameter is valid.
   *
   * @param value the long
   * @return long value.
   * @throws IndexOutOfBoundsException if String size is not between [0-7] (inclusive)
   * @throws IllegalArgumentException  if the input vlaue is not valid. (size and idex values)
   */
  public static long create(long value) {
    final int l = length(value);
    if (l < 0 || l > 7)
      throw new IndexOutOfBoundsException("length must be between [0..7]. But it is:" + l);

    long s = value >> 8;
    for (int i = 0; i < l; i++) {
      long t = s & 0xff;
      if (t < 0 || t >= TurkishAlphabet.ALPHABET_SIZE)
        throw new IllegalArgumentException("String connot contain characters outside TurkishAlphabet");
      s = s >> 8;
    }
    return s;
  }

  /**
   * constructor using
   *
   * @param str input string.
   * @return long vString representation.
   * @throws IndexOutOfBoundsException if String size is larger than 7
   * @throws IllegalArgumentException  if there is a character out of TurkishAlphabet set.
   */
  public static long create(String str) {
    if (str == null || str.length() == 0) {
      return 0L;
    }
    if (str.length() > 7) {
      throw new IndexOutOfBoundsException("Compact String size cannot be larger than 7");
    }
    long t = 0;
    for (int i = str.length() - 1; i >= 0; i--) {
      char c = str.charAt(i);
      int index = TurkishAlphabet.getIndex(c);
      if (index == -1)
        throw new IllegalArgumentException("String connot contain characters outside TurkishAlphabet");
      t = (t | index) << 8;
    }
    t = t | str.length();
    return t;
  }

  /**
   * creates an object using value char c.
   *
   * @param c the content.
   * @return long value.
   * @throws IllegalArgumentException if character is out of TurkishAlphabet set.
   */
  public static long create(char c) {
    final int index = TurkishAlphabet.getIndex(c);
    if (index != -1)
      return index << 8 | 0x01;
    else
      throw new IllegalArgumentException("char:" + c + "cannot be outside TurkishAlphabet");
  }


  /**
   * lenth of the containing characters.
   *
   * @param value the long representation.
   * @return length
   */
  public static int length(long value) {
    return (int) value & 0xFF;
  }

  /**
   * returns the char at given index. index value must be between [0..length) length exclusive.
   *
   * @param l the long representation.
   * @param index index
   * @return char at that index.
   */
  public char charAt(long l, int index) {
    if (index < 0 || index >= length(l))
      throw new IndexOutOfBoundsException("index must be between [0.." + length(l) + "). But it is:" + index);
    return TurkishAlphabet.getChar((int) ((l >> (index + 1) * 8) & 0xff));
  }

  /**
   * generates String representation.
   *
   * @param l the long representation.
   * @return String representation.
   */
  public static String asString(long l) {
    final int length = length(l);
    if (length == 0)
      return "";

    StringBuilder sb = new StringBuilder(length);
    l = l >> 8;
    for (int i = 0; i < length; i++) {
      sb.append(TurkishAlphabet.getChar((int) l & 0xff));
      l = l >> 8;
    }
    return sb.toString();
  }

}
