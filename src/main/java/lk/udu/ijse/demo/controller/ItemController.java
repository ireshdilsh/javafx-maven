package lk.udu.ijse.demo.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.udu.ijse.demo.dto.ItemDto;
import lk.udu.ijse.demo.dto.tm.ItemTM;
import lk.udu.ijse.demo.model.ItemModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ItemController implements Initializable {

    private final ItemModel itemModel = new ItemModel();
    public Label idLabel;
    public TextField priceText;
    public TextField nameText;
    public TextField qtyText;
    public TableView <ItemTM> itemTable;
    public TableColumn <ItemTM,String> idColumn;
    public TableColumn <ItemTM,String> nameColumn;
    public TableColumn <ItemTM,Double> priceColumn;
    public TableColumn <ItemTM,Integer> qtyColumn;

    public void clearTextFields(){
        nameText.setText("");
        priceText.setText("");
        qtyText.setText("");
    }

    public void getAllItems(){
        itemTable.setPlaceholder(new Label("Not Items Yet"));

        idColumn.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        qtyColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        ObservableList<ItemTM> itemTMS = FXCollections.observableArrayList();
        ItemTM itemTM = null;
        try {
            ObservableList<ItemTM> allItems = itemModel.getAllItems();
            itemTMS.add(itemTM);
            itemTable.setItems(allItems);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void savItem(ActionEvent actionEvent) throws SQLException {
        try {
            ItemDto itemDto = new ItemDto(
                    idLabel.getText(),
                    nameText.getText(),
                    Integer.parseInt(qtyText.getText()),
                    Double.parseDouble(priceText.getText()));

            String resp = itemModel.saveItem(itemDto);
            new Alert(Alert.AlertType.INFORMATION,resp).show();
            getAllItems();
            getNextItemID();
            clearTextFields();
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteItem(ActionEvent actionEvent) throws SQLException {
        try {
            String itemID = idLabel.getText();
            String resp = itemModel.deleteItem(itemID);
            new Alert(Alert.AlertType.INFORMATION,resp).show();
            clearTextFields();
            getAllItems();
            getNextItemID();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateItem(ActionEvent actionEvent) throws SQLException {
        try {
            ItemDto itemDto = new ItemDto(
                    idLabel.getText(),
                    nameText.getText(),
                    Integer.parseInt(qtyText.getText()),
                    Double.parseDouble(priceText.getText()));

            String resp = itemModel.updateItem(itemDto);
            new Alert(Alert.AlertType.INFORMATION,resp).show();
            getAllItems();
            clearTextFields();
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchItem(MouseEvent mouseEvent) {
        if (itemTable.getSelectionModel().getSelectedItem() != null) {
            ItemTM itemTM = itemTable.getSelectionModel().getSelectedItem();
            idLabel.setText(itemTM.getItemID());
            nameText.setText(itemTM.getName());
            priceText.setText(Double.toString(itemTM.getPrice()));
            qtyText.setText(Integer.toString(itemTM.getQty()));
        } else {
            new Alert(Alert.AlertType.WARNING, "Select a Item First").show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            getAllItems();
            getNextItemID();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void getNextItemID() throws SQLException {
        try {
            String lastItemID = itemModel.getNextItemID();
            idLabel.setText(lastItemID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
