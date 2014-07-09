package com.example.csc207app;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import type.Nurse;
import type.Patient;
import type.PatientDataBase;
import account.LoginAccount;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/** The second activity. */
public class NurseUpdateActivity extends Activity {

	// The file with all of the Patient's info.
	public static final String FILENAME = "patientdata.txt";
	// The PatientDataBase that holds and record all of the info
	private PatientDataBase database;
	// The Patient with info and is sent to the text file
	private Patient patient;

	@Override
	/**
	 * The button that takes all of the info from the text field
	 * and transfers them to other classes in order to create Patient.
	 * This gives them attributes such as 
	 * Name, ID, DOB, Vital Signs and symptoms
	 * 
	 * @param The state of the instance that will be loaded
	 */
	// Modified from the lecture notes
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nurse_update);

		// Gets the intent passed to it by MainActivity.
		Intent intent = getIntent();
		// Gets the Nurse object from the intent.
		database = (PatientDataBase) intent.getSerializableExtra
				("databaseKey");

	}

	@Override
	/**
	 * Starts up the contents in the activity and the options menu
	 * @param The option menu to bring up
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar 
		// if it is present.
		getMenuInflater().inflate(R.menu.nurse_update, menu);
		return true;
	}

	/**
	 * Chooses a certain Patient and allows the edit of the Patient's
	 * attributes. It reads the data from patientdata.txt 
	 * and modifies a certain Patient object.
	 * 
	 * @param A user interface component
	 */
	public void loadPatient(View view) {
		Intent intent = getIntent();
		// Gets the account number from the first EditText field.
		EditText cardNumberText = (EditText) findViewById
				(R.id.patient_number_field1);
		String cardNumber = cardNumberText.getText().toString();

		Patient patient = database.getPatientByCardNumber(cardNumber);
		// Saves all Persons managed by manager to file.
		if (patient != null) { // If the patient is empty
			intent = new Intent(this, UpdateActivity.class);
			// Adds the three main components that is needed to create Patient
			intent.putExtra("patientKey", patient);
			intent.putExtra("cardNumberKey", cardNumber);
			intent.putExtra("databaseKey", database);
			startActivity(intent);
		} else {
			String pushInfo = "Cannot find this patient!";
		}
	}

	/**
	 * When the button is pressed it creates a Patient given the Healthcard ID,
	 * Name, and DOB. It can then be used to modify the vital signs and/or
	 * symptoms
	 * 
	 * @param A user interface component
	 */
	public void addPatient(View view) {

		// Gets the account number from the first EditText field.
		EditText cardNumberText = (EditText) findViewById
				(R.id.patient_number_field2);
		String cardNumber = cardNumberText.getText().toString();

		// Gets the patient name from the second EditText field.
		EditText patientNameText = (EditText) findViewById
				(R.id.patient_name_field);
		String patientName = patientNameText.getText().toString();

		// Gets the DOB from the third EditText field.
		EditText dobText = (EditText) findViewById(R.id.dob_field);
		String dob = dobText.getText().toString();

		// Creates a new Patient with the given info
		patient = new Patient(patientName, dob, cardNumber,
				Calendar.getInstance());
		if (patient != null) {
			database.addNewPatient(cardNumber, patient);

			//String pushInfo = "New patient added!";

		}
	}

	/**
	 * Adds the time the doctor visited the patient
	 * @param The view interface components and widgets
	 */
	public void addVisit(View view) {

		EditText cardNumberText = (EditText) findViewById
				(R.id.patient_number_field_for_doctor_visit);
		String cardNumber = cardNumberText.getText().toString();

		EditText dateText = (EditText) findViewById
				(R.id.date_field_for_doctor_visit);
		String date = dateText.getText().toString();

		// Creates a new Patient with the given info
		patient = database.getPatientByCardNumber(cardNumber);

		if (patient != null) {
			patient.hasBeenSeen(date);
			// Saves all Persons managed by manager to file.
			String pushInfo = "New patient added!";
		}
	}

	/**
	 * Saves all of the data changed by the nurse into the patient record
	 * @param The view interface components and widgets
	 */
	public void save(View view) {
		if (database != null)
			try {
				FileOutputStream outputStream;
				outputStream = openFileOutput(FILENAME, 0);
				database.saveToFile(outputStream);
				String pushInfo = "Patient info updated!";
				Intent intent = new Intent(this, NurseSelectionActivity.class);
				intent.putExtra("databaseKey", database);
				startActivity(intent);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	}
}
