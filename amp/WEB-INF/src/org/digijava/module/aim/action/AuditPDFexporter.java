
package org.digijava.module.aim.action;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPTableEvent;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.digijava.kernel.request.TLSUtils;
import org.digijava.kernel.translator.TranslatorWorker;
import org.digijava.module.aim.annotations.activityversioning.CompareOutput;
import org.digijava.module.aim.form.CompareActivityVersionsForm;
import org.digijava.module.aim.util.versioning.ActivityComparisonResult;

//import org.digijava.kernel.util.SiteUtils;
public class AuditPDFexporter {

    private static AuditPDFexporter auditPDFexporter;

    private static final Font titleFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, new BaseColor(0, 255, 255));
    private static final Font plainFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, new BaseColor(0, 255, 255));

    private static final BaseColor BACKGROUND_COLOR = new BaseColor(244, 244, 242);

	private static final int VALUE_NAME = 0;

    public static AuditPDFexporter getInstance() {
        if (auditPDFexporter == null) {
            auditPDFexporter = new AuditPDFexporter();
        }
        return auditPDFexporter;
    }

    private AuditPDFexporter() {

    }


    public ByteArrayOutputStream buildPDFexport(Map<String, List<CompareOutput>> outputCollectionGrouped) {
    	 ByteArrayOutputStream baos=null;
     
        
        try {

            Document document = new Document();
           baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();
            PdfPTable table = buildPdfTable(3);
            table.setWidths(new float[]{1f, 2f});
            table.setWidthPercentage(100);
            PdfPTableEvents event = new PdfPTableEvents();
            table.setTableEvent(event);
            table.getDefaultCell().setBorder(0);
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, new BaseColor(0, 0, 0));

            PdfPCell titleCell = new PdfPCell();
            //TODO this needs to be translatable
            Paragraph p1 = new Paragraph("comparsion b/n activites", headerFont);
            p1.setAlignment(Element.ALIGN_CENTER);
            titleCell.addElement(p1);
            titleCell.setColspan(3);
            titleCell.setBackgroundColor(new BaseColor(0, 102, 153));
            table.addCell(titleCell);
            document.add(table);

            Set<String> keyset = outputCollectionGrouped.keySet();
            for (String key : keyset) {
               PdfPCell mainCell = new PdfPCell();
               Paragraph pmain = new Paragraph(key,headerFont);
               pmain.setAlignment(Element.ALIGN_CENTER);
               mainCell.addElement(pmain);
               mainCell.setColspan(3);
               table.addCell(mainCell);
               
             List<CompareOutput> nameList = outputCollectionGrouped.get(key);
             CompareOutput comp = nameList.get(VALUE_NAME);
              PdfPCell oldValueCell = new PdfPCell();
               String[] value = comp.getStringOutput();
               String oldValue = value[1];
               Paragraph pold = new Paragraph(oldValue,headerFont);
               oldValueCell.addElement(pold);
 
               table.addCell(oldValueCell);
               
               PdfPCell newValueCell = new PdfPCell();
               String newValue = value[0];
               Paragraph pnew = new Paragraph(newValue,headerFont);
               newValueCell.addElement(pnew);
               table.addCell(newValueCell);
               }
            }
            
         catch (DocumentException e) {
            e.printStackTrace();
        }

        return baos;

            }
        

    private void createGeneralInfoRow(PdfPTable table, String columnName, String value) {
        createGeneralInfoRow(table, columnName, "", value);

    }

    private void createGeneralInfoRow(PdfPTable table, String columnName, String label, String value) {
        if (value == null || value.isEmpty()) {
            return;
        }
        PdfPCell cell1 = new PdfPCell();
        Paragraph p1 = new Paragraph(columnName, titleFont);
        p1.setAlignment(Element.ALIGN_RIGHT);
        cell1.addElement(p1);
        cell1.setBackgroundColor(BACKGROUND_COLOR);
        cell1.setBorder(0);
        table.addCell(cell1);

        PdfPCell cell2 = new PdfPCell(createGeneralInfoTable(label, value));
        cell2.setBorder(0);
        table.addCell(cell2);
    }
    
    private void createCompareOutput(PdfPTable table, String columnName, String label, String value) {
    	
    }

    private PdfPTable createGeneralInfoTable(String label, String value) {
        PdfPTable valueTable = new PdfPTable(2);

        if (value != null && !value.isEmpty()) {
            PdfPCell labelCell = new PdfPCell(new Paragraph(label, plainFont));
            labelCell.setBorder(0);
            PdfPCell valueCell = new PdfPCell(new Paragraph(value, plainFont));
            valueCell.setBorder(0);
            valueTable.addCell(labelCell);
            valueTable.addCell(valueCell);
        }

        return valueTable;
    }


    private static PdfPTable buildPdfTable(int columns) {
        PdfPTable table = new PdfPTable(columns);
        return table;
    }

    static class PdfPTableEvents implements PdfPTableEvent {
        /**
         * @see com.lowagie.text.pdf.PdfPTableEvent#tableLayout(com.lowagie.text.pdf.PdfPTable,
         * float[][], float[], int, int, com.lowagie.text.pdf.PdfContentByte[])
         */
        public void tableLayout(PdfPTable table, float[][] width, float[] height, int headerRows, int rowStart, PdfContentByte[] canvas) {
            // widths of the different cells of the first row
            //TODO This seems to be duplicate code but they are different libraries.
            float widths[] = width[0];
            PdfContentByte cb = canvas[PdfPTable.TEXTCANVAS];
            cb.saveState();
            
            // border for the complete table
            cb.setLineWidth(1);
            cb.setRGBColorStroke(0, 0, 0);
            cb.rectangle(widths[0], height[height.length - 1], widths[widths.length - 1] - widths[0], height[0] - height[height.length - 1]);
            cb.stroke();
            cb.restoreState();
        }
    }

	public ByteArrayOutputStream buildPDFexport1(List<ActivityComparisonResult> activityComparisonResultList) {
		// TODO Auto-generated method stub
		return null;
	}
}
