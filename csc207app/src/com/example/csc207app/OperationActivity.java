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
public class OperationActivity extends Activity {

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
	 */
	// Modified from the lecture notes
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_operation);

		// Gets the intent passed to it by MainActivity.
		Intent intent = getIntent();
		// Gets the Nurse object from the intent.
		database = (PatientDataBase) intent.getSerializableExtra("databaseKey");
		String accountNumber = (String) intent
				.getSerializableExtra("accountNumberKey");
		// Sets the TextView to the name of Person person.

	}

	@Override
	// Modified from the lecture notes
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.operation, menu);
		return true;
	}

	/**
	 * Chooses a certain Patient and allows the edit of the Patient's
	 * attributes. It reads the data from patientdata.txt and modifies a certain
	 * Patient object.
	 * 
	 * @param view
	 *            A user interface component
	 */
	public void loadPatient(View view) {
		Intent intent = getIntent();
		// Gets the account number from the first EditText field.
		EditText cardNumberText = (EditText) findViewById(R.id.patient_number_field1);
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
	 * @param view
	 *            A user interface component
	 */
	public void addPatient(View view) {

		// Gets the account number from the first EditText field.
		EditText cardNumberText = (EditText) findViewById(R.id.patient_number_field2);
		String cardNumber = cardNumberText.getText().toString();

		// Gets the patient name from the second EditText field.
		EditText patientNameText = (EditText) findViewById(R.id.patient_name_field);
		String patientName = patientNameText.getText().toString();

		// Gets the DOB from the third EditText field.
		EditText dobText = (EditText) findViewById(R.id.dob_field);
		String dob = dobText.getText().toString();

		// Creates a new Patient with the given info
		patient = new Patient(cardNumber, patientName, dob,
				Calendar.getInstance());
		if (patient != null) {
			database.addNewPatient(cardNumber, patient);

			// Saves all Persons managed by manager to file.
			String pushInfo = "New patient added!";
			// Sets the TextView to the name of Person person.

		}
	}

	/**
	 * Once the info for the Patient is given with the vital signs, the Nurse
	 * can logout of the triage application whilst adding all of the information
	 * of the Patient to patientdata.txt
	 * 
	 * @param view
	 *            A user interface component
	 */
	public void logOut(View view) {
		if (database != null)
			try {
				FileOutputStream outputStream;
				outputStream = openFileOutput(FILENAME, 0);
				database.saveToFile(outputStream);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	}

}
