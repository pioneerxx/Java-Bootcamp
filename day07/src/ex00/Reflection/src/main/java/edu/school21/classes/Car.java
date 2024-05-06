package edu.school21.classes;

import java.util.StringJoiner;

public class Car {
    private String brand;
    private String model;
    private int mileage;

    public Car() {
        this.brand = "Daewoo";
        this.model = "Matiz";
        this.mileage = 0;
    }

    public Car(String brand, String model, int mileage) {
        this.brand = brand;
        this.model = model;
        this.mileage = mileage;
    }

    public int drive(int miles) {
        this.mileage += miles;
        return mileage;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("brand='" + brand + "'")
                .add("model='" + model + "'")
                .add("mileage=" + mileage)
                .toString();
    }
}
