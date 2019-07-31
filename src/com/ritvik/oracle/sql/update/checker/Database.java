/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ritvik.oracle.sql.update.checker;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.*;
import javax.swing.*;

/**
 *
 * @author ritvikc
 */
public class Database {
    private static String filename;

    public Database() {
        generateFile();
    }
    
    void add(String entry, boolean flag){
        PrintWriter out = null;
        try {
            generateFile();
            out = new PrintWriter(new BufferedWriter(new FileWriter(filename, flag)));
            out.println(entry);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
        }
    }
     
    void remove(String entry){
        BufferedReader r = null;
        try {
            generateFile();
            ArrayList<String> list = new ArrayList<String>();
            r = new BufferedReader(new FileReader(new File(filename)));
            String line = "";
            list.add("");
            while(line != null){
                line = r.readLine();
                if(line!=null && !line.startsWith(entry)){
                    list.add(line);
                }
            }   
            add("",false);
            for(String db : list){
                if(db!=null && !db.isEmpty()){
                    String[] i = db.split("=");
                    add(db, true);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                r.close();
            } catch (IOException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    ArrayList<String> get(){
        BufferedReader r = null;
        ArrayList<String> list = new ArrayList<String>();
        try {
            generateFile();            
            r = new BufferedReader(new FileReader(new File(filename)));
            String line = "";
            while(line != null){
                line = r.readLine();
                if(line !=null){
                    list.add(line);
                }
            }   
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                r.close();
            } catch (IOException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
    
    String get(String entry){
        String toReturn = null;
        BufferedReader r = null;
        try {
            generateFile();            
            r = new BufferedReader(new FileReader(new File(filename)));
            String line = "";
            while(line != null){
                line = r.readLine();
                if(line !=null && line.startsWith(entry)){
                    toReturn=line;
                    break;
                }
            }   
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                r.close();
            } catch (IOException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return toReturn;
    }
    
    void generateFile(){
        filename = System.getProperty("user.home")+"\\.kavach.info";
        File f = new File(filename);
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,"Unable to Create File : "+f.getAbsolutePath(),"File Problem",JOptionPane.ERROR_MESSAGE);
            }
        }
    }


}
