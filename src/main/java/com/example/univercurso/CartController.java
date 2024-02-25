package com.example.univercurso;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CartController implements Initializable {
    private Connection connection;

    @FXML
    private TableView<ProductEntity> prodTable = new TableView<>();

    @FXML
    private final TableColumn<ProductEntity, String> prodName = new TableColumn<>("Наименование товара");
    @FXML
    private final TableColumn<ProductEntity, Integer> price = new TableColumn<>("Цена");
    @FXML
    private TextField total_sum;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/univerdb", "postgres", "rooot");

            prodName.setCellValueFactory(new PropertyValueFactory<>("name"));
            prodTable.getColumns().add(prodName);
            price.setCellValueFactory(new PropertyValueFactory<>("price"));
            prodTable.getColumns().add(price);

            PreparedStatement getBoughtProd = connection
                    .prepareStatement("select p.product_name, p.price from product p " +
                    "inner join cart c on p.product_id = c.product_id where c.order_id  = ?;");
            getBoughtProd.setInt(1,GlobalClassToGetOrderID.currentOrderID);
            ResultSet allBought = getBoughtProd.executeQuery();
            while (allBought.next()) {
                prodTable.getItems()
                        .add(new ProductEntity(allBought.getString(1), allBought.getInt(2)));
            }

            PreparedStatement sumUpAll = connection
                    .prepareStatement("select sum(p.price) from product p " +
                    "inner join cart c on p.product_id = c.product_id where c.order_id = ?;");
            sumUpAll.setInt(1,GlobalClassToGetOrderID.currentOrderID);
            ResultSet totalPrice = sumUpAll.executeQuery();
            int total = 0;
            while (totalPrice.next()) {
                total = totalPrice.getInt(1);
            }

            PreparedStatement updateOrderSum = connection
                    .prepareStatement("update orders set total_sum = ? where order_id = ?;");
            updateOrderSum.setInt(1,total);
            updateOrderSum.setInt(2,GlobalClassToGetOrderID.currentOrderID);
            updateOrderSum.executeUpdate();
            total_sum.setText(String.valueOf(total));


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void pay(ActionEvent ae) throws SQLException, IOException {
        PreparedStatement pay = connection
                .prepareStatement("update orders set paid = true where order_id = ?;");
        pay.setInt(1,GlobalClassToGetOrderID.currentOrderID);
        pay.executeUpdate();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Final.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
