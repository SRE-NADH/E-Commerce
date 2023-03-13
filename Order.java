package com.example.ecomerce;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class Order {
    static TableView<product> orderTable;
    // order the items
    public static boolean placeOrder(customer cust,product prod){
        try{
            String query = "insert into orders(order_id,product_id,status)values("+cust.getId()+","+prod.getId()+",'ordered')";
            DatabaseConnection dbcon  = new DatabaseConnection();
            return dbcon.insertUpdate(query);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    // place multiple orders
    public static int placeMultipleOrder(ObservableList<product> list,customer cust){
        int count=0;
        for(product x:list){
            if(placeOrder(cust,x))
                count++;
        }
        return count;
    }
    public static Pane createTableFromList(ObservableList<product> orderList){
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

        orderTable = new TableView<>();
        orderTable.setItems(orderList);
        orderTable.getColumns().addAll(id,name,price);

        Pane table = new Pane();
        table.getChildren().add(orderTable);
        return table;
    }
    public static Pane getOrders(customer cust){
        String order = "select products.pid,products.name,products.price from orders inner join products on products.pid=orders.product_id where order_id="+cust.getId();
        ObservableList<product> orderlist = product.getProduct(order);
        return createTableFromList(orderlist);
    }

}
