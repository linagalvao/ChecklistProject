/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lina.checklistproject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Lina
 */
public class Main {
    public static void main(String[]args){
        Checklist checklist = new Checklist("Tobuy");
        checklist.addItem("sugar");
        checklist.addItem("beans");
        checklist.addItem("rice");
        checklist.addItem("pasta");
        
        checklist.checkItem("pasta");
        
        System.out.println(checklist);
        
        checklist.uncheckItem("pasta");
        System.out.println(checklist);
    }
}
