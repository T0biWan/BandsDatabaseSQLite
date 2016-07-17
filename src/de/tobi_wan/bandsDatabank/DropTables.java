package de.tobi_wan.bandsDatabank;

public class DropTables extends DatabaseOperations {

   public static void main(String [] args) {
      initialiseAttributes();
      connect(dbPath);
      dropTable("Bands");
      dropTable("Concerts");
      dropTable("ConcertsBands");
      dropTable("iTunes");
      disconnect();
   }

}
