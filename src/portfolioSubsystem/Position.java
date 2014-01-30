package portfolioSubsystem;

import java.io.Serializable;
import brokerageControlSubsystem.*;

/**
 * Position object is an element of the List of all stock positions
 * in the Investor's Portfolio. 
 * 
 * @author  David Brodeur
 * @version 201211200001
 * @version 20121129 Final comment and clean-up
 * 
 */
public class Position implements Serializable {

	/**
	 * ID needed for serialization
	 */
	private static final long serialVersionUID = 6744682911657441904L;

	static boolean online = true;

	/**
	 * Stock ticker (CAPITAL letters) Set in the constructor and never changes
	 * thereafter Use as primary key for this position
	 */
	final String symbol;

	/**
	 * Number of shares. Negative quantity indicates a short position.
	 */
	int quantity;

	/**
	 * Last market quote obtained for share price.
	 */
	Double lastQuote;

	/**
	 * quantity * lastQuote
	 */
	// Double marketValue;

	/**
	 * Cumulative cost of position (basis) to Investor
	 */
	Double cost;

	/**
	 * marketValue - cost
	 */
	// Double gain;

	/**
	 * Creates a position for a new stock holding. To be updated whenever a
	 * Trade involving this stock is executed.
	 * 
	 * @pre This stock ticker symbol is not currently in Portfolio.positions
	 * @param symbol
	 *            Stock ticker (CAPITAL letters)
	 * @param quantity
	 *            Number of shares (negative quantity is a short position)
	 * @param cost
	 *            Cumulative cost of position (basis) to Investor
	 * @see Portfolio#modifyPositions
	 */
	public Position(String symbol, int quantity, Double cost) {
		this.symbol = symbol;
		this.quantity = quantity;
		this.cost = cost;
		if (online) {
			// MarketData data = new MarketData();
			this.lastQuote = 0.; // in case no quote available at construction
			this.lastQuote = BrokerageControl.getMarketSys().getStockPrice(
					symbol); // trap error if no quote
		} else {
			this.lastQuote = 14D;
		}
		// this.marketValue = this.lastQuote*this.quantity;
		// this.gain = this.marketValue - this.cost;
		// marketValue = quantity * lastQuote;
		// gain = marketValue - cost;
	}

	public Double getGain() {
		return ((int) (((quantity * lastQuote) - cost) * 100)) / 100D;
	}

	public Double getMarketValue() {
		return ((int) (quantity * lastQuote * 100)) / 100D;
	}

	/**
	 * @return immutable stock Symbol for this position
	 */
	public String getSymbol() {
		return symbol;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int newQuant) {
		quantity = newQuant;
	}

	public Double getCost() {
		return ((int) (cost * 100)) / 100D;
	}

	public void setCost(double newCost) {
		cost = ((int) (newCost * 100)) / 100D;
	}

	public Double getLastQuote() {
		return ((int) (lastQuote * 100)) / 100D;
	}

	@Override
	public String toString() {
		return "[" + symbol + "] "
				+ BrokerageControl.getMarketSys().getStockName(symbol) + " | "
				+ Integer.toString(quantity) + " shares @ "
				+ Double.toString(this.getLastQuote()) + " MktV: "
				+ Double.toString(this.getMarketValue()) + " Cost: "
				+ Double.toString(this.getCost()) + " Gain: "
				+ Double.toString(this.getGain());
	}
}
