package com.example.csc207app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import type.Doctor;
import type.Nurse;
import type.PatientDataBase;
import account.LoginAccount;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	/** The LoginAccount used to access the Nurse class for the application */
	private LoginAccount loginAccount;
	
	/** the file that holds the login info for the Nurse */
	public static final String FILENAME = "passwords.txt";


	@Override
	/**
	 * When the Activity begins, onCreate calls from the parent and loads the 
	 * data from the previous activity.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try {
			loginAccount = new LoginAccount(this.getApplicationContext()
					.getFilesDir(), FILENAME);
			System.out.println(this.getApplicationContext().getFilesDir()
					.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	/**
	 * This call creates the button on the Activity page.
	 * It is called only once per load.
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; 
		// this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	/**
	 * Reads data from the Nurse login text file and logins to the Nurse class
	 * then passes the Nurse to the next activity, OperationActivity
	 * @param view A user interface component.
	 */
	// modified from registerPerson (lecture notes)
	public void nurseLogin(View view) {

		// An Intent is an operation to be performed. In this case,
		// the operation is to start a new activity. The activity will
		// be started later in this method when startActivity is called.

		Intent intent = new Intent(this, NurseSelectionActivity.class);

		// Gets the account number from the first EditText field.
		EditText accountNumberText = (EditText) findViewById
				(R.id.account_number_field);
		String accountNumber = accountNumberText.getText().toString();

		// Gets the password from the second EditText field.
		EditText passwordText = (EditText) findViewById(R.id.password_field);
		String password = passwordText.getText().toString();

		// Get the Nurse according to the accountNumber and password.
		Nurse nurse = loginAccount.getNurse(accountNumber, password);
		if (nurse != null) {
			PatientDataBase database;
			try {
				database = new PatientDataBase(this.getApplicationContext()
						.getFilesDir(), "patientdata.txt");
				intent.putExtra("nurseKey", nurse);
				intent.putExtra("accountNumberKey", accountNumber);
				intent.putExtra("databaseKey", database);
				// Start DisplayActivity, passing extras (the Patient object)
				// via the intent.
				startActivity(intent);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	}
	
	/**
	 * The call that allows the Doctor to login to the triage application.
	 * Using the Doctor's credentials, by selecting on the "login as Doctor"
	 * button will it only allow the Doctor to login.
	 * It reads the data from the passwords.txt file and if true it will
	 * continue to the next activity, OperationActivity.
	 * 
	 * @param view A user interface component
	 */
	public void doctorLogin(View view) {

		// An Intent is an operation to be performed. In this case,
		// the operation is to start a new activity. The activity will
		// be started later in this method when startActivity is called.

		Intent intent = new Intent(this, DoctorSelectionActivity.class);

		// Gets the account number from the first EditText field.
		EditText accountNumberText = (EditText) findViewById
				(R.id.account_number_field);
		String accountNumber = accountNumberText.getText().toString();

		// Gets the password from the second EditText field.
		EditText passwordText = (EditText) findViewById(R.id.password_field);
		String password = passwordText.getText().toString();

		// Get the Doctor according to the accountNumber and password.
		Doctor doctor = loginAccount.getDoctor(accountNumber, password);
		if (doctor != null) {
			PatientDataBase database;
			try {
				database = new PatientDataBase(this.getApplicationContext()
						.getFilesDir(), "patientdata.txt");
				intent.putExtra("nurseKey", doctor);
				intent.putExtra("accountNumberKey", accountNumber);
				intent.putExtra("databaseKey", database);
				// Start DisplayActivity, passing extras (the Patient object)
				// via the intent.
				startActivity(intent);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	}
	
}
