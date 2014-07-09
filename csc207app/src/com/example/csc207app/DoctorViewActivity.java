package com.example.csc207app;

import type.Patient;
import type.PatientDataBase;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class DoctorViewActivity extends Activity {
	
	/** The Patient class that is being monitored throughout the program */
	private Patient patient;
	
	@Override
	/**
	 * When the Activity begins, onCreate calls from the parent and loads the 
	 * data from the previous activity.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nurse_view);
		// Gets the intent passed to it by MainActivity.
		Intent intent = getIntent();
		patient = (Patient) intent.getSerializableExtra("patientKey");
		// Access all of the Patient's infos
		TextView showPatientInfo = (TextView) findViewById
				(R.id.showPatientInfo);
		// Collectively combine all of the Patient's info so the Doctor can see
		showPatientInfo.setText(patient.infoDetails()
				+ patient.vitalSignsInfo()
				+ patient.prescriptionInfo());
		
	}

	@Override
	/**
	 * This call creates the button on the Activity page.
	 * It is called only once per load.
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; 
		// this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nurse_view, menu);
		return true;
	}

	
	
}
