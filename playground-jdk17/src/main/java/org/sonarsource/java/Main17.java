package org.sonarsource.java;

public class Main17 {

  /**
   * JEP-406 (1st preview) Pattern Matching for switch
   */
  static Object switchDefaultCase(Object o) {
    return switch (o) {
      case default -> o;
    };
  }

  static int switchArrayNullPattern(Object o) {
    return switch (o) {
      case Object[] arr -> arr.length;
      // default case voluntarily not in last position
      default -> -1;
      case null -> 42;
    };
  }

  static int switchArrayDefaultNullPattern(Object o) {
    return switch (o) {
      case Object[] arr -> arr.length;
      case default, null -> 42;
    };
  }

  static String switchSealedClassMinimum(Shape shape) {
    return switch (shape) {
      case Triangle t -> "triangle " + t;
      case Rectangle r -> "rectangle" + r;
    };
  }

  static String switchSealedClassNullDefaultSubClasses(Shape shape) {
    return switch (shape) {
      case null -> "null case";
      case Triangle t -> String.format("triangle (%d,%d,%d)", t.a(), t.b(), t.c());
      case Rectangle r && r.volume() > 42 -> String.format("big rectangle of volume %d!", r.volume());
      case Square s -> String.format("Square %s!", s);
      case Rectangle r -> String.format("Rectangle (%d,%d)", r.base, r.height);
      default -> "default case";
    };
  }

  /**
   * JEP-409 (final) sealed classes
   */
  public sealed interface Shape permits Rectangle,Triangle {
    default int volume() { return 0; }
  }

  public static non-sealed class Rectangle implements Shape {
    protected int base;
    protected int height;
    Rectangle(int base, int height) { this.base = base; this.height = height; }
    @Override
    public String toString() { return String.format("%dx%d", base, height); }
  }

  public static final class Square extends Rectangle {
    Square(int side) { super(side, side); }
  }

  public static record Triangle(int a, int b, int c) implements Shape {}

}
