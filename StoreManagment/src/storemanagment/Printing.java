/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storemanagment;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFileChooser;
import org.jfree.ui.Align;

/**
 *
 * @author kimani kogi
 */
public class Printing {

    Methods method = new Methods();

    public Printing(String transactionReceiptNo, String officer, ArrayList<CartPojo> items) {
        TransactionGiven(transactionReceiptNo, officer, items);
    }
      public Printing(String transactionReceiptNo, String officer, ArrayList<ReceivedCart> items,String totalCash,String from) {
        TransactionReceived(transactionReceiptNo, officer, items,totalCash,from);
    }
        public Printing(String store, String officer, ArrayList<ItemsPojo> items,String trNo) {
        Inventory(store, officer,  items,trNo);
    }
              public Printing(String store, String officer, ArrayList<TransactionsPojo> items,String trNo,int a) {
        Transactions(store,officer,  items,trNo);
    }

    private String[] getRes() {
        String[] resl = method.getOrgDetails();

        return resl;

    }

    public final void TransactionGiven(String transactionReceiptNo, String officer, ArrayList<CartPojo> items) {
        Calendar c = Calendar.getInstance();
        Date today = c.getTime();

        java.util.Date d = (today);

        java.sql.Date DATE = new java.sql.Date(d.getTime());

        // String OrgDetails=res[1]+"\n"+DATE.toString();
        String orgDetails[] = getRes();
        String orgImg = orgDetails[0];
        String orgAbout = orgDetails[1];

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File(","));
        chooser.setDialogTitle("Save at");
        chooser.setApproveButtonText("save");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {

                Document pdfp = new Document();
                PdfWriter writer = PdfWriter.getInstance(pdfp, new FileOutputStream(new File(chooser.getSelectedFile(), transactionReceiptNo + "" + DATE + ".pdf")));

                // PdfWriter writer = PdfWriter.getInstance(pdfp, new FileOutputStream(new File(chooser.getSelectedFile(),"Group "+jComboBoxGroup.getSelectedItem().toString()+".pdf")));
                HeaderFooterPageEvent event = new HeaderFooterPageEvent(officer, transactionReceiptNo,DATE);
                writer.setPageEvent(event);
                pdfp.open();

//        Document pdfp=new Document();
//            PdfWriter.getInstance(pdfp, new FileOutputStream(new File(chooser.getSelectedFile(),transactionReceiptNo+""+DATE+".pdf")));
//                pdfp.open();
                PdfPTable header1 = new PdfPTable(1);

//                //  tbl.setWidthPercentage(100);
//                
                header1.setTotalWidth(575);
                header1.setLockedWidth(true);
                header1.addCell(createTextCell(""));
                header1.addCell(createTextCell(""));
                header1.addCell(createTextCell(""));
                header1.addCell(createTextCell(""));
                PdfPTable header = new PdfPTable(3);

//                //  tbl.setWidthPercentage(100);
//                
                header.setTotalWidth(575);
                header.setLockedWidth(true);

                header.setWidths(new int[]{1, 4, 1});

                //THE FIRST ROW
                //first column ///Logo
                if (orgImg.equals("image")) {
                    header.addCell("no image ");

                } else {
                    header.addCell(createImageCell(orgImg));

                }
                //second column ///description
                header.addCell(createTextCell(orgAbout));

                header.addCell(createTextCell(""));

                PdfPTable RecieptTilte = new PdfPTable(3);

//                //  tbl.setWidthPercentage(100);
//                
                RecieptTilte.setTotalWidth(575);
                RecieptTilte.setLockedWidth(true);
                // RecieptTilte.setHorizontalAlignment(Align.CENTER);
                RecieptTilte.setWidths(new int[]{1, 1, 1});
                RecieptTilte.setSpacingBefore(8);
                RecieptTilte.setSpacingAfter(6);
                //       RecieptTilte.getDefaultCell().setBorderWidthBottom(2);
                RecieptTilte.getDefaultCell().setBorderWidthLeft(0);
                RecieptTilte.getDefaultCell().setBorderWidthRight(0);
                RecieptTilte.addCell("");

                RecieptTilte.addCell("Transaction Receipt");

                RecieptTilte.setSpacingAfter(8);
                RecieptTilte.addCell("");

                PdfPTable RecieptitemsTitles = new PdfPTable(3);

                RecieptitemsTitles.setTotalWidth(575);
                RecieptitemsTitles.setWidths(new int[]{2, 1, 1});

                RecieptitemsTitles.setLockedWidth(true);

                RecieptitemsTitles.addCell(creatTextCellTitles("Item-Name"));
                RecieptitemsTitles.addCell(creatTextCellTitles("Quantiy"));
                RecieptitemsTitles.addCell(creatTextCellTitles("In"));
                //    RecieptitemsTitles.addCell(creatTextCellTitles("Id"));

                //  PdfPTable Recieptitems=new PdfPTable(4);
                for (int a = 0; a < items.size(); a++) {
                    RecieptitemsTitles.addCell(createTextCellcolor(((CartPojo) items.get(a)).getItem_name(), a));
                    RecieptitemsTitles.addCell(createTextCellcolor(((CartPojo) items.get(a)).getTransaction_quantity(), a));
                    RecieptitemsTitles.addCell(createTextCellcolor(((CartPojo) items.get(a)).getTransaction_quantity_in(), a));
                    //  RecieptitemsTitles.addCell(createTextCellcolor(String.valueOf(((CartPojo)items.get(a)).getItem_id()),a));

                }
                
                

                pdfp.add(header1);
                pdfp.add(header);
                pdfp.add(RecieptTilte);
                pdfp.add(RecieptitemsTitles);

                pdfp.close();

            } catch (DocumentException ex) {
                Logger.getLogger(Printing.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Printing.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Printing.class.getName()).log(Level.SEVERE, null, ex);
            }
            // jComboBoxGroup
            if (Desktop.isDesktopSupported()) {
                try {
                    File file = new File(chooser.getSelectedFile(), transactionReceiptNo + "" + DATE + ".pdf");
                    Desktop.getDesktop().open(file);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

    }
    
    
    
    
    
    
    
    
    public final void  TransactionReceived(String transactionReceiptNo, String officer, ArrayList<ReceivedCart> items,String total_cash,String from) {
        Calendar c = Calendar.getInstance();
        Date today = c.getTime();

        java.util.Date d = (today);

        java.sql.Date DATE = new java.sql.Date(d.getTime());

        // String OrgDetails=res[1]+"\n"+DATE.toString();
        String orgDetails[] = getRes();
        String orgImg = orgDetails[0];
        String orgAbout = orgDetails[1];

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File(","));
        chooser.setDialogTitle("Save at");
        chooser.setApproveButtonText("save");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {

                Document pdfp = new Document();
                PdfWriter writer = PdfWriter.getInstance(pdfp, new FileOutputStream(new File(chooser.getSelectedFile(), transactionReceiptNo + "" + DATE + ".pdf")));

                // PdfWriter writer = PdfWriter.getInstance(pdfp, new FileOutputStream(new File(chooser.getSelectedFile(),"Group "+jComboBoxGroup.getSelectedItem().toString()+".pdf")));
                HeaderFooterPageEvent event = new HeaderFooterPageEvent(officer, transactionReceiptNo,DATE);
                writer.setPageEvent(event);
                pdfp.open();

//        Document pdfp=new Document();
//            PdfWriter.getInstance(pdfp, new FileOutputStream(new File(chooser.getSelectedFile(),transactionReceiptNo+""+DATE+".pdf")));
//                pdfp.open();
                PdfPTable header1 = new PdfPTable(1);

//                //  tbl.setWidthPercentage(100);
//                
                header1.setTotalWidth(575);
                header1.setLockedWidth(true);
                header1.addCell(createTextCell(""));
                header1.addCell(createTextCell(""));
                header1.addCell(createTextCell(""));
                header1.addCell(createTextCell(""));
                PdfPTable header = new PdfPTable(3);

//                //  tbl.setWidthPercentage(100);
//                
                header.setTotalWidth(575);
                header.setLockedWidth(true);

                header.setWidths(new int[]{1, 4, 1});

                //THE FIRST ROW
                //first column ///Logo
                if (orgImg.equals("image")) {
                    header.addCell("no image ");

                } else {
                    header.addCell(createImageCell(orgImg));

                }
                //second column ///description
                header.addCell(createTextCell(orgAbout));

                header.addCell(createTextCell(""));

                PdfPTable RecieptTilte = new PdfPTable(3);

//                //  tbl.setWidthPercentage(100);
//                
                RecieptTilte.setTotalWidth(575);
                RecieptTilte.setLockedWidth(true);
                // RecieptTilte.setHorizontalAlignment(Align.CENTER);
                RecieptTilte.setWidths(new int[]{1, 1, 1});
                RecieptTilte.setSpacingBefore(8);
                RecieptTilte.setSpacingAfter(6);
                //       RecieptTilte.getDefaultCell().setBorderWidthBottom(2);
                RecieptTilte.getDefaultCell().setBorderWidthLeft(0);
                RecieptTilte.getDefaultCell().setBorderWidthRight(0);
                RecieptTilte.addCell("");

                RecieptTilte.addCell("Transaction Receipt");

                RecieptTilte.setSpacingAfter(8);
                RecieptTilte.addCell("");

                PdfPTable RecieptitemsTitles = new PdfPTable(4);

                RecieptitemsTitles.setTotalWidth(575);
                RecieptitemsTitles.setWidths(new int[]{2, 1, 1,1});

                RecieptitemsTitles.setLockedWidth(true);

                RecieptitemsTitles.addCell(creatTextCellTitles("Item-Name"));
                RecieptitemsTitles.addCell(creatTextCellTitles("Quantiy"));
                RecieptitemsTitles.addCell(creatTextCellTitles("In"));
                RecieptitemsTitles.addCell(creatTextCellTitles("Cash"));
                //    RecieptitemsTitles.addCell(creatTextCellTitles("Id"));

                //  PdfPTable Recieptitems=new PdfPTable(4);
                for (int a = 0; a < items.size(); a++) {
                    RecieptitemsTitles.addCell(createTextCellcolor(((ReceivedCart) items.get(a)).getItem_name(), a));
                    RecieptitemsTitles.addCell(createTextCellcolor(((ReceivedCart) items.get(a)).getTransaction_quantity(), a));
                    RecieptitemsTitles.addCell(createTextCellcolor(((ReceivedCart) items.get(a)).getTransaction_quantity_in(), a));
                    RecieptitemsTitles.addCell(createTextCellcolor(((ReceivedCart) items.get(a)).getTransaction_cash(), a));
                    //  RecieptitemsTitles.addCell(createTextCellcolor(String.valueOf(((CartPojo)items.get(a)).getItem_id()),a));

                }
                
                
                PdfPTable RecieptTotals = new PdfPTable(2);

//                //  tbl.setWidthPercentage(100);
//                
                RecieptTotals.setTotalWidth(575);
                RecieptTotals.setLockedWidth(true);
                // RecieptTilte.setHorizontalAlignment(Align.CENTER);
                RecieptTotals.setWidths(new int[]{1, 1});
                RecieptTotals.setSpacingBefore(8);
                RecieptTotals.setSpacingAfter(6);
                //       RecieptTilte.getDefaultCell().setBorderWidthBottom(2);
                RecieptTotals.getDefaultCell().setBorderWidthLeft(0);
                RecieptTotals.getDefaultCell().setBorderWidthRight(0);
                
               RecieptTotals.addCell(createTextCellNb("Received From : "));

                RecieptTotals.addCell(from);

                
                RecieptTotals.addCell(createTextCellNb("Total Amount Due"));
                RecieptTotals.addCell(total_cash);
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                

                pdfp.add(header1);
                pdfp.add(header);
                pdfp.add(RecieptTilte);
                pdfp.add(RecieptitemsTitles);
                pdfp.add(RecieptTotals); 

                pdfp.close();

            } catch (DocumentException ex) {
                Logger.getLogger(Printing.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Printing.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Printing.class.getName()).log(Level.SEVERE, null, ex);
            }
            // jComboBoxGroup
            if (Desktop.isDesktopSupported()) {
                try {
                    File file = new File(chooser.getSelectedFile(), transactionReceiptNo + "" + DATE + ".pdf");
                    Desktop.getDesktop().open(file);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

    }
      
    
    
    
    
    
    
     public final void Inventory(String store, String officer, ArrayList<ItemsPojo> items,String trNo) {
        Calendar c = Calendar.getInstance();
        Date today = c.getTime();

        java.util.Date d = (today);

        java.sql.Date DATE = new java.sql.Date(d.getTime());

        // String OrgDetails=res[1]+"\n"+DATE.toString();
        String orgDetails[] = getRes();
        String orgImg = orgDetails[0];
        String orgAbout = orgDetails[1];

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File(","));
        chooser.setDialogTitle("Save at");
        chooser.setApproveButtonText("save");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {

                Document pdfp = new Document();
                PdfWriter writer = PdfWriter.getInstance(pdfp, new FileOutputStream(new File(chooser.getSelectedFile(), store + "" + DATE + ".pdf")));

                // PdfWriter writer = PdfWriter.getInstance(pdfp, new FileOutputStream(new File(chooser.getSelectedFile(),"Group "+jComboBoxGroup.getSelectedItem().toString()+".pdf")));
                HeaderFooterPageEvent event = new HeaderFooterPageEvent(officer, trNo,DATE);
                writer.setPageEvent(event);
                pdfp.open();

//        Document pdfp=new Document();
//            PdfWriter.getInstance(pdfp, new FileOutputStream(new File(chooser.getSelectedFile(),transactionReceiptNo+""+DATE+".pdf")));
//                pdfp.open();
                PdfPTable header1 = new PdfPTable(1);

//                //  tbl.setWidthPercentage(100);
//                
                header1.setTotalWidth(575);
                header1.setLockedWidth(true);
                header1.addCell(createTextCell(""));
                header1.addCell(createTextCell(""));
                header1.addCell(createTextCell(""));
                header1.addCell(createTextCell(""));
                PdfPTable header = new PdfPTable(3);

//                //  tbl.setWidthPercentage(100);
//                
                header.setTotalWidth(575);
                header.setLockedWidth(true);

                header.setWidths(new int[]{1, 4, 1});

                //THE FIRST ROW
                //first column ///Logo
                if (orgImg.equals("image")) {
                    header.addCell("no image ");

                } else {
                    header.addCell(createImageCell(orgImg));

                }
                //second column ///description
                header.addCell(createTextCell(orgAbout));

                header.addCell(createTextCell(""));

                PdfPTable RecieptTilte = new PdfPTable(3);

//                //  tbl.setWidthPercentage(100);
//                
                RecieptTilte.setTotalWidth(575);
                RecieptTilte.setLockedWidth(true);
                // RecieptTilte.setHorizontalAlignment(Align.CENTER);
                RecieptTilte.setWidths(new int[]{1, 1, 1});
                RecieptTilte.setSpacingBefore(8);
                RecieptTilte.setSpacingAfter(6);
                //       RecieptTilte.getDefaultCell().setBorderWidthBottom(2);
                RecieptTilte.getDefaultCell().setBorderWidthLeft(0);
                RecieptTilte.getDefaultCell().setBorderWidthRight(0);
                RecieptTilte.addCell("");

                RecieptTilte.addCell(store+" INVENTORY");

                RecieptTilte.setSpacingAfter(8);
                RecieptTilte.addCell("");

                PdfPTable RecieptitemsTitles = new PdfPTable(3);

                RecieptitemsTitles.setTotalWidth(575);
                RecieptitemsTitles.setWidths(new int[]{2, 1, 1});

                RecieptitemsTitles.setLockedWidth(true);

                RecieptitemsTitles.addCell(creatTextCellTitles("Item-Name"));
                RecieptitemsTitles.addCell(creatTextCellTitles("Quantiy"));
                RecieptitemsTitles.addCell(creatTextCellTitles("In"));
                //    RecieptitemsTitles.addCell(creatTextCellTitles("Id"));

                //  PdfPTable Recieptitems=new PdfPTable(4);
                for (int a = 0; a < items.size(); a++) {
                    RecieptitemsTitles.addCell(createTextCellcolor(((ItemsPojo) items.get(a)).getItem_name(), a));
                    RecieptitemsTitles.addCell(createTextCellcolor(((ItemsPojo) items.get(a)).getItem_quantity(), a));
                    RecieptitemsTitles.addCell(createTextCellcolor(((ItemsPojo) items.get(a)).getItem_quantity_in(), a));
                    //  RecieptitemsTitles.addCell(createTextCellcolor(String.valueOf(((CartPojo)items.get(a)).getItem_id()),a));

                }
                
                

                pdfp.add(header1);
                pdfp.add(header);
                pdfp.add(RecieptTilte);
                pdfp.add(RecieptitemsTitles);

                pdfp.close();

            } catch (DocumentException ex) {
                Logger.getLogger(Printing.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Printing.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Printing.class.getName()).log(Level.SEVERE, null, ex);
            }
            // jComboBoxGroup
            if (Desktop.isDesktopSupported()) {
                try {
                    File file = new File(chooser.getSelectedFile(), store + "" + DATE + ".pdf");
                    Desktop.getDesktop().open(file);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

    }
    
    
    
    
    
    
      
    
    
    
    
    
    
     public final void Transactions(String store, String officer, ArrayList<TransactionsPojo> items,String trNo) {
        Calendar c = Calendar.getInstance();
        Date today = c.getTime();

        java.util.Date d = (today);

        java.sql.Date DATE = new java.sql.Date(d.getTime());

        // String OrgDetails=res[1]+"\n"+DATE.toString();
        String orgDetails[] = getRes();
        String orgImg = orgDetails[0];
        String orgAbout = orgDetails[1];

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File(","));
        chooser.setDialogTitle("Save at");
        chooser.setApproveButtonText("save");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {

                Document pdfp = new Document();
                PdfWriter writer = PdfWriter.getInstance(pdfp, new FileOutputStream(new File(chooser.getSelectedFile(), store + "tr" + DATE + ".pdf")));

                // PdfWriter writer = PdfWriter.getInstance(pdfp, new FileOutputStream(new File(chooser.getSelectedFile(),"Group "+jComboBoxGroup.getSelectedItem().toString()+".pdf")));
                HeaderFooterPageEvent event = new HeaderFooterPageEvent(officer, trNo,DATE);
                writer.setPageEvent(event);
                pdfp.open();

//        Document pdfp=new Document();
//            PdfWriter.getInstance(pdfp, new FileOutputStream(new File(chooser.getSelectedFile(),transactionReceiptNo+""+DATE+".pdf")));
//                pdfp.open();
                PdfPTable header1 = new PdfPTable(1);

//                //  tbl.setWidthPercentage(100);
//                
                header1.setTotalWidth(575);
                header1.setLockedWidth(true);
                header1.addCell(createTextCell(""));
                header1.addCell(createTextCell(""));
                header1.addCell(createTextCell(""));
                header1.addCell(createTextCell(""));
                PdfPTable header = new PdfPTable(3);

//                //  tbl.setWidthPercentage(100);
//                
                header.setTotalWidth(575);
                header.setLockedWidth(true);

                header.setWidths(new int[]{1, 4, 1});

                //THE FIRST ROW
                //first column ///Logo
                if (orgImg.equals("image")) {
                    header.addCell("no image ");

                } else {
                    header.addCell(createImageCell(orgImg));

                }
                //second column ///description
                header.addCell(createTextCell(orgAbout));

                header.addCell(createTextCell(""));

                PdfPTable RecieptTilte = new PdfPTable(3);

//                //  tbl.setWidthPercentage(100);
//                
                RecieptTilte.setTotalWidth(575);
                RecieptTilte.setLockedWidth(true);
                // RecieptTilte.setHorizontalAlignment(Align.CENTER);
                RecieptTilte.setWidths(new int[]{1, 1, 1});
                RecieptTilte.setSpacingBefore(8);
                RecieptTilte.setSpacingAfter(6);
                //       RecieptTilte.getDefaultCell().setBorderWidthBottom(2);
                RecieptTilte.getDefaultCell().setBorderWidthLeft(0);
                RecieptTilte.getDefaultCell().setBorderWidthRight(0);
                RecieptTilte.addCell("");

                RecieptTilte.addCell(store+" Transactions");

                RecieptTilte.setSpacingAfter(8);
                RecieptTilte.addCell("");

                PdfPTable RecieptitemsTitles = new PdfPTable(8);

                RecieptitemsTitles.setTotalWidth(575);
                RecieptitemsTitles.setWidths(new int[]{1, 1, 2,1,1,1,1,2});

                RecieptitemsTitles.setLockedWidth(true);

                RecieptitemsTitles.addCell(creatTextCellTitles("Item-Name"));
                RecieptitemsTitles.addCell(creatTextCellTitles("Quantiy"));
                RecieptitemsTitles.addCell(creatTextCellTitles("Type"));
                RecieptitemsTitles.addCell(creatTextCellTitles("Cash"));
                RecieptitemsTitles.addCell(creatTextCellTitles("From"));
                RecieptitemsTitles.addCell(creatTextCellTitles("To"));
                RecieptitemsTitles.addCell(creatTextCellTitles("R.No"));
                RecieptitemsTitles.addCell(creatTextCellTitles("Date"));
                //    RecieptitemsTitles.addCell(creatTextCellTitles("Id"));

                //  PdfPTable Recieptitems=new PdfPTable(4);
                for (int a = 0; a < items.size(); a++) {
                    RecieptitemsTitles.addCell(createTextCellcolor(((TransactionsPojo) items.get(a)).getItem_name(), a));
                    RecieptitemsTitles.addCell(createTextCellcolor(((TransactionsPojo) items.get(a)).getTransaction_quantity(), a));
                    RecieptitemsTitles.addCell(createTextCellcolor(((TransactionsPojo) items.get(a)).getTransaction_type(), a));
                    
                    
                    RecieptitemsTitles.addCell(createTextCellcolor(((TransactionsPojo) items.get(a)).getTransaction_cash(), a));
                    RecieptitemsTitles.addCell(createTextCellcolor(((TransactionsPojo) items.get(a)).getTransaction_from(), a));
                    RecieptitemsTitles.addCell(createTextCellcolor(((TransactionsPojo) items.get(a)).getTransaction_to(), a));
                    RecieptitemsTitles.addCell(createTextCellcolor(((TransactionsPojo) items.get(a)).getTransaction_receipt_no_out(), a));
                    RecieptitemsTitles.addCell(createTextCellcolor(((TransactionsPojo) items.get(a)).getTransaction_time_string(), a));
                    //  RecieptitemsTitles.addCell(createTextCellcolor(String.valueOf(((CartPojo)items.get(a)).getItem_id()),a));

                }
                
                

                pdfp.add(header1);
                pdfp.add(header);
                pdfp.add(RecieptTilte);
                pdfp.add(RecieptitemsTitles);

                pdfp.close();

            } catch (DocumentException ex) {
                Logger.getLogger(Printing.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Printing.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Printing.class.getName()).log(Level.SEVERE, null, ex);
            }
            // jComboBoxGroup
            if (Desktop.isDesktopSupported()) {
                try {
                    File file = new File(chooser.getSelectedFile(), store + "tr" + DATE + ".pdf");
                    Desktop.getDesktop().open(file);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

    }
    
    
    
    
    
    
    
    
    
    
    
    
    public PdfPCell createTextCellNb(String text) {

        PdfPCell cell = new PdfPCell();
        Paragraph p = new Paragraph();
       
        p.setAlignment(Element.ALIGN_CENTER);
        p.add(text);
        cell.addElement(p);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Rectangle.NO_BORDER);

        return cell;

    }

    public PdfPCell creatTextCellTitles(String text) {
        PdfPCell cell = new PdfPCell();
        Paragraph p = new Paragraph();
        p.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD));
        p.add(text);
        cell.addElement(p);
        // cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    public PdfPCell createTextCell(String text) {

        PdfPCell cell = new PdfPCell();
        Paragraph p = new Paragraph();
        p.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 16, java.awt.Font.BOLD));
        //  p.setFont(Font.BOLD);
        p.setAlignment(Element.ALIGN_CENTER);
        p.add(text);
        cell.addElement(p);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Rectangle.NO_BORDER);

        return cell;

    }

    public PdfPCell createTextCellNormal(String text) {

        PdfPCell cell = new PdfPCell();
        Paragraph p = new Paragraph();
        // p.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD,16,java.awt.Font.BOLD));
        //  p.setFont(Font.BOLD);
        p.setAlignment(Element.ALIGN_CENTER);
        p.add(text);
        cell.addElement(p);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Rectangle.NO_BORDER);

        return cell;

    }

    public PdfPCell createTextCellNormalUnderlined(String text) {

        PdfPCell cell = new PdfPCell();
        Paragraph p = new Paragraph();
        // p.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD,16,java.awt.Font.BOLD));
        //  p.setFont(Font.BOLD);
        p.setAlignment(Element.ALIGN_CENTER);

        p.add(text);

        cell.addElement(p);

        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Rectangle.NO_BORDER);

        return cell;

    }

    public PdfPCell createImageCell(String path) throws IOException {
        PdfPCell cell = null;
        try {
            Image img = Image.getInstance(path);
            cell = new PdfPCell(img, true);
            cell.setFixedHeight(30);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingTop(10);

        } catch (BadElementException ex) {
            Logger.getLogger(Printing.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Printing.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cell;
    }
    public PdfPCell createImageCellCrip(String path,PdfWriter writer) throws IOException, DocumentException {
        PdfPCell cell = null;
        try {
            Image img = Image.getInstance(path);
        float w=    img.getScaledWidth();
        float h=    img.getScaledHeight();
        
        PdfTemplate t=writer.getDirectContent().createTemplate(w, h);
        t.ellipse(0, 0, w, h);
        t.newPath();
        t.addImage(img, w, 0, 0, h, 0, -600);
        Image clipped=Image.getInstance(t);
            cell = new PdfPCell(clipped, true);
           // cell.setFixedHeight(30);
           // cell.setBorder(Rectangle.NO_BORDER);
            //cell.setBorder(R);
            cell.setPaddingTop(10);

        } catch (BadElementException ex) {
            Logger.getLogger(Printing.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Printing.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cell;
    }
    
    
    

    public PdfPCell creatTextCellHeader(String text) {
        PdfPCell cell = new PdfPCell();
        Paragraph p = new Paragraph();
        p.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD));
        p.add(text);
        cell.addElement(p);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    public PdfPCell createTextCellcolor(String text, int c) {
        PdfPCell cell = new PdfPCell();
        if (c % 2 == 0) {

            Paragraph p = new Paragraph();
            cell.setBackgroundColor(Color.CYAN);
            p.add(text);
            cell.addElement(p);
        } else {
            Paragraph p = new Paragraph();
            //cell.setBackgroundColor(Color.CYAN);
            p.add(text);
            cell.addElement(p);
        }

        return cell;

    }

}
