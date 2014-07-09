package account;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import type.Doctor;
import type.Nurse;
import type.Patient;

public class LoginAccount implements Serializable {

	/** A unique ID. */
	private static final long serialVersionUID = 2251097784839924444L;
	// A map for the accountNumber of a nurse and an object of that Nurse
	Map<String, Nurse> nurseMap;
	Map<String, Doctor> doctorMap;

	/**
	 * Tries to login to an existing Nurse with a database (or create one)
	 * 
	 * @param dir
	 *            The text file that contains all of the passwords for Nurses
	 * @param fileName
	 *            The name of the file that stores the texts
	 * @throws IOException
	 *             Exception if input/output is missing
	 */
	public LoginAccount(File dir, String fileName) throws IOException {
		nurseMap = new HashMap<String, Nurse>();
		doctorMap = new HashMap<String, Doctor>();

		File file = new File(dir, fileName);
		if (file.exists()) {
			this.readFromFile(file.getPath());
		} else
			file.createNewFile();
	}

	/**
	 * Populates the people list using stored data.
	 * 
	 * @param accountNumber
	 *            The username/ID number used to identify Nurse
	 * @param nurse
	 *            The Nurse object that is added to the Map
	 */
	public void addNurse(String accountNumber, Nurse nurse) {
		nurseMap.put(accountNumber, nurse);
		return;
	}

	/**
	 * Populates the people list using stored data.
	 * 
	 * @param accountNumber
	 *            The username/ID number used to identify Doctor
	 * @param doctor
	 *            The Doctor object that is added to the Map
	 */
	public void addDoctor(String accountNumber, Doctor doctor) {
		doctorMap.put(accountNumber, doctor);
		return;
	}

	/**
	 * Login's to a certain Nurse so it can access Patients
	 * 
	 * @param accountNumber
	 *            The ID used to login for the Nurse
	 * @param passWord
	 *            The Nurse's verification to login
	 * @return A Nurse to access for the triage application
	 */
	public Nurse getNurse(String accountNumber, String passWord) {
		Nurse nurse = nurseMap.get(accountNumber);
		if (nurse != null && nurse.verifyPassWord(passWord))
			return nurse;
		else
			return null;
	}

	public Doctor getDoctor(String accountNumber, String passWord) {
		Doctor doctor = doctorMap.get(accountNumber);
		if (doctor != null && doctor.verifyPassWord(passWord))
			return doctor;
		else
			return null;
	}

	/**
	 * Writes the values to file outputStream: one per line, comma separated.
	 * 
	 * @param outputStream
	 *            the output stream to write the records to
	 */
	// modified from lecture notes.
	public void saveToFile(FileOutputStream outputStream) {
		try {
			Iterator<Doctor> itDoc = doctorMap.values().iterator();
			Iterator<Nurse> it = nurseMap.values().iterator();
			// write record info one per line into outputStream
			while (it.hasNext()) {
				// if the value is not empty
				Nurse currentNurse = it.next();
				// go to the next value
				outputStream.write((currentNurse.toString() + "\n").getBytes());
				// save the Nurse info to the file
				System.out.println(currentNurse.toString());
				System.out.println(nurseMap.size());
			}

			while (itDoc.hasNext()) {
				// if the value is not empty
				Doctor currentDoctor = itDoc.next();
				// go to the next value
				outputStream
						.write((currentDoctor.toString() + "\n").getBytes());
				// save the Nurse info to the file
				System.out.println(currentDoctor.toString());
				System.out.println(doctorMap.size());
			}

		} catch (IOException e) {
			// if the Map is empty, call Exception
			e.printStackTrace();
		}
	}

	/**
	 * Scans the file so it can be accessed through codes
	 * 
	 * @param filePath
	 *            The name of the file of which is being accessed
	 * @throws FileNotFoundException
	 *             Exception if file isn't there / created
	 */
	// modified from lecture notes
	private void readFromFile(String filePath) throws FileNotFoundException {

		Scanner scanner = new Scanner(new FileInputStream(filePath));
		String[] record;
		while (scanner.hasNextLine()) {
			// iterates through each line
			record = scanner.nextLine().split("~");
			if (record[0].equals("Doctor"))
				doctorMap.put(record[1], new Doctor(record[1], record[2]));
			else
				nurseMap.put(record[1], new Nurse(record[1], record[2]));
		}
		scanner.close();
	}

//	public static void main(String[] args) {
//		File file = new File("/h/u13/c3/00/c3linhai/csc207/");
//		File file2 = new File("/h/u13/c3/00/c3linhai/csc207/NurseRecord.txt");
//		try {
//			LoginAccount login = new LoginAccount(file, "NurseDoctorRecord.txt");
//			Nurse nurse1 = new Nurse("N1001", "n123456");
//			Nurse nurse2 = new Nurse("N1003", "n654321");
//			Doctor doctor1 = new Doctor("D1001", "123456");
//			login.addNurse("N1001", nurse1);
//			login.addNurse("N1002", nurse2);
//			login.addDoctor("D1001", doctor1);
//
//			FileOutputStream outputStream = new FileOutputStream(file2);
//			login.saveToFile(outputStream);
//
//			Nurse nurseRequested = login.getNurse("N1001", "123456");
//			Nurse nurseRequested2 = login.getNurse("N1001", "n123456");
//			Doctor doctorRequested1 = login.getDoctor("D1001", "123456");
//
//			System.out.println("Get it! 0");
//			if (nurseRequested != null)
//				System.out.println("Get it! N");
//			if (doctorRequested1 != null)
//				System.out.println("Get it! D1");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return;
//	}
}
