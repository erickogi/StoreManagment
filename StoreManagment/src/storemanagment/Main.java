/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storemanagment;

import LogingRgestration.login1;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.URI;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import storemanagment.BackUpRestore.Restore;
import storemanagment.BackUpRestore.backup;
import storemanagment.Configs.AddUsers;
import storemanagment.Configs.Settings;
import storemanagment.Configs.about;
import storemanagment.Give.GiveForm;
import storemanagment.Recieve.RecieveForm;
import storemanagment.Transactions.TransactionsForm;
//import storemanagment.BackRestore.Restore;

/**
 *
 * @author kimani kogi
 */
public final class Main extends javax.swing.JFrame {
    
    Methods methods=new Methods();
    //login set
    public static String loggedInName;
    public static String hash="";
    public static int on;
    public static  int logged=0;
    public static int  ch=0;
    
    
    
      String filePathi;
   String tti;
  String fileurlpi = null;
  //end login set
    
    /**
     * Creates new form Main
     */
        public void deleteAllCart() {
        //String query = "DELETE FROM "+Keys.KEY_CART_TABLE+"  WHERE "+Keys.KEY_ITEM_ID+" = '" +item_id+"'";
        String query = "DELETE FROM " + Keys.KEY_RECEIVED_CART_TABLE + " ";
        if (methods.executeSQlQueryN(query) == 1) {
            //JOptionPane.showMessageDialog(null, "READY");
        } else {
            System.out.println("Error deleteCartR");
        }
    }
           public void deleteAlCart() {
        //String query = "DELETE FROM "+Keys.KEY_CART_TABLE+"  WHERE "+Keys.KEY_ITEM_ID+" = '" +item_id+"'";
        String query = "DELETE FROM " + Keys.KEY_CART_TABLE + " ";
        if (methods.executeSQlQueryN(query) == 1) {
           // JOptionPane.showMessageDialog(null, "READY");
        } else {
            System.out.println("Error deleteCartG");
        }
    }
           public static void applyQualityRenderingHints(Graphics2D g2d) {

    g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
    g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

}
    public BufferedImage image(BufferedImage master1){
         BufferedImage masked=null;
         BufferedImage master = master1;
         int diameter = Math.min(master.getWidth(), master.getHeight());
         BufferedImage mask = new BufferedImage(master.getWidth(), master.getHeight(), BufferedImage.TYPE_INT_ARGB);
         Graphics2D g2d = mask.createGraphics();
         applyQualityRenderingHints(g2d);
         g2d.fillOval(0, 0, diameter - 1, diameter - 1);
         g2d.dispose();
         masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
         g2d = masked.createGraphics();
         applyQualityRenderingHints(g2d);
         int x = (diameter - master.getWidth()) / 2;
         int y = (diameter - master.getHeight()) / 2;
         g2d.drawImage(master, x, y, null);
         g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
         g2d.drawImage(mask, 0, 0, null);
         g2d.dispose();
        return masked;
    }
    
    public Main() {
        initComponents();
        setTilteImage();
        pic();
        getTime();
       if(logged==0){
           deleteAlCart();
           deleteAllCart();
         login();
       }
    }
     public final void pic(){
         BufferedImage d;
         Image f;
    try {
         Container cont=this.getContentPane();
            //;
        d = ImageIO.read(Main.class.getResource("/storemanagment/admin.jpg"));
        if(showimg()!=null){
           f=showimg().getScaledInstance(labale_background.getWidth(), labale_background.getHeight(),Image.SCALE_SMOOTH);
         // 
        }
        else{
             f=image(ImageIO.read(Main.class.getResource("/storemanagment/admin.jpg"))).getScaledInstance(labale_background.getWidth(), labale_background.getHeight(),Image.SCALE_SMOOTH);
        }
        
       labale_background.setIcon(new ImageIcon(f));
    } catch (IOException ex) {
        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    }   catch (Exception ex) {       
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
      public BufferedImage showimg()
    throws Exception
  {
      BufferedImage img = null;
    try
    {
      
                Connection con = methods.getConnection();
      Statement st2 = con.createStatement();
      
      ResultSet res7 = st2.executeQuery("SELECT imgurl FROM prefrences  WHERE id=1");
      if (res7.next()) {
        this.filePathi = res7.getString("imgurl");
      } else {
       // JOptionPane.showMessageDialog(null, "error loading image \n  make sure image is in images folder ");
      }
      st2.close();
      res7.close();
      con.close();
      String op = "image";
      if (this.filePathi.equals(op))
      {
       // this.img.setIcon(null);
       // this.img.setIcon(null);
       // this.img.setText(" no image");
      }
      else
      {
        
        try
        {
          img = image(ImageIO.read(new File(this.filePathi)));
          this.fileurlpi = this.filePathi.replace("\\", "\\\\");
        }
        catch (IOException e)
        {
          //JOptionPane.showMessageDialog(null, "error loading image \n  make sure image is in images folder ");
          
          //this.img.setIcon(null);
          //this.img.setText(" no image");
        }
//        Image dimg = img.getScaledInstance(this.img.getWidth(), this.img.getHeight(), 4);
//        
//        ImageIcon icon = new ImageIcon(dimg);
//        this.img.setText("");
        //this.img.setIcon(icon);
      }
    }
    catch (Exception ex)
    {
      System.out.println(ex.getMessage());
    }
    return img;
  }
     public final void login(){
Thread log=new Thread(){
public void run(){
    
     try{
      sleep(1000);
      a();
      
     }
      catch(Exception b){
         System.out.println("Error");
      }
} 
      }   ;  
      log.start();
  }  
     public void a(){
        login1 a=new login1();
        //this.setVisible(true);
        this.setEnabled(false);
        a.setVisible(true);
        
      
    }
     public void seten(){
        this.setEnabled(true);
    }
     public final  void getTime(){

Thread clock=new Thread(){
public void run(){
    
    String x;
    String z;
for(;;){
Calendar cal=new GregorianCalendar();
        int month=cal.get(Calendar.MONTH)+1;
        switch (month) {
            case 1:
                z="Jan";
                break;
            case 2:
                z="Feb";
                break;
            case 3:
                z="Mar";
                break;
            case 4:
                z="Apr";
                break;
            case 5:
                z="May";
                break;
            case 6:
                z="June";
                break;
            case 7:
                z="July";
                break;
            case 8:
                z="Aug";
                break;
            case 9:
                z="Sept";
                break;
            case 10:
                z="Oct";
                break;
            case 11:
                z="Nov";
                break;
            default:
                z="Dec";
                break;
        }
        
        int year=cal.get(Calendar.YEAR);
        int day=cal.get(Calendar.DAY_OF_MONTH);
        
        int pmam=cal.get(Calendar.AM_PM);
       if(pmam==1){
           x="PM";
       }
       else{
            x="AM";
       }
        int second=cal.get(Calendar.SECOND);
        int min=cal.get(Calendar.MINUTE);
        if(min==1){
            
           //x="PM";
       }
       else{
           // x="AM";
       }
        int hour=cal.get(Calendar.HOUR);
     //   checkLoanApplications1();//i did this in another thread to change the time interval 
        txttymer.setFont(new java.awt.Font("Tahoma", 1, 16));
       
        Border pb=BorderFactory.createEmptyBorder(10, 60, 30, 30);
         txttymer.setBorder(BorderFactory.createCompoundBorder(pb, pb));
         
        txttymer.setText("DATE:   "+day+" "+z+" "+year+"   TIME:  "+hour+":"+min+":"+second+":"+x);
        if(logged>0){
            setEnabled();
            logged=0;
        }
         if(ch==1){
          
           
          ch=0;
       }
try{
sleep(1000);
}
catch(InterruptedException ex){

}
}
}
};
clock.start();    



}
     public void setEnabled(){
      this.setEnabled(true);
      this.setVisible(true);
  }
     public Color setTilteImage(){
        Color c=null;
        try {
            Methods n=new Methods();
            String t= n.setTitle();
            this.setTitle(t);
            txt_title.setText(t);
            String i=n.setIconImage();
            this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(i)));
            
            String col=n.selectcolor();
             c=new Color(Integer.parseInt(col));
           // jPanel1.setBackground(c);
            Container cont=this.getContentPane();
            cont.getWidth();
            cont.setBackground(c);
                        
            jPanel1.setBackground(c);
            
            
            
            jToolBar1.setBackground(c);
            this.setForeground(c);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
}
     void setNewFrameTransaction(String classTo){
         
         TransactionsForm transactionsForm=new TransactionsForm(classTo);
        transactionsForm.setVisible(true);
     }
    void setNewFrameRecieve(String classTo) {

        RecieveForm recieveForm = new RecieveForm(classTo);
        recieveForm.setVisible(true);
    }

    void setNewFrameGive(String classTo) {

        GiveForm giveForm = new GiveForm(classTo);
        giveForm.setVisible(true);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        txttymer = new javax.swing.JLabel();
        labale_background_pic = new javax.swing.JLabel();
        labale_background = new javax.swing.JLabel();
        txt_title = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenuItem27 = new javax.swing.JMenuItem();
        jMenuItem28 = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem26 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenuItem23 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem24 = new javax.swing.JMenuItem();
        jMenuItem25 = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        jMenuItem6.setText("jMenuItem6");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setRollover(true);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/store50/store-icon-red.png"))); // NOI18N
        jButton1.setText("CENTRAL STORE");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setMargin(new java.awt.Insets(1, 14, 1, 14));
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/store50/icon-stationery.jpg"))); // NOI18N
        jButton2.setText("STATIONERY STORE");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setMargin(new java.awt.Insets(1, 14, 1, 14));
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/store50/kitchen.jpg"))); // NOI18N
        jButton3.setText("KITCHEN STORE");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setMargin(new java.awt.Insets(1, 14, 1, 14));
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/store50/HardwareIcon_copy2.png"))); // NOI18N
        jButton4.setText("HARDWARE STORE");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setMargin(new java.awt.Insets(1, 14, 1, 14));
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/store50/icon_farm.png"))); // NOI18N
        jButton5.setText("FARM STORE");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setMargin(new java.awt.Insets(1, 14, 1, 14));
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton5);

        txttymer.setText("jLabel1");
        jToolBar1.add(txttymer);

        labale_background.setText("jLabel1");

        txt_title.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        txt_title.setText("jLabel1");
        txt_title.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 1532, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(labale_background_pic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txt_title))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(514, 514, 514)
                        .addComponent(labale_background, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txt_title)
                .addGap(36, 36, 36)
                .addComponent(labale_background, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(401, 401, 401)
                .addComponent(labale_background_pic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu1.setText("File");

        jMenuItem18.setText("Backup");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem18);

        jMenuItem19.setText("Restore");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem19);

        jMenuItem27.setText("XLS Transactions");
        jMenuItem27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem27ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem27);

        jMenuItem28.setText("XLS Inventory");
        jMenuItem28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem28ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem28);

        jMenuItem20.setText("Log-Out");
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem20);

        jMenuItem21.setText("Exit");
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem21);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Recieve");

        jMenuItem2.setText("Central Store");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem2);

        jMenuItem3.setText("Stationery Store");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuItem4.setText("Kitchen Store");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuItem12.setText("Hardware Store");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem12);

        jMenuItem13.setText("Farm Store");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem13);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Issue");

        jMenuItem5.setText("Central Store");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem5);

        jMenuItem7.setText("Stationery Store");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem7);

        jMenuItem14.setText("Kitchen Store");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem14);

        jMenuItem8.setText("Hardware Store");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem8);

        jMenuItem15.setText("Farm Store");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem15);

        jMenuBar1.add(jMenu4);

        jMenu5.setText("Transactions");

        jMenuItem9.setText("Central Store");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem9);

        jMenuItem10.setText("Stationery Store");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem10);

        jMenuItem11.setText("Kitchen Store");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem11);

        jMenuItem16.setText("Hardware Store");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem16);

        jMenuItem17.setText("Farm Store");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem17);

        jMenuBar1.add(jMenu5);

        jMenu2.setText("Returnables ");

        jMenuItem26.setText("View\\Return");
        jMenuItem26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem26ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem26);

        jMenuBar1.add(jMenu2);

        jMenu6.setText("Settings");

        jMenuItem22.setText("System Settings");
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem22);

        jMenuItem23.setText("Admin Settings");
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem23);

        jMenuBar1.add(jMenu6);

        jMenu7.setText("Help");

        jMenuItem24.setText("About");
        jMenuItem24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem24ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem24);

        jMenuItem25.setText("Manual");
        jMenuItem25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem25ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem25);

        jMenuBar1.add(jMenu7);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      //  storeDefiner=new StoreDefiner("Central Store",101);
        setNewFrameTransaction("Central Store");    
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        setNewFrameTransaction("Stationery Store");   
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        setNewFrameTransaction("Kitchen Store");    
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        setNewFrameTransaction("Hardware Store");
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        setNewFrameTransaction("Farm Store");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        setNewFrameRecieve("Kitchen Store");
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        setNewFrameGive("Hardware Store");
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
        a();
        logged=0;
        this.setEnabled(false);
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        setNewFrameTransaction("Central Store"); 
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
         setNewFrameTransaction("Stationery Store"); 
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
       setNewFrameTransaction("Kitchen Store");        
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        setNewFrameTransaction("Hardware Store");        
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        setNewFrameTransaction("Farm Store");
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
         setNewFrameRecieve("Central Store");
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
       setNewFrameRecieve("Stationery Store");
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        setNewFrameRecieve("Hardware Store");
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
       setNewFrameRecieve("Farm Store");
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
      setNewFrameGive("Central Store");
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
       setNewFrameGive("Stationery Store");
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        setNewFrameGive("Kitchen Store");
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
         setNewFrameGive("Farm Store");
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
       backup backUp=new backup();
       backUp.setVisible(true);
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
       Restore restore=new Restore();
       restore.setVisible(true);
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void jMenuItem24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem24ActionPerformed
      about ab=new about();
      ab.setVisible(true);
    }//GEN-LAST:event_jMenuItem24ActionPerformed

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
        Settings settings=new Settings();
        settings.setVisible(true);
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem23ActionPerformed
        if(hash.length()<0){
            JOptionPane.showMessageDialog(null, "YOU MUST HAVE ADMINISTRATOR CLEARANCE");
        }
        else{
            AddUsers addUsers=new AddUsers();
            addUsers.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem23ActionPerformed

    private void jMenuItem25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem25ActionPerformed
       try
    {
      String url = "help.htm";
      Desktop.getDesktop().browse(URI.create(url));
    }
    catch (IOException e1)
    {
       JOptionPane.showMessageDialog(null, "NOT AVAILABLE");
    }
    }//GEN-LAST:event_jMenuItem25ActionPerformed

    private void jMenuItem26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem26ActionPerformed
        loaned ln=new loaned();
        ln.setVisible(true);
    }//GEN-LAST:event_jMenuItem26ActionPerformed

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
        System.exit(1);
    }//GEN-LAST:event_jMenuItem21ActionPerformed

    private void jMenuItem27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem27ActionPerformed
        int result;
        
    chooser = new JFileChooser(); 
    chooser.setCurrentDirectory(new java.io.File("."));
    chooser.setDialogTitle(choosertitle);
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    //
    // disable the "All files" option.
    //
    chooser.setAcceptAllFileFilterUsed(false);
    //    
    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
      System.out.println("getCurrentDirectory(): " 
         +  chooser.getCurrentDirectory());
      System.out.println("getSelectedFile() : " 
         +  chooser.getSelectedFile());
      chooser.getSelectedFile();
      
      
      xls();
      }
    else {
      System.out.println("No Selection ");
      }


    }//GEN-LAST:event_jMenuItem27ActionPerformed

    private void jMenuItem28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem28ActionPerformed
         int result;
        
    chooser = new JFileChooser(); 
    chooser.setCurrentDirectory(new java.io.File("."));
    chooser.setDialogTitle(choosertitle);
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    //
    // disable the "All files" option.
    //
    chooser.setAcceptAllFileFilterUsed(false);
    //    
    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
      System.out.println("getCurrentDirectory(): " 
         +  chooser.getCurrentDirectory());
      System.out.println("getSelectedFile() : " 
         +  chooser.getSelectedFile());
      chooser.getSelectedFile();
      
      
      inventory();
      }
    else {
      System.out.println("No Selection ");
      }


    }//GEN-LAST:event_jMenuItem28ActionPerformed
    private void jButton1ActionPerformed() {                                         
                



    } 
    public void xls(){
            try{

        Connection con = methods.getConnection();
Statement statement = con.createStatement();
FileOutputStream fileOut;
fileOut = new FileOutputStream(""+chooser.getSelectedFile()+"\\transactions.xls");
HSSFWorkbook workbook = new HSSFWorkbook();
HSSFSheet worksheet = workbook.createSheet("Sheet 0");
Row row1 = worksheet.createRow((short)0);
row1.createCell(0).setCellValue("T.ID");
row1.createCell(1).setCellValue("T.ITEM");
row1.createCell(2).setCellValue("T.QUANTITY");
row1.createCell(3).setCellValue("T.UNIT");

row1.createCell(4).setCellValue("T.TYPE");
row1.createCell(5).setCellValue("T,TO");
row1.createCell(6).setCellValue("T.FORM");
row1.createCell(7).setCellValue("T.I.CASH");
row1.createCell(8).setCellValue("T.T.CASH");
row1.createCell(9).setCellValue("T.R.IN");
row1.createCell(10).setCellValue("T.R.OUT");

row1.createCell(11).setCellValue("T.DATE");
row1.createCell(12).setCellValue("T.OFFICER");
row1.createCell(13).setCellValue("T.REC.BY");


// public static final String KEY_TRANSACTION_ID="transaction_id";
//    public static final String KEY_TRANSACTION_QUANTITY="transaction_quantity";
//    public static final String KEY_TRANSACTION_TYPE="transaction_type";
//    public static final String KEY_TRANSACTION_REVERT_STATUS="transaction_revert_status";
//    
//    public static final String KEY_TRANSACTION_QUANTITY_IN="transaction_quantity_in";
//    public static final String KEY_TRANSACTION_TO="transaction_to";
//    public static final String KEY_TRANSACTION_FROM="transaction_from";
//    public static final String KEY_TRANSACTION_CASH="transaction_cash";
//    public static final String KEY_TRANSACTION_RECEIPT_RECIEVED="transaction_receipt_no_in";
//    public static final String KEY_TRANSACTION_RECEIPT_GIVEN="transaction_receipt_no_out";
//    public static final String KEY_TRANSACTION_OFFICER_INCHARGE="transaction_officer_incharge";
//    public static final String KEY_TRANSACTION_TIME="transaction_time";
//    
//    
//    public static final String KEY_TRANSACTION_ITEM_CASH="transaction_item_cash";
//    public static final String KEY_TRANSACTION_PURCHASE_ORDER_NO="transaction_purchase_order_no";
//    public static final String KEY_TRANSACTION_FROM_ADDRESS="transaction_from_address";
//    public static final String KEY_TRANSACTION_RECEIVED_BY="transaction_received_by";
//    public static final String KEY_TRANSACTION_RECEIVER_DESIGNATION="transaction_receiver_designation";
//    public static final String KEY_TRANSACTION_DEPARTMENT="transaction_department";
//    public static final String KEY_TRANSACTION_ITEM_DEIVERED_BY="transaction_delivered_by";
//row1.createCell(10).setCellValue("DATE");
Row row2 ;
ResultSet rs = statement.executeQuery("SELECT * FROM "+Keys.KEY_TRANSACTION_TABLE+"");
while(rs.next()){
int a = rs.getRow();


row1.createCell(0).setCellValue("T.ID");
row1.createCell(1).setCellValue("T.ITEM");
row1.createCell(2).setCellValue("T.QUANTITY");
row1.createCell(3).setCellValue("T.UNIT");

row1.createCell(4).setCellValue("T.TYPE");
row1.createCell(5).setCellValue("T,TO");
row1.createCell(6).setCellValue("T.FORM");
row1.createCell(7).setCellValue("T.I.CASH");
row1.createCell(8).setCellValue("T.T.CASH");
row1.createCell(9).setCellValue("T.R.IN");
row1.createCell(10).setCellValue("T.R.OUT");

row1.createCell(11).setCellValue("T.DATE");
row1.createCell(12).setCellValue("T.OFFICER");
row1.createCell(13).setCellValue("T.REC.BY");
row2 = worksheet.createRow((short)a);
row2.createCell(0).setCellValue(rs.getString(Keys.KEY_TRANSACTION_ID));
row2.createCell(1).setCellValue(rs.getString(Keys.KEY_ITEM_NAME));
row2.createCell(2).setCellValue(rs.getString(Keys.KEY_TRANSACTION_QUANTITY));
row2.createCell(3).setCellValue(rs.getString(Keys.KEY_TRANSACTION_QUANTITY_IN));
row2.createCell(4).setCellValue(rs.getString(Keys.KEY_TRANSACTION_TYPE));
row2.createCell(5).setCellValue(rs.getString(Keys.KEY_TRANSACTION_TO));
row2.createCell(6).setCellValue(rs.getString(Keys.KEY_TRANSACTION_FROM));
row2.createCell(7).setCellValue(rs.getString(Keys.KEY_TRANSACTION_ITEM_CASH));
row2.createCell(8).setCellValue(rs.getString(Keys.KEY_TRANSACTION_CASH));
row2.createCell(9).setCellValue(rs.getString(Keys.KEY_TRANSACTION_RECEIPT_RECIEVED));

row2.createCell(10).setCellValue(rs.getString(Keys.KEY_TRANSACTION_RECEIPT_GIVEN));
row2.createCell(11).setCellValue(rs.getString(Keys.KEY_TRANSACTION_TIME));
row2.createCell(12).setCellValue(rs.getString(Keys.KEY_TRANSACTION_OFFICER_INCHARGE));
row2.createCell(13).setCellValue(rs.getString(Keys.KEY_TRANSACTION_RECEIVED_BY));

}
workbook.write(fileOut);
fileOut.flush();
fileOut.close();
rs.close();statement.close();

con.close();
System.out.println("Export Success");

}catch(SQLException ex){
System.out.println(ex);
}catch(IOException ioe){
System.out.println(ioe);
}
}
    public void inventory(){
            try{

        Connection con = methods.getConnection();
Statement statement = con.createStatement();
FileOutputStream fileOut;
fileOut = new FileOutputStream(""+chooser.getSelectedFile()+"\\inventory.xls");
HSSFWorkbook workbook = new HSSFWorkbook();
HSSFSheet worksheet = workbook.createSheet("Sheet 0");
Row row1 = worksheet.createRow((short)0);
row1.createCell(0).setCellValue("ID");
row1.createCell(1).setCellValue("ITEM");
row1.createCell(2).setCellValue("QUANTITY");
row1.createCell(3).setCellValue("UNITS");
row1.createCell(4).setCellValue("STORE");

Row row2 ;
ResultSet rs = statement.executeQuery("SELECT * FROM "+Keys.KEY_ITEMS_TABLE+"");
while(rs.next()){
int a = rs.getRow();
row2 = worksheet.createRow((short)a);
row2.createCell(0).setCellValue(rs.getString(Keys.KEY_ITEM_ID));
row2.createCell(1).setCellValue(rs.getString(Keys.KEY_ITEM_NAME));
row2.createCell(2).setCellValue(rs.getString(Keys.KEY_ITEM_QUANTITY));
row2.createCell(3).setCellValue(rs.getString(Keys.KEY_ITEM_QUANTITY_IN));
row2.createCell(4).setCellValue(rs.getString(Keys.KEY_ITEM_TYPE));


}
workbook.write(fileOut);
fileOut.flush();
fileOut.close();
rs.close();statement.close();

con.close();

System.out.println("Export Success");
}catch(SQLException ex){
System.out.println(ex);
}catch(IOException ioe){
System.out.println(ioe);
}
}
   JFileChooser chooser;
   String choosertitle;

String filePath;
String tt;
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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem24;
    private javax.swing.JMenuItem jMenuItem25;
    private javax.swing.JMenuItem jMenuItem26;
    private javax.swing.JMenuItem jMenuItem27;
    private javax.swing.JMenuItem jMenuItem28;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel labale_background;
    private javax.swing.JLabel labale_background_pic;
    private javax.swing.JLabel txt_title;
    private javax.swing.JLabel txttymer;
    // End of variables declaration//GEN-END:variables
}
