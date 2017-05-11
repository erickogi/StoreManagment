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
public class loanedPojo {

    /**
     * @param args the command line arguments
//     */
//    `loan_id` int(100) NOT NULL AUTO_INCREMENT,
//  `item_id` int(100) NOT NULL,
//  `item_name` varchar(100) NOT NULL,
//  `item_type` varchar(70) NOT NULL,
//  `item_quantity` varchar(70) DEFAULT NULL,
//  `item_quantity_in` varchar(70) DEFAULT NULL,
//  `transaction_to` varchar(200) DEFAULT NULL,
//  `transaction_receipt_no_out` varchar(70) DEFAULT NULL,
//  `transaction_officer_incharge` varchar(70) DEFAULT NULL,
//  `transaction_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    
    private int loan_id;
    private int item_id;
    private String item_name;
    private String item_type;
    private String item_quantity;
    private String item_quantity_in;
    private String transaction_to;
    private String transaction_receipt_no;
    private String transaction_receipt_no_out;
    private String officer_incharge;
    private String transaction_time;

    public loanedPojo(int loan_id, int item_id, String item_name, String item_type, String item_quantity, String item_quantity_in, String transaction_to, String transaction_receipt_no, String transaction_receipt_no_out, String officer_incharge, String transaction_time) {
        this.loan_id = loan_id;
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_type = item_type;
        this.item_quantity = item_quantity;
        this.item_quantity_in = item_quantity_in;
        this.transaction_to = transaction_to;
        this.transaction_receipt_no = transaction_receipt_no;
        this.transaction_receipt_no_out = transaction_receipt_no_out;
        this.officer_incharge = officer_incharge;
        this.transaction_time = transaction_time;
    }

    public int getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(int loan_id) {
        this.loan_id = loan_id;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public String getItem_quantity() {
        return item_quantity;
    }

    public void setItem_quantity(String item_quantity) {
        this.item_quantity = item_quantity;
    }

    public String getItem_quantity_in() {
        return item_quantity_in;
    }

    public void setItem_quantity_in(String item_quantity_in) {
        this.item_quantity_in = item_quantity_in;
    }

    public String getTransaction_to() {
        return transaction_to;
    }

    public void setTransaction_to(String transaction_to) {
        this.transaction_to = transaction_to;
    }

    public String getTransaction_receipt_no() {
        return transaction_receipt_no;
    }

    public void setTransaction_receipt_no(String transaction_receipt_no) {
        this.transaction_receipt_no = transaction_receipt_no;
    }

    public String getTransaction_receipt_no_out() {
        return transaction_receipt_no_out;
    }

    public void setTransaction_receipt_no_out(String transaction_receipt_no_out) {
        this.transaction_receipt_no_out = transaction_receipt_no_out;
    }

    public String getOfficer_incharge() {
        return officer_incharge;
    }

    public void setOfficer_incharge(String officer_incharge) {
        this.officer_incharge = officer_incharge;
    }

    public String getTransaction_time() {
        return transaction_time;
    }

    public void setTransaction_time(String transaction_time) {
        this.transaction_time = transaction_time;
    }
    
    
    
    
    
}
