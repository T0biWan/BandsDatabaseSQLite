package de.tobi_wan.bandsDatabank;

import java.io.IOException;
import tobi_wan.dataStructure.Table;



public class Test extends DatabaseOperations {

   public static void main(String [] args) {
      initialiseAttributes();
      connect(dbPath);
      createTable(createTableBands);
      createTable(createTableConcerts);
      createTable(createTableConcertsBands);
      createTable(createTableiTunes);
      // insertData(InsertIntoBands, bands);
      // insertData(InsertIntoConcerts, concerts);
      // insertData(InsertIntoConcertsBands, concertsBands);
      // insertData(InsertIntoITunes, iTunes);
      // printResultSet("SELECT * FROM Bands LIMIT 10");
      // printResultSet("SELECT * FROM Concerts LIMIT 10");
      // printResultSet("SELECT * FROM ConcertsBands LIMIT 10");
      // printResultSet("SELECT * FROM iTunes LIMIT 10");
      // dropTable("Bands");
      // dropTable("Concerts");
      // dropTable("ConcertsBands");
      // dropTable("iTunes");
      // disconnect();

      try {
         Table bands = io.readCSVIntoTable("BandsTest.csv");
         Table concerts = io.readCSVIntoTable("ConcertsTest.csv");
         Table concertsBands = io.readCSVIntoTable("ConcertsBandsTest.csv");
         Table iTunes = io.readCSVIntoTable("iTunesTest.csv");
         // bands.printTable();
         // concerts.printTable();
         // concertsBands.printTable();
         // iTunes.printTable();
         dbo.changeElementToForeignKey(concertsBands, 0, concerts, 1, 0);
         dbo.changeElementToForeignKey(concertsBands, 1, bands, 1, 0);
         // concertsBands.printTable();
         // // io.writeCSVFromTable("ConcertsBandsWithFK.csv", concertsBands);
         dbo.changeElementToForeignKey(iTunes, 2, bands, 1, 0);
         // iTunes.printTable();
         // io.writeCSVFromTable("iTunesWithFK.csv", iTunes);
         insertData(InsertIntoBands, bands);
         insertData(InsertIntoConcerts, concerts);
         insertData(InsertIntoConcertsBands, concertsBands);
         insertData(InsertIntoiTunes, iTunes);
         dropTable("Bands");
         dropTable("Concerts");
         dropTable("ConcertsBands");
         dropTable("iTunes");
      } catch (IOException e) {
         e.printStackTrace();
      }

   }

}
