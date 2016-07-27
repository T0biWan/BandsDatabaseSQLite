package de.tobi_wan.bandsDatabank;

public class Query extends DatabaseOperations {
   public static void main(String [] args) {
      initialiseAttributes();
      connect(dbPath);
      printComplexResultSet("SELECT Bands.Band, count(iTunes.Album) FROM iTunes INNER JOIN Bands ON iTunes.BID = Bands.BID GROUP BY Bands.band, iTunes.Album");
      disconnect();
   }
}
