package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSourced;

import static model.Inventory.lookupPart;

/**
 *  Controller for add part screen.
 */
public class AddPartController {

    private boolean inHouseBool = true;
    public RadioButton inHouseButton;
    public RadioButton outsourcedButton;
    public ToggleGroup PartSwitcher;
    public TextField partIdField;
    public TextField partNameField;
    public TextField partInventoryField;
    public TextField partPriceField;
    public TextField partMaxField;
    public TextField partMinField;
    public TextField machineCompanyField;
    public Label machineCompanyLabel;
    public Button partSaveButton;
    public Button partCancelButton;
    public boolean success;

    /**
     * Switches part type to in-house and updates the label for machine id.
     */
    public void inHouseButtonPressed(ActionEvent actionEvent) {
        machineCompanyLabel.setText("Machine ID");
        inHouseBool = true;
        machineCompanyField.setPromptText("Enter machine ID #");
    }

    /**
     * Switches part type to outsourced and updates the label for company name.
     */
    public void outsourcedButtonPressed(ActionEvent actionEvent) {
        machineCompanyLabel.setText("Company Name");
        inHouseBool = false;
        machineCompanyField.setPromptText("Enter name");
    }

    /**
     * Generates a new ID number and finds the lowest number to use.
     */
    public static int getNewID() {
        int newID = 1;
        while (lookupPart(newID) != null) {
            newID++;
        }
        return newID;
    }

    /**
     * Function that saves data and sets success to true if it was successful.
     */
    public void partSaveButtonPressed(ActionEvent actionEvent) {
        try {
            success = false;
            if (partNameField.getText() == "" || partPriceField.getText() == "" || partInventoryField.getText() == "" || partMinField.getText() == "" || partMaxField.getText() == "" || machineCompanyField.getText() == "") {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Make sure all fields are filled in");
                alert.showAndWait();
            }
            else if (partNameField.getText().trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR,"Make sure all fields are filled in");
                    alert.showAndWait();
            }
            else if (Integer.parseInt(partMinField.getText()) < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Minimum can't be negative");
                alert.showAndWait();
            }
            else if (Integer.parseInt(partMinField.getText()) > Integer.parseInt(partMaxField.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Maximum can't be less than minimum");
                alert.showAndWait();
            }
            else if (Integer.parseInt(partMinField.getText()) > Integer.parseInt(partInventoryField.getText()) ||
                    Integer.parseInt(partMaxField.getText()) < Integer.parseInt(partInventoryField.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory must be between the range of minimum and maximum");
                alert.showAndWait();
            }
            else {
                int id = getNewID();
                String name = partNameField.getText();
                double price = Double.parseDouble(partPriceField.getText());
                int inventory = Integer.parseInt(partInventoryField.getText());
                int min = Integer.parseInt(partMinField.getText());
                int max = Integer.parseInt(partMaxField.getText());
                success = true;
                if (inHouseBool) {
                    int machine = Integer.parseInt(machineCompanyField.getText());
                    Inventory.addPart(new InHouse(id, name, price, inventory, min, max, machine));
                } else {
                    String company = machineCompanyField.getText();
                    Inventory.addPart(new OutSourced(id, name, price, inventory, min, max, company));
                }

            }
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Check fields for correct input values");
            alert.showAndWait();
        }
        if (success) {
            Stage stage = (Stage) partCancelButton.getScene().getWindow();
            stage.close();
        }
    }
    /**
     * Exits the screen.
     */
    public void partCancelButtonPressed(ActionEvent actionEvent) {
        Stage stage = (Stage) partCancelButton.getScene().getWindow();
        stage.close();
    }

}