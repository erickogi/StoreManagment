/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storemanagment;

import java.awt.Color;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import storemanagment.Give.GiveForm;
import storemanagment.Recieve.RecieveForm;

/**
 *
 * @author kimani kogi
 */
public final class loaned extends javax.swing.JFrame {
Methods methods=new Methods();
String storeSearch="All";
    /**
     * Creates new form loaned
     */

    /**
     * Creates new form loaned
     * @return
     */
    public Color setTilteImage() {
        Color c = null;
        try {
            //Methods n=new Methods();

            String i = methods.setIconImage();
            this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(i)));

            String col = methods.selectcolor();
            c = new Color(Integer.parseInt(col));
            // jPanel1.setBackground(c);
            Container cont = this.getContentPane();
            cont.getWidth();
            cont.setBackground(c);

            jPanel1.setBackground(c);
            jPanel2.setBackground(c);
            

            this.setForeground(c);
        } catch (Exception ex) {
            Logger.getLogger(GiveForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }
    public loaned() {
        initComponents();
        setTilteImage();
        findItems();
    }

    
    public ArrayList<loanedPojo> ListItems(String Id,String store) {
        ArrayList<loanedPojo> loanedList = new ArrayList();
        try {

            Connection con = methods.getConnection();

            Statement st = con.createStatement();

//                private int loan_id;
//    private int item_id;
//    private String item_name;
//    private String item_type;
//    private String item_quantity;
//    private String item_quantity_in;
//    private String transaction_to;
//    private String transaction_receipt_no;
//    private String transaction_receipt_no_out;
//    private String officer_incharge;
//    private String transaction_time;
String searchQuery=null;
            if(store.equals("All")){
            
             searchQuery = "SELECT * FROM " + Keys.KEY_LOANED_TABLE + ""
                    + " WHERE CONCAT(" + Keys.KEY_ITEM_ID + "," + Keys.KEY_ITEM_NAME + ","+Keys.KEY_TRANSACTION_TO+") LIKE '%" + Id + "%'  ";
            }
            else{
                  searchQuery = "SELECT * FROM " + Keys.KEY_LOANED_TABLE + ""
                    + " WHERE CONCAT(" + Keys.KEY_ITEM_ID + "," + Keys.KEY_ITEM_NAME + ","+Keys.KEY_TRANSACTION_TO+") LIKE '%" + Id + "%'  AND "+Keys.KEY_ITEM_TYPE+"='"+store+"'  ";
            }
            ResultSet rs = st.executeQuery(searchQuery);
            while (rs.next()) {

                loanedPojo data = new loanedPojo(
                        rs.getInt(Keys.KEY_LOANED_ID), 
                        rs.getInt(Keys.KEY_ITEM_ID),
                        rs.getString(Keys.KEY_ITEM_NAME),
                        rs.getString(Keys.KEY_ITEM_TYPE),
                       
                        rs.getString(Keys.KEY_ITEM_QUANTITY), 
                        rs.getString(Keys.KEY_ITEM_QUANTITY_IN),
                        rs.getString(Keys.KEY_TRANSACTION_TO),
                        
                        rs.getString(Keys.KEY_TRANSACTION_RECEIPT_GIVEN),
                        rs.getString(Keys.KEY_TRANSACTION_RECEIPT_GIVEN),
                        rs.getString(Keys.KEY_TRANSACTION_OFFICER_INCHARGE),
                        rs.getString(Keys.KEY_TRANSACTION_TIME)
                );

                loanedList.add(data);
            }
            st.close();
            rs.close();
            con.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return loanedList;
    }

    private void refresh() {

        DefaultTableModel model = (DefaultTableModel) this.table.getModel();

        model.setRowCount(0);

        findItems();
    }

    public void findItems() {
        ArrayList<loanedPojo> data = ListItems(txt_table_search.getText(),storeSearch);
        DefaultTableModel model = new DefaultTableModel();
        // public ItemsPojo(int item_id, String item_name, String item_type, String item_quantity, String item_quantity_in, Date item_updated_at)
        model.setColumnIdentifiers(new Object[]{"ID","LOANED TO", "ITEM_NAME", "QUANTITY", "IN","DATE","STORE","L:ID"});
        Object[] row = new Object[8];
        for (int i = 0; i < data.size(); i++) {
            row[0] = ((loanedPojo) data.get(i)).getItem_id();
            row[1] = ((loanedPojo) data.get(i)).getTransaction_to();

            row[2] = ((loanedPojo) data.get(i)).getItem_name();

            row[3] = ((loanedPojo) data.get(i)).getItem_quantity();
            row[4] = ((loanedPojo) data.get(i)).getItem_quantity_in();
            
            row[5] = ((loanedPojo) data.get(i)).getTransaction_time();
              row[6] = ((loanedPojo) data.get(i)).getItem_type();
            row[7] = ((loanedPojo) data.get(i)).getLoan_id();
            
          

            model.addRow(row);
        }
        this.table.setModel(model);

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
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        txt_table_search = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        txt_item_id = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txt_item_name = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_to = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_date = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_item_quantity = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_in = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        txt_loan_id = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        table.setRowHeight(40);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        txt_table_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_table_searchKeyReleased(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("RETURN"));

        jLabel1.setText("ITEM ID");

        jLabel2.setText("ITEM NAME");

        jLabel3.setText("TO");

        txt_date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_dateActionPerformed(evt);
            }
        });

        jLabel4.setText("DATE");

        jLabel5.setText("QUANTITY");

        txt_in.setText("IN");

        jButton1.setText("RETURN");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("CLEAR");

        txt_loan_id.setText("L:ID");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txt_to, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txt_item_id))
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_item_name)
                            .addComponent(txt_date)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel2))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(txt_item_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txt_in))))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_loan_id)
                                .addGap(72, 72, 72)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_item_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_item_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_to, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_item_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_in)
                    .addComponent(txt_loan_id))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Central", "Stationery", "Kitchen", "Hardware", "Farm" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_table_search, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_table_search, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_table_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_table_searchKeyReleased
        refresh();
    }//GEN-LAST:event_txt_table_searchKeyReleased

    private void txt_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dateActionPerformed
boolean checkEmpty(){
    boolean okay=false;
      if (txt_item_name.getText().isEmpty()
                    || txt_item_quantity.getText().isEmpty() || txt_in.getText().isEmpty()
                    || txt_item_id.getText().isEmpty()
                    || txt_to.getText().isEmpty() || txt_date.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Fill All Details");

            } else {
                okay = true;
            }
      return okay;
}
void deleteLoaned(String loan_id,String to){
    
     String query = "DELETE FROM " + Keys.KEY_LOANED_TABLE + "  WHERE " + Keys.KEY_LOANED_ID + " = '" + loan_id + "'";
        if (methods.executeSQlQueryN(query) == 1) {
            
         refresh();
         clear();
        } else {
            System.out.println("Error deleteCart");
        }
    
    
}
void updateItems(String officer){
    String query = "UPDATE " + Keys.KEY_ITEMS_TABLE + " SET " + Keys.KEY_ITEM_QUANTITY + "=" + Keys.KEY_ITEM_QUANTITY + "+'" + this.txt_item_quantity.getText() + "'"
                + "," + Keys.KEY_ITEM_UPDATED_AT + "= now() "
                + "WHERE " + Keys.KEY_ITEM_ID + "= '" + this.txt_item_id.getText() + "' ";

        if (methods.executeSQlQueryN(query) == 1) {
            setTransaction(officer, txt_item_quantity.getText(),Integer.valueOf( txt_item_id.getText()), Keys.KEY_TRANSACTION_RETURN_LOANED_ITEM,produce(),storetype);
            deleteLoaned(txt_loan_id.getText(),txt_to.getText());
          }
    
    
}
    void setTransaction(String officer_in_charge, String quantity, int item_id, String transactionType,String rNo,String type) {
//txt_item_cash.getText()
        String nullValue = "--";
        String randomReceiptNo = "rcpt";
        String query = "INSERT INTO transactions_table(" + Keys.KEY_ITEM_ID + "," + Keys.KEY_ITEM_NAME + ", " + Keys.KEY_ITEM_TYPE + ","
                + "" + Keys.KEY_TRANSACTION_QUANTITY + ","
                + "" + Keys.KEY_TRANSACTION_TYPE + ","
                + "" + Keys.KEY_TRANSACTION_QUANTITY_IN + "," + Keys.KEY_TRANSACTION_TO + "," + Keys.KEY_TRANSACTION_FROM + ","
                + "" + Keys.KEY_TRANSACTION_CASH + "," + Keys.KEY_TRANSACTION_RECEIPT_RECIEVED + "," + Keys.KEY_TRANSACTION_RECEIPT_GIVEN + ","
                + "" + Keys.KEY_TRANSACTION_OFFICER_INCHARGE + "," + Keys.KEY_TRANSACTION_TIME + ")"
                + " VALUES ('" + item_id + "','" + this.txt_item_name.getText() + "','" + type + "',"
                + "'" + quantity + "',"
                + "'" + transactionType + "',"
                + "'" + this.txt_in.getText() + "',"
                + "'" + nullValue + "',"
                + "'" + this.txt_to.getText() + "',"
                + "'" + nullValue + "',"
                + "'" + nullValue + "',"
                + "'" + rNo+ "',"
                + "'" + officer_in_charge + "',now())";

        if (methods.executeSQlQueryN(query) == 1) {
            //registerReceiptNo(item_id, txt_item_receipt_no.getText());
        }
    }


void clear(){
    this.txt_item_id.setText("");

        this.txt_item_name.setText("");
        
        
        this.txt_item_quantity.setText("");
        
        this.txt_in.setText("");
        

        this.txt_date.setText("");
        

        
        this.txt_to.setText("");
        this.txt_loan_id.setText("");
        
}
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
               String password = JOptionPane.showInputDialog("Enter Your Password ");
        String user_name = methods.getUserNameByPassword(password);

        if (!"null".equals(user_name)) {

            if (checkEmpty()) {

              updateItems(user_name);

            }

        } else {
            JOptionPane.showMessageDialog(null, "UN-REGISTERD PASSWORD..."
                    + "\n"
                    + "FIND ASSISTANCE FROM SYSTEM ADMINISTRATOR");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
         int i = this.table.getSelectedRow();

        TableModel model = this.table.getModel();

        this.txt_item_id.setText(model.getValueAt(i, 0).toString());

        this.txt_item_name.setText(model.getValueAt(i, 2).toString());
        
        
        this.txt_item_quantity.setText(model.getValueAt(i, 3).toString());
        
        this.txt_in.setText(model.getValueAt(i, 4).toString());
        

        this.txt_date.setText(model.getValueAt(i, 5).toString());
        

        
        this.txt_to.setText(model.getValueAt(i, 1).toString());
        this.txt_loan_id.setText(model.getValueAt(i, 7).toString());
        
          storetype=model.getValueAt(i, 6).toString();
        
    }//GEN-LAST:event_tableMouseClicked

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
      String choice[]={ "All", "Central", "Stationery", "Kitchen", "Hardware", "Farm"};
     
        if (evt.getItem() == choice[0] && evt.getStateChange() == ItemEvent.SELECTED) {
            storeSearch ="All" ;
         
        }
         if (evt.getItem() == choice[1] && evt.getStateChange() == ItemEvent.SELECTED) {
            storeSearch = "Central Store";
         
        }
          if (evt.getItem() == choice[2] && evt.getStateChange() == ItemEvent.SELECTED) {
            storeSearch = "Stationery Store";
         
        }
           if (evt.getItem() == choice[3] && evt.getStateChange() == ItemEvent.SELECTED) {
            storeSearch = "Kitchen Store";
         
        }
            if (evt.getItem() == choice[4] && evt.getStateChange() == ItemEvent.SELECTED) {
            storeSearch = "Hardware Store";
         
        }
               if (evt.getItem() == choice[5] && evt.getStateChange() == ItemEvent.SELECTED) {
            storeSearch = "Farm Store";
         
        }
               refresh();
    }//GEN-LAST:event_jComboBox1ItemStateChanged
String storetype="null";
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
            java.util.logging.Logger.getLogger(loaned.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(loaned.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(loaned.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(loaned.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new loaned().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    private javax.swing.JTextField txt_date;
    private javax.swing.JLabel txt_in;
    private javax.swing.JTextField txt_item_id;
    private javax.swing.JTextField txt_item_name;
    private javax.swing.JTextField txt_item_quantity;
    private javax.swing.JLabel txt_loan_id;
    private javax.swing.JTextField txt_table_search;
    private javax.swing.JTextField txt_to;
    // End of variables declaration//GEN-END:variables
    boolean checkReceiptNo(String No) {
        boolean isNewNo = false;
        try {
            String stru = No;

            Connection con = methods.getConnection();
            String str = "";

            str = "select * from transactions_reciepts where  receipt_no =?";

            PreparedStatement pst = con.prepareStatement(str);

            pst.setString(1, stru);

            ResultSet rs;

            rs = pst.executeQuery();

            if (rs.next()) {

                isNewNo = false;
                //ran();

            } else {
                isNewNo = true;
            }

            rs.close();
            pst.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(RecieveForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isNewNo;
    }

    String produce() {

        Random rand = new Random();
        int nr = rand.nextInt(50000) + 1;
        String rNo = String.valueOf(nr);
        return ran(rNo);
    }

    public String ran(String nr) {

        String results = null;
        if (checkReceiptNo(nr) == true) {
            results = nr;

        } else {
            produce();

        }

        return results;

    }


}
