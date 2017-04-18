/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storemanagment.Give;

import static java.lang.System.out;
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
import storemanagment.CartPojo;
import storemanagment.ItemsPojo;
import storemanagment.Keys;
import storemanagment.Methods;
import storemanagment.Recieve.RecieveForm;
import storemanagment.Transactions.TransactionsForm;

/**
 *
 * @author kimani kogi
 */
public class GiveForm extends javax.swing.JFrame {
Methods methods=new Methods();
private double balanceQuantity = 0.0;
final String uline = "__________________________________________________________________________________";
    final String dline = "----------------------------------------------------------------------------------";
    /**
     * Creates new form GiveForm
     */
    private String storeType;

    public GiveForm(String storeType) {
        this.storeType = storeType;
    
         initComponents();
         this.setTitle(storeType+" -Give Form");
         findItems();
         setCart();
         jProgressBar1.setVisible(false);
    }
    
    public GiveForm() {
        initComponents();
    }
    private void setCart(){
         txt_cart_area.append("Item :                            Qty :                            Id  \n"   );
         txt_cart_area.append(uline +"\n"+dline+"\n\n" );

    }
    public ArrayList<ItemsPojo> ListItems(String Id) {
        ArrayList<ItemsPojo> itemsList = new ArrayList();
        try {

            Connection con = methods.getConnection();
            
            Statement st = con.createStatement();
            
            String searchQuery = "SELECT * FROM " + Keys.KEY_ITEMS_TABLE + ""
                    + " WHERE CONCAT(" + Keys.KEY_ITEM_ID + "," + Keys.KEY_ITEM_NAME + ") LIKE '%" + Id + "%'  "
                    + "AND " + Keys.KEY_ITEM_TYPE + " = '" + this.storeType + "'";
            ResultSet rs = st.executeQuery(searchQuery);
            while (rs.next()) {

                ItemsPojo data = new ItemsPojo(
                        rs.getInt(Keys.KEY_ITEM_ID), rs.getString(Keys.KEY_ITEM_NAME), rs.getString(Keys.KEY_ITEM_TYPE), rs.getString(Keys.KEY_ITEM_QUANTITY), rs.getString(Keys.KEY_ITEM_QUANTITY_IN), rs.getString(Keys.KEY_ITEM_UPDATED_AT)
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

    private void refresh() {

        DefaultTableModel model = (DefaultTableModel) this.table.getModel();

        model.setRowCount(0);

        findItems();
    }

    public void findItems() {
        ArrayList<ItemsPojo> data = ListItems(txt_table_search.getText());
        DefaultTableModel model = new DefaultTableModel();
        // public ItemsPojo(int item_id, String item_name, String item_type, String item_quantity, String item_quantity_in, Date item_updated_at)
        model.setColumnIdentifiers(new Object[]{"ID", "NAME", "QUANTITY", "IN"});
        Object[] row = new Object[4];
        for (int i = 0; i < data.size(); i++) {
            row[0] = ((ItemsPojo) data.get(i)).getItem_id();

            row[1] = ((ItemsPojo) data.get(i)).getItem_name();

            row[2] = ((ItemsPojo) data.get(i)).getItem_quantity();
            row[3] = ((ItemsPojo) data.get(i)).getItem_quantity_in();
          

            model.addRow(row);
        }
        this.table.setModel(model);

    }
    
    
    
    void setTransaction(int item_id,String item_name,String transaction_quantity,
    String transaction_quantity_in,String receipt_no,String officer_in_charge) {

        String nullValue = "--";
        
        String query = "INSERT INTO transactions_table(" + Keys.KEY_ITEM_ID + "," + Keys.KEY_ITEM_NAME + ", " + Keys.KEY_ITEM_TYPE + ","
                + "" + Keys.KEY_TRANSACTION_QUANTITY + ","
                + "" + Keys.KEY_TRANSACTION_TYPE + ","
                + "" + Keys.KEY_TRANSACTION_QUANTITY_IN + "," + Keys.KEY_TRANSACTION_TO + "," + Keys.KEY_TRANSACTION_FROM + ","
                + "" + Keys.KEY_TRANSACTION_CASH + "," + Keys.KEY_TRANSACTION_RECEIPT_RECIEVED + "," + Keys.KEY_TRANSACTION_RECEIPT_GIVEN + ","
                + "" + Keys.KEY_TRANSACTION_OFFICER_INCHARGE + "," + Keys.KEY_TRANSACTION_TIME + ")"
                + " VALUES ('" + item_id + "',"
                + "'" + item_name+ "',"
                + "'" + this.storeType + "',"
                
                + "'" + transaction_quantity + "',"
                
                + "'" + Keys.KEY_TRANSACTION_TYPE + "',"
                
                + "'" + transaction_quantity_in + "',"
                
                + "'" + this.txt_item_to.getText() + "',"
                + "'" + nullValue + "',"
                + "'" + nullValue+ "',"
               // + "'" + this.txt_item_receipt_no.getText() + "',"
                + "'" + nullValue + "',"
                + "'" + receipt_no + "',"
                + "'" + officer_in_charge + "',now())";

        if (methods.executeSQlQueryN(query) == 1) {
            registerReceiptNo(item_id,item_name, receipt_no);
        }
        else{
            System.out.println("Error setTransaction");
        }
    }
    
    
    
    void updateExistingItem(String officer_in_charge,String item_new_quantity,
            int item_id,String item_name,String transaction_quantity,String transaction_quantity_in,String receipt_no) {
        
        String query = "UPDATE " + Keys.KEY_ITEMS_TABLE + " SET " + Keys.KEY_ITEM_QUANTITY + "='" + item_new_quantity + "'"
                + "," + Keys.KEY_ITEM_UPDATED_AT + "= now()"
                + "WHERE " + Keys.KEY_ITEM_ID + "= '" + item_id + "' ";

        if (methods.executeSQlQueryN(query) == 1) {
          
           setTransaction(item_id,item_name,transaction_quantity,
           transaction_quantity_in,receipt_no,officer_in_charge);
            }
        else{
            System.out.println("Error UpdateExistingItem");
        }

    }

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
String produce(){
    
        Random rand = new Random();
        int nr = rand.nextInt(50000) + 1;
        String rNo = String.valueOf(nr);
      return  ran(rNo);
}
    public String ran(String nr) {
        
        String results = null;
        if (checkReceiptNo(nr) == true) {
            results = nr;

        }
        else{
            produce();
            
        }

        return results;

    }
        public void registerReceiptNo(int item_id,String item_name, String rcNo) {
        String query = "INSERT INTO " + Keys.KEY_RECEIPT_TABLE + "(" + Keys.KEY_ITEM_NAME + ", " + Keys.KEY_ITEM_ID + ","
                + "" + Keys.KEY_RECEIPT_NO + "," + Keys.KEY_RECEIPT_TIME + ")"
                + " VALUES ('" + item_name + "','" + item_id + "','" + rcNo + "',now())";

        if (methods.executeSQlQuery(query, "INSERTED") == 1) {
             deleteCart(item_id);
            
          
        } else {
           out.println("Error registerReceipt");
        }
 
    }
        
        public void deleteCart(int item_id){
                    String query = "DELETE FROM "+Keys.KEY_CART_TABLE+"  WHERE "+Keys.KEY_ITEM_ID+" = '" +item_id+"'";
                    if(methods.executeSQlQueryN(query)==1){
                        
                    }
                    else{
                        System.out.println("Error deleteCart");
                    }
        }
    
    public ArrayList<CartPojo> ListCartItems() {
        ArrayList<CartPojo> itemsList = new ArrayList();
        try {

            Connection con = methods.getConnection();
            
            Statement st = con.createStatement();
            
            String searchQuery = "SELECT * FROM " + Keys.KEY_CART_TABLE + " ";
            ResultSet rs = st.executeQuery(searchQuery);
            while (rs.next()) {

                CartPojo data = new CartPojo(
                        rs.getInt(Keys.KEY_CART_ID), rs.getInt(Keys.KEY_ITEM_ID),
                        rs.getString(Keys.KEY_ITEM_NAME), rs.getString(Keys.KEY_ITEM_QUANTITY), 
                        rs.getString(Keys.KEY_TRANSACTION_QUANTITY),
                        rs.getString(Keys.KEY_TRANSACTION_QUANTITY_IN)
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

    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_item_name = new javax.swing.JTextField();
        txt_item_id = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_item_remove = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_item_quantity = new javax.swing.JTextField();
        txt_in = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        txt_item_to = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        txt_table_search = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_cart_area = new javax.swing.JTextArea();
        jButton3 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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
        table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("DETAILS"));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("ITEM NAME");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("ITEM ID");

        txt_item_name.setEditable(false);

        txt_item_id.setEditable(false);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("QUANTITY OUT");

        txt_item_remove.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_item_removeKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("BALANCE");

        txt_item_quantity.setEditable(false);

        txt_in.setText("IN");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_item_remove)
                            .addComponent(txt_item_name))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_item_id)
                            .addComponent(txt_item_quantity, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(123, 123, 123))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(88, 88, 88)
                        .addComponent(jLabel5)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txt_in)
                .addGap(164, 164, 164))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_item_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_item_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_item_remove, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_item_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_in)
                .addGap(23, 23, 23))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("ACTIONS"));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText("CLEAR");

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton2.setText("EXECUTE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("TO");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
                    .addComponent(txt_item_to, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7)
                        .addGap(37, 37, 37)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_item_to, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        txt_table_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_table_searchKeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("SEARCH");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("CART"));

        txt_cart_area.setEditable(false);
        txt_cart_area.setColumns(20);
        txt_cart_area.setRows(5);
        jScrollPane2.setViewportView(txt_cart_area);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );

        jButton3.setText("ADD");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jMenu1.setText("File");
        jMenu1.setMargin(new java.awt.Insets(0, 0, 0, 20));
        jMenu1.setName(""); // NOI18N

        jMenuItem3.setText("Help");
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("Exit");
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Operations");

        jMenuItem1.setText("Recieve");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuItem2.setText("Transactions");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txt_table_search, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_table_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
 public void threadExecute(){
 Thread log=new Thread(){
 public void run(){
    
    try{
        jProgressBar1.setVisible(true);
        jProgressBar1.setBorderPainted(true);
        jProgressBar1.setIndeterminate(true);
       String password = JOptionPane.showInputDialog("Enter Your Password ");
        String user_name = methods.getUserNameByPassword(password);
        String rNo=produce();
        if (!"null".equals(user_name)) {
         ArrayList<CartPojo> cartData=  ListCartItems() ;
         for(int count=0;count<cartData.size();count++){
          updateExistingItem(user_name,
            ((CartPojo) cartData.get(count)).getItem_new_quantity(),
            ((CartPojo) cartData.get(count)).getItem_id(),
            ((CartPojo) cartData.get(count)).getItem_name(),
            ((CartPojo) cartData.get(count)).getTransaction_quantity(),
            ((CartPojo) cartData.get(count)).getTransaction_quantity_in(),
            
            rNo);
         
         
         
         }
         txt_item_to.setText("");
         txt_item_to.setText("");
         setCart();
         jProgressBar1.setIndeterminate(false);
         jProgressBar1.setVisible(false);
         
        }else {
            jProgressBar1.setIndeterminate(false);
            jProgressBar1.setVisible(false);
            JOptionPane.showMessageDialog(null, "UN-REGISTERD PASSWORD..."
                    + "\n"
                    + "FIND ASSISTANCE FROM SYSTEM ADMINISTRATOR");
        }
        
        
      
    }
      catch(Exception b){
         System.out.println("Error");
      }
} 
      }   ;  
      log.start();
  }
 
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
threadExecute();       
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        TransactionsForm transactionsForm=new TransactionsForm(storeType);
        transactionsForm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        RecieveForm recieveForm=new RecieveForm(storeType);
        recieveForm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void txt_table_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_table_searchKeyReleased
    refresh();     // TODO add your handling code here:
    }//GEN-LAST:event_txt_table_searchKeyReleased

    private void tableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tableKeyReleased

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        balanceQuantity=0.0;
        int i = this.table.getSelectedRow();

        TableModel model = this.table.getModel();

        
        this.txt_item_id.setText(model.getValueAt(i, 0).toString());

        this.txt_item_name.setText(model.getValueAt(i, 1).toString());

        this.txt_item_quantity.setText(model.getValueAt(i, 2).toString());
        
        this.txt_in.setText(model.getValueAt(i, 3).toString());

        //this.txt_item_.setText(model.getValueAt(i, 3).toString());

        balanceQuantity = Double.valueOf(txt_item_quantity.getText());        
    }//GEN-LAST:event_tableMouseClicked

    private void txt_item_removeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_item_removeKeyReleased
        reduceQuantity();
    }//GEN-LAST:event_txt_item_removeKeyReleased
private void clearAll() {
          txt_item_remove.setText("");
        txt_item_id.setText("");
        txt_item_name.setText("");
        txt_item_quantity.setText("");
        //txt_item_to.setText("");
        txt_in.setText("IN");
        txt_table_search.setText("");
}
public void insertToCart(){
    String query = "INSERT INTO " + Keys.KEY_CART_TABLE + "(" + Keys.KEY_ITEM_ID + ", "
                  + "" + Keys.KEY_ITEM_NAME + ","
                   + "" + Keys.KEY_ITEM_QUANTITY+ ","
                + "" + Keys.KEY_TRANSACTION_QUANTITY + "," + Keys.KEY_TRANSACTION_QUANTITY_IN + ")"
                + " VALUES ('" + this.txt_item_id.getText() + "','" +this.txt_item_name.getText() + "','" +this.txt_item_quantity.getText() + "',"
                  + "'" + this.txt_item_remove.getText() + "','"+txt_in.getText()+"')";

        if (methods.executeSQlQuery(query, "ADDED TO CART") == 1) {
            
             txt_cart_area.append(""+this.txt_item_name.getText()+"                            "+this.txt_item_remove.getText()+""
                    + "                            "+this.txt_item_id.getText()+"  \n" );
             
             
             
             
             
            
             clearAll();
     
        } else {
               JOptionPane.showMessageDialog(null, "ERROR !!! \n Possible Duplicate Items");
        }   
}
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
             if(txt_item_remove.getText().isEmpty()||txt_item_id.getText().isEmpty()) {
                 JOptionPane.showMessageDialog(null, "Empty fields detected");
             }
             else{
             insertToCart();
             }
    }//GEN-LAST:event_jButton3ActionPerformed
 private void reduceQuantity() {

        try {
            double newBalanceQuantity = (balanceQuantity - Double.valueOf(txt_item_remove.getText()));
            if(newBalanceQuantity>=0.0){
            this.txt_item_quantity.setText(String.valueOf(newBalanceQuantity));
            }
            else{
                this.txt_item_quantity.setText("0.0");
                JOptionPane.showMessageDialog(null, "Limit Execeded");
                txt_item_remove.setText(String.valueOf(balanceQuantity));
            }
        } catch (Exception a) {
            this.txt_item_quantity.setText(String.valueOf(balanceQuantity));
            // a.printStackTrace();
        }

    }
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
            java.util.logging.Logger.getLogger(GiveForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GiveForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GiveForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GiveForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new GiveForm("null store").setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable table;
    private javax.swing.JTextArea txt_cart_area;
    private javax.swing.JLabel txt_in;
    private javax.swing.JTextField txt_item_id;
    private javax.swing.JTextField txt_item_name;
    private javax.swing.JTextField txt_item_quantity;
    private javax.swing.JTextField txt_item_remove;
    private javax.swing.JTextField txt_item_to;
    private javax.swing.JTextField txt_table_search;
    // End of variables declaration//GEN-END:variables
}
