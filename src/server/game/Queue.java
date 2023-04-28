package server.game;

public class Queue {
  private int size;
  private int index = 0;
  private Player[] players;
  private QueueHandler handler;

  public Queue(int size, QueueHandler handler) {
    this.size = size;
    this.players = new Player[size];
    this.handler = handler;
  }

  public void add(Player player) {
    player.setQueueIndex(index);
    players[index++] = player;
    System.out.println("Socket added to queue!");
    if (index == size) {
      index = 0;
      handler.handle(players);
    }
  }

  public void remove(Player player) {
    var lastPlayer = players[--index];
    players[index] = null;

    var removedPlayerindex = player.getQueueIndex();
    players[removedPlayerindex] = lastPlayer;
    lastPlayer.setQueueIndex(removedPlayerindex);

    System.out.println("Socket removed from queue!");
  }
}