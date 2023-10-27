package au.edu.federation.itech3107.studentattendance30395775.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import au.edu.federation.itech3107.studentattendance30395775.R;
import au.edu.federation.itech3107.studentattendance30395775.dao.UserDao;

public class RegisterActivity extends Activity {
    // Member variables
    EditText editname, editpass1, editpass2;
    Button butsave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Call the init() method to initialize
        this.init();
        // Add event listener to the save button
        this.butsave.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Call the saveAction() method to handle the save button click event
                saveAction();
            }
        });
    }

    /**
     * Initialize the declared control objects
     */
    private void init() {
        this.editname = (EditText) findViewById(R.id.regeditname);
        this.editpass1 = (EditText) findViewById(R.id.regditpass1);
        this.editpass2 = (EditText) findViewById(R.id.regditpass2);
        this.butsave = (Button) findViewById(R.id.regbutsave);
    }

    /**
     * Handle the save button click event
     */
    public void saveAction() {
        // 1. Get the input values
        String name = this.editname.getText().toString();
        String pass1 = this.editpass1.getText().toString();
        String pass2 = this.editpass2.getText().toString();
        // 2. Check if the inputs are empty
        if (name.length() == 0 || pass1.length() == 0 || pass2.length() == 0) {
            Toast.makeText(this, "Incomplete input data, please modify", Toast.LENGTH_LONG).show();
            // Return
            return;
        }
        // 3. Check if the passwords match
        if (!pass1.equals(pass2)) {
            // Passwords do not match
            Toast.makeText(this, "Passwords do not match, please modify", Toast.LENGTH_LONG).show();
            // Return
            return;
        }
        // 4. Call relevant methods to modify the database content
        UserDao udao = new UserDao(this);
        long n = udao.addUser(name, pass1);
        if (n > 0) {
            // Registration successful
            Toast.makeText(this, "New user registered successfully", Toast.LENGTH_LONG).show();
//					// Redirect from the new user registration page to the user login page
//					Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
//		    		startActivity(intent);
//		    		// After redirection, destroy the new user registration page
//		    		this.finish();
        } else {
            // Registration failed
            Toast.makeText(this, "Failed to register new user", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Handle the reset button click event
     */
    public void resetAction() {
        this.editname.setText("");
        this.editpass1.setText("");
        this.editpass2.setText("");
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