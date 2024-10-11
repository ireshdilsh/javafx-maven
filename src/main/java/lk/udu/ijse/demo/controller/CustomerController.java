package lk.udu.ijse.demo.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.udu.ijse.demo.dto.CustomerDto;
import lk.udu.ijse.demo.dto.tm.CustomerTM;
import lk.udu.ijse.demo.model.CustomerModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    private final CustomerModel customerModel = new CustomerModel();

    public TextField idText;
    public TextField nameText;
    public TextField nicText;
    public TextField emailText;
    public TextField contactText;
    public TableView <CustomerTM> customerTable;
    public TableColumn <CustomerTM,String> idColumn;
    public TableColumn <CustomerTM,String> nameColumn;
    public TableColumn <CustomerTM,String> nicColumn;
    public TableColumn <CustomerTM,String> emailColumn;
    public TableColumn <CustomerTM,String> contactColumn;
    public Button saveButton;
    public Button updateButton;
    public Button deleteButton;

    public void addNewCustomer(ActionEvent actionEvent) throws SQLException {
        CustomerDto customerDto = new CustomerDto(
                idText.getText(),
                nameText.getText(),
                nicText.getText(),
                emailText.getText(),
                contactText.getText());
        String resp = customerModel.saveCustomer(customerDto);
        new Alert(Alert.AlertType.INFORMATION, resp).show();
        clearTextFields();
        getNextCustomerID();
        getAllCustomers();
    }

    public void updateCustomer(ActionEvent actionEvent) throws SQLException {

        if (new Alert(Alert.AlertType.CONFIRMATION,"Do you want to update this Customer ?").showAndWait().get() == ButtonType.OK) {
            saveButton.setDisable(true);
            CustomerDto customerDto = new CustomerDto(
                    idText.getText(),
                    nameText.getText(),
                    nicText.getText(),
                    emailText.getText(),
                    contactText.getText());
            String resp = customerModel.updateCustomer(customerDto);
            new Alert(Alert.AlertType.INFORMATION, resp).show();
            clearTextFields();
            getAllCustomers();
            getNextCustomerID();
        }
    }

    public void deleteCustomer(ActionEvent actionEvent) throws SQLException {
        if (new Alert(Alert.AlertType.CONFIRMATION,"Do you want to Delete this Customer ? ").showAndWait().get() == ButtonType.OK){
            saveButton.setDisable(true);
            String deleteID = idText.getText();
            String resp = customerModel.deleteCustomer(deleteID);
            new Alert(Alert.AlertType.INFORMATION, resp).show();
            clearTextFields();
            getNextCustomerID();
            getAllCustomers();
        }
    }

    void clearTextFields() {
        idText.clear();
        nameText.clear();
        nicText.clear();
        emailText.clear();
        contactText.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            updateButton.setDisable(true);
            deleteButton.setDisable(true);
            getAllCustomers();
            getNextCustomerID();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getAllCustomers(){
        customerTable.setPlaceholder(new Label("Not Customer Yet"));

        idColumn.setCellValueFactory(new PropertyValueFactory<>("custID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("custName"));
        nicColumn.setCellValueFactory(new PropertyValueFactory<>("custNIC"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("custEmail"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("custPhone"));

        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();
        CustomerTM customerTM = null;
        try {
            ObservableList<CustomerTM> allCustomers = customerModel.getAllCustomers();
            customerTMS.add(customerTM);
            customerTable.setItems(allCustomers);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void searchCustomer(MouseEvent mouseEvent) {
        saveButton.setDisable(true);
        deleteButton.setDisable(false);
        updateButton.setDisable(false);
        if (customerTable.getSelectionModel().getSelectedItem() != null) {
            CustomerTM customerTM = customerTable.getSelectionModel().getSelectedItem();
            idText.setText(customerTM.getCustID());
            nameText.setText(customerTM.getCustName());
            nicText.setText(customerTM.getCustNIC());
            emailText.setText(customerTM.getCustEmail());
            contactText.setText(customerTM.getCustPhone());
        } else {
            new Alert(Alert.AlertType.WARNING, "Select a Customer First").show();
        }
    }

    public void getNextCustomerID() throws SQLException {
        String lastCustomerID = customerModel.getNextCustomerID();
        idText.setText(lastCustomerID);
    }
}
