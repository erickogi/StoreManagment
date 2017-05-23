/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storemanagment;

/**
 *
 * @author kimani kogi
 */
public class Keys {
    
    
    //ITEMS DB COLUMN NAMES
    public static final String KEY_ITEMS_TABLE="items_table";
    
    
    
    public static final String KEY_ITEM_NAME="item_name";
    public static final String KEY_ITEM_ID="item_id";
    public static final String KEY_ITEM_QUANTITY="item_quantity";
    public static final String KEY_ITEM_QUANTITY_IN="item_quantity_in";
    public static final String KEY_ITEM_TYPE="item_type";
    public static final String KEY_ITEM_UPDATED_AT="item_updated_at";
    
    
    //TRANSACTIONS DB COLUMN NAMES
    public static final String KEY_TRANSACTION_TABLE="transactions_table";
    
    
    public static final String KEY_TRANSACTION_ID="transaction_id";
    public static final String KEY_TRANSACTION_QUANTITY="transaction_quantity";
    public static final String KEY_TRANSACTION_TYPE="transaction_type";
    public static final String KEY_TRANSACTION_REVERT_STATUS="transaction_revert_status";
    
    public static final String KEY_TRANSACTION_QUANTITY_IN="transaction_quantity_in";
    public static final String KEY_TRANSACTION_TO="transaction_to";
    public static final String KEY_TRANSACTION_FROM="transaction_from";
    public static final String KEY_TRANSACTION_CASH="transaction_cash";
    public static final String KEY_TRANSACTION_RECEIPT_RECIEVED="transaction_receipt_no_in";
    public static final String KEY_TRANSACTION_RECEIPT_GIVEN="transaction_receipt_no_out";
    public static final String KEY_TRANSACTION_OFFICER_INCHARGE="transaction_officer_incharge";
    public static final String KEY_TRANSACTION_TIME="transaction_time";
    
    
    public static final String KEY_TRANSACTION_ITEM_CASH="transaction_item_cash";
    public static final String KEY_TRANSACTION_PURCHASE_ORDER_NO="transaction_purchase_order_no";
    public static final String KEY_TRANSACTION_FROM_ADDRESS="transaction_from_address";
    public static final String KEY_TRANSACTION_RECEIVED_BY="transaction_received_by";
    public static final String KEY_TRANSACTION_RECEIVER_DESIGNATION="transaction_receiver_designation";
    public static final String KEY_TRANSACTION_DEPARTMENT="transaction_department";
    public static final String KEY_TRANSACTION_ITEM_DEIVERED_BY="transaction_delivered_by";
    
    
    
            
            
            
            
//             `transaction_item_cash` varchar(70) DEFAULT NULL,
//  `transaction_purchase_order_no` varchar(70) DEFAULT NULL,
//  
//  
//  
//  
//  `transaction_from_address` varchar(200) DEFAULT NULL,
//  
//  `transaction_received_by` varchar(200) DEFAULT NULL,
//  
//  `transaction_receiver_designation` varchar(200) DEFAULT NULL,
//  
//  `transaction_department` varchar(200) DEFAULT NULL,
//  
//  `transaction_delivered_by` varchar(200) DEFAULT NULL,
            
            
            
            
            
            
            
            
            
            
            
            
            
            
    //RECEIPTS DB
    public static final String KEY_RECEIPT_TABLE="transactions_reciepts";
   
    
    public static final String KEY_RECEIPT_ID="receipt_id";
    public static final String KEY_RECEIPT_TOTAL="receipt_total";
    public static final String KEY_RECEIPT_NO="receipt_no";
    public static final String KEY_RECEIPT_TIME="receipt_time";
    
    
    
    //Transaction types
    public static final String KEY_TRANSACTION_RECIEVE_NEW="Recieved New Item(Insert)";
    public static final String KEY_TRANSACTION_RECEIVE_EXISTING="Recieved Existing Item(Update)";
    public static final String KEY_TRANSACTION_RETURN_LOANED_ITEM="Returned Loaned Item";
    
    
    public static final String KEY_TRANSACTION_GIVE="Given Out";
    
     public static final String KEY_TRANSACTION_REVERT="Revert Of";
     public static final String KEY_TRANSACTION_UPDATE="Update Of";
  
    
    
    //CART
    public static final String KEY_CART_TABLE="items_cart";
    
    public static final String KEY_CART_ID="cart_id";
    
    public static final String KEY_CART_LOANEDTYPE="loanedtype";
    
    public static final String KEY_RETURNABLE="Returnable";
    public static final String KEY_NON_RETURNABLE="Non_Returnable";
    
//      `loan_id` int(100) NOT NULL AUTO_INCREMENT,
//  `item_id` int(100) NOT NULL,
//  `item_name` varchar(100) NOT NULL,
//  `item_type` varchar(70) NOT NULL,
//  `item_quantity` varchar(70) DEFAULT NULL,
//  `item_quantity_in` varchar(70) DEFAULT NULL,
//  `transaction_to` varchar(200) DEFAULT NULL,
//  `transaction_receipt_no_out` varchar(70) DEFAULT NULL,
//  `transaction_officer_incharge` varchar(70) DEFAULT NULL,
//  `transaction_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
//  PRIMARY KEY (`loan_id`)
    public static final String KEY_LOANED_TABLE="items_loaned_table";
    public static final String KEY_LOANED_ID="loan_id";
    
    
   public static final String KEY_RECEIVED_CART_TABLE="items_received_cart";
    
    
}
