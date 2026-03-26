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

        try {
            String url = "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/usd.json";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
            usd = obj.getAsJsonObject("usd");
            // System.out.print(obj);
            exchangeRate = usd.get("thb").getAsDouble();            

        } catch (Exception e) {
            e.printStackTrace();
        }


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
                boolean choosingCurrency = true;
                while (choosingCurrency) {
                    System.out.printf("\nCurrent Currency : %s ", curBetaName);
                    System.out.print("\nEnter currency code : ");
                    scanner.nextLine();
                    String chosenCurrency = scanner.nextLine();
                    try {
                        exchangeRate = usd.get(chosenCurrency).getAsDouble();
                        curBetaName = chosenCurrency;
                        choosingCurrency = false;
                    } catch (Exception e) {
                        System.out.println("\nThis Current doesn't exist!");
                    }
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
}