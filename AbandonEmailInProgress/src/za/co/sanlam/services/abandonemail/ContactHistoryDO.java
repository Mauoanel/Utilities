/**
 * 
 */
package za.co.sanlam.services.abandonemail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author E937776
 * 
 */
public class ContactHistoryDO {

    private Integer serviceRequestNo;
    private String partyID;
    private Integer emailNumber;
    private Integer statusCode;
    private String statusDesc;
    private String abandonedTxt;

    public Integer getServiceRequestNo() {
        return serviceRequestNo;
    }

    public void setServiceRequestNo(Integer serviceRequestNo) {
        this.serviceRequestNo = serviceRequestNo;
    }

    public String getPartyID() {
        return partyID;
    }

    public void setPartyID(String partyID) {
        this.partyID = partyID;
    }

    public Integer getEmailNumber() {
        return emailNumber;
    }

    public void setEmailNumber(Integer emailNumber) {
        this.emailNumber = emailNumber;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getAbandonedTxt() {
        return abandonedTxt;
    }

    public void setAbandonedTxt(String abandonedTxt) {
        this.abandonedTxt = abandonedTxt;
    }

    public static void main(String[] r) throws IOException {

        File file = new File("d:/ws/main/file.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));;
        SortedMap<Integer, String> a = new TreeMap<Integer, String>();;

        try {
            
            file.createNewFile();
            populateMap(a);

            for (Integer element : a.keySet()) {
                writeOnConsole(a, element);
                writeToFile(bw, a, element);
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @param a
     * @param element
     */
    private static void writeOnConsole(SortedMap<Integer, String> a, Integer element) {
        System.out.print("Key : {" + element + "}");
        System.out.println("\tValue : " + (a.get(element) == null ? null : a.get(element)));
    }

    /**
     * @param bw
     * @param a
     * @param element
     * @throws IOException
     */
    private static void writeToFile(BufferedWriter bw, SortedMap<Integer, String> a, Integer element)
            throws IOException {
        bw.write("Key : {" + element + "}");
        bw.write("\tValue : " + (a.get(element) == null ? null : a.get(element))+" \n");
    }
    /**
     * 
     * @param a SortedMap
     */
    public static void populateMap(SortedMap<Integer, String> a) {
        for (Integer i = 0; i <= 10000; i++) {
            a.put(i.intValue(), "args_" + i);
        }
    }
}
