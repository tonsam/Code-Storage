import java.util.Scanner;

public class ATM {
   // accounts
   private Account account1;
   private Account account2;

   private Scanner input;

   public ATM() {
       input = new Scanner( System.in );
       account1 = new Account( 1, 50.00 ); // create Account object
       account2 = new Account( 2, -7.53 ); // create Account object
       System.out.printf( "Initial balance for account %d: $%.2f\n", account1.getAccountNo(), account1.getBalance());
       System.out.printf( "Initial balance for account %d: $%.2f\n", account2.getAccountNo(), account2.getBalance());
   }

   // Execute the ATM
   public void execute() {    
      // create Scanner to obtain input from command window
 
      for (;;) {
          System.out.println("Select account");
          System.out.println("1 - account 1");
          System.out.println("2 - account 2");
          System.out.println("3 - exit");
          int cmd = input.nextInt();

          if (cmd == 3) {
             return;
          } else if (cmd == 1) {
             processAccount(account1); 
          } else if (cmd == 2) {
             processAccount(account2); 
          } else {
             System.out.println("Invalid selection");
             continue;
          }
      }
   }

   private void processAccount(Account account) {
      for (;;) {
         System.out.println("Select transaction");
         System.out.println("1 - balance");
         System.out.println("2 - deposit");
         System.out.pritnln("3 - withdraw");
         System.out.println("4 - exit");

         int cmd = input.nextInt();
          
         if (cmd == 1) {
       	     System.out.printf( "Balance for account %d: $%.2f\n", account.getAccountNo(), account.getBalance());
         } else if (cmd == 2) {
    	      double depositAmount; // deposit amount read from user

              System.out.printf( "Enter deposit amount for account %d: ", account.getAccountNo(); // prompt
              depositAmount = input.nextDouble(); // obtain user input
              System.out.printf( "\nadding %.2f to account1 balance\n\n", depositAmount );
              account.credit( depositAmount ); // add to account1 balance

       	      System.out.printf( "Balance for account %d: $%.2f\n", account.getAccountNo(), account.getBalance());
            
         } else if (cmd == 3) {
             System.out.println("Not implemented");
         } else if (cmd == 4) {
             return;
         } else  {
             System.out.println("Invalid selection");
             continue;
        }
      } 
   }
