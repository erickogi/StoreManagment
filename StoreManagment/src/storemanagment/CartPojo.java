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
public class CartPojo {
    private int cart_id;
    private int item_id;
    private String item_name;
    private String item_new_quantity;
    private String transaction_quantity;
    private String transaction_quantity_in;
    private String transaction_cash;

    public CartPojo(int cart_id, int item_id, String item_name, String item_new_quantity, String transaction_quantity, String transaction_quantity_in) {
        this.cart_id = cart_id;
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_new_quantity = item_new_quantity;
        this.transaction_quantity = transaction_quantity;
        this.transaction_quantity_in = transaction_quantity_in;
       // this.transaction_cash=transaction_cash;
    }

    public String getTransaction_cash() {
        return transaction_cash;
    }

    public void setTransaction_cash(String transaction_cash) {
        this.transaction_cash = transaction_cash;
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
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

    public String getItem_new_quantity() {
        return item_new_quantity;
    }

    public void setItem_new_quantity(String item_new_quantity) {
        this.item_new_quantity = item_new_quantity;
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
    
}
