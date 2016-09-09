package me.zzw.app.jdk8.generics.genMethods;

/**
 * Created by infosea on 2016-07-28.
 */
class Shape {

}

class Circle extends Shape {

}

class Rectangle extends Shape {

}

public class App {
    public static <T extends Shape> void draw(T shape) {
        System.out.println(shape);
    }
    public static void main(String... args) {
        Circle circle = new Circle();
       draw(circle);
    }
}
