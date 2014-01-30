package fileSubsystem;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import usersSubsystem.*;

/**
 * This class handles object serialization for all persistent data required by
 * the system. A single ArrayList of all Users is serialized, catching all
 * extended serializable objects associated with those Users along with it.
 * 
 * @author Thomas Savage
 * @version 201211181530
 */
public class UserArrayWriter {
	
	private ArrayList<User> users;
	private String filename;
	private FileOutputStream fos;
	private ObjectOutputStream oos;

	/**
	 * Constructs a new UsersArrayWriter and sets the output filename.
	 * 
	 * @param users An ArrayList containing all Users.
	 */
	public UserArrayWriter(ArrayList<User> users) {
		this.users = users;
		this.filename = "laser.ser";
	}
	
	/**
	 * Perform the serialization.
	 * 
	 * @return true/false Success status of the serialization operation.
	 */
	public Boolean writeUserArray() {
		
		try {
			fos = new FileOutputStream(filename);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(users);
			oos.close();
		}
		catch(IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

}