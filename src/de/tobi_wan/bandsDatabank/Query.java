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
   static String                   SELECT;

   public static void main(String [] args) {
      initialiseAttributes();
      connect(dbPath);
      printResultSet(SELECT + joinAllTables + "ORDER BY Concert LIMIT 25");
      disconnect();
   }

   private static void initialiseAttributes() {
      s = new StandardOutput("*", 80);
      dbPath = "db/bands.db";
      dbo = new DatabaseOperationsSQLite();
      SELECT = "SELECT Place, Date, Concert, Band";
      joinAllTables = " FROM Bands INNER JOIN ConcertsBands ON Bands.BID = ConcertsBands.BID INNER JOIN Concerts On Concerts.CID = ConcertsBands.CID ";
      countBandsStatement = "SELECT COUNT(Band) AS Bands FROM Bands";
      selectBandsStatement = "SELECT Band FROM Bands";
      countBandsISawStatement = "SELECT COUNT(Band) AS Anzahl, Band" + joinAllTables + "GROUP BY Band ORDER BY COUNT(Band) DESC";
      countEventsStatement = "SELECT COUNT(Concert) AS Events FROM Concerts";
      countBandsISawPerEvent = "SELECT COUNT(Band) AS Anzahl, Concert, Date" + joinAllTables + "GROUP BY Concerts.CID ORDER BY COUNT(Band) DESC";
      countBandsISawPerYear = "";
      joinAllTablesStatement = "SELECT Place, Date, Concert, Band" + joinAllTables + "ORDER BY Concert";
   }

   private static void printResultSet(String sqlDMLStatement) {
      try {
         dbo.tableOutOfQuery(sqlDMLStatement).printTable();
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
