package com.example.ecomerce;

import java.sql.*;

public class DatabaseConnection {
    String dburl = "jdbc:mysql://localhost:3306/ecomm";
    String username = "root";
    String password = "sreenadh@2000";

    private Statement getStatement() {
        try {
            Connection conn = DriverManager.getConnection(dburl, username, password);
            return conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getQueryTable(String query){
        Statement statement= getStatement();
        try{
            return statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
      return null;
    }
    public boolean insertUpdate(String query){
        Statement statement= getStatement();
        try{
            statement.executeUpdate(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        String query = "select * from products";
        DatabaseConnection dcon = new DatabaseConnection();
        ResultSet resultSet = dcon.getQueryTable(query);
        if(resultSet!=null){
            System.out.println("connected to database");
        }
    }
}
