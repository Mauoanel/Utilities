package za.co.fnb.mobile.techcheck;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class SQLUtils {

	/*
	 * MySQL Database properties.
	 * 
	 * MySQL tip: 
	 * LAST_INSERT_ID() (no arguments) returns the first automatically generated value successfully
	 * inserted for an AUTO_INCREMENT column as a result of the most recently executed INSERT statement.
	 * The value of LAST_INSERT_ID() remains unchanged if no rows are successfully inserted.
	 * For example, after inserting a row that generates an AUTO_INCREMENT value, you can get the value like this:
	 * mysql> SELECT LAST_INSERT_ID();
	 * -> 195
	 */
	private static final String MYSQL_DRIVER	= "com.mysql.jdbc.Driver"; 
	private static final String MYSQL_URL		= "jdbc:mysql://localhost:3315/TECHCHECK"; 
	private static final String MYSQL_USER		= "techcheck"; 
	private static final String MYSQL_PWD 		= "s3cr3t";
	
	private static final String MYSQL_TEST		= "SELECT VERSION() AS TEST;";
	
	
	static {
		try {
			Class.forName( MYSQL_DRIVER );
		}
		catch ( ClassNotFoundException e ) {
			System.err.println( "Failed to load driver for [" + MYSQL_DRIVER + "]." );
		}
	}
	
	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection( MYSQL_URL, MYSQL_USER, MYSQL_PWD );
	}
	
	public void test() throws SQLException {
		Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement( MYSQL_TEST );
		ResultSet rs = statement.executeQuery();
		if( rs.next() ) {
			String result = rs.getString( "TEST" );
			System.out.println( "Database test result [" + result + "]." );
		}
	}
	
	public static void main( String[] args ) {
		try {
			SQLUtils utils = new SQLUtils();
			utils.test();
			System.out.println( "Test executed successfully." );
		}
		catch ( SQLException e ) {
			System.err.println( "DB test failed: " + e.getMessage() );
			e.printStackTrace();
		}
	}
	
}
