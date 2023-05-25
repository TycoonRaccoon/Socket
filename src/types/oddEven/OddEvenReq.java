package types.oddEven;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OddEvenReq implements Serializable {
  public enum Type {
    BEGIN, RESULT
  }

  @Data
  @AllArgsConstructor
  public static class Begin implements Serializable {
    private boolean isFirst;
    private Choice firstPlayerChoice;
  }

  @Data
  @AllArgsConstructor
  public static class Result implements Serializable {
    private boolean won;
    private int sum;
    private Choice oddEven;
    private Choice playerChoice;
    private int playerNumber;
    private Choice otherPlayerChoice;
    private int otherPlayerNumber;
  }

  private Type type;
  private Begin begin;
  private Result result;
}
