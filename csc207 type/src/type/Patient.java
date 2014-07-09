package type;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

;

public class Patient implements Serializable {

	private static final long serialVersionUID = 2712187085688936282L;
	// instance variables
	private String name;
	private String dob;
	private String cardNumber;
	private Calendar arrivalTime;
	private VitalSign vitalSigns;
	private List<String> symptoms;
	private String patientInfo;
	private String age;
	private String urgency;
	private Doctor doctor;
	private double temperature;
	private int heartRate;
	private int bloodPressure;
	private Prescription prescriptions;
	private String seenByDoctor;

	private char bloodPressureUnit;
	private static final DateFormat dateFormat = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");

	// /**
	// * Creates a new patient with all of their information
	// *
	// * @param The
	// * name of the patient
	// * @param The
	// * date of birth of the patient (mmddyy)
	// * @param The
	// * patient's health card number
	// * @param The
	// * patient's arrival time at the hospital
	// * @param The
	// * patient's vital signs
	// */
	// public Patient(String name, String dob, String cardNumber,
	// Calendar arrivalTime, VitalSign vitalSign) {
	// this.name = name;
	// this.dob = dob;
	// this.cardNumber = cardNumber;
	// this.arrivalTime = arrivalTime;
	// this.symptoms = new LinkedList<String>();
	// this.vitalSigns = vitalSign;
	// this.prescriptions = new Prescription();
	// setAge();
	// this.hasVitalSign = true;
	// return;
	// }

	/**
	 * Second constructor used if vital signs have not been recorded
	 * 
	 * @param The
	 *            patient's name
	 * @param The
	 *            patient's date of birth (mmddyyyy)
	 * @param The
	 *            patient's health card id
	 * @param The
	 *            patient's arrival time at the hospital
	 */
	public Patient(String name, String dob, String cardNumber,
			Calendar arrivalTime) {
		super();
		this.name = name;
		this.dob = dob;
		this.cardNumber = cardNumber;
		this.arrivalTime = arrivalTime;
		this.vitalSigns = new VitalSign();
		this.prescriptions = new Prescription();
		this.urgency = "Non Urgent";
		this.seenByDoctor = "Not seen by doctor";
		this.setAge();
		this.setUrgency();
	}

	public Patient(String name2, String dob2, String cardNumber2,
			Calendar arrivalTime2, String seenByDoc, VitalSign vitalSign,
			Prescription prescription) {
		this.name = name2;
		this.dob = dob2;
		this.cardNumber = cardNumber2;
		this.arrivalTime = arrivalTime2;
		this.seenByDoctor = seenByDoc;
		this.symptoms = new LinkedList<String>();
		this.vitalSigns = vitalSign;
		this.prescriptions = prescription;
		this.setAge();
		this.setUrgency();

		return;
	}

	/**
	 * Sets the vital signs of the patient
	 * 
	 * @param The
	 *            updated blood pressure to set
	 * @param The
	 *            updated temperature to set
	 * @param The
	 *            updated heart rate to set
	 */
	public void setVitalSign(Integer newBloodPressure, Double newTemperature,
			Integer newHeartRate) {
		vitalSigns.addBloodPressure(newBloodPressure);
		vitalSigns.addHeartRate(newHeartRate);
		vitalSigns.addTemperature(newTemperature);
		vitalSigns.addTimeStamp(Calendar.getInstance());
		// this.hasVitalSign = true;
		setAge();
		setUrgency();
		return;
	}

	/**
	 * Updates the symptoms of the patient
	 * 
	 * @param The
	 *            new symptom recording to set
	 */
	public void setSymptoms(String newSymptoms) {
		symptoms.add(newSymptoms);
		return;
	}

	public void hasBeenSeen(String time) {
		seenByDoctor = "Has been seen by doctor on " + time;
		return;
	}

	public void setNotBeenSeen() {
		seenByDoctor = "Not seen by doctor";
		return;
	}

	/**
	 * Gets all of the symptoms from the patient
	 * 
	 * @return All of the patient's symptoms
	 */
	public List<String> getAllSymptoms() {
		return symptoms;
	}

	/**
	 * Displays the information of the patient
	 * 
	 * @return The patient's info
	 */
	public String toString() {
		return name + "~" + dob + "~" + cardNumber + "~"
				+ dateFormat.format(arrivalTime.getTime()) + "~"
				+ this.seenByDoctor + "~" + vitalSigns.toString()
				+ prescriptions.toString();
	}

	/**
	 * Creates a patient out of the scanned information
	 * 
	 * @param The
	 *            scanned info from the file
	 * @return A new patient with the information
	 */
	public static Patient scan(String[] input) {
		Patient patient;

		Calendar arrivalTime = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			String name = input[0];
			String dob = input[1];
			String cardNumber = input[2];
			arrivalTime.setTime(format.parse(input[3]));

			String seenByDoc = input[4];
			VitalSign vitalSign = new VitalSign();
			Prescription prescription = new Prescription();
			if (!input[5].equals("null")) {
				String bloodPressureString = input[5].substring(1,
						input[5].length() - 1);
				String temperatureString = input[6].substring(1,
						input[6].length() - 1);
				String heartRateString = input[7].substring(1,
						input[7].length() - 1);
				String timeStampsString = input[8].substring(1,
						input[8].length() - 1);
				List<Integer> bloodPressure = new ArrayList<Integer>();
				List<Double> temperature = new ArrayList<Double>();
				List<Integer> heartRate = new ArrayList<Integer>();
				List<Calendar> timeStamps = new ArrayList<Calendar>();
				Calendar timeStamp = Calendar.getInstance();
				// transform the string into Integer/double/calendar
				for (String str : Arrays.asList(bloodPressureString
						.split("\\s*,\\s*")))
					bloodPressure.add(Integer.parseInt(str));
				for (String str : Arrays.asList(temperatureString
						.split("\\s*,\\s*")))
					temperature.add(Double.parseDouble(str));
				for (String str : Arrays.asList(heartRateString
						.split("\\s*,\\s*")))
					heartRate.add(Integer.parseInt(str));
				for (String str : Arrays.asList(timeStampsString
						.split("\\s*,\\s*"))) {
					timeStamp.setTime(format.parse(str));
					timeStamps.add(timeStamp);

					// create the vital sign of this patient
					vitalSign = new VitalSign(bloodPressure, temperature,
							heartRate, timeStamps);
				}
			}
			if (!input[9].equals("null")) {
				String prescriptionString = input[9].substring(1,
						input[9].length() - 1);
				String prescriptionTimeStampsString = input[10].substring(1,
						input[10].length() - 1);

				List<String> prescriptionList = new ArrayList<String>();
				List<Calendar> precriptionTimeStamps = new ArrayList<Calendar>();
				Calendar prescriptiontimeStamp = Calendar.getInstance();
				// transform the string into Integer/double/calendar
				for (String str : Arrays.asList(prescriptionString
						.split("\\s*,\\s*")))
					prescriptionList.add(str);

				for (String str : Arrays.asList(prescriptionTimeStampsString
						.split("\\s*,\\s*"))) {
					prescriptiontimeStamp.setTime(format.parse(str));
					precriptionTimeStamps.add(prescriptiontimeStamp);
					//
					// System.out.println(prescriptiontimeStamp.toString());
				}

				// create the vital sign of this patient
				prescription = new Prescription(prescriptionList,
						precriptionTimeStamps);
				// System.out.println(precriptionTimeStamps.size());
				// System.out.println(prescription.toString());
			}
			patient = new Patient(name, dob, cardNumber, arrivalTime,
					seenByDoc, vitalSign, prescription);

			return patient;

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the information of the patient
	 * 
	 * @return The general information of the patient
	 */
	public String getPatientInfo() {
		return patientInfo;
	}

	/**
	 * Sets the general information of the patient
	 * 
	 * @param The
	 *            general information of the patient to set
	 */
	public void setPatientInfo(String patientInfo) {
		this.patientInfo = patientInfo;
	}

	/**
	 * Gets the name of the patient
	 * 
	 * @return The patient's first name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the patient
	 * 
	 * @param The
	 *            patient's first name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the date of birth of the patient
	 * 
	 * @return The patients date of birth (mmddyyyy)
	 */
	public String getDob() {
		return dob;
	}

	/**
	 * Sets the date of birth of the patient
	 * 
	 * @param The
	 *            patients date of birth (mmddyyyy) to set
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}

	/**
	 * Gets the health card number of the patient
	 * 
	 * @return The patient's health card number
	 */
	public String getId() {
		return cardNumber;
	}

	/**
	 * Sets the patient's health card
	 * 
	 * @param The
	 *            patient's health card number to set
	 */
	public void setId(String id) {
		this.cardNumber = id;
	}

	/**
	 * Gets the patient's arrival time at the hospita;
	 * 
	 * @return The patient's arrival time at the hospital
	 */
	public Calendar getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * Sets the patient's arrival time at the hospital
	 * 
	 * @param The
	 *            patient's arrival time at the hospital to set
	 */
	public void setArrivalTime(Calendar arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	/**
	 * Gets the age of the patient
	 * 
	 * @return The patient's age
	 */
	public String getAge() {
		return age;
	}

	/**
	 * Gets the patient's blood pressure unit of measurement
	 * 
	 * @return The patient's blood pressure unit of measurement
	 */
	public char getBloodPressureUnit() {
		return bloodPressureUnit;
	}

	/**
	 * Sets the patient's blood pressure unit of measurement
	 * 
	 * @param The
	 *            patient's blood pressure unit of measurement to set
	 */
	public void setBloodPressureUnit(char bloodPressureUnit) {
		this.bloodPressureUnit = bloodPressureUnit;
	}

	/**
	 * Gets the urgency of the patient's treatment
	 * 
	 * @return The urgency of the patient's treatment
	 */
	public String getUrgency() {
		return urgency;
	}

	/**
	 * Gets the patient's doctor
	 * 
	 * @return The patient's doctor
	 */
	public Doctor getDoctor() {
		return doctor;
	}

	/**
	 * Sets the patient's doctor
	 * 
	 * @param The
	 *            patient's doctor to assign
	 */
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	/**
	 * Gets the patient's prescription
	 * 
	 * @return The patient's description
	 */
	public Prescription getPrescription() {
		return this.prescriptions;
	}

	/**
	 * Sets the patient's precription
	 * 
	 * @param The
	 *            patient's prescription to set
	 */
	public void setPrescription(String prescription) {
		this.prescriptions.addPrescription(prescription);
		this.prescriptions.addTimeStamp(Calendar.getInstance());
	}

	/**
	 * Calculates the age of the patient from their date of birth and
	 * automatically sets it
	 */
	public void setAge() {
		Calendar current = Calendar.getInstance();

		int month = (Integer.parseInt(dob.charAt(0) + "") * 10)
				+ Integer.parseInt(dob.charAt(1) + "");
		int day = (Integer.parseInt(dob.charAt(2) + "") * 10)
				+ Integer.parseInt(dob.charAt(3) + "");
		int year = (Integer.parseInt(dob.charAt(4) + "") * 10)
				+ (Integer.parseInt(dob.charAt(5) + ""));

		if (year + 2000 > current.get(Calendar.YEAR) - year) {
			year = year + 1900;
		} else {
			year = year + 2000;
		}

		if (current.get(Calendar.MONTH) > month) {
			age = (current.get(Calendar.YEAR) - year) + "";
		} else if (current.get(Calendar.MONTH) < month) {
			age = (current.get(Calendar.YEAR) - year) - 1 + "";
		} else {
			if (current.get(Calendar.DATE) >= day) {
				age = (current.get(Calendar.YEAR) - year) + "";
			} else if (current.get(Calendar.DATE) < day) {
				age = (current.get(Calendar.YEAR) - year) - 1 + "";
			}
		}
	}

	/**
	 * Shows all the data of the patient
	 * 
	 * @return The full data of the patient
	 */
	// public String showAllData(){
	//
	// String chain;
	//
	// List<Double> allTemperatures = vitalSigns.showAllTemperatures();
	//
	// List<Integer> allBloodPressures = vitalSigns.showAllBloodPressures();
	//
	// List<Integer> allHeartRates = vitalSigns.showAllHeartRates();
	//
	// for(int i = 0; i < allTemperatures.size(); i++){
	// chain = chain + allTemperatures.get(i) + "$";
	// chain = chain + allBloodPressures.get(i) + "$";
	// chain = chain + allHeartRates.get(i) + "$";
	// chain = chain + toString();
	// }
	// }

	public boolean isSeenByDoctor() {
		if (this.seenByDoctor.equals("Not seen by doctor"))
			return false;
		else
			return true;
	}

	/**
	 * Calculates the patient's urgency and automatically sets it
	 */
	public void setUrgency() {
		int priority = 0;
		if (vitalSigns.isEmpty()) {
			System.out.println("The vital sign is empty");
			urgency = "Non Urgent";
			return;
		}

		List<Double> allTemperatures = vitalSigns.getAllTemperature();
		this.temperature = allTemperatures.get(allTemperatures.size() - 1);

		List<Integer> allBloodPressures = vitalSigns.getAllBloodPressure();
		this.bloodPressure = allBloodPressures
				.get(allBloodPressures.size() - 1);

		List<Integer> allHeartRates = vitalSigns.getAllHeartRate();
		this.heartRate = allHeartRates.get(allHeartRates.size() - 1);

		if (Integer.parseInt(age) < 2) {
			priority++;
		}
		if (temperature >= 39) {
			priority++;
		}
		if (bloodPressureUnit == 's') {
			if (bloodPressure >= 140) {
				priority++;
			}
		} else {
			if (bloodPressure >= 90) {
				priority++;
			}
		}
		if (heartRate >= 100 || heartRate <= 50) {
			priority++;
		}

		if (priority <= 1) {
			urgency = "Non Urgent";
		} else if (priority == 2) {
			urgency = "Less Urgent";
		} else {
			urgency = "Urgent";
		}

	}

	/**
	 * Combines all of the Patient's info into multiple lines and creates it on
	 * a single string.
	 * 
	 * @return The info of the Patient in one string but multiple lines: The
	 *         format should look like: Name: name DOB: dob Card Number:
	 *         cardNumber Arrival Time: [Time Stamp] username has seen/notseen
	 *         the doctor. Non Urgent / Less Urgent / Urgent
	 */
	public String infoDetails() {
		String info = this.toString();
		String[] details = info.split("~");
		String combined;
		combined = "\nName: " + details[0] + "\nDOB: " + details[1]
				+ "\nCard Number: " + details[2] + "\nArrival Time: "
				+ details[3] + "\n" + details[0] + " " + this.seenByDoctor
				+ "." + "\n" + this.getUrgency() + "\n";
		return combined;
	}

	/**
	 * Returns the string of the Vital Signs on multiple lines
	 * 
	 * @return The Vital Signs of the Patient on one string but multiple lines:
	 *         The format should look like: Blood Pressure: [List of past and
	 *         current Blood Pressure levels] Heart Rate: [List of past and
	 *         current Heart Rate levels] Temperature: [List of past and current
	 *         Temperature levels] Time Recorded: [List of Time Stamps]
	 */
	public String vitalSignsInfo() {
		return vitalSigns.showAllVitalSigns();
	}

	/**
	 * Combines the Prescription and the time it was recorded into a single
	 * string but with multiple lines and returns it
	 * 
	 * @return A string that combines the many Prescriptions and time it was
	 *         recorded The format should look like: Prescription: [List of
	 *         Prescription] Time Recorded: [Time Stamp]
	 */
	public String prescriptionInfo() {
		return prescriptions.showPrescriptionList();
	}

	// public static void main(String[] args) {
	// Patient joe = new Patient("joe", "10", "123", Calendar.getInstance());
	// System.out.println(joe);
	// }
}
