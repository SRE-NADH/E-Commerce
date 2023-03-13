package com.example.ecomerce;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import java.io.IOException;


public class Ecommerce extends Application {

    Button addTocart = new Button("Add to cart");
    productlist prod = new productlist();
    Pane bodypane;
    Pane footerPane;
    private final int width=500,height =500,boarder=50;

    ObservableList<product> cartList = FXCollections.observableArrayList();
    Button signIn = new Button("sign in");
    Button placeOrder = new Button("place order");
    Button Orderbutton = new Button("orders");
    Label signLab = new Label("welcome customer");
    customer loggedIn = null;
    Button buynow = new Button("Buy now");
    public void addItemsToCart(product prod){
        if(cartList.contains(prod))
            return;
        cartList.add(prod);
       // System.out.println(cartList.stream().count());
    }
    // set dialogue boxes for successfull orders
    public void showDialogue(String message){
        //Creating a dialog
        Dialog<String> dialog = new Dialog<String>();
        //Setting the title
        dialog.setTitle("order status");
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        //Setting the content of the dialog
        dialog.setContentText(message);
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
    }
    // creating the footer bar
    private GridPane footerBar(){
        // action on buy now button
        buynow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                product tmp = productlist.getselectedProduct();
                boolean getStatus = false;
                if(tmp !=null && loggedIn!=null){
                   getStatus=Order.placeOrder(loggedIn,tmp);
                }
                else if(tmp==null){
                    showDialogue("select the item");
                }
                else if(loggedIn==null){
                    showDialogue("you need to sign in");
                }
                if(getStatus==true) {
                    showDialogue("order successfull");
                }
                else{

                }
            }
        });

        addTocart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                product pro = productlist.getselectedProduct();
                if(pro==null){
                    showDialogue("select item");
                }
                addItemsToCart(pro);
            }
        });
         GridPane footer = new GridPane();
         footer.setTranslateY(boarder+height);
         footer.setHgap(20);
         footer.add(buynow,0,0);
         footer.add(addTocart,1,0);
         //footer.add(placeOrder,2,0);
         return footer;
    }
    // creating header bar and fuctionalities
    private GridPane headerBar(){
        GridPane header = new GridPane();
        TextField search = new TextField();
        Button b = new Button("search");
        Button cart = new Button("cart");
        Orderbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(loggedIn!=null) {
                    bodypane.getChildren().clear();
                    bodypane.getChildren().add(Order.getOrders(loggedIn));
                }
                else{
                    showDialogue("you need to sign in");
                }
            }
        });
        // action event when clicking search button
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                addTocart.setVisible(true);
                buynow.setVisible(true);
                 bodypane.getChildren().clear();
                String sr =search.getText();
                bodypane.getChildren().add(prod.getSearchProducts(sr));
            }
        });
        // action on sign in button
        signIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodypane.getChildren().clear();
                bodypane.getChildren().add(loginpage());
                addTocart.setVisible(false);
                buynow.setVisible(false);
            }
        });
        // action on cart button
        cart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodypane.getChildren().clear();
                addTocart.setVisible(false);
                buynow.setVisible(false);
                // action on placeorder button
                placeOrder.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        int ordercount = 0;
                        if(!cartList.isEmpty()&& loggedIn!=null){
                            ordercount=Order.placeMultipleOrder(cartList,loggedIn);
                        }
                        if(loggedIn==null){
                            showDialogue("you need to sign in");
                        }
                        if(ordercount>0) {
                            showDialogue(ordercount+"orders successfull");
                        }
                        else{

                        }
                    }
                });
                placeOrder.setLayoutX(250);
                placeOrder.setLayoutY(250);
                bodypane.getChildren().addAll(productlist.cartItemsIn(cartList),placeOrder);


            }
        });
        header.setHgap(10);
        header.add(search,0,0);
        header.add(b,1,0);
        header.add(signIn,2,0);
        header.add(signLab,3,0);
        header.add(cart,4,0);
        header.add(Orderbutton,5,0);
        return header;
    }
    private GridPane loginpage(){

       Button creatNew  = new Button("create new");
        Label userlab= new Label("username");// email box label
        Label passlab = new Label("password");// password label
        TextField nametext = new TextField(); // text field for name
        nametext.setPromptText("Enter the username");
        PasswordField pastext = new PasswordField();// text field for password
        pastext.setPromptText("Enter the password");
        Button login = new Button("Login"); // login button
        Label message = new Label("Login-message"); // login message label
       // acton on pressing login button
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = nametext.getText();
                String pass =pastext.getText();
                loggedIn = Login.customerLogin(name,pass);
                if(loggedIn!=null){
                     message.setText("login successful");
                     signLab.setText("welcome "+loggedIn.getName());
                }
                else{
                    message.setText("login failed");
                }
            }
        });
         //action on sign up,for create new account
        creatNew.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodypane.getChildren().clear();
                bodypane.getChildren().add(signupPage());
                addTocart.setVisible(false);
                buynow.setVisible(false);
            }
        });

        GridPane logpane = new GridPane();
        logpane.setTranslateY(50);
        logpane.setHgap(10);
        logpane.setVgap(10);
        logpane.add(userlab,0,0);
        logpane.add(passlab,0,1);
        logpane.add(nametext,1,0);
        logpane.add(pastext,1,1);
        logpane.add(login,0,2);
        logpane.add(message,1,2);
        logpane.add(creatNew,1,4);
        return logpane;
    }
    public Pane signupPage(){
        Button create = new Button("create");
        Label userlab= new Label("Name");// name box label
        Label passlab = new Label("password");// password label
        TextField nametext = new TextField(); // text field for name
        nametext.setPromptText("Enter the Name");
        PasswordField pastext = new PasswordField();// text field for password
        pastext.setPromptText("Enter the password");
          Label mobile = new Label("mobile");
          TextField mobtxt = new TextField(); // text field for mobile
          mobtxt.setPromptText("Enter mobile");
        Label mail = new Label("email(username)");
        TextField mailtext = new TextField(); // text fie
        mailtext.setPromptText("Enter your email");
        GridPane signup = new GridPane();
        create.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String nam = nametext.getText();
                String pass = pastext.getText();
                String emai = mailtext.getText();
                String mob = mobtxt.getText();
                int mobint = Integer.valueOf(mob);
                boolean flag = Login.signUp(nam,pass,emai,mobint);
                if(flag==true){
                    showDialogue("new account created");
                }
            }
        });

        signup.setTranslateY(50);
        signup.setHgap(10);
        signup.setVgap(10);
        signup.add(userlab,0,0);
        signup.add(passlab,0,1);
        signup.add(nametext,1,0);
        signup.add(pastext,1,1);
        signup.add(mobile,0,2);
        signup.add(mobtxt,1,2);
        signup.add(mail,0,3);
        signup.add(mailtext,1,3);
        signup.add(create,0,4);
        return signup;
   }

    private Pane createContent(){
      Pane root = new Pane();
      root.setPrefSize(width,height+(2*boarder));
      bodypane = new Pane();
      bodypane.setPrefSize(width,height);
      bodypane.setTranslateY(boarder);
      bodypane.setTranslateX(50);
        footerPane = new Pane();
        footerPane.setTranslateY(boarder+height);
        footerPane.getChildren().add(footerBar());
      bodypane.getChildren().addAll(prod.getAllProducts());
      root.getChildren().addAll(headerBar(),
              //loginpage(),prod.getAllProducts()
              bodypane,footerBar());
      return root;
    }
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(Ecommerce.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());
        stage.setTitle("ECOMMERCE");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}