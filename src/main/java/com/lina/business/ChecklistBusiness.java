/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lina.business;

import com.lina.checklistproject.Checklist;
import com.lina.dao.ChecklistDAO;
import com.lina.myExceptions.CheckListIllegalArgumentException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Lina
 */
public class ChecklistBusiness {

    private ChecklistDAO dao;

    public ChecklistBusiness() {
        this(new ChecklistDAO());
    }

    ChecklistBusiness(ChecklistDAO dao) {
        this.dao = dao;
    }

    public void insert(String checklist) throws CheckListIllegalArgumentException, Exception {
        insert(new Checklist(checklist));
    }
    
    public void insert(Checklist checklist) throws CheckListIllegalArgumentException, Exception {
        if (checklist==null || checklist.getName().equals("")) {
            throw new CheckListIllegalArgumentException("You should inform the checklist's name!");
        }
        try {
            if(dao.doesChecklistNameExist(checklist)){
                throw new Exception("The item is already created in the list!");
            }else{
                dao.insert(checklist);
            }
        } catch (SQLException e) {
            Logger.getLogger(ChecklistBusiness.class.getName()).log(Level.SEVERE, null, e);
            throw new Exception("Connection wasn't done!");
        }
    }

    public void update(Checklist checklist) throws CheckListIllegalArgumentException, Exception {
        if (checklist==null || checklist.getName().equals("")) {
            throw new CheckListIllegalArgumentException("You should inform the checklist's name!");
        }
        try {
            if(dao.doesChecklistNameExist(checklist)){
                throw new Exception("The item is already created in the list!");
            }else{
                if(dao.update(checklist)==0){
                    throw new Exception("It was not possible to update the checklist!");
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(ChecklistBusiness.class.getName()).log(Level.SEVERE, null, e);
            throw new Exception("Connection wasn't done!");
        }
    }

    public List<Checklist> listAllChecklist() throws Exception {
        try {
            return dao.listAll();
        } catch (SQLException e) {
            Logger.getLogger(ItemBusiness.class.getName()).log(Level.SEVERE, null, e);
            throw new Exception("Connection wasn't done!");
        }
    }

    public void delete(int id) throws CheckListIllegalArgumentException, Exception {
        if (id == 0) {
            throw new CheckListIllegalArgumentException("You should inform checklist's id!");
        }    
        try {
            if(dao.delete(id) == 0){
                throw new Exception("It was not possible to delete the checklist!");
            }
        }catch (SQLException e) {
            Logger.getLogger(ChecklistBusiness.class.getName()).log(Level.SEVERE, null, e);
            throw new Exception("Connection wasn't done!");
        }
    }

    public static void main(String[] args) {
        ChecklistBusiness ckb = new ChecklistBusiness();
        Checklist ck = new Checklist("To do");
        try {
            ckb.insert(ck);
            //ckb.delete(5);
        } catch (CheckListIllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Wrong parameter!", JOptionPane.WARNING_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(null, "Error when inserting an item, please contact Lina", "Error!", JOptionPane.ERROR_MESSAGE);
        } catch (Exception n) {
            JOptionPane.showMessageDialog(null, n.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
}
