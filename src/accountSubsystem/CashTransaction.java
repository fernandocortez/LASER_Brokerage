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
 * 
 */
public class CashTransaction implements Serializable {

	/**
	 * ID needed for serialization
	 */
	private static final long serialVersionUID = -5408568798170156509L;

	/**
	 * Transaction Date and Time stamp from system.
	 */
	Date date;
	// Use DateFormat for output: DateFormat.getInstance().format(dateInstance)

	/**
	 * Cash deposit to Account. Use a negative number for withdrawal.
	 */
	Double depositAmount;

	/**
	 * String description of CashTransaction
	 */
	String description;

	SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm");

	/**
	 * Creates a CashTransaction, including Date stamp.
	 * 
	 * @pre Use a negative depositAmount for a cash withdrawal
	 * @post Stamped with date & time at time of transaction
	 * @param depositAmount
	 * @param description
	 */
	public CashTransaction(Double depositAmount, String description) {
		date = new Date(System.currentTimeMillis());
		this.depositAmount = depositAmount;
		this.description = description;
	}

	public String getDate() {
		return df.format(date);
	}

	public Double getAmount() {
		return ((int) (depositAmount * 100)) / 100D;
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
