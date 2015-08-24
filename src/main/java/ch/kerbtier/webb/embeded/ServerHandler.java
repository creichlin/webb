package ch.kerbtier.webb.embeded;

import java.io.IOException;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.picocontainer.DefaultPicoContainer;

import ch.kerbtier.webb.Livecycles;

public class ServerHandler extends AbstractHandler {
  private static final MultipartConfigElement MULTI_PART_CONFIG = new MultipartConfigElement(System.getProperty("java.io.tmpdir"));
  private ServerRunner serverRunner;

  public ServerHandler(ServerRunner serverRunner) {
    this.serverRunner = serverRunner;
  }

  @Override
  public void handle(String arg0, Request request, HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    
    
    if (req.getContentType() != null && request.getContentType().startsWith("multipart/form-data")) {
      request.setAttribute(Request.__MULTIPART_CONFIG_ELEMENT, MULTI_PART_CONFIG);
    }
    

    DefaultPicoContainer requestContainer = new DefaultPicoContainer(serverRunner.getSessionContainer());

    serverRunner.getContainerSetup().createdRequest(requestContainer, req, resp);

    try {
      requestContainer.getComponent(Livecycles.class).request(requestContainer);
    } catch (Exception e) {
      e.printStackTrace();
    }

    request.setHandled(true);

    serverRunner.getContainerSetup().destroyRequest(requestContainer);
  }

}
