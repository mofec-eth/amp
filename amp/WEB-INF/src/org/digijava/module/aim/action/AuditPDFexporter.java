package org.digijava.module.aim.action;

import java.awt.Color;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.ListItem;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPTableEvent;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import com.itextpdf.text.*;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfPTable;
import clover.com.google.common.base.Strings;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.digijava.kernel.entity.Locale;
import org.digijava.kernel.request.Site;
import org.digijava.kernel.translator.TranslatorWorker;
import org.digijava.kernel.util.RequestUtils;
import org.digijava.kernel.util.SiteUtils;
import org.digijava.module.aim.action.ExportActivityToPDF.PdfPTableEvents;
import org.digijava.module.aim.dbentity.AmpActivityVersion;
import org.digijava.module.aim.form.EditActivityForm;
import org.digijava.module.aim.util.ActivityUtil;
import org.digijava.module.aim.util.FeaturesUtil;
import org.digijava.module.categorymanager.dbentity.AmpCategoryValue;
public class AuditPDFexporter extends Action {

    public static final int COLUMNS_3 = 3;
    public static final int COLUMNS_4 = 4;
    public static final int INDENTATION_LEFT = 5;
    public static final Color BACKGROUND_COLOR = new Color(244, 244, 242);
    public static final Color BACKGROUND_COLOR_WHITE = new Color(255, 255, 255);
    public static final Color BORDER_COLOR = new Color(201, 201, 199);
    public static final Color MTEF_BACKGROUND_COLOR = new Color(255, 255, 204);
    public static final Color SUBTOTAL_BACKGROUND_COLOR = new Color(221, 221, 221);
    private static final int CURRENCY_COLUMN_WIDTH = 13;
    private static final int AMOUNT_COLUMN_WIDTH = 87;
    private static final float SUBTOTAL_BORDER_TOP_WIDTH = 0.5f;
    private static final int ARRAY_IDX_3 = 3;
    
    private static Logger logger = Logger.getLogger(ExportActivityToPDF.class);

    /**
     * font which supports Diacritics - for Romanian language support. The standard PDF fonts do not have diacritics, so we have to embed a TTF font into AMP
     */
    public final static BaseFont basefont = getBaseFont();

    private static final com.lowagie.text.Font plainFont = new com.lowagie.text.Font(basefont, 11,Font.NORMAL);
    private static final com.lowagie.text.Font smallerFont = new com.lowagie.text.Font(basefont, 9,Font.NORMAL);
    private static final com.lowagie.text.Font titleFont = new com.lowagie.text.Font(basefont, 11,Font.BOLD);
    private static final String [] Value_name = {"\n" + 
    		"auditLoggerManager.do/compareActivityVersions.do"};
    private static final String [] First_versionOlder = {"\n" + 
    		"auditLoggerManager.do/compareActivityVersions.do"};
    private static final String [] Second_versionOlder = {"\n" + 
    		"auditLoggerManager.do/compareActivityVersions.do"};
    
    private static final Chunk BULLET_SYMBOL = new Chunk("\u2022");
    private static BaseFont getBaseFont()
    {
        try
        {
            InputStream inputStream = ExportActivityToPDF.class.getResourceAsStream("Arial.ttf");
            byte[] fontFile = IOUtils.toByteArray(inputStream);

            BaseFont result = BaseFont.createFont("Arial.ttf", BaseFont.IDENTITY_H, true, true, fontFile, null);
            IOUtils.closeQuietly(inputStream);

            return result;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
            public ActionForward pdfExport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  throws Exception {
              //  EditActivityForm myForm=(EditActivityForm)form;
                Long siteId=null;
                String locale=null;
                ServletContext ampContext = null;

                ampContext = getServlet().getServletContext();
                //to know whether print happens from Public View or not
                HttpSession session = request.getSession();
                Long actId=null;
                AmpActivityVersion activity=null;
                if(request.getParameter("activityid")!=null){
                    actId=new Long(request.getParameter("activityid"));
                }

                response.setContentType("application/pdf; charset=UTF-8");
                response.setHeader("content-disposition", "attachment;filename=activity.pdf");
                Document document = new Document(PageSize.A4.rotate());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PdfWriter.getInstance(document, baos);
                document.open();
                PdfPTable mainLayout = buildPdfTable(3);
                if (SiteUtils.isEffectiveLangRTL()) {
                    mainLayout.setWidths(new float[]{2f, 1f});
                } else {
                    mainLayout.setWidths(new float[]{1f, 2f});
                }
                mainLayout.setWidthPercentage(100);
                PdfPTableEvents event = new PdfPTableEvents();
                mainLayout.setTableEvent(event);
                mainLayout.getDefaultCell().setBorder(0);
                try {
                    activity=ActivityUtil.loadActivity(actId);
                    Site site = RequestUtils.getSite(request);
                    Locale navigationLanguage = RequestUtils.getNavigationLanguage(request);

                    siteId = site.getId();
                    locale = navigationLanguage.getCode();
                } catch (Exception e) {
                    logger.error(e);
                }
                

                //building  table
                if(activity!=null){
                    AmpCategoryValue catVal=null;
                    String translatedValue="";
                    String output="";
                    String columnName="";
                    String columnVal="";

                    Paragraph p1=null;
                    //heading cell
                    PdfPCell titleCell=new PdfPCell();

                    com.lowagie.text.Font headerFont = new com.lowagie.text.Font(basefont, 11, Font.BOLD, new Color(255, 255, 255));
                    p1=new Paragraph((TranslatorWorker.translateText("Activity Details",locale,siteId)), headerFont);
                    p1.setAlignment(Element.ALIGN_CENTER);
                    titleCell.addElement(p1);
                    titleCell.setColspan(2);
                    titleCell.setBackgroundColor(new Color(0,102,153));
                    mainLayout.addCell(titleCell);
                    //activity name cells
                   
}
                private PdfPTable buildPdfTable(int columns) {
                    PdfPTable table = new PdfPTable(columns);
                    if (SiteUtils.isEffectiveLangRTL()) {
                        table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                    }
                    return table;    }
            
            }
    