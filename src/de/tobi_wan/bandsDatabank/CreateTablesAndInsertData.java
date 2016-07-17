package de.tobi_wan.bandsDatabank;

import java.io.IOException;
import java.sql.SQLException;
import tobi_wan.IO.IOStreamTableCSV;
import tobi_wan.dataStructure.Table;
import tobi_wan.databaseOperations.DatabaseOperationsSQLite;
import tobi_wan.support.StandardOutput;



public class CreateTablesAndInsertData {
   static StandardOutput           s;
   static String                   dbPath;
   static DatabaseOperationsSQLite dbo;
   static IOStreamTableCSV         io;
   static Table                    bands;
   static Table                    concerts;
   static Table                    concertsBands;
   static String                   createTableBands;
   static String                   createTableConcerts;
   static String                   createTableConcertsBands;
   static String                   InsertIntoBands;
   static String                   InsertIntoConcerts;
   static String                   InsertIntoConcertsBands;

   public static void main(String [] args) {
      initialiseAttributes();
      connect(dbPath);
      createTable(createTableBands);
      createTable(createTableConcerts);
      createTable(createTableConcertsBands);
      insertData(InsertIntoBands, bands);
      insertData(InsertIntoConcerts, concerts);
      insertData(InsertIntoConcertsBands, concertsBands);
      disconnect();
   }

   private static void initialiseAttributes() {
      s = new StandardOutput("*", 80);
      dbPath = "db/bands.db";
      dbo = new DatabaseOperationsSQLite();
      io = new IOStreamTableCSV(";");
      bands = readCSVIntoTable("data/bands.csv");
      concerts = readCSVIntoTable("data/concerts.csv");
      concertsBands = readCSVIntoTable("data/concertsBands.csv");
      createTableBands = "CREATE TABLE IF NOT EXISTS Bands (BID INT PRIMARY KEY NOT NULL, Band TEXT)";
      createTableConcerts = "CREATE TABLE IF NOT EXISTS Concerts (CID INT PRIMARY KEY NOT NULL, Concert TEXT, Date TEXT, Place TEXT)";
      createTableConcertsBands = "CREATE TABLE IF NOT EXISTS ConcertsBands (CID INT, BID INT)";
      InsertIntoBands = "INSERT INTO Bands (BID, Band) VALUES (?, ?)";
      InsertIntoConcerts = "INSERT INTO Concerts (CID, Concert, Date, Place) VALUES (?, ?, ?, ?)";
      InsertIntoConcertsBands = "INSERT INTO ConcertsBands (CID, BID) VALUES (?, ?)";
   }

   private static void writeResultSetCSV(String path, String sqlDMLStatement) {
      try {
         io.writeCSVFromTable(path, dbo.tableOutOfQuery(sqlDMLStatement));
      } catch (IOException | SQLException e) {
         e.printStackTrace();
      }
   }

   private static void printResultSet(String sqlDMLStatement) {
      try {
         dbo.tableOutOfQuery(sqlDMLStatement).printTable();
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   private static Table readCSVIntoTable(String path) {
      Table table = null;
      try {
         table = io.readCSVIntoTable(path);
      } catch (IOException e) {
         e.printStackTrace();
      }
      return table;
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
