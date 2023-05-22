package util;

import java.util.function.Consumer;

// * https://www.lihaoyi.com/post/BuildyourownCommandLinewithANSIescapecodes.html
public class Logger {
  private static LogLevel level = LogLevel.INFO;

  public static Color color = new Color();

  private static enum LogLevel {
    ERROR(1), WARN(2), INFO(3), DEBUG(4);

    private int value;

    LogLevel(int value) {
      this.value = value;
    }
  }

  public static void error(Object msg) {
    var threadName = Thread.currentThread().getName();
    errorLog.accept(color.bold(color.green(threadName) + " - [" + color.red("ERROR") + "] ") + msg);
  }

  public static void warn(Object msg) {
    var threadName = Thread.currentThread().getName();
    warnLog.accept(color.bold(color.green(threadName) + " - [" + color.yellow("WARN") + "] ") + msg);
  }

  public static void info(Object msg) {
    var threadName = Thread.currentThread().getName();
    infoLog.accept(color.bold(color.green(threadName) + " - [" + color.blue("INFO") + "] ") + msg);
  }

  public static void debug(Object msg) {
    var threadName = Thread.currentThread().getName();
    debugLog.accept(color.bold(color.green(threadName) + " - [" + color.cyan("DEBUG") + "] ") + msg);
  }

  private static Consumer<Object> errorLog = level.value >= LogLevel.ERROR.value ? System.out::println : msg -> {
  };
  private static Consumer<Object> warnLog = level.value >= LogLevel.WARN.value ? System.out::println : msg -> {
  };
  private static Consumer<Object> infoLog = level.value >= LogLevel.INFO.value ? System.out::println : msg -> {
  };
  private static Consumer<Object> debugLog = level.value >= LogLevel.DEBUG.value ? System.out::println : msg -> {
  };
}
