package server.game;

import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import server.connection.Connection;
import util.Logger;

public class Queue {
  private int size;
  private int index = 0;
  private Item[] items;
  private IQueueHandler handler;

  @Data
  @AllArgsConstructor
  private class Item {
    private Connection connection;
    private int queueIndex;
  }

  public Queue(int size, IQueueHandler handler) {
    this.size = size;
    this.items = new Item[size];
    this.handler = handler;
  }

  public void add(Connection connection) {
    var item = new Item(connection, index);
    items[index++] = item;
    Logger.info(Logger.color.bold().yellow("Queue: ") + "Socket " + connection.getAddress() + " added to queue!");
    Logger.info(Logger.color.bold().yellow("Queue: ") + "Sockets in queue: " + index);

    connection.setOnDisconnect(() -> remove(item));

    if (index == size) {
      index = 0;
      handler.apply(getConnections());
    }
  }

  public void remove(Item item) {
    var lastItem = items[--index];
    items[index] = null;

    var removedIndex = item.getQueueIndex();
    items[removedIndex] = lastItem;
    lastItem.setQueueIndex(removedIndex);

    Logger.info(
        Logger.color.bold().yellow("Queue: ") + "Socket " + item.getConnection().getAddress() + " removed from queue!");
    Logger.info(Logger.color.bold().yellow("Queue: ") + "Sockets in queue: " + index);
  }

  private Connection[] getConnections() {
    return Stream.of(items).map(Item::getConnection).toArray(Connection[]::new);
  }
}