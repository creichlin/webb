package ch.kerbtier.weproc.css;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.kerbtier.weproc.Processor;
import ch.kerbtier.weproc.Source;
import ch.kerbtier.weproc.urlmodifiers.URLModifier;

public class URLProcessor<T extends URLModifier> implements Processor {

  private static Pattern URL = Pattern.compile("url\\(\\s*'(.*?)'\\s*\\)|url\\((.*?)\\)");

  private T urlModifier;

  public URLProcessor(T urlModifier) {
    this.urlModifier = urlModifier;
  }

  @Override
  public String process(Source source) {
    Matcher m = URL.matcher(source.get());
    StringBuffer sb = new StringBuffer();
    while (m.find()) {
      
      String url = m.group(1);
      if(url == null) {
        url = m.group(2);
      }
      
      
      String replacement = "url(" + urlModifier.change(url) + ")";
      try {
        m.appendReplacement(sb, replacement);
      } catch (IndexOutOfBoundsException e) {
        System.out.println("problem in url in: " + source.get().substring(m.start()));
        System.out.println("replacemnt: " + replacement);
        e.printStackTrace();
      }
    }
    m.appendTail(sb);

    return sb.toString();
  }

  public T getModifier() {
    return urlModifier;
  }
}
