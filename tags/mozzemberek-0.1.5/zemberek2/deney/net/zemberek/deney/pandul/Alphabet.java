package net.zemberek.deney.pandul;

public interface Alphabet {
  
  int getSize();
  
  int getIndex(char c);
  
  char getChar(int index);
  
  boolean isValid(char c);
}
