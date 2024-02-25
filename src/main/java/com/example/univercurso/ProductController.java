package com.example.univercurso;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProductController implements Initializable {

    private Connection connection;
    private Statement statement;
    @FXML
    private TableView<ProductEntity> prodTable = new TableView<>();
    @FXML
    private final TableColumn<ProductEntity, String> prodName = new TableColumn<>("Наименование товара");
    @FXML
    private final TableColumn<ProductEntity, Integer> price = new TableColumn<>("Цена");
    @FXML
    private final TableColumn<ProductEntity, Integer> availability = new TableColumn<>("В наличии");
    @FXML
    private final TableColumn<ProductEntity, Button> addProductButton = new TableColumn<>();

    @FXML
    private List<String> addedProd = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/univerdb", "postgres", "rooot");
            statement = connection.createStatement();

            prodName.setCellValueFactory(new PropertyValueFactory<>("name"));
            prodTable.getColumns().add(prodName);
            price.setCellValueFactory(new PropertyValueFactory<>("price"));
            prodTable.getColumns().add(price);
            availability.setCellValueFactory(new PropertyValueFactory<>("available"));
            prodTable.getColumns().add(availability);
            addProductButton.setCellValueFactory(new PropertyValueFactory<>("addProduct"));
            prodTable.getColumns().add(addProductButton);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void goBack(ActionEvent ae) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    public void showAllInTV() throws SQLException {
        prodProcessing("tv_product_info");
    }
    public void showAllInPhoto() throws SQLException {
        prodProcessing("photo_product_info");
    }
    public void showAllInSmartphone() throws SQLException {
        prodProcessing("phone_product_info");
    }
    public void showAllInAudio() throws SQLException {
        prodProcessing("audio_product_info");
    }
    public void showAllInGames() throws SQLException {
        prodProcessing("game_product_info");
    }
    public void showAllInHouse() throws SQLException {
        prodProcessing("house_product_info");
    }

    private void prodProcessing(String category) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from " + category);
        while (resultSet.next()) {
            String prodName = resultSet.getString(1);
            int prodPrice = resultSet.getInt(2);
            int prodAvailable = resultSet.getInt(3);
            Button button = new Button("добавить в корзину");
            if(prodAvailable == 0) {
                button.setDisable(true);
            }
            button.setOnAction(event-> {
                try {
                    int prodID = 0;
                    ResultSet prodIDSet = statement
                            .executeQuery("select p.product_id from product p " +
                                    "where p.product_name = '" + prodName + "';");
                    while (prodIDSet.next()) {
                        prodID = prodIDSet.getInt(1);
                    }
                    PreparedStatement preparedStatement = connection
                            .prepareStatement("insert into cart(order_id, product_id) values(?,?);");
                    preparedStatement.setInt(1, GlobalClassToGetOrderID.currentOrderID);
                    preparedStatement.setInt(2, prodID);
                    preparedStatement.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                button.setDisable(true);
            });
            prodTable.getItems().add(new ProductEntity(prodName,prodPrice, prodAvailable, button));
        }
    }
}
