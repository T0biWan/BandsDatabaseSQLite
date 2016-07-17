package de.tobi_wan.bandsDatabank;

public class CreateTablesAndInsertData extends DatabaseOperations {

   public static void main(String [] args) {
      initialiseAttributes();
      connect(dbPath);
      createTable(createTableBands);
      createTable(createTableConcerts);
      createTable(createTableConcertsBands);
      createTable(createTableiTunes);
      insertData(InsertIntoBands, bands);
      insertData(InsertIntoConcerts, concerts);
      insertData(InsertIntoConcertsBands, concertsBands);
      insertData(InsertIntoiTunes, iTunes);
      disconnect();
   }

}
