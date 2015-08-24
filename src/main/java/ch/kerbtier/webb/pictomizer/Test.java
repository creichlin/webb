package ch.kerbtier.webb.pictomizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;

public class Test {
  public static void main(String[] args) {
    
    
    try {
      for (Path p : Files.newDirectoryStream(Paths.get("tests/pictomizer"))) {
        if (Files.isDirectory(p)) {
          continue;
        }
        System.out.println(p.getFileName());
        Path t = p.getParent().resolve("out");
        Pictomizer pmz = new Pictomizer(p);

        String name = p.getFileName().toString();
        name = name.substring(0, name.lastIndexOf("."));

        
        ImageIO.write(pmz.getTarget(), pmz.getFormat(), Files.newOutputStream(t.resolve(name + "." + pmz.getFormat().toLowerCase())));

      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
