package ch.kerbtier.webb.transform2d.parser;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.antlr.v4.runtime.tree.TerminalNode;

import ch.kerbtier.lanthanum.Line2i;
import ch.kerbtier.lanthanum.Vec2i;
import ch.kerbtier.webb.transform2d.parser.T2dParser.CropContext;
import ch.kerbtier.webb.transform2d.parser.T2dParser.DiffMaskContext;
import ch.kerbtier.webb.transform2d.parser.T2dParser.FitContext;
import ch.kerbtier.webb.transform2d.parser.T2dParser.OperationContext;
import ch.kerbtier.webb.transform2d.parser.T2dParser.RectContext;
import ch.kerbtier.webb.transform2d.parser.T2dParser.Rect_borderContext;
import ch.kerbtier.webb.transform2d.parser.T2dParser.Rect_vec_sizeContext;
import ch.kerbtier.webb.transform2d.parser.T2dParser.Rect_vecsContext;

public class T2dExecuteVisitor extends T2dBaseVisitor<Object> {

  private Stack<BufferedImage> current = new Stack<>();
  private List<BufferedImage> sources = new ArrayList<>();
  private BufferedImage lastOperation = null;

  public T2dExecuteVisitor(List<BufferedImage> sources) {
    this.sources = new ArrayList<>();

    // make sure they are all of type INT_ARGB
    for (BufferedImage source : sources) {
      BufferedImage n = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);

      Graphics2D g2d = n.createGraphics();
      g2d.drawImage(source, 0, 0, null);
      g2d.dispose();

      this.sources.add(n);
    }
  }

  public BufferedImage getLastOperation() {
    return lastOperation;
  }

  @Override
  public Object visitOperation(OperationContext ctx) {
    int si = Integer.parseInt(ctx.VAR().toString().substring(1));

    try {
      current.push(sources.get(si - 1));
    } catch (IndexOutOfBoundsException e) {
      throw new RuntimeException("trying to use non existent source image " + ctx.VAR().toString());
    }

    super.visitOperation(ctx);

    lastOperation = current.peek();
    current.pop();

    return lastOperation;
  }

  @Override
  public Void visitCrop(CropContext ctx) {
    // fetch parameter
    Line2i rect = (Line2i) visitRect(ctx.rect());

    // create new image with new size
    Vec2i size = rect.toVector();
    BufferedImage bi = new BufferedImage(size.x(), size.y(), BufferedImage.TYPE_INT_ARGB);

    // paint old into new image, properly clipped
    Vec2i pos = rect.start().mul(-1);
    Graphics2D g = bi.createGraphics();
    g.drawImage(current.peek(), pos.x(), pos.y(), null);
    g.dispose();

    // replace current image with newly created
    current.pop();
    current.push(bi);

    return null;
  }

  @Override
  public Object visitDiffMask(DiffMaskContext ctx) {

    BufferedImage input = current.peek();
    BufferedImage base = (BufferedImage) visitOperation(ctx.operation());
    
    BufferedImage diff = new BufferedImage(base.getWidth(), base.getHeight(), BufferedImage.TYPE_INT_ARGB);

    for (int x = 0; x < input.getWidth(); x++) {
      for (int y = 0; y < input.getHeight(); y++) {
        if (input.getRGB(x, y) == base.getRGB(x, y)) {
          // pixel is the same, set destination to transparent
          diff.setRGB(x, y, 0);
        } else {
          // set dest color
          diff.setRGB(x, y, input.getRGB(x, y));
        }
      }
    }
    
    current.pop();
    current.push(diff);

    return null;
  }
  
  @Override
  public Object visitFit(FitContext ctx) {
    BufferedImage input = current.peek();
    Vec2i size = toVec(ctx.SIZE2());
    
    
    float ri = input.getWidth() / (float)input.getHeight();
    float ro = size.x() / (float)size.y();
    
    BufferedImage ni = new BufferedImage(size.x(), size.y(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = ni.createGraphics();
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    if(ri > ro) {
      int tx = (int)(size.y() * ri);
      int delta = (tx - size.x()) / 2;
      g.drawImage(input, -delta, 0, tx, size.y(), null);
    } else {
      int ty = (int)(size.x() / ri);
      int delta = (ty - size.y()) / 2;
      g.drawImage(input, 0, -delta, size.x(), ty, null);
    }
    
    g.dispose();
    
    current.pop();
    current.push(ni);
    
    
    return null;
  }

  @Override
  public Object visitRect(RectContext ctx) {
    if (ctx.rect_border() != null) {
      return visitRect_border(ctx.rect_border());
    } else if (ctx.rect_vec_size() != null) {
      return visitRect_vec_size(ctx.rect_vec_size());
    } else {
      return visitRect_vecs(ctx.rect_vecs());
    }
  }

  @Override
  public Object visitRect_border(Rect_borderContext ctx) {
    return null;
  }

  @Override
  public Object visitRect_vecs(Rect_vecsContext ctx) {
    return new Line2i(toVec(ctx.VEC2(0)), toVec(ctx.VEC2(1)));
  }

  @Override
  public Object visitRect_vec_size(Rect_vec_sizeContext ctx) {
    Vec2i sp = toVec(ctx.VEC2());
    return new Line2i(sp, sp.add(toVec(ctx.SIZE2())));
  }

  private Vec2i toVec(TerminalNode tn) {
    String[] numbers = tn.getText().split("[|/x]");
    return new Vec2i(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]));
  }

}
