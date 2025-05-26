package case_study4;

public class DisplayingPrimeNumbers {
    public static void main(String[] args) {
        final int NUMBER_OF_PRIMES = 50; // number of primes to display
        final int NUMBER_OF_PRIMES_PER_LINE = 10; // display 10 per lines
        int count = 0; // count the number of prime numbers
        int number = 2; // a number to be tested for primeness

        System.out.println("The first 50 prime numbers are \n");

        // repeatedly find prime numbers
        while (count < NUMBER_OF_PRIMES) {
            boolean isPrime = true;
            for (int divisor = 2; divisor <= number / 2; divisor++) {
                if (number % divisor == 0) {
                    isPrime = false;
                    break;
                }
            }

            // display the prime number and increase the count
            if (isPrime) {
                count++;
                if (count % NUMBER_OF_PRIMES_PER_LINE == 0) {
                    System.out.println(number);
                } else {
                    System.out.print(number + " ");
                }
            }
            // check if the number is prime
            number++;
        }
    }
}
