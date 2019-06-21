package org.digijava.module.aim.action;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.digijava.kernel.translator.TranslatorWorker;
import org.digijava.module.aim.annotations.activityversioning.CompareOutput;
import org.digijava.module.aim.util.versioning.ActivityComparisonResult;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;

public class AuditExcelExporter {
    public static HSSFWorkbook generateExcel(String locale, Long siteId, Map<String, List<CompareOutput>> outputCollectionGrouped) {
    HSSFWorkbook wb = new HSSFWorkbook();
    HSSFSheet sheet = wb.createSheet(TranslatorWorker.translateText("Audit Logger"));
    HSSFCellStyle titleCS = wb.createCellStyle();
    titleCS.setWrapText(true);
    titleCS.setFillForegroundColor(HSSFColor.BROWN.index);
    HSSFFont fontHeader = wb.createFont();
    fontHeader.setFontName(HSSFFont.FONT_ARIAL);
    fontHeader.setFontHeightInPoints((short) 10);
    fontHeader.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    titleCS.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    titleCS.setFont(fontHeader);

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
    
    Set<String> keyset = outputCollectionGrouped.keySet();
    for(String key : keyset){
        cellIndex = 0;
        HSSFRow valueRow = sheet.createRow(rowIndex++);
        HSSFCell colcell = valueRow.createCell(cellIndex++);
        colcell.setCellValue(key);
        HSSFCellStyle valueCS = wb.createCellStyle();
        valueCS.setWrapText(true);
        colcell.setCellStyle(valueCS);
  
        List<CompareOutput> nameList = outputCollectionGrouped.get(key);
        for(CompareOutput s : nameList){
          HSSFCell groupcell = valueRow.createCell(cellIndex++);
          String[] value = s.getStringOutput();
          String oldValue = value[1].toString().replaceAll("\\<.*?>","");
          String oldVal=oldValue.replaceAll("&nbsp;", "\n");
          String old = oldVal.replaceAll("<br>","\n");
          groupcell.setCellValue(old);
          groupcell.setCellStyle(valueCS);
          
          HSSFCell newcell = valueRow.createCell(cellIndex++);
          String newValue = value[0].toString().replaceAll("\\<.*?>","");
          String newValues = newValue.replaceAll("&nbsp;", "\n");
          String newVal = newValues.replaceAll("<br>","\n");
          newcell.setCellValue(newVal);
          newcell.setCellStyle(valueCS);
        }
    }
    return wb;
}

    public static HSSFWorkbook generateExcel(String locale, Long siteId, List<ActivityComparisonResult> outputCollection) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(TranslatorWorker.translateText("Audit Logger"));
        HSSFCellStyle titleCS = wb.createCellStyle();
        titleCS.setWrapText(true);
        titleCS.setFillForegroundColor(HSSFColor.BROWN.index);
        HSSFFont fontHeader = wb.createFont();
        fontHeader.setFontName(HSSFFont.FONT_ARIAL);
        fontHeader.setFontHeightInPoints((short) 10);
        fontHeader.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        titleCS.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleCS.setFont(fontHeader);

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
            HSSFRow valueRow = sheet.createRow(rowIndex++);
            HSSFCell colcell = valueRow.createCell(cellIndex++);
            colcell.setCellValue(key);
            HSSFCellStyle valueCS = wb.createCellStyle();
            valueCS.setWrapText(true);
            valueCS.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            colcell.setCellStyle(valueCS);
      
            List<CompareOutput> nameList = outputCollectionGrouped.get(key);
            CompareOutput comp = nameList.get(0);
              HSSFCell groupcell = valueRow.createCell(cellIndex++);
              String[] value = comp.getStringOutput();
              String oldValue = value[1].toString().replaceAll("\\<.*?>","");
              String oldVal=oldValue.replaceAll("&nbsp;", "");
              String old = oldVal.replaceAll("<br>","\r\n");
              groupcell.setCellValue(old);
              groupcell.setCellStyle(valueCS);
              
              HSSFCell newcell = valueRow.createCell(cellIndex++);
              String newValue = value[0].toString().replaceAll("\\<.*?>","");
              String newValues = newValue.replaceAll("&nbsp;", "");
              String newVal = newValues.replaceAll("<br>","\r\n");
              newcell.setCellValue(newVal);
              newcell.setCellStyle(valueCS);
        }
        }
        return wb;
    }  
}
