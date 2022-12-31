package main;

/**
 * @author Precious Naff
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;
/** RUNTIME ERROR located in MainScreenController.
 * FUTURE ENHANCEMENT: A future enhancement that could be made to this application would be to add the
 * ability to select multiple parts that are associated to a product when deleting a product.
 * Currently, you have to go through and delete each part one by one then delete the product.
 */
public class Main extends Application {

    /**
     * The start method creates the fxml file and loads the stage.
     */
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(root, 1070,440));
        stage.show();
    }
    /**
     * The main method loads beginning data and launches the application.
     */
    public static void main(String[] args) {


        Inventory.addPart(new InHouse(1,"Brakes", 15.00, 12, 1, 20, 1));
        Inventory.addPart(new InHouse(2,"Wheel", 24.99, 24, 1, 20, 3));
        Inventory.addPart(new InHouse(3,"Seat", 12.00, 18, 1, 20, 3));
        Inventory.addPart(new InHouse(4,"Rim", 30.00, 16, 1, 20, 4));
        Inventory.addPart(new InHouse(5,"Tire", 29.99, 15, 1, 20, 2));

        Inventory.addPart(new OutSourced(6,"Basket", 35.00, 6, 1, 20, "Rollin Bikes"));
        Inventory.addPart(new OutSourced(7,"Bell", 19.99, 13, 1, 20, "Explore"));
        Inventory.addPart(new OutSourced(8,"Lights", 49.99, 11, 1, 20, "Rollin Bikes"));

        Inventory.addProduct(new Product(1, "Giant Bike",499.99, 5, 0, 10));
        Inventory.lookupProduct(1).addAssociatedPart(Inventory.lookupPart(12));
        Inventory.lookupProduct(1).addAssociatedPart(Inventory.lookupPart(9));
        Inventory.lookupProduct(1).addAssociatedPart(Inventory.lookupPart(11));
        Inventory.addProduct(new Product(2, "Tricycle",299.99, 9, 0, 10));
        Inventory.lookupProduct(2).addAssociatedPart(Inventory.lookupPart(1));
        Inventory.lookupProduct(2).addAssociatedPart(Inventory.lookupPart(3));
        Inventory.lookupProduct(2).addAssociatedPart(Inventory.lookupPart(5));
        Inventory.addProduct(new Product(3, "Super Bike",600.00, 2, 0, 10));
        Inventory.lookupProduct(3).addAssociatedPart(Inventory.lookupPart(6));
        Inventory.lookupProduct(3).addAssociatedPart(Inventory.lookupPart(1));
        Inventory.lookupProduct(3).addAssociatedPart(Inventory.lookupPart(9));
        Inventory.lookupProduct(3).addAssociatedPart(Inventory.lookupPart(14));

        launch(args);
    }
}