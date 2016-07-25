package de.tobi_wan.bandsDatabank;

public class Query extends DatabaseOperations {
   public static void main(String [] args) {
      initialiseAttributes();
      connect(dbPath);
      printResultSet("SELECT * FROM Bands LIMIT 10");
      disconnect();
   }
}
