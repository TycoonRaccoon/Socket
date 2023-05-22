package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import types.Protocol;
import util.Logger;

public class Connection {
  private Socket socket;
  private ObjectInputStream input;
  private ObjectOutputStream output;

  public Connection(Socket socket) {
    try {
      this.socket = socket;
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
}
