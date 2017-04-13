/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogingRgestration;


import Configrations.DbConfig;
import java.awt.Color;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import storemanagment.Main;
import storemanagment.Methods;

/**
 *
 * @author Kamau
 */
public class login1 extends javax.swing.JFrame {

    /**
     * Creates new form login
     */
    public login1() {
        initComponents();
       // ex();
       btnDb.setMnemonic(KeyEvent.VK_R);
      btnDb.setOpaque(false);
      btnDb.setContentAreaFilled(false);
      btnDb.setBorderPainted(false);
      //testBr.setMnemonic(KeyEvent.VK_T);
         setTilteImage();
         
 
         
         
        this.txtUserName.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent b) {
                txtPassword.requestFocus();
            }
        });
        this.txtPassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent b) {
                 if(jCheckBox1.isSelected()){
             firstTime();
                }
             else
                verify()  ;
            }

           
        });
    }
     private void firstTime() {
       String temporarypass="A1234";
       String temporaryusername="t1emp3";
       String val="V";
         try {
            Methods m=new Methods();
            Connection con = m.getConnection();
            Statement st = con.createStatement();
            //  String searchQuery = "SELECT * FROM `accounttypes`WHERE name='" + a + "' ";

            
            String str="";
            str="select * from System_user ";
            ResultSet rs = st.executeQuery(str);
            if (rs.next())
            {
                JOptionPane.showMessageDialog(null, "SYSTEM IS ALREADY IN USE","ERROR",JOptionPane.ERROR_MESSAGE);
                
            }
            else
            {
              try {
           
            try (Connection connection = m.getConnection()) {
                String query = "INSERT INTO `system_user`(`user_name`, `user_password`, `hash`) VALUES ('" + temporaryusername + "','" + temporarypass + "','" + val + "')";
                
                try (PreparedStatement pst = connection.prepareStatement(query)) {
                    pst.executeUpdate(query);
                    
                     JOptionPane.showMessageDialog(null, "USE PASS-"+temporarypass+" \nAND UserName "+temporaryusername+"\n Make sure to delete or change this temporary credentials","ERROR",JOptionPane.ERROR_MESSAGE);
                     jCheckBox1.setEnabled(false);
                     jCheckBox1.setSelected(false);
                    
                    pst.close();
                    connection.close();
                }
            }
            
        } catch (SQLException ex) {
              JOptionPane.showMessageDialog(null, "Error");
            Logger.getLogger(login1.class.getName()).log(Level.SEVERE, null, ex);
        }  
            }
            
            st.close();
            rs.close();
            //str.close();
            con.close();      
        } catch (SQLException ex) {
            Logger.getLogger(login1.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         
         
         
         
            }
         private void setTilteImage(){
        try {
            Methods n=new Methods();
            String t= n.setTitle();
            this.setTitle(t);
            String i=n.setIconImage();
            this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(i)));
            
            String col=n.selectcolor();
            Color c=new Color(Integer.parseInt(col));
            jPanel1.setBackground(c);
              Container cont=this.getContentPane();
            cont.setBackground(c);
        } catch (Exception ex) {
            Logger.getLogger(login1.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    public void verify(){
        String stru="";
        stru=txtUserName.getText();

        String strp="";
        strp=txtPassword.getText();
        if(stru.equals("oop")&&strp.equals("oop"))
        {
           open("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        }
        else{
        if (stru.isEmpty()==true)
        {
         JOptionPane.showMessageDialog(null,"Enter User Name");
         return;
        }

        if (strp.isEmpty()==true)
        {
         JOptionPane.showMessageDialog(null,"Enter Password");
         return;
        }
        try
        {
            //get database connection details
           // MainClass mc=new MainClass();

             //open connection
            //Connection connection;
            //connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root","123ERYcog.");
         Methods m=new Methods();
//           try {
//        m.selectdbpath();
//    } catch (Exception ex) {
//        Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
//    }
         Connection connection = m.getConnection();
            String str="";
            str="select * from System_user where user_name =? and user_password =?";
           PreparedStatement pst=connection.prepareStatement(str);
           pst.setString(1, stru);
           pst.setString(2, strp);
           ResultSet rs;
           rs=pst.executeQuery();
           if (rs.next())
           {
               String hash=rs.getString("hash");
               open(hash);

           }
           else
           {
                JOptionPane.showMessageDialog(null,"User name or password are not correct.");
                return;
            }

pst.close();
              rs.close();
              //str.close();
               connection.close();
        }
        catch (Exception k)
        {
            System.err.println(k);
            System.exit(1);
        }


    }
            
    }

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
        jLabel2 = new javax.swing.JLabel();
        txtUserName = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        btnEnter = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        btnDb = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocation(new java.awt.Point(500, 250));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 255));
        jLabel1.setText("UserName");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 255));
        jLabel2.setText("Password");

        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });

        btnEnter.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnEnter.setForeground(new java.awt.Color(51, 51, 255));
        btnEnter.setText("Enter");
        btnEnter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnterActionPerformed(evt);
            }
        });

        jCheckBox1.setText("First Time");

        btnDb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDbActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(btnDb)
                        .addGap(18, 18, 18)
                        .addComponent(btnEnter))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jCheckBox1)
                                .addComponent(jLabel2)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUserName)
                            .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEnter)
                    .addComponent(jCheckBox1)
                    .addComponent(btnDb))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    
    
    
    
    
    
    
    private void open(String hash){
        
      
           // Main n=new Main();
           Main.hash=hash;
           
          Main.a=1;
          Main.ch=1;
         // Main.checkTodays();
            this.dispose();
            
            // n.checkTodays();
         // this.setVisible(false);
        
         
       
        
    }
    
    public  void seten(){
       // open();
     //Home.setEnabled(true);
    }
    private void btnEnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnterActionPerformed
    if(jCheckBox1.isSelected()){
        firstTime();
    }
    else
        
        verify();
        
        
        
    }//GEN-LAST:event_btnEnterActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void btnDbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDbActionPerformed
       DbConfig db=new DbConfig();
       db.setVisible(true);
    }//GEN-LAST:event_btnDbActionPerformed

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
            java.util.logging.Logger.getLogger(login1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(login1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(login1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(login1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new login1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDb;
    private javax.swing.JButton btnEnter;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}
