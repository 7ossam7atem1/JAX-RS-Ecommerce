package com.example.jakarta.hello.Models;

import jakarta.ejb.Singleton;

import java.util.*;

@Singleton
public class ProductRepository {
    private static final ProductRepository instance = new ProductRepository();

    private final Map<String, ProductModel> productModels = new HashMap<>();

    private ProductRepository() {}

    public static ProductRepository getInstance() {
        return instance;
    }

    public String addProduct(ProductModel productModel) {
        String response = validateProductParameters(productModel);

        if (productModels.containsKey(productModel.getName())) {
            return "The product '" + productModel.getName() + "' already exists in the store. Please use a unique product name.";
        }

        if (response.equals("valid")) {
            productModels.put(productModel.getName(), productModel);
            return "The product '" + productModel.getName() + "' has been added successfully to the store.";
        }
        return response;
    }

    public String updateProduct(ProductModel newProductModel) {
        String response = validateProductParameters(newProductModel);

        if (response.equals("valid")) {
            if (!productModels.containsKey(newProductModel.getName())) {
                return "The product '" + newProductModel.getName() + "' does not exist in the store. Update operation failed.";
            }

            productModels.put(newProductModel.getName(), newProductModel);
            return "The product '" + newProductModel.getName() + "' has been updated successfully.";
        }
        return response;
    }

    public String deleteProduct(String name) {
        if (!productModels.containsKey(name)) {
            return "The product '" + name + "' does not exist in the store. Deletion operation failed.";
        }

        productModels.remove(name);
        return "The product '" + name + "' has been removed successfully from the store.";
    }

    public Optional<ProductModel> searchToProduct(String name) {
        return Optional.ofNullable(productModels.get(name));
    }

    public Collection<ProductModel> getProducts() {
        return productModels.values();
    }

    private String validateProductParameters(ProductModel productModel) {
        String productName = productModel.getName();
        double productPrice = productModel.getPrice();

        if (productName == null || productName.isEmpty())
            return "The product name must not be empty. Please provide a valid name.";
        if (productName.length() > 100)
            return "The product name must not exceed 100 characters. Please provide a shorter name.";
        if (productPrice < 0)
            return "The product price must be a non-negative value. Please provide a valid price.";
        return "valid";
    }
}
