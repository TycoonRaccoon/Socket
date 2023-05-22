package server;

public class Main {
  public static void main(String[] args) {
    var server = new Server();

    server.start();

    // TODO: Create shutdown hook
    // https://stackoverflow.com/questions/750026/how-to-handle-a-closing-application-event-in-java
    // Runtime.getRuntime().addShutdownHook(null);
  }
}
