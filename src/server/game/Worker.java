package server.game;

import java.util.stream.Stream;

import lombok.Getter;
import lombok.Setter;
import server.connection.Connection;
import util.Logger;

public abstract class Worker extends Thread {
  @Getter
  @Setter
  protected volatile boolean isRunning = true;
  protected Connection[] connections;

  public Worker(Connection[] connections) {
    this.setName("Worker");
    this.connections = connections;
    setOnDisconnect();
  }

  private void setOnDisconnect() {
    Stream.of(connections).forEach(c -> c.setOnDisconnect(() -> {
      Logger.debug("Interrupting worker thread");
      isRunning = false;
      closeAllSockets();
    }));
  }

  private void closeAllSockets() {
    Stream.of(connections).forEach(c -> c.close());
  }
}
