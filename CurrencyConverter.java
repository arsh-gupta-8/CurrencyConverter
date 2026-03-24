import java.util.Scanner;

public class CurrencyConverter {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String curAlphaName = "USD";
        String curBetaName = "THB";
        double curAlphaStr = 1.0;
        double curBetaStr = 0.031;

        boolean run = true;
        while (run) {
            System.out.print("\n(1) View Settings\n(2) Convert\n(3) Exit\n Choose Option : ");
            int option = scanner.nextInt();
            System.out.print("");

            if (option == 1){
                System.out.printf("\nInput Currency : %s\nOutput Currency : %s\nRate : %.2f\n", curAlphaName, curBetaName, (curAlphaStr/curBetaStr));
            }
            else if (option == 2){
                System.out.print("\nEnter your desired amount : ");
                double amount = scanner.nextDouble();
                System.out.print("");
                double newAmount = amount / curBetaStr;
                System.out.printf("\n%.2f %s is equivalent to %.2f %s\n", amount, curAlphaName, newAmount, curBetaName);
            }
            else if (option == 3){
                run = false;
            }
            else {
                System.out.println("Invalid Option\n");
            }
        }

        scanner.close();
    }
}