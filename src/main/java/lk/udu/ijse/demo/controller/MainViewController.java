package lk.udu.ijse.demo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    public AnchorPane centerPane;

    public void navigatePages(String path){
        try {
            centerPane.getChildren().clear();
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/" + path + ".fxml"));
            centerPane.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gotoCustomerView(ActionEvent actionEvent) {
        navigatePages("CustomerView");
    }

    public void gotoItemView(ActionEvent actionEvent) {
        navigatePages("ItemView");
    }

    public void gotoOrdersView(ActionEvent actionEvent) {
        navigatePages("OrdersView");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        navigatePages("CustomerView");
    }
}
