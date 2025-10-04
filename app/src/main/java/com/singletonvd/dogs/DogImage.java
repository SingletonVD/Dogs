package com.singletonvd.dogs;

public class DogImage {

    private String message;
    private String status;

    public DogImage(String message, String status) {
        this.message = message;
        this.status = status;
    }

    public String getImage() {
        return message;
    }

    @Override
    public String toString() {
        return "DogImage{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
