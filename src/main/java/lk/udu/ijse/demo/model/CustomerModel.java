package lk.udu.ijse.demo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.udu.ijse.demo.db.DBConnection;
import lk.udu.ijse.demo.dto.CustomerDto;
import lk.udu.ijse.demo.dto.tm.CustomerTM;
import lk.udu.ijse.demo.util.CrudUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {

    public String saveCustomer(CustomerDto customerDto) throws SQLException {
        String query = "INSERT INTO customer VALUES (?,?,?,?,?)";
        Boolean b = CrudUtils.executeCrud(query, customerDto.getCustID(),customerDto.getCustName(),customerDto.getCustNIC(),customerDto.getCustEmail(),customerDto.getCustPhone());
        return b == Boolean.TRUE ? "Success" : "Fail";
    }

    public String updateCustomer(CustomerDto customerDto) throws SQLException {
        String query = "UPDATE customer SET name=?, nic=?, email=?, phone=? WHERE customer_id=?";
        Boolean b = CrudUtils.executeCrud(query,customerDto.getCustName(),customerDto.getCustNIC(),customerDto.getCustEmail(),customerDto.getCustPhone(),customerDto.getCustID());
        return b == Boolean.TRUE ? "Success" : "Fail";
    }

    public String deleteCustomer(String deleteID) throws SQLException {

        String query = "DELETE FROM customer WHERE customer_id=?";
        Boolean b = CrudUtils.executeCrud(query,deleteID);
        return b == Boolean.TRUE ? "Success" : "Fail";
    }

    public ObservableList<CustomerTM> getAllCustomers() throws SQLException {
        String query = "SELECT * FROM customer";
        ResultSet resultSet = CrudUtils.executeCrud(query);

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
        String query = "SELECT customer_id FROM customer ORDER BY customer_id DESC LIMIT 1";
        ResultSet resultSet = CrudUtils.executeCrud(query);
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