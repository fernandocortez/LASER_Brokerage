package portfolioSubsystem;

import java.io.Serializable;
import java.util.ArrayList;
import orderSubsystem.Trade;
import usersSubsystem.*;
import brokerageControlSubsystem.*;


/**
 * The record of all Trades, and report of current stock Positions.
 * Negative positions are "short".
 * 
 * @author  David Brodeur
 * @version 201211200001
 * @version 201211211706 DB
 * @version 20121129  Final comments and clean-up
 *
 */
public class Portfolio implements Serializable {
	
	/**
	 * Version ID for a Serializable class
	 */
	private static final long serialVersionUID = 3916496373270829321L;
	
	/**
	 * Record of all current stock positions.
	 * A negative position (quantity of shares) is "short". 
	 */
	private ArrayList<Position> positions;
	
	/**
	 * Record of all executed Trades.
	 */
	private ArrayList<Trade> tradeHistory;
	
	/**
	 * Portfolio maps one-to-one with an Investor 
	 * Portfolio owner maps to an Investor at time of Investor creation.
	 */
	private Investor owner;
	
	/**
	 * Creates a new Portfolio for an Investor.
	 * @param owner Portfolio owner maps to an Investor at time of Investor creation.
	 */
	public Portfolio(Investor owner) {
		this.owner = owner;
		this.positions = new ArrayList<Position>();
		this.tradeHistory = new ArrayList<Trade>();
	}
	
	/**
	 * Long positions have a positive quantity (number of shares)
	 * @return Sum of all long stock positions (a positive number)
	 */
	public Double LongStockValue() {
		Double value = 0.;
		updatePrices();
		for (Position p : positions) {
			//Driver.prn("LSVcalc: ticker="+p.symbol+" lastQuote="+p.lastQuote);
			if (p.quantity>0) value = value + (p.quantity * p.lastQuote);
		}
		return ((int)(value*100))/100D;
	}
	
	/**
	 * Short positions have a negative quantity (number of shares)
	 * @return Sum of all short stock positions (a positive number)
	 */
	public Double ShortStockValue() {
		Double value = 0.;
		updatePrices();
		for (Position p : positions) {
			if (p.quantity<0) value = value + (-1 * p.quantity * p.lastQuote);
		}
		return ((int)(value*100))/100D;
	}
	
	/**
	 * Modify the list of current positions to reflect the results of a new Trade.
	 * Create a new position if that ticker symbol isn't already in the Portfolio.
	 * @param trade Newly-executed stock Trade
	 * @return TRUE if successful update
	 */
	public String updatePositions(Trade trade) {
		String result = "Positions in portfolio not changed.";
		int prior = 0;
		int mult; if (trade.getBuyOrder()) mult = 1; else mult = -1; 
		// BUY mult = 1; SELL mult = -1
		boolean exists = false;
		if (trade.getExecuteAmount() == 0D) return result;
		else {
			for (Position p : positions) {
				//Driver.prn("p.symbol:" + p.symbol + ", 
				// trade.getSymbol():" + trade.getSymbol() );
				if (p.symbol.equals(trade.getSymbol())) {
					exists = true;
					prior = positions.indexOf(p);
				} // end determine if ticker is already in a position
			} // end for each symbol in existing portfolio positions
			if (!exists) {
				positions.add(new Position(trade.getSymbol(), mult*trade.getQuantity(), 
						(mult*trade.getQuantity()*trade.getExecutePrice())));
				result = trade.getSymbol() + " new position added.";
				//Driver.prn( trade.getSymbol() + " new position added." );
				return result;
			} 
			else {  // update existing position
			Double transAmt= mult*trade.getQuantity() * trade.getExecutePrice();
			positions.get(prior).quantity = positions.get(prior).quantity
					+ (mult*trade.getQuantity());
			positions.get(prior).cost = positions.get(prior).cost + transAmt;
			result = positions.get(prior).symbol + " existing position updated.";
			//Driver.prn(positions.get(prior).symbol + " existing position updated.");
			return result;
			}
		} // end else
		
		
	} // end method
	
	/**
	 * @return List of all stock positions, long and short
	 */
	public ArrayList<Position> getPositions() {
		updatePrices();
		return positions;
	}
	
	/**
	 * Add a new Trade to the history
	 * @param trade recently-executed stock Trade to be added to history
	 * @return TRUE if successful
	 */
	public String addTradeHistory(Trade trade) {
		tradeHistory.add(trade);
		return "Trade history updated.";
	}
	
	/**
	 * @return History of all executed stock Trades
	 */
	public ArrayList<Trade> getTradeHistory() {
		return tradeHistory;
	}
	
	/**
	 * @return boolean result of attempt to update all prices in Portfolio
	 */
	public String updatePrices() {
		String result = "Portfolio prices not updated.";
			for (Position p : positions) {
				p.lastQuote = BrokerageControl.getMarketSys().getStockPrice(p.symbol);
			result = "Portfolio prices updated.";
			}
		return result;  // depends on error checking in MarketData API interface
	}
	
	/**
	 * @return immutable Investor who owns this Portfolio
	 */
	public Investor getOwner() {
		return owner;
	}
	
}
