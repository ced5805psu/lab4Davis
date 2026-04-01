/**
 * The name of the student.
 */

public class Pizza {
    private String pizzaCheese;
    private String pizzaSize;
    private String pizzaPrice;
    private String pizzaToppings;

    public Pizza(String pizzaCheese, String pizzaSize, String pizzaToppings, String pizzaPrice) {
        this.pizzaCheese = pizzaCheese;
        this.pizzaSize = pizzaSize;
        this.pizzaToppings = pizzaToppings;
        this.pizzaPrice = pizzaPrice;
    }
    // Returns the type of cheese on the pizza
    public String getPizzaCheese() {return pizzaCheese;}
    public void setPizzaCheese(String pizzaCheese) {this.pizzaCheese = pizzaCheese;}

    // Returns the size of the pizza in inches
    public String getPizzaSize() {return pizzaSize;}
    public void setPizzaSize(String pizzaSize) {this.pizzaSize = pizzaSize;}

    // Returns the kind of pizza toppings on the pizza
    public String getPizzaToppings() {return pizzaToppings;}
    public void setPizzaToppings(String pizzaToppings) {this.pizzaToppings = pizzaToppings;}

    // Returns the price of the pizza
    public String getPizzaPrice() {return pizzaPrice;}
    public void setPizzaPrice(String pizzaPrice) {this.pizzaPrice = pizzaPrice;}

    public String toFixedFormatString() {
        return String.format("%-10s%-10s%-20s", pizzaCheese, pizzaSize, pizzaToppings, pizzaPrice);
    }

    public static Pizza fromFixedFormatString(String line) {
        String pizzaCheese = line.substring(0, 10).trim();
        String pizzaSize = line.substring(10, 20).trim();
        String pizzaToppings = line.substring(20, 40).trim();
        String pizzaPrice = line.substring(40, 50).trim();
        return new Pizza(pizzaCheese, pizzaSize, pizzaToppings, pizzaPrice);
    }
};

