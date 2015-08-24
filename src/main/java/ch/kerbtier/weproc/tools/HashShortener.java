package ch.kerbtier.weproc.tools;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HashShortener {
  private List<String> names = new ArrayList<>();
  private String symbols = "a78vwxb9lmk5qj23rst6yzcde01nopufghi4";
  private int start = 126;

  public String getShortForm(String name){
    if(!names.contains(name)){
      names.add(name);
    }
    return encode(names.indexOf(name) + start);
  }

  public Collection<String> getNames(){
    return Collections.unmodifiableList(names);
  }

  public String getLongForm(String key){
    int index = decode(key) - start;
    if(names.size() > index){
      return names.get(index);
    }
    return null;
  }

  private int decode(String s) {
    final int B = symbols.length();
    int num = 0;
    for (char ch : s.toCharArray()) {
      num *= B;
      num += symbols.indexOf(ch);
    }
    return num;
  }
  
  private String encode(int num) {
    final int B = symbols.length();
    StringBuilder sb = new StringBuilder();
    while (num != 0) {
      sb.append(symbols.charAt((num % B)));
      num /= B;
    }
    return sb.reverse().toString();
  }
}
