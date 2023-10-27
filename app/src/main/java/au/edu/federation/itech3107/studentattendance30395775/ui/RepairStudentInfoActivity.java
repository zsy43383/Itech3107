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
import android.widget.TextView;
import android.widget.Toast;

import au.edu.federation.itech3107.studentattendance30395775.R;
import au.edu.federation.itech3107.studentattendance30395775.bean.CourseInfo;
import au.edu.federation.itech3107.studentattendance30395775.bean.StudentInfo;
import au.edu.federation.itech3107.studentattendance30395775.dao.StudentInfoDao;
import au.edu.federation.itech3107.studentattendance30395775.dao.ComData;
import au.edu.federation.itech3107.studentattendance30395775.dao.CourseInfoDao;

import java.util.ArrayList;

public class RepairStudentInfoActivity extends Activity {
    // Member variables
    TextView repaireditnum, repaireditname;
    EditText repaireditage, repaireditmark;
    RadioButton repairradiomen, repairradiowomen;
    ArrayAdapter<String> proadapter;

    Spinner repairpro;
    Button butsave, butdel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_student_info);

        // Initialize and declare the control objects
        this.init();
        // Call the method to get data from the public data area and display it
        this.showOldStudentData();
        // Save button
        this.butsave.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                saveAction();
            }
        });
        // Delete button
        this.butdel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                deleteAction();
            }
        });
    }

    // Initialize and declare the control objects
    private void init() {
        this.repaireditnum = (TextView) findViewById(R.id.repairnumtext);
        this.repaireditname = (TextView) findViewById(R.id.repairnametext);
        this.repairradiomen = (RadioButton) findViewById(R.id.repairradiomen);
        this.repairradiowomen = (RadioButton) findViewById(R.id.repairradiowoman);
        this.repaireditage = (EditText) findViewById(R.id.repairageedit);
        this.repairpro = (Spinner) findViewById(R.id.spinner1);
        this.repaireditmark = (EditText) findViewById(R.id.repairmarkedit);
        this.butsave = (Button) findViewById(R.id.repairsavebutton);
        this.butdel = (Button) findViewById(R.id.repairdelbutton);
    }

    /**
     * Get data from the public data area and display it
     */
    private void showOldStudentData() {
        // Get stored data
        StudentInfo tem = ComData.item;
        // Display the retrieved data
        this.repaireditnum.setText(tem.getNum());
        this.repaireditname.setText(tem.getName());

        this.repaireditage.setText(tem.getAge());
        if (tem.getSex().equals("男")) {
            repairradiomen.setChecked(true);
        } else {
            repairradiowomen.setChecked(true);
        }
        int n = 0;
        String pro = tem.getPro();
        ArrayList<CourseInfo> courseData = new CourseInfoDao(this).getCourseData(null);
        // Traverse courseData and store the name of each CourseInfo object in a String[] array
        String[] proname = new String[courseData.size()];
        for (int i = 0; i < courseData.size(); i++) {
            CourseInfo courseInfo = courseData.get(i);
            proname[i] = courseInfo.getName();
        }
        for (int i=0; i<courseData.size(); i++) {
            if (proname[i].equals(pro)) {
                n = i;
                break;
            }
        }
        this.proadapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, proname);
        // Set spinpro data source
        this.repairpro.setAdapter(proadapter);
        this.repairpro.setSelection(n);
        this.repaireditmark.setText(tem.getMark());
    }

    /**
     * Delete student information button event function
     */
    private void deleteAction() {
        String num = ComData.item.getNum();
        // Call the method to delete related information
        StudentInfoDao adao = new StudentInfoDao(this);
        long n = adao.deleteById(num);
        String mes = "Failed to delete student information";
        if (n > 0) {
            mes = "Student information deleted successfully";
        }
        Toast.makeText(this, mes, Toast.LENGTH_LONG).show();
    }

    /**
     * Save student information button event function
     */
    private void saveAction() {
        // 1. Get the information entered by the user (modified information)
        String num = this.repaireditnum.getText().toString();
        String name = this.repaireditname.getText().toString();
        String sex = "男";
        if (this.repairradiowomen.isChecked()) {
            sex = "女";
        }
        String age = this.repaireditage.getText().toString();
        String pro = this.repairpro.getSelectedItem().toString();
        String mark = this.repaireditmark.getText().toString();
        // Modify relevant information in the public database
        ComData.item.setNum(num);
        ComData.item.setName(name);
        ComData.item.setSex(sex);
        ComData.item.setAge(age);
        ComData.item.setPro(pro);
        ComData.item.setMark(mark);
        // Call the relevant method to modify the database
        StudentInfoDao adao = new StudentInfoDao(this);
        long n = adao.updateById(ComData.item);
        // Display according to the result
        String mes = "Failed to modify student information";
        if (n > 0) {
            mes = "Student information modified successfully";
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