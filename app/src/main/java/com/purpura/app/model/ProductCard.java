package com.purpura.app.model;

public class ProductCard {
    String productName;
    String productValue;
    String productWeight;
    String productImage;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }



    public String getProductImage() {
        return productImage;
    }

    public ProductCard(String productName, String productValue, String productWeight, String productImage) {
        this.productName = productName;
        this.productValue = productValue;
        this.productWeight = productWeight;
        this.productImage = productImage;
    }
}
