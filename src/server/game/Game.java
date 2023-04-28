package server.game;

import java.net.Socket;

import lombok.Getter;

public abstract class Game {
  protected WorkerFactory workerFactory;
  protected Queue queue;
  @Getter
  protected int playerAmount;

  public Game(int playerAmount, WorkerFactory workerFactory) {
    this.playerAmount = playerAmount;
    this.workerFactory = workerFactory;
    this.queue = new Queue(playerAmount, this::start);
  }

  public void start(Player[] players) {
    System.out.println("Creating game...");
    var worker = workerFactory.create(players);
    System.out.println("Game created!");
    System.out.println("Starting game...");
    worker.start();
  }

  public void addClient(Socket socket) {
    var player = new Player(socket);
    queue.add(player);
  }
}
