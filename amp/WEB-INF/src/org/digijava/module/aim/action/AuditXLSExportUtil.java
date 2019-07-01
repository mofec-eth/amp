package org.digijava.module.aim.action;

import static org.apache.poi.ss.usermodel.CellStyle.BORDER_THIN;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class AuditXLSExportUtil {
    
    public static HSSFCellStyle createTitleStyle(HSSFWorkbook wb){
        HSSFCellStyle titleCS = wb.createCellStyle();
        wb.createCellStyle();
        titleCS.setWrapText(true);
        titleCS.setFillForegroundColor(HSSFColor.BROWN.index);
        HSSFFont fontHeader = wb.createFont();
        fontHeader.setFontName(HSSFFont.FONT_ARIAL);
        fontHeader.setFontHeightInPoints((short) 10);
        fontHeader.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        titleCS.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleCS.setFont(fontHeader);
        titleCS.setBorderBottom(BORDER_THIN);
        titleCS.setBorderLeft(BORDER_THIN);
        titleCS.setBorderRight(BORDER_THIN);
        titleCS.setBorderTop(BORDER_THIN);
        return titleCS;
    }
    
    public static HSSFCellStyle  createOrdinaryStyle(HSSFWorkbook wb){
        HSSFCellStyle  cs = wb.createCellStyle();
        cs.setWrapText(true);
        cs.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
        cs.setBorderBottom(BORDER_THIN);
        cs.setBorderLeft(BORDER_THIN);
        cs.setBorderRight(BORDER_THIN);
        cs.setBorderTop(BORDER_THIN);
        return cs;
    }
    
    public static String htmlToXLSFormat(String stringToFormat) {
        String newValue = stringToFormat.toString().replaceAll("\\<.*?>","");
        String newValues = newValue.replaceAll("&nbsp;", "\n");
        String newVal = newValues.replaceAll("<br>","");
        return newVal;
    }
}
