package com.example.ecomerce;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class product {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;

    public int getId(){
        return id.get();
    }
    public String getName(){
        return name.get();
    }
    public Double getPrice(){
        return price.get();
    }
    public product(int id,String name,Double price){
      this.id=new SimpleIntegerProperty(id);
      this.name = new SimpleStringProperty(name);
      this.price = new SimpleDoubleProperty(price);
    }
    public static ObservableList<product> getAllProduct(){
        String list = "select * from products";
        return getProduct(list);
    }
    public static ObservableList<product> getSearchProduct(String search){
        String list = "select * from products where name like '%"+search+"%'";
        return getProduct(list);
    }

    public static ObservableList<product> getProduct(String list){
      DatabaseConnection dbcon = new DatabaseConnection();
        ResultSet result = dbcon.getQueryTable(list);
        ObservableList<product> data = FXCollections.observableArrayList();
        try{
            if(result!=null){
                while(result.next()){
                    data.add(new product(result.getInt("pid"),result.getString("name"),result.getDouble("price")));
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }
}
