package atm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


public class User 
{
	// First name of the user.
	private String firstName;
	
	// Last name of the user.
	private String lastName;
	
	// The ID number of the user.
	private String uuid; 
	
	// The MD5 hash of the user's pin number. 
	private byte pinHash [];
	
	// The list of accounts for this user.
	private ArrayList <Account> accounts;
	
	
	/**
	 * Create a new User.
	 * @param firstName  the user's first name.
	 * @param lastName	 the user's last name.
	 * @param pin		 the user's account pin number.
	 * @param theBank	 the Bank object that the User is a customer of.
	 */

	public User (String firstName, String lastName, String pin, Bank theBank)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		
		
		/*
		 * Store the pin's MD5 hash, 
		 * rather than the original value
		 * for security reason
		 */
	
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.err.println("ERROR - caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
	
		
		// Get a new unique universal ID for the User.
		this.uuid = theBank.getNewUserUUID();
		
		
		// Create empty list of accounts.
		this.accounts = new ArrayList<Account>();
		
		
		// Print log message.
		System.out.printf("New user %s, %s with ID %s created.\n", firstName, lastName, uuid );
	}
	
	
	/**
	 * Add an account for the user
	 * @param anAcct  the account to add
	 */
	
	public void addAccount(Account anAcct)
	{
		this.accounts.add(anAcct);
	}
	
	
	/**
	 * Return the user's UUID
	 * @return the uuid
	 */

	public String getUUID()
	{
		return this.uuid;
	}
	
	
	/**
	 * Check whether a given pin matches the true User pin
	 * @param aPin	pin to check
	 * @return		whether the pin is valid or not
	 */
	public boolean validatePin(String aPin)
	{
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
		}catch (NoSuchAlgorithmException e) {
			System.err.println("ERROR - caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		return false;
	}
	
	public String getFirstName()
	{
		return this.firstName;
	}
	
	
	/**
	 * Print summaries for the accounts of this user.
	 */
	public void printAccountsSummary()
	{
		System.out.printf("\n\n%s's accounts summary\n", this.firstName);
		for (int a = 0; a < this.accounts.size(); a++)
		{
			System.out.printf("  %d) %s\n", a+1,
					this.accounts.get(a).getSummaryLine());
		}
			System.out.println();
	}
	
	
	/**
	 * Get number of user's account's
	 * @return	number of account's
	 */
	public int numAccounts()
	{
		return this.accounts.size();
	}
	
	
	/**
	 * Print transaction history for a particular account.
	 * @param acctIdx	the index of the account to use.
	 */
	public void printAcctTransHistory(int acctIdx)
	{
		this.accounts.get(acctIdx).printTransHistory();
	}
	
	
	/**
	 * Get the balance of a particular account 
	 * @param acctIdx	index of the account to use
	 * @return			balance of the account 
	 */
	public double getAcctBalance(int acctIdx)
	{
		return this.accounts.get(acctIdx).getBalance();
	}
	
	
	/**
	 * Get the UUID of a particular account 
	 * @param acctIdx	index of the account to use 
	 * @return			UUID of the account
	 */
	public String getAcctUUID(int acctIdx)
	{
		return this.accounts.get(acctIdx).getUUID();
	}
	
	
	/**
	 * Add transaction to a particular account 
	 * @param acctIdx	index of the account
	 * @param amount	amount of the transaction 
	 * @param memo		memo of the transaction
	 */
	public void addAcctTransaction(int acctIdx, double amount, String memo)
	{
		this.accounts.get(acctIdx).addTransaction(amount, memo);
	}
}






