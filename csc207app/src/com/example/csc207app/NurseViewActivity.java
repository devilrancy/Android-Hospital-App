package com.example.csc207app;

import type.Patient;
import type.PatientDataBase;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class NurseViewActivity extends Activity {
	private Patient patient;
	
	@Override
	/**
	 * Initializes activity and sets the layout
	 * @param The state of the instance that will be loaded
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nurse_view);
		Intent intent = getIntent();
		patient =(Patient) intent.getSerializableExtra("patientKey");
		TextView showPatientInfo = (TextView) findViewById
				(R.id.showPatientInfo);
		showPatientInfo.setText(patient.infoDetails()+
				patient.vitalSignsInfo()+patient.prescriptionInfo());
		
	}

	@Override
	/**
	 * Starts up the contents in the activity and the options menu
	 * @param The option menu to bring up
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the 
		// action bar if it is present.
		getMenuInflater().inflate(R.menu.nurse_view, menu);
		return true;
	}

	
	
}
