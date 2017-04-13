/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storemanagment;

import java.awt.Color;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author kimani kogi
 */
public class Methods {
    
     public Connection getConnection()
  {
      //for client server situations ,unomment the following line getpath();
      //then comment line 103=path="localhost";
    //getPath();
    Connection con = null;
    String path="";
    try
    {
   // String path;
   // String db=":3306/inua";
   String db=":3306/schoolstore?autoReeconnect=true&useSSL=false";
   String jdbc="jdbc:mysql://";
   String user="root";
   String pass="123ERYcog.";
   //for client server instance comment next line path =localhost
   path=pathselector();
   String dbp=(jdbc+path+db);
        
      con = DriverManager.getConnection(dbp,user,pass);
     // con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "123ERYcog.");
    }
    catch (Exception ex)
    {
        JOptionPane.showMessageDialog(null, "localhost\n"+path+ex);
      System.out.println(ex.getMessage());
    }
    return con;
  }
      public int executeSQlQuery(String query, String message)
  {
      int a=0;
 //  methods m=new methods();
        Connection con = getConnection();
    try
    {
      Statement st = con.createStatement();
      if (st.executeUpdate(query) == 1)
      {
        a=1;
        JOptionPane.showMessageDialog(null,  message + " Succefully");
      }
      else
      {
        JOptionPane.showMessageDialog(null, message);
      }
      st.close();
      con.close();  
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return a;
  }
       public int executeSQlQueryN(String query)
  {
      int a=0;
 //  methods m=new methods();
        Connection con = getConnection();
    try
    {
      Statement st = con.createStatement();
      if (st.executeUpdate(query) == 1)
      {
        a=1;
       
      }
      else
      {
        a=0;
      }
      st.close();
      con.close();  
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return a;
  }
      
      public String setIconImage() {
       String image="store_icon.png";
       return image;
    }
public void addToOrgAccount(Double amount,String Description,String mode){
    String xro="0";
     String query = "INSERT INTO orgaccount("
             + "`cashin`,"
             + "`cashout`,"
             
             + "`description`"
             + ",`mode`"
             + ",`date`)"
             + " VALUES ("
             + "'" + amount + "'"
             + ",'" + xro+ "'"
             + ",'" + Description+ "'"
             + ",'" + mode+ "'"
            + ",now())";
    
    executeSQlQueryN(query);
    switch (mode) {
             case "Cash":
                 
              Double available=Double.valueOf(getDetails("cashinoffice"));
                 
              Double val=available+amount;
                  // JOptionPane.showMessageDialog(null, val);
               String c = "UPDATE `account` SET `cashinoffice`='"+val+"'";
               
              //if( 
                      executeSQlQueryN(c);
                      //==1){
                  
                //  JOptionPane.showMessageDialog(null, c);
                  
             // }
               
                 break;
             case "Cheque":
              Double available1=Double.valueOf(getDetails("bank"));
                 
              Double val1=available1+amount;
                 
              String c1 = "UPDATE `account` SET `bank`='"+val1+"'";
              executeSQlQueryN(c1);
                 break;
             case "Mpesa":
                 //JOptionPane.showMessageDialog(null, "wk");
              Double available2=Double.valueOf(getDetails("mpesa"));
                 
              Double val2=available2+amount;
                 // JOptionPane.showMessageDialog(null, val2);
              String c2 = "UPDATE `account` SET `mpesa`='"+val2+"'";
              executeSQlQueryN(c2);
              // JOptionPane.showMessageDialog(null, c2);
                 
                 break;
         }
    
    
}
public String  getDetails(String value){
    String val="0";
    try {
            
            //Methods m = new Methods();
            Connection con = getConnection();
            Statement st = con.createStatement();
            String one="1";
             String searchQuery = "SELECT * FROM `account` WHERE autoid='"+one+"'";
          
            ResultSet rs = st.executeQuery(searchQuery);
            while (rs.next()) {
               if("cashonoffice".equals(value)){
                val=rs.getString("cashinoffice");
               }
               else if("mpesa".equals(value)){
                   val=rs.getString("mpesa");
               }
               else{
                   val=rs.getString("bank");
               }
          //   JOptionPane.showMessageDialog(null, val);
                
                
                
            }
            st.close();
            rs.close();
            con.close();
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(Methods.class.getName()).log(Level.SEVERE, null, ex);
        }
    return val;
}
public void RemoveFromOrgAccount(Double amount,String Description,String mode){
    String xro="0";
     String query = "INSERT INTO orgaccount("
             + "`cashin`,"
             + "`cashout`,"
             
             + "`description`"
             + ",`mode`"
             + ",`date`)"
             + " VALUES ("
             + "'" + xro + "'"
             + ",'" + amount+ "'"
             + ",'" + Description+ "'"
             + ",'" + mode+ "'"
            + ",now())";
    
    executeSQlQueryN(query);
    
        if(  null != mode)
            switch (mode) {
             case "Cash":
                 
              Double available=Double.valueOf(getDetails("cashinoffice"));
                 
              Double val=available-amount;
                 
               String c = "UPDATE `account` SET `cashinoffice`='"+val+"'";
               executeSQlQueryN(query);
                 break;
             case "Cheque":
              Double available1=Double.valueOf(getDetails("bank"));
                 
              Double val1=available1-amount;
                 
              String c1 = "UPDATE `account` SET `bank`='"+val1+"'";
              executeSQlQueryN(c1);
                 break;
             case "Mpesa":
              Double available2=Double.valueOf(getDetails("mpesa"));
                 
              Double val2=available2-amount;
                 
              String c2 = "UPDATE `account` SET `mpesa`='"+val2+"'";
              executeSQlQueryN(c2);
                 
                 break;
         }
    
    
    
}
    public String setTitle() {
        String title="Store Managment System";
      
         return title;
    }
      

      
      public ArrayList<String>ListUsersNames(){
        ArrayList<String> usersList = new ArrayList<String>();
    try
    {
      
        Connection con = getConnection();
     // Connection con = getConnection();
      Statement st = con.createStatement();
      String searchQuery = "SELECT * FROM `system_user` ";
      ResultSet rs = st.executeQuery(searchQuery);
      while (rs.next())
      {
          
        usersList.add(rs.getString("user_name"));
        
        //usersList.add(user);
      }
      st.close();
      rs.close();
      con.close();
    }
    catch (Exception ex)
    {
      System.out.println(ex.getMessage());
    }
    return usersList;  
      }
   
   

      
      
      
       public static Date addMonth(Date date,int hw){
      Calendar  c= Calendar.getInstance();
      c.setTime(date);
      
      c.add(Calendar.MONTH, hw);
      
      return c.getTime();
  }
   public static Date addWeek(Date date,int hw){
      Calendar  c= Calendar.getInstance();
      c.setTime(date);
      
      c.add(Calendar.DATE, hw*7);
      
      return c.getTime();
  }
    public static Date addDay(Date date,int hw){
      Calendar  c= Calendar.getInstance();
      c.setTime(date);
      
      c.add(Calendar.DAY_OF_WEEK, hw);
      
      return c.getTime();
  }
    public static Date addYear(Date date,int hw){
      Calendar  c= Calendar.getInstance();
      c.setTime(date);
      
      c.add(Calendar.YEAR, hw);
      
      return c.getTime();
  }
       
        public String selectcolor()
    throws Exception
           
  {  String col = null;
      try{
         File x=new File("file.txt"); 
        // File x=new File("file.txt");
           Scanner sc=new Scanner(x);
           String fs="";
           while(sc.hasNext()){
              col=sc.next(); 
              }
           
             try{
                 
            
     
     //  Color c=new Color(Integer.parseInt(col));
       }
           
            
       catch(Exception cc){
           cc.printStackTrace();
           col="-1";
       }
             

      }
        catch(Exception cc){
         // cc.printStackTrace();
           col="-1";
       }
    return col;
  }
   public String[] getNameImage(){
   String contactx="Details";
   String imgurlx="image";
    try {
       // Methods m=new Methods();
        Connection con = getConnection();
        Statement st2 = con.createStatement();
        
        ResultSet res7 = st2.executeQuery("SELECT imgurl,name,address,email,website,phone FROM prefrences  WHERE id=1");
        if (res7.next()) {
         imgurlx = res7.getString("imgurl");
        contactx = res7.getString("name")+"\n"+res7.getString("address")+"\n"+res7.getString("website")+
        "\n"+res7.getString("email")+"\n"+res7.getString("phone");
            
        } else {
            JOptionPane.showMessageDialog(null, "error loading image \n  make sure image is in images folder ");
        }
        st2.close();
        res7.close();   
        con.close();
    } catch (SQLException ex) {
        Logger.getLogger(Methods.class.getName()).log(Level.SEVERE, null, ex);
    }
  return new String[] {imgurlx,contactx};
}
 
     public String pathselector()
    throws Exception
           
  {  String col = "localhost";
      try{
         File x=new File("dbConfig.txt"); 
        // File x=new File("file.txt");
           col=new Scanner(new File("dbConfig.txt"),"UTF-8").next();
          // String fs="";
          // while(sc.hasNext()){
           //   col=sc.next(); 
           //   }
           
             try{
                 
            
     
     //  Color c=new Color(Integer.parseInt(col));
       }
           
            
       catch(Exception cc){
           System.out.println("path not found");
           cc.printStackTrace();
           col="localhost";
       }
             

      }
        catch(Exception cc){
         System.out.println("path not found");
           col="localhost";
       }
    return col;
  }
         
}
