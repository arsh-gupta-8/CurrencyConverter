import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CurrencyConverter {
    public static void main(String[] args) {

        JsonObject usd = null;
        double exchangeRate = 32.109324;

        Scanner scanner = new Scanner(System.in);

        String curAlphaName = "usd";
        String curBetaName = "thb";

        boolean run = true;
        while (run) {
            System.out.print("\n(0) Exit\n(1) View Settings\n(2) Convert\n(3) Change 2nd Currency\n Choose Option : ");
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
            else if (option == 3){
                
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

    // static void main(String curExchangeCode) {
        
    //     Scanner scanner = new Scanner(System.in);
    //     String newExchangeRate;
    //     String currencyCode;

    //     do {
    //         System.out.printf("\nCurrent Currency : %s ", curExchangeCode);
    //         System.out.print("\nEnter currency code : ");
    //         scanner.nextLine();
    //         String chosenCurrency = scanner.nextLine();
    //         try {
    //             newExchangeRate = usd.get(chosenCurrency).getAsDouble();
    //             curBetaName = chosenCurrency;
    //         } catch (Exception e) {
    //             System.out.println("\nThis Current doesn't exist!");
    //         }
    //     } while (choosingCurrency);
    //     scanner.close();
    
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
            // System.out.print(obj);
            Double exchangeRate = cur.get(cur2).getAsDouble();

            return exchangeRate;

        } catch(Exception e) {

            return -1;

        }
            
        
    }

}