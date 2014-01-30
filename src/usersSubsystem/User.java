package usersSubsystem;

import java.io.Serializable;
import messageSubsystem.*;

/**
 * Basic user class for the Brokerage system
 * 
 * @author Fernando Cortez
 * @version 20121130
 */
public class User implements Serializable {

    private static final long serialVersionUID = 3519255399883763879L;

    /*
     * VARIABLES
     */
    /**
     * User's identification number. Primarily used for log in and record
     * keeping. The use of final denotes that once set it is not to be changed
     */
    private final long ID;
    /**
     * User's first name
     */
    private String fname;
    /**
     * User's last name
     */
    private String lname;
    /**
     * User's password
     */
    private String password;
    /**
     * Message account belonging to the user
     */
    private MessageAccount messageAccount;

    /*
     * CONSTRUCTORS
     */
    /**
     * When creating a new user, a unique identification number is needed,
     * first, and last name of the user are needed. Constructor is called by
     * Investor and Broker subclasses when creating a new Investor or Broker
     *
     * @param ID Identification number is set for new user
     * @param fname First name of new user
     * @param lname Last name for new user
     * @param password Password of the user
     * @param role Denotes role of user; distinguishes between types of users
     */
    public User(long ID, String fname, String lname, String password) {
        this.ID = ID;
        this.fname = fname;
        this.lname = lname;
        this.password = password;
        this.messageAccount = new MessageAccount(this);
    }

    /*
     * USER METHODS
     */

    /**
     * Returns the user's identification number
     *
     * @return ID
     */
    public long getID() {
        return this.ID;
    }

    /**
     * Returns user's first name
     *
     * @return fname
     */
    public String getFname() {
        return this.fname;
    }

    /**
     * Modifies the user's first name in case of error
     *
     * @param newFname New first name to replace text in current first name
     */
    public void editFname(String newFname) {
        this.fname = newFname;
    }

    /**
     * Returns the user's last name
     *
     * @return lname
     */
    public String getLname() {
        return this.lname;
    }

    /**
     * Modifies the user's last name in case of error
     *
     * @param newLname New last name to replace text in current last name
     */
    public void editLname(String newLname) {
        this.lname = newLname;
    }
    
    /**
     * Returns the user's full name as a single string
     * 
     * @return fname + " " +  lname
     */
    public String getFullName() {
        return this.fname + " " + this.lname;
    }

    /**
     * Returns user's password for authentication
     *
     * @return password
     */
    public String getPasswd() {
        return this.password;
    }

    /**
     * Changes user's password to new inputed password
     *
     * @param newPasswd User's new password
     */
    public void setPasswd(String newPasswd) {
        this.password = newPasswd;
    }
    
    public MessageAccount getMessageAccount(User user){
        return user.messageAccount;
    }
}
