package case_study2;

import java.util.Scanner;

public class ConvertCelsiusToFahrenheit {
    public static void main(String[] args) {
        // create a scanner
        Scanner sc = new Scanner(System.in);

        // input a degree in celsius from user
        System.out.print("Enter a degree in Celsius: ");
        double celsius = sc.nextDouble();

        // convert celsius to fahrenheit
        double fahrenheit = (9.0 / 5.0) * celsius + 32;

        // display the result
        System.out.println(celsius + " Celsius is " + String.format("%.1f", fahrenheit) + " Fahrenheit");
    }
}
