package fileSubsystem;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import usersSubsystem.*;

/**
 * This class handles object deserialization for all persistent data required
 * by the system. A single ArrayList of all Users is deserialized, catching all
 * extended serialized objects associated with those Users along with it.
 * 
 * @author Thomas Savage
 * @version 201211181530
 */
public class UserArrayReader {
	
	private ArrayList<User> users;
	private String filename;
	private FileInputStream fis;
	private ObjectInputStream ois;

	/**
	 * Constructs a new UsersArrayReader and sets the input filename.
	 * 
	 * @param users An ArrayList to store all Users. Any data stored in users
	 * will be erased and replaced by the deserialization process.
	 */
	public UserArrayReader(ArrayList<User> users) {
		this.users = users;
		this.filename = "laser.ser";
	}
	
	/**
	 * Perform the deserialization.
	 * 
	 * @return users An ArrayList populated with all Users and their extended
	 * data.
	 */
	public ArrayList<User> readUserArray() {
		try {
			fis = new FileInputStream(filename);
			ois = new ObjectInputStream(fis);
			users = (ArrayList<User>)ois.readObject();
			ois.close();
		}
                catch(FileNotFoundException e) {
                    return users;
                }
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return users;
	}

}
