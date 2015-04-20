package ch.kerbtier.weproc.css;

import org.lesscss.LessCompiler;
import org.lesscss.LessException;

import ch.kerbtier.weproc.Processor;
import ch.kerbtier.weproc.Source;

public class LessCSS implements Processor {
  private LessCompiler lessCompiler = new LessCompiler();
  
  private LessCssPathResolver resolver = null;
  
  public LessCSS(LessCssPathResolver resolver) {
    this.resolver = resolver;
  }
  
  @Override
  public String process(Source source) {
    try {
      return lessCompiler.compile(source.get());
    } catch (LessException e) {
      String name = source.getName();
      System.out.println("file " + name + " " + e.getMessage());
      return source.get();
    }
  }
}
