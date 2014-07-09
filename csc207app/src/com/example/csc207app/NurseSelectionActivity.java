package com.example.csc207app;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import type.Patient;
import type.PatientDataBase;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class NurseSelectionActivity extends Activity {
	private Patient patient;
	private PatientDataBase database;
	private String cardNumber;

	@Override
	/**
	 * Initializes activity and sets the layout
	 * @param The state of the instance that will be loaded
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nurse_selection);
		Intent intent = getIntent();
		database = (PatientDataBase) intent.getSerializableExtra
				("databaseKey");
	}

	@Override
	/**
	 * Starts up the contents in the activity and the options menu
	 * @param The option menu to bring up
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the 
		// action bar if it is present.
		getMenuInflater().inflate(R.menu.nurse_selection, menu);
		return true;
	}

	/**
	 * Looks up the patient based on the card number and brings out the
	 * information about it if the program find him/her
	 * @param The view interface components and widgets
	 */
	public void nurseView(View view) {
		Intent viewIntent = new Intent(this, NurseViewActivity.class);

		EditText cardNumberText = (EditText) findViewById
				(R.id.patient_card_number_field_in_nurse_selection);
		String cardNumber = cardNumberText.getText().toString();

		Patient patient = database.getPatientByCardNumber(cardNumber);

		if (patient != null) {
			viewIntent.putExtra("patientKey", patient);
			startActivity(viewIntent);
		} else {
			String pushInfo = "Cannot find this patient!";
		}
	}

	/**
	 * Brings up the screen to update the patient
	 * @param The view interface components and widgets
	 */
	public void nurseUpdate(View view) {
		Intent updateIntent = new Intent(this, NurseUpdateActivity.class);
		updateIntent.putExtra("databaseKey", database);
		startActivity(updateIntent);
	}

	public void showNotSeenPatient(View view) {
		List<Patient> patients = database.getPatientByUrgency();

		String patientsString = new String();
		if (patients != null) {
			Iterator<Patient> it = patients.iterator();
			while (it.hasNext()) {
				Patient currentPatient = it.next();
				patientsString += currentPatient.infoDetails();
			}

			Intent viewIntent = new Intent(this,
					NurseUrgentPatientActivity.class);
			viewIntent.putExtra("patientKey", patientsString);
			startActivity(viewIntent);
		}
	}

	/**
	 * Logs out the nurse and returns to the main screen
	 * @param The view interface components and widgets
	 */
	public void nurseLogout(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
}
