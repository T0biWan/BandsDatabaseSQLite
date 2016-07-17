package de.tobi_wan.bandsDatabank;

import java.io.IOException;
import java.sql.SQLException;
import tobi_wan.IO.IOStreamTableCSV;
import tobi_wan.dataStructure.Table;
import tobi_wan.databaseOperations.DatabaseOperationsSQLite;
import tobi_wan.support.StandardOutput;



public class DatabaseOperations {

   static StandardOutput           s;
   static String                   dbPath;
   static DatabaseOperationsSQLite dbo;
   static IOStreamTableCSV         io;
   static Table                    bands;
   static Table                    concerts;
   static Table                    concertsBands;
   static Table                    iTunes;
   static String                   createTableBands;
   static String                   createTableConcerts;
   static String                   createTableConcertsBands;
   static String                   createTableITunes;
   static String                   InsertIntoBands;
   static String                   InsertIntoConcerts;
   static String                   InsertIntoConcertsBands;
   static String                   InsertIntoITunes;
   static String                   joinAllTables;
   static String                   joinAllTablesStatement;
   static String                   countBandsStatement;
   static String                   selectBandsStatement;
   static String                   countBandsISawStatement;
   static String                   countEventsStatement;
   static String                   countBandsISawPerEvent;
   static String                   countBandsISawPerYear;
   static String                   countInterpreten;
   static String                   countSongsPerInterpret;
   static String                   countMrMetalPlusSongsPerInterpret;

   public static void initialiseAttributes() {
      s = new StandardOutput("*", 80);
      dbPath = "db/bands.db";
      dbo = new DatabaseOperationsSQLite();
      io = new IOStreamTableCSV(";");

      bands = readCSVIntoTable("data/bands.csv");
      concerts = readCSVIntoTable("data/concerts.csv");
      concertsBands = readCSVIntoTable("data/concertsBands.csv");
      iTunes = readCSVIntoTable("data/iTunes.csv");

      createTableBands = "CREATE TABLE IF NOT EXISTS Bands (BID INT PRIMARY KEY NOT NULL, Band TEXT)";
      createTableConcerts = "CREATE TABLE IF NOT EXISTS Concerts (CID INT PRIMARY KEY NOT NULL, Concert TEXT, Date TEXT, Place TEXT)";
      createTableConcertsBands = "CREATE TABLE IF NOT EXISTS ConcertsBands (CID INT, BID INT)";
      createTableITunes = "CREATE TABLE IF NOT EXISTS iTunes (Name, Duration, Interpret, Album, Genre, Year, Commentary, Space)";

      InsertIntoBands = "INSERT INTO Bands (BID, Band) VALUES (?, ?)";
      InsertIntoConcerts = "INSERT INTO Concerts (CID, Concert, Date, Place) VALUES (?, ?, ?, ?)";
      InsertIntoConcertsBands = "INSERT INTO ConcertsBands (CID, BID) VALUES (?, ?)";
      InsertIntoITunes = "INSERT INTO iTunes (Name, Duration, Interpret, Album, Genre, Year, Commentary, Space) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

      joinAllTables = " FROM Bands INNER JOIN ConcertsBands ON Bands.BID = ConcertsBands.BID INNER JOIN Concerts On Concerts.CID = ConcertsBands.CID ";
      countBandsStatement = "SELECT COUNT(Band) AS Bands FROM Bands";
      selectBandsStatement = "SELECT Band FROM Bands";
      countBandsISawStatement = "SELECT COUNT(Band) AS Anzahl, Band" + joinAllTables + "GROUP BY Band ORDER BY COUNT(Band) DESC";
      countEventsStatement = "SELECT COUNT(Concert) AS Events FROM Concerts";
      countBandsISawPerEvent = "SELECT COUNT(Band) AS Anzahl, Concert, Date" + joinAllTables + "GROUP BY Concerts.CID ORDER BY COUNT(Band) DESC";
      countBandsISawPerYear = "";
      joinAllTablesStatement = "SELECT Place, Date, Concert, Band" + joinAllTables + "ORDER BY Concert";
      countInterpreten = "SELECT COUNT(DISTINCT Interpret) FROM iTunes";
      countSongsPerInterpret = "SELECT Interpret, COUNT(Name) FROM iTunes GROUP BY Interpret ORDER BY COUNT(Interpret) DESC";
      countMrMetalPlusSongsPerInterpret = "SELECT Interpret, COUNT(Name) FROM iTunes WHERE Commentary = 'Mr. Metal +' GROUP BY Interpret ORDER BY COUNT(Interpret) DESC";
   }

   public static void writeResultSetCSV(String path, String sqlDMLStatement) {
      try {
         io.writeCSVFromTable(path, dbo.tableOutOfQuery(sqlDMLStatement));
      } catch (IOException | SQLException e) {
         e.printStackTrace();
      }
   }

   public static void printResultSet(String sqlDMLStatement) {
      try {
         dbo.tableOutOfQuery(sqlDMLStatement).printTable();
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   public static Table readCSVIntoTable(String path) {
      Table table = null;
      try {
         table = io.readCSVIntoTable(path);
      } catch (IOException e) {
         e.printStackTrace();
      }
      return table;
   }

   public static void dropTable(String sqlStatement) {
      try {
         dbo.dropTable(sqlStatement);
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   public static void createTable(String sqlStatement) {
      try {
         dbo.sqlDataDefinition(sqlStatement);
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   public static void insertData(String sqlStatement, Table table) {
      try {
         dbo.insertWithPreparedStatement(sqlStatement, table);
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   public static void connect(String dbPath) {
      try {
         dbo.connect(dbPath);
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   public static void disconnect() {
      try {
         dbo.disconnect();
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }
}
