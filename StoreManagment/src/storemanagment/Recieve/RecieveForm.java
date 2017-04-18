/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storemanagment.Recieve;

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

    ButtonGroup radiog = new ButtonGroup();
    private boolean newItem = false;
    private double balanceQuantity = 0.0;

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

    public RecieveForm(String storeType) {
        this.storeType = storeType;
        initComponents();
        jProgressBar1.setVisible(false);
        this.setTitle(storeType + " -Recieve Form");
        radiog.add(jRadioButton_existing_item);
        radiog.add(jRadioButton_new_item);
        jRadioButton_existing_item.setSelected(true);
        findItems();
        jRadioButton_existing_item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txt_item_name.setEditable(false);
                txt_item_quantity.setEditable(false);
                txt_quantity_in.setEditable(false);
                txt_item_add.setEditable(true);
                newItem = false;

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

            }
        });
    }

    public RecieveForm() {
        initComponents();

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

    boolean checkEmpty() {
        boolean okay = false;
        if (newItem) {
            if (txt_item_name.getText().isEmpty()
                    || txt_item_quantity.getText().isEmpty() || txt_quantity_in.getText().isEmpty()
                    || txt_item_cash.getText().isEmpty()
                    || txt_item_receipt_no.getText().isEmpty() || txt_item_from.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Fill All Details");

            } else {
                okay = true;
            }
        } else if (txt_item_name.getText().isEmpty() || txt_item_id.getText().isEmpty()
                || txt_item_quantity.getText().isEmpty() || txt_quantity_in.getText().isEmpty()
                || txt_item_cash.getText().isEmpty() || txt_item_add.getText().isEmpty()
                || txt_item_receipt_no.getText().isEmpty() || txt_item_from.getText().isEmpty()) {
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

    void insertNewItem(String officer_in_charge) {

        progressBarTrue();
        String query = "INSERT INTO items_table(" + Keys.KEY_ITEM_NAME + ", " + Keys.KEY_ITEM_TYPE + "," + Keys.KEY_ITEM_QUANTITY + ","
                + "" + Keys.KEY_ITEM_QUANTITY_IN + "," + Keys.KEY_ITEM_UPDATED_AT + ")"
                + " VALUES ('" + this.txt_item_name.getText() + "','" + this.storeType + "','" + this.txt_item_quantity.getText() + "',"
                + "'" + this.txt_quantity_in.getText() + "',now())";

        if (methods.executeSQlQueryN(query) == 1) {

            setTransaction(officer_in_charge, txt_item_quantity.getText(), getItemIdByName(this.txt_item_name.getText()), Keys.KEY_TRANSACTION_RECIEVE_NEW);
        } else {

        }

    }

    void setTransaction(String officer_in_charge, String quantity, int item_id, String transactionType) {

        String nullValue = "--";
        String randomReceiptNo = "rcpt";
        String query = "INSERT INTO transactions_table(" + Keys.KEY_ITEM_ID + "," + Keys.KEY_ITEM_NAME + ", " + Keys.KEY_ITEM_TYPE + ","
                + "" + Keys.KEY_TRANSACTION_QUANTITY + ","
                + "" + Keys.KEY_TRANSACTION_TYPE + ","
                + "" + Keys.KEY_TRANSACTION_QUANTITY_IN + "," + Keys.KEY_TRANSACTION_TO + "," + Keys.KEY_TRANSACTION_FROM + ","
                + "" + Keys.KEY_TRANSACTION_CASH + "," + Keys.KEY_TRANSACTION_RECEIPT_RECIEVED + "," + Keys.KEY_TRANSACTION_RECEIPT_GIVEN + ","
                + "" + Keys.KEY_TRANSACTION_OFFICER_INCHARGE + "," + Keys.KEY_TRANSACTION_TIME + ")"
                + " VALUES ('" + item_id + "','" + this.txt_item_name.getText() + "','" + this.storeType + "',"
                + "'" + quantity + "',"
                + "'" + transactionType + "',"
                + "'" + this.txt_quantity_in.getText() + "',"
                + "'" + nullValue + "',"
                + "'" + this.txt_item_from.getText() + "',"
                + "'" + this.txt_item_cash.getText() + "',"
                + "'" + this.txt_item_receipt_no.getText() + "',"
                + "'" + nullValue + "',"
                + "'" + officer_in_charge + "',now())";

        if (methods.executeSQlQueryN(query) == 1) {
            registerReceiptNo(item_id, txt_item_receipt_no.getText());
        }
    }

    void updateExistingItem(String officer_in_charge) {
        progressBarTrue();
        String query = "UPDATE " + Keys.KEY_ITEMS_TABLE + " SET ," + Keys.KEY_ITEM_QUANTITY + "='" + this.txt_item_quantity.getText() + "',"
                + "," + Keys.KEY_ITEM_UPDATED_AT + "`=now()"
                + "WHERE " + Keys.KEY_ITEM_ID + "= '" + this.txt_item_id.getText() + "' ";

        if (methods.executeSQlQueryN(query) == 1) {
            setTransaction(officer_in_charge, txt_item_add.getText(), Integer.valueOf(this.txt_item_id.getText()), Keys.KEY_TRANSACTION_RECEIVE_EXISTING);
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
                ran();

            } else {
                isNewNo = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RecieveForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isNewNo;
    }

    public String ran() {
        String results = null;
        Random rand = new Random();
        int nr = rand.nextInt(50000) + 1;
        String rNo = String.valueOf(nr);

        while (checkReceiptNo(String.valueOf(nr)) == true) {
            results = rNo;

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

        if (methods.executeSQlQuery(query, "INSERTED") == 1) {

            clearAll();
            progressBarFalse();
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
        jLabel7 = new javax.swing.JLabel();
        txt_item_from = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_item_cash = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_item_receipt_no = new javax.swing.JTextField();
        jRadioButton_new_item = new javax.swing.JRadioButton();
        jRadioButton_existing_item = new javax.swing.JRadioButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        txt_table_search = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

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

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("FROM");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("CASH");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("RECIEPT NO");

        jRadioButton_new_item.setText("NEW");

        jRadioButton_existing_item.setText("EXISTING");

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
                                .addComponent(txt_item_from, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addGap(18, 18, 18)
                                    .addComponent(txt_item_add, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))
                                .addComponent(txt_item_quantity, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabel9)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_quantity_in)
                            .addComponent(txt_item_cash)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel5))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_item_name, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_item_receipt_no, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jRadioButton_new_item))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jRadioButton_existing_item)
                                    .addComponent(txt_item_id, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 11, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
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
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_item_add, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_item_from, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_item_cash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_item_receipt_no, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        txt_table_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_table_searchKeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("SEARCH");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String password = JOptionPane.showInputDialog("Enter Your Password ");
        String user_name = methods.getUserNameByPassword(password);

        if (!"null".equals(user_name)) {

            if (checkEmpty()) {

                if (newItem) {
                    insertNewItem(user_name);
                } else {
                    updateExistingItem(user_name);
                }

            }

        } else {
            JOptionPane.showMessageDialog(null, "UN-REGISTERD PASSWORD..."
                    + "\n"
                    + "FIND ASSISTANCE FROM SYSTEM ADMINISTRATOR");
        }
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
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JRadioButton jRadioButton_existing_item;
    private javax.swing.JRadioButton jRadioButton_new_item;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    private javax.swing.JTextField txt_item_add;
    private javax.swing.JTextField txt_item_cash;
    private javax.swing.JTextField txt_item_from;
    private javax.swing.JTextField txt_item_id;
    private javax.swing.JTextField txt_item_name;
    private javax.swing.JTextField txt_item_quantity;
    private javax.swing.JTextField txt_item_receipt_no;
    private javax.swing.JTextField txt_quantity_in;
    private javax.swing.JTextField txt_table_search;
    // End of variables declaration//GEN-END:variables


}
