package au.edu.federation.itech3107.studentattendance30395775.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import au.edu.federation.itech3107.studentattendance30395775.R;
import au.edu.federation.itech3107.studentattendance30395775.bean.CourseInfo;
import au.edu.federation.itech3107.studentattendance30395775.bean.StudentInfo;
import au.edu.federation.itech3107.studentattendance30395775.dao.StudentInfoDao;
import au.edu.federation.itech3107.studentattendance30395775.dao.CourseInfoDao;

import java.util.ArrayList;

public class AddStudentInfoActivity extends Activity {
    // Member variables
    EditText editnum, editname, editage, editmark;
    RadioButton radiomen, radiowomen;
    ArrayAdapter<String> proadapter;
    Spinner pro;
    Button butok, butre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_info);
        // Call the init() method
        this.init();
        // Add listener to the submit button
        this.butok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View V) {
                // Handle the submit button click event
                addaction();
            }
        });
        // Add listener to the clear button
        this.butre.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Handle the clear button click event
                qingkongaction();
            }
        });
    }

    // Initialize the declared control objects
    public void init() {
        this.editnum = (EditText) findViewById(R.id.addnumedit);
        this.editname = (EditText) findViewById(R.id.addnameedit);
        this.radiomen = (RadioButton) findViewById(R.id.addradioman);
        this.radiowomen = (RadioButton) findViewById(R.id.addradiowoman);
        this.editage = (EditText) findViewById(R.id.addageedit);
        this.pro = (Spinner) findViewById(R.id.spinner1);
        // Build the adapter - data source - display format
        ArrayList<CourseInfo> courseData = new CourseInfoDao(this).getCourseData(null);
        String[] proname = new String[courseData.size()];
        // Traverse courseData and store the name of each CourseInfo object into the String[] array
        for (int i = 0; i < courseData.size(); i++) {
            CourseInfo courseInfo = courseData.get(i);
            proname[i] = courseInfo.getName();
        }
        this.proadapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, proname);
        // Set the data source for spinpro
        this.pro.setAdapter(proadapter);
        this.editmark = (EditText) findViewById(R.id.addmarkedit);
        this.butok = (Button) findViewById(R.id.addbutton);
        this.butre = (Button) findViewById(R.id.resbutton);

    }

    // Method for handling the submit button event
    public void addaction() {
        // 1. Get the user input information
        String num = this.editnum.getText().toString();

        String name = this.editname.getText().toString();
        String sex = "Male";
        if (this.radiowomen.isChecked()) {
            //
            sex = "Female";
        }
        String age = this.editage.getText().toString();
        String pro = this.pro.getSelectedItem().toString();
        String mark = this.editmark.getText().toString();
        // 2. Call the relevant storage to add student information
        StudentInfo tem = new StudentInfo();
        tem.setNum(num);
        tem.setName(name);
        tem.setSex(sex);
        tem.setAge(age);
        tem.setPro(pro);
        tem.setMark(mark);
        StudentInfoDao adao = new StudentInfoDao(this);
        long n = adao.addStudentInfo(tem);
        // 3. Display the result
        String mes = "Failed to add student information";
        if (n > 0) {
            mes = "Student information added successfully";
        }
        Toast.makeText(this, mes, Toast.LENGTH_LONG).show();
    }

    // Method for handling the clear button event
    public void qingkongaction() {
        this.editnum.setText("");
        this.editname.setText("");
        // Set the default gender to male and select it
        this.radiomen.setChecked(true);
        this.editage.setText("");
        // Set the major of the drop-down selection box to the first one in the selection box
        this.pro.setSelection(0);
        this.editmark.setText("");
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