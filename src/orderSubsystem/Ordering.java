package orderSubsystem;

import java.io.Serializable;
import java.util.Date;
import brokerageControlSubsystem.BrokerageControl;
import usersSubsystem.*;
import messageSubsystem.*;

/**
 * Enters and executes Trades.  Send all new Trade orders to
 * orderSubsystem.Ordering.enterOrder(...).  Instantiated once when system is
 * launched. Enter new Trades as needed. Array of pending trades maintained
 * Periodically iterates through array of pending Trades to look for executable
 * limit orders. Limit orders expire at midnight.
 * 
 * @author  David Brodeur
 * @version 201211200001
 * @version 201211211224 DB
 * @version 201211232139 DB
 * @version 20121129 Final comments and clean-up
 * 
 */
public class Ordering implements Serializable {

	/**
	 * ID needed for serialization
	 */
	private static final long serialVersionUID = 4638121042875687423L;

	/**
	 * If "online", use MarketData subsystem
	 */
	static boolean online = true;

	/**
	 * Default constructor
	 */
	public Ordering() {
	}

	/**
	 * create a new Trade order
	 * @param investor
	 * @param symbol  stock ticker
	 * @param quantity number of shares to be traded
	 * @param limitPrice if needed for a limit order
	 * @param buyOrder true if a buy order; false if a sell
	 * @param limitOrder true if limit order
	 * @param brokerAssist True of broker-assisted
	 * @return
	 */
	public String enterOrder(Investor investor, String symbol, int quantity,
			Double limitPrice, boolean buyOrder, boolean limitOrder,
			boolean brokerAssist) {
		Trade t = new Trade(investor, symbol, quantity, limitPrice, buyOrder,
				limitOrder, brokerAssist);
		Double currentPrice = 999999D;
		currentPrice = BrokerageControl.getMarketSys().getStockPrice(
				t.getSymbol());
		// Driver.prn("LivePrice(enterOrder): "+t.getQuantity()+
		// " shares, "+t.getSymbol()+"="+currentPrice);
		if (!validateOrder(t, currentPrice)) {
			t.setPending(false);
			t.cancelTrade(t);
			// Driver.tr(t.getInvestor(),
			// "Order not valid due to insufficient funds / over margin-limit.");
			return "Order not valid due to insufficient funds / over margin-limit.";
		} // end if validate
		else if (brokerAssist) {
			sendBrokerOrder(t);
			t.cancelTrade(t);
			// Driver.tr(t.getInvestor(), "Broker-assist message sent");
			return "Order sent to Broker.";
		} // end if broker assist
		else if ((t.getBuyOrder() & currentPrice <= t.getLimitPrice())
				|| !t.getBuyOrder()) { // buy in-limit, or sell
			completeOrder(t);

			for (int i = 0; i < investor.getPortfolio().getPositions().size(); i++) {
				// Driver.prn("Clear Empties");
				// Driver.prn("position " +
				// investor.getPortfolio().getPositions().get(i).getSymbol() +
				// " = " +
				// investor.getPortfolio().getPositions().get(i).getQuantity() +
				// " shares");
				if (investor.getPortfolio().getPositions().get(i).getQuantity() == 0) {
					// Driver.prn("Remove" +
					// investor.getPortfolio().getPositions().get(i).getSymbol());
					investor.getPortfolio().getPositions().remove(i);
				}
			} // clear empty positions

			return "Order completed at MARKET price.";
		} else {
			t.setPending(true);
			BrokerageControl.pendingTrades.add(t);
			t.getInvestor().getPortfolio().addTradeHistory(t);
			// Driver.prn("Limit order pended: " + t.getSymbol()+ " current:"+
			// currentPrice+ " limitPrice:" + t.getLimitPrice());
			// Driver.tr(t.getInvestor(), "Limit-order pended.");
			return "Order pended for later execution.";
		}
	} // end function

	/**
	 * Complete a trade order, including accounting and portfolio update
	 * @param t Trade order
	 */
	public void completeOrder(Trade t) {
		// MarketData data = new MarketData();
		Double price = BrokerageControl.getMarketSys().getStockPrice(
				t.getSymbol());
		t.executeTrade(t, price); // includes update to TradeHistory
		t.setPending(false);
		t.getInvestor().getPortfolio().addTradeHistory(t);
		t.getInvestor().getPortfolio().updatePositions(t);
		t.getInvestor().getAccount().depositCash(-9.99, "Order fee");
		if (t.getBuyOrder()) { // settle a BUY order
			if (t.getInvestor().getAccount().getCashBalance() > t
					.getExecuteAmount()) {
				t.getInvestor()
						.getAccount()
						.depositCash(
								(-t.getExecuteAmount()),
								"Stock Buy: " + t.getQuantity() + " "
										+ t.getSymbol());
			} else { // not enough cash to cover; borrow margin
				t.getInvestor()
						.getAccount()
						.borrowMargin(
								-(t.getInvestor().getAccount().getCashBalance() - t
										.getExecuteAmount()),
								"Stock Buy: " + t.getQuantity() + " "
										+ t.getSymbol());
				t.getInvestor()
						.getAccount()
						.depositCash(
								(-t.getInvestor().getAccount().getCashBalance()),
								"Stock Buy: " + t.getQuantity() + " "
										+ t.getSymbol());
			} // end else
		} else { // settle a SELL order
			if (t.getInvestor().getAccount().getMarginBorrowed() > 0) {
				if (t.getExecuteAmount() > t.getInvestor().getAccount()
						.getMarginBorrowed()) {
					t.getInvestor()
							.getAccount()
							.borrowMargin(
									t.getInvestor().getAccount()
											.getMarginBorrowed(),
									"Margin paid with sale proceeds"); // pay
																		// off
																		// margin
																		// and
																		// put
																		// remainder
																		// in
																		// cash;
					t.getInvestor()
							.getAccount()
							.depositCash(
									(t.getExecuteAmount() - t.getInvestor()
											.getAccount().getMarginBorrowed()),
									"Margin paid/Cash deposited: Sale");
				} else
					t.getInvestor()
							.getAccount()
							.borrowMargin(-t.getExecuteAmount(),
									"Margin paid with sale proceeds"); // pay
																		// down
																		// margin
			} else
				t.getInvestor()
						.getAccount()
						.depositCash(
								(t.getExecuteAmount()),
								"Stock Sale: " + t.getQuantity() + " "
										+ t.getSymbol()); // add proceeds to
															// cash balance
		}
		for (int i = 0; i < t.getInvestor().getPortfolio().getPositions().size(); i++) {
			// Driver.prn("Clear Empties");
			if (t.getInvestor().getPortfolio().getPositions().get(i).getQuantity() == 0) {
				// Driver.prn("Empty position to be removed: " +
				// t.getInvestor().portfolio.getPositions().get(i).getSymbol());
				t.getInvestor().getPortfolio().getPositions().remove(i);

			}
		}
		// Driver.tr(t.getInvestor(), "Trade: Order completed");

	}

	/**
	 * Canceled a timed-out Trade: remove from pendingTrades and add to
	 * TradeHistory
	 * @param t Trade from pendingTrades queue
	 */
	public void sendBrokerOrder(Trade t) {
		String buySell = "";
		String limitMkt = "";
		if (t.getBuyOrder())
			buySell = " BUY ";
		else
			buySell = " SELL ";
		if (t.getLimitOrder())
			limitMkt = " LIMIT ";
		else
			limitMkt = " MARKET ";
		t.getInvestor().getAccount().depositCash(-4.99, "Broker-assist fee");
		String body = "Broker-assisted Trade Request: \n" + "For Investor "
				+ t.getInvestor().getLname() + " " + t.getInvestor().getID()
				+ ", " + buySell + limitMkt + t.getQuantity() + " shares of "
				+ t.getSymbol() + ", LimitPrice:" + t.getLimitPrice() + "\n";
		Message message = new Message(t.getInvestor().getBroker(),
				t.getInvestor(), "Broker-Assisted Trade-Request", body);
		t.getInvestor().getMessageAccount(t.getInvestor().getBroker())
				.sendMessage(message);
		// Driver.prn("Broker-assisted msg sent: " + body);
		// pendingTrades.remove(t);
	}

	public void iteratePendingOrders() {
		Date dateNow = new Date(System.currentTimeMillis());
		for (Trade t : BrokerageControl.pendingTrades) {
			if (t.getPending() == false)
				continue;
			Double currentPrice = 999999D;
			currentPrice = BrokerageControl.getMarketSys().getStockPrice(
					t.getSymbol());
			// Driver.prn("Iteration pendingTrades: " + t.getSymbol()+
			// " current:"+ currentPrice+ " limitPrice:" + t.getLimitPrice() +
			// " orderDate:" + t.getOrderDate());
			if (!validateOrder(t, currentPrice)) {
				t.setPending(false);
				t.cancelTrade(t); // insufficient funds or margin over limit
				// Driver.prn("Pending order failed funds validation: "
				// +t.getQuantity()+ t.getSymbol());
				continue; // end the current iteration
			} else if ((dateNow.getTime() - t.getOrderDateMs().getTime()) > (8 * 60 * 60 * 1000L)) { // if
																										// expired,
																										// 8hrs=8*60*60*1000L;
																										// 1
																										// min
																										// =
																										// 60000L
				t.setPending(false);
				t.cancelTrade(t); // remains on Investor's tradeHistory
				// Driver.prn("Pending order expired: " +t.getQuantity()+
				// t.getSymbol());
				continue; // end the current iteration
			} else {
				if (currentPrice <= t.getLimitPrice()) {
					// pendingTrades.remove(t);
					t.setPending(false);
					// Driver.prn("Executing a pending trade:" +t.getQuantity()+
					// t.getSymbol());
					t.executeTrade(t, currentPrice);
					// Driver.tr(t.getInvestor(),
					// "Pended Trade went through: Order completed");
					continue;
				} // end if
			} // end else
		} // end for-each
	} // function iterate pending orders

	/**
	 * Validate an order: Check for adequate cash and margin reserve
	 * @param t Trade
	 * @return true if Investor has adequate funds for purchase
	 */
	public boolean validateOrder(Trade t, Double currentPrice) {
		boolean result = true;
		// Driver.prn("LivePrice(validate): "+t.getSymbol()+"="+currentPrice);
		if (t.getBuyOrder()) { // if BUY order
			if (!(t.getQuantity() * currentPrice < t.getInvestor().getAccount()
					.getAvailFundsToBuyLong())) {
				result = false;
			}
		}
		return result;
	}
} // end class
