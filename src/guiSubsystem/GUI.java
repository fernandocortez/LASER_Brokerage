package guiSubsystem;
/**
 * The Class that is used by the BrokerageControl to initiate each part
 * of the GUI when it is needed.  This class is used as a buffer class
 * so that the BrokerageControl only has to call one instance for the GUI
 * and then when each screen needs to be displayed the method that starts
 * the needed GUI is called.
 * 
 * @author Thomas Wilson
 * @version 201211181400 (TS)
 * @version 201211212000 (TW)
 */
public class GUI
{


/**
 * Initiates the Login Screen GUI, this will be called by BrokerageControl
 * through an instance of the GUI class.  This will allow a user to log into
 * Laser Brokerage as either an Investor or Broker depending on the information
 * given.
 */    

    public Login LoginGUI()
    {

        Login log = new Login();
        return log;
    }
    
/**
 * Initiates the Investor Screen GUI, this will be called by BrokerageControl
 * through an instance of the GUI class.  This screen only appears when a user
 * enters the correct information on LoginGUI that shows that they are
 * a registered Investor. This will allow a user to view their portfolio,
 * buy and sell stock, view the market, and contact their Broker.
 */

    public InvestorGUI InvestorGUI()
    {
        InvestorGUI invest = new InvestorGUI();
        return invest;
    }
    
/**
 * Initiates the Broker Screen GUI, this will be called by BrokerageControl
 * through an instance of the GUI class.  This screen only appears when a user
 * enters the correct information on LoginGUI that shows that they are
 * a registered Broker.  This will allow a user to view their Investors, 
 * contact their Investor, view the market, and Buy or Sell an
 * Investor's stock depending on what they have requested.
 */
    
    public BrokerGUI BrokerGUI()
    {
        BrokerGUI broker = new BrokerGUI();
        return broker;
    }
}