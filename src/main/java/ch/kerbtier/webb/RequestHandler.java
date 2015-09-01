package ch.kerbtier.webb;

import java.io.IOException;

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

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doIt(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doIt(req, resp);
  }

  private void doIt(HttpServletRequest req, HttpServletResponse resp) {
    ContextListener.containerSetup.createdRequest((HttpServletRequest) req, (HttpServletResponse) resp);

    Esdi.get(Livecycles.class, InjectSingleton.class).request();

    ContextListener.containerSetup.destroyRequest();
  }
}
