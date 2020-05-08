/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lina.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sqlite.SQLiteConfig;

/**
 *
 * @author Lina
 */
public class Connect {

    private Connection conn;
    
    private Connection connect(){
        
        try{
            String url = "jdbc:sqlite:C:\\Users\\Lina\\Desktop\\ChecklistDB";
            SQLiteConfig config = new SQLiteConfig();  
            config.enforceForeignKeys(true);  
            conn = DriverManager.getConnection(url,config.toProperties());
            System.out.println("Connection to SQLite has been established.");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return conn;
    } 
    
    public Statement getStatement(){
        Statement st = null;
        try {
            st = connect().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return st;
    }
    
    public PreparedStatement setPreparedStatement(String sql){
        PreparedStatement ps = null;
        try{
            ps = connect().prepareStatement(sql);
        }catch(SQLException ex){
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ps;
    }
    
    public void closeConnect(){
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        Connect con = new Connect();
        con.connect();
        con.closeConnect();
    }

}