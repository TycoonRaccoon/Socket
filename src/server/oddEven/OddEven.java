package server.oddEven;

import server.game.Game;

public class OddEven extends Game {
  private static final int PLAYER_AMOUNT = 2;
  private static OddEven instance;

  private OddEven() {
    super(PLAYER_AMOUNT, OddEvenWorker::new);
  }

  public static OddEven getInstance() {
    if (instance == null)
      instance = new OddEven();
    return instance;
  }
}
