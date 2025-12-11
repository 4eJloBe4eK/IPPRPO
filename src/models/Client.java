package models;

public class Client {
    private int id;
    private String name;
    private String phone;
    private String address;

    public Client(int id, String name, String phone, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Клиент: " + name + " (ID: " + id + "), тел: " + phone + ", адрес: " + address;
    }
}