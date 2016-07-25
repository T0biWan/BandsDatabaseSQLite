package de.tobi_wan.bandsDatabank;

import java.io.IOException;
import java.sql.SQLException;
import tobi_wan.IO.IOStreamTableCSV;
import tobi_wan.dataStructure.Table;
import tobi_wan.databaseOperations.DatabaseOperationsSQLite;
import tobi_wan.support.StandardOutput;



public class DatabaseOperations {

   protected static StandardOutput           s;
   protected static String                   dbPath;
   protected static DatabaseOperationsSQLite dbo;
   protected static IOStreamTableCSV         io;

   protected static Table                    bands;
   protected static Table                    concerts;
   protected static Table                    concertsBands;
   protected static Table                    iTunes;

   protected static String                   createTableBands;
   protected static String                   createTableConcerts;
   protected static String                   createTableConcertsBands;
   protected static String                   createTableiTunes;

   protected static String                   InsertIntoBands;
   protected static String                   InsertIntoConcerts;
   protected static String                   InsertIntoConcertsBands;
   protected static String                   InsertIntoiTunes;

   protected static String                   joinAllTables;
   protected static String                   joinAllTablesStatement;
   protected static String                   countBandsStatement;
   protected static String                   selectBandsStatement;
   protected static String                   countBandsISawStatement;
   protected static String                   countEventsStatement;
   protected static String                   countBandsISawPerEvent;
   protected static String                   countBandsISawPerYear;
   protected static String                   countInterpreten;
   protected static String                   countSongsPerInterpret;
   protected static String                   countAlbumsPerInterpret;
   protected static String                   countMrMetalPlusSongsPerInterpret;
   protected static String                   showAlbumsPerInterpret;

   protected static void initialiseAttributes() {
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
      createTableiTunes = "CREATE TABLE IF NOT EXISTS iTunes (Title, Duration, BID, Album, Genre, Year, Comment)";

      InsertIntoBands = "INSERT INTO Bands (BID, Band) VALUES (?, ?)";
      InsertIntoConcerts = "INSERT INTO Concerts (CID, Concert, Date, Place) VALUES (?, ?, ?, ?)";
      InsertIntoConcertsBands = "INSERT INTO ConcertsBands (CID, BID) VALUES (?, ?)";
      InsertIntoiTunes = "INSERT INTO iTunes (Title, Duration, BID, Album, Genre, Year, Comment) VALUES (?, ?, ?, ?, ?, ?, ?)";

      joinAllTables = " FROM Bands INNER JOIN ConcertsBands ON Bands.BID = ConcertsBands.BID INNER JOIN Concerts On Concerts.CID = ConcertsBands.CID ";
      countBandsStatement = "SELECT COUNT(Band) AS Bands FROM Bands";
      selectBandsStatement = "SELECT Band FROM Bands";
      countBandsISawStatement = "SELECT COUNT(Band) AS Anzahl, Band" + joinAllTables + "GROUP BY Band ORDER BY COUNT(Band) DESC";
      countEventsStatement = "SELECT COUNT(Concert) AS Events FROM Concerts";
      countBandsISawPerEvent = "SELECT COUNT(Band) AS Anzahl, Concert, Date" + joinAllTables + "GROUP BY Concerts.CID ORDER BY COUNT(Band) DESC";
      countBandsISawPerYear = "";
      joinAllTablesStatement = "SELECT Place, Date, Concert, Band" + joinAllTables + "ORDER BY Concert";
      countInterpreten = "SELECT COUNT(DISTINCT Interpret) FROM iTunes";
      countSongsPerInterpret = "SELECT BID, COUNT(Title) FROM iTunes GROUP BY BID ORDER BY COUNT(BID) DESC";
      countAlbumsPerInterpret = "SELECT BID, COUNT(DISTINCT Album) FROM iTunes GROUP BY BID ORDER BY COUNT(DISTINCT Album) DESC";
      countMrMetalPlusSongsPerInterpret = "SELECT BID, COUNT(Title) FROM iTunes WHERE Comment = 'Mr. Metal +' GROUP BY BID ORDER BY COUNT(BID) DESC";
      showAlbumsPerInterpret = "SELECT BID, Album FROM iTunes GROUP BY Album ORDER BY BID, Album";
   }

   protected static void writeResultSetCSV(String path, String sqlDMLStatement) {
      try {
         io.writeCSVFromTable(path, dbo.tableOutOfQuery(sqlDMLStatement));
      } catch (IOException | SQLException e) {
         e.printStackTrace();
      }
   }

   protected static void printResultSet(String sqlDMLStatement) {
      try {
         dbo.tableOutOfQuery(sqlDMLStatement).printTableWithHeadline();
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   protected static Table readCSVIntoTable(String path) {
      Table table = null;
      try {
         table = io.readCSVIntoTable(path);
      } catch (IOException e) {
         e.printStackTrace();
      }
      return table;
   }

   protected static void dropTable(String sqlStatement) {
      try {
         dbo.dropTable(sqlStatement);
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   protected static void createTable(String sqlStatement) {
      try {
         dbo.sqlDataDefinition(sqlStatement);
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   protected static void insertData(String sqlStatement, Table table) {
      try {
         dbo.insertWithPreparedStatement(sqlStatement, table);
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   protected static void connect(String dbPath) {
      try {
         dbo.connect(dbPath);
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   protected static void disconnect() {
      try {
         dbo.disconnect();
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   protected static void concertBandsForeignKeyConstraints() {
      dbo.changeValueToForeignKey(concertsBands, 0, concerts, 1, 0);
      dbo.changeValueToForeignKey(concertsBands, 1, bands, 1, 0);
      try {
         io.writeCSVFromTable("data/concertsBands.csv", concertsBands);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   protected static void iTunesForeignKeyConstraints() {
      dbo.changeValueToForeignKey(iTunes, 2, bands, 1, 0);
      try {
         io.writeCSVFromTable("data/iTunes.csv", iTunes);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

}
