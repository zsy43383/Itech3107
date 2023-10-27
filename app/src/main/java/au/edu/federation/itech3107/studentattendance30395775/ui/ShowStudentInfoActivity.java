package au.edu.federation.itech3107.studentattendance30395775.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import au.edu.federation.itech3107.studentattendance30395775.R;
import au.edu.federation.itech3107.studentattendance30395775.bean.StudentInfo;
import au.edu.federation.itech3107.studentattendance30395775.dao.StudentInfoDao;

import java.util.ArrayList;

public class ShowStudentInfoActivity extends Activity {
	// Member variables
	Button butall, butshow;
	TextView numedit;
	ListView listshow;
	ArrayList<StudentInfo> adata; // Store the query results

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_student_info);
		// Call the method to initialize
		this.init();
		// Call showData() after initialization to directly display student information
//        this.showData();
		// Add a listener to the "Query All Student Information" button
		this.butall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Call showData() in the button listener to query all students
				showData();
			}
		});
		// Add a listener to the "Query Student Information by Number" button
		this.butshow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String num = numedit.getText().toString();
				shownumData(num);
			}
		});
	}

	/*
	 * Initialize the declared control objects
	 */
	private void init() {
		this.butall = (Button) findViewById(R.id.showallbutton);
		this.butshow = (Button) findViewById(R.id.showbutton);
		this.numedit = (TextView) findViewById(R.id.numedit);
		this.listshow = (ListView) findViewById(R.id.listView1);
	}

	/**
	 * Query all student information from the data table and display it
	 *
	 * @param type
	 */
	private void showData() {
		// 1. Call relevant methods to query data
		StudentInfoDao adao = new StudentInfoDao(this);
		this.adata = adao.getStudentData();
		// 2. Build the data source needed for the list
		ArrayList<String> sdata = new ArrayList<String>();
		for (int i = 0; i < this.adata.size(); i++) {
			// Read the i-th record from the repository
			StudentInfo tem = this.adata.get(i);
			// Build the content to be displayed in each row of the list
			String str = tem.getNum() + "\t\t" + tem.getName() + "\t\t" + tem.getSex() + "\t\t" + tem.getPro();
			// Add to the data source
			sdata.add(str);
		}
		// Bridge the data source and the list
		ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, sdata);
		// Set the adapter for the list
		this.listshow.setAdapter(adapter);
	}

	/**
	 * Query individual student information based on the student number and display it
	 *
	 * @param type
	 */
	public void shownumData(String num) {
		// 1. Call relevant methods to query data
		StudentInfoDao adao = new StudentInfoDao(this);
		this.adata = adao.getStudentnumData(num);
		// 2. Build the data source needed for the list
		ArrayList<String> sdata = new ArrayList<String>();
		for (int i = 0; i < this.adata.size(); i++) {
			// Read the i-th record from the repository
			StudentInfo tem = this.adata.get(i);
			// Build the content to be displayed in each row of the list
			String str = tem.getNum() + "\t\t" + tem.getName() + "\t\t" + tem.getSex() + "\t\t" + tem.getAge() + "\t\t" + tem.getPro() + "\t\t" + tem.getMark();
			// Add to the data source
			sdata.add(str);
		}
		// Bridge the data source and the list
		ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, sdata);
		// Set the adapter for the list
		this.listshow.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_student_info, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}