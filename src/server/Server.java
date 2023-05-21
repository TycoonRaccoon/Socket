package server;

import java.io.IOException;
import java.net.ServerSocket;

import server.game.Game;
import server.oddOrEven.OddOrEven;
import util.Logger;

public class Server {
  private int PORT = 8080;
  private ServerSocket serverSocket;
  private Game game;

  public void start() {
    try {
      serverSocket = new ServerSocket(PORT);
      game = OddOrEven.getInstance();
      if (game == null)
        Logger.warn("Game not setted!");
      handleConnections();
    } catch (IOException e) {
      e.printStackTrace();
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
      e.printStackTrace();
    }
  }
}
