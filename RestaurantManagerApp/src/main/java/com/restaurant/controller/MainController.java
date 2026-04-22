package com.restaurant.controller;

import com.restaurant.dao.RestaurantDAO;
import com.restaurant.model.Restaurant;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MainController {

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField addressField;
    @FXML private TextArea outputArea;

    private final RestaurantDAO dao = new RestaurantDAO();

    @FXML
    private void insertRestaurant() {
        try {
            Restaurant r = new Restaurant(
                    Integer.parseInt(idField.getText()),
                    nameField.getText(),
                    addressField.getText()
            );
            dao.insertRestaurant(r);
            outputArea.setText("Inserted successfully!");
        } catch (Exception e) {
            outputArea.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void viewRestaurants() {
        try {
            outputArea.clear();
            dao.getAllRestaurants().forEach(r ->
                    outputArea.appendText(r.getId() + " - " + r.getName() + "\n")
            );
        } catch (Exception e) {
            outputArea.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void deleteRestaurant() {
        try {
            dao.deleteRestaurant(Integer.parseInt(idField.getText()));
            outputArea.setText("Deleted!");
        } catch (Exception e) {
            outputArea.setText("Error: " + e.getMessage());
        }
    }
}
