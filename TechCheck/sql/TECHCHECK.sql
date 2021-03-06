DROP DATABASE IF EXISTS TECHCHECK;

CREATE DATABASE TECHCHECK;

USE TECHCHECK;

-- REPORT_SUMMARY
DROP TABLE IF EXISTS REPORT_SUMMARY;
CREATE TABLE REPORT_SUMMARY (
	ID				INT				NOT NULL	AUTO_INCREMENT,
    SUMMARY_DATE 	TIMESTAMP		DEFAULT 	CURRENT_TIMESTAMP,
    DIRECTORY		VARCHAR(100) 	NOT NULL 	DEFAULT '',
    TOTAL_FILES		INT				NOT NULL	DEFAULT 0,
    TOTAL_LINES		INT				NOT NULL	DEFAULT 0,
    
    TOTAL_TXN		INT				NOT NULL	DEFAULT 0,
    TOTAL_H			INT				NOT NULL	DEFAULT 0,
    TOTAL_F			INT				NOT NULL	DEFAULT 0,

	PRIMARY KEY ( ID )
);

-- DETAIL_TYPE
DROP TABLE IF EXISTS DETAIL_TYPE;
CREATE TABLE DETAIL_TYPE (
	ID				SMALLINT		NOT NULL	AUTO_INCREMENT,
    DESCRIPTION		VARCHAR(100) 	NOT NULL 	DEFAULT '',
    
	PRIMARY KEY ( ID )
);

INSERT INTO DETAIL_TYPE ( ID, DESCRIPTION ) VALUES ( 1, 'Problem' );
INSERT INTO DETAIL_TYPE ( ID, DESCRIPTION ) VALUES ( 2, 'Unidentified' );


-- REPORT_DETAIL
DROP TABLE IF EXISTS REPORT_DETAIL;
CREATE TABLE REPORT_DETAIL (
	ID				INT				NOT NULL	AUTO_INCREMENT,
	SUMMARY_ID		INT				NOT NULL,
	
    FILENAME		VARCHAR(100) 	NOT NULL 	DEFAULT '',
    LINE_NUMBER		INT				NOT NULL	DEFAULT 0,
    DETAIL_TYPE		SMALLINT		NOT NULL	DEFAULT 0,
    
	PRIMARY KEY ( ID ),
	FOREIGN KEY ( DETAIL_TYPE )	REFERENCES DETAIL_TYPE ( ID ),	
	FOREIGN KEY ( SUMMARY_ID )	REFERENCES REPORT_SUMMARY ( ID )	
);

