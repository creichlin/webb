package ch.kerbtier.webb.embeded;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import org.eclipse.jetty.server.Server;
import org.picocontainer.DefaultPicoContainer;

import ch.kerbtier.webb.ContainerSetup;

public class ServerRunner {
  
  private String context;
  private String livecycles;
  private String address;
  private int port;
  

  // config vars

  // context
  private Server server;
  private ContainerSetup cs = new ContainerSetup();
  private DefaultPicoContainer contextContainer;
  private DefaultPicoContainer sessionContainer;

  public ServerRunner(String context, String livecycles, String address, int port) {
    this.context = context;
    this.livecycles = livecycles;
    this.address = address;
    this.port = port;
  }

  /**
   * will start up the server and wait till the server terminates.
   * @throws UnknownHostException
   */
  public void start() throws UnknownHostException {
    contextContainer = new DefaultPicoContainer();
    cs.initializedContext(contextContainer, new EmbededContextInfo(context), livecycles);

    sessionContainer = new DefaultPicoContainer(contextContainer);
    cs.createdSession(sessionContainer);

    server = new Server(new InetSocketAddress(InetAddress.getByName(address), port));
    server.setHandler(new ServerHandler(this));

    try {

      server.start();

      System.out.println(server.getConnectors()[0]);

      System.out.println("started server...");
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

  public void stop() {
    System.out.println("exiting...");
    new Thread() {
      @Override
      public void run() {
        try {
          server.stop();
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    }.start();
  }
}
