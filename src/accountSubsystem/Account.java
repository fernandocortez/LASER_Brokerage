package accountSubsystem;

import java.util.ArrayList;
import usersSubsystem.Investor;
import java.io.Serializable;

/**
 * The record of all CashTransactions and MarginTransactions between the
 * Investor and the Brokerage, along with current cashBalance, marginBalance,
 * and other business parameters
 * 
 * @author David Brodeur
 * @version 201211200001
 * @version DB201211201346
 * @version 20121129  Final comments and format
 */
public class Account implements Serializable {

	/**
	 * ID needed for serialization
	 */
	private static final long serialVersionUID = 8875443145748393736L;

	/**
	 * History of all CashTransaction.
	 */
	private ArrayList<CashTransaction> cashHistory;

	/**
	 * History of all MarginTransaction.
	 */
	private ArrayList<MarginTransaction> marginHistory;

	/**
	 * Account maps one-to-one with an Investor owner
	 */
	private Investor owner;

	/**
	 * Value of cash in Investor Account
	 */
	private Double cashBalance;

	/**
	 * Margin amount borrowed by Investor from Brokerage Positive number
	 */
	private Double marginBorrowed;

	/**
	 * ============== Business Logic ========================= LSV =
	 * longStockValue (positive number) SSV = shortStockValue (positive number)
	 * margin = amount of marginBorrowed from broker (positive number) cash =
	 * cashBalance on Account (positive number) assets = LSV + cash liabilities
	 * = margin + SSV (thinking of SSV as a positive number) equity = assets -
	 * liabilities = LSV + cash - margin - SSV equity / assets > 2 AFTER any
	 * long BUY or short SALE asset > 2 liabilities after any long BUY or short
	 * SALE max leverage: 2 * (margin + SSV) = LSV + cash (percentEquity = 50%)
	 * max LSV purchase = -2 * (margin + SSV) + cash + LSV(before purchase) max
	 * SSV sale = 0.5 * (LSV + cash) - margin - SSV(before sale) percentEquity =
	 * equity / assets AFBL = cash + LSV - 2*(M+SSV) avail for buy long AFSS =
	 * 1/2 * (LSV+C)-M-SSV avail for short sale
	 */

	Double liabilities;
	Double availFundsToBuyLong;
	Double availFundsToSellShort;
	Double equity;
	Double assets;
	Double percentEquity;

	// SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm");

	/**
	 * Creates a new Account (cash and margin) for an Investor.
	 * 
	 * @param owner
	 *            maps to an Investor at time of Investor creation.
	 */
	public Account(Investor owner) {
		cashBalance = 0.;
		marginBorrowed = 0.;
		cashHistory = new ArrayList<CashTransaction>();
		marginHistory = new ArrayList<MarginTransaction>();
		this.owner = owner;

		/**
		 * Sum of margin funds borrowed, plus shortStockValue Positive number
		 * ShortStockValue() is a negative number
		 */
		liabilities = -owner.getPortfolio().ShortStockValue() + marginBorrowed;

		equity = cashBalance + owner.getPortfolio().LongStockValue()
				- liabilities;

		/**
		 * Investor's total assets in the Account: longStockValue + cashBalance
		 */
		assets = cashBalance + owner.getPortfolio().LongStockValue();

		/**
		 * Investor's percent equity on account: assets / equity
		 */
		percentEquity = equity / assets;
	}

	/**
	 * Deposit cash to account Deposit a negative amount to withdraw
	 * 
	 * @param amount
	 *            positive Double
	 * @param description
	 *            String to describe this transaction
	 * @return boolean result of operation: true for success
	 */
	public boolean depositCash(Double amount, String description) {
		boolean result = false;
		cashBalance = cashBalance + amount;
		CashTransaction ct = new CashTransaction(amount, description);
		addCashHistory(ct);
		result = true;
		return result;
	}

	/**
	 * Borrow margin on account (positive amount) Borrow a negative amount to
	 * pay back margin
	 * 
	 * @post percentEquity > 0.50 after transaction
	 * @param amount
	 *            positive Double
	 * @param description
	 *            String to describe this transaction
	 * @return boolean result of operation: true for success
	 */
	public boolean borrowMargin(Double amount, String description) {
		boolean result = false;
		marginBorrowed = marginBorrowed + amount;
		MarginTransaction mt = new MarginTransaction(amount, description);
		addMarginHistory(mt);
		result = true;
		return result;
	}

	/**
	 * Add a CashTransaction to the cashHistory.
	 * 
	 * @param transaction
	 *            CashTransaction
	 * @return boolean: true if success
	 */
	public boolean addCashHistory(CashTransaction transaction) {
		boolean result = false;
		cashHistory.add(transaction);
		result = true;
		return result;
	}

	/**
	 * Get Collection of all CashTransaction
	 * 
	 */
	public ArrayList<CashTransaction> getCashHistory() {
		return cashHistory;
	}

	/**
	 * Add a MarginTransaction to the marginHistory
	 * 
	 * @param transaction
	 *            MarginTransaction
	 * @return boolean: true if success
	 */
	public boolean addMarginHistory(MarginTransaction transaction) {
		boolean result = false;
		marginHistory.add(transaction);
		result = true;
		return result;
	}

	/**
	 * Get Collection of all CashTransaction
	 * 
	 */
	public ArrayList<MarginTransaction> getMarginHistory() {
		return marginHistory;
	}

	/**
	 * @return cashBalance
	 */
	public Double getCashBalance() {
		return ((int) (cashBalance * 100)) / 100D;
	}

	/**
	 * Get cumulative amount borrowed on margin. Does not include value of short
	 * stocks.
	 * 
	 * @return marginBorrowed
	 * @see Account#getMarginBalance()
	 */
	public Double getMarginBorrowed() {
		return ((int) (marginBorrowed * 100)) / 100D;
	}

	/**
	 * Get total marginBalance = marginBorrowed + shortStockValue
	 * 
	 * @return marginBalance
	 * @see Account#getMarginBorrowed()
	 */
	public Double getLiabilities() {
		return ((int) (liabilities * 100)) / 100D;
	}

	/**
	 * Get percentEquity = equity / assets for this Account and Portfolio
	 * 
	 * @return percentEquity
	 */
	public int getPercentEquity() {
		// return percentEquity;
		return (int) ((owner.getPortfolio().LongStockValue() + cashBalance - (marginBorrowed + owner
				.getPortfolio().ShortStockValue())) * 100D / (owner
				.getPortfolio().LongStockValue() + cashBalance));
	}

	/**
	 * Get availFundsToTrade prior to executing a Trade (calculated value)
	 * 
	 * @return availFundsToTrade
	 */
	public Double getAvailFundsToBuyLong() {
		Double afbl = -2
				* (marginBorrowed + owner.getPortfolio().ShortStockValue())
				+ 1.5 * (cashBalance + owner.getPortfolio().LongStockValue());
		return ((int) (afbl * 100)) / 100D;
	}

	public Double getAvailFundsToSellShort() {
		Double afss = 0.5
				* (owner.getPortfolio().LongStockValue() + cashBalance)
				- (marginBorrowed - owner.getPortfolio().ShortStockValue());
		return ((int) (afss * 100)) / 100D;

	}

	public Investor getOwner() {
		return owner;
	}
}
