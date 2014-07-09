 
package type;

import java.io.Serializable;

public class Doctor implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -867153217061388477L;
	private String username, password;
	private PatientDataBase database;
	private Patient patient;
	/**
	 * Creates a new Doctor account
	 * @param the doctor's username to login to the triage application
	 * @param the validation key so the Doctor can log in to the program
	 */
	public Doctor(String username, String password) {
		this.username = username;
		this.password = password;
		
	}
	
	/**
	 * Assigns a Patient to a Doctor. Only one Patient can be assigned to 
	 * a Doctor at any given time.
	 * @param The Patient's health card number used to assign them to a Doctor.
	 */
	public void getCurrentPatient(String cardNumber) {
		this.patient = database.getPatientByCardNumber(cardNumber);
	}
	
	/**
	 * Returns a Patient given the health card number.
	 * @param The card number identification for a patient. 
	 * It is used to identify a patient in the database.
	 * @return A Patient with the given health card number.
	 */
	public Patient viewPatientByCard(String cardNumber) {
		return database.getPatientByCardNumber(cardNumber);
	}
	
	
	/**
	 * Gets the information of the Doctor.
	 * @return A Doctor with their login credentials
	 */
	public String toString() {
		return "Doctor" + "~" + username + "~" + password;

	}
	
	public boolean verifyPassWord(String password) {
		if (this.password.equals(password))
			return true;
		else
			return false;
	}

}
