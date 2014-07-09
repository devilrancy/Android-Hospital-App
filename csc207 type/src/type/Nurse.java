package type;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.Serializable;

public class Nurse implements Serializable {

	/** A unique ID. */
	private static final long serialVersionUID = 5596553211825613280L;

	// declaring all of the variables Nurse uses
	private String username, password;
//	private VitalSign vitalSigns;
//	private String name, dob, cardNumber, doctor;
//	private Calendar arrivalTime;
//	private HashMap<String, Patient> patientMap;
	private Patient patient;
	private PatientDataBase database;
//	private Integer bloodPressure, heartRate;
//	private Double temperature;
//	private List<String> symptoms;
	private List<Patient> listOfPatients; 
	private List<String> nonUrgent, lessUrgent, urgent; 
	private Map<String, List<String>> mapOfPatients;
//	private Map<String, String> patientInfo;
	 

	/**
	 * Creates a new Nurse account
	 * @param the nurse's username to login to the triage application
	 * @param the validation key so the Nurse can log in to the program
	 */
	public Nurse(String username, String password) {
		this.username = username;
		this.password = password;
	}


	/**
	 * Assigns a patient to a nurse. Only one Patient can be assigned to 
	 * a nurse at any given time.
	 * @param The Patient's health card number used to assign them to a nurse.
	 */
	public void getCurrentPatient(String cardNumber) {
		this.patient = database.getPatientByCardNumber(cardNumber);
	}

	/*
	 * public void savePatientToDatabase() { //
	 * database.addNewPatient(cardNumber, patient);
	 * database.saveToFile(outputStream); // }
	 * 
	 * public void addNewPatientToDatabase() {
	 * database.addNewPatient(cardNumber, patient); }
	 */

	/**
	 * Sets the current vital signs of the patient 
	 * It adds on to the previous history of vital signs.
	 * @param The patient's blood pressure measured in systolic/diastolic.
	 * @param The patient's temperature measured in celsius.
	 * @param The patient's heart rate.
	 */
	public void setVitalSign(Integer bloodPressure, Double temperature,
			Integer heartRate) {
		this.patient.setVitalSign(bloodPressure, temperature, heartRate);
	}

	// Old design of setting and recording the Patients temperature.

	/*
	 * public void setTemperature(String temperature) {
	 * this.patient.setTemperature(temperature); }
	 * 
	 * public void setBloodPressure(String bloodPressure) {
	 * this.patient.setBloodPressure(bloodPressure); }
	 * 
	 * public void setHeartRate(String heartRate) {
	 * this.patient.setHeartRate(heartRate); }
	 */

	/**
	 * Sets and records the symptoms for the patient.
	 * @param The symptoms the current patient has and the Nurse will record.
	 */
	public void setSymptoms(String symptoms) {
		this.patient.setSymptoms(symptoms);
	}

	/**
	 * Sets the arrival time of the current patient when admitted to the
	 * hospital.
	 * @param The patient's arrival time to the hospital.
	 */
	public void setArrivalTime(Calendar arrivalTime) {
		this.patient.setArrivalTime(arrivalTime);
	}

	/**
	 * Sets the patient info that they have been visited by a doctor.
	 * @param doctor The name of the doctor who diagnosed the Nurse's patient
	 * @param arrivalTime The time when the doctor visited the patient
	 */
//	public void addDoctorVisit(String doctor, Calendar arrivalTime) {
//		this.patient.setDoctor(doctor); // visit doctor (true or false)
////		this.patient.setDoctorTime(arrivalTime); 
//	}
	
	/**
	 * The Nurse acquires a list of Patients based on their urgency.
	 * If the Patient has an urgency level of 1 or less, it is Non Urgent.
	 * If the Patient has an urgency level of 2, it is Less Urgent.
	 * If the Patient has an urgency level of 3, it is Urgent.
	 * The Patient is only added to the list if they have no been visited by
	 * a Doctor according to the Nurse.
	 * @return A hashmap of the urgency level followed by a list of Patients.
	 */
	public Map<String, List<String>> getListByUrgency() { 
		// Only selects Patients based on if they have visited the Doctor.
		// If they have been seen by the Doctor, their name will not appear.
		
		// Initializes the ArrayList's and HashMap used in this method
		listOfPatients = new ArrayList<Patient>(); 
		nonUrgent = new ArrayList<String>(); 
		lessUrgent = new ArrayList<String>(); 
		urgent = new ArrayList<String>();
		mapOfPatients = new HashMap<String, List<String>>();
		
		for (int i = 0; i < listOfPatients.size(); i++) {
			Patient currentPatient = listOfPatients.get(i);
			if (currentPatient.getDoctor() == null) {
				// check if the patient has a doctor ** remember doing this later eric***
				if (currentPatient.getUrgency() == "Non Urgent") {
					nonUrgent.add(currentPatient.getName());
				} // currentPatient.getUrgency returns a String
				
				if (currentPatient.getUrgency() == "Less Urgent") {
					lessUrgent.add(currentPatient.getName());
				} 
				
				if (currentPatient.getUrgency() == "Urgent") {
					urgent.add(currentPatient.getName());
				}
			} // currentPatient.getName returns a String and it is added
			  // to the ArrayList of: [nonUrgent, lessUrgent, or urgent]
		}
		
		mapOfPatients.put("Non Urgent List", nonUrgent);
		mapOfPatients.put("Less Urgent List", lessUrgent);
		mapOfPatients.put("Urgent List", urgent);
		// Adds the respective lists to the string name
		  
		return mapOfPatients;
	}
	 
	  // public List<String> viewListByTime() {  do we need a variable to see
		  // how long they have been here? 
	  // }
	 
	
	/**
	 * Returns a patient given the health card number.
	 * @param The card number identification for a patient. 
	 * It is used to identify a patient in the database.
	 * @return A patient with the given health card number.
	 */
	public Patient viewPatientByCard(String cardNumber) {
		return database.getPatientByCardNumber(cardNumber);
	}

	/**
	 * Return the current patient's data.
	 * @return The current Patient's data.
	 */
	public String viewPatientData() {
		return this.patient.getPatientInfo();
	}

	/**
	 * Return whether the Nurse's password for the login name is correct
	 * @return The boolean value if the password matches the password
	 * in the passwords.txt file
	 */
	// verify if the given password is correct.
	public boolean verifyPassWord(String password) {
		if (this.password.equals(password))
			return true;
		else
			return false;
	}

	/**
	 * Gets the information of the nurse
	 * @return The login info of the Nurse in format: Nurse~username~password
	 */
	public String toString() {
		return "Nurse" + "~" + username + "~" + password;

	}
}