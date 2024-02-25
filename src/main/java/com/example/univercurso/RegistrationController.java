package com.example.univercurso;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import java.util.ResourceBundle;

public class RegistrationController  implements Initializable {
    private Connection connection;
    private Statement statement;
    @FXML
    private TextField name = new TextField();
    @FXML
    private TextField last_name = new TextField();
    @FXML
    private TextField patronym = new TextField();
    @FXML
    private TextField reg_email = new TextField();
    @FXML
    private TextField date_of_birth = new TextField();
    @FXML
    private PasswordField reg_pass = new PasswordField();
    @FXML
    private Label react = new Label();

    @FXML
    private ChoiceBox<String> choiceBox = new ChoiceBox<>();

    private HashMap<String, Integer> cities = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ResultSet resultSet;
        try {
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/univerdb", "postgres", "rooot");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from city");
            int id_counter = 1;
            while (resultSet.next()) {
                cities.put(resultSet.getString(2), id_counter++);
            }
            choiceBox.getItems().addAll(cities.keySet());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void submit_registration(ActionEvent ae) {
        String emailText = reg_email.getText();

        try{
            connection.prepareStatement("select * from client where email = ?")
                    .setString(1,emailText);

            String nameText = name.getText();
            String lastNameText = last_name.getText();
            String patronymText = patronym.getText();
            String city_chosen = choiceBox.getValue();
            String dateText = date_of_birth.getText();
            String passText = reg_pass.getText();

            PreparedStatement preparedStatement1 = connection.prepareStatement(
                    "insert into client" +
                            "(first_name, last_name, patronym, date_of_birth, city_id, email, password) " +
                            "values (?, ?, ?, ?, ?, ?, ?);");
            preparedStatement1.setString(1, nameText);
            preparedStatement1.setString(2, lastNameText);
            preparedStatement1.setString(3, patronymText);
            preparedStatement1.setDate(4, Date.valueOf(dateText));
            preparedStatement1.setInt(5, cities.get(city_chosen));
            preparedStatement1.setString(6, emailText);
            preparedStatement1.setString(7, passText);
            preparedStatement1.execute();


            FXMLLoader loader = new FXMLLoader(getClass().getResource("Start.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage)((Node)ae.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
            react.setText("Почта занята!");
        }
    }


}