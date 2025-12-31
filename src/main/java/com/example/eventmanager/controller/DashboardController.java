package com.example.eventmanager.controller;

import com.example.eventmanager.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class DashboardController {
    @FXML private Label lblWelcome;

    @FXML
    public void initialize() {
        if (SessionManager.getCurrentUser() != null) {
            lblWelcome.setText("Logged in as: " + SessionManager.getCurrentUser().getUsername());
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) throws IOException {
        SessionManager.logout();
        Parent loginView = FXMLLoader.load(getClass().getResource("/fxml/LoginView.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loginView));
        stage.show();
    }
}