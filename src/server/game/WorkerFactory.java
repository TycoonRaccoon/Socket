package server.game;

public interface WorkerFactory {
  public class Worker extends Thread {
    protected Player[] players;

    public Worker(Player[] players) {
      this.players = players;
    }
  }

  Worker create(Player[] players);
}
