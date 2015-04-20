package ch.kerbtier.weproc;

import java.util.ArrayList;
import java.util.List;

public class MultiSource implements Source {

  private List<Source> sources = new ArrayList<Source>();
  
  private String delimiter = "\n\n";
  
  @Override
  public String get() {
    StringBuilder sb = new StringBuilder();
    
    for(Source source: sources) {
      sb.append(source.get());
      sb.append(delimiter);
    }
    
    return sb.toString();
  }
  
  public void add(Source source) {
    sources.add(source);;
  }

  @Override
  public String getName() {
    return "multiple-sources";
  }
}
