package server;

import java.io.IOException;
import java.net.ServerSocket;

import server.game.Game;
import server.oddEven.OddEven;
import util.Logger;

public class Server {
  private int PORT = 8080;
  private ServerSocket serverSocket;
  private Game game;

  public void start() {
    try {
      serverSocket = new ServerSocket(PORT);
      game = OddEven.getInstance();
      handleConnections();
    } catch (IOException e) {
      Logger.error(e);
    }
  }

  private void handleConnections() {
    try {
      while (true) {
        Logger.info("Waiting connection request...");
        var clientSocket = serverSocket.accept();
        Logger.info("Connection stablished!");
        game.addClient(clientSocket);
      }
    } catch (IOException e) {
      Logger.error(e);
    }
  }
}
