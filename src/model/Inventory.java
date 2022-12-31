package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory model for parts and products with lists and functions.
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
    /**
     * Looks up a part.
     */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) return part;

        }
        return null;
    }
    /**
     * Looks up a product.
     */
    public static Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) return product;
        }
        return null;
    }
    /**
     * Looks up all parts by name.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> parts = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().contains(partName)) {
                parts.add(part);
            }
        }
        return parts;
    }
    /**
     * Looks up all products by name.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> products = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if (product.getName().contains(productName)) {
                products.add(product);
            }
        }
        return products;
    }
    /**
     * Updates a part.
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }
    /**
     * Updates a product.
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }
    /**
     * Deletes a part.
     */
    public static boolean deletePart(Part selectedPart) {
        if(allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }
    /**
     * Deletes a product.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if(allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }
    /**
     * Gets all parts.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    /**
     * Gets all products.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}