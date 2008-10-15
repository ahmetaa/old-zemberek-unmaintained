package net.zemberek.deney.pandul;

/**
 * immutable long representation of small strings.
 * it uses the alphabet indexes as characters.(index starts from zero.)
 * if the length of the string is l, the format is:
 * index(charAt(l-1)|index(charAt(l-2)|..|index(charAt(0)|l
 * for example. String "abe" is : [04010003] -> [e b a 3]
 * l can be maximum 7.
 */
public class TinyTrString {

  final long value;

  /**
   * long based constructor. checks if input parameter is valid.
   *
   * @param value the long
   * @throws IndexOutOfBoundsException if String size is not between [0-7] (inclusive)
   * @throws IllegalArgumentException  if the input vlaue is not valid. (size and idex values)
   */
  public TinyTrString(long value) {
    this.value = value;
    int l = length();
    if (l < 0 || length() > 7)
      throw new IndexOutOfBoundsException("length must be between [0..7]. But it is:" + l);

    long s = value >> 8;
    for (int i = 0; i < l; i++) {
      long t = s & 0xff;
      if (t < 0 || t >= TurkishAlphabet.ALPHABET_SIZE)
        throw new IllegalArgumentException("String connot contain characters outside TurkishAlphabet");
      s = s >> 8;
    }
  }

  /**
   * constructor using
   *
   * @param str input string.
   * @throws IndexOutOfBoundsException if String size is larger than 7
   * @throws IllegalArgumentException  if there is a character out of TurkishAlphabet set.
   */
  public TinyTrString(String str) {
    if (str == null || str.length() == 0) {
      this.value = 0;
      return;
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
    value = t;
  }

  /**
   * creates an object using value char c.
   *
   * @param c the content.
   * @throws IllegalArgumentException if character is out of TurkishAlphabet set.
   */
  public TinyTrString(char c) {
    final int index = TurkishAlphabet.getIndex(c);
    if (index != -1)
      this.value = index << 8 | 0x01;
    else
      throw new IllegalArgumentException("char:" + c + "cannot be outside TurkishAlphabet");
  }


  /**
   * lenth of the containing characters.
   *
   * @return length
   */
  public int length() {
    return (int) value & 0xFF;
  }

  /**
   * returns the char at given index. index value must be between [0..length) length exclusive.
   *
   * @param index index
   * @return char at that index.
   */
  public char charAt(int index) {
    if (index < 0 || index >= length())
      throw new IndexOutOfBoundsException("index must be between [0.." + length() + "). But it is:" + index);
    return TurkishAlphabet.getChar((int) ((value >> (index + 1) * 8) & 0xff));
  }

  /**
   * generates String representation.
   *
   * @return String representation.
   */
  public String asString() {
    final int length = length();
    if (length == 0)
      return "";

    StringBuilder sb = new StringBuilder(length);
    long s = value >> 8;
    for (int i = 0; i < length; i++) {
      sb.append(TurkishAlphabet.getChar((int) s & 0xff));
      s = s >> 8;
    }
    return sb.toString();
  }

  @Override
  public String toString() {
    return asString();
  }


}
