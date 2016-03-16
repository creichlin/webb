package ch.kerbtier.webb.transform2d;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import ch.kerbtier.esdi.Inject;
import ch.kerbtier.webb.di.InjectSingleton;
import ch.kerbtier.webb.transform2d.parser.T2dExecuteVisitor;
import ch.kerbtier.webb.transform2d.parser.T2dLexer;
import ch.kerbtier.webb.transform2d.parser.T2dParser;
import ch.kerbtier.webb.transform2d.parser.T2dParser.OperationContext;
import ch.kerbtier.webb.util.Configuration;

@Inject
public class ImageTransformer {

  private String localBasePath;

  @InjectSingleton
  private Configuration config;
  
  public ImageTransformer() {
    localBasePath = config.get("t2d.localBasePath", "public");
  }

  public ImageTransformer(String path) {
    localBasePath = path;
  }

  /**
   * images is a list of image paths, whereas all images after the first are
   * relative to the path of the first image.
   * 
   * @param images
   * @param code
   * @return
   */

  public BufferedImage transform(String images, String code) {
    List<File> files = pathsToRelative(images);
    return transform(files, code);

  }

  public List<File> pathsToRelative(String images) {
    List<File> files = new ArrayList<>();

    File base = null;

    for (String img : images.split(",")) {
      File file = null;

      if (base == null) {
        if (img.startsWith("/")) {
          file = new File(img);
        } else {
          file = new File(new File(localBasePath), img);
        }
        base = file.getParentFile();
      } else {
        file = new File(base, img);
      }

      files.add(file);
    }
    return files;
  }

  public BufferedImage transform(List<File> files, String code) {
    List<BufferedImage> imgs = new ArrayList<>();
    for (File file : files) {
      try {
        imgs.add(ImageIO.read(file));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return transformImages(imgs, code);
  }

  public BufferedImage transform(ByteBuffer ba, String code) {
    try {
      ByteArrayInputStream bais;
      try {
        bais = new ByteArrayInputStream(ba.array());
      } catch (UnsupportedOperationException e) {
        byte[] data = new byte[ba.remaining()];
        ba.get(data);
        bais = new ByteArrayInputStream(data);
      }
      BufferedImage bi = ImageIO.read(bais);
      bais.close();

      List<BufferedImage> bis = new ArrayList<>();
      bis.add(bi);

      return transformImages(bis, code);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public BufferedImage transformImages(List<BufferedImage> imgs, String code) {
    if (code != null && code.length() > 0) {
      try {

        InputStream is = new ByteArrayInputStream(code.getBytes("UTF-8"));

        ANTLRInputStream input = new ANTLRInputStream(is);
        T2dLexer lexer = new T2dLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        T2dParser parser = new T2dParser(tokens);

        OperationContext tree = parser.operation(); // parse

        T2dExecuteVisitor visitor = new T2dExecuteVisitor(imgs);
        visitor.visit(tree);
        return visitor.getLastOperation();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }

    } else {
      return imgs.get(imgs.size() - 1);
    }
  }

}
