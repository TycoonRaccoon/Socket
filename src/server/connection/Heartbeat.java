package server.connection;

import java.io.IOException;

import types.Protocol;
import util.Logger;

public class Heartbeat extends Thread {
  private Connection connection;
  private static final long INTERVAL = 10 * 100; // 1 second

  public Heartbeat(Connection connection) {
    this.setName("Heartbeat");
    this.connection = connection;
  }

  @Override
  public void run() {
    Logger.info(Logger.color.bold().purple("Heartbeat: ") + "created!");

    try {
      while (!this.isInterrupted()) {
        var msg = new Protocol<String>(Protocol.MessageType.HEARTBEAT, "ping");
        connection.send(msg);
        Logger
            .debug(Logger.color.white(Logger.color.bold().purple("Heartbeat: ") + connection.getAddress() + " - ping"));
        Thread.sleep(INTERVAL);
      }
    } catch (IOException | InterruptedException e) {
      Logger.warn(e);
      connection.close();
    }

    connection.setOpen(false);
    connection.onDisconnect();
    Logger.info(Logger.color.bold().purple("Heartbeat: ") + "deleted!");
  }
}
