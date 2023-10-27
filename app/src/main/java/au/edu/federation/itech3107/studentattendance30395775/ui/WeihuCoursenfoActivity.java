package au.edu.federation.itech3107.studentattendance30395775.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import au.edu.federation.itech3107.studentattendance30395775.R;
import au.edu.federation.itech3107.studentattendance30395775.bean.CourseInfo;
import au.edu.federation.itech3107.studentattendance30395775.dao.ComData;
import au.edu.federation.itech3107.studentattendance30395775.dao.CourseInfoDao;

import java.util.ArrayList;

public class WeihuCoursenfoActivity extends Activity {
    // Member variables
    Button butshow;
    EditText course_name;
    ListView listshow;
    ArrayList<CourseInfo> adata; // Store the query results

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weihu_course_info);
        // Call the method to initialize
        this.init();
        // Add event listener for the button that queries by student ID
        this.butshow.setOnClickListener(v -> {
            String s = course_name.getText().toString();
            if ("".equals(s) || "null".equals(s)) {
                query(null);
            }else {
                query(s);
            }
        });
        // Add event listener for the listshow control
        this.listshow.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {
            listLongAction(arg2);
        });

        // Add event listener for the listshow control
        this.listshow.setOnItemLongClickListener((arg0, arg1, arg2, arg3) -> {
            listAction(arg2);
            return true;
        });

    }

    /*
     * Initialize the declared control objects
     */
    private void init() {
        this.butshow = (Button) findViewById(R.id.showbutton);
        this.course_name = (EditText) findViewById(R.id.et_course_name);
        this.listshow = (ListView) findViewById(R.id.listView1);
        query(null);
    }

    /**
     * Query a single student information by name and display the data
     *
     * @param name
     */
    public void query(String name) {
        // 1. Call the relevant method to query the data
        CourseInfoDao adao = new CourseInfoDao(this);
        this.adata = adao.getCourseData(name);
        // 2. Build the required data source for the list
        ArrayList<String> sdata = new ArrayList<String>();
        for (int i = 0; i < this.adata.size(); i++) {
            // Get the i-th record from the repository
            CourseInfo tem = this.adata.get(i);
            // Build the content displayed in each row of the list
            String str = tem.getName()  + "\t\t" + tem.getStarttime() + "\t\t" + tem.getEndtime() ;
            // Add to the data source
            sdata.add(str);
        }
        // Connect the data source to the list
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, sdata);
        // Set the adapter for the list
        this.listshow.setAdapter(adapter);
    }

    /**
     * Event handler for when the user clicks on a student information item in the list
     */
    private void listLongAction(int num) {
        // 1. Get the data of the num-th item from the data
        // 2. Store the retrieved data to the public data area -- to be retrieved in the student information maintenance interface
        ComData.ctem = this.adata.get(num);
        // 3. Jump to the information modification and deletion interface
        Intent intent = new Intent(this, RepairCourseInfoActivity.class);
        startActivity(intent);
    }


    /**
     * Event handler for when the user long clicks on a student information item in the list
     */
    private void listAction(int num) {
        // 1. Get the data of the num-th item from the data
        // 2. Store the retrieved data to the public data area -- to be retrieved in the student attendance entry interface
        ComData.ctem = this.adata.get(num);
        // 3. Jump to the attendance entry interface
        Intent intent = new Intent(this, AddStudentAttendanceActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weihu_student_info, menu);
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