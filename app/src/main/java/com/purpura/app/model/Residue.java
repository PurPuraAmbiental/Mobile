package com.purpura.app.model;

public class Residue {
    private String name;
    private String description;
    private double weight;
    private int quantity;
    private String unitMesure;
    private String photoUrl;

    public Residue(String name, String description, double weight, int quantity, String unitMesure, String photoUrl) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.quantity = quantity;
        this.unitMesure = unitMesure;
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnitMesure() {
        return unitMesure;
    }

    public void setUnitMesure(String unitMesure) {
        this.unitMesure = unitMesure;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
