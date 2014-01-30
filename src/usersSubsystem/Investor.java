package usersSubsystem;

import accountSubsystem.Account;
import portfolioSubsystem.Portfolio;

/**
 * Investor class for Brokerage system that extends User class
 *
 * @author Fernando Cortez
 * @version 20121130
 *
 * 20121117: DB amended constructor to create one-to-one mapping with Investor's
 * Account and Portfolio, plus added Broker field
 *
 * 20121120: DB altered Investor class to work with account, portfolio, and
 * order
 */
public class Investor extends User {

    private static final long serialVersionUID = 2133401306872573351L;

    /*
     * VARIABLES
     */
    /**
     * Investor's portfolio storing stocks owned
     */
    private Portfolio portfolio;
    /**
     * Investor's account storing margin balance, cash balance, etc.
     */
    private Account account;
    /**
     * The Broker object the Investor is assigned to
     */
    private Broker broker;

    /*
     * CONSTRUCTORS
     */
    /**
     * When creating a new investor account, a unique identification number,
     * first and last name, and initial cash deposit of the investor are needed.
     * Constructor calls the constructor of the super class
     *
     * @param ID Identification number is set when creating new investor account
     * @param fname First name for new investor
     * @param lname Last name for new investor
     * @param initialDeposit Initial cash deposit investor makes when creating
     * account
     */
    public Investor(long ID, String fname, String lname, String password,
            Broker broker, double initialDeposit) {
        super(ID, fname, lname, password);
        this.portfolio = new Portfolio(this);
        this.account = new Account(this);
        this.broker = broker;
        this.getAccount().depositCash(initialDeposit, "Initial deposit");
    }
    
    /**
     * Returns the Investor's portfolio
     * 
     * @return portfolio
     */
    public Portfolio getPortfolio() {
        return portfolio;
    }
    
    /**
     * Returns the Investor's account
     * 
     * @return account
     */
    public Account getAccount() {
        return account;
    }
    
    /**
     * Returns the Broker object the Investor is associated with
     * 
     * @return broker
     */
    public Broker getBroker() {
        return broker;
    }
}
