package net.zemberek.deney.pandul;

/**
 * This class has static methods for special long numbers that actually represent
 * small Strings.
 * it uses the alphabet indexes as characters.(index starts from zero.)
 * if the length of the string is l, the format is:
 * index(charAt(l-1)|index(charAt(l-2)|..|index(charAt(0)|l
 * for characters 6 bit, for length, 4 bits values are used.
 * for example. String "abe" is :
 * binary[ 000100 000001 000000 0011]
 * hex [40403] -> [e b a 3]
 * l can be maximum 10.
 */
public class TinyStrings {

  private static final int LENGTH_BIT_MASK = 0x0f;
  private static final int MAX_STRING_LEGTH = 10;
  private static final int CHAR_BIT_SIZE = 6;
  private static final int CHAR_BIT_MASK = 0x3f;
  private static final int LENGTH_BIT_SIZE = 4;

  /**
   * long based generator. it sounds useless but it actually makes validity check.
   *
   * @param value the long
   * @return long value.
   * @throws IndexOutOfBoundsException if String size is not between [0-10] (inclusive)
   * @throws IllegalArgumentException  if the input vlaue is not valid. (size and idex values)
   */
  public static long create(long value) {
    final int l = length(value);
    if (l < 0 || l > MAX_STRING_LEGTH - 1)
      throw new IndexOutOfBoundsException("length must be between [0.." + (MAX_STRING_LEGTH - 1) + "]. But it is:" + l);

    long s = value >> LENGTH_BIT_SIZE;
    for (int i = 0; i < l; i++) {
      long t = s & CHAR_BIT_MASK;
      if (t < 0 || t >= TurkishAlphabet.ALPHABET_SIZE)
        throw new IllegalArgumentException("String connot contain characters outside TurkishAlphabet");
      s = s >> CHAR_BIT_SIZE;
    }
    return value;
  }

  /**
   * String based generator
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
    if (str.length() > MAX_STRING_LEGTH) {
      throw new IndexOutOfBoundsException("String size cannot be larger than " + MAX_STRING_LEGTH);
    }
    long t = 0;
    for (int i = str.length() - 1; i >= 0; i--) {
      int index = TurkishAlphabet.getIndex(str.charAt(i));
      if (index == -1)
        throw new IllegalArgumentException("String connot contain characters outside TurkishAlphabet");
      if (i > 0)
        t = (t | index) << CHAR_BIT_SIZE;
      else
        t = (t | index) << LENGTH_BIT_SIZE;
    }
    t = t | str.length();
    return t;
  }

  /**
   * char based generator.
   *
   * @param c the content.
   * @return long value.
   * @throws IllegalArgumentException if character is out of TurkishAlphabet set.
   */
  public static long create(char c) {
    final int index = TurkishAlphabet.getIndex(c);
    if (index != -1)
      return (index << CHAR_BIT_SIZE) | 0x01;
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
    return (int) value & LENGTH_BIT_MASK;
  }

  /**
   * returns the char at given index. index value must be between [0..length) length exclusive.
   *
   * @param l     the long representation.
   * @param index index
   * @return char at that index.
   */
  public static char charAt(long l, int index) {
    if (index < 0 || index >= length(l))
      throw new IndexOutOfBoundsException("index must be between [0.." + length(l) + "). But it is:" + index);
    l = l >> LENGTH_BIT_SIZE;
    return TurkishAlphabet.getChar((int) ((l >> (index * CHAR_BIT_SIZE)) & CHAR_BIT_MASK));
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
    l = l >> LENGTH_BIT_SIZE;
    for (int i = 0; i < length; i++) {
      sb.append(TurkishAlphabet.getChar((int) l & CHAR_BIT_MASK));
      l = l >> CHAR_BIT_SIZE;
    }
    return sb.toString();
  }

}
