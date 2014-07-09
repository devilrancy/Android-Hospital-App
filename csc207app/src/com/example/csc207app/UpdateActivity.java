package com.example.csc207app;

import java.util.Calendar;

import type.Nurse;
import type.Patient;
import type.PatientDataBase;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class UpdateActivity extends Activity {
	private Patient patient;
	private PatientDataBase database;
	private String cardNumber;

	@Override
	/**
	 * Initializes activity
	 * Gets the patient, cardnumber and database from previous activity
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);
		Intent intent = getIntent();
		patient = (Patient) intent.getSerializableExtra("patientKey");
		cardNumber = (String) intent.getSerializableExtra("cardNumberKey");
		database = (PatientDataBase) intent.getSerializableExtra("databaseKey");
	}

	/**
	 * Inflates the menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.update, menu);
		return true;
	}

	/**
	 * Updates the patient from the values on the fields
	 * 
	 * @param view
	 *            A user interface component
	 */
	public void updatePatient(View view) {

		// Gets the account number from the first EditText field.
		EditText bloodPressureText = (EditText) findViewById(R.id.blood_pressure_field);
		String bloodPressure = bloodPressureText.getText().toString();
		EditText temperatureText = (EditText) findViewById(R.id.temperature_field);
		String temperature = temperatureText.getText().toString();

		EditText heartRateText = (EditText) findViewById(R.id.heart_rate_field);
		String heartRate = heartRateText.getText().toString();

		if (bloodPressure.equals("") || temperature.equals("")
				|| heartRate.equals(""))
			return;
		if (patient != null) {
			patient.setVitalSign(Integer.parseInt(bloodPressure),
					Double.parseDouble(temperature),
					Integer.parseInt(heartRate));
			database.addNewPatient(cardNumber, patient);

			String pushInfo = "Patient info updated!";
			Intent intent = new Intent(this, NurseUpdateActivity.class);
			intent.putExtra("patientKey", patient);
			intent.putExtra("databaseKey", database);
			startActivity(intent);
		}
	}

	/**
	 * Returns to the previous operation activity
	 * 
	 * @param view
	 *            A user interface component
	 */
	public void goBackToOperation(View view) {
		String pushInfo = "Patient info updated!";
		Intent intent = new Intent(this, OperationActivity.class);
		intent.putExtra("patientKey", patient);
		intent.putExtra("databaseKey", database);
		startActivity(intent);
	}
}