package com.example.csc207app;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import type.Nurse;
import type.PatientDataBase;
import account.LoginAccount;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends Activity {
	public static final String FILENAME = "passwords.txt";
	private String accountNumber;
	private LoginAccount loginAccount;

	@Override
	/**
	 * Sets the layout and initializes the activity
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		Intent intent = getIntent();
		loginAccount = (LoginAccount) intent
				.getSerializableExtra("loginAccountKey");

	}

	@Override
	/**
	 * Inflates the menu
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	/**
	 * Gets the values for the fields and registers the new nurse account
	 * 
	 * @param view
	 *            A user interface component
	 */
	public void nurseRegister(View view) {
		// Gets the account number from the first EditText field.
		EditText accountNumberText = (EditText) findViewById(R.id.account_number_field);
		accountNumber = accountNumberText.getText().toString();

		// Gets the password from the second EditText field.
		EditText passwordText = (EditText) findViewById(R.id.password_field);
		String password = passwordText.getText().toString();

		Nurse nurse = new Nurse(accountNumber, password);
		if (nurse != null && loginAccount != null)
			try {
				FileOutputStream outputStream;
				loginAccount.addNurse(accountNumber, nurse);
				outputStream = openFileOutput(FILENAME, 0);
				loginAccount.saveToFile(outputStream);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		try {
			Intent intent = new Intent(this, OperationActivity.class);
			PatientDataBase database = new PatientDataBase(this
					.getApplicationContext().getFilesDir(), "patientdata.txt");
			intent.putExtra("nurseKey", nurse);
			intent.putExtra("accountNumberKey", accountNumber);
			intent.putExtra("databaseKey", database);
			startActivity(intent);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
