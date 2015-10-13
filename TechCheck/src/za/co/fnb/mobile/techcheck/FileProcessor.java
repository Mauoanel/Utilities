package za.co.fnb.mobile.techcheck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import za.co.sanlam.services.logreader.LogsConstants;

/**
 * This application has the following functional requirements:
 * <ul>
 * <li/>Analyse the contents of a directory and report on the files found in it
 * as well as the attributes of the lines in the files.
 * <li/>Input to the application should be the name of a directory containing a
 * number of text files.
 * <li>The lines of each file in the named directory should be checked for one
 * the following characteristics (it can be assumed that if a line has one of
 * these characteristics, it will not have another):
 * <ul>
 * <li/>The line starts with 'H'.
 * <li/>The line ends with 'F'.
 * <li/>The line contains 'TRANSACTION'.
 * <li/>The line contains 'PROBLEM'.
 * </ul>
 * </li>
 * <li>At the end of processing the application should report:
 * <ul>
 * <li/>The total number of files processed.
 * <li/>The total number of lines processed.
 * <li/>The number of lines, across all files, displaying each of the
 * characteristics above.
 * </ul>
 * </li>
 * <li/>Lines containing 'PROBLEM' should result in an alert listing the file
 * and the offending line number being generated.
 * <li/>If a line does not contain any of the characteristics above, an alert
 * should be generated listing the file and offending line number.
 * <li/>
 * </ul>
 * 
 * The application requires the following enhancements:
 * <ol>
 * <li/>Multi-threading: enable the processing of more than one file at a time.
 * 
 * <li/>Persistent reports: save the report to a database. The suggested schema
 * and basic SQLUtils which have been provided can be used a a basis for this.
 * This enhancement should also provide improvements to the schema as well
 * another program which can be used to query and print reports for a given
 * directory.
 * 
 * <li/>Generalised line identification: replace the hard-coded "if" statements
 * with a more generic mechanism of identifying different line attributes. This
 * enhancement should also display the use of this by introducing a new line
 * type.
 * 
 * <li/>Generic alerts: implement a mechanism to which future alerting methods
 * can be added without the FileProcessor requiring changes.
 * 
 * </ol>
 * 
 * TODO: Implement one or more of the above enhancements.
 * 
 * Other considerations:
 * <ul>
 * <li/>Nice debugging / logging.
 * <li/>Use of current Java APIs, features and syntax.
 * <li/>Conformance to standard Java coding conventions.
 * </ul>
 * 
 * 
 * 
 */
public class FileProcessor {

    private static int totalFiles;

    private int totallines;

    private int startingWithH;

    private int endingWithF;

    private int containingTransaction;

    private static String directoryName;
    
    private static List<File> listOfFilesInAFolder = new ArrayList<File>();
    private static List<String> directoryFileName = new ArrayList<String>();
    private static String FileContents = new String();
    private static Integer lineCount = 1;

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {

        new FileProcessor(args[0]).process();
    }

    public FileProcessor(String directoryName) {
        this.directoryName = directoryName;
    }

    public void process() throws IOException {
        
        determineNoOfFiles();
        
        BufferedReader bufferedReader = new BufferedReader(new FileReader(logFile));

        for (int i = 0; i < totalFiles; i++) {
            
        }
        
        while ((FileContents = bufferedReader.readLine()) != null) {
            
            if (FileContents.startsWith("H")) {
                startingWithH++;
            } else if (FileContents.endsWith("F")) {
                endingWithF++;
            } else if (FileContents.contains("TRANSACTION")) {
                containingTransaction++;
            } else if (FileContents.contains("PROBLEM")) {
                System.out.println("Line [" + j + "] in file [" + fileName + "] contains 'PROBLEM'");
            } else {
                System.out.println("Line [" + j + "] in file [" + fileName + "] contains none of the characteristics.");
            }
            
            lineCount++;
        }
        
        System.out.println("Total number of files processed: " + totalFiles);
        System.out.println("Total number of lines processed: " + totallines);
        System.out.println("Total number of lines starting with 'H': " + startingWithH);
        System.out.println("Total number of lines ending in 'F': " + endingWithF);
        System.out.println("Total number of lines containing 'TRANSACTION': " + containingTransaction);
    }
    
    /**
     * Determines the number of files that are on a given directory.
     */
    private static void determineNoOfFiles() {

        File directory = new File(directoryName);

        try {
            if (directory.isDirectory()) {
                System.out.println("isDirectory ? " + directory.isDirectory());
                listOfFilesInAFolder.addAll(Arrays.asList(directory.listFiles()));
                for (File file : listOfFilesInAFolder) {
                    if (file.isFile()) {
                        directoryFileName.add(file.getName());
                    }
                }
            }
            totalFiles = directoryFileName.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private List getLines(File file) throws IOException {

        FileReader reader = new FileReader(file);
        return getLines(reader);
    }

    private List getLines(Reader reader) {
        List lines = new ArrayList();
        BufferedReader buff = new BufferedReader(reader);
        try {
            String line = null;
            do {
                line = buff.readLine();
                if (line != null) {
                    lines.add(line);
                }
            } while (line != null);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        return lines;

    }

}
