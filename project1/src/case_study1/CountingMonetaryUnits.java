package case_study1;

import java.util.Scanner;

public class CountingMonetaryUnits {
    public static void main(String[] args) {
        // create a new scanner
        Scanner sc = new Scanner(System.in);

        // receive the amount
        System.out.print("Enter an amount in double, for example 11.56: ");
        double amount = sc.nextDouble();

        int remainingAmount = (int) (amount * 100);

        // find the number of one dollars
        int numberOfOneDollars = remainingAmount / 100;
        remainingAmount = remainingAmount % 100;

        // find the number of quarters in the remaining amount
        int numberOfQuarters = remainingAmount / 25;
        remainingAmount = remainingAmount % 25;

        // find the number of dimes in the remaining amount
        int numberOfDimes =  remainingAmount / 10;
        remainingAmount =  remainingAmount % 10;

        // find the number of nickles in the remaining amount
        int numberOfNickles =  remainingAmount / 5;
        remainingAmount = remainingAmount % 5;

        // find the number of pennies in the remaining amount
        int numberOfPennies = remainingAmount;

        // display results
        System.out.println("Your amount " + amount + " consists of");
        System.out.println(" " + numberOfOneDollars + " dollars");
        System.out.println(" " + numberOfQuarters + " quarters");
        System.out.println(" " + numberOfDimes + " dimes");
        System.out.println(" " + numberOfNickles + " nickles");
        System.out.println(" " + numberOfPennies + " pennies");
    }
}
