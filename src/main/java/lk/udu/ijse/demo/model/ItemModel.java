package lk.udu.ijse.demo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.udu.ijse.demo.db.DBConnection;
import lk.udu.ijse.demo.dto.ItemDto;
import lk.udu.ijse.demo.dto.tm.ItemTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemModel {
    public String getNextItemID() throws SQLException {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String query = "SELECT item_id FROM item ORDER BY item_id DESC LIMIT 1";
            PreparedStatement pstm = connection.prepareStatement(query);
            ResultSet resultSet = pstm.executeQuery();
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
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String querry = "insert into item values(?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(querry);

            preparedStatement.setString(1,itemDto.getItemID());
            preparedStatement.setString(2,itemDto.getName());
            preparedStatement.setInt(3,itemDto.getQty());
            preparedStatement.setDouble(4,itemDto.getPrice());

            return preparedStatement.executeUpdate() > 0 ? "Item Added Success!" : "Something Fail";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String updateItem(ItemDto itemDto) throws SQLException {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String querry = "update item set name = ?,quantity = ?,price = ? where item_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(querry);

            preparedStatement.setString(1,itemDto.getName());
            preparedStatement.setInt(2,itemDto.getQty());
            preparedStatement.setDouble(3,itemDto.getPrice());
            preparedStatement.setString(4,itemDto.getItemID());

            return preparedStatement.executeUpdate() > 0 ? "Item Updated Success!" : "Something Fail";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String deleteItem(String itemID) throws SQLException {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String querry = "delete from item where item_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(querry);
            preparedStatement.setString(1,itemID);

            return preparedStatement.executeUpdate() > 0 ? "Delete Success!" : "Something Went Wrong";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<ItemTM> getAllItems() throws SQLException {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String querry = "select * from item";
            PreparedStatement preparedStatement = connection.prepareStatement(querry);
            ResultSet resultSet = preparedStatement.executeQuery();

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
