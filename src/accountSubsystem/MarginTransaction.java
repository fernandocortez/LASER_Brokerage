package accountSubsystem;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.Serializable;

/**
 * Transaction object for Account
 * 
 * @author David Brodeur
 * @version 201211200001
 * @version 20121129 Final comments and clean-up
 */
public class MarginTransaction implements Serializable {

	/**
	 * ID needed for serialization
	 */
	private static final long serialVersionUID = 4845949326143886858L;

	/**
	 * Transaction Date and Time.
	 */
	Date date;
	// Use DateFormat for output: DateFormat.getInstance().format(dateInstance)

	/**
	 * Margin borrowed on Account.
	 */
	Double borrowedAmount;

	/**
	 * String description of CashTransaction
	 */
	String description;

	SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm");

	/**
	 * Creates a MarginTransaction, including Date stamp.
	 * 
	 * @pre Use a negative borrowedAmount for a margin payback
	 * @post Stamped with date & time at time of transaction
	 * @param depositAmount
	 * @param description
	 */
	public MarginTransaction(Double borrowedAmount, String description) {
		date = new Date(System.currentTimeMillis());
		this.description = description;
		this.borrowedAmount = borrowedAmount;
	}

	public String getDate() {
		return df.format(date);
	}

	public Double getAmount() {
		return ((int) (borrowedAmount * 100)) / 100D;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return Double.toString(this.getAmount()) + " " + this.getDate() + " "
				+ description;
	}
}
