package za.co.sanlam.services.abandonemail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import za.co.sanlam.services.jdbc.DBConnection;
import za.co.sanlam.services.utils.AbandonEmailConstants;
import za.co.sanlam.services.utils.AbandonEmailInProgressException;
import za.co.sanlam.services.utils.CreatePdf;
import za.co.sanlam.services.utils.ResourceBundle;

public class AbandonEmailInProgress {

    private static final Logger LOGGER = Logger.getLogger(AbandonEmailInProgress.class);

    private static Connection con = null;
    private static List<Integer> emailNumber = new ArrayList<Integer>();
    private static List<ContactHistoryDO> beforeUpdateList = null;
    private static List<ContactHistoryDO> afterUpdatedList = null;

    private static String selectQuery = null;
    private static String updateQuery = null;
    private static Integer status = null;

    static ContactHistoryDO contactHistoryDO = new ContactHistoryDO();

    public static void main(String[] args) throws Exception {

        String selectSQL = null;
        String contactHistoryNumber = null;
        con = DBConnection.getCHDBConnection();

        try {
            if (!(con.isClosed())) {
                beforeUpdateList = new ArrayList<ContactHistoryDO>();
                afterUpdatedList = new ArrayList<ContactHistoryDO>();
                contactHistoryNumber = ResourceBundle.getProperty(AbandonEmailConstants.CONTACT_HISTORY_NUMBER.trim());

                if ( contactHistoryNumber != null && (!contactHistoryNumber.equals(""))) {
                    Integer contactNumber = new Integer(contactHistoryNumber);
                    if (contactNumber != 0) {
                        selectSQL = getCHSelectSql() + (contactNumber + ")");
                        getEmailInProgress(selectSQL, con);
                        abandonEmailInProgress();
                        System.out.println("10" + 1);
                    } else {
                        throw new AbandonEmailInProgressException("Contact History Number doesn't exit!");
                    }
                } else {
                    throw new Exception("Contact History Number cannot be blank or null!");
                }
            }

        } catch (SQLException e) {
            LOGGER.debug(e.getMessage());
        }
    }

    @SuppressWarnings("static-access")
    private static void abandonEmailInProgress() {

        Statement stmt = null;
        String updateSql = null;

        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Enter abandonEmailInProgress");
            }
            stmt = con.createStatement();

            if (contactHistoryDO.getStatusCode() == 3) {

                updateSql = ResourceBundle.getProperty(AbandonEmailConstants.UPDATE_SQL) + contactHistoryDO.getEmailNumber() + ")";

                updateQuery = updateSql;
                stmt.executeUpdate(updateSql);

                LOGGER.info("Record/Email : " + contactHistoryDO.getEmailNumber() + " Updated Successfully.");

                updatedCHRecordList(con, selectQuery);
                new CreatePdf(beforeUpdateList, afterUpdatedList, selectQuery, updateQuery).createPdfDocument();
            } else {

                switch (contactHistoryDO.getStatusCode()) {
                    case 1:
                        LOGGER.info(("Email (" + contactHistoryDO.getEmailNumber() + ") is already Done."));
                        break;
                    case 2:
                        LOGGER.info(("Email (" + contactHistoryDO.getEmailNumber() + ") is already Abandoned."));
                        break;
                    case 4:
                        LOGGER.info(("Email : " + contactHistoryDO.getEmailNumber() + " is Not Started."));
                        break;
                    case 5:
                        LOGGER.info(("Email : " + contactHistoryDO.getEmailNumber() + " is on status: Retrieval In Progress."));
                        break;
                    default:
                        break;
                }
            }

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Exit abandonEmailInProgress");
            }
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
        }
    }

    private static List<ContactHistoryDO> updatedCHRecordList(Connection con, String sql) {

        Statement stmt = null;
        ResultSet rs = null;
        try {

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Enter updatedCHRecordList");
            }

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                contactHistoryDO = new ContactHistoryDO();

                if (rs.getString(ResourceBundle.getProperty(AbandonEmailConstants.SR)) != null) {
                    contactHistoryDO.setServiceRequestNo(Integer.parseInt(rs.getString(ResourceBundle
                            .getProperty(AbandonEmailConstants.SR))));
                }
                contactHistoryDO.setPartyID(rs.getString(ResourceBundle.getProperty(AbandonEmailConstants.PARTY_ID)));
                contactHistoryDO.setEmailNumber((Integer.parseInt(rs.getString(ResourceBundle
                        .getProperty(AbandonEmailConstants.EMAIL_NUM)))));
                contactHistoryDO.setStatusCode(Integer.parseInt(rs.getString(ResourceBundle
                        .getProperty(AbandonEmailConstants.STATUS_CODE))));
                if ((rs.getString(ResourceBundle.getProperty(AbandonEmailConstants.STATUS_DESC))) != null) {
                    contactHistoryDO.setStatusDesc(rs.getString(ResourceBundle.getProperty(AbandonEmailConstants.STATUS_DESC)));
                }
                contactHistoryDO.setAbandonedTxt(rs.getString(ResourceBundle.getProperty(AbandonEmailConstants.ABANDON_TXT)));

                afterUpdatedList.add(contactHistoryDO);
            }

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Exit updatedCHRecordList");
            }
        } catch (Exception e) {
            LOGGER.debug(e.getStackTrace());
        } finally {
            try {
                con.close();
                LOGGER.info("Connection Closed");
            } catch (SQLException e) {
                LOGGER.debug(e.getMessage());
            }
        }

        return new ArrayList<ContactHistoryDO>();
    }

    private static List<Integer> getEmailInProgress(String selectSQL, Connection con) {

        Statement stmt = null;
        ResultSet rs = null;

        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Enter getEmailInProgress");
            }

            stmt = con.createStatement();
            rs = stmt.executeQuery(selectSQL);
            selectQuery = selectSQL;

            LOGGER.debug(selectQuery);

            while (rs.next()) {

                if (rs.getString(ResourceBundle.getProperty(AbandonEmailConstants.SR)) != null) {
                    contactHistoryDO.setServiceRequestNo(Integer.parseInt(rs.getString(ResourceBundle
                            .getProperty(AbandonEmailConstants.SR))));
                }
                if ((rs.getString(ResourceBundle.getProperty(AbandonEmailConstants.STATUS_DESC))) != null) {
                    contactHistoryDO.setStatusDesc(rs.getString(ResourceBundle.getProperty(AbandonEmailConstants.STATUS_DESC)));
                } else {
                    contactHistoryDO.setStatusDesc("");
                }
                {
                    contactHistoryDO.setPartyID(rs.getString(ResourceBundle.getProperty(AbandonEmailConstants.PARTY_ID)));
                    contactHistoryDO.setEmailNumber((Integer.parseInt(rs.getString(ResourceBundle
                            .getProperty(AbandonEmailConstants.EMAIL_NUM)))));
                    contactHistoryDO.setStatusCode(Integer.parseInt(rs.getString(ResourceBundle
                            .getProperty(AbandonEmailConstants.STATUS_CODE))));
                    contactHistoryDO.setAbandonedTxt(rs.getString(ResourceBundle.getProperty(AbandonEmailConstants.ABANDON_TXT)));

                    beforeUpdateList.add(contactHistoryDO);
                }
            }

            emailNumber.add(contactHistoryDO.getEmailNumber());

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Exit getEmailInProgress");
            }
        } catch (Exception e) {
            LOGGER.info(e.getStackTrace());
            e.printStackTrace();
        }
        return emailNumber;
    }

    private static String getCHSelectSql() {

        String sql = null;
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Enter getCHSelectSql");
            }
            sql = ResourceBundle.getProperty(AbandonEmailConstants.EMAIL_NUMBER_SQL);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Exit getCHSelectSql");
            }

        } catch (Exception e) {

            LOGGER.debug(e.getMessage());
        }
        return sql;
    }
}
