package ch.kerbtier.webb.util;

import com.google.common.base.Joiner;

public class HttpInfoParameter {
  
  private String[] value;
  
  public HttpInfoParameter(String[] value) {
    this.value = value;
  }
  
  public String asString() {
    if(value != null) {
      if(value.length == 0) {
        return null;
      } else if(value.length == 1) {
        return value[0];
      } else {
        return Joiner.on("|").join(value);
      }
    }
    return null;
  }

}
