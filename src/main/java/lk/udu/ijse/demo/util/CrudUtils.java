package lk.udu.ijse.demo.util;

import lk.udu.ijse.demo.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CrudUtils {
    public static <T>T executeCrud(String querry,Object...obj){
        try {

            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(querry);

            for(int i=0;i<obj.length;i++){
                preparedStatement.setObject(i+1,obj[i]);
            }
            // check querry is executeUpdate or executeQuerry.
            if (querry.startsWith("select") || querry.startsWith("SELECT")){
                ResultSet resultSet = preparedStatement.executeQuery();
                return (T) resultSet;
            }else{
                int i = preparedStatement.executeUpdate();
                boolean isSave = i > 0;
                return (T)((Boolean)isSave);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
