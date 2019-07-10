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
    HSSFRow titleRow = createHeader(wb,sheet,locale, siteId);
    
    int rowIndex = 1;
    HSSFCellStyle  cs = AuditXLSExportUtil.createOrdinaryStyle(wb);        
        
    Set<String> keyset = outputCollectionGrouped.keySet();
    for(String key : keyset){    
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
    rowIndex=checkMaxCellLimit(old, sheet, rowIndex, cellIndex, groupcell);
    groupcell.setCellStyle(cs);
          
    HSSFCell newcell = valueRow.createCell(cellIndex);
    String newValue = value[0];
    String newVal = AuditXLSExportUtil.htmlToXLSFormat(newValue);
    rowIndex=checkMaxCellLimit(newVal, sheet, rowIndex, cellIndex, newcell);
    rowIndex++;
    newcell.setCellStyle(cs);
    }
    AuditXLSExportUtil.setColumnWidth(sheet);
    return wb;
}

    public static HSSFWorkbook generateExcel(String locale, Long siteId, List<ActivityComparisonResult> outputCollection) {
    HSSFWorkbook wb = new HSSFWorkbook();
    HSSFSheet sheet = wb.createSheet(TranslatorWorker.translateText("Audit Logger"));
    HSSFRow titleRow = createHeader(wb,sheet,locale, siteId);
    int rowIndex = 1;
    HSSFCellStyle  cs = AuditXLSExportUtil.createOrdinaryStyle(wb);     
   
    for(ActivityComparisonResult result:outputCollection) {
    int cellIndex = 0;
    String name= result.getName();
    HSSFRow nameRow = sheet.createRow(rowIndex);
    sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,0,2)); 
    rowIndex++;
    HSSFCell nameCell = nameRow.createCell(cellIndex++);
    nameCell.setCellValue(name);
    nameCell.setCellStyle(AuditXLSExportUtil.createTitleStyle(wb));
        
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
    rowIndex=checkMaxCellLimit(old, sheet, rowIndex,cellIndex, groupcell);
    groupcell.setCellStyle(cs);
              
    HSSFCell newcell = valueRow.createCell(cellIndex);
    String newValue = value[0];
    String newVal=AuditXLSExportUtil.htmlToXLSFormat(newValue);
    rowIndex=checkMaxCellLimit(newVal, sheet, rowIndex,cellIndex, newcell);
    newcell.setCellStyle(cs);
    rowIndex++;
      }
        }
    AuditXLSExportUtil.setColumnWidth(sheet);
    return wb;
    }
    
    public static HSSFRow createHeader(HSSFWorkbook wb, HSSFSheet sheet, String locale, Long siteId) {
        HSSFCellStyle titleCS= AuditXLSExportUtil.createTitleStyle(wb);       

        int rowIndex = 0;
        int cellIndex = 0;

        HSSFRow titleRow = sheet.createRow(rowIndex++);
        
        HSSFCell titleCell = titleRow.createCell(cellIndex++);
        HSSFRichTextString title = new HSSFRichTextString(TranslatorWorker.translateText("Value Name",locale,siteId));
        titleCell.setCellValue(title);
        titleCell.setCellStyle(titleCS);
        
        HSSFCell cellName = titleRow.createCell(cellIndex++);
        HSSFRichTextString previous = new HSSFRichTextString(TranslatorWorker.translateText("Previous Version",locale,siteId));
        cellName.setCellValue(previous);
        cellName.setCellStyle(titleCS);  
         
        HSSFCell cellNew = titleRow.createCell(cellIndex++);
        HSSFRichTextString newVersion = new HSSFRichTextString(TranslatorWorker.translateText("New Version",locale,siteId));
        cellNew.setCellValue(newVersion);
        cellNew.setCellStyle(titleCS);

        return titleRow;
    }
    
    public static int checkMaxCellLimit(String cellValue,HSSFSheet sheet, int rowIndex, int cellIndex, HSSFCell cell) {
        int x=32767;
        int length=cellValue.length();
        int mergeIndex= rowIndex;
          if(length > x) {
            String valueOne = cellValue.substring(0, x);
            cell.setCellValue(valueOne);
            int remain = length - x;
            for(int i=x;i<length;i+=x) {
                String valueNext = null;
                HSSFRow newvalueRow = sheet.createRow(++rowIndex);
                HSSFCell valuescell = newvalueRow.createCell(cellIndex);
                if(remain<x) {
                    valueNext = cellValue.substring(i);  
                } else {
                valueNext = cellValue.substring(i,i+x);
                }
                valuescell.setCellValue(valueNext);
                HSSFWorkbook wb = sheet.getWorkbook();
                valuescell.setCellStyle(AuditXLSExportUtil.createOrdinaryStyle(wb));
                remain=length-(i+x);
            }
            sheet.addMergedRegion(new CellRangeAddress(mergeIndex,rowIndex,0,0));
        }
           else {
               cell.setCellValue(cellValue);
            }
          return rowIndex; 
     }
}

