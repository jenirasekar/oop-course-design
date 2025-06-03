package problem2;

public abstract class Shape {
    public abstract double calculateArea();

    public static void main(String[] args) {
        Shape rectangle = new Rectangle(5, 3);
        System.out.println("Rectangle area: " + rectangle.calculateArea());

        Shape circle = new Circle(9);
        System.out.println("Circle area: " + String.format("%.2f", circle.calculateArea()));
    }
}
