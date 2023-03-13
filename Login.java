package com.example.ecomerce;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.ResultSet;

public class Login {

    private static byte[] getSha(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(password.getBytes(StandardCharsets.UTF_8));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
     private static String getEncript(String password){
        try{
            BigInteger num = new BigInteger(1,getSha(password));
            StringBuilder str = new StringBuilder(num.toString(16));
            return str.toString();
        }
        catch(Exception e){
           e.printStackTrace();
        }
        return null;
     }
    public static customer customerLogin(String email, String password){
        String encrpass =getEncript(password);
        String query = "select * from customer where email='"+email+"' and password='"+encrpass +"'";
        DatabaseConnection dcon = new DatabaseConnection();
        try {
            ResultSet result = dcon.getQueryTable(query);
            if(result!=null && result.next()){
                return new customer(result.getInt("cid"),result.getString("name"),result.getString("email"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
      return null;
    }
    // for sign up
    public static boolean signUp(String Name, String Password,String Email,int Mobile){
        String newPassword=getEncript(Password);
        String query = "INSERT INTO customer(name,email,password,mobile) VALUES ('"+Name+"','"+Email+"','"+newPassword+"',"+Mobile+")";
        try {
            DatabaseConnection dcon = new DatabaseConnection();
            return dcon.insertUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
//    public static void main(String[] args) {
//        System.out.println(customerLogin("sreenadh@gmail.com","1234"));
//        System.out.println(getEncript("aserdd"));
//    }
}

