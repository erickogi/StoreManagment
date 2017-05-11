/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storemanagment.Transactions;

import java.awt.Color;
import java.awt.Container;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import storemanagment.CartPojo;
import storemanagment.Give.GiveForm;
import storemanagment.ItemsPojo;
import storemanagment.Keys;
import storemanagment.Methods;
import storemanagment.Printing;
import storemanagment.Recieve.RecieveForm;
import storemanagment.TransactionsPojo;

/**
 *
 * @author kimani kogi
 */
public class TransactionsForm extends javax.swing.JFrame {
Methods methods=new Methods();
    /**
     * Creates new form TransactionsForm
     */
    private String storeType;

    public TransactionsForm(String storeType) {
        this.storeType = storeType;
         initComponents();
         findTransactions();
         jButton4.setVisible(false);
          setTilteImage();
         this.setTitle(storeType+" -Transactions");
    }
    
    
    public TransactionsForm() {
           initComponents();
           findTransactions();
    }
             public Color setTilteImage(){
        Color c=null;
        try {
            //Methods n=new Methods();
            
            String i=methods.setIconImage();
            this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(i)));
            
            String col=methods.selectcolor();
             c=new Color(Integer.parseInt(col));
           // jPanel1.setBackground(c);
            Container cont=this.getContentPane();
            cont.getWidth();
            cont.setBackground(c);
            
              jPanel1.setBackground(c);
              jPanel2.setBackground(c);
              jPanel3.setBackground(c);
            
            this.setForeground(c);
        } catch (Exception ex) {
            Logger.getLogger(TransactionsForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
}

    public ArrayList<TransactionsPojo> ListTransactions(String Id) {
        ArrayList<TransactionsPojo> transactionsList = new ArrayList();
        try {

            Connection con = methods.getConnection();
            
            Statement st = con.createStatement();
            
            String searchQuery = "SELECT * FROM " + Keys.KEY_TRANSACTION_TABLE + ""
                    + " WHERE CONCAT(" + Keys.KEY_TRANSACTION_RECEIPT_GIVEN + "," + Keys.KEY_TRANSACTION_RECEIPT_RECIEVED+ "," + Keys.KEY_ITEM_ID + "," + Keys.KEY_ITEM_NAME + ") LIKE '%" + Id + "%'  "
                    + "AND " + Keys.KEY_ITEM_TYPE + " = '" + this.storeType + "' ORDER BY "+Keys.KEY_TRANSACTION_ID+" DESC";
            ResultSet rs = st.executeQuery(searchQuery);
            while (rs.next()) {
//TransactionsPojo(int transaction_id, int item_id, String item_name, String item_type, String transaction_quantity,
//String transaction_quantity_in, String transaction_type, String transaction_to, String transaction_from, String transaction_cash, 
//String transaction_receipt_no_in, String transaction_receipt_no_out, String transaction_officer_incharge, String transaction_time_string)
        
        
        
                TransactionsPojo data = new TransactionsPojo(
                        rs.getInt(Keys.KEY_TRANSACTION_ID),
                        rs.getInt(Keys.KEY_ITEM_ID),
                        rs.getString(Keys.KEY_ITEM_NAME),
                        rs.getString(Keys.KEY_ITEM_TYPE),
                        rs.getInt(Keys.KEY_TRANSACTION_REVERT_STATUS),
                        rs.getString(Keys.KEY_TRANSACTION_QUANTITY),
                        rs.getString(Keys.KEY_TRANSACTION_QUANTITY_IN),
                        rs.getString(Keys.KEY_TRANSACTION_TYPE),
                        rs.getString(Keys.KEY_TRANSACTION_TO),
                        rs.getString(Keys.KEY_TRANSACTION_FROM),
                        rs.getString(Keys.KEY_TRANSACTION_CASH),
                        rs.getString(Keys.KEY_TRANSACTION_RECEIPT_RECIEVED),
                        rs.getString(Keys.KEY_TRANSACTION_RECEIPT_GIVEN),
                        rs.getString(Keys.KEY_TRANSACTION_OFFICER_INCHARGE),
                        rs.getString(Keys.KEY_TRANSACTION_TIME)
                );

                transactionsList.add(data);
            }
            st.close();
            rs.close();
            con.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return transactionsList;
    }

    private void refresh() {

        DefaultTableModel model = (DefaultTableModel) this.table.getModel();

        model.setRowCount(0);

        findTransactions();
    }

    public void findTransactions() {
        ArrayList<TransactionsPojo> data = ListTransactions(txt_table_search.getText());
        DefaultTableModel model = new DefaultTableModel();
//TransactionsPojo(int transaction_id, int item_id, String item_name, String item_type, String transaction_quantity,
//String transaction_quantity_in, String transaction_type, String transaction_to, String transaction_from, String transaction_cash, 
//String transaction_receipt_no_in, String transaction_receipt_no_out, String transaction_officer_incharge, String transaction_time_string)
        model.setColumnIdentifiers(new Object[]{"DATE", "RECEIPT", "ID", "NAME","TYPE", "QUANTITY","IN","TO/FROM","CASH","OFFICER","ITEM_NO"});
        Object[] row = new Object[11];
        for (int i = 0; i < data.size(); i++) {
            row[0] = ((TransactionsPojo) data.get(i)).getTransaction_time_string();
            
             String type = ((TransactionsPojo) data.get(i)).getTransaction_type();
             if(type.equals(Keys.KEY_TRANSACTION_GIVE)){
                row[1] = ((TransactionsPojo) data.get(i)).getTransaction_receipt_no_out(); 
                row[7] = ((TransactionsPojo) data.get(i)).getTransaction_to();
             }
             else {
                 row[1] = ((TransactionsPojo) data.get(i)).getTransaction_receipt_no_in();
                 row[7] = ((TransactionsPojo) data.get(i)).getTransaction_from();
             }
            
            row[2] = ((TransactionsPojo) data.get(i)).getTransaction_id();
            row[3] = ((TransactionsPojo) data.get(i)).getItem_name();
            
            row[4] = type;
             
            row[5] = ((TransactionsPojo) data.get(i)).getTransaction_quantity();
            row[6] = ((TransactionsPojo) data.get(i)).getTransaction_quantity_in();
            
            row[8] = ((TransactionsPojo) data.get(i)).getTransaction_cash();
            row[9] = ((TransactionsPojo) data.get(i)).getTransaction_officer_incharge();
            row[10] = ((TransactionsPojo) data.get(i)).getItem_id();
           

            model.addRow(row);
        }
        this.table.setModel(model);

    }
    
     public void threadExecute(int code){
     Thread log=new Thread(){
     public void run(){
     String newQuantity="0";
         switch (code) {
             case 1:
                 if (type.equals(Keys.KEY_TRANSACTION_GIVE)) {
                     newQuantity = String.valueOf(getQuantityNow(txt_item_id.getText()) + Double.valueOf(txt_transaction_quantity.getText()));
                 } else if (type.equals(Keys.KEY_TRANSACTION_RECIEVE_NEW) || type.equals(Keys.KEY_TRANSACTION_RECEIVE_EXISTING)) {
                     newQuantity = String.valueOf(getQuantityNow(txt_item_id.getText())
                             - Integer.valueOf(txt_transaction_quantity.getText()));
                 }

                 System.out.println(newQuantity);
                 updateExistingItem(
                         txt_officer_in_charge.getText(),
                         newQuantity,
                         Integer.valueOf(txt_item_id.getText()), txt_item_name.getText(),
                         "-" + txt_transaction_quantity.getText(),
                         txt_transaction_quantity_in.getText(),
                         txt_transaction_receipt.getText(),
                         Keys.KEY_TRANSACTION_REVERT + " " + txt_transaction_id.getText(),
                         "--",
                         "--",
                         "-" + txt_transaction_cash.getText(),
                         txt_transaction_receipt.getText()
                 );
                 break;
             case 2:
                 reProduceReceipt();
                 
                 break;
             case 3:
                 
                 
                 break;
             case 4:
                 
                 
                 break;
             default:
                 
                 
                 break;
         }
} 
      }   ;  
      log.start();
  }
    double getQuantityNow(String itemId){
        double quantity=0;
          try {
            System.out.println(itemId);
            Connection con = methods.getConnection();
            
            Statement st = con.createStatement();
            
            String searchQuery = "SELECT * FROM " + Keys.KEY_ITEMS_TABLE + " WHERE item_id ='"+itemId+"' ";
            ResultSet rs = st.executeQuery(searchQuery);
            if(rs.next()){
                quantity=Double.valueOf(rs.getString("item_quantity"));
                 System.out.println(quantity);
            }
            else{
                 System.out.println("else");
            }
            st.close();
            rs.close();
            con.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
          return quantity;
     }
     void updateExistingItem(
             String officer_in_charge,
             String item_new_quantity,
            int item_id,String item_name,String transaction_quantity,String transaction_quantity_in,String receipt_no,
            String transactionType,String item_to,String item_from,String cash,String receipt_recieved
     
     ) {

         
         
         
         
         
        String query = "UPDATE " + Keys.KEY_ITEMS_TABLE + " SET " + Keys.KEY_ITEM_QUANTITY + "='" + item_new_quantity + "'"
                + "," + Keys.KEY_ITEM_UPDATED_AT + "= now()"
                + "WHERE " + Keys.KEY_ITEM_ID + "= '" + item_id + "' ";

        if (methods.executeSQlQueryN(query) == 1) {
//            String officer_in_charge,
//             String item_new_quantity,
//            int item_id,String item_name,String transaction_quantity,String transaction_quantity_in,String receipt_no,
//            String transactionType,String item_to,String item_from,String cash,String receipt_recieved

 
       setTransaction(item_id,item_name,transaction_quantity,
       transaction_quantity_in,receipt_no,officer_in_charge,
       transactionType,item_to,item_from,cash,receipt_recieved);
     
        }
    // ) {
        
        
            
//           setTransaction(item_id,item_name,transaction_quantity,
//           transaction_quantity_in,receipt_no,officer_in_charge);
           
           
           
           
           
           
           
           // }
        else{
            System.out.println("Error UpdateExistingItem");
        }

    }
       void setTransaction(int item_id,String item_name,String transaction_quantity,
       String transaction_quantity_in,String receipt_no,String officer_in_charge,
       String transactionType,String item_to,String item_from,String cash,String reciept_recieved) {
        
        String nullValue = "--";
        
        String query = "INSERT INTO transactions_table("
                + "" + Keys.KEY_ITEM_ID + ","
                + "" + Keys.KEY_ITEM_NAME + ", "
                + "" + Keys.KEY_ITEM_TYPE + ","
                + "" + Keys.KEY_TRANSACTION_QUANTITY + ","
                + "" + Keys.KEY_TRANSACTION_TYPE + ","
                + "" + Keys.KEY_TRANSACTION_QUANTITY_IN + ","
                + "" + Keys.KEY_TRANSACTION_TO + ","
                + "" + Keys.KEY_TRANSACTION_FROM + ","
                + "" + Keys.KEY_TRANSACTION_CASH + ","
                + "" + Keys.KEY_TRANSACTION_RECEIPT_RECIEVED + ","
                + "" + Keys.KEY_TRANSACTION_RECEIPT_GIVEN + ","
                + "" + Keys.KEY_TRANSACTION_OFFICER_INCHARGE + ","
                + "" + Keys.KEY_TRANSACTION_TIME + ")"
                + " VALUES ("
                + "'" + item_id + "',"
                + "'" + item_name+ "',"
                + "'" + this.storeType + "',"
                
                + "'" + transaction_quantity + "',"
                
                + "'" + transactionType+ "',"
                
                + "'" + transaction_quantity_in + "',"
                
                + "'" + item_to + "',"
                + "'" + item_from + "',"
                + "'" + cash+ "',"
               // + "'" + this.txt_item_receipt_no.getText() + "',"
                + "'" +  reciept_recieved+ "',"
                + "'" + receipt_no + "',"
                + "'" + officer_in_charge + "',now())";

        if (methods.executeSQlQueryN(query) == 1) {
            
            JOptionPane.showMessageDialog(null, "REVERTED SUCCESSFULY");
            refresh();
        }
        else{
            System.out.println("Error setTransaction");
        }
    }
    private void revertTransaction(){
        
        
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
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txt_transaction_id = new javax.swing.JTextField();
        txt_item_name = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_transaction_date = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_transaction_to_from = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_transaction_receipt = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_officer_in_charge = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_transaction_quantity = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txt_transaction_quantity_in = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txt_transaction_cash = new javax.swing.JTextField();
        txt_it = new javax.swing.JLabel();
        txt_item_id = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        txt_table_search = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jDateChooserFrom = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jDateChooserTo = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("DETAILS"));

        jLabel4.setText("Transction No");

        txt_transaction_id.setEditable(false);

        txt_item_name.setEditable(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("ITEM NAME");

        txt_transaction_date.setEditable(false);
        txt_transaction_date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_transaction_dateActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("TRANSACTION DATE");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("TO/FROM");

        txt_transaction_to_from.setEditable(false);

        jLabel8.setText("Reciept No");

        txt_transaction_receipt.setEditable(false);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("OFFICER IN CHARGE");

        txt_officer_in_charge.setEditable(false);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("QUANTITY");

        txt_transaction_quantity.setEditable(false);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("IN");

        txt_transaction_quantity_in.setEditable(false);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("CASH");

        txt_transaction_cash.setEditable(false);

        txt_it.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txt_it.setText("ITEM ID");

        txt_item_id.setEditable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_transaction_quantity)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(txt_transaction_id, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
                            .addComponent(txt_transaction_to_from, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_item_name, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(20, 20, 20)))
                        .addComponent(jLabel7))
                    .addComponent(jLabel10)
                    .addComponent(jLabel12)
                    .addComponent(txt_transaction_cash))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel11)
                        .addComponent(jLabel9)
                        .addComponent(jLabel6)
                        .addComponent(txt_transaction_date)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addGap(18, 18, 18)
                            .addComponent(txt_transaction_receipt, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
                        .addComponent(txt_officer_in_charge)
                        .addComponent(txt_transaction_quantity_in)
                        .addComponent(txt_item_id))
                    .addComponent(txt_it))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_transaction_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txt_transaction_receipt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_it))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_item_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_item_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_transaction_to_from, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_transaction_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_transaction_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_transaction_quantity_in, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_transaction_cash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_officer_in_charge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(49, Short.MAX_VALUE))
        );

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
        jScrollPane2.setViewportView(table);

        txt_table_search.setToolTipText("Search");
        txt_table_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_table_searchKeyReleased(evt);
            }
        });

        jLabel1.setText("SEARCH");

        jLabel2.setText("From");

        jLabel3.setText("To");

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText("FIND");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("ACTIONS"));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton2.setText("UNDO TRANSACTION");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton3.setText("RE-PRODUCE RECIEPT");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton4.setText("UPDATE TRANSACTION");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton5.setText("CLEAR");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_table_search, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(75, 75, 75)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jDateChooserFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(61, 61, 61)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jDateChooserTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(0, 506, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_table_search, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jDateChooserFrom, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jDateChooserTo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jMenu1.setText("File");
        jMenu1.setMargin(new java.awt.Insets(0, 0, 0, 20));

        jMenuItem3.setText("Help");
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("Exit");
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Operations");
        jMenu2.setMargin(new java.awt.Insets(0, 0, 0, 20));

        jMenuItem1.setText("Recieve ");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuItem2.setText("Give");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Tools");

        jMenuItem5.setText("Calculator");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        jMenuBar1.add(jMenu3);

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
public void reProduceReceipt(){
    print(ListCartItems(),txt_transaction_receipt.getText()+"  B",txt_officer_in_charge.getText());
}
 public ArrayList<CartPojo> ListCartItems() {
        ArrayList<CartPojo> itemsList = new ArrayList();
        try {

            Connection con = methods.getConnection();

            Statement st = con.createStatement();

            String searchQuery = "SELECT * FROM " + Keys.KEY_TRANSACTION_TABLE+ " WHERE "+Keys.KEY_TRANSACTION_RECEIPT_GIVEN+" ='"+txt_transaction_receipt.getText()+"'";
            ResultSet rs = st.executeQuery(searchQuery);
            while (rs.next()) {

                CartPojo data = new CartPojo(
                        0, 
                        rs.getInt(Keys.KEY_ITEM_ID),
                        rs.getString(Keys.KEY_ITEM_NAME),
                        "",
                        rs.getString(Keys.KEY_TRANSACTION_QUANTITY),
                        rs.getString(Keys.KEY_TRANSACTION_QUANTITY_IN),
                        ""
                );

                itemsList.add(data);
            }
            st.close();
            rs.close();
            con.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return itemsList;
    }

    void print(ArrayList<CartPojo> items, String rNo, String officer) {
        // ArrayList<CartPojo> itemsoop=null;
        Printing print = new Printing(rNo, officer, items);
    }
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String password = JOptionPane.showInputDialog("Enter Your Password ");
         String user_name = methods.getUserNameByPassword(password);
        if (!"null".equals(user_name)) {
            if(check()==1){
        threadExecute(2);
            }
        }
        else{
             JOptionPane.showMessageDialog(null, "UN-REGISTERD PASSWORD..."
                    + "\n"
                    + "FIND ASSISTANCE FROM SYSTEM ADMINISTRATOR");
        }
    }//GEN-LAST:event_jButton3ActionPerformed
int check(){
    int status=1;
    if(txt_transaction_id.getText().isEmpty()||txt_transaction_receipt.getText().isEmpty()){
        status=0;
    }
    return status;
}
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String password = JOptionPane.showInputDialog("Enter Your Password ");
         String user_name = methods.getUserNameByPassword(password);

        if (!"null".equals(user_name)) {
            if(check()==1){
             threadExecute(1);
            }
        }
        else{
             JOptionPane.showMessageDialog(null, "UN-REGISTERD PASSWORD..."
                    + "\n"
                    + "FIND ASSISTANCE FROM SYSTEM ADMINISTRATOR");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        RecieveForm recieveForm=new RecieveForm(storeType);
        recieveForm.setVisible(true);
        this.dispose();
        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        GiveForm giveForm=new GiveForm(storeType);
        giveForm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       //String password = JOptionPane.showInputDialog("Enter Your Password ");
      // threadExecute(2);
       
    }//GEN-LAST:event_jButton4ActionPerformed
private String getRevertStatus(String transactionId){
     String transactionRevertStatus="0";
    try {
            
            Connection con = methods.getConnection();
            
            Statement st = con.createStatement();
            
            String searchQuery = "SELECT transaction_revert_status FROM " + Keys.KEY_TRANSACTION_TABLE + " WHERE "+Keys.KEY_TRANSACTION_ID+" ='"+transactionId+"' ";
            ResultSet rs = st.executeQuery(searchQuery);
            if(rs.next()){
                transactionRevertStatus=(rs.getString("transaction_revert_status"));
                 
            }
            else{
                 System.out.println("else");
            }
            st.close();
            rs.close();
            con.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    return transactionRevertStatus;
}
    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        int i = this.table.getSelectedRow();

        TableModel model = this.table.getModel();

       // "DATE", "RECEIPT", "ID", "NAME","TYPE", "QUANTITY","IN","OFFICER","ITEM_NO"
        this.txt_item_id.setText(model.getValueAt(i, 10).toString());

        this.txt_item_name.setText(model.getValueAt(i, 3).toString());
        
         type=model.getValueAt(i, 4).toString();
         System.out.println(type);
         
        this.txt_transaction_quantity.setText(model.getValueAt(i, 5).toString());
        
        this.txt_transaction_quantity_in.setText(model.getValueAt(i, 6).toString());
        
        this.txt_officer_in_charge.setText(model.getValueAt(i, 9).toString());
        
        this.txt_transaction_date.setText(model.getValueAt(i, 0).toString());
        
        this.txt_transaction_receipt.setText(model.getValueAt(i, 1).toString());
        
        this.txt_transaction_id.setText(model.getValueAt(i, 2).toString());
        
        this.txt_transaction_to_from.setText(model.getValueAt(i, 7).toString());
        
        this.txt_transaction_cash.setText(model.getValueAt(i, 8).toString());
        
        
        if("1".equals(getRevertStatus(txt_transaction_id.getText()))){
            jButton2.setEnabled(false);
        }
        else if ("0".equals(getRevertStatus(txt_transaction_id.getText()))) {
            jButton2.setEnabled(true);
        }
        
        
        
    }//GEN-LAST:event_tableMouseClicked
String type;
    private void txt_transaction_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_transaction_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_transaction_dateActionPerformed

    private void txt_table_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_table_searchKeyReleased
      refresh();
    }//GEN-LAST:event_txt_table_searchKeyReleased

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        Calc n=new Calc();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
     
    }//GEN-LAST:event_jButton5ActionPerformed

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
            java.util.logging.Logger.getLogger(TransactionsForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TransactionsForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TransactionsForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TransactionsForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TransactionsForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private com.toedter.calendar.JDateChooser jDateChooserFrom;
    private com.toedter.calendar.JDateChooser jDateChooserTo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable table;
    private javax.swing.JLabel txt_it;
    private javax.swing.JTextField txt_item_id;
    private javax.swing.JTextField txt_item_name;
    private javax.swing.JTextField txt_officer_in_charge;
    private javax.swing.JTextField txt_table_search;
    private javax.swing.JTextField txt_transaction_cash;
    private javax.swing.JTextField txt_transaction_date;
    private javax.swing.JTextField txt_transaction_id;
    private javax.swing.JTextField txt_transaction_quantity;
    private javax.swing.JTextField txt_transaction_quantity_in;
    private javax.swing.JTextField txt_transaction_receipt;
    private javax.swing.JTextField txt_transaction_to_from;
    // End of variables declaration//GEN-END:variables
}
