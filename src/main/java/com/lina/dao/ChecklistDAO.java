/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lina.dao;

import com.lina.checklistproject.Checklist;
import com.lina.checklistproject.Item;
import com.lina.connection.Connect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Lina
 */
public class ChecklistDAO {

    private Connect conn;
    private final String QUERY_ALL = "SELECT   c.id check_id, "
                                            + "c.name list_name, "
                                            + "i.id item_id, "
                                            + "i.name item_name, "
                                            + "i.checked "
                                     + "FROM Checklist c LEFT JOIN Item i ON c.id = i.checklist_id "
                                     + "ORDER BY c.name";
    private final String INSERT = "INSERT INTO Checklist(name) "
                                            + "VALUES (?)";
    private final String DELETE = "DELETE FROM Checklist " 
                                            + "WHERE id = ?";
    private final String UPDATE = "UPDATE Checklist SET name = ? "
                                            + "WHERE id= ?";
    private final String CHECK_CHECKLIST_NAME_EXISTS_ALREADY = "SELECT c.name FROM Checklist c "
                                     + "WHERE c.name = ? COLLATE NOCASE";
    
    public List<Checklist> listAll() throws SQLException{
        conn = new Connect();
        List<Item> listAll = new ArrayList<>();
        try {
            Statement stmt = conn.getStatement();
            ResultSet rs;

            rs = stmt.executeQuery(QUERY_ALL);
            //Item item = new Item("");
            long timeStart = new Date().getTime();
            while (rs.next()) {
                int checkId = rs.getInt("check_id");
                String listName = rs.getString("list_name");
                //iniciar montagem do item. primeiro passo pegar resultado da consulta
                int itemId = rs.getInt("item_id");
                boolean checked = rs.getInt("checked") == 1;
                String itemName = rs.getString("item_name");
                Item item = new Item(itemName);
                item.setId(itemId);
                item.setStatus(checked);
                Checklist ck = new Checklist(listName);
                ck.setId(checkId);
                
                item.setChecklist(ck);
                listAll.add(item);
            }
            //Agrupando os item por checklist
            Map<Checklist, List<Item>> result =
                listAll.stream().collect(
                        Collectors.groupingBy(Item::getChecklist));
            
            List<Checklist> listCheckList = new ArrayList<>();//Criando a lista que será retornada
            
            result.forEach((key, items)-> {//iterando todo mapa agrupado por checklist
                items.removeIf(e-> e.getName()==null);//removendo os items nulos
                key.addAllItems(items);//adicionado lista de items sem os valores nulos
                listCheckList.add(key);//adicionando a lista que será retornada
                        }
                    );
            listCheckList.sort(new Comparator<Checklist>() {
                @Override
                public int compare(Checklist o1, Checklist o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            
            long timeEnd = new Date().getTime();
            System.out.println("total time: "+(timeEnd-timeStart));
            return listCheckList;

        } finally {
            conn.closeConnect();
            return null;
        }
    }
    
    public boolean doesChecklistNameExist(Checklist checklist) throws SQLException{
        conn = new Connect();
        
        try{
            PreparedStatement pstmt = conn.setPreparedStatement(CHECK_CHECKLIST_NAME_EXISTS_ALREADY);
            pstmt.setString(1, checklist.getName());
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()){
                return true;
            }
        }finally{
            conn.closeConnect();
        }
        return false;
        
    }
    
    public void insert (Checklist checklist)throws SQLException{
        conn = new Connect();
        try{
            PreparedStatement stmt =  conn.setPreparedStatement(INSERT); 
            stmt.setString(1, checklist.getName());
            stmt.executeUpdate();
        } finally{
            conn.closeConnect();
        }
    }
    
    public int update (Checklist checklist) throws SQLException{
        conn = new Connect();
        try{
           PreparedStatement pstmt = conn.setPreparedStatement(UPDATE);
           pstmt.setString(1, checklist.getName());
           pstmt.setInt(2, checklist.getId());
           return pstmt.executeUpdate();
        } finally{
            conn.closeConnect();
        }
    }
    
    public int delete (int id) throws SQLException{
        conn = new Connect();
        try{
            PreparedStatement stmt =  conn.setPreparedStatement(DELETE); 
            stmt.setInt(1, id);
            return stmt.executeUpdate();
        } finally{
            conn.closeConnect();
        }
    }
    
    public static void main(String[] args) {
        ChecklistDAO c = new ChecklistDAO();
    
        //System.out.println(c.listAll());
    }
}
 