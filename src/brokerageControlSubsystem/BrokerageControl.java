package brokerageControlSubsystem;

import marketDataSubsystem.*;
import accountSubsystem.*;
import orderSubsystem.*;
import usersSubsystem.*;
import guiSubsystem.*;
import fileSubsystem.*;
import portfolioSubsystem.*;
import java.io.File;
import messageSubsystem.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;

/**
 * The main controller class for the system. Execution begins here and access
 * to all other controllers flows through this class.
 *
 * @author Thomas Savage
 * @version 201211271730
 */
public class BrokerageControl {

    /**
     * The GUI superclass. Used for reference when instantiating the other GUI
     * components.
     */
    private static GUI gui;
    
    /**
     * The Login window.
     */
    private static Login loginGUI;
    
    /**
     * The main window presented when logging in as a Broker.
     */
    private static BrokerGUI brokerGUI;
    
    /**
     * The main window presented when logging in as an Investor. 
     */
    private static InvestorGUI investorGUI;
    
    /**
     * The Ordering subsystem.
     */
    private static Ordering orderSys;
    
    /**
     * The MarketData subsystem. 
     */
    private static MarketData marketSys;
    
    /**
     * The list of all registered users. This and userIndex are used
     * extensively to keep track of which User is currently logged in. 
     */
    private static ArrayList<User> userList;
    
    /**
     * The index of the current User within userList. This and userList are
     * used extensively to keep track of which User is currently logged in. 
     */
    private static int userIndex;
    
    /**
     * The list of currently pending trades. The PeriodicFunctionsThread checks
     * the contents of this list routinely to determine if a pending order's
     * conditions have been met.
     */
    public static ArrayList<Trade> pendingTrades;
    
    /**
     * A list of all registered Investors.
     */
    private static ArrayList<Investor> investorList;
    
    /**
     * A flag indicating whether or not a stock lookup has been performed. The
     * PeriodicFunctionsThread checks this flag to determine if the stock
     * lookup fields should be included in its MarketData updates. 
     */
    private static boolean lookupFlag = false;
    
    /**
     * The stock ticker symbol for which the user has requested a MarketData
     * lookup. 
     */
    private static String lookupSymbol;
    
    /**
     * The class that runs the other thread used by the program for periodic
     * functions that can occur in the background and that should not interrupt
     * the flow of the program.
     */
    private static final PeriodicFunctionsThread periodic =
            new PeriodicFunctionsThread();
    
    /**
     * The thread used by PeriodicFunctionsThread.
     */
    private static final Thread pfThread = new Thread(periodic);

    /**
     * The main method. Execution begins here. GUI, Ordering, and MarketData
     * subsystems are readied. The list of registered users is loaded from the
     * serialization file and some elements of the Login window are
     * initialized. PeriodicFunctionsThread is started.
     * 
     * @param args Command line arguments; not used by this program.
     */
    public static void main(String[] args) {

        gui = new GUI();
        orderSys = new Ordering();
        marketSys = new MarketData();
        userList = new ArrayList<User>();
        pendingTrades = new ArrayList<Trade>();
        investorList = new ArrayList<Investor>();

// Read in the serialized Users:  
//================================================================
        UserArrayReader loadUsers = new UserArrayReader(userList);
        userList = loadUsers.readUserArray();
//===============================================================

//==========================================================
//   Use this block of code to generate three new Brokers. 
//   Remember to comment out when you're done with it.
//   Before you reset system and write new Brokers, erase
//   the previous .ser serialization file.

//Broker bob = new Broker(002, "Bob", "Jones", "abc");
//userList.add(bob);
//UserArrayWriter writeUsers = new UserArrayWriter(userList);
//            
//Broker carol = new Broker(003, "Carol", "Kennedy", "abc");
//userList.add(carol);
//            
//Broker ted = new Broker(004, "Ted", "Nugent", "abc");
//userList.add(ted);
//writeUsers.writeUserArray();

//===========================================================

        loginGUI = gui.LoginGUI();
        investorGUI = gui.InvestorGUI();
        brokerGUI = gui.BrokerGUI();

        // Populate the list of Brokers, list of Investors
        for (int i = 0; i < userList.size(); i++) {
            User usr = userList.get(i);
            if (usr instanceof Broker) {
                loginGUI.getBrokerBox().addItem(usr);
            }
            if (usr instanceof Investor) {
            	investorList.add((Investor)usr);
            }
            
        }
        
        // Construct an array of pendingTrades for PeriodicFunctionsThread use.
        for (Investor i : investorList) {
        	for (Trade t : i.getPortfolio().getTradeHistory()) {
        		if (t.getPending() == true) { 
        			pendingTrades.add(t);
        		}
        	}
        }
        
        loginGUI.setVisible(true);

        // Remove the "No Users Found" label if users were found
        if (!userList.isEmpty()) {
            loginGUI.getNoUsersLabel().setVisible(false);
        }

        // This loop is for diagnostic purposes, comment out if you wish. It
        // will print user names and passwords to stdout to help you remember
        // login credentials.
        //for (int i = 0; i < userList.size(); i++) {
            //System.out.print(userList.get(i).getFname() + " ");
            //System.out.print(userList.get(i).getID() + " ");
            //System.out.println(userList.get(i).getPasswd());
        //}
        
        // Start running PeriodicFunctionThread.
        pfThread.start();
    }

    /**
     * Deposit cash into an Investor's cash account. This method is called from
     * InvestorGUI's Deposit_Cash_panel.
     *
     * @param amt Amount of cash to add to the account.
     */
    public static void depositCash(double amt) {
        Investor inv = (Investor) userList.get(userIndex);
        inv.getAccount().depositCash(amt, "Cash Deposit");
    }

    /**
     * Withdraw cash from an Investor's cash account. This method is called
     * from InvestorGUI's Withdraw_Cash_panel. A warning is displayed if the
     * amount requested to withdraw exceeds the Investor's available funds.
     *
     * @param amt Amount of cash to withdraw from the account.
     */
    public static void withdrawCash(double amt) {
        Investor inv = (Investor) userList.get(userIndex);
        
        if(inv.getAccount().getCashBalance() - amt >= 0) {
            inv.getAccount().depositCash(-1.*amt, "Cash Withdrawal");
            investorGUI.getWithdrawCashPanel().setVisible(false);
        }
        // Insufficient funds
        else {
            String bal = Double.toString(inv.getAccount().getCashBalance());
            JOptionPane.showMessageDialog(null, "Insufficient funds. Enter"
                    + " an amount less than or equal to ".concat(bal),
                    "Insufficient Funds", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Populate the combobox containing all users available to receive a
     * message. This method is called when the user wants to compose a new
     * message.
     */
    public static void populateToBox() {
        User usr = userList.get(userIndex);
        
        for (int i = 0; i < userList.size(); i++) {
            if ((i != userIndex) && (usr instanceof Investor)) {
                investorGUI.getToBox().addItem(userList.get(i));
            }
            else if ((i != userIndex) && (usr instanceof Broker)) {
                brokerGUI.getToBox().addItem(userList.get(i));
            }
        }
    }
    
    /**
     * @param symbol The stock ticker symbol for which the user is requesting a
     * MarketData lookup.
     */
    public static void lookupRequest(String symbol) {
        lookupSymbol = symbol;
        
        String lookupName = marketSys.getStockName(lookupSymbol);
        String lookupAsk = Double.toString(marketSys.getStockPrice(lookupSymbol));
        String lookupChange = marketSys.getStockChange(lookupSymbol);
        String lookupPercent = marketSys.getStockPercent(lookupSymbol);

        investorGUI.getLookupNameLabel().setText(lookupName);
        investorGUI.getLookupAskLabel().setText(lookupAsk);
        investorGUI.getLookupChangeLabel().setText(lookupChange);
        investorGUI.getLookupPercentChangeLabel().setText(lookupPercent);
        
        lookupFlag = true;
    }
    
    /**
     * Suspend updates of the requested stock's MarketData lookups in
     * PeriodicFunctionsThread.
     */
    public static void lookupStop() {
        lookupFlag = false;
    }
    
    /**
     * Sends a message from one User to another. This method is called when the
     * user clicks the "Send" button from the ComposeMessageFrame.
     */
    public static void sendMessage() {
        User from = userList.get(userIndex);
        
        if(from instanceof Investor) {
            User to = (User)investorGUI.getToBox().getSelectedItem();
            String subject = investorGUI.getSubjectText().getText();
            String body = investorGUI.getBodyTextArea().getText();
            
            Message msg = new Message(to, from, subject, body);
            MessageAccount toAcct = to.getMessageAccount(to);
            toAcct.sendMessage(msg);
        }
        else if(from instanceof Broker) {
            User to = (User)brokerGUI.getToBox().getSelectedItem();
            String subject = brokerGUI.getSubjectText().getText();
            String body = brokerGUI.getBodyTextArea().getText();
            
            Message msg = new Message(to, from, subject, body);
            MessageAccount toAcct = to.getMessageAccount(to);
            toAcct.sendMessage(msg);
        }
    }
    
    /**
     * Display the contents of the selected message.
     * 
     * @param msgIndex The JList index of the selected message.
     */
    public static void showMessage(int msgIndex) {
        User usr = userList.get(userIndex);

        String sender = usr.getMessageAccount(usr).getMessages()
                .get(msgIndex).getFrom().toString();
        String subject = usr.getMessageAccount(usr).getMessages()
                .get(msgIndex).getSubject();
        String body = usr.getMessageAccount(usr).getMessages()
                .get(msgIndex).getBody();
        String tstamp = usr.getMessageAccount(usr).getMessages()
                .get(msgIndex).getTimestamp();

        if(usr instanceof Investor) {
            investorGUI.getSenderText().setText(sender);
            investorGUI.getSubjectViewText().setText(subject);
            investorGUI.getBodyViewTextArea().setText(body);
            investorGUI.getDateText().setText(tstamp);
        }
        else if(usr instanceof Broker) {
            brokerGUI.getSenderText().setText(sender);
            brokerGUI.getSubjectViewText().setText(subject);
            brokerGUI.getBodyViewTextArea().setText(body);
            brokerGUI.getDateText().setText(tstamp);
        }
    }
    
    /**
     * Delete the selected message from the User's messageAccount.
     * 
     * @param msgIndex The JList index of the selected message.
     */
    public static void deleteMessage(int msgIndex) {
        User usr = userList.get(userIndex);
        usr.getMessageAccount(usr).getMessages().remove(msgIndex);
        
        if(usr instanceof Investor) {
            investorGUI.getSenderText().setText("");
            investorGUI.getSubjectViewText().setText("");
            investorGUI.getDateText().setText("");
            investorGUI.getBodyViewTextArea().setText("");
        }
        else if(usr instanceof Broker) {
            brokerGUI.getSenderText().setText("");
            brokerGUI.getSubjectViewText().setText("");
            brokerGUI.getDateText().setText("");
            brokerGUI.getBodyViewTextArea().setText("");
        }
        updateMessagesView();
    }
    
    /**
     * Select the next message in the list.
     * 
     * @param msgIndex The JList index of the current selected message.
     */
    public static void nextMessage(int msgIndex) {
        User usr = userList.get(userIndex);
        
        if(usr instanceof Investor) {
            int numMsgs = investorGUI.getMessagesList().getModel().getSize();

            if(msgIndex == (numMsgs - 1)) {
                investorGUI.getMessagesList().setSelectedIndex(0);
            }
            else {
                investorGUI.getMessagesList().setSelectedIndex(msgIndex + 1);
            }
        }
        else if(usr instanceof Broker) {
            int numMsgs = brokerGUI.getMessagesList().getModel().getSize();

            if(msgIndex == (numMsgs - 1)) {
                brokerGUI.getMessagesList().setSelectedIndex(0);
            }
            else {
                brokerGUI.getMessagesList().setSelectedIndex(msgIndex + 1);
            }
        }
    }
    
    /**
     * Select the previous message in the list.
     * 
     * @param msgIndex The JList index of the current selected message.
     */
    public static void previousMessage(int msgIndex) {
        User usr = userList.get(userIndex);
        
        if(usr instanceof Investor) {
            int numMsgs = investorGUI.getMessagesList().getModel().getSize();

            if(msgIndex == 0) {
                investorGUI.getMessagesList().setSelectedIndex(numMsgs -1);
            }
            else {
                investorGUI.getMessagesList().setSelectedIndex(msgIndex - 1);
            }
        }
        else if(usr instanceof Broker) {
            int numMsgs = brokerGUI.getMessagesList().getModel().getSize();

            if(msgIndex == 0) {
                brokerGUI.getMessagesList().setSelectedIndex(numMsgs -1);
            }
            else {
                brokerGUI.getMessagesList().setSelectedIndex(msgIndex - 1);
            }
        }
    }
    
    /**
     * Select the first message in the list.
     */
    public static void firstMessage() {
        User usr = userList.get(userIndex);
        
        if(usr instanceof Investor) {
            investorGUI.getMessagesList().setSelectedIndex(0);
        }
        else if(usr instanceof Broker) {
            brokerGUI.getMessagesList().setSelectedIndex(0);
        }
    }
    
    /**
     * Select the last message in the list.
     */
    public static void lastMessage() {
        User usr = userList.get(userIndex);
        
        if(usr instanceof Investor) {
            int numMsgs = investorGUI.getMessagesList().getModel().getSize();
            investorGUI.getMessagesList().setSelectedIndex(numMsgs - 1);
        }
        else if(usr instanceof Broker) {
            int numMsgs = brokerGUI.getMessagesList().getModel().getSize();
            brokerGUI.getMessagesList().setSelectedIndex(numMsgs - 1);
        }
    }

    /**
     * Check a new Investor's requested ID number for uniqueness. All users
     * must have a unique ID number.
     *
     * @param userid The requested user ID number.
     * @return true/false if the requested ID number is not currently in use.
     */
    public static boolean isUniqueID(long userid) {
        boolean isUnique = true;

        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getID() == userid) {
                isUnique = false;
            }
        }

        return isUnique;
    }

    /**
     * Add a new Investor to the userList. This method is called from Login's
     * newInvestorFrame or BrokerGUI's New_Investor_Account_panel. Note that
     * input error checking is handled by the calling GUI component before the
     * arguments are passed here.
     *
     * @param userid The ID number of the new Investor.
     * @param fname The new Investor's first name.
     * @param lname The new Investor's last name.
     * @param pword The new Investor's password. If called from the BrokerGUI
     * this will be a default password set by the Broker.
     * @param broker The Broker responsible for this Investor.
     * @param deposit The initial cash deposit in the new Investor's account.
     */
    public static void newInvestor(long userid, String fname, String lname,
            char[] pword, Broker broker, double deposit) {

        String password = new String(pword);

        userList.add(new Investor(userid, fname, lname, password, broker,
                deposit));
    }

    /**
     * Log in to the Brokerage. This method handles some input error checking
     * in addition to validation of correct user ID / password combinations.
     *
     * @param uid The supplied user ID at the login screen.
     * @param pword The supplied password at the login screen.
     */
    public static void doLogin(String uid, char[] pword) {
        boolean validID = false;
        boolean validPW = false;
        int index = 0;
        long userid = 0;

        try {
            userid = Long.valueOf(uid).longValue();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "User ID must consist of"
                    + " only numbers.", "Invalid ID Number",
                    JOptionPane.WARNING_MESSAGE);
        }

        String password = new String(pword);

        // Check for user ID and password matches.
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getID() == userid) {
                validID = true;

                if (userList.get(i).getPasswd().equals(password)) {
                    validPW = true;
                    index = i;
                }
            }
        }

        // Found a match for user ID and password.
        if (validID && validPW) {
            User temp = userList.get(index);

            if (temp instanceof Investor) {
                loginGUI.setVisible(false);
                investorGUI.setVisible(true);
                investorGUI.getCurrentUserText().setText(
                        temp.getFname().concat(" ").concat(temp.getLname()));
                userIndex = index;
            } else if (temp instanceof Broker) {
                loginGUI.setVisible(false);
                brokerGUI.setVisible(true);
                brokerGUI.getCurrentUserText().setText(
                        temp.getFname().concat(" ").concat(temp.getLname()));
                userIndex = index;
            }
        } // Found a user ID match but invalid password.
        else if (validID && !validPW) {
            JOptionPane.showMessageDialog(null, "The password you entered"
                    + " is not correct.", "Invalid Password",
                    JOptionPane.WARNING_MESSAGE);
        } // No user ID match found.
        else {
            JOptionPane.showMessageDialog(null, "The ID number you entered"
                    + " is not valid.", "Invalid ID Number",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Log out the current Investor. Called from InvestorGUI's log_out_panel.
     */
    public static void doInvestorLogout() {
        investorGUI.setVisible(false);
        loginGUI.setVisible(true);
    }

    /**
     * Log out the current Broker. Called from BrokerGUI's log_out_panel.
     */
    public static void doBrokerLogout() {
        brokerGUI.setVisible(false);
        loginGUI.setVisible(true);
    }

    /**
     * Update information display on InvestorGUI's View_Account_panel. This
     * method is called when the Investor clicks the View Account button. This
     * method is not yet complete.
     */
    public static void updateAccountView() {
        Investor inv = (Investor) userList.get(userIndex);
        investorGUI.getBrokerNameText().setText(inv.getBroker().getFname()
                .concat(" ").concat(inv.getBroker().getLname()));
        investorGUI.getBalanceText().setText(Double.toString(
                inv.getAccount().getCashBalance()));
        investorGUI.getMarginText().setText(Double.toString(
                inv.getAccount().getMarginBorrowed()));
        investorGUI.getBuyLongText().setText(Double.toString(
                inv.getAccount().getAvailFundsToBuyLong()));
        investorGUI.getSellShortText().setText(Double.toString(
                inv.getAccount().getAvailFundsToSellShort()));
        investorGUI.getPercentEquityText().setText(Double.toString(
                inv.getAccount().getPercentEquity()));
        
        Account acct = inv.getAccount();
        Portfolio folio = inv.getPortfolio();
        double totalMarketValue = 0;
        ArrayList<Position> positions = folio.getPositions();
        ArrayList<Trade> trades = folio.getTradeHistory();
        ArrayList<CashTransaction> cashTrans = acct.getCashHistory();
        ArrayList<MarginTransaction> margTrans = acct.getMarginHistory();
        DefaultListModel positionModel = new DefaultListModel();
        DefaultListModel pendingModel = new DefaultListModel();
        DefaultListModel tradesModel = new DefaultListModel();
        DefaultListModel cashHistModel = new DefaultListModel();
        DefaultListModel margHistModel = new DefaultListModel();
        
        investorGUI.getStockOwnedList().setModel(positionModel);
        investorGUI.getPendingList().setModel(pendingModel);
        investorGUI.getTradeHistoryList().setModel(tradesModel);
        investorGUI.getCashHistoryList().setModel(cashHistModel);
        investorGUI.getMarginHistoryList().setModel(margHistModel);
        
        // Build the list of positions
        if(!positions.isEmpty()) {
            for(int i = 0; i < positions.size(); i++) {
                positionModel.addElement(positions.get(i));
                totalMarketValue += positions.get(i).getMarketValue();
            }
            
            investorGUI.getMarketValueText().setText(Double.toString(totalMarketValue));
            investorGUI.getStockOwnedList().setModel(positionModel);
        }
        
        // Build the list of trades
        if(!trades.isEmpty()) {
            for(int i = 0; i < trades.size(); i++) {
                tradesModel.addElement(trades.get(i));
                
                if(trades.get(i).getPending()) {
                    pendingModel.addElement(trades.get(i));
                }
            }
            
            investorGUI.getTradeHistoryList().setModel(tradesModel);
            investorGUI.getPendingList().setModel(pendingModel);
        }
        
        // Build the list of cash transactions
        if(!cashTrans.isEmpty()) {
            for(int i = 0; i < cashTrans.size(); i++) {
                cashHistModel.addElement(cashTrans.get(i));
            }
            
            investorGUI.getCashHistoryList().setModel(cashHistModel);
        }
        
        // Build the list of margin transactions
        if(!margTrans.isEmpty()) {
            for(int i = 0; i < margTrans.size(); i++) {
                margHistModel.addElement(margTrans.get(i));
            }
            
            investorGUI.getMarginHistoryList().setModel(margHistModel);
        }
    }
    
    /**
     * Populate the combobox in the Broker's Place Order screen. This box
     * contains a list of the current Broker's Investors.
     */
    public static void updateBrokerPlaceOrder() {
        Broker bkr = (Broker)userList.get(userIndex);

        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i) instanceof Investor) {
                Investor inv = (Investor)userList.get(i);

                if (inv.getBroker().equals(bkr)) {
                    brokerGUI.getInvestorOrderBox().addItem(inv);
                }
            }
        }
    }

    /**
     * Update information display on BrokerGUI's View_Investor_Account_panel.
     * This method is called when the Broker clicks the View Investor Account
     * button.
     */
    public static void updateInvestorAccountView() {
        Broker bkr = (Broker)userList.get(userIndex);

        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i) instanceof Investor) {
                Investor inv = (Investor)userList.get(i);

                if (inv.getBroker().equals(bkr)) {
                    brokerGUI.getInvestorBoxViewAcct().addItem(inv);
                }
            }
        }
    }

    /**
     * Updates the Investor Balance display in BrokerGUI's
     * View_Investor_Account_panel. This method is called from
     * updateInvestorAccountView or whenever the Broker selects a new Investor
     * from the InvestorBox.
     *
     * @param inv The currently selected Investor in the InvestorBox.
     */
    public static void updateSelectedInvestor(Investor inv) {
        brokerGUI.getInvestorBalanceText().setText("");
        
        if((brokerGUI.getInvestorBoxViewAcct().getSelectedIndex() != -1)) {
            brokerGUI.getInvestorBalanceText().setText(
                    Double.toString(inv.getAccount().getCashBalance()));
            brokerGUI.getMarginText().setText(Double.toString(
                    inv.getAccount().getMarginBorrowed()));
            brokerGUI.getBuyLongText().setText(Double.toString(
                    inv.getAccount().getAvailFundsToBuyLong()));
            brokerGUI.getSellShortText().setText(Double.toString(
                    inv.getAccount().getAvailFundsToSellShort()));
            brokerGUI.getPercentEquityText().setText(Double.toString(
                    inv.getAccount().getPercentEquity()));
            
            Account acct = inv.getAccount();
            Portfolio folio = inv.getPortfolio();
            double totalMarketValue = 0;
            ArrayList<Position> positions = folio.getPositions();
            ArrayList<Trade> trades = folio.getTradeHistory();
            ArrayList<CashTransaction> cashTrans = acct.getCashHistory();
            ArrayList<MarginTransaction> margTrans = acct.getMarginHistory();
            DefaultListModel positionModel = new DefaultListModel();
            DefaultListModel pendingModel = new DefaultListModel();
            DefaultListModel tradesModel = new DefaultListModel();
            DefaultListModel cashHistModel = new DefaultListModel();
            DefaultListModel margHistModel = new DefaultListModel();
            
            brokerGUI.getStockOwnedList().setModel(positionModel);
            brokerGUI.getPendingList().setModel(pendingModel);
            brokerGUI.getTradeHistoryList().setModel(tradesModel);
            brokerGUI.getCashHistoryList().setModel(cashHistModel);
            brokerGUI.getMarginHistoryList().setModel(margHistModel);
            
            // Build list of positions
            if(!positions.isEmpty()) {
                for(int i = 0; i < positions.size(); i++) {
                    positionModel.addElement(positions.get(i));
                    totalMarketValue += positions.get(i).getMarketValue();
                }
                
                brokerGUI.getMarketValueText().setText(Double.toString(totalMarketValue));
                brokerGUI.getStockOwnedList().setModel(positionModel);
            }
            
            // Build the list of trades
            if(!trades.isEmpty()) {
                for(int i = 0; i < trades.size(); i++) {
                    tradesModel.addElement(trades.get(i));

                    if(trades.get(i).getPending()) {
                        pendingModel.addElement(trades.get(i));
                    }
                }

                brokerGUI.getTradeHistoryList().setModel(tradesModel);
                brokerGUI.getPendingList().setModel(pendingModel);
            }
            
            // Build the list of cash transactions
            if(!cashTrans.isEmpty()) {
                for(int i = 0; i < cashTrans.size(); i++) {
                    cashHistModel.addElement(cashTrans.get(i));
                }

                brokerGUI.getCashHistoryList().setModel(cashHistModel);
            }
            
            // Build the list of margin transactions
            if(!margTrans.isEmpty()) {
                for(int i = 0; i < margTrans.size(); i++) {
                    margHistModel.addElement(margTrans.get(i));
                }

                brokerGUI.getMarginHistoryList().setModel(margHistModel);
            }
        }
    }

    /**
     * Update the list of messages for the current User. This method is called
     * when the user clicks the messages button.
     */
    public static void updateMessagesView() {
        User usr = userList.get(userIndex);
        MessageAccount msgAcct = usr.getMessageAccount(usr);
        DefaultListModel dlmodel = new DefaultListModel();

        if (!msgAcct.getMessages().isEmpty()) {
            for (int i = 0; i < msgAcct.getMessages().size(); i++) {
                dlmodel.addElement(msgAcct.getMessages().get(i).getSubject());
            }
        }
        
        if(usr instanceof Investor) {
            investorGUI.getMessagesList().setModel(dlmodel);
        }
        else if(usr instanceof Broker) {
          brokerGUI.getMessagesList().setModel(dlmodel);
        }
    }
    
    /**
     * Submit an order. This method will pass the relevant information to the
     * Ordering subsystem for processing.
     * 
     * @param Symbol The stock ticker symbol.
     * @param amount The amount of stock to buy or sell.
     * @param LimitPrice The limit price if this is a limit order.
     * @param buyOrder Buy/sell type of order. If false, this is a sell.
     * @param limitOrder Market/limit type of order. If false, this is a market
     * order.
     * @param BrokerAssist True/false if the Investor wants Broker assistance.
     */
    public static void submitOrder(String Symbol,int amount, Double LimitPrice, 
            Boolean buyOrder, Boolean limitOrder, Boolean BrokerAssist)
    {
        User usr = userList.get(userIndex);
        String result = "";
        
        if(usr instanceof Investor)
        {
            Investor inv = (Investor)usr;
            result = orderSys.enterOrder(inv,Symbol,amount,LimitPrice,buyOrder,limitOrder,BrokerAssist);
        }
        else if(usr instanceof Broker)
        {
            Investor invest = (Investor) brokerGUI.getInvestorOrderBox().getSelectedItem();
            result = orderSys.enterOrder(invest,Symbol,amount,LimitPrice,buyOrder,limitOrder,BrokerAssist);
        }
        
        investorGUI.getStatusBar().setText(result);
    }

    /**
     * Save the users, stop the PeriodicFunctionsThread, clean up the graphs,
     * and shut down the system.
     */
    public static void shutDown() {
        pfThread.interrupt();
        
        UserArrayWriter writeUsers = new UserArrayWriter(userList);
        writeUsers.writeUserArray();
        
        int graphNum = marketSys.getGraphNum();
        for(int i = 0; i < graphNum; i++) {
            File file = new File("mktgraph".concat(Integer.toString(i))
                    .concat(".png"));
            file.delete();
        }
        
        System.exit(0);
    }

    public static GUI getGUI() {
        return gui;
    }
    
    public static InvestorGUI getInvestorGUI() {
        return investorGUI;
    }
    
    public static BrokerGUI getBrokerGUI() {
        return brokerGUI;
    }

    public static Ordering getOrderSys() {
        return orderSys;
    }

    public static MarketData getMarketSys() {
        return marketSys;
    }

    public static ArrayList<User> getUserList() {
        return userList;
    }

    public static int getUserIndex() {
        return userIndex;
    }
    
    public static boolean getLookupFlag() {
        return lookupFlag;
    }
    
    public static String getLookupSymbol() {
        return lookupSymbol;
    }
}
