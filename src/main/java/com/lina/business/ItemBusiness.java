/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lina.business;

import com.lina.checklistproject.Checklist;
import com.lina.checklistproject.Item;
import com.lina.myExceptions.CheckListIllegalArgumentException;
import com.lina.dao.ItemDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger; 
import javax.swing.JOptionPane;

/**
 *
 * @author Lina
 */
public class ItemBusiness {

    ItemDAO dao = new ItemDAO();

    public void insert(Item item) throws CheckListIllegalArgumentException, Exception {
        if ((item.getName().isEmpty()) || item.getChecklist().getId() == 0) {
            throw new CheckListIllegalArgumentException("Some data is missing or wrong. Please, check the given data and try again.");
        }
        try {
            boolean result = dao.doesItemNameExist(item);
            if(result){
                throw new Exception("The item is already created in the list!");
            }else{
                dao.insert(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemBusiness.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Unfortunately you couldn't insert in the database!");
        }
    }

    public void update(Item item) throws CheckListIllegalArgumentException, Exception {
        if ((item.getName().isEmpty() || item.getId() == 0)) {
            throw new CheckListIllegalArgumentException("Some data is missing or wrong. Please, check the given data and try again.");
        }
        try {
            if(dao.updateName(item) == 0){
                throw new Exception("It was not possible to update the item!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemBusiness.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Connection did not be done!");
        }
    }

    public List<Item> listItemsByChecklistId(int checklistId) throws CheckListIllegalArgumentException, Exception{
        if (checklistId == 0) {
            throw new CheckListIllegalArgumentException("You should give an id different than 0!");
        }    
        try {
            return dao.findItemsByChecklistId(checklistId);
        } catch (SQLException ex) {
            Logger.getLogger(ItemBusiness.class.getName()).log(Level.SEVERE,null,ex);
            throw new Exception("Connection did not be done!");
        }
    }

    public void delete(int id) throws CheckListIllegalArgumentException, Exception {
        if (id == 0) {
            throw new CheckListIllegalArgumentException("Id given to delete the item is wrong!");
        }
        try {
            if(dao.delete(id) == 0){
                throw new Exception("It was not possible to delete the item!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemBusiness.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Connection did not be done!");
        }
    }

    public static void main(String[] args) {
        ItemBusiness itemB = new ItemBusiness();
        Item item = new Item("cooking");
        item.setChecklist(new Checklist("to solve"));
        item.getChecklist().setId(2);
        
        try {
            itemB.insert(item);
        } catch (CheckListIllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Wrong parameter!", JOptionPane.WARNING_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(null, "Error when inserting an item, please contact Lina", "Error!", JOptionPane.ERROR_MESSAGE);
        } catch (Exception n) {
            JOptionPane.showMessageDialog(null, n.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
}

