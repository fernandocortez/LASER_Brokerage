package brokerageControlSubsystem;

import usersSubsystem.*;

/**
 * 
 *
 * @author Thomas Savage
 * @version 201211271820
 */
public final class PeriodicFunctionsThread implements Runnable {
    
    /**
     * The ask value of DIA .
     */
    private String diaAsk = "";
    
    /**
     * The daily change value of DIA. 
     */
    private String diaChange = "";
    
    /**
     * The daily percent change value of DIA. 
     */
    private String diaPercent = "";
    
    /**
     * The ask value of NASDAQ. 
     */
    private String nasdaqAsk = "";
    
    /**
     * The daily change value of NASDAQ. 
     */
    private String nasdaqChange = "";
    
    /**
     * The daily percent change value of NASDAQ. 
     */
    private String nasdaqPercent = "";
    
    /**
     * The ask value of the S&P500. 
     */
    private String spAsk = "";
    
    /**
     * The daily change value of the S&P500.
     */
    private String spChange = "";
    
    /**
     * The daily percent change value of the S&P500.
     */
    private String spPercent = "";
    
    /**
     * The ask value of the ten-year T-note.
     */
    private String bondAsk = "";
    
    /**
     * The daily change value of the ten-year T-note.
     */
    private String bondChange = "";
    
    /**
     * The daily percent change value of the ten-year T-note.
     */
    private String bondPercent = "";
    
    /**
     * The full company name of the requested stock.
     */
    private String lookupName = "";
    
    /**
     * The ask value of the requested stock.
     */
    private String lookupAsk = "";
    
    /**
     * The daily change value of the requested stock.
     */
    private String lookupChange = "";
    
    /**
     * The daily percent change value of the requested stock. 
     */
    private String lookupPercent = "";

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     * 
     * The main thread loop.
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                bgProc();
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    /**
     * This method handles the various background tasks performed by the
     * PeriodicFunctionsThread. It updates fields on the Market Research screen
     * and iterates through pending orders.
     * 
     * @throws InterruptedException
     */
    public void bgProc() throws InterruptedException {
        diaAsk = BrokerageControl.getMarketSys().updateMarketIndexData("DIA", "l1");
        diaChange = BrokerageControl.getMarketSys().updateMarketIndexData("DIA", "c6");
        diaPercent = BrokerageControl.getMarketSys().updateMarketIndexData("DIA", "p2");
        nasdaqAsk = BrokerageControl.getMarketSys().updateMarketIndexData("^IXIC", "l1");
        nasdaqChange = BrokerageControl.getMarketSys().updateMarketIndexData("^IXIC", "c6");
        nasdaqPercent = BrokerageControl.getMarketSys().updateMarketIndexData("^IXIC", "p2");
        spAsk = BrokerageControl.getMarketSys().updateMarketIndexData("^GSPC", "l1");
        spChange = BrokerageControl.getMarketSys().updateMarketIndexData("^GSPC", "c6");
        spPercent = BrokerageControl.getMarketSys().updateMarketIndexData("^GSPC", "p2");
        bondAsk = BrokerageControl.getMarketSys().updateMarketIndexData("^TNX", "l1");
        bondChange = BrokerageControl.getMarketSys().updateMarketIndexData("^TNX", "c6");
        bondPercent = BrokerageControl.getMarketSys().updateMarketIndexData("^TNX", "p2");
        
        if(BrokerageControl.getUserList().get(BrokerageControl.getUserIndex()) instanceof Investor) {
            BrokerageControl.getInvestorGUI().getDiaAskLabel().setText(diaAsk);
            BrokerageControl.getInvestorGUI().getDiaChangeLabel().setText(diaChange);
            BrokerageControl.getInvestorGUI().getDiaPercentChangeLabel().setText(diaPercent);
            BrokerageControl.getInvestorGUI().getNasdaqAskLabel().setText(nasdaqAsk);
            BrokerageControl.getInvestorGUI().getNasdaqChangeLabel().setText(nasdaqChange);
            BrokerageControl.getInvestorGUI().getNasdaqPercentChangeLabel().setText(nasdaqPercent);
            BrokerageControl.getInvestorGUI().getSPAskLabel().setText(spAsk);
            BrokerageControl.getInvestorGUI().getSPChangeLabel().setText(spChange);
            BrokerageControl.getInvestorGUI().getSPPercentChangeLabel().setText(spPercent);
            BrokerageControl.getInvestorGUI().getBondAskLabel().setText(bondAsk);
            BrokerageControl.getInvestorGUI().getBondChangeLabel().setText(bondChange);
            BrokerageControl.getInvestorGUI().getBondPercentChangeLabel().setText(bondPercent);
        }
        else if(BrokerageControl.getUserList().get(BrokerageControl.getUserIndex()) instanceof Broker) {
            BrokerageControl.getBrokerGUI().getDiaAskLabel().setText(diaAsk);
            BrokerageControl.getBrokerGUI().getDiaChangeLabel().setText(diaChange);
            BrokerageControl.getBrokerGUI().getDiaPercentChangeLabel().setText(diaPercent);
            BrokerageControl.getBrokerGUI().getNasdaqAskLabel().setText(nasdaqAsk);
            BrokerageControl.getBrokerGUI().getNasdaqChangeLabel().setText(nasdaqChange);
            BrokerageControl.getBrokerGUI().getNasdaqPercentChangeLabel().setText(nasdaqPercent);
            BrokerageControl.getBrokerGUI().getSPAskLabel().setText(spAsk);
            BrokerageControl.getBrokerGUI().getSPChangeLabel().setText(spChange);
            BrokerageControl.getBrokerGUI().getSPPercentChangeLabel().setText(spPercent);
            BrokerageControl.getBrokerGUI().getBondAskLabel().setText(bondAsk);
            BrokerageControl.getBrokerGUI().getBondChangeLabel().setText(bondChange);
            BrokerageControl.getBrokerGUI().getBondPercentChangeLabel().setText(bondPercent);
        }
        
        // LookupFlag is true if the user has requested a stock lookup on the
        // Market Research screen.
        if (BrokerageControl.getLookupFlag()) {
            lookupName = BrokerageControl.getMarketSys().getStockName(BrokerageControl.getLookupSymbol());
            lookupAsk = Double.toString(BrokerageControl.getMarketSys().getStockPrice(BrokerageControl.getLookupSymbol()));
            lookupChange = BrokerageControl.getMarketSys().getStockChange(BrokerageControl.getLookupSymbol());
            lookupPercent = BrokerageControl.getMarketSys().getStockPercent(BrokerageControl.getLookupSymbol());
            
            if(BrokerageControl.getUserList().get(BrokerageControl.getUserIndex()) instanceof Investor) {
                BrokerageControl.getInvestorGUI().getLookupNameLabel().setText(lookupName);
                BrokerageControl.getInvestorGUI().getLookupAskLabel().setText(lookupAsk);
                BrokerageControl.getInvestorGUI().getLookupChangeLabel().setText(lookupChange);
                BrokerageControl.getInvestorGUI().getLookupPercentChangeLabel().setText(lookupPercent);
            }
            else if(BrokerageControl.getUserList().get(BrokerageControl.getUserIndex()) instanceof Broker) {
                BrokerageControl.getBrokerGUI().getLookupNameLabel().setText(lookupName);
                BrokerageControl.getBrokerGUI().getLookupAskLabel().setText(lookupAsk);
                BrokerageControl.getBrokerGUI().getLookupChangeLabel().setText(lookupChange);
                BrokerageControl.getBrokerGUI().getLookupPercentChangeLabel().setText(lookupPercent);
            }
        }
        
        // Check to see if any pending orders are due for action.
        BrokerageControl.getOrderSys().iteratePendingOrders();
        
        // Pause for a short (but arbitrary) amount of time.
        Thread.currentThread().sleep(10000);
    }
}
