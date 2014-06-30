package ch.kerbtier.webb.embeded;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.picocontainer.DefaultPicoContainer;

import ch.kerbtier.webb.Livecycles;

public class ServerHandler extends AbstractHandler {

  private ServerRunner serverRunner;

  public ServerHandler(ServerRunner serverRunner) {
    this.serverRunner = serverRunner;
  }

  @Override
  public void handle(String arg0, Request request, HttpServletRequest req,
      HttpServletResponse resp) throws IOException, ServletException {

    String p = req.getPathInfo();
    if (p.matches(".*\\.(png|js|css)")) {
      ResourceHandler rs = new ResourceHandler();
      
      rs.setResourceBase("public");
      rs.handle(arg0, request, req, resp);
    } else {

      DefaultPicoContainer requestContainer = new DefaultPicoContainer(
          serverRunner.getSessionContainer());

      serverRunner.getContainerSetup().createdRequest(requestContainer, req,
          resp);

      try {
        requestContainer.getComponent(Livecycles.class).request(
            requestContainer);
      } catch (Exception e) {
        e.printStackTrace();
      }

      request.setHandled(true);

      serverRunner.getContainerSetup().destroyRequest(requestContainer);
    }
  }

}
