package com.example.jakarta.hello.Models;

import java.util.*;

public class Store {
    // Singleton instance
    private static final Store instance = new Store();


    private final Set<Product> products =
            new TreeSet<>(Comparator.comparing(Product::getName));


    private Store() {}


    public static Store getInstance() {
        return instance;
    }

    public String addProduct(Product product) {
        String message = validateProductParameters(product);

        if (products.contains(product)) {
            return "The product '" + product.getName() + "' already exists in the store. Please use a unique product name.";
        }

        if (message.equals("valid")) {
            products.add(product);
            return "The product '" + product.getName() + "' has been added successfully to the store.";
        }
        return message;
    }

    public String updateProduct(Product newProduct) {
        String message = validateProductParameters(newProduct);

        if (message.equals("valid")) {
            Optional<Product> oldProduct = getProduct(newProduct.getName());

            if (oldProduct.isEmpty())
                return "The product '" + newProduct.getName() + "' does not exist in the store. Update operation failed.";

            products.remove(oldProduct.get());
            products.add(newProduct);

            return "The product '" + newProduct.getName() + "' has been updated successfully.";
        }
        return message;
    }

    public String deleteProduct(String name) {
        Optional<Product> product = getProduct(name);

        if (product.isEmpty())
            return "The product '" + name + "' does not exist in the store. Deletion operation failed.";

        products.remove(product.get());
        return "The product '" + name + "' has been removed successfully from the store.";
    }

    public Optional<Product> searchToProduct(String name) {
        return getProduct(name);
    }

    public Set<Product> getProducts() {
        return products;
    }

    private Optional<Product> getProduct(String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .findFirst();
    }

    private String validateProductParameters(Product product) {
        String productName = product.getName();
        double productPrice = product.getPrice();

        if (productName == null || productName.isEmpty())
            return "The product name must not be empty. Please provide a valid name.";
        if (productName.length() > 100)
            return "The product name must not exceed 100 characters. Please provide a shorter name.";
        if (productPrice < 0)
            return "The product price must be a non-negative value. Please provide a valid price.";
        return "valid";
    }
}
