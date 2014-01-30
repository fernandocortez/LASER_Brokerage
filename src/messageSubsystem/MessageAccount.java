package messageSubsystem;

import java.io.Serializable;
import java.util.ArrayList;
import usersSubsystem.*;

/**
 * One-to-one relationship with an Investor or Broker. Stores messages and
 * provides methods for sending and receiving.
 * 
 * @author Thomas Savage
 * @version 20121117 (DB)
 * @version 201211191900 (TS)
 */
public class MessageAccount implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2494676952240727476L;

	/**
	 * The name of this account's owner.
	 */
	private User owner;
	
	/**
	 * A list of all messages sent or received.
	 */
	private ArrayList<Message> messages;

	/**
	 * Create a new account for a new user.
	 * 
	 * @param owner The name of the new account's owner.
	 */
	public MessageAccount(User owner) {
		this.owner = owner;
		this.messages = new ArrayList<Message>();
	}
	
	/**
	 * Add a message to this account. This name is now misleading and will
         * be changed in a future version.
	 * 
	 * @param message The message to be added.
	 * .
	 */
	public void sendMessage(Message message) {
		messages.add(message);
        }
	
	/**
	 * @return owner The name of this account's owner.
	 */
	public User getOwner() {
		return owner;
	}
	
	/**
	 * @return messages The list of the account's messages.
	 */
	public ArrayList<Message> getMessages() {
		return messages;
	}

}
