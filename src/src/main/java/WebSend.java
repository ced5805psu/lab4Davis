import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class WebSend {
    public static void main(String[] args) {
        Pizza pizza = new Pizza("Provolone", "9", "pepperoni", "10.00");
        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(new Pizza("American", "5 in", "Onions","$5.00"));
        pizzas.add(new Pizza("Cheddar", "9 in", "Peppers","$9.00"));

        try (PrintWriter writer = new PrintWriter(new FileWriter("pizzas.txt"))) {
            for (Pizza pizza1 : pizzas) {
                writer.println(pizza.toFixedFormatString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // Specify the URL of the web service
            String url = "http://localhost:8000/hello";

            // Create a URL object
            URL obj = new URL(url);

            // Open a connection to the URL
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // Set the request method
            con.setRequestMethod("GET");

            // Set request headers if needed
            // con.setRequestProperty("Content-Type", "application/json");

            // Get the response code
            int responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            // Read the response from the web service
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            ObjectMapper objectMapper = new ObjectMapper();
            String pizzaJson = objectMapper.writeValueAsString(pizza);
            System.out.println("Pizza object serialized to JSON string:");
            System.out.println(pizzaJson);

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();


            // Print the response
            System.out.println("Response: " + response.toString());

            // Parse the JSON response as needed

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
