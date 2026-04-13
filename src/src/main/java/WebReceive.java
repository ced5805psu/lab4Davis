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
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Serialization
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Pizza pizza = new Pizza("Provolone", "9 in", "pepperoni", "$10.00");
                List<Pizza> pizzas = new ArrayList<>();
                pizzas.add(pizza);
                pizzas.add(new Pizza("American", "5 in", "Onions", "$5.00"));
                pizzas.add(new Pizza("Cheddar", "9 in", "Peppers", "$9.00"));

                String pizzaJson = objectMapper.writeValueAsString(pizza);
                System.out.println("Pizza objects serialized to JSON string:");
                System.out.println(pizzaJson);

                // Deserialization: Converting the JSON string back to a student object
                Pizza deserializedPizza = objectMapper.readValue(pizzaJson, Pizza.class);
                //System.out.println("\nPizza object deserialized from JSON string:");
                //System.out.println("Cheese: " + deserializedPizza.getPizzaCheese());
                //System.out.println("Size: " + deserializedPizza.getPizzaSize());
                //System.out.println("Toppings: " + deserializedPizza.getPizzaToppings());
                //System.out.println("Price: " + deserializedPizza.getPizzaPrice());
                String response = "Pizza object deserialized from JSON string: "
                        + "\nCheese: " + deserializedPizza.getPizzaCheese()
                        + System.lineSeparator() + " \r,Size: " + deserializedPizza.getPizzaSize()
                        + System.lineSeparator() + " \r,Toppings: " + deserializedPizza.getPizzaToppings()
                        + System.lineSeparator() + " \r,Price: " + deserializedPizza.getPizzaPrice();

                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                ;
            } catch (JsonProcessingException e) {
                    e.printStackTrace();
            }




        }
    }
}
