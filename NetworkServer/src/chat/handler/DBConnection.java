package chat.handler;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DBConnection{
    private static Connection connection;
    private static Statement stmt;
    private static DBConnection instance = null;
    private static PreparedStatement psGetNickname;


//    public static synchronized DBConnection getInstance() throws SQLException {
//        if (instance == null)
//            instance = new DBConnection();
//        return instance;
//    }

//    public List<Data> getAllData(){
//        try (Statement statement = this.connection.createStatement()){
//            List<Data> data = new ArrayList<Data>();
//
//            ResultSet resultSet = statement.executeQuery("SELECT login,password FROM users");
//            while(resultSet.next()){
//                data.add(new Data(resultSet.getString("login"), resultSet.getString("password")));
//
//            }return data;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return Collections.emptyList();
//        }
//
//    }





    public static boolean connect(){

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Author.db");
            prepareAllStatements();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException  e) {
            e.printStackTrace();
            return false;
        }
    }
    private static void prepareAllStatements() throws SQLException{
     psGetNickname = connection.prepareStatement("SELECT nickname FROM users WHERE login = ? AND password = ?;");




    }


    public static String getNicknameByLoginAndPassword(String login, String password){
        String nickname = null;
        try{
            psGetNickname.setString(1,login);
            psGetNickname.setString(2, password);
            ResultSet rs = psGetNickname.executeQuery();
            if(rs.next()){
                nickname = rs.getString(1);
            }
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return nickname;
    }

    private static void disconnect() {

    }

//    public void equalsOrNot(){
//        getAllData();
//        String login = loginField.getText();
//        String password = passField.getText();
//        try (Statement statement = this.connection.createStatement()) {
//            ResultSet resultSet = statement.executeQuery("SELECT login,password FROM users");
//            while(resultSet.next()){
//                getAllData();
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//
//
//    }

}
