/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storemanagment.Configs;


import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import storemanagment.Methods;

/**
 *
 * @author kimani kogi
 */
public class changepass extends javax.swing.JFrame {
Methods methods=new Methods();
String toCombo[];
  //  String toAccountsTypes[];
  //  private String group = null;
  //  private String accounttype = null;
 // String accounts[];
  String usernames[];
    /**
     * Creates new form changepass
     */
    public changepass() {
        initComponents();
        setTilteImage();
        findGroups();
this.newpassword1.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        changepass.this.newpassword2.requestFocus();
      }
    });
    this.newpassword2.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
          if(newpassword1.getText().equals(newpassword2.getText())){
       effect();
          }
          else{
        JOptionPane.showMessageDialog(null, "PASSWORDS DON'T MATCH","ERROR",JOptionPane.ERROR_MESSAGE);
    }
      }
      
    });
  }
     public void findGroups()
  {
  
    ArrayList<String> usersList =  methods.ListUsersNames();
   usernames=new String[usersList.size()];
   usersList.toArray(usernames);
   groupnames();
   //JOptionPane.showMessageDialog(null, groups[0]);
  }
     private void groupnames() {
       // String namesdb[] = {"eric", "kogi"};
        toCombo = usernames;
        setModel();

    }

    private void setModel() {
        jComboBoxUserNames.removeAllItems();
        jComboBoxUserNames.addItem("User");
        for (int a = 0; a < toCombo.length; a++) {

            jComboBoxUserNames.addItem(toCombo[a]);
        }
    }
    private void setTilteImage(){
        try {
            
            String t= methods.setTitle();
            this.setTitle(t);
            String i=methods.setIconImage();
            this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(i)));
            
            String col=methods.selectcolor();
            Color c=new Color(Integer.parseInt(col));
            jPanel1.setBackground(c);
        } catch (Exception ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    public void effect(){
        if(username.getText().isEmpty()||username.getText().equals("User")){
            JOptionPane.showMessageDialog(null, "Select User");
        }else{
         String stru = "";
        stru = changepass.this.newpassword1.getText();
        
        String strc = "";
        strc = changepass.this.newpassword2.getText();
        if (stru.isEmpty() == true)
        {
          JOptionPane.showMessageDialog(null, "Enter Password");
          return;
        }
        if (strc.isEmpty() == true)
        {
          JOptionPane.showMessageDialog(null, "Enter Password");
          return;
        }
        try
        {
           Connection connection = methods.getConnection();
                String query = "UPDATE `system_user` SET `user_password`='" + this.newpassword2.getText() + "' WHERE `user_name` = '" + this.username.getText() + "'";
                
                try (PreparedStatement pst = connection.prepareStatement(query)) {
                    pst.executeUpdate(query);
                    pst.close();
                    connection.close();
                }
            
             
        } catch (SQLException ex) {
              JOptionPane.showMessageDialog(null, "Error","ERROR",JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(changepass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
       
        JOptionPane.showMessageDialog(null, "successful.");
        System.exit(1);
        }
    }
        
// public Connection getConnection()
//  {
//    try
//    {
//      return DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "123ERYcog.");
//    }
//    catch (Exception e)
//    {
//      e.printStackTrace();
//    }
//    return null;
//  }
  
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        newpassword1 = new javax.swing.JTextField();
        newpassword2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxUserNames = new javax.swing.JComboBox<>();
        username = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        newpassword2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newpassword2ActionPerformed(evt);
            }
        });

        jButton1.setText("EFFECT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("FOR THE CHANGE TO EFFECT,THE PROGRAM WILL EXIT");

        jLabel3.setText("USERNAME");

        jLabel4.setText("CONFIRM PASSWORD");

        jComboBoxUserNames.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxUserNamesItemStateChanged(evt);
            }
        });

        username.setEditable(false);

        jLabel5.setText("PASSWORD");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(158, 158, 158)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jComboBoxUserNames, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(username)
                            .addComponent(newpassword1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(newpassword2)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(80, 80, 80))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(36, 36, 36)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBoxUserNames, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(username))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(newpassword1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(newpassword2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newpassword2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newpassword2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newpassword2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
effect();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBoxUserNamesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxUserNamesItemStateChanged
       username.setText(jComboBoxUserNames.getSelectedItem().toString());
    }//GEN-LAST:event_jComboBoxUserNamesItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(changepass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(changepass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(changepass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(changepass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new changepass().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBoxUserNames;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField newpassword1;
    private javax.swing.JTextField newpassword2;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}
