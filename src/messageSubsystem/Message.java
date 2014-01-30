package messageSubsystem;

import java.io.Serializable;
import java.util.Date;
import usersSubsystem.*;

/**
 * Message class represents an individual message.
 * 
 * @author Thomas Savage
 * @version 20121117 (DB)
 * @version 201211191900 (TS) 
 */
public class Message implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8527193118198389215L;

	/**
	 * The name of the owner of the MessageAccount to which this message was
	 * sent.
	 */
	private User to;
	
	/**
	 * The name of the owner of the MessageAccount from which this message was
	 * sent.
	 */
	private User from;
	
	/**
	 * The subject header of the message. 
	 */
	private String subject;
	
	/**
	 * The body of the message.
	 */
	private String body;
	
	/**
	 * Date object for recording a timestamp.
	 */
	private Date date;
	
	/**
	 * The date and time that this message was created (sent).
	 */
	private String timestamp;

	/**
	 * Create a new message. This is called when the user sends the message.
	 * 
	 * @param to The message recipient.
	 * @param from The message sender.
	 * @param subject The subject line.
	 * @param body The main body of the message.
	 */
	public Message(User to, User from, String subject, String body) {
		this.to = to;
		this.from = from;
		this.subject = subject;
		this.body = body;
		this.date = new Date(System.currentTimeMillis());
		this.timestamp = date.toString();
	}
	
	/**
	 * @return to The message recipient.
	 */
	public User getTo() {
		return to;
	}
	
	/**
	 * @return from The message sender.
	 */
	public User getFrom() {
		return from;
	}
	
	/**
	 * @return subject The message subject.
	 */
	public String getSubject() {
		return subject;
	}
	
	/**
	 * @return body The message body.
	 */
	public String getBody() {
		return body;
	}
	
	/**
	 * @return timestamp The time and date the message was sent.
	 */
	public String getTimestamp() {
		return timestamp;
	}
}
