package atm;

import java.util.Scanner;

public class ATM 
{

	public static void main(String[] args) 
	{
		
		Scanner sc = new Scanner(System.in);
		
		
		Bank theBank = new Bank("Bank of Adrian");
		
		
		User aUser = theBank.addUser("Adrian", "G", "1234");
		
		
		Account newAccount = new Account("Checking", aUser, theBank);
		aUser.addAccount(newAccount);
		theBank.addAccount(newAccount);
		
		
		User curUser;
		while(true)
		{
			// Stay in the login prompt until successful login
			curUser = ATM.mainManuPrompt(theBank, sc);
			
			// Stay in main menu until user quits
			ATM.printUserMenu(curUser, sc);
		}
	}
	
	
	/**
	 * Print the ATM login menu
	 * @param theBank	the Bank object whose accounts to use 
	 * @param sc		Scanner to use for user input
	 * @return			the authenticated User
	 */
	public static User mainManuPrompt(Bank theBank, Scanner sc)
	{
		String userID;
		String pin;
		User authUser;
		
		/* Prompt the user for user ID/pin combo
		 * until a correct one is reached
		 */
		do{
			
			System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
			System.out.print("Enter user ID :" );
			userID = sc.nextLine();
			System.out.print("Enter PIN :");
			pin = sc.nextLine();
			
			// get the user corresponding to the ID and PIN combo
			authUser = theBank.userLogin(userID, pin);
			if(authUser == null)
			{
				System.out.println("Incorrect user ID/PIN"
									+ " Please try again");
			}
			
		// Continue looping until successful
		} while(authUser == null);
	
		return authUser;
	}

	
	public static void printUserMenu(User theUser, Scanner sc)
	{
		// Print a summary of user's accounts 
		theUser.printAccountsSummary();
		
		int choice;
		
		// User menu
		do {
			System.out.printf("Welcome %s, what would you like to do ?\n",
							  theUser.getFirstName());
			System.out.println(" 1) Show account transaction history");
			System.out.println(" 2) Withdraw");
			System.out.println(" 3) Deposit");
			System.out.println(" 4) Transfer");
			System.out.println(" 5) Quit");
			System.out.println();
			System.out.print("Enter the choice : ");
			choice = sc.nextInt();
			
		
			if(choice < 1 || choice > 5)
			{
				System.out.println("Invalid choice ! Please choose 1-5");
			}
			
		} while(choice < 1 || choice > 5);
		
		// process the choice 
		switch(choice)
		{
		case 1:
			ATM.showTransHistory(theUser, sc);
			break;
		case 2:
			ATM.withdrawFunds(theUser, sc);
			break;
		case 3:
			ATM.depositFunds(theUser, sc);
			break;
		case 4:
			ATM.transferFunds(theUser, sc);
			break;
		case 5:
			sc.nextLine();
			break;
		}
		
		// Redisplay menu, unless the user wants to quit.
		if(choice != 5 )
		{
			ATM.printUserMenu(theUser, sc);
		}
	}
	
	
	/**
	 * Show the transaction history for an account
	 * @param theUser	the logged in User
	 * @param sc		scanner for User input
	 */
	public static void showTransHistory(User theUser, Scanner sc)
	{
		int theAcct;
		
		
		// Get account whose transaction history to look at
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" +
					"whose transactions you want to see : ", theUser.numAccounts());
			
			theAcct = sc.nextInt()-1;
			if (theAcct < 0 || theAcct >= theUser.numAccounts())
			{
				System.out.println("Invalid account ! Please try again.");
			}
			
		} while (theAcct < 0 || theAcct >= theUser.numAccounts());
		
		// Print the transactions history.
		theUser.printAcctTransHistory(theAcct);
	}
	
	
	/**
	 * Process transferring funds from one account to another
	 * @param theUser	logged in user
	 * @param sc		scanner for user input
	 */
	public static void transferFunds(User theUser, Scanner sc)
	{
		int fromAcct;
		int toAcct;
		double amount;
		double acctBal;
		
		
		// Get the account to transfer from 
		do 
		{ 
			System.out.printf("Enter the number (1-%d) of the account\n" + 
					"to transfer from : ", theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if (fromAcct < 0 || fromAcct >= theUser.numAccounts())
			{
				System.out.println("Invalid account ! Please try again.");
			}
			
		} while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
		
		acctBal = theUser.getAcctBalance(fromAcct);
		
		
		// Get the account to transfer to
		do 
		{ 
			System.out.printf("Enter the number (1-%d) of the account\n" +
					"to transfer to : ", theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if (toAcct < 0 || toAcct >= theUser.numAccounts())
			{
				System.out.println("Invalid account ! Please try again.");
			}
			
		} while (toAcct < 0 || toAcct >= theUser.numAccounts());
		
		
		// Get the amount to transfer 
		do 
		{
			System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
			amount = sc.nextDouble();
			if (amount < 0)
			{
				System.out.println("Amount must be greater than 0");
		
			} else if (amount > acctBal) {
				System.out.printf("Amount can't be greater than\n" + "balance of $%.02f.\n", acctBal);
			}	
			
		} while (amount < 0 || amount > acctBal);
		
		
		// Do the transfer
		theUser.addAcctTransaction(fromAcct, -1*amount, String.format(
				"Transfer to account %s", theUser.getAcctUUID(toAcct)));
		theUser.addAcctTransaction(toAcct, amount, String.format(
				"Transfer to account %s", theUser.getAcctUUID(fromAcct)));
	}
	
	
	/**
	 * Process a fund withdraw from an account 
	 * @param theUser	logged in user
	 * @param sc		scanner for user input 
	 */
	public static void withdrawFunds(User theUser, Scanner sc)
	{
		int fromAcct;
		double amount;
		double acctBal;
		String memo;
		
		
		// Get the account to transfer from 
		do 
		{ 
			System.out.printf("Enter the number (1-%d) of the account\n" + 
					"to withdraw from : ", theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if (fromAcct < 0 || fromAcct >= theUser.numAccounts())
			{
				System.out.println("Invalid account ! Please try again.");
			}
			
		} while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
		
		acctBal = theUser.getAcctBalance(fromAcct);
	
		
		// Get the amount to transfer 
		do 
		{
			System.out.printf("Enter the amount to withdraw (max $%.02f): $", acctBal);
			amount = sc.nextDouble();
			if (amount < 0)
			{
				System.out.println("Amount must be greater than 0");
		
			} else if (amount > acctBal) {
				System.out.printf("Amount can't be greater than\n" + "balance of $%.02f.\n", acctBal);
			}	
			
		} while (amount < 0 || amount > acctBal);
		
		
		// Gobble up rest of previous input
		sc.nextLine();
		
		// Get a memo
		System.out.print("Enter a memo : ");
		memo =sc.nextLine();
		
		// Do the withdraw
		theUser.addAcctTransaction(fromAcct, -1*amount, memo);
	}
	
	
	/**
	 * Process a fund deposit to an account 
	 * @param theUser	logged in user
	 * @param sc		scanner for user input
	 */
	public static void depositFunds(User theUser, Scanner sc)
	{
		int toAcct;
		double amount;
		double acctBal;
		String memo;
		
		
		// Get the account to transfer from 
		do 
		{ 
			System.out.printf("Enter the number (1-%d) of the account\n" + 
					"to deposit in : ", theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if (toAcct < 0 || toAcct >= theUser.numAccounts())
			{
				System.out.println("Invalid account ! Please try again.");
			}
			
		} while (toAcct < 0 || toAcct >= theUser.numAccounts());
		
		acctBal = theUser.getAcctBalance(toAcct);
	
		
		// Get the amount to transfer 
		do 
		{
			System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
			amount = sc.nextDouble();
			if (amount < 0)
			{
				System.out.println("Amount must be greater than zero");
			} 	
			
		} while (amount < 0);
		
		
		// Gobble up rest of previous input
		sc.nextLine();
		
		// Get a memo
		System.out.print("Enter a memo : ");
		memo =sc.nextLine();
		
		// Do the withdraw
		theUser.addAcctTransaction(toAcct, amount, memo);
	}
}







