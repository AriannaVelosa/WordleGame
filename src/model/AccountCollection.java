package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

/**
 * This class is the collection of WordleAccounts. It allows for adding new
 * accounts, getting accounts, and checking if an account exists or a username
 * is taken. The constructor creates a default account, for testing purposes,
 * may delete later on.
 * 
 * @author Zsavanni Ware and Savannah Rabasa
 */

public class AccountCollection implements Serializable {

	private HashMap<String, WordleAccount> accounts = new HashMap<>();

	/**
	 * This method adds an account to the account collection
	 * 
	 * @param account is the account to add
	 * @param username is the account username
	 */
	public void add(String username, WordleAccount account) {
		accounts.put(username, account);
	}

	/**
	 * This method returns the account collection
	 * 
	 * @return accounts is the account collection
	 */
	public HashMap<String, WordleAccount> getAccounts() {
		return accounts;
	}

	/**
	 * This method returns an existing Wordle account with the given user name.
	 * 
	 * @param username is the user name
	 * @return account if it exists, else returns null
	 */
	public WordleAccount getAccount(String username) {
		if (accounts.containsKey(username)) {
			WordleAccount account = accounts.get(username);
			return account;
		}
		return null;
	}

	/**
	 * This method checks if a user name is take when signing up
	 * 
	 * @param username is the user name
	 * @return true if the name is taken
	 */
	public boolean nameTaken(String username) {
		if (accounts.containsKey(username)) {
			return true;
		}
		return false;
	}

	/**
	 * This method checks if the account collection contains the account with the
	 * given user name and password. If the user name is correct but the password is
	 * not it will return false.
	 * 
	 * @param username is the user name
	 * @param password is the password
	 * @return true if the account exists
	 */
	public boolean hasAccount(String username, String password) {
		if (accounts.containsKey(username)) {
			WordleAccount account = accounts.get(username);
			if (account.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method reads through previously saved account information in order to
	 * allow users to login to their accounts.
	 */
	public void readAccounts() {
		FileInputStream rawBytes;
		try {
			rawBytes = new FileInputStream("accounts.ser");
			ObjectInputStream inFile = new ObjectInputStream(rawBytes);
			// read entire object from file on disk. casts required
			accounts = (HashMap<String, WordleAccount>) inFile.readObject();
			// close input files
			inFile.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * This method is called when a user exits. It saves the account information as
	 * a HashMap to the .ser file.
	 *
	 */
	public void writeAccounts() {
		FileOutputStream bytesToDisk;
		try {
			bytesToDisk = new FileOutputStream("accounts.ser");
			ObjectOutputStream outFile = new ObjectOutputStream(bytesToDisk);
			// outFile understands the write Object message.
			outFile.writeObject(accounts);
			outFile.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	public void deleteAccount(String username) {
		accounts.remove(username);
	}
	
	public void print() {
		for(String name:  accounts.keySet()) {
			WordleAccount acc = accounts.get(name);
			System.out.println(acc.getUsername());
			String pass = accounts.get(name).getPassword();
			System.out.println(pass);
		}
	}
	
	public void updateUsername(String oldUsername,String newUsername, WordleAccount acc) {
		accounts.remove(oldUsername);
		accounts.put(newUsername, acc);
	}

}
