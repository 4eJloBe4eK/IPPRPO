package models;

public class Pizza {
    public enum Size {
        SMALL("Маленькая", 25),
        MEDIUM("Средняя", 30),
        LARGE("Большая", 35);

        private final String name;
        private final int diameter;

        Size(String name, int diameter) {
            this.name = name;
            this.diameter = diameter;
        }

        public String getName() {
            return name;
        }

        public int getDiameter() {
            return diameter;
        }

        @Override
        public String toString() {
            return name + " (" + diameter + " см)";
        }
    }

    private int id;
    private String name;
    private double price;
    private Size size;

    public Pizza(int id, String name, double price, Size size) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.size = size;
    }

    // Геттеры
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Size getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "Пицца: " + name + " | Размер: " + size + " | Цена: " + price + " руб.";
    }
}