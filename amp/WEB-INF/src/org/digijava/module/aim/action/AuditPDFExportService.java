package org.digijava.module.aim.action;
public class AuditPDFExportService {
	 
    public static String htmlToPDFFormat(String stringToFormat) {
        String newValue = stringToFormat.toString().replaceAll("\\<.*?>", "");
        String newValues = newValue.replaceAll("&nbsp;", "\n");
        String newVal = newValues.replaceAll("<br>", "");
        return newVal;
}
}