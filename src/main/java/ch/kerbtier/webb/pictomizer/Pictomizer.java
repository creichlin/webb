package ch.kerbtier.webb.pictomizer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.IndexColorModel;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Pictomizer {

  private BufferedImage source;
  private BufferedImage target;
  private String format;

  public Pictomizer(Path path) {
    try {
      read(Files.newInputStream(path));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Pictomizer(BufferedImage bi) {
    source = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = source.createGraphics();
    g2d.drawImage(bi, 0, 0, null);
    g2d.dispose();
    analyze();
  }

  private void read(InputStream inputStream) {
    try {
      BufferedImage bi = ImageIO.read(inputStream);
      source = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2d = source.createGraphics();
      g2d.drawImage(bi, 0, 0, null);
      g2d.dispose();
    } catch (IOException e) {
      e.printStackTrace();
    }
    analyze();
  }

  private void analyze() {
    Map<Integer, Integer> colors = new HashMap<>();

    for (int cy = 0; cy < source.getHeight(); cy++) {
      for (int cx = 0; cx < source.getWidth(); cx++) {
        int color = source.getRGB(cx, cy);
        if (colors.containsKey(color)) {
          colors.put(color, colors.get(color) + 1);
        } else {
          colors.put(color, 1);
        }
      }
    }

    boolean alpha = hasAlpha(colors);
    boolean grey = isGreyScale(colors);

    int pngMax = Math.min(2000, source.getHeight() * source.getWidth() / 10);

    boolean indexedColors = false;

    if (colors.size() < pngMax || alpha) {
      format = "png";
      if (alpha) {
        // if alpha, greyscale doesn't really matter, no special support as much
        // as i know
        if (colors.size() <= 256) { // indexed
          int[] ca = new int[colors.size()];

          int index = 0;

          for (int c : colors.keySet()) {
            ca[index] = c;
            index++;
          }

          target = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_BYTE_INDEXED,
              new IndexColorModel(8, ca.length, ca, 0, true, -1, DataBuffer.TYPE_BYTE));

          indexedColors = true;
        } else {
          // keep
        }

      } else { // no alpha
        if (grey) {
          target = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        } else { // no grey
          if (colors.size() <= 256) {
            int[] ca = new int[colors.size()];

            int index = 0;

            for (int c : colors.keySet()) {
              ca[index] = c;
              index++;
            }

            target = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_BYTE_INDEXED,
                new IndexColorModel(8, ca.length, ca, 0, false, -1, DataBuffer.TYPE_BYTE));
            indexedColors = true;
          } else {
            // keep
          }
        }
      }

    } else {
      format = "jpeg";
      target = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_RGB);
    }

    if (target != null) {
      Graphics2D g = target.createGraphics();

      if (indexedColors) {
        // if not seting image pixel per pixel it will dither indexed
        // palettes,
        // even when having the perfect palette.
        for (int x = 0; x < source.getWidth(); x++) {
          for (int y = 0; y < source.getHeight(); y++) {
            if (grey) {
              Color col = new Color(source.getRGB(x, y));
              int c = (col.getRed() + col.getGreen() + col.getBlue()) / 3;
              target.setRGB(x, y, new Color(c, c, c).getRGB());
            } else {
              target.setRGB(x, y, source.getRGB(x, y));
            }
          }
        }
      } else {
        Graphics2D g2 = target.createGraphics();
        g2.drawImage(source, 0, 0, null);
        g2.dispose();
      }

      g.dispose();
    } else {
      target = source;
    }
    source = null;
  }

  private boolean isGreyScale(Map<Integer, Integer> colors) {

    int maxdiff = 3;

    for (int i : colors.keySet()) {
      Color c = new Color(i);

      int avg = (c.getRed() + c.getBlue() + c.getGreen()) / 3;

      int dr = Math.abs(avg - c.getRed());
      int dg = Math.abs(avg - c.getBlue());
      int db = Math.abs(avg - c.getGreen());

      if (dr < maxdiff && dg < maxdiff && db < maxdiff) {
        // it's grey
      } else {
        return false;
      }
    }
    return true;
  }

  private boolean hasAlpha(Map<Integer, Integer> colors) {
    for (int i : colors.keySet()) {
      Color c = new Color(i, true);

      if (c.getAlpha() == 255) {
        // no alpha
      } else {
        return true;
      }
    }
    return false;
  }

  public Pictomizer(InputStream stream) {
    read(stream);
  }

  public RenderedImage getTarget() {
    return target;
  }

  public RenderedImage getSource() {
    return source;
  }

  public String getFormat() {
    return format;
  }

}
