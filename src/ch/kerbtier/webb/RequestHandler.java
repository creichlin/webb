package ch.kerbtier.webb;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.picocontainer.DefaultPicoContainer;

@MultipartConfig()
public class RequestHandler extends HttpServlet {

  private static final long serialVersionUID = 1L;
  
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    
    DefaultPicoContainer requestContainer = (DefaultPicoContainer)req.getAttribute("requestContainer");
    requestContainer.getComponent(Livecycles.class).request(requestContainer);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    DefaultPicoContainer requestContainer = (DefaultPicoContainer)req.getAttribute("requestContainer");
    requestContainer.getComponent(Livecycles.class).request(requestContainer);
  }

  
}
