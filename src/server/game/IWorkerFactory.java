package server.game;

import server.connection.Connection;

public interface IWorkerFactory {
  Worker create(Connection[] players);
}
