import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class RabbitMQSend {
    private final static String QUEUE_NAME = "hello";
    public static void main(String[] argv) throws Exception {

        Pizza pizza = new Pizza("Provolone", "9", "pepperoni", "10.00");

        // Serialization: Converting the pizza object to JSON string

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();

             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            ObjectMapper objectMapper = new ObjectMapper();
            String pizzaJson = null;
            try {
                pizzaJson = objectMapper.writeValueAsString(pizza);
                System.out.println("Pizza object serialized to JSON string:");
                System.out.println(pizzaJson);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            String message = pizzaJson;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

        }
    }
}