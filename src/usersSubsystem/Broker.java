package usersSubsystem;

/**
 * Broker class for Brokerage system that extends User class
 *
 * @author Thomas Savage
 * @author Fernando Cortez
 * @version 201211130
 */
public class Broker extends User {

    private static final long serialVersionUID = 2791052972797102940L;

    /*
     * CONSTRUCTORS
     */
    /**
     * When creating a new broker account, a unique identification number,
     * first, and last name of the broker are needed. Constructor calls the
     * constructor of the super class
     *
     * @param ID Identification number is set when creating new broker account
     * @param fname First name for new broker
     * @param lname Last name for new broker
     */
    public Broker(long ID, String fname, String lname, String password) {
        super(ID, fname, lname, password);
    }
}
