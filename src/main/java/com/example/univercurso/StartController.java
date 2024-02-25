package com.example.univercurso;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void registration(ActionEvent ae) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Registration.fxml"));
        root = loader.load();
        stage = (Stage)((Node)ae.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void authorisation(ActionEvent ae) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Authorisation.fxml"));
        root = loader.load();
        stage = (Stage)((Node)ae.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
