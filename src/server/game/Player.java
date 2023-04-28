package server.game;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import lombok.Getter;
import lombok.Setter;

public class Player {
  @Getter
  private Socket socket;
  @Getter
  private Scanner input;
  @Getter
  private PrintStream output;
  @Setter
  @Getter
  private int queueIndex;

  public Player(Socket socket) {
    try {
      this.socket = socket;
      // this.input = new BufferedReader(new
      // InputStreamReader(socket.getInputStream()));
      this.input = new Scanner(socket.getInputStream());
      this.output = new PrintStream(socket.getOutputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
// ? Usar Scanner para pegar inputs dos sockets