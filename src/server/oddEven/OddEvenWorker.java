package server.oddEven;

import java.io.IOException;
import java.util.Random;

import server.connection.Connection;
import server.game.Worker;
import types.Protocol;
import types.oddEven.Choice;
import types.oddEven.OddEvenReq;
import types.oddEven.OddEvenRes;
import types.oddEven.OddEvenReq.Begin;
import types.oddEven.OddEvenReq.Result;
import types.oddEven.OddEvenReq.Type;
import util.Logger;

public class OddEvenWorker extends Worker {
  public OddEvenWorker(Connection[] connections) {
    super(connections);
  }

  @Override
  @SuppressWarnings("unchecked")
  public void run() {
    Logger.info("Game started!");
    var gerador = new Random();
    try {
      while (isRunning) {
        var rand = gerador.nextInt(2);

        var player1 = connections[rand];
        var player2 = connections[1 - rand];

        var req1 = new OddEvenReq(Type.BEGIN, new Begin(true, null), null);
        player1.send(new Protocol<>(Protocol.MessageType.GAME, req1));
        var res1 = ((Protocol<OddEvenRes>) player1.read()).getMessage();

        var req2 = new OddEvenReq(Type.BEGIN, new Begin(false, res1.getChoice()), null);
        player2.send(new Protocol<>(Protocol.MessageType.GAME, req2));
        var res2 = ((Protocol<OddEvenRes>) player2.read()).getMessage();

        Logger.info(
            Logger.color.bold().green("Player 1: ") +
                "Choice - " + res1.getChoice() +
                " / Number - " + res1.getNumber());
        Logger.info(
            Logger.color.bold().green("Player 2: ") +
                "Choice - " + res2.getChoice() +
                " / Number - " + res2.getNumber());

        var sum = res1.getNumber() + res2.getNumber();
        var result = isEven(sum) ? Choice.EVEN : Choice.ODD;
        var p1Msg = new OddEvenReq(Type.RESULT, null,
            new Result(true, sum, result, res1.getChoice(), res1.getNumber(), res2.getChoice(), res2.getNumber()));
        var p2Msg = new OddEvenReq(Type.RESULT, null,
            new Result(true, sum, result, res2.getChoice(), res2.getNumber(), res1.getChoice(), res1.getNumber()));
        if (res1.getChoice().equals(result)) {
          p1Msg.getResult().setWon(true);
          p2Msg.getResult().setWon(false);
          player1.send(new Protocol<>(Protocol.MessageType.GAME, p1Msg));
          player2.send(new Protocol<>(Protocol.MessageType.GAME, p2Msg));
        } else {
          p1Msg.getResult().setWon(false);
          p2Msg.getResult().setWon(true);
          player2.send(new Protocol<>(Protocol.MessageType.GAME, p1Msg));
          player1.send(new Protocol<>(Protocol.MessageType.GAME, p2Msg));
        }
      }
    } catch (IOException e) {
      Logger.warn(e);
      Logger.info("Lost connection!");
    }
    Logger.info("Closing game...");
    Logger.info("Game closed!");
  }

  private boolean isEven(int num) {
    return num % 2 == 0;
  }
}
