
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
import com.lowagie.text.pdf.PdfPRow;

import org.digijava.kernel.request.TLSUtils;
import org.digijava.kernel.translator.TranslatorWorker;
import org.digijava.module.aim.annotations.activityversioning.CompareOutput;
import org.digijava.module.aim.util.versioning.ActivityComparisonResult;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AuditPDFexporter {
    
    private static final Integer VALUE_NAME = 0;
    private static AuditPDFexporter auditPDFexporter;
    ByteArrayOutputStream baos = null;
    private static final Font titleFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, new BaseColor(0, 255, 255));
    private static final Font plainFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, new BaseColor(0, 0, 0));
    private static final BaseColor BACKGROUND_COLOR = new BaseColor(244, 244, 242);

    public static AuditPDFexporter getInstance() {
        if (auditPDFexporter == null) {
            auditPDFexporter = new AuditPDFexporter();
        }
        return auditPDFexporter;
    }

    private AuditPDFexporter() {

    }

    public ByteArrayOutputStream buildPDFexport(List<ActivityComparisonResult> activityComparisonResult) {
        return generatePDFExport(activityComparisonResult);
    }

    public ByteArrayOutputStream buildPDFexport(Map<String, List<CompareOutput>> outputCollectionGrouped) {        
        try {
            Document document = createDocument();
            PdfPTable mainLayout = createHeader();
            createValues(outputCollectionGrouped, mainLayout);            
            document.add(mainLayout);
            document.close();
        } catch (DocumentException e) {
        e.printStackTrace();
    }
          return baos;
   }

    private ByteArrayOutputStream generatePDFExport(List<ActivityComparisonResult> activityComparisonResult) {
          try {
              Document document = createDocument();
              PdfPTable mainLayout = createHeader();
            for (ActivityComparisonResult cr : activityComparisonResult) {
                createGeneralInfoRow(mainLayout, "Activity ", cr.getName());
                Map<String, List<CompareOutput>> outputCollectionGrouped = cr.getCompareOutput();
                createValues(outputCollectionGrouped, mainLayout);                
            }
            document.add(mainLayout);
            document.close();        
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return baos;
    }
    
    private Document createDocument() throws DocumentException {
        Document document = new Document();
        baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();
        return document;
    }
    
    private PdfPTable createHeader() throws DocumentException {

        PdfPTable mainLayout = buildPdfTable(3);
        mainLayout.setWidths(new float[]{1f, 2f, 2f});
        mainLayout.setWidthPercentage(100);
//           PdfPTableEvents event = new PdfPTableEvents();
//           mainLayout.setTableEvent(event);
        mainLayout.getDefaultCell().setBorder(0);
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, new BaseColor(0, 0, 0));

        PdfPCell mainTitleCell = new PdfPCell();
        Paragraph pmain = new Paragraph(TranslatorWorker.translateText("Activity difference", TLSUtils.getEffectiveLangCode(),
                TLSUtils.getSiteId()), headerFont);
        pmain.setAlignment(Element.ALIGN_CENTER);
        mainTitleCell.addElement(pmain);
        mainTitleCell.setColspan(3);
        mainTitleCell.setBackgroundColor(new BaseColor(0, 102, 153));
        mainLayout.addCell(mainTitleCell);
        
        PdfPCell titleCell = new PdfPCell();
        Paragraph p1 = new Paragraph(TranslatorWorker.translateText("Value Name", TLSUtils.getEffectiveLangCode(),
                    TLSUtils.getSiteId()), headerFont);
        p1.setAlignment(Element.ALIGN_CENTER);
        titleCell.addElement(p1);
        titleCell.setBackgroundColor(new BaseColor(0, 102, 153));
        mainLayout.addCell(titleCell);

        PdfPCell prevCell = new PdfPCell();
        Paragraph p2 = new Paragraph(TranslatorWorker.translateText("Previous Version", TLSUtils.getEffectiveLangCode(),
                    TLSUtils.getSiteId()), headerFont);
        p2.setAlignment(Element.ALIGN_CENTER);
        prevCell.addElement(p2);
        titleCell.setBackgroundColor(new BaseColor(0, 102, 153));
        mainLayout.addCell(prevCell);

        PdfPCell newCell = new PdfPCell();
        Paragraph p3 = new Paragraph(TranslatorWorker.translateText("New Version", TLSUtils.getEffectiveLangCode(),
                    TLSUtils.getSiteId()), headerFont);
        p3.setAlignment(Element.ALIGN_CENTER);
        newCell.addElement(p3);
        newCell.setBackgroundColor(new BaseColor(0, 102, 153));
        mainLayout.addCell(newCell);
        return mainLayout;
    }
    
    private void createValues(Map<String, List<CompareOutput>> outputCollectionGrouped, PdfPTable mainLayout) {
        Set<String> keyset = outputCollectionGrouped.keySet();      
        for (String key : keyset) {
           PdfPCell mainCell = new PdfPCell();               
           Paragraph pmain = new Paragraph(key,plainFont);
           pmain.setAlignment(Element.ALIGN_CENTER);
           mainCell.addElement(pmain);
           mainLayout.addCell(mainCell);
           
           List<CompareOutput> nameList = outputCollectionGrouped.get(key);
           CompareOutput comp = nameList.get(VALUE_NAME);
           PdfPCell oldValueCell = new PdfPCell();
           String[] value = comp.getStringOutput();
           String oldValue = value[1];
           String old = AuditPDFExportService.htmlToPDFFormat(oldValue);
           Paragraph pold = new Paragraph(old,plainFont);
           oldValueCell.addElement(pold);            
           mainLayout.addCell(oldValueCell);
           
           PdfPCell newValueCell = new PdfPCell();
           String newValue = value[0];
           String formatValue = AuditPDFExportService.htmlToPDFFormat(newValue);
           Paragraph pnew = new Paragraph(formatValue,plainFont);
           newValueCell.addElement(pnew);
           mainLayout.addCell(newValueCell);
    }
    }

    private void createGeneralInfoRow(PdfPTable mainLayout, String columnName, String value) {
        createGeneralInfoRow(mainLayout, columnName, "", value);

    }

    private void createGeneralInfoRow(PdfPTable mainLayout, String columnName, String label, String value) {
        if (value == null || value.isEmpty()) {
            return;
        }
        PdfPCell cell1 = new PdfPCell();
        Paragraph p1 = new Paragraph(columnName, titleFont);
        p1.setAlignment(Element.ALIGN_RIGHT);
        cell1.addElement(p1);
        cell1.setBackgroundColor(BACKGROUND_COLOR);
        cell1.setBorder(0);
        mainLayout.addCell(cell1);

        PdfPCell cell2 = new PdfPCell(createGeneralInfoTable(label, value));
        cell2.setBorder(0);
        cell2.setColspan(2);
        mainLayout.addCell(cell2);
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

//    static class PdfPTableEvents implements PdfPTableEvent {
//        /**
//         * @see com.lowagie.text.pdf.PdfPTableEvent#tableLayout(com.lowagie.text.pdf.PdfPTable,
//         * float[][], float[], int, int, com.lowagie.text.pdf.PdfContentByte[])
//         */
//        public void tableLayout(PdfPTable table, float[][] width, float[] height, int headerRows, int rowStart, PdfContentByte[] canvas) {
//            // widths of the different cells of the first row
//            //TODO This seems to be duplicate code but they are different libraries.
//            float widths[] = width[0];
//            PdfContentByte cb = canvas[PdfPTable.TEXTCANVAS];
//            cb.saveState();
//            // border for the complete table
//            cb.setLineWidth(1);
//            cb.setRGBColorStroke(0, 0, 0);
//            cb.rectangle(widths[0], height[height.length - 1], widths[widths.length - 1] - widths[0], height[0] - height[height.length - 1]);
//            cb.stroke();
//            cb.restoreState();
//        }
//    }
    
}
