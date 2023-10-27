package au.edu.federation.itech3107.studentattendance30395775.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import au.edu.federation.itech3107.studentattendance30395775.R;
import au.edu.federation.itech3107.studentattendance30395775.bean.UserInfo;
import au.edu.federation.itech3107.studentattendance30395775.dao.ComData;
import au.edu.federation.itech3107.studentattendance30395775.dao.UserDao;

public class LoginActivity extends Activity {
	//Member variables
	EditText editname, editpass;
	Button butlogin, butreg;
	CheckBox checksave;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		//Call the initialization method
		this.init();

		//Call the method to read saved user information
		this.readLoginInfo();

		//Add button listener for "New User Registration"
		this.butreg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//Switch to the user registration interface
				Intent abc = new Intent(LoginActivity.this, RegisterActivity.class);
				startActivity(abc);

			}
		});
		//Add button listener for login
		this.butlogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//Call the relevant login method
				loginAction();
			}
		});
	}

	/**
	 * Initialize the interface
	 */
	public void init() {
		this.editname = (EditText) findViewById(R.id.loginditname);
		this.editpass = (EditText) findViewById(R.id.logeditpass);
		this.butlogin = (Button) findViewById(R.id.Logbutloging);
		this.butreg = (Button) findViewById(R.id.logbutredister);
		this.checksave = (CheckBox) findViewById(R.id.checkBox1);
	}

	/**
	 * Login button function
	 */
	public void loginAction() {
		//1. Get the username and password entered by the user
		String name = this.editname.getText().toString();
		String pass = this.editpass.getText().toString();
		//2. Check if the input is empty
		if (name.length() == 0 || pass.length() == 0) {
			Toast.makeText(this, "Incomplete data input, please modify", Toast.LENGTH_LONG).show();
			return;
		}
		//3. Call the relevant method to query the database in the user information table
		UserDao udao = new UserDao(this);
		UserInfo utem = udao.checkUser(name, pass);
		//4. Handle the result
		if (utem == null) {
			//If the user information in the user information table is empty--login failed
			Toast.makeText(this, "Incorrect username or password, please modify!", Toast.LENGTH_LONG).show();
		} else {
			//If there is user information in the user information table--login successful--legal user
			Toast.makeText(this, "User login successful!", Toast.LENGTH_LONG).show();
			SharedPreferences sharesave = getSharedPreferences("loginfo", Context.MODE_PRIVATE);
			Editor editor = sharesave.edit();
			if (this.checksave.isChecked()) {
				//If the user chooses to save the user information, display the username and password
				editor.putString("uname", name);
				editor.putString("upass", pass);
				//Submit
				editor.commit();
			} else {
				//If the user chooses not to save, clear the saved content
				editor.clear();
				//Only display the username that the user has logged in with, not the password
				editor.putString("uname", name);
				editor.commit();
			}
			//Legal user--login successful--jump to the user interface
			ComData.utem = utem;
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			//Destroy the login and registration interface--directly exit when returned
			this.finish();
		}
	}

	/**
	 * Read saved username and password
	 */
	public void readLoginInfo() {
		SharedPreferences sharelogin = getSharedPreferences("loginfo", Context.MODE_PRIVATE);
		//Read based on key names and display default empty values if not read
		String uname = sharelogin.getString("uname", "");
		String upass = sharelogin.getString("upass", "");
		//Display the content that is read in the username and password box
		this.editname.setText(uname);
		this.editpass.setText(upass);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_exit) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}