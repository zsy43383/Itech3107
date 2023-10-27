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
import au.edu.federation.itech3107.studentattendance30395775.dao.ComData;
import au.edu.federation.itech3107.studentattendance30395775.dao.CourseInfoDao;
import au.edu.federation.itech3107.studentattendance30395775.utils.TimeUtils;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class RepairCourseInfoActivity extends Activity {
    // Member variables
    EditText et_course, et_end_time, et_start_time;
    Button butsave, butdel;

    String courseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_course_info);

        // Initialize and declare control objects
        this.init();
        // Call the method to retrieve and display data from the common data area
        this.showOldStudentData();
        // Save button
        this.butsave.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                saveAction();
            }
        });
        // Delete button
        this.butdel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                deleteAction();
            }
        });
    }

    // Initialize and declare control objects
    private void init() {
        this.et_course = (EditText) findViewById(R.id.et_course);
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
        this.butsave = (Button) findViewById(R.id.repairsavebutton);
        this.butdel = (Button) findViewById(R.id.repairdelbutton);
    }

    /**
     * Retrieve and display data from the common data area
     */
    private void showOldStudentData() {
        // Retrieve stored data
        CourseInfo tem = ComData.ctem;
        // Display the retrieved data
        this.et_course.setText(tem.getName());
        this.et_start_time.setText(tem.getStarttime());
        this.et_end_time.setText(tem.getEndtime());
    }

    /**
     * Delete course information button event functionality
     */
    private void deleteAction() {
        Integer id = ComData.ctem.getId();
        // Call the method to delete related information
        CourseInfoDao adao = new CourseInfoDao(this);
        long n = adao.deleteById(id);
        String mes = "Failed to delete course information";
        if (n > 0) {
            mes = "Course information deleted successfully";
        }
        Toast.makeText(this, mes, Toast.LENGTH_LONG).show();
    }

    /**
     * Save course information button event functionality
     */
    private void saveAction() {
        // 1. Get user input
        String courseName = this.et_course.getText().toString();
        String endTime = this.et_end_time.getText().toString();
        String startTime = this.et_start_time.getText().toString();
        // 2. Call the relevant method to store and add course information
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setId(ComData.ctem.getId());
        courseInfo.setName(courseName);
        courseInfo.setDate(courseDate);
        courseInfo.setEndtime(endTime);
        courseInfo.setStarttime(startTime);
        CourseInfoDao adao = new CourseInfoDao(this);
        ComData.ctem = courseInfo;
        long n = adao.updateById(ComData.ctem);
        // Display based on the result
        String mes = "Failed to modify course information";
        if (n > 0) {
            mes = "Course information modified successfully";
        }
        Toast.makeText(this, mes, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.repair_student_info, menu);
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