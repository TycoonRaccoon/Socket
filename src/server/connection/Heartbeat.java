package server.connection;

import java.io.IOException;

import types.Protocol;
import util.Logger;

public class Heartbeat extends Thread {
  private Connection connection;
  private static final long INTERVAL = 10 * 1000; // 10 seconds

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
        Logger.info(Logger.color.white()
            .underline(Logger.color.bold().purple("Heartbeat: ") + connection.getAddress() + " - ping"));
        Thread.sleep(INTERVAL);
      }
    } catch (IOException | InterruptedException e) {
      Logger.error(e);
      connection.close();
    }

    connection.setOpen(false);
    connection.onDisconnect();
    Logger.info(Logger.color.bold().purple("Heartbeat: ") + "deleted!");
  }
}
