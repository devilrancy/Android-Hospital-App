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

public class DoctorSelectionActivity extends Activity {
	// instance variables

	/** The text file that PatientDataBase accesses from */
	public static final String FILENAME = "patientdata.txt";

	/** The Patient class that is being monitored throughout the program */
	private Patient patient;

	/** The database that is used to store all of the info */
	private PatientDataBase database;

	/** The object used to identify and select certain Patients */
	private String cardNumber;

	@Override
	/**
	 * When the Activity begins, onCreate calls from the parent and loads the 
	 * data from the previous activity.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor_selection);
		// Gets the intent passed to it by MainActivity.
		Intent intent = getIntent();

		// When opened to DoctorSelectionActivity,
		// creates the variables that will be used on this Activity
		patient = (Patient) intent.getSerializableExtra("patientKey");
		cardNumber = (String) intent.getSerializableExtra("cardNumberKey");
		database = (PatientDataBase)intent.getSerializableExtra("databaseKey");
	}

	@Override
	/** 
	 * This call creates the button on the Activity page.
	 * It is called only once per load.
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; 
		// this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.doctor_selection, menu);
		return true;
	}

	/**
	 * An activity method that once called, the Doctor is able to look up a
	 * certain Patient and is able to edit the Prescription 
	 * in the next Activity screen.
	 * 
	 * @param view A user interface component.
	 */
	public void doctorView(View view) {
		Intent viewIntent = new Intent(this, DoctorViewActivity.class);

		// Gets the account number from the first EditText field.
		EditText cardNumberText = (EditText) findViewById
				(R.id.patient_card_number_field_in_doctor_selection);
		// Creates the cardNumber variable based on the text field
		String cardNumber = cardNumberText.getText().toString();
		// Gets a Patient's data based on the cardNumber
		Patient patient = database.getPatientByCardNumber(cardNumber);

		if (patient != null) { // The Patient exists
			viewIntent.putExtra("patientKey", patient); 
			startActivity(viewIntent);
			// Goes to the next page to where they can view the Patient info
		} else { // Else if the Patient does not exists
			String pushInfo = "Cannot find this patient!";
		}
	}

	/**
	 * When called, the Doctor is then going to the next Activity screen,
	 * DoctorUpdateActivitiy.class, and there the Doctor is able to give
	 * a Prescription to the Patient and records the time he prescribed it.
	 * 
	 * @param view A user interface component
	 */
	public void doctorUpdate(View view) {
		Intent updateIntent = new Intent(this, DoctorUpdateActivity.class);
		updateIntent.putExtra("databaseKey", database);
		// Access the Patient and move on to the next screen
		startActivity(updateIntent);
	}

	/**
	 * This saves all of the info that the Doctor has done to patient.txt. Once
	 * the button is pressed, the screen changes to the MainActivity.class.
	 * There is a message that let's you know that the changes to the Patient
	 * has been made as a String will pop up notifying you.
	 * 
	 * @param view A user interface component
	 */
	public void doctorSave(View view) {
		if (database != null) // check if the database file doesn't exist
			try {
				FileOutputStream outputStream;
				outputStream = openFileOutput(FILENAME, 0);
				database.saveToFile(outputStream); // save the info to the file
				String pushInfo = "Patient info updated!";
				Intent intent = new Intent(this, MainActivity.class);
				// After the info is saved, it moves to the main screen
				startActivity(intent);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} // Error check in case file isn't found
	}
}
