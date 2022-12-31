package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.lookupProduct;
/**
 * Controller for add product screen.
 */
public class AddProductController implements Initializable{
    public TextField partSearchBar;
    public TextField productIdField;
    public TextField productNameField;
    public TextField productInventoryField;
    public TextField productPriceField;
    public TextField productMaxField;
    public TextField productMinField;
    public Label recommendedPriceText;
    public Button addPartButton;
    public Button removePartButton;
    public Button productSaveButton;
    public Button productCancelButton;
    public TableView<Part> partTable;
    public TableColumn<Part, Integer> partIdCol;
    public TableColumn<Part, String> partNameCol;
    public TableColumn<Part, Integer> partInventoryCol;
    public TableColumn<Part, Integer> partPriceCol;
    public TableView<Part> associatedPartTable;
    public TableColumn<Part, Integer> associatedPartIdCol;
    public TableColumn<Part, String> associatedPartNameCol;
    public TableColumn<Part, Integer> associatedPartInventoryCol;
    public TableColumn<Part, Integer> associatedPartPriceCol;
    private static Part partToMove;
    public boolean success;
    private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Initialize and populates the part table.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        partTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    /**
     * Parts search function.
     */
    private ObservableList<Part> partSearch(String searchString){
        ObservableList<Part> parts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();

        for(Part part : allParts){
            if(part.getName().toLowerCase().contains(searchString.toLowerCase()) || Integer.toString(part.getId()).contains(searchString)){
                parts.add(part);
            }
        }
        return parts;
    }
    /**
     * Uses search function when user types something in the part search bar.
     */
    public void partSearched() {
        String searchString = partSearchBar.getText();
        ObservableList<Part> parts = partSearch(searchString);
        partTable.setItems(parts);
        if (parts.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No part was found");
            alert.setContentText("Try new search");
            alert.show();
        }
    }
    /**
     * Adds selected part in table to the associated part table.
     */
    public void addPartButtonPressed(ActionEvent actionEvent) {
        partToMove = partTable.getSelectionModel().getSelectedItem();
        if (partToMove == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"No part selected");
            alert.showAndWait();
        }
        else{
            associatedParts.add(partToMove);
            associatedPartTable.setItems(associatedParts);
        }
    }
    /**
     * Removes selected part.
     * Error if no part is selected.
     * Confirmation alert when a part is selected and delete button pressed.
     */
    public void removePartButtonPressed(ActionEvent actionEvent) {
        partToMove = associatedPartTable.getSelectionModel().getSelectedItem();
        if (partToMove == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"No part selected");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("WARNING!");
            alert.setHeaderText("You are about to remove a part");
            alert.setContentText("Are you sure you want to remove this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                associatedParts.remove(partToMove);
                associatedPartTable.setItems(associatedParts);
            }
        }
    }
    /**
     * Generates a new ID number and finds the lowest number to use.
     */
    public static int getNewID() {
        int newID = 1;
        while ( lookupProduct(newID) != null){
            newID++;
        }
        return newID;
    }
    /**
     * Function that saves data and sets success to true if it was successful.
     */
    public void productSaveButtonPressed(ActionEvent actionEvent) {
        try {
            success = false;

            if(productNameField.getText() == "" || productPriceField.getText() == "" || productInventoryField.getText() == "" || productMinField.getText() == "" || productMaxField.getText() == ""){
                Alert alert = new Alert(Alert.AlertType.ERROR,"Make sure all fields are filled in");
                alert.showAndWait();
            }
            else if (productNameField.getText().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Make sure all fields are filled in");
                alert.showAndWait();
            }
            else if (Integer.parseInt(productMinField.getText()) < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Minimum can't be negative");
                alert.showAndWait();
            }
            else if (Integer.parseInt(productMinField.getText()) > Integer.parseInt(productMaxField.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Maximum can't be less than minimum");
                alert.showAndWait();
            }
            else if (Integer.parseInt(productMinField.getText()) > Integer.parseInt(productInventoryField.getText()) ||
                    Integer.parseInt(productMaxField.getText()) < Integer.parseInt(productInventoryField.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Inventory must be between the range of minimum and maximum");
                alert.showAndWait();
            }
            else {
                int id = getNewID();
                String name = productNameField.getText();
                double price = Double.parseDouble(productPriceField.getText());
                int inventory = Integer.parseInt(productInventoryField.getText());
                int min = Integer.parseInt(productMinField.getText());
                int max = Integer.parseInt(productMaxField.getText());
                success = true;
                Inventory.addProduct(new Product(id, name, price, inventory, min, max));
                for (Part part : associatedParts) {
                    Inventory.lookupProduct(id).addAssociatedPart(part);
                }
            }
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Check fields for correct input values");
            alert.showAndWait();
        }
        if (success) {
            Stage stage = (Stage) productCancelButton.getScene().getWindow();
            stage.close();
        }
    }
    /**
     * Exits the screen.
     */
    public void productCancelButtonPressed(ActionEvent actionEvent) {
        Stage stage = (Stage) productCancelButton.getScene().getWindow();
        stage.close();
    }

}