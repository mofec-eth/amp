package org.digijava.module.aim.action;

import static org.apache.poi.ss.usermodel.CellStyle.BORDER_THIN;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public final class AuditXLSExportUtil {
    
    private static final Integer first_COLUMN_WIDTH = 3840;
    private static final Integer COLUMN_WIDTH = 12800;
    private AuditXLSExportUtil() {
        
    }

    public static HSSFCellStyle createTitleStyle(HSSFWorkbook wb) {
        HSSFCellStyle titleCS = wb.createCellStyle();
        wb.createCellStyle();
        titleCS.setWrapText(true);
        HSSFFont fontHeader = wb.createFont();
        fontHeader.setFontName(HSSFFont.FONT_ARIAL);
        fontHeader.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        titleCS.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleCS.setFont(fontHeader);
        titleCS.setBorderLeft(BORDER_THIN);
        titleCS.setBorderRight(BORDER_THIN);
        titleCS.setBorderTop(BORDER_THIN);
        titleCS.setBorderBottom(BORDER_THIN);
        return titleCS;
    }

    public static HSSFCellStyle createOrdinaryStyle(HSSFWorkbook wb) {
        HSSFCellStyle cs = wb.createCellStyle();
        cs.setWrapText(true);
        cs.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
        cs.setBorderBottom(BORDER_THIN);
        cs.setBorderLeft(BORDER_THIN);
        cs.setBorderRight(BORDER_THIN);
        cs.setBorderTop(BORDER_THIN);
        return cs;
    }

    public static String htmlToXLSFormat(String stringToFormat) {
        String newValue = stringToFormat.toString().replaceAll("\\<.*?>", "");
        String newValues = newValue.replaceAll("&nbsp;", "\n");
        String newVal = newValues.replaceAll("<br>", "");
        return newVal;
    }

    public static void setColumnWidth(HSSFSheet sheet) {
        sheet.setColumnWidth(0, first_COLUMN_WIDTH);
        sheet.setColumnWidth(1, COLUMN_WIDTH);
        sheet.setColumnWidth(2, COLUMN_WIDTH);
    }
}
