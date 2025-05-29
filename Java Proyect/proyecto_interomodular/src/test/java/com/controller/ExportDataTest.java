package com.controller;

import com.model.Database;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.*;

/**
 *
 * @author meker
 */
public class ExportDataTest
{
     public ExportDataTest() {
     }
     /**
      * Test of ExportDataToJSON method, of class ExportData.
      * @throws java.sql.SQLException
      */
     
     @Test 
     public void testExportDataToJSON() throws SQLException 
     {
          Database db = Database.getInstance();

	     System.out.println("ExportDataToJSON");
	     boolean expResult = false;
	     boolean result = ExportData.ExportDataToJSON(db);
          assertEquals(expResult, result);
     }

     /**
      * Test of ExportDataToCSV method, of class ExportData.
      * @throws java.lang.SQLException
      * @throws java.io.IOException
      */
     @Test
     public void testExportDataToCSV() throws SQLException, IOException {
	     System.out.println("ExportDataToCSV");
	     Database db = Database.getInstance();
	     boolean expResult = false;
	     boolean result = ExportData.ExportDataToCSV(db);
	     assertEquals(expResult, result);
          assertEquals(expResult, result);
     }
}
