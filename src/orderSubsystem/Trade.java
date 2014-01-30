package orderSubsystem;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.Serializable;
import usersSubsystem.*;

/**
 * Stock Trade object.
 * Every Trade needs to be eventually executed or canceled.
 * Will be added to TradeHistory, either way.
 * 
 * @author  David Brodeur
 * @version 201211200001
 * @version 20121129  Final comments and format
 */
public class Trade implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8462288522210206242L;

	/**
	 * Boolean indicator of whether this is to  be a broker-assisted Trade.
	 * TRUE = Broker-assisted trade
	 */
	private boolean brokerAssist;
	
	/**
	 * limitOrder == TRUE is a LIMIT order
	 * limitOrder == FALSE is a MARKET order
	 */
	private boolean limitOrder;
	
	/**
	 * buyOrder == TRUE is a BUY order
	 * buyOrder == FALSE is a SELL order
	 */
	private boolean buyOrder;
	
	/**
	 * pending == TRUE if still pending execution 
	 * (needs to be gathered into pendingTrades array at start-up)
	 * pending == FALSE if executed or canceled
	 */
	private boolean pending;
	
	/**
	 * Investor (object) to whose Account and Portfolio this Trade pertains
	 */
	private Investor investor;
	
	/**
	 * Number of shares to trade.
	 * Number of shares (quantity) always remains POSITIVE
	 */
	private int quantity;
	
	/**
	 * Stock ticker symbol.
	 * Capital letters.
	 */
	private String symbol;
	
	SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
	
	/**
	 * Date & Time stamped when Trade request was created
	 */
	private Date orderDate;
	
	/**
	 * applies if tradeType == LIMIT; 
	 * set to 999999D by constructor if this is a MARKET order
	 */
	private Double limitPrice;
	
	/**
	 * Date / Time when Trade was executed, if at all.
	 * Unexecuted Trade is marked with executeDate = 31-Dec-1969 19:00
	 */
	private Date executeDate;
	
	/**
	 * Price at which Trade was executed, it at all
	 * Unexectuted Trade is marked with executePrice = 0D
	 */
	private Double executePrice;
	
	/**
	 * Always positive: executePrice * quantity
	 */
	private Double executeAmount;
        
    private boolean canceled;
	
	/**
	 * new Trade constructor, invoked by orderSubsystem.Ordering.enterOrder()
	 * @param investor Investor(object) for this Trade (Account and Portfolio)
	 * @param symbol Stock ticker (1 to 5 capital letters)
	 * @param quantity Number of shares to transact (always positive)
	 * @param limitPrice Double: Pertains to LIMIT order.
	 * 		  set to 999999D by constructor if a MARKET order
	 * @param buyOrder TRUE if a BUY; FALSE if a SELL
	 * @param limitOrder TRUE if a LIMIT order; FALSE if a MARKET order
	 * @param brokerAssist TRUE if a Broker-assisted transaction
	 */
	public Trade(Investor investor, String symbol, int quantity, Double limitPrice, 
			boolean buyOrder, boolean limitOrder, boolean brokerAssist) {
		symbol.toUpperCase();
		Pattern p = Pattern.compile("[A-Z]{1,4}[\\.]?[A-Z]?"); 
		// valid ticker symbol is 1-4 capital letters, optional period, optional one letter
		Matcher m = p.matcher(symbol);
		if (!m.matches()) throw new RuntimeException("Invalid Ticker Symbol"); // one-time check of symbol
		this.investor = investor;
		this.orderDate = new Date(System.currentTimeMillis());
		this.symbol = symbol;
		this.quantity = quantity;
		this.limitOrder = limitOrder;
		if (limitOrder) this.limitPrice = limitPrice;
		else this.limitPrice = 999999D;
		this.buyOrder = buyOrder;
		if (this.buyOrder == false) limitOrder = false;  // limit order only applies to BUY orders
		this.brokerAssist = brokerAssist;
		this.pending = true;
		this.executeDate = new Date(1L);
		this.executePrice = 0D;
		this.executeAmount = 0D;
                this.canceled = false;
		
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public Double getExecutePrice() {
		return ((int)(executePrice*100))/100D;
	}
	
	public String getOrderDate() {
		return df.format(orderDate);
	}
	
	public Date getOrderDateMs() {
		return orderDate;
	}
	
	public String getExecuteDate() {
		return df.format(executeDate);
	}
	
	public Double getExecuteAmount() {
		if (executeAmount == null) {
                    return 0D;
                }
		else {
                    return ((int)(executeAmount*100))/100D;
                }
	}
	
	public Double getLimitPrice() {
		Double result = 0D;
		if (limitPrice != null) {
			result = limitPrice;
		}
		return result;
	}
	
	public Investor getInvestor() {
		return investor;
	}
	
	public boolean getLimitOrder() {
		return limitOrder;
	}
	
	public boolean getBuyOrder() {
		return buyOrder;
	}
	
	public boolean getBrokerAssist() {
		return brokerAssist;
	}
	
	public boolean getPending() {
		return pending;
	}
	
	public void setPending(boolean b) {
		pending = b;
	}
	
	/**
	 * Finalize a Trade when it completes.
	 * Date stamp when completed.
	 * Add completed Trade to TradeHistory
	 * If Trade expires unfulfilled (canceled or timed-out), use cancelTrade(Trade)
	 * @param t Trade to be finalized
	 * @param price executePrice of the Trade, needs to come from the MarketData System
	 */
	public void executeTrade(Trade t, Double price) {
		//System.out.println("enter ExecuteTrade("+t.getSymbol()+")");
		t.executeDate = new Date(System.currentTimeMillis());
		t.executePrice = price; // price needs to come from MarketData System
		t.executeAmount = t.quantity*t.getExecutePrice();
		t.setPending(false);
		//System.out.println("Finished executing: " + t.getSymbol());
	}
	
	/**
	 * Cancel an unfulfilled Trade that is not longer needed or that times out.
	 * Date stamp when canceled.
	 * Add canceled Trade to TradeHistory, but with zeros in executePrice, executeAmount
	 * @param t Trade to be canceled
	 */
	protected void cancelTrade(Trade t) {
		executeDate = new Date(System.currentTimeMillis());
		executePrice = 0D; // price needs to come from MarketData System
		t.executeAmount = 0D;
		t.setPending(false);
                t.canceled = true;
		t.investor.getPortfolio().addTradeHistory(t);
		//Driver.tr(t.getInvestor(), "Canceled Trade.");
	}

	@Override
	public String toString() {
		String buysell = "SELL";
		String limitmkt = "MARKET";
		String bkrasst = "";
		String cancel = "";
		String limitprice = "";
		String pend = "";

		if (buyOrder) {
			buysell = "BUY";
		}

		if (limitOrder) {
			limitmkt = "LIMIT";
			limitprice = " Limit Price: "
					+ Double.toString(this.getLimitPrice());
		}

		if (brokerAssist) {
			bkrasst = " BKR ASSIST";
		}

		if (pending) {
			pend = " PENDING";
		}

		if (canceled) {
			cancel = " CANCELLED";
		}

		return "[" + symbol + "] " + buysell + " " + limitmkt + " "
				+ Integer.toString(quantity) + " shares " + limitprice
				+ bkrasst + " Executed: " + this.getExecuteDate() + " Price: "
				+ Double.toString(this.getExecutePrice()) + " Total Amt: "
				+ Double.toString(this.getExecuteAmount()) + pend + cancel;
	}
}


