package ch.kerbtier.weproc.css;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import ch.kerbtier.weproc.Processor;
import ch.kerbtier.weproc.Source;

import com.yahoo.platform.yui.compressor.CssCompressor;

public class CSSCompressor implements Processor {

  @Override
  public String process(Source source) {
    try {
      CssCompressor cssComp = new CssCompressor(new StringReader(source.get()));
      StringWriter sw = new StringWriter();
      cssComp.compress(sw, 0);
      return sw.toString();
    } catch (IOException e) {
      e.printStackTrace();
      return source.get();
    }
  }
}
