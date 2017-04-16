/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storemanagment;

import java.sql.ResultSet;
import java.util.Date;
import javax.swing.table.TableModel;

/**
 *
 * @author kimani kogi
 */
public class ItemsPojo {
  private int  item_id;
  private String item_name;
  private String item_type;
  private String item_quantity;
  private String item_quantity_in;
  private Date item_updated_at;
  
  
  static TableModel resultSetToTableModel(ResultSet result)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

    public ItemsPojo(int item_id, String item_name, String item_type, String item_quantity, String item_quantity_in, Date item_updated_at) {
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_type = item_type;
        this.item_quantity = item_quantity;
        this.item_quantity_in = item_quantity_in;
        this.item_updated_at = item_updated_at;
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

    public Date getItem_updated_at() {
        return item_updated_at;
    }

    public void setItem_updated_at(Date item_updated_at) {
        this.item_updated_at = item_updated_at;
    }

}
