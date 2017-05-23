/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storemanagment;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
import java.sql.Date;

/**
 *
 * @author kimani kogi
 */
public class FooterOnly extends PdfPageEventHelper {
private final String officerInCharge;
private final String receiptNO;
//private final String transactionId;
private final Date date;

    public FooterOnly(String officerInCharge, String receiptNO,Date date) {
        this.officerInCharge = officerInCharge;
        this.receiptNO = receiptNO;
       // this.transactionId = transactionId;
        this.date=date;
    }
   
    /**
     *
     * @param writer
     * @param document
     */
    @Override
    public void onStartPage(PdfWriter writer, Document document) {
      //  ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("     R.NO :"+receiptNO), 30, 800, 0);
      // ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(""+date), 550, 800, 0);
    }

    /**
     *
     * @param writer
     * @param document
     */
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Officer In Charge : "+officerInCharge), 110, 30, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Sign : _________________       " + document.getPageNumber()), 550, 30, 0);
    }

}

