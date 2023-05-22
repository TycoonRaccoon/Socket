package server.game;

import java.net.Socket;

import lombok.Getter;
import server.connection.Connection;
import util.Logger;

public abstract class Game {
  protected IWorkerFactory workerFactory;
  protected Queue queue;
  @Getter
  protected int playerAmount;

  public Game(int playerAmount, IWorkerFactory workerFactory) {
    this.playerAmount = playerAmount;
    this.workerFactory = workerFactory;
    this.queue = new Queue(playerAmount, start);
  }

  private IQueueHandler start = (Connection[] players) -> {
    Logger.info("Creating game...");
    var worker = workerFactory.create(players);
    Logger.info("Game created!");
    Logger.info("Starting game...");
    worker.start();
  };

  public void addClient(Socket socket) {
    var connection = new Connection(socket);
    connection.createHeartbeat();
    queue.add(connection);
  }
}
