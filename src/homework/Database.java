package homework;

/**
 * Database.java
 *
 * This class contains the methods the readers and writers will use
 * to coordinate access to the database. Access is coordinated using Java synchronization.
 *
 *
 * @author Ricky Stapler
 * @version 1.0 - April 4, 2015
 */
 
public class Database
{  
   public Database()
   {
      readerCount = 0;
      dbReading = false;
      dbWriting = false;
      lock = false;
   }

   // readers and writers will call this to nap
   public static void napping()
   {
     int sleepTime = (int) (NAP_TIME * Math.random() );
     try { Thread.sleep(sleepTime*1000); } 
     catch(InterruptedException e) {}
   }


   // reader will call this when they start reading
   public synchronized int startRead()
   { 
      while (dbWriting == true || lock == true)
      {
         try { wait(); }
         catch(InterruptedException e) {}
      }

      ++readerCount;

      // if I am the first reader tell all others
      // that the database is being read
      if (readerCount == 1)
         dbReading = true;
     
      return readerCount;
   }

   // reader will call this when they finish reading
   public synchronized int endRead()
   { 
      --readerCount;

      // if I am the last reader tell all others
      // that the database is no longer being read
      if (readerCount == 0)
         dbReading = false;
      
      notifyAll();

	System.out.println("Reader Count = " + readerCount);

      return readerCount;
   }
   
   // writer will call this when they start writing
    public synchronized void startWrite()
   { 
      lock = true;
      while (dbReading == true || dbWriting == true)
      {
         try { wait(); }
         catch(InterruptedException e) {}
      }

      // once there are either no readers or writers
      // indicate that the database is being written
      dbWriting = true;
   }

   // writer will call this when they start writing
   public synchronized void endWrite()
   { 
      dbWriting = false;
      lock = false;
      notifyAll();
   }

   // the number of active readers
   private int readerCount;

   // flags to indicate whether the database is
   // being read or written
   private boolean dbReading;
   private boolean dbWriting;
   private boolean lock;
    
   private static final int NAP_TIME = 5;
}
