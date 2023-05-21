package server.game;

import server.connection.Connection;

public interface IQueueHandler {
  void apply(Connection[] connections);
}
