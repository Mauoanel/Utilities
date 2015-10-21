<<<<<<< HEAD
/**
 * 
 */
package za.co.zailab.services.accounts;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import za.co.zailab.services.accounts.exceptions.AccountExistsException;
import za.co.zailab.services.accounts.exceptions.AccountNotFoundException;
import za.co.zailab.services.accounts.exceptions.MinimumAmountRequiredException;
import za.co.zailab.services.accounts.exceptions.WithdrawalAmountTooLargeException;
import za.co.zailab.services.accounts.rules.SavingsRules;
import za.co.zailab.services.accounts.transactions.AccountService;
import za.co.zailab.services.dataholders.DataHolder;

/**
 * @author Lawrence
 *
 */
public class SavingsAccount implements AccountService {

	private double balance;

	public SavingsAccount() {
		balance = 0;
	}

	public SavingsAccount(double initialBalance, double initialInterest) {
		balance = initialBalance;
	}

	@Override
	public void withdraw(Long accountId, int amountToWithdraw)
			throws AccountNotFoundException, WithdrawalAmountTooLargeException {

		DataHolder dataHolder = new DataHolder();
		Map<Long, Double> existingRecords = new HashMap<>();
		Double currentBalance = 0.0;
		boolean accountExists = false;

		existingRecords = dataHolder.savingsDataHolder();

		Iterator<Map.Entry<Long, Double>> entries = existingRecords.entrySet().iterator();
		AccNoAndBalance: while (entries.hasNext()) {
			Map.Entry<Long, Double> entry = entries.next();
			Long dataHolderAccNo = entry.getKey();
			Double dataHolderAccBal = entry.getValue();

			if ((dataHolderAccNo == accountId) && (SavingsRules.isMinimumAccountEnough((double) dataHolderAccBal))) {
				balance = dataHolderAccBal;
				currentBalance = dataHolderAccBal;
				balance = balance - amountToWithdraw;
				accountExists = true;
				break AccNoAndBalance;
			}
		}
		if (!accountExists) {
			throw new AccountNotFoundException("");
		} else {

			System.out.println();
			System.out.println("Saving Account.");
			System.out.println("Current Balance : R" + currentBalance);
			System.out.println("Withdrawn : R" + amountToWithdraw);
			System.out.println("New Balance : R" + balance);
			System.out.println();
		}
		balance = 0.0;
	}

	@Override
	public void deposit(Long accountId, int amountToDeposit) throws AccountNotFoundException {

		DataHolder dataHolder = new DataHolder();
		Map<Long, Double> existingRecords = new HashMap<>();
		Double currentBalance = 0.0;
		boolean accountExists = false;

		existingRecords = dataHolder.savingsDataHolder();

		Iterator<Map.Entry<Long, Double>> entries = existingRecords.entrySet().iterator();
		AccNoAndBalance: while (entries.hasNext()) {
			Map.Entry<Long, Double> entry = entries.next();
			Long dataHolderAccNo = entry.getKey();
			Double dataHolderAccBal = entry.getValue();

			if ((dataHolderAccNo == accountId) && (SavingsRules.isMinimumAccountEnough((double) dataHolderAccBal))) {
				balance = dataHolderAccBal;
				currentBalance = dataHolderAccBal;
				balance = balance + amountToDeposit;
				accountExists = true;
				break AccNoAndBalance;
			}
		}

		if (!accountExists) {
			throw new AccountNotFoundException("");
		} else {

			System.out.println("Saving Account.");
			System.out.println("Current Balance : R" + currentBalance);
			System.out.println("Deposited amount : R" + amountToDeposit);
			System.out.println("New Balance : R" + balance);
		}
		balance = 0.0;
	}

	@Override
	public void openSavingsAccount(Long accountId, Long amountToDeposit) {

		DataHolder dataHolder = new DataHolder();
		Map<Long, Double> existingRecords = new HashMap<>();

		existingRecords = dataHolder.savingsDataHolder();
		boolean accountExists = false;

		Iterator<Map.Entry<Long, Double>> entries = existingRecords.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<Long, Double> entry = entries.next();
			Long dataHolderAccNo = entry.getKey();

			if ((dataHolderAccNo == accountId)) {
				try {
					accountExists = true;
					throw new AccountExistsException("Account Number already EXISTS!".toUpperCase());
				} catch (AccountExistsException e) {
					e.printStackTrace();
				}
			}
		}

		if ((accountExists == false)) {
			try {
				if (SavingsRules.isMinimumAccountEnough(amountToDeposit)) {
					existingRecords.put(accountId, (double) amountToDeposit);
					displayNewAccount(accountId, amountToDeposit);
				} else {
					throw new MinimumAmountRequiredException("R" + amountToDeposit
							+ " is not the minimum required in opening this account. Minimum required is R1000"
									.toUpperCase());
				}
			} catch (MinimumAmountRequiredException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Display the newly added account.
	 * 
	 * @param accountId
	 *            no to be opened.
	 * @param amountToDeposit
	 *            amount to be deposited!
	 */
	private void displayNewAccount(Long accountId, Long amountToDeposit) {

		System.out.println();
		System.out.println("Saving Account Opened Successfully.");
		System.out.println("Acc No :" + accountId);
		System.out.println("New Balance : R" + amountToDeposit);
		System.out.println();
		balance = 0.0;
	}

	@Override
	public void openCurrentAccount(Long accountId) {	}
}
=======
/**
 * 
 */
package za.co.zailab.services.accounts;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import za.co.zailab.services.accounts.exceptions.AccountNotFoundException;
import za.co.zailab.services.accounts.exceptions.WithdrawalAmountTooLargeException;
import za.co.zailab.services.accounts.rules.SavingsRules;
import za.co.zailab.services.accounts.transactions.AccountService;
import za.co.zailab.services.dataholders.DataHolder;

/**
 * @author Lawrence
 *
 */
public class SavingsAccount implements AccountService {

	private double balance;

	public SavingsAccount() {
		balance = 0;
	}

	public SavingsAccount(double initialBalance, double initialInterest) {
		balance = initialBalance;
	}

	@Override
	public void withdraw(Long accountId, int amountToWithdraw)
			throws AccountNotFoundException, WithdrawalAmountTooLargeException {

		DataHolder dataHolder = new DataHolder();
		Map<Long, Double> existingRecords = new HashMap<>();
		Double currentBalance = 0.0;

		existingRecords = dataHolder.savingsDataHolder();

		Iterator<Map.Entry<Long, Double>> entries = existingRecords.entrySet().iterator();
		AccNoAndBalance: while (entries.hasNext()) {
			Map.Entry<Long, Double> entry = entries.next();
			Long dataHolderAccNo = entry.getKey();
			Double dataHolderAccBal = entry.getValue();

			if ((dataHolderAccNo == accountId) && (SavingsRules.isMinimumAccountEnough((double) dataHolderAccBal))) {
				balance = dataHolderAccBal;
				currentBalance = dataHolderAccBal;
				balance = balance - amountToWithdraw;
				break AccNoAndBalance;
			}
		}

		System.out.println("Saving Account.");
		System.out.println("Current Balance : R" + currentBalance);
		System.out.println("Withdrawn : R" + amountToWithdraw);
		System.out.println("New Balance : R" + balance);
	}

	@Override
	public void deposit(Long accountId, int amountToDeposit) throws AccountNotFoundException {
		balance = balance + amountToDeposit;

	}

	@Override
	public void openSavingsAccount(Long accountId, Long amountToDeposit) {
		// TODO Auto-generated method stub

	}

	@Override
	public void openCurrentAccount(Long accountId) {
		// TODO Auto-generated method stub

	}
}
>>>>>>> origin/master
