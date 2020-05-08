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
import java.util.List;
import java.util.Map;

/**
 *
 * @author Lina
 */
public class ItemDAO {

    private Connect conn;
    private final String LIST_ALL
            = "SELECT   c.id check_id, "
            + "c.name list_name, "
            + "i.id item_id, "
            + "i.name item_name, "
            + "i.checked "
            + "FROM Checklist c LEFT JOIN Item i ON c.id = i.checklist_id "
            + "ORDER BY c.name";
    private final String INSERT = "INSERT INTO Item "
            + "(name, checklist_id) VALUES (?, ?)";
    private final String DELETE = "DELETE FROM Item "
            + "WHERE id = ?";
    private final String UPDATE = "UPDATE Item SET name = ? "
            + "WHERE id = ?";
    private final String CHECK_ITEM_NAME_EXISTS_ALREADY = "SELECT i.name FROM Item i "
            + "WHERE i.name = ? COLLATE NOCASE AND i.checklist_id = ?"; 
    private final String LIST_ITEM_BY_CHECKLIST = "SELECT i.id item_id, i.name item_name, i.checked, i.checklist_id "
            + "FROM Item i "
            + "WHERE i.checklist_id = ? "
            + "ORDER BY i.name";

    public List<Item> listAllItems() {
        conn = new Connect();
        List<Item> items = new ArrayList<>();
        //Item item = null;

        try {
            Statement stmt = conn.getStatement();
            ResultSet rs;
            rs = stmt.executeQuery(LIST_ALL);

            while (rs.next()) {
                int checkId = rs.getInt("check_id");
                int itemId = rs.getInt("item_id");
                String itemName = rs.getString("item_name");
                boolean itemChecked = rs.getBoolean("checked");
                String checklistName = rs.getString("list_name");
                Item item = new Item(itemName);
                item.setId(itemId);
                item.setStatus(itemChecked);
                Checklist checklist = new Checklist(checklistName);
                checklist.setId(checkId);

                item.setChecklist(checklist);
                items.add(item);
            }

            return items;
        } catch (SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());

        } finally {
            conn.closeConnect();
        }
        return null;
    }

    public int delete(int id) throws SQLException {
        conn = new Connect();
        try {
            PreparedStatement pstmt = conn.setPreparedStatement(DELETE);
            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        } finally {
            conn.closeConnect();
        }
    }

    public void insert(Item item) throws SQLException {
        conn = new Connect();
        try {
            PreparedStatement pstmt = conn.setPreparedStatement(INSERT);
            pstmt.setString(1, item.getName());
            pstmt.setInt(2, item.getChecklist().getId());
            pstmt.executeUpdate();
        } finally {
            conn.closeConnect();
        }
    }

    public int updateName(Item item) throws SQLException {
        conn = new Connect();
        try {
            PreparedStatement pstmt = conn.setPreparedStatement(UPDATE);
            pstmt.setString(1, item.getName());
            pstmt.setInt(2, item.getId());
            return pstmt.executeUpdate();
        } finally {
            conn.closeConnect();
        }
    }

    public List<Item> findItemsByChecklistId(int chcklistId) throws SQLException {
        conn = new Connect();
        List<Item> itemsList = new ArrayList<>();

        try {
            PreparedStatement pstmt = conn.setPreparedStatement(LIST_ITEM_BY_CHECKLIST);
            pstmt.setInt(1, chcklistId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int checkId = rs.getInt("checklist_id");
                String itemName = rs.getString("item_name");
                int itemId = rs.getInt("item_id");
                boolean checked = rs.getInt("checked") == 1;
                Item item = new Item(itemName);
                item.setName(itemName);
                item.setId(itemId);
                item.setStatus(checked);
                Checklist ck = new Checklist(null);
                ck.setId(checkId);
                item.setChecklist(ck);
                itemsList.add(item);

            }

            return itemsList;

        } finally {
            conn.closeConnect();
            return itemsList;
        }
    }
    
    public boolean doesItemNameExist (Item item) throws SQLException{
        conn = new Connect();
        try{
            PreparedStatement pstmt = conn.setPreparedStatement(CHECK_ITEM_NAME_EXISTS_ALREADY);
            pstmt.setString(1, item.getName());
            pstmt.setInt(2, item.getChecklist().getId());
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()){
                return true;
            }
        }finally{
            conn.closeConnect();
        }
        return false;
    }

    public static void main(String[] args) {
        ItemDAO i = new ItemDAO();
        //System.out.println(i.findItemsByChecklistId(2).toString().replace(",", ""));
    }
}
