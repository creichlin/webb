package ch.kerbtier.webb.transform2d.parser;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import ch.kerbtier.webb.transform2d.parser.T2dParser.OperationContext;

public class T2dTests {

  public static void main(String[] args) {

    try {

      InputStream is = new ByteArrayInputStream("$1.crop(50|18 74|42).border(3, #0000ff)".getBytes("UTF-8"));

      ANTLRInputStream input = new ANTLRInputStream(is);
      T2dLexer lexer = new T2dLexer(input);
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      T2dParser parser = new T2dParser(tokens);

      OperationContext tree = parser.operation(); // parse
      
      
      List<BufferedImage> imgs = new ArrayList<BufferedImage>();
      
      imgs.add(ImageIO.read(new URL("http://www.libpng.org/pub/png/img_png/pnglogo-blk-sml1.png")));
      
      T2dExecuteVisitor visitor = new T2dExecuteVisitor(imgs);
      
      visitor.visit(tree); // initiate walk of tree with listener

      System.out.println(visitor.getLastOperation());
      
      ImageIO.write(visitor.getLastOperation(), "png", new File("cropOut.png"));
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
