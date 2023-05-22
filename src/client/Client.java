package client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import types.Protocol;
import types.oddEven.Choice;
import types.oddEven.OddEvenReq;
import types.oddEven.OddEvenRes;
import types.oddEven.OddEvenReq.Type;
import util.Logger;

public class Client {
  private int PORT = 8080;
  private Socket socket;

  public void start() {
    try {
      socket = new Socket("localhost", PORT);
      var connection = new Connection(socket);
      game(connection);
      socket.close();
    } catch (IOException e) {
      Logger.error(e);
    }
  }

  private void game(Connection connection) {
    Scanner scanner = new Scanner(System.in);
    try {
      while (true) {
        var ptl = connection.read();
        if (ptl.getType() != Protocol.MessageType.GAME)
          continue;

        var msg = (OddEvenReq) ptl.getMessage();

        if (msg.getType().equals(Type.BEGIN)) {
          var choice = choose(scanner, msg);
          var num = getNumber(scanner);
          var res = new OddEvenRes(choice, num);
          connection.send(new Protocol<>(Protocol.MessageType.GAME, res));
        } else if (msg.getType().equals(Type.RESULT))
          result(msg);
      }
    } catch (Exception e) {
      Logger.warn(e);
    }
    scanner.close();
  }

  private Choice choose(Scanner scanner, OddEvenReq msg) {
    if (!msg.getBegin().isFirst()) {
      Logger.info(
          Logger.color.bold().blue("The other player chose first: ") +
              Logger.color.bold(msg.getBegin().getFirstPlayerChoice()));
      return msg.getBegin().getFirstPlayerChoice().equals(Choice.EVEN) ? Choice.ODD : Choice.EVEN;
    }

    String inputMsg;
    do {
      Logger.info(Logger.color.bold().blue("Choose Odd or Even:"));
      inputMsg = scanner.nextLine();
    } while (!(inputMsg.equals("Odd") || inputMsg.equals("Even")));
    return inputMsg.equals("Even") ? Choice.EVEN : Choice.ODD;
  }

  private int getNumber(Scanner scanner) throws Exception {
    int num;
    do {
      Logger.info(Logger.color.bold().blue("Choose the number:"));
      try {
        num = scanner.nextInt();
      } catch (Exception e) {
        num = -2;
        Logger.debug(e);
      }
      scanner.nextLine();

      if (num == -1)
        throw new Exception("Quiting...");
    } while (num < 0);
    return num;
  }

  private void result(OddEvenReq msg) {
    var result = msg.getResult();
    var log = "";

    if (result.isWon())
      log += Logger.color.bold("You won!");
    else
      log += Logger.color.bold("You lose!");

    log += Logger.color.bold().blue("\nResult: ") +
        Logger.color.bold(result.getOddEven() + " - " + result.getSum()) +
        Logger.color.bold().blue("\nYour choose: ") +
        Logger.color.bold(result.getPlayerChoice() + " - " + result.getPlayerNumber()) +
        Logger.color.bold().blue("\nOther player choose: ") +
        Logger.color.bold(result.getOtherPlayerChoice() + " - " + result.getOtherPlayerNumber());

    Logger.info(log);
  }
}
