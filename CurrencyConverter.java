import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CurrencyConverter {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String curAlphaName = "usd";
        String curBetaName = "thb";
        double exchangeRate = getRate(curAlphaName, curBetaName);

        boolean run = true;
        while (run) {
            System.out.print("\n(0) Exit\n(1) View Settings\n(2) Convert\n(3) Change 1st Currency\n(4) Change 2nd Currency\n Choose Option : ");
            int option = scanner.nextInt();
            System.out.print("");

            if (option == 1){
                System.out.printf("\nInput Currency : %s\nOutput Currency : %s\nExchange Rate : %.2f\n", curAlphaName, curBetaName, exchangeRate);
            }
            else if (option == 2){
                System.out.printf("\nEnter your amount in %s : ", curAlphaName);
                double amount = scanner.nextDouble();
                System.out.print("");
                double newAmount = amount * exchangeRate;
                System.out.printf("\n%.2f %s is equivalent to %.2f %s\n", amount, curAlphaName, newAmount, curBetaName);
            }
            else if (option == 3 || option == 4){
                
                switch (option) {
                    case 3 -> System.out.printf("\nCurrent Currency : %s ", curAlphaName);
                    case 4 -> System.out.printf("\nCurrent Currency : %s ", curBetaName);
                }

                System.out.print("\nEnter currency code (or S to continue current one) : ");
                scanner.nextLine();
                String userCurrency = scanner.nextLine().toLowerCase();

                double newExchangeRate = getRate(userCurrency, curBetaName);

                while (newExchangeRate == -1.0 && !(userCurrency.charAt(0) == 's' && userCurrency.length() == 1)) {
                    System.out.println("Invalid currency. Choose another one!");
                    System.out.print("\nEnter currency code (or S to continue current one) : ");

                    userCurrency = scanner.nextLine().toLowerCase();
                    switch (option) {
                        case 3 -> newExchangeRate = getRate(userCurrency, curBetaName);
                        case 4 -> newExchangeRate = getRate(curAlphaName, userCurrency);
                    }
                    
                }

                if (newExchangeRate != -1.0) {
                    switch (option) {
                        case 3 -> curAlphaName = userCurrency;
                        case 4 -> curBetaName = userCurrency;
                    }
                    exchangeRate = newExchangeRate;
                }
            }
            else if (option == 0){
                run = false;
            }
            else {
                System.out.println("Invalid Option\n");
            }
        }

        scanner.close();
    }


    
    static double getRate(String cur1, String cur2) {

        try {
            String url = "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/" + cur1 + ".json";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
            JsonObject cur = obj.getAsJsonObject(cur1);
            System.out.print(cur);
            // System.out.print(obj);
            Double exchangeRate = cur.get(cur2).getAsDouble();

            return exchangeRate;

        } catch(Exception e) {
            return -1;
        }
            
    }

    static String getCurrencyInput(String current) {
        Scanner currencyInput = new Scanner(System.in);

        String userCurrency = currencyInput.nextLine();
        currencyInput.close();
        return userCurrency;

        
    }

}