package ch.kerbtier.weproc;

import java.util.ArrayList;
import java.util.List;

public class ProcessorChain implements Processor {
  private List<Processor> processors = new ArrayList<>();
  
  public ProcessorChain(Processor... processors){
    for(Processor processor: processors) {
      add(processor);
    }
  }
  
  public String process(Source source) {
    for(Processor processor: processors) {
      source = new StringSource(processor.process(source), source.getName());
    }
    return source.get();
  }
  
  public void add(Processor processor){
    processors.add(processor);
  }
}
