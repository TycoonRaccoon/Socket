package server.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketAddress;

import lombok.Getter;
import lombok.Setter;
import types.Protocol;
import util.Logger;

/*
! The connection messages needs to be read at only one place
* Bc other wise the stream will be locked at one thread until it releases
? create events to handle income messages?
? create other socket connection to handle haertbeats
*/

public class Connection {
  private Socket socket;
  @Getter
  private SocketAddress address;
  private ObjectInputStream input;
  private ObjectOutputStream output;

  private boolean heartbeatCreated = false;
  @Getter
  @Setter
  private boolean isOpen = true;
  @Setter
  private volatile IDisconnectHandler onDisconnect;

  public Connection(Socket socket) {
    try {
      this.socket = socket;
      this.address = socket.getRemoteSocketAddress();
      this.output = new ObjectOutputStream(socket.getOutputStream());
      output.flush();
      this.input = new ObjectInputStream(socket.getInputStream());
    } catch (IOException e) {
      Logger.error(e);
    }
  }

  public void send(Protocol<?> data) throws IOException {
    synchronized (output) {
      output.writeObject(data);
      output.reset();
    }
  }

  public Protocol<?> read() throws IOException {
    synchronized (input) {
      try {
        return (Protocol<?>) input.readObject();
      } catch (ClassNotFoundException e) {
        Logger.error(e);
        return null;
      }
    }
  }

  public void close() {
    try {
      socket.close();
    } catch (IOException e) {
      Logger.error(e);
    }
  }

  public void createHeartbeat() {
    if (heartbeatCreated)
      return;
    new Heartbeat(this).start();
  }

  public void onDisconnect() {
    this.onDisconnect.apply();
  }
}
