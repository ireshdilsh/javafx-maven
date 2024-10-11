package lk.udu.ijse.demo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.udu.ijse.demo.db.DBConnection;
import lk.udu.ijse.demo.dto.ItemDto;
import lk.udu.ijse.demo.dto.tm.ItemTM;
import lk.udu.ijse.demo.util.CrudUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemModel {
    public String getNextItemID() throws SQLException {
        try {
            String query = "SELECT item_id FROM item ORDER BY item_id DESC LIMIT 1";
            ResultSet resultSet = CrudUtils.executeCrud(query);
            if (resultSet.next()) {
                String lastID = resultSet.getString(1);
                String subString = lastID.substring(1);
                int tempID = Integer.parseInt(subString);
                tempID++;
                return String.format("C%03d", tempID);
            }
            return "C001";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public String saveItem(ItemDto itemDto) throws SQLException {
        String querry = "insert into item values(?,?,?,?)";
        Boolean b  = CrudUtils.executeCrud(querry,itemDto.getItemID(),itemDto.getName(),itemDto.getQty(),itemDto.getPrice());
        return b == Boolean.TRUE ? "Success" : "Fail";
    }

    public String updateItem(ItemDto itemDto) throws SQLException {
         String querry = "update item set name = ?,quantity = ?,price = ? where item_id = ?";
         Boolean b = CrudUtils.executeCrud(querry,itemDto.getName(),itemDto.getQty(),itemDto.getPrice(),itemDto.getItemID());
         return b == Boolean.TRUE ? "Success" : "Fail";
    }

    public String deleteItem(String itemID) throws SQLException {
        String querry = "delete from item where item_id = ?";
        Boolean b = CrudUtils.executeCrud(querry,itemID);
        return b == Boolean.TRUE ? "Success" : "Fail";
    }

    public ObservableList<ItemTM> getAllItems() throws SQLException {
        try {
            String querry = "select * from item";
            ResultSet resultSet = CrudUtils.executeCrud(querry);

            ArrayList<ItemTM>itemTMS = new ArrayList<>();

            while (resultSet.next()){
                 itemTMS.add(new ItemTM(
                         resultSet.getString(1),
                         resultSet.getString(2),
                         resultSet.getInt(3),
                         resultSet.getDouble(4)
                 ));
            }
            return FXCollections.observableArrayList(itemTMS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
