package com.example.univercurso;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;


public class AuthorisationController {

    private final Connection connection;

    @FXML
    private TextField getAut_email = new TextField();

    @FXML
    private PasswordField getAut_pass = new PasswordField();

    @FXML
    private Label wrongPassOrMail;

    {
        try {
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/univerdb", "postgres", "rooot");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void logIn(ActionEvent ae) throws IOException, SQLException {
        String autEmail = getAut_email.getText();
        String autPass = getAut_pass.getText();
        PreparedStatement preparedStatement = connection.prepareStatement("select c.password from client c " +
                "where c.email = ?;");
        preparedStatement.setString(1, autEmail);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            if(autPass.equals(resultSet.getString(1))) {
                try(PreparedStatement createNewOrder = connection.prepareStatement("insert into orders (total_sum) values (0);"
                        , PreparedStatement.RETURN_GENERATED_KEYS)) {
                    createNewOrder.executeUpdate();
                    while (createNewOrder.getGeneratedKeys().next()) {
                        GlobalClassToGetOrderID.currentOrderID =  createNewOrder.getGeneratedKeys().getInt(1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                wrongPassOrMail.setText("Неверный пароль!");
            }
        } else {
            wrongPassOrMail.setText("Почта не зарегистрирована!");
        }
    }
}
