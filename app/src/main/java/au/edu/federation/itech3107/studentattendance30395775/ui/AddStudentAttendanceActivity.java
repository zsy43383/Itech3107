package au.edu.federation.itech3107.studentattendance30395775.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import au.edu.federation.itech3107.studentattendance30395775.R;
import au.edu.federation.itech3107.studentattendance30395775.adapter.StudentAttendanceAdapter;
import au.edu.federation.itech3107.studentattendance30395775.bean.StudentAttendance;
import au.edu.federation.itech3107.studentattendance30395775.bean.StudentInfo;
import au.edu.federation.itech3107.studentattendance30395775.dao.ComData;
import au.edu.federation.itech3107.studentattendance30395775.dao.StudentAttendanceDao;
import au.edu.federation.itech3107.studentattendance30395775.dao.StudentInfoDao;

import java.util.ArrayList;

public class AddStudentAttendanceActivity extends Activity {
    // Member variables
    ListView data;
    ArrayAdapter<String> proadapter;
    Spinner pro;
    ArrayList<StudentInfo> adata;// Store query results

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_attendance);
        // Call the init() method
        this.init();
    }

    // Initialize the declared control objects
    public void init() {
        this.pro = (Spinner) findViewById(R.id.spinner1);
        String[] proname = ComData.ctem.getDate().split(",");
        this.proadapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, proname);
        // Set spinpro data source
        this.pro.setAdapter(proadapter);
        this.data = (ListView) findViewById(R.id.lv_data);
        // 1. Query students under the course
        StudentInfoDao studentInfoDao = new StudentInfoDao(this);
        ArrayList<StudentInfo> studentByPro = studentInfoDao.getStudentByPro(ComData.ctem.getName());
        if (studentByPro == null || studentByPro.size() == 0) {
            Toast.makeText(this, "Please enter students for this course!!!", Toast.LENGTH_LONG).show();
        } else {
            // Query attendance based on (course name and date)
            StudentAttendanceDao studentAttendanceDao = new StudentAttendanceDao(this);
            ArrayList<StudentAttendance> allAttendanceData = studentAttendanceDao.getAllAttendanceData(ComData.ctem.getName(), proname[0]);
            if (allAttendanceData == null || allAttendanceData.size() == 0) {
                // Initialize
                for (StudentInfo studentInfo : studentByPro) {
                    StudentAttendance attendance = new StudentAttendance();
                    attendance.setName(ComData.ctem.getName());
                    attendance.setDate(proname[0]);
                    attendance.setNum(studentInfo.getNum());
                    attendance.setStuname(studentInfo.getName());
                    attendance.setStuclass(studentInfo.getMark());
                    attendance.setStatus("0");
                    new StudentAttendanceDao(this).addStudentAttendance(attendance);
                }
                allAttendanceData = new StudentAttendanceDao(this).getAllAttendanceData(ComData.ctem.getName(), proname[0]);
            }
            StudentAttendanceAdapter adapter = new StudentAttendanceAdapter(this, allAttendanceData);
            // Create an adapter and bind the data source
            this.data.setAdapter(adapter);

            this.pro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // Handle Spinner option selection events
                    ArrayList<StudentAttendance> allAttendanceData1 =  new StudentAttendanceDao(getApplicationContext()).getAllAttendanceData(ComData.ctem.getName(), proname[position]);
                    if (allAttendanceData1 == null || allAttendanceData1.size() == 0) {
                        // Initialize
                        for (StudentInfo studentInfo : studentByPro) {
                            StudentAttendance attendance = new StudentAttendance();
                            attendance.setName(ComData.ctem.getName());
                            attendance.setDate(proname[position]);
                            attendance.setNum(studentInfo.getNum());
                            attendance.setStuname(studentInfo.getName());
                            attendance.setStuclass(studentInfo.getMark());
                            attendance.setStatus("0");
                            new StudentAttendanceDao(getApplicationContext()).addStudentAttendance(attendance);
                        }
                        allAttendanceData1 = new StudentAttendanceDao(getApplicationContext()).getAllAttendanceData(ComData.ctem.getName(), proname[position]);
                    }
                    StudentAttendanceAdapter adapter1 = new StudentAttendanceAdapter(getApplicationContext(), allAttendanceData1);
                    // Create an adapter and bind the data source
                    data.setAdapter(adapter1);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Handle Spinner no option selected events
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_student_score, menu);
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