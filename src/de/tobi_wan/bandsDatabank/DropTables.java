package de.tobi_wan.bandsDatabank;

import java.sql.SQLException;
import tobi_wan.dataStructure.Table;
import tobi_wan.databaseOperations.DatabaseOperationsSQLite;
import tobi_wan.support.StandardOutput;



public class DropTables {
   static StandardOutput           s;
   static String                   dbPath;
   static DatabaseOperationsSQLite dbo;

   public static void main(String [] args) {
      initialiseAttributes();
      connect(dbPath);
      dropTable("Bands");
      dropTable("Konzerte");
      dropTable("KonzerteBands");
      disconnect();
   }

   private static void initialiseAttributes() {
      s = new StandardOutput("*", 80);
      dbPath = "db/bands.db";
      dbo = new DatabaseOperationsSQLite();
   }

   private static void printResultSet(String sqlDMLStatement) {
      try {
         s.println(dbo.generateTableFromQuery(sqlDMLStatement));
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   private static void dropTable(String sqlStatement) {
      try {
         dbo.dropTable(sqlStatement);
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   private static void createTable(String sqlStatement) {
      try {
         dbo.sqlDataDefinition(sqlStatement);
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   private static void insertData(String sqlStatement, Table table) {
      try {
         dbo.insertWithPreparedStatement(sqlStatement, table);
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   private static void connect(String dbPath) {
      try {
         dbo.connect(dbPath);
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   private static void disconnect() {
      try {
         dbo.disconnect();
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

}
