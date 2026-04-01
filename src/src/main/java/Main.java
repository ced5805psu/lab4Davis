import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Main {

    public static void main(String[] args) {
        // Creating an instance of the Pizza class
        Pizza pizza = new Pizza("Provolone", "9","pepperoni", "10.0");

        // Serialization: Converting the pizza object to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String pizzaJson = objectMapper.writeValueAsString(pizza);
            System.out.println("Pizza object serialized to JSON string:");
            System.out.println(pizzaJson);

            // Deserialization: Converting the JSON string back to a pizza object
            Pizza deserializedPizza = objectMapper.readValue(pizzaJson, Pizza.class);
            System.out.println("\nPizza object deserialized from JSON string:");
            System.out.println("Cheese: " + deserializedPizza.getPizzaCheese());
            System.out.println("Size: " + deserializedPizza.getPizzaSize());
            System.out.println("Toppings: " + deserializedPizza.getPizzaToppings());
            System.out.println("Price: " + deserializedPizza.getPizzaPrice());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
