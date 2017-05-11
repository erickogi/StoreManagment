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
public class UserAcc {
    


  private String id;
  private String accountname;
  private String appfee;

 
  
  public UserAcc(String Id, String Accountname,String Appfe)
  {
    this.id = Id;
    this.accountname = Accountname;
    this.appfee=Appfe;
 
  }
  
  public String getId()
  {
   return this.id;
 }
  
  public String getAccName()
  {
    return this.accountname;
  }
  public String getAccFee()
  {
    return this.appfee;
  }
 
}
