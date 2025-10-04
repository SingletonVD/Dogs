package com.singletonvd.dogs;

public class DogImage {

    private String image;
    private String status;

    public DogImage(String image, String status) {
        this.image = image;
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "DogImage{" +
                "image='" + image + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
