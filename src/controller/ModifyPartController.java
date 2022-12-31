package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSourced;
import model.Part;

import java.net.URL;
import java.util.ResourceBundle;
/**
 * Controller for modify part screen.
 */
public class ModifyPartController implements Initializable {
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

    /**
     * Initializes and fills in information on the selected part
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Part selectedPart = MainScreenController.getSelectedPart();
        if(selectedPart instanceof InHouse){
            inHouseButton.setSelected(true);
            machineCompanyLabel.setText("Machine ID");
            inHouseBool = true;
            machineCompanyField.setPromptText("Enter machine ID #");
            machineCompanyField.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        }
        if(selectedPart instanceof OutSourced){
            outsourcedButton.setSelected(true);
            machineCompanyLabel.setText("Company Name");
            inHouseBool = false;
            machineCompanyField.setPromptText("Enter name");
            machineCompanyField.setText(String.valueOf(((OutSourced) selectedPart).getCompanyName()));
        }
        partIdField.setText(String.valueOf(selectedPart.getId()));
        partNameField.setText(selectedPart.getName());
        partInventoryField.setText(String.valueOf(selectedPart.getStock()));
        partPriceField.setText(String.valueOf(selectedPart.getPrice()));
        partMaxField.setText(String.valueOf(selectedPart.getMax()));
        partMinField.setText(String.valueOf(selectedPart.getMin()));
    }
    /**
     * Modifies part to inhouse and sets input text.
     */
    public void inHouseButtonPressed(ActionEvent actionEvent) {
        machineCompanyLabel.setText("Machine ID");
        inHouseBool = true;
        machineCompanyField.setPromptText("Enter machine ID #");
    }
    /**
     * Modifies part to outsourced and sets input text.
     */
    public void outsourcedButtonPressed(ActionEvent actionEvent) {
        machineCompanyLabel.setText("Company Name");
        inHouseBool = false;
        machineCompanyField.setPromptText("Enter vendor name");
    }
    /**
     * Function that saves data and sets success to true if it was successful.
     */
    public void partSaveButtonPressed(ActionEvent actionEvent) {
        try {
            if(partNameField.getText() == "" || partPriceField.getText() == "" || partInventoryField.getText() == "" || partMinField.getText() == "" || partMaxField.getText() == "" || machineCompanyField.getText() == ""){
                Alert alert = new Alert(Alert.AlertType.ERROR,"Make sure all fields are filled in appropriately");
                alert.showAndWait();
            }
            else if (partNameField.getText().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Make sure all fields are filled in");
                alert.showAndWait();
            }
            else if (Integer.parseInt(partMinField.getText()) < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Minimum can't be negative");
                alert.showAndWait();
            }
            else if (Integer.parseInt(partMinField.getText()) > Integer.parseInt(partMaxField.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Maximum can't be less than minimum");
                alert.showAndWait();
            }
            else if (Integer.parseInt(partMinField.getText()) > Integer.parseInt(partInventoryField.getText()) ||
                    Integer.parseInt(partMaxField.getText()) < Integer.parseInt(partInventoryField.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Inventory must be between the range of minimum and maximum");
                alert.showAndWait();
            }
            else{
                int id = Integer.parseInt(partIdField.getText());
                String name = partNameField.getText();
                double price = Double.parseDouble(partPriceField.getText());
                int inventory = Integer.parseInt(partInventoryField.getText());
                int min = Integer.parseInt(partMinField.getText());
                int max = Integer.parseInt(partMaxField.getText());
                if (inHouseBool) {
                    int machine = Integer.parseInt(machineCompanyField.getText());
                    InHouse update = (new InHouse(id, name, price, inventory, min, max, machine));
                    Inventory.updatePart(id-1, update);
                }
                else {
                    String company = machineCompanyField.getText();
                    OutSourced update = (new OutSourced(id, name, price, inventory, min, max, company));
                    Inventory.updatePart(id-1, update);
                }
                Stage stage = (Stage) partCancelButton.getScene().getWindow();
                stage.close();

            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Check fields for correct input values");
            alert.showAndWait();
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