package lk.udu.ijse.demo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.udu.ijse.demo.db.DBConnection;
import lk.udu.ijse.demo.dto.CustomerDto;
import lk.udu.ijse.demo.dto.tm.CustomerTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {

    public String saveCustomer(CustomerDto customerDto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "INSERT INTO customer VALUES (?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(query);
        try {
            pstm.setString(1, customerDto.getCustID());
            pstm.setString(2, customerDto.getCustName());
            pstm.setString(3, customerDto.getCustNIC());
            pstm.setString(4, customerDto.getCustEmail());
            pstm.setString(5, customerDto.getCustPhone());
            return pstm.executeUpdate() > 0 ? "Saved" : "Not Saved";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String updateCustomer(CustomerDto customerDto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "UPDATE customer SET name=?, nic=?, email=?, phone=? WHERE customer_id=?";
        PreparedStatement pstm = connection.prepareStatement(query);
        try {
            pstm.setString(1, customerDto.getCustName());
            pstm.setString(2, customerDto.getCustNIC());
            pstm.setString(3, customerDto.getCustEmail());
            pstm.setString(4, customerDto.getCustPhone());
            pstm.setString(5, customerDto.getCustID());

            return pstm.executeUpdate() > 0 ? "Updated" : "Not Updated";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String deleteCustomer(String deleteID) {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "DELETE FROM customer WHERE customer_id=?";
        try {
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setString(1, deleteID);
            return pstm.executeUpdate() > 0 ? "Deleted" : "Not Deleted";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<CustomerTM> getAllCustomers() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "SELECT * FROM customer";
        PreparedStatement pstm = connection.prepareStatement(query);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<CustomerTM> customerList = new ArrayList<>();

        while (resultSet.next()) {
            customerList.add(new CustomerTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return FXCollections.observableArrayList(customerList);
    }

    public String getNextCustomerID() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "SELECT customer_id FROM customer ORDER BY customer_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(query);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String lastID = resultSet.getString(1);
            String subString = lastID.substring(1);
            int tempID = Integer.parseInt(subString);
            tempID++;
            return String.format("C%03d", tempID);
        }
        return null;
    }

}