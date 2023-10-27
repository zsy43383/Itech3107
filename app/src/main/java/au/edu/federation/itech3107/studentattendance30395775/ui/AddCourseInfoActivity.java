package au.edu.federation.itech3107.studentattendance30395775.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import au.edu.federation.itech3107.studentattendance30395775.R;
import au.edu.federation.itech3107.studentattendance30395775.bean.CourseInfo;
import au.edu.federation.itech3107.studentattendance30395775.dao.CourseInfoDao;
import au.edu.federation.itech3107.studentattendance30395775.utils.TimeUtils;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class AddCourseInfoActivity extends Activity {
    // Member variables
    EditText et_course, et_end_time, et_start_time;
    Button butok, butre;

    String courseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_info);
        // Call the init() method
        this.init();
        // Add click listener to the submit button
        this.butok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View V) {
                // Submit button click handling code
                addaction();
            }
        });
        // Add click listener to the clear button
        this.butre.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Clear button click handling code
                qingkongaction();
            }
        });
    }

    // Initialize declared control objects
    public void init() {
        this.et_course = (EditText) findViewById(R.id.et_course);
    /*this.pro = (Spinner) findViewById(R.id.spinner1);
    // Build the adapter -- data source -- display format
    this.proadapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, this.proname);
    // Set the spinpro data source
    this.pro.setAdapter(proadapter);*/
        this.et_end_time = (EditText) findViewById(R.id.et_end_time);
        this.et_start_time = (EditText) findViewById(R.id.et_start_time);
        // Create a Calendar object and get the current year, month, and day
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog object
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
            // Handle the selected date here
            String date = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDayOfMonth;
            et_start_time.setText(date);
            List<String> dates = TimeUtils.culAllTime(this.et_start_time.getText().toString());
            StringBuilder sb = new StringBuilder();
            Iterator<String> iterator = dates.iterator();

            while (iterator.hasNext()) {
                sb.append(iterator.next());
                if (iterator.hasNext()) {
                    sb.append(",");
                }
            }
            et_end_time.setText(dates.get(dates.size()-1));
            courseDate = sb.toString();
        }, year, month, day);

        // Show the date picker when EditText is clicked
        et_start_time.setOnClickListener(v -> datePickerDialog.show());
        this.butok = (Button) findViewById(R.id.addbutton);
        this.butre = (Button) findViewById(R.id.resbutton);

    }



    // Method for handling the submit button click
    public void addaction() {
        // 1. Get user input
        String courseName = this.et_course.getText().toString();
        String endTime = this.et_end_time.getText().toString();
        String startTime = this.et_start_time.getText().toString();
        // 2. Call the relevant storage to add course information
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setName(courseName);
        courseInfo.setDate(courseDate);
        courseInfo.setEndtime(endTime);
        courseInfo.setStarttime(startTime);
        CourseInfoDao adao = new CourseInfoDao(this);
        long n = adao.addCourseInfo(courseInfo);
        // 3. Display the result
        String mes = "Failed to add course information";
        if (n > 0) {
            mes = "Course information added successfully";
        }
        Toast.makeText(this, mes, Toast.LENGTH_LONG).show();
    }

    // Method for handling the clear button click
    public void qingkongaction() {
        this.et_course.setText("");
        this.et_end_time.setText("");
        this.et_start_time.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_info, menu);
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