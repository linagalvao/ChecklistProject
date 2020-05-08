/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lina.checklistproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Lina
 */
public class Item {
    private int id;
    private String name;
    private boolean status;
    private Checklist checklist;
     
    private final List<Item> items = new ArrayList<>();
    
    public Item(String name) {
        this.name = name;
        this.status = false;
    }
    
    public Item(String name, Checklist checklist) {
        this.name = name;
        this.status = false;
        this.setChecklist(checklist);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Checklist getChecklist() {
        return checklist;
    }

    public void setChecklist(Checklist checklist) {
        this.checklist = checklist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.id;
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + (this.status ? 1 : 0);
        hash = 23 * hash + Objects.hashCode(this.checklist);
        hash = 23 * hash + Objects.hashCode(this.items);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.checklist, other.checklist)) {
            return false;
        }
        if (!Objects.equals(this.items, other.items)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString(){
       return getName() + " - " + isStatus() + "\n";
    }
}
