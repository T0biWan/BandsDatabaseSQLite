package de.tobi_wan.bandsDatabank;

import java.io.IOException;
import java.sql.SQLException;
import tobi_wan.IO.IOStreamTableCSV;
import tobi_wan.dataStructure.Table;
import tobi_wan.databaseOperations.DatabaseOperationsSQLite;
import tobi_wan.support.StandardOutput;



public class Test {
   static StandardOutput           s;
   static String                   dbPath;
   static DatabaseOperationsSQLite dbo;
   static IOStreamTableCSV         io;
   static Table                    bands;
   static Table                    konzerte;
   static Table                    konzerteBands;
   static String                   createTableBands;
   static String                   createTableKonzerte;
   static String                   createTableKonzerteBands;
   static String                   InsertIntoBands;
   static String                   InsertIntoKonzerte;
   static String                   InsertIntoKonzerteBands;

   public static void main(String [] args) {
      initialiseAttributes();
      connect(dbPath);
      createTable(createTableBands);
      createTable(createTableKonzerte);
      createTable(createTableKonzerteBands);
      insertData(InsertIntoBands, bands);
      insertData(InsertIntoKonzerte, konzerte);
      insertData(InsertIntoKonzerteBands, konzerteBands);
      // printResultSet("SELECT * FROM Bands");
      // printResultSet("SELECT * FROM Konzerte");
      // printResultSet("SELECT * FROM KonzerteBands");
      dropTable("Bands");
      dropTable("Konzerte");
      dropTable("KonzerteBands");
      disconnect();
   }

   private static void initialiseAttributes() {
      s = new StandardOutput("*", 80);
      dbPath = "db/bands.db";
      dbo = new DatabaseOperationsSQLite();
      io = new IOStreamTableCSV(";");
      bands = readCSVIntoTable("data/bands.csv");
      konzerte = readCSVIntoTable("data/konzerte.csv");
      konzerteBands = readCSVIntoTable("data/konzerteBands.csv");
      createTableBands = "CREATE TABLE IF NOT EXISTS Bands (BID INT PRIMARY KEY NOT NULL, Band TEXT)";
      createTableKonzerte = "CREATE TABLE IF NOT EXISTS Konzerte (KID INT PRIMARY KEY NOT NULL, Konzert TEXT, Datum TEXT, Ort TEXT)";
      createTableKonzerteBands = "CREATE TABLE IF NOT EXISTS KonzerteBands (KID INT, BID INT)";
      InsertIntoBands = "INSERT INTO Bands (BID, Band) VALUES (?, ?)";
      InsertIntoKonzerte = "INSERT INTO Konzerte (KID, Konzert, Datum, Ort) VALUES (?, ?, ?, ?)";
      InsertIntoKonzerteBands = "INSERT INTO KonzerteBands (KID, BID) VALUES (?, ?)";
   }

   private static void printResultSet(String sqlDMLStatement) {
      try {
         s.println(dbo.generateTableFromQuery(sqlDMLStatement));
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
