<<<<<<< HEAD
package za.co.zailab.services.launch;

import za.co.zailab.services.accounts.CurrentAccount;
import za.co.zailab.services.accounts.SavingsAccount;
import za.co.zailab.services.accounts.exceptions.AccountNotFoundException;
import za.co.zailab.services.accounts.exceptions.WithdrawalAmountTooLargeException;

public class Launch {

	public static void main(String[] args) {

		Launch launch = new Launch();
		launch.start();
	}

	/**
	 * Start program execution
	 */
	private void start() {

		// savings withdraw
		SavingsAccount savingsAccount = new SavingsAccount();
		CurrentAccount currentAccount = new CurrentAccount();

		// perform savings transactions
		startSavingTransactions(savingsAccount);
		// perform current account transactions
		startCurrentTransactions(currentAccount);
	}

	private void startCurrentTransactions(CurrentAccount currentAccount) {

		try {
			currentAccount.deposit(203L, 700);
			currentAccount.withdraw(201L, 500);
			currentAccount.openCurrentAccount(209L);
		} catch (AccountNotFoundException | WithdrawalAmountTooLargeException e) {
			if (e instanceof AccountNotFoundException) {
				try {
					throw new AccountNotFoundException("Account NOT found!".toUpperCase());
				} catch (AccountNotFoundException e1) {
					e1.printStackTrace();
				}
			} else {
				try {
					throw new WithdrawalAmountTooLargeException("Withdrawal amount too large!".toUpperCase());
				} catch (WithdrawalAmountTooLargeException e1) {
					e1.printStackTrace();
				}
			}
		}

	}

	/**
	 * Start the savings account transactions.
	 * 
	 * @param savings
	 *            object
	 */
	private static void startSavingTransactions(SavingsAccount savingsAccount) {

		// Savings
		try {
			savingsAccount.withdraw(101L, 500);
			savingsAccount.deposit(105L, 700);
			savingsAccount.openSavingsAccount(106L, 2000L);
		} catch (AccountNotFoundException | WithdrawalAmountTooLargeException e) {
			if (e instanceof AccountNotFoundException) {
				try {
					throw new AccountNotFoundException("Account NOT found!".toUpperCase());
				} catch (AccountNotFoundException e1) {
					e1.printStackTrace();
				}
			} else {
				try {
					throw new WithdrawalAmountTooLargeException("Withdrawal amount too large!".toUpperCase());
				} catch (WithdrawalAmountTooLargeException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
=======
package za.co.zailab.services.launch;

import za.co.zailab.services.accounts.SavingsAccount;
import za.co.zailab.services.accounts.exceptions.AccountNotFoundException;
import za.co.zailab.services.accounts.exceptions.WithdrawalAmountTooLargeException;

public class Launch {

	public static void main(String[] args) {

		Launch launch = new Launch();
		launch.start();
	}

	/**
	 * Start program execution
	 */
	private void start() {

		// savings withdraw
		withdraw(new SavingsAccount());

	}

	/**
	 * 
	 * @param savings
	 */
	private static void withdraw(SavingsAccount savings) {

		// Savings
		SavingsAccount savingsAccount = new SavingsAccount();
		try {
			savingsAccount.withdraw(101L, 500);
		} catch (AccountNotFoundException | WithdrawalAmountTooLargeException e) {
			if (e instanceof AccountNotFoundException) {
				try {
					throw new AccountNotFoundException("Account NOT found!");
				} catch (AccountNotFoundException e1) {
					e1.printStackTrace();
				}
			} else {
				try {
					throw new WithdrawalAmountTooLargeException("Withdrawal amount too large!");
				} catch (WithdrawalAmountTooLargeException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
>>>>>>> origin/master
