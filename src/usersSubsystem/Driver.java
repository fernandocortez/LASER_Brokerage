package usersSubsystem;

import brokerageControlSubsystem.BrokerageControl;
import portfolioSubsystem.*;
import orderSubsystem.Trade;
import accountSubsystem.*;

/**
 * Test Driver for constructors and transactions.
 * Used temporarily in BC, Portfolio, and Ordering classes.
 * Set Investor.account and Investor.portfolio temporarily _public_ for
 * 		compatibility with Driver methods.
 * Set online = TRUE if live MarketData available.
 * 
 * @author  David Brodeur
 * @version 201211210130
 * @version DB 20121121215
 * 
 */
public class Driver {

	static boolean online = true;
	
//  Generate a new Investor	
//	public static void main(String[] args) {
//		Broker newBroker = new Broker(45L, "Luke", "Skywalker", "pwd");
//		//Broker newBroker = (Broker)BrokerageControl.getUserList().get(1);
//                Investor newbie =new Investor(46L, "Ima", "Newbie","pwd", newBroker, 10000D);
//		prn("Constructed new Investor: "+newbie.getLname());
//		//newbie.account.borrowMargin(2000D, "Dummy margin transaction");
//
//
//		dch(newbie);
//		dmh(newbie);
//		dth(newbie);
//		dip(newbie);
//		dia(newbie);
//		daf(newbie);
//		Ordering os = new Ordering();
//		
//		//======================== construct a Trade =======================
//		String ticker = "GE"; // CAPITAL letters
//		int shares =725;
//		double limitPrice = 9999D; // only applicable if limit == TRUE
//		boolean buy = true;     // set to FALSE for SELL
//		boolean limit = false;	// set to TRUE for LIMIT order
//		boolean brokerAssisted = false;
//		//==================================================================
//		
//		prn("enterOrder" + ticker);
//		os.enterOrder(newbie, ticker, shares, limitPrice, buy, limit, brokerAssisted);
//		
//		dch(newbie);
//		dmh(newbie);
//		dth(newbie);
//		dip(newbie);
//		dia(newbie);
//		daf(newbie);
//		
//		//======================== construct a Trade =======================
//		ticker = "GE"; // CAPITAL letters
//		shares = 30;
//		limitPrice = 9999D; // only applicable if limit == TRUE
//		buy = false;     // set to FALSE for SELL
//		limit = false;	// set to TRUE for LIMIT order
//		brokerAssisted = false;
//		//==================================================================
//		
//		prn("sell order" + ticker);
//		os.enterOrder(newbie, ticker, shares, limitPrice, buy, limit, brokerAssisted);
//		
//
//		
//		dch(newbie);
//		dmh(newbie);
//		dth(newbie);
//		dip(newbie);
//		dia(newbie);
//		daf(newbie);
//		prn("End Driver");
//		
//	} // end main
	
	public static void tr(Investor i, String event) {
		prn(event);
		prn("Trade Report:");
		dth(i);
		dip(i);
		dch(i);
		dmh(i);
		dia(i);
		daf(i);
		dpt();
		prn("--end trade report---");
	}
	
	/**
	 * Display Investor Portfolio
	 */
	public static void dip(Investor i) {
		prn("PortfolioPositions(" + i.getLname() + "): " );
		for (Position p : i.getPortfolio().getPositions()) {
			prn("  -- "+p.getQuantity()+p.getSymbol()+p.getLastQuote()+" MktV:"+p.getMarketValue()+" Cost:"+p.getCost()+" Gain:"+p.getGain());
		}
	}
	
	/**
	 * Display Investor Account Balances
	 * @param i
	 */
	public static void dia(Investor i) {
		prn("AccountBalances(" + i.getLname() + "): CashBalance: "+i.getAccount().getCashBalance()+", MarginBalance: "+i.getAccount().getMarginBorrowed()+", LSV: "+ i.getPortfolio().LongStockValue()+", SSV: "+i.getPortfolio().ShortStockValue() + ", %eq:"+i.getAccount().getPercentEquity()  );
		
		//prn("Account: " + i.account.getCashBalance()());
	}
	
	/**
	 * Utility static println method
	 * @param s String
	 */
	public static void prn(String s) {
		System.out.println(s);
	}
	
	/**
	 * Display a Trade
	 * @param t Trade
	 */
	public static void dt(Trade t) {
		prn("Trade Order: "+t.getInvestor().getLname()+" "+t.getSymbol()+" "+t.getQuantity()+" "+t.getLimitPrice()+" "+t.getOrderDate()+" buy:"+t.getBuyOrder()+" limit:"+t.getLimitOrder()+" broker:"+t.getBrokerAssist());
		prn("   -- Executed?: "+t.getExecuteDate()+" price:"+t.getExecutePrice()+" total_amt:"+t.getExecuteAmount());
	}

	/**
	 * Display Trade History
	 * @param i Investor
	 */
	public static void dth(Investor i) {
		prn("Trade History(" + i.getLname()+"):" );
		for (Trade t : i.getPortfolio().getTradeHistory() ) {
		prn("  --: "+t.getInvestor().getLname()+" "+t.getSymbol()+" "+t.getQuantity()+" "+t.getLimitPrice()+" "+t.getOrderDate()+" buy:"+t.getBuyOrder()+" limit:"+t.getLimitOrder()+" broker:"+t.getBrokerAssist());
		prn("     Executed: "+t.getExecuteDate()+" price:"+t.getExecutePrice()+" total_amt:"+t.getExecuteAmount() + " Pending:" + t.getPending());
		}
	}
	
	/**
	 * Display Cash History
	 * @param i Investor
	 */
	public static void dch(Investor i) {
		prn("CashHistory(" + i.getLname() + "): ");
		for (CashTransaction ct : i.getAccount().getCashHistory()) {
			prn("  -- "+ct.getDate()+" "+ct.getAmount()+" "+ct.getDescription());
		}
	}
	
	/**
	 * Display Margin History
	 * @param i Investor
	 */
	public static void dmh(Investor i) {
		prn("MarginHistory(" + i.getLname() + "): ");
		for (MarginTransaction mt : i.getAccount().getMarginHistory()) {
			prn("  -- "+mt.getDate()+" "+mt.getAmount()+" "+mt.getDescription());
		}
	}
	
	/**
	 * Display pendingTrades queue for this Ordering system
	 * @param os  Ordering instance
	 */
	public static void dpt() {
		prn("PendingTrades:");
		for (Trade t : BrokerageControl.pendingTrades) {
			prn("  --: "+t.getInvestor().getLname()+" "+t.getSymbol()+" "+t.getQuantity()+" "+t.getLimitPrice()+" "+t.getOrderDate()+" buy:"+t.getBuyOrder()+" limit:"+t.getLimitOrder()+" broker:"+t.getBrokerAssist() + " pending:" + t.getPending());
			prn("   -- Executed?: "+t.getExecuteDate()+" price:"+t.getExecutePrice()+" total_amt:"+t.getExecuteAmount());
		}
	}
	
	
	/**
	 * Display available funds for trading
	 * @param i Investor
	 */
	public static void daf(Investor i) {
		prn("AvailableFunds:  AFBL=" + i.getAccount().getAvailFundsToBuyLong()+ " AFSS="+ i.getAccount().getAvailFundsToSellShort());
	}
}
