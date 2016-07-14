package de.tobi_wan.bandsDatabank;

import java.sql.SQLException;
import tobi_wan.dataStructure.Table;
import tobi_wan.databaseOperations.DatabaseOperationsSQLite;
import tobi_wan.support.StandardOutput;



public class Query {
   static StandardOutput           s;
   static String                   dbPath;
   static DatabaseOperationsSQLite dbo;
   static String                   joinAllTables;
   static String                   joinAllTablesStatement;
   static String                   countBandsStatement;
   static String                   selectBandsStatement;
   static String                   countBandsISawStatement;
   static String                   countEventsStatement;
   static String                   countBandsISawPerEvent;
   static String                   countBandsISawPerYear;

   public static void main(String [] args) {
      initialiseAttributes();
      connect(dbPath);
      printResultSet(joinAllTablesStatement + " LIMIT 25");
      disconnect();
   }

   private static void initialiseAttributes() {
      s = new StandardOutput("*", 80);
      dbPath = "db/bands.db";
      dbo = new DatabaseOperationsSQLite();
      joinAllTables = " FROM Bands INNER JOIN KonzerteBands ON Bands.BID = KonzerteBands.BID INNER JOIN Konzerte On Konzerte.KID = KonzerteBands.KID ";
      countBandsStatement = "SELECT COUNT(Band) AS Bands FROM Bands";
      selectBandsStatement = "SELECT Band FROM Bands";
      countBandsISawStatement = "SELECT COUNT(Band) AS Anzahl, Band" + joinAllTables + "GROUP BY Band ORDER BY COUNT(Band) DESC";
      countEventsStatement = "SELECT COUNT(Konzert) AS Events FROM Konzerte";
      countBandsISawPerEvent = "SELECT COUNT(Band) AS Anzahl, Konzert, Datum" + joinAllTables + "GROUP BY Konzerte.KID ORDER BY COUNT(Band) DESC";
      countBandsISawPerYear = "";
      joinAllTablesStatement = "SELECT Ort, Datum, Konzert, Band" + joinAllTables + "ORDER BY Konzert";
   }

   private static void printResultSet(String sqlDMLStatement) {
      try {
         dbo.printTableFromQuery(sqlDMLStatement);
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
