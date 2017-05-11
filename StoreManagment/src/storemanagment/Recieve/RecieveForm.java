/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storemanagment.Recieve;

import java.awt.Color;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import storemanagment.Give.GiveForm;
import storemanagment.ItemsPojo;
import storemanagment.Keys;
import storemanagment.Methods;
import storemanagment.Printing;
import storemanagment.ReceivedCart;
import storemanagment.Transactions.TransactionsForm;
import storemanagment.TransactionsPojo;

/**
 *
 * @author kimani kogi
 */
public class RecieveForm extends javax.swing.JFrame {

    TransactionsPojo transactionsPojo;
    ItemsPojo itemPojo;
    Methods methods = new Methods();
    
    private double totalC=0.0;

    ButtonGroup radiog = new ButtonGroup();
    private boolean newItem = false;
    private double balanceQuantity = 0.0;
    private String transactionType=Keys.KEY_TRANSACTION_RECEIVE_EXISTING;

//     txt_item_add;
//     txt_item_cash;
//     txt_item_from;
//     txt_item_id;
//     txt_item_name;
//     txt_item_quantity;
//     txt_item_receipt_no;
//     txt_quantity_in;
//     txt_table_search;
    /**
     * Creates new form RecieveForm
     */
    private String storeType;
    private void setCart() {
        txt_cart_area.setText(null);
        txt_cart_area.append("Item :                            Qty :                            Id  \n");
        txt_cart_area.append(uline + "\n");

    }
    public RecieveForm(String storeType) {
        this.storeType = storeType;
        initComponents();
        jProgressBar1.setVisible(false);
        this.setTitle(storeType + " -Recieve Form");
        radiog.add(jRadioButton_existing_item);
        radiog.add(jRadioButton_new_item);
        jRadioButton_existing_item.setSelected(true);
        findItems();
        setCart();
         setTilteImage();
         deleteAllCart();
        jRadioButton_existing_item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txt_item_name.setEditable(false);
                txt_item_quantity.setEditable(false);
                txt_quantity_in.setEditable(false);
                txt_item_add.setEditable(true);
                newItem = false;
                transactionType=Keys.KEY_TRANSACTION_RECEIVE_EXISTING;

            }
        });
        jRadioButton_new_item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txt_item_name.setEditable(true);
                txt_item_quantity.setEditable(true);
                txt_quantity_in.setEditable(true);
                txt_item_add.setEditable(false);
                txt_item_id.setText("");
                newItem = true;
                transactionType=Keys.KEY_TRANSACTION_RECIEVE_NEW;
            }
        });
    }

    public RecieveForm() {
        initComponents();
        txt_item_cash.setText("0");
        radiog.add(jRadioButton_existing_item);
        radiog.add(jRadioButton_new_item);
        jRadioButton_existing_item.setSelected(true);
        findItems();
        jProgressBar1.setVisible(false);
        this.setTitle(storeType + " -Recieve Form");
        jRadioButton_existing_item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txt_item_name.setEditable(false);
                txt_item_quantity.setEditable(false);
                txt_quantity_in.setEditable(false);
                newItem = false;

            }
        });
        jRadioButton_new_item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txt_item_name.setEditable(true);
                txt_item_quantity.setEditable(true);
                txt_quantity_in.setEditable(true);
                txt_item_id.setText("");
                newItem = true;

            }
        });
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
            Logger.getLogger(RecieveForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
}
    boolean checkEmpty() {
        boolean okay = false;
        if (newItem) {
            if (txt_item_name.getText().isEmpty()
                    || txt_item_quantity.getText().isEmpty()
                    || txt_quantity_in.getText().isEmpty()
                    || txt_item_cash.getText().isEmpty())
                   // || txt_item_receipt_no.getText().isEmpty()
                   // || txt_item_from.getText().isEmpty())
                    {

                JOptionPane.showMessageDialog(null, "Fill All Details");

            } else {
                okay = true;
            }
        } else if (txt_item_name.getText().isEmpty() 
                || txt_item_id.getText().isEmpty()
                || txt_item_quantity.getText().isEmpty() 
                || txt_quantity_in.getText().isEmpty()
                || txt_item_cash.getText().isEmpty()
                || txt_item_add.getText().isEmpty())
               // || txt_item_receipt_no.getText().isEmpty()
               // || txt_item_from.getText().isEmpty()) 
        {
            JOptionPane.showMessageDialog(null, "Fill All Details");
        } else {
            okay = true;
        }
        return okay;
    }
   boolean checkLower(){
         boolean okay = false;
         
            if (
                     txt_item_receipt_no.getText().isEmpty()
                    
                    || txt_item_from.getText().isEmpty())
                
                {

                JOptionPane.showMessageDialog(null, "Fill All Details");

            } else {
                okay = true;
            }
         
           return okay;
         
    }

    void progressBarTrue() {
        jProgressBar1.setVisible(true);
        jProgressBar1.setBorderPainted(true);
        jProgressBar1.setIndeterminate(true);
    }

    void progressBarFalse() {
        jProgressBar1.setVisible(false);
        jProgressBar1.setBorderPainted(false);
        jProgressBar1.setIndeterminate(false);
    }

    void insertNewItem(
            String item_name,
            String item_type,
            String item_quantity,
            String transaction_quantity,
            String item_quantity_in,
            String item_cash,
            int item_id,
            String officer_in_charge,
            String rNo,
            String from,
            String receipt_in     
    ) {

        
        
       
            
            
        
        String query = "INSERT INTO items_table("
                + "" + Keys.KEY_ITEM_NAME + ", "
                + "" + Keys.KEY_ITEM_TYPE + ","
                + "" + Keys.KEY_ITEM_QUANTITY + ","
                + "" + Keys.KEY_ITEM_QUANTITY_IN + ","
                + "" + Keys.KEY_ITEM_UPDATED_AT + ")"
                + " VALUES ("
                + "'" + item_name + "',"
                + "'" + storeType + "',"
                + "'" + item_quantity + "',"
                + "'" + item_quantity_in+ "',"
                + "now())";

        if (methods.executeSQlQueryN(query) == 1) {
             int id=getItemIdByName(item_name);
            setTransaction(
                    item_name,
                    item_type,
                    item_quantity,
                    transaction_quantity,
                    item_quantity_in,
                    item_cash,
                    id,
                    officer_in_charge,
                    rNo,
                    from,
                    receipt_in,
                    Keys.KEY_TRANSACTION_RECIEVE_NEW
            );
        } else {

        }
        

    }

    void setTransaction(
            String item_name,
            String item_type,
            String item_quantity,
            String transaction_quantity,
            String item_quantity_in,
            String item_cash,
            int item_id,
            String officer_in_charge,
            String rNo,
            String from,
            String receipt_in,
            String transactiontype) {
            //int id=getItemIdByName(item_name);
        String nullValue = "--";
        
        String query = "INSERT INTO transactions_table("
                + "" + Keys.KEY_ITEM_ID + ","
                + "" + Keys.KEY_ITEM_NAME + ","
                + " " + Keys.KEY_ITEM_TYPE + ","
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
                + "'" + item_name + "',"
                + "'" + this.storeType + "',"
                + "'" + transaction_quantity + "',"
                + "'" + transactiontype + "',"
                + "'" + item_quantity_in + "',"
                + "'" + nullValue + "',"
                + "'" + from + "',"
                + "'" + item_cash + "',"
                + "'" + receipt_in + "',"
                + "'" + rNo + "',"
                + "'" + officer_in_charge + "',now())";

        if (methods.executeSQlQueryN(query) == 1) {
            if(transactiontype.equals( Keys.KEY_TRANSACTION_RECEIVE_EXISTING)){
            registerReceiptNo(item_id, rNo);
            }
        }
    }

    void updateExistingItem(
            String item_name,
            String item_type,
            String item_quantity,
            String transaction_quantity,
            String item_quantity_in,
            String item_cash,
            int item_id,
            String officer_in_charge,
            String rNo,
            String from,
            String receipt_in 
    ) {

        String query = "UPDATE " + Keys.KEY_ITEMS_TABLE + " "
                + "SET " + Keys.KEY_ITEM_QUANTITY + "='" + item_quantity + "'"
                
                + "," + Keys.KEY_ITEM_UPDATED_AT + "= now() "
                
                + "WHERE " + Keys.KEY_ITEM_ID + "= '" + item_id + "' ";

        if (methods.executeSQlQueryN(query) == 1) {
            setTransaction(
                     item_name,
                    item_type,
                    item_quantity,
                    transaction_quantity,
                    item_quantity_in,
                    item_cash,
                    item_id,
                    officer_in_charge,
                    rNo,
                    from,
                    receipt_in,
                    
                    Keys.KEY_TRANSACTION_RECEIVE_EXISTING);
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


    public int getItemIdByName(String itemName) {
        int userName = 0;
        try {

            Connection con = methods.getConnection();
            // Connection con = getConnection();
            Statement st = con.createStatement();
            String searchQuery = "SELECT item_id FROM `items_table` WHERE `item_name`='" + itemName + "' ";
            ResultSet rs = st.executeQuery(searchQuery);
            while (rs.next()) {

                userName = rs.getInt("item_id");

            }
            st.close();
            rs.close();
            con.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return userName;

    }

    public void registerReceiptNo(int item_id, String rcNo) {
        String query = "INSERT INTO " + Keys.KEY_RECEIPT_TABLE + "(" + Keys.KEY_ITEM_NAME + ", " + Keys.KEY_ITEM_ID + ","
                + "" + Keys.KEY_RECEIPT_NO + "," + Keys.KEY_RECEIPT_TIME + ")"
                + " VALUES ('" + this.txt_item_name.getText() + "','" + item_id + "','" + rcNo + "',now())";

        if (methods.executeSQlQueryN(query) == 1) {
            deleteCart(item_id);
            //clearTop();
            
        } else {

        }

    }

    private void clearAll() {
        txt_item_add.setText("");
        txt_item_cash.setText("");
        txt_item_from.setText("");
        txt_item_id.setText("");
        txt_item_name.setText("");
        txt_item_quantity.setText("");
        txt_item_receipt_no.setText("");
        txt_quantity_in.setText("");
        txt_table_search.setText("");

        jRadioButton_existing_item.setSelected(true);
        txt_item_name.setEditable(false);
        txt_item_quantity.setEditable(false);
        txt_quantity_in.setEditable(false);
        txt_item_add.setEditable(true);
        newItem = false;
        balanceQuantity = 0.0;
        totalC=0.0;
        txt_cart_area.setText("");
    }
    void clearTop(){
        txt_item_add.setText("");
        txt_item_cash.setText("");
        
        txt_item_id.setText("");
        txt_item_name.setText("");
        txt_item_quantity.setText("");
        
        txt_quantity_in.setText("");
        txt_table_search.setText("");
        
        jRadioButton_existing_item.setSelected(true);
        txt_item_name.setEditable(false);
        txt_item_quantity.setEditable(false);
        txt_quantity_in.setEditable(false);
        txt_item_add.setEditable(true);
        newItem = false;
        balanceQuantity = 0.0;
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
        model.setColumnIdentifiers(new Object[]{"ID", "NAME", "QUANTITY", "IN", "DATE"});
        Object[] row = new Object[5];
        for (int i = 0; i < data.size(); i++) {
            row[0] = ((ItemsPojo) data.get(i)).getItem_id();

            row[1] = ((ItemsPojo) data.get(i)).getItem_name();

            row[2] = ((ItemsPojo) data.get(i)).getItem_quantity();
            row[3] = ((ItemsPojo) data.get(i)).getItem_quantity_in();
            row[4] = ((ItemsPojo) data.get(i)).getItem_updated_at_string();

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
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_item_name = new javax.swing.JTextField();
        txt_item_id = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_item_quantity = new javax.swing.JTextField();
        txt_quantity_in = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_item_add = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_item_cash = new javax.swing.JTextField();
        jRadioButton_new_item = new javax.swing.JRadioButton();
        jRadioButton_existing_item = new javax.swing.JRadioButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        txt_item_receipt_no = new javax.swing.JTextField();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel9 = new javax.swing.JLabel();
        txt_item_from = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_total_cash = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_table_search = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_cart_area = new javax.swing.JTextArea();
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

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("ITEM NAME");

        txt_item_name.setEditable(false);

        txt_item_id.setEditable(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("ITEM ID");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("ITEM QUANTITY");

        txt_item_quantity.setEditable(false);

        txt_quantity_in.setEditable(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("IN");

        txt_item_add.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_item_addKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("ADD");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("CASH");

        txt_item_cash.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_item_cashKeyReleased(evt);
            }
        });

        jRadioButton_new_item.setText("NEW");

        jRadioButton_existing_item.setText("EXISTING");

        jButton3.setText("ADD");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_item_quantity)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6)
                            .addComponent(txt_item_add))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_quantity_in)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel8))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(txt_item_cash, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_item_name, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jRadioButton_new_item))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jRadioButton_existing_item)
                            .addComponent(txt_item_id, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 11, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton_new_item)
                    .addComponent(jRadioButton_existing_item))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_item_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_item_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_item_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_quantity_in, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel6)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_item_add, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_item_cash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(jButton3))
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
        jScrollPane1.setViewportView(table);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("ACTIONS"));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText("CLEAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton2.setText("EFFECT");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("RECIEPT NO");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("FROM");

        txt_total_cash.setEditable(false);

        jLabel10.setText("TOTAL CASH");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txt_item_receipt_no, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(109, 109, 109))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txt_total_cash)
                                .addGap(28, 28, 28)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txt_item_from)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel10))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_total_cash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_item_from, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_item_receipt_no, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Cart"));

        txt_cart_area.setEditable(false);
        txt_cart_area.setColumns(20);
        txt_cart_area.setRows(5);
        jScrollPane2.setViewportView(txt_cart_area);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txt_table_search, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addContainerGap())
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_table_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

        jMenuItem1.setText("Give");
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

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        GiveForm giveForm = new GiveForm(storeType);
        giveForm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        TransactionsForm transactionsForm = new TransactionsForm(storeType);
        transactionsForm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItem2ActionPerformed
    public ArrayList<ReceivedCart> ListCartItems() {
        ArrayList<ReceivedCart> itemsList = new ArrayList();
        try {

            Connection con = methods.getConnection();

            Statement st = con.createStatement();

            String searchQuery = "SELECT * FROM " + Keys.KEY_RECEIVED_CART_TABLE+ " ";
            ResultSet rs = st.executeQuery(searchQuery);
            while (rs.next()) {

                ReceivedCart data = new ReceivedCart(
                        rs.getInt(Keys.KEY_CART_ID), 
                        rs.getInt(Keys.KEY_ITEM_ID),
                        rs.getString(Keys.KEY_ITEM_NAME),
                        rs.getString(Keys.KEY_ITEM_QUANTITY),
                        rs.getString(Keys.KEY_TRANSACTION_QUANTITY),
                        rs.getString(Keys.KEY_TRANSACTION_QUANTITY_IN),
                        rs.getString(Keys.KEY_TRANSACTION_CASH),
                        rs.getString(Keys.KEY_TRANSACTION_TYPE)
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
private void runn(){
    String password = JOptionPane.showInputDialog("Enter Your Password ");
        String user_name = methods.getUserNameByPassword(password);
            jButton2.setEnabled(false);
        if (!"null".equals(user_name)) {

            if (checkLower()) {
                try{
              ArrayList<ReceivedCart> items = ListCartItems();
              String rNo=produce();
              String from=txt_item_from.getText();
              String reciept_in=txt_item_receipt_no.getText();
              String totalCash=txt_total_cash.getText();
              progressBarTrue();
            for(int a=0;a<items.size();a++){
            
            String item_name=items.get(a).getItem_name();
            String item_type=items.get(a).getTransaction_type();
            String item_quantity=items.get(a).getItem_new_quantity();
            String item_quantity_in=items.get(a).getTransaction_quantity_in();
            String item_cash=items.get(a).getTransaction_cash();
            int item_id=items.get(a).getItem_id();
            String transaction_quantity=items.get(a).getTransaction_quantity();
                

                if (item_type.equals(Keys.KEY_TRANSACTION_RECIEVE_NEW)) {
//       
                    
                    insertNewItem(item_name,item_type,item_quantity,transaction_quantity,item_quantity_in,item_cash,item_id,user_name,rNo,from,reciept_in);
                }
                    

                    
                else if(item_type.equals(Keys.KEY_TRANSACTION_RECEIVE_EXISTING)){
                        
                    updateExistingItem(item_name,item_type,item_quantity,transaction_quantity,item_quantity_in,item_cash,item_id,user_name,rNo,from,reciept_in);
                }

            }
            Printing printing=new Printing(rNo, user_name, items,String.valueOf(totalC),from);
            //Printing(String transactionReceiptNo, String officer, ArrayList<CartPojo> items,String totalCash,String from)
            
            jButton2.setEnabled(true);
            progressBarFalse();
            refresh();
            jButton2.setEnabled(false);
                }
                catch(Exception m){
                    m.printStackTrace();
                }
        }else{
             JOptionPane.showMessageDialog(null, "Fill All Required Fields");   
            }
        }
            
            else {
            JOptionPane.showMessageDialog(null, "UN-REGISTERD PASSWORD..."
                    + "\n"
                    + "FIND ASSISTANCE FROM SYSTEM ADMINISTRATOR");
        }
            
        
}
        public void threadExecute() {
        Thread log = new Thread() {
            public void run() {

               runn();
            }
        };
        log.start();
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        threadExecute();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        clearAll();

    }//GEN-LAST:event_jButton1ActionPerformed
    void existingItemControl() {
        jRadioButton_existing_item.setSelected(true);
        txt_item_name.setEditable(false);
        txt_item_quantity.setEditable(false);
        txt_quantity_in.setEditable(false);
        txt_item_add.setEditable(true);

        newItem = false;
    }
    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        int i = this.table.getSelectedRow();

        TableModel model = this.table.getModel();

        existingItemControl();
        this.txt_item_id.setText(model.getValueAt(i, 0).toString());

        this.txt_item_name.setText(model.getValueAt(i, 1).toString());

        this.txt_item_quantity.setText(model.getValueAt(i, 2).toString());

        this.txt_quantity_in.setText(model.getValueAt(i, 3).toString());

        balanceQuantity = Double.valueOf(txt_item_quantity.getText());


    }//GEN-LAST:event_tableMouseClicked

    private void txt_table_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_table_searchKeyReleased
        refresh();        // TODO add your handling code here:
    }//GEN-LAST:event_txt_table_searchKeyReleased

    private void txt_item_addKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_item_addKeyReleased
        addQuantity();
    }//GEN-LAST:event_txt_item_addKeyReleased

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        Calc n=new Calc();
    }//GEN-LAST:event_jMenuItem5ActionPerformed
    final String uline = "__________________________________________________________________________________";
    final String dline = "----------------------------------------------------------------------------------";
  
    
    public void insertToCart(int item_id) {
        String  query = "INSERT INTO " + Keys.KEY_RECEIVED_CART_TABLE + "("
                + "" + Keys.KEY_ITEM_ID + ", "
                + "" + Keys.KEY_ITEM_NAME + ","
                + "" + Keys.KEY_ITEM_QUANTITY + ","
                + "" + Keys.KEY_TRANSACTION_QUANTITY + ","
                + "" + Keys.KEY_TRANSACTION_QUANTITY_IN + ","
                + "" + Keys.KEY_TRANSACTION_CASH + ","
                + Keys.KEY_TRANSACTION_TYPE + ")"
                + " VALUES ("
                + "'" + item_id + "',"
                + "'" + this.txt_item_name.getText() + "',"
                + "'" + this.txt_item_quantity.getText() + "',"
                + "'" + this.txt_item_add.getText() + "',"
                + "'" + txt_quantity_in.getText() + "',"
                + "'" + txt_item_cash.getText() + "',"
                
              + "'"+transactionType+"')";

        if (methods.executeSQlQueryN(query) == 1) {
if(newItem){
            txt_cart_area.append("" + this.txt_item_name.getText() + " \n                           " + this.txt_item_quantity.getText() + ""
                    + "                            " + item_id + "  \n");

            clearTop();
}
else{
    txt_cart_area.append("" + this.txt_item_name.getText() + " \n                           " + this.txt_item_add.getText() + ""
                    + "                            " + item_id + "  \n");

            clearTop();
}

        } else {
            JOptionPane.showMessageDialog(null, "ERROR !!! \n Possible Duplicate Items");
        }
    }
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       if (checkEmpty()) {
              try{
            if(txt_item_cash.getText().isEmpty()){
                 JOptionPane.showMessageDialog(null, "Enter Cash");
            }
           else {
                totalC=totalC+Double.valueOf(txt_item_cash.getText());
                txt_total_cash.setText(String.valueOf(totalC));
            }
        }
        catch(Exception a){
            
        } 
        if(newItem){
        insertToCart(0);
       }
       else{
          insertToCart(Integer.valueOf(txt_item_id.getText())); 
           
       }
        
        
       
       }
       
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txt_item_cashKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_item_cashKeyReleased
        try{
            int n=Integer.valueOf(txt_item_cash.getText());
        }
        catch(Exception nm){
            txt_item_cash.setText("0");
             JOptionPane.showMessageDialog(null, "Numerical Values Only..If Null Use 0");
            
        }
    }//GEN-LAST:event_txt_item_cashKeyReleased
    private void addQuantity() {

        try {
            double newBalanceQuantity = (balanceQuantity + Double.valueOf(txt_item_add.getText()));
            this.txt_item_quantity.setText(String.valueOf(newBalanceQuantity));
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
            java.util.logging.Logger.getLogger(RecieveForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RecieveForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RecieveForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RecieveForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RecieveForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JRadioButton jRadioButton_existing_item;
    private javax.swing.JRadioButton jRadioButton_new_item;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable table;
    private javax.swing.JTextArea txt_cart_area;
    private javax.swing.JTextField txt_item_add;
    private javax.swing.JTextField txt_item_cash;
    private javax.swing.JTextField txt_item_from;
    private javax.swing.JTextField txt_item_id;
    private javax.swing.JTextField txt_item_name;
    private javax.swing.JTextField txt_item_quantity;
    private javax.swing.JTextField txt_item_receipt_no;
    private javax.swing.JTextField txt_quantity_in;
    private javax.swing.JTextField txt_table_search;
    private javax.swing.JTextField txt_total_cash;
    // End of variables declaration//GEN-END:variables
    public void deleteCart(int item_id) {
        String query = "DELETE FROM " + Keys.KEY_RECEIVED_CART_TABLE + "  WHERE " + Keys.KEY_ITEM_ID + " = '" + item_id + "'";
        if (methods.executeSQlQueryN(query) == 1) {

        } else {
            System.out.println("Error deleteCart");
        }
    }

    public void deleteAllCart() {
        //String query = "DELETE FROM "+Keys.KEY_CART_TABLE+"  WHERE "+Keys.KEY_ITEM_ID+" = '" +item_id+"'";
        String query = "DELETE FROM " + Keys.KEY_RECEIVED_CART_TABLE + " ";
        if (methods.executeSQlQueryN(query) == 1) {
            JOptionPane.showMessageDialog(null, "READY");
        } else {
            System.out.println("Error deleteCart");
        }
    }

}
