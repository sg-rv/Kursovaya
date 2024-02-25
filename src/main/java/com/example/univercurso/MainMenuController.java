package com.example.univercurso;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML
    private Button toTV;
    @FXML
    private Button toPhotoProd;
    @FXML
    private Button toSmartphones;
    @FXML
    private Button toAudioProd;
    @FXML
    private Button toGames;
    @FXML
    private Button toHouseProd;


    private Connection connection;
    private Statement statement;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/univerdb", "postgres", "rooot");
            statement = connection.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<String> categories = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery("select * from category");
            while (resultSet.next()) {
                categories.add(resultSet.getString(2));
            }
            toTV.setText(categories.get(0));
            toPhotoProd.setText(categories.get(1));
            toSmartphones.setText(categories.get(2));
            toAudioProd.setText(categories.get(3));
            toGames.setText(categories.get(4));
            toHouseProd.setText(categories.get(5));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void toTVCommand(ActionEvent ae) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Products.fxml"));
        Parent root = loader.load();
        ProductController productController = loader.getController();
        try {
            productController.showAllInTV();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void toPhotoCommand(ActionEvent ae) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Products.fxml"));
        Parent root = loader.load();
        ProductController productController = loader.getController();
        try {
            productController.showAllInPhoto();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void toSmartCommand(ActionEvent ae) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Products.fxml"));
        Parent root = loader.load();

        ProductController productController = loader.getController();
        try {
            productController.showAllInSmartphone();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void toAudioCommand(ActionEvent ae) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Products.fxml"));
        Parent root = loader.load();

        ProductController productController = loader.getController();
        try {
            productController.showAllInAudio();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void toGamesCommand(ActionEvent ae) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Products.fxml"));
        Parent root = loader.load();

        ProductController productController = loader.getController();
        try {
            productController.showAllInGames();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void toHouseCommand(ActionEvent ae) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Products.fxml"));
        Parent root = loader.load();

        ProductController productController = loader.getController();
        try {
            productController.showAllInHouse();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void toCart(ActionEvent ae) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Cart.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
