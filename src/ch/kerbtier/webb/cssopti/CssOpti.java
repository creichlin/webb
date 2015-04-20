package ch.kerbtier.webb.cssopti;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.lesscss.LessCompiler;
import org.lesscss.LessException;

import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;
import com.yahoo.platform.yui.compressor.CssCompressor;

import ch.kerbtier.webb.pictomizer.Pictomizer;

public class CssOpti {
  private List<Part> css = new ArrayList<>();
  private Path targetFolder;
  private String id;
  
  private Hasher hasher;

  public CssOpti(Path targetFolder) {
    this.targetFolder = targetFolder;
    
    hasher = Hashing.murmur3_128().newHasher();
  }

  public void add(Path path) {
    if(id != null) {
      throw new RuntimeException("can only add files before calling getId()");
    }
    css.add(new Part(path));
    try {
      hasher.putBytes(FileUtils.readFileToByteArray(path.toFile()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public String getId() {
    if(id == null) {
      id = BaseEncoding.base64Url().encode(hasher.hash().asBytes(), 0, 9);
    }
    return id;
  }

  public String process() {

    compileLess();

    Map<Path, String> map = collectUrlResources();

    replaceUrls(map);

    compressCss();
    
    return mergeCss();
  }

  private String mergeCss() {
    StringBuilder sb = new StringBuilder();
    for (Part p : css) {
      sb.append(p.getCss() + "\n");
    }
    return sb.toString();
  }

  private void compressCss() {
    for (Part p : css) {
      try {
        CssCompressor cssComp = new CssCompressor(new StringReader(p.getCss()));
        StringWriter sw = new StringWriter();
        cssComp.compress(sw, 0);
        p.setCss(sw.toString());
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }
  }

  private void replaceUrls(Map<Path, String> map) {
    for (Part p : css) {
      p.setCss(UrlOperator.replace(map, p.getSource().getParent(), p.getCss()));
    }
  }

  private Map<Path, String> collectUrlResources() {
    Map<Path, String> paths = new HashMap<>();
    Set<Path> resources = new HashSet<>();
    for (Part p : css) {
      resources.addAll(UrlOperator.find(p.getSource().getParent(), p.getCss()));
    }

    for (Path resource : resources) {
      Pictomizer pic = new Pictomizer(resource);
      ByteArrayOutputStream out = new ByteArrayOutputStream();

      try {
        ImageIO.write(pic.getTarget(), pic.getFormat(), out);

        Hasher hash = Hashing.murmur3_128().newHasher();
        hash.putBytes(out.toByteArray());
        String key = BaseEncoding.base64Url().encode(hash.hash().asBytes(), 0, 9) + "." + pic.getFormat();

        paths.put(resource, key);

        ByteArrayInputStream bais = new ByteArrayInputStream(out.toByteArray());
        FileUtils.copyInputStreamToFile(bais, targetFolder.resolve(key).toFile());
        out.close();
        bais.close();

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return paths;
  }

  private void compileLess() {
    LessCompiler lc = new LessCompiler();

    for (Part p : css) {
      try {
        p.setCss(lc.compile(p.getSource().toFile()));
      } catch (IOException e) {
        e.printStackTrace();
      } catch (LessException e) {
        e.printStackTrace();
      }
    }
  }

  class Part {
    private Path source;
    private String css;

    public Part(Path path) {
      source = path;
    }

    public String getCss() {
      return css;
    }

    public void setCss(String css) {
      this.css = css;
    }

    public Path getSource() {
      return source;
    }

  }
}
