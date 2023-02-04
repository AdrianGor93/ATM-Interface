package atm;

import java.util.Date;


public class Transaction 
{
	// The amount of transactions.
	private double amount;
	
	// The time and date of transaction.
	private Date timestamp;
	
	// A memo of transaction.
	private String memo;
	
	// The account in which the transactions was performed.  
	private Account inAccount;

	
	/**
	 * Create new transaction
	 * @param amount	the amount transacted
	 * @param inAccount the account the transaction belong to
	 */
	public Transaction (double amount, Account inAccount) 
	{
		this.amount = amount;
		this.inAccount = inAccount;
		this.timestamp = new Date();
		this.memo = "";
	}
	
	
	/**
	 * Create new transaction
	 * @param amount	the amount transacted
	 * @param memo		the memo for the transaction
	 * @param inAccount	the account the transaction belong to
	 */
	public Transaction (double amount, String memo, Account inAccount)
	{
		this(amount, inAccount);
		this.memo = memo;
	}
	
	
	/**
	 * Get the amount of the transactions 
	 * @return the amount
	 */
	public double getAmount()
	{
		return this.amount;
	}
	
	
	/**
	 * Get a string summarizing the transactions 
	 * @return	the summary string
	 */
	public String getSummaryLine()
	{
		if(this.amount >= 0 )
		{
			return String.format("%s : $%.02f : %s", 
					this.timestamp.toString(),
					this.amount, this.memo);
		} else {
			
			return String.format("%s : $(%.02f) : %s", 
					this.timestamp.toString(),
					-this.amount, this.memo);
		}
	}
}





