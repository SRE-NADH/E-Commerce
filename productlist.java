package com.example.ecomerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class productlist {
    public static TableView<product> producttable;

    public Pane getAllProducts(){
        ObservableList<product> data2 = product.getAllProduct();
        return createTableFromList(data2);
    }
    public Pane getSearchProducts(String search){
        ObservableList<product> data2 = product.getSearchProduct(search);
        return createTableFromList(data2);
    }
    public static Pane createTableFromList(ObservableList<product> list){
        TableColumn id = new TableColumn("id");
        id.setCellValueFactory(new PropertyValueFactory<product,Integer>("id"));

        TableColumn name = new TableColumn("name");
        name.setCellValueFactory(new PropertyValueFactory<product,String>("name"));

        TableColumn price = new TableColumn("price");
        price.setCellValueFactory(new PropertyValueFactory<product,Double>("price"));

//        ObservableList<product> data = FXCollections.observableArrayList();
//        data.addAll(new product(123,"laptop",(double)235.5),
//                new product(234,"mobile",(double)456.8)
      //  );
        //ObservableList<product> data2 = product.getAllProduct();

        producttable = new TableView<>();
        producttable.setItems(list);
        producttable.getColumns().addAll(id,name,price);

        Pane table = new Pane();
        table.getChildren().add(producttable);
        return table;
    }
    public static Pane cartItemsIn(ObservableList<product> list){
        return createTableFromList(list);
    }
    public static product getselectedProduct(){
        // getting selected Item
        return producttable.getSelectionModel().getSelectedItem();
    }
}
