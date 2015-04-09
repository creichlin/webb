package ch.kerbtier.webb.render;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Map.Entry;

import ch.kerbtier.webb.util.ContextInfo;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.google.common.base.CaseFormat;

public class MustacheRenderer {

  private Handlebars handlebars;

  public MustacheRenderer(final ContextInfo ci) {

    FileTemplateLoader loader = new FileTemplateLoader(ci.getLocalPath().resolve("templates").toFile());

    loader.setSuffix("");

    handlebars = new Handlebars(loader);

    handlebars.registerHelper("render", new Helper<Object>() {

      @Override
      public CharSequence apply(Object model, Options options)
          throws IOException {
        StringWriter sw = new StringWriter();
        render(sw, model);
        return new Handlebars.SafeString(sw.toString());
      }

    });

    handlebars.registerHelper("map", new Helper<Map<? extends Object, ? extends Object>>(){
      @Override
      public CharSequence apply(Map<? extends Object, ? extends Object> map, Options opts) throws IOException {
        
        StringBuilder sb = new StringBuilder();
        
        for(Entry<? extends Object, ? extends Object> e: map.entrySet()) {
          sb.append(opts.fn(e));
        }
        return sb.toString();
      }
    });

    handlebars.registerHelper("link", new Helper<String>(){
      @Override
      public CharSequence apply(String link, Options opts) throws IOException {
        String all = link;
        for(Object o: opts.params) {
          all += o;
        }
        
        return "<a href=\"" + ci.getPath() + "/" + all + "\">" + opts.fn() + "</a>";
      }
    });
    
    handlebars.registerHelper("url", new Helper<String>(){
      @Override
      public CharSequence apply(String link, Options opts) throws IOException {
        String all = link;
        for(Object o: opts.params) {
          all += o;
        }
        
        return ci.getPath() + "/" + all;
      }
    });
    
    handlebars.registerHelper("css", new Helper<String>(){

      @Override
      public CharSequence apply(String link, Options opts) throws IOException {
        String all = link;
        for(Object o: opts.params) {
          all += o;
        }
        return new Handlebars.SafeString("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + ci.getPath() + "/" + all + "\">");
      }
      
    });

    handlebars.registerHelper("js", new Helper<String>(){

      @Override
      public CharSequence apply(String link, Options opts) throws IOException {
        String all = link;
        for(Object o: opts.params) {
          all += o;
        }
        return new Handlebars.SafeString("<script  type=\"text/javascript\" language=\"JavaScript\" src=\"" + ci.getPath() + "/" + all + "\"></script>");
      }
      
    });

  }
  
  public void registerHelper(Object helper) {
    handlebars.registerHelpers(helper);
  }

  public void render(Writer pw, Object model) {
    if(model == null) {
      throw new RuntimeException("cannot evaluate view for null model");
    }
    
    String name = model.getClass().getSimpleName();
    name = name.substring(name.lastIndexOf(".") + 1);
    name = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
    render(pw, name + ".html", model);
  }

  public void render(Writer pw, String template, Object model) {
    try {
      Template compiled = handlebars.compile(template);
      compiled.apply(model, pw);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
