package lk.udu.ijse.demo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {

    public TextField emailText;
    public TextField passwordText;
    public AnchorPane loginPain;

    private final String email = "1234@gmail.com";
    private final String password = "1234";


    public void gotoDashboard(ActionEvent actionEvent) throws Exception {
        if (emailText.getText().equals(email) && passwordText.getText().equals(password)) {

            Parent root = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();
            loginPain.getScene().getWindow().hide();

        } else if (emailText.getText().equals(email) && !passwordText.getText().equals(password)) {
            new Alert(Alert.AlertType.ERROR,"Your Password is Incorrect").show();
        } else if (!emailText.getText().equals(email) && passwordText.getText().equals(password)) {
            new Alert(Alert.AlertType.ERROR, "Your Email is Incorrect").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Your Email and Password is Incorrect").show();
        }
    }
}
