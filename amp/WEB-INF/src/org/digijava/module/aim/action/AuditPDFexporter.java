
package org.digijava.module.aim.action;
import com.lowagie.text.pdf.BaseFont;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.digijava.kernel.translator.TranslatorWorker;
import org.digijava.kernel.util.SiteUtils;
import org.digijava.module.aim.action.ExportActivityToPDF.PdfPTableEvents;
import org.digijava.module.aim.annotations.activityversioning.CompareOutput;
import org.digijava.module.aim.dbentity.AmpActivityVersion;
import org.digijava.module.aim.form.EditActivityForm;
import org.digijava.module.aim.util.FeaturesUtil;
import org.digijava.module.aim.util.versioning.ActivityComparisonResult;
import org.digijava.module.categorymanager.dbentity.AmpCategoryValue;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;
public class AuditPDFexporter {
private static final String FILE_NAME = null;


public static void PDFexport(Map<String, List<CompareOutput>> outputCollectionGrouped) {

    public static final int COLUMNS = 3;
    
    public static void main(String[] args) {
        writeUsingIText();
    }

    private static void writeUsingIText() {

        com.itextpdf.text.Document document = new Document();
      
       
        try {

            PdfWriter.getInstance(document, new FileOutputStream(new File(FILE_NAME)));

            //open
            document.open();
            PdfPTable mainLayout = buildPdfTable(3);
           
            mainLayout.setWidthPercentage(100);
            PdfPTableEvents event = new PdfPTableEvents();
            mainLayout.setTableEvent(event);
            mainLayout.getDefaultCell().setBorder(0);
            Paragraph p = new Paragraph();
            //p.setAlignment(Element.ALIGN_CENTER);

            document.add(p);
            Paragraph p1 = new Paragraph();
            p1.add(""); //no alignment
            p1.setAlignment(Element.ALIGN_CENTER);
            Paragraph p2 = new Paragraph();
            p2.add(""); //no alignment

            document.add(p2);
            Paragraph p3 = new Paragraph();
            p3.add(""); //no alignment

            document.add(p3);
            Font f = new Font();
            f.setStyle(Font.BOLD);
            f.setSize(8);
                String columnName="";
                String columnVal="";

                               //heading cell
                PdfPCell titleCell=new PdfPCell();

                com.lowagie.text.Font headerFont = new com.lowagie.text.Font( 11, Font.BOLD, 0, new Color(255, 255, 255));
                p1=new Paragraph("Value");
                p2=new Paragraph("First Version");
                p3=new Paragraph("Second Version");
                p1.setAlignment(Element.ALIGN_CENTER);
                titleCell.addElement((com.lowagie.text.Element) p1);
                titleCell.addElement((com.lowagie.text.Element) p2);
                titleCell.addElement((com.lowagie.text.Element) p3);
                titleCell.setColspan(2);
                titleCell.setBackgroundColor(new Color(0,102,153));
                mainLayout.addCell(titleCell);
                Object outputCollectionGrouped;
				outputCollectionGrouped.actvity();
                File actvity;
				createGeneralInfoRow(mainLayout,columnName,actvity.getName());                 
            //close
            document.close();

            System.out.println("Done");
           
         
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private static void createGeneralInfoRow(PdfPTable mainLayout, String columnName, String name) {
		// TODO Auto-generated method stub
		
	}

	private static PdfPTable buildPdfTable(int columns) {
        PdfPTable table = new PdfPTable(columns);
        if (SiteUtils.isEffectiveLangRTL()) {
            table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        }
        return table;
    }

}
