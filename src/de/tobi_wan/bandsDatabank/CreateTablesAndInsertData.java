package de.tobi_wan.bandsDatabank;

public class CreateTablesAndInsertData extends DatabaseOperations {

   public static void main(String [] args) {
      initialiseAttributes();
      connect(dbPath);
      createTable(createTableBands);
      createTable(createTableConcerts);
      createTable(createTableConcertsBands);
      createTable(createTableITunes);
      insertData(InsertIntoBands, bands);
      insertData(InsertIntoConcerts, concerts);
      insertData(InsertIntoConcertsBands, concertsBands);
      insertData(InsertIntoITunes, iTunes);
      disconnect();
   }

}
