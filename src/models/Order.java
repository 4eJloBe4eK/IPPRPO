package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private Client client;
    private List<Pizza> pizzas;
    private LocalDateTime orderDate;
    private double totalPrice;

    public Order(int id, Client client) {
        this.id = id;
        this.client = client;
        this.pizzas = new ArrayList<>();
        this.orderDate = LocalDateTime.now();
        this.totalPrice = 0;
    }

    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
        totalPrice += pizza.getPrice();
    }

    // Геттеры
    public int getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        StringBuilder sb = new StringBuilder();
        sb.append("Заказ №").append(id).append("\n");
        sb.append(client).append("\n");
        sb.append("Дата: ").append(orderDate.format(formatter)).append("\n");
        sb.append("Пиццы:\n");
        for (Pizza pizza : pizzas) {
            sb.append("  - ").append(pizza).append("\n");
        }
        sb.append("Итого: ").append(totalPrice).append(" руб.\n");
        return sb.toString();
    }
}