/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storemanagment;

import java.util.Date;

/**
 *
 * @author kimani kogi
 */
public class TransactionsPojo {
  private int transaction_id;
  private int item_id;
  private String item_name;
  private String item_type;
  private String transaction_quantity;
  private String transaction_quantity_in;
  private String transaction_type;
  private String transaction_to;
  private String transaction_from;
  private String transaction_cash;
  private String transaction_receipt_no_in;
  private String transaction_receipt_no_out;
  private String transaction_officer_incharge;
  private Date transaction_time;
  private String transaction_time_string;

    public TransactionsPojo(int transaction_id, int item_id, String item_name, String item_type, String transaction_quantity, String transaction_quantity_in, String transaction_type, String transaction_to, String transaction_from, String transaction_cash, String transaction_receipt_no_in, String transaction_receipt_no_out, String transaction_officer_incharge, String transaction_time_string) {
        this.transaction_id = transaction_id;
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_type = item_type;
        this.transaction_quantity = transaction_quantity;
        this.transaction_quantity_in = transaction_quantity_in;
        this.transaction_type = transaction_type;
        this.transaction_to = transaction_to;
        this.transaction_from = transaction_from;
        this.transaction_cash = transaction_cash;
        this.transaction_receipt_no_in = transaction_receipt_no_in;
        this.transaction_receipt_no_out = transaction_receipt_no_out;
        this.transaction_officer_incharge = transaction_officer_incharge;
        this.transaction_time_string = transaction_time_string;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
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

    public String getTransaction_quantity() {
        return transaction_quantity;
    }

    public void setTransaction_quantity(String transaction_quantity) {
        this.transaction_quantity = transaction_quantity;
    }

    public String getTransaction_quantity_in() {
        return transaction_quantity_in;
    }

    public void setTransaction_quantity_in(String transaction_quantity_in) {
        this.transaction_quantity_in = transaction_quantity_in;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getTransaction_to() {
        return transaction_to;
    }

    public void setTransaction_to(String transaction_to) {
        this.transaction_to = transaction_to;
    }

    public String getTransaction_from() {
        return transaction_from;
    }

    public void setTransaction_from(String transaction_from) {
        this.transaction_from = transaction_from;
    }

    public String getTransaction_cash() {
        return transaction_cash;
    }

    public void setTransaction_cash(String transaction_cash) {
        this.transaction_cash = transaction_cash;
    }

    public String getTransaction_receipt_no_in() {
        return transaction_receipt_no_in;
    }

    public void setTransaction_receipt_no_in(String transaction_receipt_no_in) {
        this.transaction_receipt_no_in = transaction_receipt_no_in;
    }

    public String getTransaction_receipt_no_out() {
        return transaction_receipt_no_out;
    }

    public void setTransaction_receipt_no_out(String transaction_receipt_no_out) {
        this.transaction_receipt_no_out = transaction_receipt_no_out;
    }

    public String getTransaction_officer_incharge() {
        return transaction_officer_incharge;
    }

    public void setTransaction_officer_incharge(String transaction_officer_incharge) {
        this.transaction_officer_incharge = transaction_officer_incharge;
    }

    public Date getTransaction_time() {
        return transaction_time;
    }

    public void setTransaction_time(Date transaction_time) {
        this.transaction_time = transaction_time;
    }

    public String getTransaction_time_string() {
        return transaction_time_string;
    }

    public void setTransaction_time_string(String transaction_time_string) {
        this.transaction_time_string = transaction_time_string;
    }
  
  
  
}
