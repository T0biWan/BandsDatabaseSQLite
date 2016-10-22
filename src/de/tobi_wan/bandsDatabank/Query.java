package de.tobi_wan.bandsDatabank;

public class Query extends DatabaseOperations {
   public static void main(String [] args) {
      initialiseAttributes();
      connect(dbPath);
      printComplexResultSet("SELECT SUM(Duration) FROM iTunes WHERE Comment = 'Mr. Metal +'");
      disconnect();
   }
}
