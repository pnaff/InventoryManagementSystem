package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The controller for the main screen.
 */
public class MainScreenController implements Initializable {
    public static Part selectedPart;
    public static Part getSelectedPart() {
        return selectedPart;
    }
    public static Product selectedProduct;
    public TableView<Part> partTable;
    public TableColumn<Part, Integer> partIdCol;
    public TableColumn<Part, String> partNameCol;
    public TableColumn<Part, Integer> partInventoryCol;
    public TableColumn<Part, Integer> partPriceCol;
    public Button addPartButton;
    public Button modifyPartButton;
    public TextField partSearchBar;
    public Button deletePartButton;
    public TextField productSearchBar;
    public TableView<Product> productTable;
    public TableColumn<Product, String> productIdCol;
    public TableColumn<Product, Integer> productNameCol;
    public TableColumn<Product, Integer> productInventoryCol;
    public TableColumn<Product, Integer> productPriceCol;
    public Button addProductButton;
    public Button modifyProductButton;
    public Button deleteProductButton;
    public Button exitButton;

    /**
     * Initialize and populates the tables.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTable.setItems(Inventory.getAllParts());

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productTable.setItems(Inventory.getAllProducts());
    }
    /**
     * Uses search function when user types something in the part search bar.
     */
    public void partSearched() {
        String searchString = partSearchBar.getText();
        ObservableList<Part> parts = partSearch(searchString);
        partTable.setItems(parts);
        if (parts.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No part was found");
            alert.setContentText("Try new search");
            alert.show();
        }
    }
    /**
     * Parts search function.
     */
    private ObservableList<Part> partSearch(String searchString) {
        ObservableList<Part> parts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();

        for (Part part : allParts) {
            if (part.getName().toLowerCase().contains(searchString.toLowerCase()) || Integer.toString(part.getId()).contains(searchString)) {
                parts.add(part);
            }
        }
        return parts;
    }
    /**
     * Uses search function when user types something in the product search bar.
     */
    public void productSearched() {
        String searchString = productSearchBar.getText();
        ObservableList<Product> products = productSearch(searchString);
        productTable.setItems(products);
        if (products.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No product was found");
            alert.setContentText("Try new search");
            alert.show();
        }
    }
    /**
     * Products search function.
     */
    private ObservableList<Product> productSearch(String searchString) {
        ObservableList<Product> products = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();


        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(searchString.toLowerCase()) || Integer.toString(product.getId()).contains(searchString)) {
                products.add(product);
            }
        }
        return products;
    }
    /**
     * Opens a new screen for add part menu.
     *
     * RUNTIME ERROR: I tried removing the IOException method and when trying to run the application I got a runtime error.
     * The MainScreeController expects the exception since it is declared in four of the functions definitions.
     * I fixed this error by recognizing why the exception is thrown and adding it back to the method signature.
     */

    public void addPartButtonPressed() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AddPart.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Add Parts");
        stage.setScene(new Scene(root1));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    /**
     * Opens a new screen for modify part menu.
     * Error if no part is selected.
     */
    public void modifyPartButtonPressed() throws IOException {
        selectedPart = partTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"No part selected");
            alert.showAndWait();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ModifyPart.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Modify Part");
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        }
    }
    /**
     * Deletes selected part.
     * Error if no part is selected.
     * Confirmation alert when a part is selected and delete button pressed.
     */
    public void deletePartButtonPressed() {
        selectedPart = partTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"No part selected");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("WARNING!");
            alert.setHeaderText("You are about to delete a part");
            alert.setContentText("Are you sure you want to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
            }
        }
    }
    /**
     * Opens a new screen for add product menu.
     */
    public void addProductButtonPressed() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AddProduct.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Add Products");
        stage.setScene(new Scene(root1));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }
    /**
     * Opens a new screen for modify product menu.
     * Error if no product is selected.
     */
    public void modifyProductButtonPressed() throws IOException {
        selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"No product selected");
            alert.showAndWait();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ModifyProduct.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Modify Products");
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        }
    }
    /**
     * Deletes selected product.
     * Error if no product is selected.
     * Confirmation alert when a product is selected and delete button pressed.
     */
    public void deleteProductButtonPressed() {
        selectedProduct = productTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"No product selected");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("WARNING!");
            alert.setHeaderText("You are about to delete a product");
            alert.setContentText("Are you sure you want to delete this product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                ObservableList<Part> associatedParts = selectedProduct.getAllAssociatedParts();
                if (associatedParts.size() > 0) {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setHeaderText("This product has parts associated with it");
                    alert2.setContentText("Modify product and remove parts");
                    alert2.showAndWait();
                } else {
                    Inventory.deleteProduct(selectedProduct);
                }
            }
        }
    }
    /**
     * Exits the application.
     */
    public void exitButtonPressed() {
        System.exit(0);
    }
}