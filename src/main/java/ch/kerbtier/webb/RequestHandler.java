package ch.kerbtier.webb;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.kerbtier.esdi.Esdi;
import ch.kerbtier.webb.di.InjectSingleton;

@MultipartConfig()
public class RequestHandler extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private Logger logger = Logger.getLogger(RequestHandler.class.getName());

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doIt(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doIt(req, resp);
  }

  private void doIt(HttpServletRequest req, HttpServletResponse resp) {
    try {
      ContextListener.containerSetup.createdRequest((HttpServletRequest) req, (HttpServletResponse) resp);

      Esdi.get(Livecycles.class, InjectSingleton.class).request();

      ContextListener.containerSetup.destroyRequest();
    } catch (Exception e) {
      logger.log(Level.WARNING, "Error during request", e);
      try {
        resp.sendError(500);
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }
  }
}
