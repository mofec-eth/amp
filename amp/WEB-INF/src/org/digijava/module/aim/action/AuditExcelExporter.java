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

public class AuditExcelExporter {
    public static HSSFWorkbook generateExcel(String locale, Long siteId, Map<String, List<CompareOutput>> outputCollectionGrouped) {
    HSSFWorkbook wb = new HSSFWorkbook();
    HSSFSheet sheet = wb.createSheet(TranslatorWorker.translateText("Audit Logger"));
    HSSFCellStyle titleCS = AuditXLSExportUtil.createTitleStyle(wb);
    HSSFCellStyle  cs = AuditXLSExportUtil.createOrdinaryStyle(wb);
    
    int rowIndex = 0;
    int cellIndex = 0;
    
    HSSFRow titleRow = sheet.createRow(rowIndex++);
     
    HSSFCell titleCell = titleRow.createCell(cellIndex++);
    HSSFRichTextString title = createTitleOne(locale, siteId);
    titleCell.setCellValue(title);
    titleCell.setCellStyle(titleCS);
    
    HSSFCell cellName = titleRow.createCell(cellIndex++);
    HSSFRichTextString previous = createTitleTwo(locale, siteId);
    cellName.setCellValue(previous);
    cellName.setCellStyle(titleCS);  
     
    HSSFCell cellNew = titleRow.createCell(cellIndex++);
    HSSFRichTextString newVersion = createTitleThree(locale, siteId);
    cellNew.setCellValue(newVersion);
    cellNew.setCellStyle(titleCS);
    
    Set<String> keyset = outputCollectionGrouped.keySet();
    for(String key : keyset){
        cellIndex = 0;
        HSSFRow valueRow = sheet.createRow(rowIndex++);
        HSSFCell colcell = valueRow.createCell(cellIndex++);
        colcell.setCellValue(key);
        colcell.setCellStyle(cs);
  
        List<CompareOutput> nameList = outputCollectionGrouped.get(key);
        CompareOutput comp = nameList.get(0);
          HSSFCell groupcell = valueRow.createCell(cellIndex++);
          String[] value = comp.getStringOutput();
          String oldValue = value[1];
          String old = AuditXLSExportUtil.htmlToXLSFormat(oldValue);
          checkMaxCellLimit(old, sheet, rowIndex, cellIndex, groupcell);
          groupcell.setCellStyle(cs);
          
          HSSFCell newcell = valueRow.createCell(cellIndex++);
          String newValue = value[0];
          String newVal = AuditXLSExportUtil.htmlToXLSFormat(newValue);
          checkMaxCellLimit(newVal, sheet, rowIndex, cellIndex, newcell);
          newcell.setCellStyle(cs);
    }
    AuditXLSExportUtil.setColumnWidth(sheet);
    return wb;
}

    public static HSSFWorkbook generateExcel(String locale, Long siteId, List<ActivityComparisonResult> outputCollection) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(TranslatorWorker.translateText("Audit Logger"));
        HSSFCellStyle titleCS= AuditXLSExportUtil.createTitleStyle(wb);
        HSSFCellStyle  cs = AuditXLSExportUtil.createOrdinaryStyle(wb);

        int rowIndex = 0;
        int cellIndex = 0;
        
        HSSFRow titleRow = sheet.createRow(rowIndex++);
        
        HSSFCell titleCell = titleRow.createCell(cellIndex++);
        HSSFRichTextString title = createTitleOne(locale, siteId);
        titleCell.setCellValue(title);
        titleCell.setCellStyle(titleCS);
        
        HSSFCell cellName = titleRow.createCell(cellIndex++);
        HSSFRichTextString previous = createTitleTwo(locale, siteId);
        cellName.setCellValue(previous);
        cellName.setCellStyle(titleCS);  
         
        HSSFCell cellNew = titleRow.createCell(cellIndex++);
        HSSFRichTextString newVersion = createTitleThree(locale, siteId);
        cellNew.setCellValue(newVersion);
        cellNew.setCellStyle(titleCS);
      
        for(ActivityComparisonResult result:outputCollection) {
            cellIndex = 0;
           String name= result.getName();
           HSSFRow nameRow = sheet.createRow(rowIndex);
           sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,0,2)); 
           rowIndex++;
           HSSFCell nameCell = nameRow.createCell(cellIndex++);
           nameCell.setCellValue(name);
           nameCell.setCellStyle(titleCS);
        
        Map<String, List<CompareOutput>> outputCollectionGrouped = result.getCompareOutput();        
        Set<String> keyset = outputCollectionGrouped.keySet();
        for(String key : keyset){
            cellIndex = 0;
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
              checkMaxCellLimit(old, sheet, rowIndex, cellIndex, groupcell);
              groupcell.setCellStyle(cs);
              
              HSSFCell newcell = valueRow.createCell(cellIndex);
              String newValue = value[0];
              String newVal=AuditXLSExportUtil.htmlToXLSFormat(newValue);
              checkMaxCellLimit(newVal, sheet, rowIndex, cellIndex, newcell);
              newcell.setCellStyle(cs);
              rowIndex++;
        }
        }
        AuditXLSExportUtil.setColumnWidth(sheet);
        return wb;
    }
    
    public static HSSFRichTextString createTitleOne(String locale, Long siteId) {
        HSSFRichTextString title = new HSSFRichTextString(TranslatorWorker.translateText("Value Name",locale,siteId));
        return title;
    }
        
    public static HSSFRichTextString createTitleTwo(String locale, Long siteId) {
        HSSFRichTextString previous = new HSSFRichTextString(TranslatorWorker.translateText("Previous Version",locale,siteId));
        return previous;
    }
        
    public static HSSFRichTextString createTitleThree(String locale, Long siteId) {
        HSSFRichTextString newVersion = new HSSFRichTextString(TranslatorWorker.translateText("New Version",locale,siteId));
        return newVersion;
    }
    
    public static void checkMaxCellLimit(String cellValue,HSSFSheet sheet,int rowIndex, int cellIndex, HSSFCell cell) {
        if(cellValue.length() > 32767) {
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,++rowIndex,0,0));
            String valueOne = cellValue.substring(0, 32766);
            cell.setCellValue(valueOne);
            HSSFRow newvalueRow = sheet.createRow(rowIndex);
            HSSFCell valuescell = newvalueRow.createCell(cellIndex);
            String valueTwo = cellValue.substring(32767);
            valuescell.setCellValue(valueTwo);
            }
            else {
               cell.setCellValue(cellValue);
            }
    }

}
