import models.Client;
import models.Order;
import models.Pizza;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private static List<Pizza> pizzas = new ArrayList<>();
    private static List<Order> orders = new ArrayList<>();
    private static List<Client> clients = new ArrayList<>();
    private static int nextPizzaId = 1;
    private static int nextOrderId = 1;
    private static int nextClientId = 1;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initData(); // Заполняем тестовыми данными
        showMenu();
    }

    private static void initData() {
        // Тестовые пиццы
        pizzas.add(new Pizza(nextPizzaId++, "Маргарита", 450, Pizza.Size.SMALL));
        pizzas.add(new Pizza(nextPizzaId++, "Маргарита", 650, Pizza.Size.MEDIUM));
        pizzas.add(new Pizza(nextPizzaId++, "Маргарита", 850, Pizza.Size.LARGE));
        pizzas.add(new Pizza(nextPizzaId++, "Пепперони", 550, Pizza.Size.SMALL));
        pizzas.add(new Pizza(nextPizzaId++, "Пепперони", 750, Pizza.Size.MEDIUM));
        pizzas.add(new Pizza(nextPizzaId++, "Пепперони", 950, Pizza.Size.LARGE));
        pizzas.add(new Pizza(nextPizzaId++, "Четыре сыра", 600, Pizza.Size.MEDIUM));
        pizzas.add(new Pizza(nextPizzaId++, "Гавайская", 700, Pizza.Size.LARGE));

        // Тестовые клиенты
        clients.add(new Client(nextClientId++, "Иван Иванов", "+7(999)123-45-67", "ул. Ленина, 10"));
        clients.add(new Client(nextClientId++, "Мария Петрова", "+7(999)765-43-21", "пр. Мира, 25"));
    }

    private static void showMenu() {
        while (true) {
            System.out.println("\n=== Система заказов пиццы ===");
            System.out.println("1. Показать все пиццы");
            System.out.println("2. Фильтрация пицц по размеру");
            System.out.println("3. Создать заказ");
            System.out.println("4. Поиск заказов по клиенту");
            System.out.println("5. Показать все заказы");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            int choice = readInt();
            switch (choice) {
                case 1 -> showAllPizzas();
                case 2 -> filterPizzasBySize();
                case 3 -> createOrder();
                case 4 -> findOrdersByClient();
                case 5 -> showAllOrders();
                case 0 -> {
                    System.out.println("До свидания!");
                    return;
                }
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private static void showAllPizzas() {
        System.out.println("\n--- Меню пицц ---");
        for (Pizza pizza : pizzas) {
            System.out.println(pizza.getId() + ". " + pizza);
        }
    }

    private static void filterPizzasBySize() {
        System.out.println("\nВыберите размер:");
        System.out.println("1. Маленькая");
        System.out.println("2. Средняя");
        System.out.println("3. Большая");
        int choice = readInt();

        Pizza.Size size = switch (choice) {
            case 1 -> Pizza.Size.SMALL;
            case 2 -> Pizza.Size.MEDIUM;
            case 3 -> Pizza.Size.LARGE;
            default -> {
                System.out.println("Неверный размер.");
                yield null;
            }
        };

        if (size == null) return;

        List<Pizza> filtered = pizzas.stream()
                .filter(p -> p.getSize() == size)
                .toList();

        System.out.println("\n--- Пиццы размера " + size.getName() + " ---");
        if (filtered.isEmpty()) {
            System.out.println("Нет пицц этого размера.");
        } else {
            filtered.forEach(p -> System.out.println(p.getId() + ". " + p));
        }
    }

    private static void createOrder() {
        System.out.println("\n--- Создание заказа ---");
        System.out.println("Выберите клиента:");
        for (int i = 0; i < clients.size(); i++) {
            System.out.println((i + 1) + ". " + clients.get(i));
        }
        System.out.println((clients.size() + 1) + ". Новый клиент");

        int clientChoice = readInt();
        Client client;
        if (clientChoice == clients.size() + 1) {
            client = createNewClient();
        } else if (clientChoice >= 1 && clientChoice <= clients.size()) {
            client = clients.get(clientChoice - 1);
        } else {
            System.out.println("Неверный выбор.");
            return;
        }

        Order order = new Order(nextOrderId++, client);

        System.out.println("\nДобавьте пиццы в заказ (0 - завершить):");
        showAllPizzas();
        while (true) {
            System.out.print("Введите ID пиццы (0 для завершения): ");
            int pizzaId = readInt();
            if (pizzaId == 0) break;

            Pizza pizza = pizzas.stream()
                    .filter(p -> p.getId() == pizzaId)
                    .findFirst()
                    .orElse(null);

            if (pizza != null) {
                order.addPizza(pizza);
                System.out.println("Добавлена: " + pizza.getName() + " (" + pizza.getSize() + ")");
            } else {
                System.out.println("Пицца не найдена.");
            }
        }

        if (order.getPizzas().isEmpty()) {
            System.out.println("Заказ пустой. Отменено.");
            return;
        }

        orders.add(order);
        System.out.println("\nЗаказ успешно создан!");
        System.out.println(order);
    }

    private static Client createNewClient() {
        System.out.print("Имя: ");
        String name = scanner.nextLine();
        System.out.print("Телефон: ");
        String phone = scanner.nextLine();
        System.out.print("Адрес: ");
        String address = scanner.nextLine();

        Client client = new Client(nextClientId++, name, phone, address);
        clients.add(client);
        System.out.println("Новый клиент добавлен: " + client);
        return client;
    }

    private static void findOrdersByClient() {
        System.out.println("\n--- Поиск заказов по клиенту ---");
        System.out.println("Выберите клиента:");
        for (int i = 0; i < clients.size(); i++) {
            System.out.println((i + 1) + ". " + clients.get(i).getName() + " (ID: " + clients.get(i).getId() + ")");
        }

        int choice = readInt();
        if (choice < 1 || choice > clients.size()) {
            System.out.println("Ошибка.");
            return;
        }

        Client selectedClient = clients.get(choice - 1);
        List<Order> clientOrders = orders.stream()
                .filter(o -> o.getClient().getId() == selectedClient.getId())
                .toList();

        System.out.println("\nЗаказы клиента " + selectedClient.getName() + ":");
        if (clientOrders.isEmpty()) {
            System.out.println("Нет заказов.");
        } else {
            clientOrders.forEach(System.out::println);
        }
    }

    private static void showAllOrders() {
        System.out.println("\n--- Все заказы ---");
        if (orders.isEmpty()) {
            System.out.println("Заказов нет.");
        } else {
            orders.forEach(order -> {
                System.out.println(order);
                System.out.println("-------------------");
            });
        }
    }

    private static int readInt() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Введите число: ");
            }
        }
    }
}