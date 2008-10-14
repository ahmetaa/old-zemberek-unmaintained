package net.zemberek.deney.pandul;

/**
 * immutable long representation of small strings.
 * it uses the alphabet indexes as characters.
 * if the length of the string is l, the format is:
 * index(charAt(l-1)|index(charAt(l-2)|..|index(charAt(0)|l
 * for example. String "abe" is : [06020103] -> [e b a 3]
 * l can be maximum 7.
 */
public class TinyTrString {

  final long value;

  /**
   * long based constructor. checks if input parameter is valid.
   * TODO: maybe validity check can be seperated for faster construction.
   *
   * @param value the long
   * @throws IllegalArgumentException if the input vlaue is not valid. (size and idex values)
   */
  public TinyTrString(long value) {
    this.value = value;
    if (!isValid())
      throw new IllegalArgumentException("The value is not valid:" + value);
  }

  /**
   * checks if the value set is valid. this only may return false if "long" constructor is used.
   *
   * @return true if, the size information is correct and embedded characters are valid.
   */
  private boolean isValid() {
    int l = length();
    if (l < 0 || length() > 7)
      return false;
    if (l == 0)
      return true;
    long s = value >> 8;
    for (int i = 0; i < l; i++) {
      long t = s & 0xff;
      if (t < 0 || t >= TurkishAlphabet.ALPHABET_SIZE)
        return false;
      s = s >> 8;
    }
    return true;
  }

  /**
   * constructor using
   *
   * @param str input string.
   * @throws IllegalArgumentException if String size is larger than 7 , or if there is a character out of
   *                                  TurkishAlphabet set.
   */
  public TinyTrString(String str) {
    if (str == null || str.length() == 0) {
      this.value = 0;
      return;
    }
    if (str.length() > 7) {
      throw new IllegalArgumentException("Compact String size cannot be larger than 7");
    }
    long t = 0;
    for (int i = str.length() - 1; i >= 0; i--) {
      char c = str.charAt(i);
      int index = TurkishAlphabet.getIndex(c);
      if (index == -1)
        throw new IllegalArgumentException("Compact String size cannot be larger than 7");
      t = (t | index) << 8;
    }
    t = t | str.length();
    value = t;
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
   * generates String representation.
   *
   * @return String representation.
   */
  public String asString() {
    final int length = length();
    if (length == 0) return "";
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
