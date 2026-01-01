package com.example.eventmanager.controller;

import com.example.eventmanager.entity.Event;
import com.example.eventmanager.service.EventService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class EventCardController {
    @FXML private ImageView imgEvent;
    @FXML private Label lblTitle, lblLocation, lblParticipants;

    private Event event;
    private EventService eventService = new EventService();
    private DashboardController dashboardController;

    public void setData(Event event, DashboardController dashboardController) {
        this.event = event;
        this.dashboardController = dashboardController;

        lblTitle.setText(event.getTitle());
        lblLocation.setText(event.getLocation());
        lblParticipants.setText("Max: " + event.getMaxParticipants());

        try {
            if (event.getImageUrl() != null && !event.getImageUrl().isEmpty()) {
                imgEvent.setImage(new Image(event.getImageUrl(), true));
            }
        } catch (Exception e) {
            // Fallback image if URL is broken
        }
    }

    @FXML
    private void onDelete() {
        eventService.deleteEvent(event);
        dashboardController.refreshEvents(); // Trigger dashboard UI update
    }

    @FXML
    private void onEdit() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EventForm.fxml"));
            Parent root = loader.load();

            EventFormController controller = loader.getController();
            controller.setEvent(this.event); // Pass the current event to the form

            Stage stage = new Stage();
            stage.setTitle("Edit Event: " + event.getTitle());
            stage.setScene(new Scene(root));
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // Refresh the list after editing
            dashboardController.refreshEvents();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}