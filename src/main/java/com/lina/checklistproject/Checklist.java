/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lina.checklistproject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Lina
 */
public class Checklist {
    private int id;
    private String name;
    private final List<Item> items;
    private final List<Checklist> listChecklist = new ArrayList<>();

    public Checklist(String name) {
        this.name = name;
        this.items = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void addItem(Item item){
        items.add(item);
        
    }
    public void addItem(String item){
        addItem(new Item(item));
    }
    
    public String listUncheckedItems(){
        StringBuilder listReturn = new StringBuilder();
        items.forEach(item ->  {
            if(!item.isStatus()){
                listReturn.append(item.getName()+" - "+item.isStatus()+"\n");
            }
        });
        return listReturn.toString();
    }
    
    public String listItems(){
        StringBuilder listReturn = new StringBuilder();
        items.forEach(item ->  {
            listReturn.append(item.getName()+" - "+item.isStatus()+"\n");
        });
        return listReturn.toString();
    }
    
    public List<Checklist> listChecklist(){
        List<Checklist> listReturn = new ArrayList<>();
        listChecklist.forEach(lchecklist -> listReturn.add(lchecklist));
        return listReturn;
    }
    

    public void checkItem(String i){
        items.forEach(item -> {
            if(item.getName().equals(i)){
                item.setStatus(true);
            }
        });
    }
    
    public void uncheckItem(String i){
        items.forEach(item -> {
            if(item.getName().equals(i)){
                item.setStatus(false);
            }
        });
    }

    public void removeItems(int index){
        items.remove(index);
    }

    public boolean addAllItems(Collection<? extends Item> c) {
        return items.addAll(c);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.id;
        hash = 41 * hash + Objects.hashCode(this.name);
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
        final Checklist other = (Checklist) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    
    
    @Override
    public String toString(){
        return "List: " + this.getName() + "\nItems:\n" + listUncheckedItems() + "Checked:\n";
                //+ listCheckedItems();
    }
}
