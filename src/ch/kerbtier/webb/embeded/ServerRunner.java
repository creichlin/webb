package ch.kerbtier.webb.embeded;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import org.eclipse.jetty.server.Server;
import org.picocontainer.DefaultPicoContainer;

import ch.kerbtier.webb.ContainerSetup;

public class ServerRunner {
  private ContainerSetup cs = new ContainerSetup();
  private DefaultPicoContainer contextContainer;
  private DefaultPicoContainer sessionContainer;
  
  public static void main(String[] args) throws UnknownHostException {
    ServerRunner sr = new ServerRunner();
  }
  
  public ServerRunner() throws UnknownHostException {
    contextContainer = new DefaultPicoContainer();
    cs.initializedContext(contextContainer, new EmbededContextInfo("default"), "ch.kerbtier.doda.DodaLivecycles");
    
    sessionContainer = new DefaultPicoContainer(contextContainer);
    cs.createdSession(sessionContainer);
    
    Server server = new Server(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 8002));
    server.setHandler(new ServerHandler(this));
    
    try {
      
      server.start();
      
      System.out.println(server.getConnectors()[0]);
      
      System.out.println("started server..." );
      server.join();
      
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

    cs.destroyedSession(sessionContainer);
    cs.destroyedContext(contextContainer);
  }

  public DefaultPicoContainer getSessionContainer() {
    return sessionContainer;
  }
  
  public ContainerSetup getContainerSetup() {
    return cs;
  }
}
