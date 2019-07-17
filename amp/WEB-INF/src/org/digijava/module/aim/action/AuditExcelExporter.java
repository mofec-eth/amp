package org.digijava.module.aim.action;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.digijava.kernel.translator.TranslatorWorker;
import org.digijava.module.aim.annotations.activityversioning.CompareOutput;
import org.digijava.module.aim.util.versioning.ActivityComparisonResult;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;

public final class AuditExcelExporter {
    private static final Integer CELL_LIMIT = 32767;
    private AuditExcelExporter() {
        
    }

    public static HSSFWorkbook generateExcel(String locale, Long siteId,
            Map<String, List<CompareOutput>> outputCollectionGrouped) {

    int rowIndex = 1; 
    HSSFSheet sheet = createWorkbook(locale, siteId); 
    getCellValues(outputCollectionGrouped, sheet, rowIndex); 
    AuditXLSExportUtil.setColumnWidth(sheet);
    return sheet.getWorkbook();
    }

    public static HSSFWorkbook generateExcel(String locale, Long siteId,
            List<ActivityComparisonResult> outputCollection) {

    int rowIndex = 1;   
    HSSFSheet sheet = createWorkbook(locale, siteId);     
    for (ActivityComparisonResult result : outputCollection) {
    int cellIndex = 0;
    String name = result.getName();
    HSSFRow nameRow = sheet.createRow(rowIndex);
    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 2));
    rowIndex++;
    HSSFCell nameCell = nameRow.createCell(cellIndex++);
    nameCell.setCellValue(name);
    nameCell.setCellStyle(AuditXLSExportUtil.createTitleStyle(sheet.getWorkbook()));        
    Map<String, List<CompareOutput>> outputCollectionGrouped = result.getCompareOutput();    
    rowIndex = getCellValues(outputCollectionGrouped, sheet, rowIndex);    
      }
    AuditXLSExportUtil.setColumnWidth(sheet);
    return sheet.getWorkbook();
     }

    public static HSSFRow createHeader(HSSFWorkbook wb, HSSFSheet sheet, String locale, Long siteId) {
        HSSFCellStyle titleCS = AuditXLSExportUtil.createTitleStyle(wb);

        int rowIndex = 0;
        int cellIndex = 0;

        HSSFRow titleRow = sheet.createRow(rowIndex++);

        HSSFCell titleCell = titleRow.createCell(cellIndex++);
        HSSFRichTextString title = new HSSFRichTextString(TranslatorWorker.translateText("Value Name", locale, siteId));
        titleCell.setCellValue(title);
        titleCell.setCellStyle(titleCS);

        HSSFCell cellName = titleRow.createCell(cellIndex++);
        HSSFRichTextString previous = new HSSFRichTextString(
                TranslatorWorker.translateText("Previous Version", locale, siteId));
        cellName.setCellValue(previous);
        cellName.setCellStyle(titleCS);

        HSSFCell cellNew = titleRow.createCell(cellIndex++);
        HSSFRichTextString newVersion = new HSSFRichTextString(
                TranslatorWorker.translateText("New Version", locale, siteId));
        cellNew.setCellValue(newVersion);
        cellNew.setCellStyle(titleCS);

        return titleRow;
    }

    public static int checkMaxCellLimit(String cellValue, HSSFSheet sheet, int rowIndex, int cellIndex, HSSFCell cell) {
        int length = cellValue.length();
        int mergeIndex = rowIndex;
        if (length > CELL_LIMIT) {
            String valueOne = cellValue.substring(0, CELL_LIMIT);
            cell.setCellValue(valueOne);
            int remain = length - CELL_LIMIT;
            for (int i = CELL_LIMIT; i < length; i += CELL_LIMIT) {
                String valueNext = null;
                HSSFRow newvalueRow = sheet.createRow(++rowIndex);
                HSSFCell valuescell = newvalueRow.createCell(cellIndex);
                if (remain < CELL_LIMIT) {
                    valueNext = cellValue.substring(i);
                } else {
                    valueNext = cellValue.substring(i, i + CELL_LIMIT);
                }
                valuescell.setCellValue(valueNext);
                HSSFWorkbook wb = sheet.getWorkbook();
                valuescell.setCellStyle(AuditXLSExportUtil.createOrdinaryStyle(wb));
                remain = length - (i + CELL_LIMIT);
            }
            sheet.addMergedRegion(new CellRangeAddress(mergeIndex, rowIndex, 0, 0));
        } else {
            cell.setCellValue(cellValue);
        }
        return rowIndex;
    }
    
    public static HSSFSheet createWorkbook(String locale, Long siteId) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(TranslatorWorker.translateText("Audit Logger"));
        createHeader(wb, sheet, locale, siteId);
         return sheet;
    }
    
    public static int getCellValues(Map<String, List<CompareOutput>> outputCollectionGrouped, HSSFSheet sheet,
            int rowIndex) {
      
      HSSFWorkbook wb = sheet.getWorkbook();  
      HSSFCellStyle  cs = AuditXLSExportUtil.createOrdinaryStyle(wb);  
      Set<String> keyset = outputCollectionGrouped.keySet();
      for (String key : keyset) {   
      int cellIndex = 0;
      HSSFRow valueRow = sheet.createRow(rowIndex);
      HSSFCell colcell = valueRow.createCell(cellIndex++);
      colcell.setCellValue(key);
      colcell.setCellStyle(cs);
    
      List<CompareOutput> nameList = outputCollectionGrouped.get(key);
      CompareOutput comp = nameList.get(0);
      HSSFCell groupcell = valueRow.createCell(cellIndex++);
      String[] value = comp.getStringOutput();
      String oldValue = value[1];
      String old = AuditXLSExportUtil.htmlToXLSFormat(oldValue);
      rowIndex = checkMaxCellLimit(old, sheet, rowIndex, cellIndex, groupcell);
      groupcell.setCellStyle(cs);
            
      HSSFCell newcell = valueRow.createCell(cellIndex);
      String newValue = value[0];
      String newVal = AuditXLSExportUtil.htmlToXLSFormat(newValue);
      rowIndex = checkMaxCellLimit(newVal, sheet, rowIndex, cellIndex, newcell);
      rowIndex++;
      newcell.setCellStyle(cs);
      }
      return rowIndex;
}
    
}
