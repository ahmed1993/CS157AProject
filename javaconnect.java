/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ahmedsyed
 */
import java.sql.*;
import javax.swing.*;

public class javaconnect {
    Connection conn = null;
    public static Connection ConnecrDb(){
        try{
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/cs157a","root","");
            return conn;
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
            return null;
        }
      
    }
    
    public static void main(String args[]){
        ConnecrDb();
    }
    
}
