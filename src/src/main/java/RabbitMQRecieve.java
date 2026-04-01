import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RabbitMQRecieve extends Pizza{

    private final static String QUEUE_NAME = "hello";

    public RabbitMQRecieve(String pizzaCheese, String pizzaSize, String pizzaToppings, String pizzaPrice) {
        super(pizzaCheese, pizzaSize, pizzaToppings, pizzaPrice);
    }

    public static void main(String[] argv) throws Exception {

        // Deserialization: Converting the JSON string back to a pizza object
        ObjectMapper objectMapper = new ObjectMapper();
        Pizza pizza = new Pizza("Provolone", "9", "pepperoni", "10.00");

        try {
            String pizzaJson = objectMapper.writeValueAsString(pizza);
            Pizza deserializedPizza = objectMapper.readValue(pizzaJson, Pizza.class);
            System.out.println("\nPizza object deserialized from JSON string:");
            System.out.println("Cheese: " + deserializedPizza.getPizzaCheese());
            System.out.println("Size: " + deserializedPizza.getPizzaSize());
            System.out.println("Toppings: " + deserializedPizza.getPizzaToppings());
            System.out.println("Price: " + deserializedPizza.getPizzaPrice());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Channel channel;
        try (Connection connection = factory.newConnection()) {
            channel = connection.createChannel();
        }

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });

    }
}