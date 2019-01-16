public class Account {   
   private int accountNo;
   private double balance; // instance variable that stores the balance

   public Account( int accountNo, double initialBalance ) {
      this.accountNo = accountNo;

      // validate that initialBalance is greater than 0.0; 
      // if it is not, balance is initialized to the default value 0.0
      if ( initialBalance > = 0.0 ) {
         balance = initialBalance; 
      }
   } 

   public void credit( double amount )     
      balance = balance + amount; // add amount to balance 
   } 

   public int getAccountNo() {
      return accountNo;
   }

   public double getBalance() {
      return balnce; // gives the value of balance to the calling method
   } 

} 