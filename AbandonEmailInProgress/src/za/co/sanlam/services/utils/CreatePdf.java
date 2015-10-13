package za.co.sanlam.services.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import za.co.sanlam.services.abandonemail.ContactHistoryDO;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class CreatePdf {

    /**
     * 
     * @param PDF
     *            FILE Creation
     */
    
    private static String FILE = ResourceBundle.getProperty(AbandonEmailConstants.FILE_PATH);
    private static Font catFont = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);
    private static Font normal = new Font(Font.FontFamily.COURIER, 10, Font.NORMAL);
    private static Font redFont = new Font(Font.FontFamily.COURIER, 10, Font.NORMAL, BaseColor.RED);
    private static Font blueFont = new Font(Font.FontFamily.COURIER, 10, Font.NORMAL, BaseColor.BLUE);

    private static List<ContactHistoryDO> beforeUpdateList = null;
    private static List<ContactHistoryDO> afterUpdatedList = null;
    private static String selectQuery = null;
    private static String updateQuery = null;

    public CreatePdf(List<ContactHistoryDO> beforeList, List<ContactHistoryDO> afterList, String beforeSQL, String afterSQL) {
        beforeUpdateList = beforeList;
        afterUpdatedList = afterList;
        selectQuery = beforeSQL;
        updateQuery = afterSQL;
    }

    public CreatePdf() {
        super();
    }

    /**
     * @param args
     */
    private static final Logger LOGGER = Logger.getLogger(CreatePdf.class);

    public static void createPdfDocument() {

        try {
            Document document = new Document();
            FileOutputStream fos = createFile(FILE,Integer.parseInt(ResourceBundle.getProperty(AbandonEmailConstants.CONTACT_HISTORY_NUMBER)));
            PdfWriter.getInstance(document, fos);
            document.open();
            addContent(document, beforeUpdateList, afterUpdatedList, selectQuery, updateQuery);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addContent(Document document, List<ContactHistoryDO> beforeUpdateList,
            List<ContactHistoryDO> afterUpdatedList, String selectQuery, String updateQuery) throws DocumentException {

        Anchor anchor = new Anchor("Contact History : Abandon Email In Progress ", catFont);
        anchor.setName("Before and after result :");

        Chapter catPart = new Chapter(new Paragraph(anchor), 1);
        // add sql select query

        LOGGER.info("Before SQL Update Execution." + selectQuery);

        Paragraph subPara = new Paragraph(selectQuery, normal);
        subPara.add(new Paragraph("\nBefore SQL Update Execution: ", redFont));
        subPara.add(new Paragraph(" \nDate: " + new Date(), blueFont));
//        subPara.add(" \nDate: " + new Date());
        addEmptyLine(subPara, 1);
        
        // add a table
        displayResult(subPara, beforeUpdateList);
        catPart.addSection(subPara);
        addEmptyLine(subPara, 1);

        // add sql update query
        LOGGER.debug(updateQuery);

        subPara = new Paragraph(updateQuery, normal);
        subPara.add(" \nDate: " + new Date());
        catPart.addSection(subPara);
        addEmptyLine(subPara, 1);

        // after update sql select query
        LOGGER.debug("After update record \n" + selectQuery);
        subPara = (new Paragraph(selectQuery, normal));
        subPara.add(new Paragraph("\nAfter SQL Update Execution:  ", redFont));
        subPara.add(new Paragraph(" \nDate: " + new Date(), blueFont));
//        subPara.add(" \nDate: " + new Date());
        addEmptyLine(subPara, 1);

        displayResult(subPara, afterUpdatedList);
        catPart.addSection(subPara);
        addEmptyLine(subPara, 1);
        document.add(catPart);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private static FileOutputStream createFile(String file, Integer contactNumber) throws FileNotFoundException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date date = new Date();

        String dateString = dateFormat.format(date);

        File sharedDestDir = new File(file + ResourceBundle.getProperty(AbandonEmailConstants.PATH_SEPERATOR)
                + dateString.substring(0, 4) + ResourceBundle.getProperty(AbandonEmailConstants.PATH_SEPERATOR) + dateString);

        if (!sharedDestDir.exists()) {
            if (sharedDestDir.mkdirs()) {
                LOGGER.info("Directory is created!");
            } else {
                LOGGER.info("Failed to create directory!");
            }
        }

        String sourcePath = sharedDestDir.getAbsolutePath();
        String filePath = sourcePath + ResourceBundle.getProperty(AbandonEmailConstants.PATH_SEPERATOR)
                + ResourceBundle.getProperty(AbandonEmailConstants.FILE_NAME) + contactNumber + ".pdf";
        LOGGER.info("File location: " + filePath);
        return new FileOutputStream(filePath);
    }

    private static void displayResult(Paragraph paragraph, List<ContactHistoryDO> selectList) {

        PdfPTable table = new PdfPTable(5);
        PdfPCell c1 = createCell("SR #", Element.ALIGN_CENTER);
        table.addCell(c1);

        PdfPCell c2 = createCell("Party Id", Element.ALIGN_CENTER);
        table.addCell(c2);

        PdfPCell c3 = createCell("Email #", Element.ALIGN_CENTER);
        table.addCell(c3);

        PdfPCell c4 = createCell("Status Desc", Element.ALIGN_CENTER);
        table.addCell(c4);

        PdfPCell c5 = createCell("Abandon Text", Element.ALIGN_CENTER);
        table.addCell(c5);
        table.setHeaderRows(1);

        for (ContactHistoryDO contactHistoryDO : selectList) {

            if (contactHistoryDO.getServiceRequestNo() != null) {
                table.addCell("" + contactHistoryDO.getServiceRequestNo());
            } else {
                table.addCell("");
            }
            table.addCell(new Phrase(contactHistoryDO.getPartyID(), normal));
            table.addCell(new Phrase(contactHistoryDO.getEmailNumber().toString(), normal));
            table.addCell(new Phrase(contactHistoryDO.getStatusDesc(), normal));
            table.addCell(new Phrase(contactHistoryDO.getAbandonedTxt(), normal));
        }
        paragraph.add(table);
    }

    private static PdfPCell createCell(String cellTxt, int alignCenter) {

        PdfPCell c = new PdfPCell(new Phrase(cellTxt));
        c.setHorizontalAlignment(alignCenter);
        c.setBackgroundColor(BaseColor.RED);
        return c;
    }
}
