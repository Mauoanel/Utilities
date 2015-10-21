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
import za.co.zailab.services.accounts.exceptions.WithdrawalAmountTooLargeException;
import za.co.zailab.services.accounts.transactions.AccountService;
import za.co.zailab.services.dataholders.DataHolder;

/**
 * @author Lawrence
 *
 */
public class CurrentAccount implements AccountService {

	private double balance;

	public CurrentAccount() {
		balance = 0;
	}

	public CurrentAccount(double initialBalance, double initialInterest) {
		balance = initialBalance;
	}

	@Override
	public void withdraw(Long accountId, int amountToWithdraw)
			throws AccountNotFoundException, WithdrawalAmountTooLargeException {

		DataHolder dataHolder = new DataHolder();
		Map<Long, Double> existingRecords = new HashMap<>();
		Map<Long, Double> existingOverDraftRecords = new HashMap<>();
		Double currentBalance = 0.0;
		boolean accountExists = false;

		existingRecords = dataHolder.currentDataHolder();
		existingOverDraftRecords = dataHolder.overDraftDataHolder();

		Iterator<Map.Entry<Long, Double>> entries = existingRecords.entrySet().iterator();
		AccNoAndBalance: while (entries.hasNext()) {
			Map.Entry<Long, Double> entry = entries.next();
			Long dataHolderAccNo = entry.getKey();
			Double dataHolderAccBal = entry.getValue();

			if (accountId.equals(dataHolderAccNo)) {
				Double overDraftLimit = existingOverDraftRecords.get(accountId);

				if (isWithdrawAmountGreater(overDraftLimit, dataHolderAccBal, amountToWithdraw)) {
					balance = dataHolderAccBal + overDraftLimit;
					currentBalance = dataHolderAccBal;
					balance = balance - amountToWithdraw;
					accountExists = true;
					break AccNoAndBalance;
				} else {
					throw new WithdrawalAmountTooLargeException(
							"Amount requested exceeds your balance and over draft limit.".toUpperCase());
				}
			}
		}
		if (!accountExists) {
			throw new AccountNotFoundException("");
		} else {

			System.out.println();
			System.out.println("Current Account.");
			System.out.println("Current Balance : R" + currentBalance);
			System.out.println("Withdrawn : R" + amountToWithdraw);
			System.out.println("New Balance : R" + balance);
			System.out.println();
		}
		balance = 0.0;

	}

	/**
	 * Checks if the withdrawal amount is not greater than the overdraft amount
	 * and the current balance.
	 * 
	 * @param overDraftLimit
	 *            limit of R100 000 per customer.
	 * @param dataHolderAccBal
	 *            is current balance of the customer.
	 * @param amountToWithdraw
	 *            is the amount to be withdrawn
	 * @return true if the amount is less than overdraft amount and the account
	 *         balance else false.
	 */
	private boolean isWithdrawAmountGreater(Double overDraftLimit, Double dataHolderAccBal, int amountToWithdraw) {
		return (amountToWithdraw < (overDraftLimit + dataHolderAccBal));
	}

	@Override
	public void deposit(Long accountId, int amountToDeposit) throws AccountNotFoundException {

		DataHolder dataHolder = new DataHolder();
		Map<Long, Double> existingRecords = new HashMap<>();
		Double currentBalance = 0.0;
		boolean accountExists = false;

		existingRecords = dataHolder.currentDataHolder();

		Iterator<Map.Entry<Long, Double>> entries = existingRecords.entrySet().iterator();
		AccNoAndBalance: while (entries.hasNext()) {
			Map.Entry<Long, Double> entry = entries.next();
			Long dataHolderAccNo = entry.getKey();
			Double dataHolderAccBal = entry.getValue();

			if (accountId.equals(dataHolderAccNo)) {
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
			System.out.println();
			System.out.println("Current Account.");
			System.out.println("Current Balance : R" + currentBalance);
			System.out.println("Deposited amount : R" + amountToDeposit);
			System.out.println("New Balance : R" + balance);
			System.out.println();
		}
		balance = 0.0;
	}

	@Override
	public void openSavingsAccount(Long accountId, Long amountToDeposit) {
	}

	@Override
	public void openCurrentAccount(Long accountId) {
		DataHolder dataHolder = new DataHolder();
		Map<Long, Double> existingRecords = new HashMap<>();

		existingRecords = dataHolder.currentDataHolder();
		boolean accountExists = false;

		Iterator<Map.Entry<Long, Double>> entries = existingRecords.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<Long, Double> entry = entries.next();
			Long dataHolderAccNo = entry.getKey();

			if (accountId.equals(dataHolderAccNo)) {
				try {
					accountExists = true;
					throw new AccountExistsException("Account Number already EXISTS!".toUpperCase());
				} catch (AccountExistsException e) {
					e.printStackTrace();
				}
			}
		}

		if ((accountExists == false)) {
			existingRecords.put(accountId, (double) 0.0);
			displayNewAccount(accountId, 0L);
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
		System.out.println("Current Account Opened Successfully.");
		System.out.println("Acc No :" + accountId);
		System.out.println("New Balance : R" + amountToDeposit);
		System.out.println();
		balance = 0.0;
	}

}
=======
/**
 * 
 */
package za.co.zailab.services.accounts;

import za.co.zailab.services.accounts.exceptions.AccountNotFoundException;
import za.co.zailab.services.accounts.exceptions.WithdrawalAmountTooLargeException;
import za.co.zailab.services.accounts.transactions.AccountService;

/**
 * @author Lawrence
 *
 */
public class CurrentAccount implements AccountService {

	private double balance;

	public CurrentAccount() {
		balance = 0;
	}

	public CurrentAccount(double initialBalance, double initialInterest) {
		balance = initialBalance;
	}

	@Override
	public void withdraw(Long accountId, int amountToWithdraw)
			throws AccountNotFoundException, WithdrawalAmountTooLargeException {
		balance = balance - amountToWithdraw;
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
