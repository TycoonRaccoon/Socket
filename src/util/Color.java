package util;

// * https://www.lihaoyi.com/post/BuildyourownCommandLinewithANSIescapecodes.html
public class Color {
  private int[] color = { 0, 0 };
  private int[] modifier = { 0, 0 };
  private boolean isBold = false;
  private boolean isBright = false;
  private boolean isUnderline = false;
  private boolean isBackground = false;

  public Color black() {
    color[0] = 30;
    color[1] = 39;
    return this;
  }

  public Color red() {
    color[0] = 31;
    color[1] = 39;
    return this;
  }

  public Color green() {
    color[0] = 32;
    color[1] = 39;
    return this;
  }

  public Color yellow() {
    color[0] = 33;
    color[1] = 39;
    return this;
  }

  public Color blue() {
    color[0] = 34;
    color[1] = 39;
    return this;
  }

  public Color purple() {
    color[0] = 35;
    color[1] = 39;
    return this;
  }

  public Color cyan() {
    color[0] = 36;
    color[1] = 39;
    return this;
  }

  public Color white() {
    color[0] = 37;
    color[1] = 39;
    return this;
  }

  public Color bold() {
    modifier[0] = 1;
    modifier[1] = 22;
    isBold = true;
    isBackground = false;
    return this;
  }

  public Color bright() {
    modifier[0] = 1;
    modifier[1] = 22;
    isBright = true;
    return this;
  }

  public Color underline() {
    modifier[0] = 4;
    modifier[1] = 24;
    isUnderline = true;
    isBackground = false;
    return this;
  }

  public Color background() {
    isBackground = true;
    return this;
  }

  public String black(Object msg) {
    black();
    return buildColor(msg);
  }

  public String red(Object msg) {
    red();
    return buildColor(msg);
  }

  public String green(Object msg) {
    green();
    return buildColor(msg);
  }

  public String yellow(Object msg) {
    yellow();
    return buildColor(msg);
  }

  public String blue(Object msg) {
    blue();
    return buildColor(msg);
  }

  public String purple(Object msg) {
    purple();
    return buildColor(msg);
  }

  public String cyan(Object msg) {
    cyan();
    return buildColor(msg);
  }

  public String white(Object msg) {
    white();
    return buildColor(msg);
  }

  public String bold(Object msg) {
    bold();
    return buildColor(msg);
  }

  public String bright(Object msg) {
    bright();
    return buildColor(msg);
  }

  public String underline(Object msg) {
    underline();
    return buildColor(msg);
  }

  public String background(Object msg) {
    background();
    return buildColor(msg);
  }

  private String buildColor(Object msg) {
    if (isBright) {
      color[0] += 60;
      color[1] += 60;
    }
    if (isBackground) {
      color[0] += 10;
      color[1] += 10;
    }

    var hasModifier = modifier[0] != 0;
    var hasColor = color[0] != 0;
    var separator = hasModifier && hasColor ? ";" : "";

    var modifierOpen = hasModifier && isBackground && !isBright ? "" : modifier[0];
    var modifierClose = hasModifier && isBackground && !isBright ? "" : modifier[1];

    var colorOpen = hasColor ? color[0] : "";
    var colorClose = hasColor ? color[1] : "";

    var open = "\033[" + modifierOpen + separator + colorOpen + "m";
    var close = "\033[" + modifierClose + separator + colorClose + "m";

    var str = open + msg + close;

    reset();
    return str;
  }

  private void reset() {
    color[0] = 0;
    color[1] = 9;
    modifier[0] = 0;
    modifier[1] = 0;
    isBold = false;
    isBright = false;
    isUnderline = false;
    isBackground = false;
  }
}
