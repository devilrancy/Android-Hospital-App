package com.example.csc207app;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import type.Patient;
import type.PatientDataBase;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class DoctorUpdateActivity extends Activity {
	// instance variables
	
	/** The text file that PatientDataBase accesses from */
	public static final String FILENAME = "patientdata.txt";

	/** The Patient class that is being monitored throughout the program */
	private Patient patient;

	/** The database that is used to store all of the info */
	private PatientDataBase database;

	@Override
	/**
	 * When the Activity begins, onCreate calls from the parent and loads the 
	 * data from the previous activity.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor_update);
		// Gets the intent passed to it by MainActivity.
		Intent intent = getIntent();
		
		// Create the variables that will be used on this Activity
		patient = (Patient)intent.getSerializableExtra("patientKey");
		database = (PatientDataBase)intent.getSerializableExtra("databaseKey");

	}

	@Override
	/**
	 * This call creates the button on the Activity page.
	 * It is called only once per load.
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items 
		// to the action bar if it is present.
		getMenuInflater().inflate(R.menu.doctor_update, menu);
		return true;
	}

	/**
	 * When called, a Doctor takes a certain Patient and adds a 
	 * Prescription to them. In addition to the Prescription string added,
	 * the Doctor also records the time and date when it was prescribed.
	 * 
	 * @param view A user interface component.
	 */
	public void updatePrescription(View view) {
		Intent intent = new Intent(this, DoctorSelectionActivity.class);
		// Gets the account number from the first EditText field.
		EditText cardNumberText = (EditText) findViewById
				(R.id.patient_cardNumber_field_for_prescription);
		String cardNumber = cardNumberText.getText().toString();

		// Gets the prescription string from the second EditText field.
		EditText prescriptionText = (EditText) findViewById(R.id.prescription);
		String prescription = prescriptionText.getText().toString();

		Patient patient = database.getPatientByCardNumber(cardNumber);

		// Saves all Persons managed by manager to file.
		if (patient != null) { // If the patient exists
			patient.setPrescription(prescription);
			// Adds the three main components that is needed 
			// to create Prescription

		} else {
			String pushInfo = "Cannot find this patient!";
		}
	}

	/**
	 * When called, the Doctor will logout of the triage application and 
	 * all the information (s)he has done to the Patient will be saved onto 
	 * the database file, patients.txt. It will then go on to the first 
	 * Activity, MainActivity.java
	 * 
	 * @param view A user interface component.
	 */
	public void save(View view) {
		if (database != null) // If the database exists
			try {
				FileOutputStream outputStream;
				outputStream = openFileOutput(FILENAME, 0); // choose database
				database.saveToFile(outputStream); // save the info
				String pushInfo = "Patient info updated!";
				Intent intent = new Intent
						(this, DoctorSelectionActivity.class);
				intent.putExtra("databaseKey", database);
				startActivity(intent); // go onto MainActivity
			} catch (FileNotFoundException e) {
				e.printStackTrace(); // to check if the file exists
			}
	}
}
