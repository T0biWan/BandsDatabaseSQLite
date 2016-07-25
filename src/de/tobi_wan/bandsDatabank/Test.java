package de.tobi_wan.bandsDatabank;

public class Test extends DatabaseOperations {

   public static void main(String [] args) {
      initialiseAttributes();
      concertBandsForeignKeyConstraints();
      iTunesForeignKeyConstraints();
      connect(dbPath);
      createTable(createTableBands);
      createTable(createTableConcerts);
      createTable(createTableConcertsBands);
      createTable(createTableiTunes);
      insertData(InsertIntoBands, bands);
      insertData(InsertIntoConcerts, concerts);
      insertData(InsertIntoConcertsBands, concertsBands);
      insertData(InsertIntoiTunes, iTunes);
      printResultSet("SELECT * FROM Bands LIMIT 10");
      printResultSet("SELECT * FROM Concerts LIMIT 10");
      printResultSet("SELECT * FROM ConcertsBands LIMIT 10");
      printResultSet("SELECT * FROM iTunes LIMIT 10");
      dropTable("Bands");
      dropTable("Concerts");
      dropTable("ConcertsBands");
      dropTable("iTunes");
      disconnect();
   }

}
