package homework;

/**
 * Writer.java
 *
 * A writer to the database.
 *
 * @author Ricky Stapler
 * @version 1.0 - April 4, 2015
 */

public class Writer extends Thread
{
   public Writer(int w, Database db)
   {
      writerNum = w;
      server = db;
   }

   public void run()
   {
     while (true)
      {
       System.out.println("writer " + writerNum + " is sleeping.");
       Database.napping();
       
       System.out.println("writer " + writerNum + " wants to write.");
       server.startWrite();
       
       // you have access to write to the database
       System.out.println("writer " + writerNum + " is writing.");
       Database.napping();

       server.endWrite();
       System.out.println("writer " + writerNum + " is done writing.");
      }
   }
   
   private Database  server;
   private int       writerNum;
}
