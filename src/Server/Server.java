package Server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
  private int PORT = 8080;
  private ServerSocket serverSocket;

  public void start() {
    try {
      serverSocket = new ServerSocket(PORT);
      handleConnections();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void handleConnections() throws IOException {
    while (true) {
      System.out.println("Waiting Connection Request.");
      var clientSocket = serverSocket.accept();
      System.out.println("Connection Stablished.");
    }
  }
}
