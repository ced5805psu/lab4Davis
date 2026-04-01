/** Project: Solo Lab4 Assignment: Systems Integration Pizza Shop: File Files, RabbitMQ, and Web Service/JSON
 * Purpose Details: To teach about Systems Integration
 * Course: IST 242 Section 001
 * Author: Conner Davis
 * Date Developed: 3/31/2026
 * Last Date Changed: 3/31/2026
 * Rev:

 */

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;



public class WebReceive {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/hello", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();

        // Deserialization: Converting the JSON string back to a student object
        Pizza pizza = new Pizza("Provolone", "9", "pepperoni", "10.00");
        ObjectMapper objectMapper = new ObjectMapper();
        String pizzaJson = objectMapper.writeValueAsString(pizza);
        Pizza deserializedPizza = objectMapper.readValue(pizzaJson, Pizza.class);
        System.out.println("\nStudent object deserialized from JSON string:");
        System.out.println("Cheese: " + deserializedPizza.getPizzaCheese());
        System.out.println("Size: " + deserializedPizza.getPizzaSize());
        System.out.println("Toppings: " + deserializedPizza.getPizzaToppings());
        System.out.println("Price: " + deserializedPizza.getPizzaPrice());
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            Pizza pizza = new Pizza("Provolone", "9", "pepperoni", "10.00");
            ObjectMapper objectMapper = new ObjectMapper();
            String pizzaJson = objectMapper.writeValueAsString(pizza);
            String response = pizzaJson;

            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
