package com.singletonvd.dogs;

public class DogImage {

    private String image;
    private String status;

    public DogImage(String image, String status) {
        this.image = image;
        this.status = status;
    }

    @Override
    public String toString() {
        return "DogImage{" +
                "image='" + image + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
