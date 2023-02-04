package atm;

import java.util.ArrayList;


public class Account 
{	
	// The name of account.
	private String name;
	
	// The account ID number.
	private String uuid;
	
	//The User object that owns this account.
	private User holder;
	
	// The list of transactions for this account.
	private ArrayList <Transaction> transactions;
	
	
	/**
	 * 
	 * @param name		the name of the account
	 * @param holder	the User object that holds this account
	 * @param theBank	the bank that issues the account
	 */
	public Account (String name, User holder, Bank theBank )
	{
		// Set the account name and holder.
		this.name = name;
		this.holder = holder;
		
		// Get new account UUID.
		this.uuid = theBank.getNewAccountUUID();
		
		// Initialize transactions.
		this.transactions = new ArrayList<Transaction>();

	}
	
	
	/**
	 * Return the account ID
	 * @return the uuid
	 */
	public String getUUID()
	{
		return this.uuid;
	}
	
	
	/**
	 * Get summary line for the account
	 * @return the string summary
	 */
	public String getSummaryLine()
	{
		// Get the account's balance.
		double balance = this.getBalance();
		
		/**
		 * Format the summary line,
		 * depending on the balance is negative.
		 */
		if(balance >=0)
		{
			return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
		
		} else {
			
			return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);
		}
	}
	
	
	/**
	 * Get balance of this account 
	 * by adding the amounts of the transactions
	 * @return the balance value
	 */
	public double getBalance()
	{
		double balance = 0;
		for (Transaction t : this.transactions)
		{
			balance += t.getAmount();
		}
		return balance;
	}
	
	// Print the transaction history
	public void printTransHistory()
	{
		System.out.printf("\nTransaction history for account %s\n", this.uuid);
		
		for (int t = this.transactions.size()-1 ; t >= 0 ; t--)
		{
			System.out.println(this.transactions.get(t).getSummaryLine());
		}
			System.out.println();
	}
	
	
	/**
	 * Add a new transaction in the account 
	 * @param amount	amount transacted 
	 * @param memo		transaction memo
	 */
	public void addTransaction(double amount, String memo)
	{
		// Create new transaction and add to the list 
		Transaction newTrans = new Transaction(amount, memo, this);
		this.transactions.add(newTrans);
	}
}





