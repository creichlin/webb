package ch.kerbtier.webb.transform2d;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tests {
  public static void main(String[] args) throws IOException {
    
    ImageTransformer it = new ImageTransformer("tests/images");
    ImageIO.write(it.transform("isle.jpeg", "$1.fit(300x100)"), "png", new File("tests/images/out/fitx.png"));
    
    ImageIO.write(it.transform("isle.jpeg", "$1.fit(100x200)"), "png", new File("tests/images/out/fity.png"));
    
    ImageIO.write(it.transform("isle.jpeg", "$1.crop(200|0 300x600)"), "png", new File("tests/images/out/cropy.png"));
  }
}
