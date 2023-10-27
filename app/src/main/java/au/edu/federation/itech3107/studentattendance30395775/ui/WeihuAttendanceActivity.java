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

public class WeihuAttendanceActivity extends Activity {
    // Member variables
    Button showButton;
    EditText courseName;
    ListView showList;
    ArrayList<CourseInfo> data; // Store the query results

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weihu_course_info);
        // Call the method to initialize
        this.init();
        // Add event listener to the button for querying by course name
        this.showButton.setOnClickListener(v -> {
            String s = courseName.getText().toString();
            if ("".equals(s) || "null".equals(s)) {
                query(null);
            } else {
                query(s);
            }
        });
        // Add item click listener to the listview
        this.showList.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {
            listAction(arg2);
        });

        // Add item long click listener to the listview
        this.showList.setOnItemLongClickListener((arg0, arg1, arg2, arg3) -> {
            listLongAction(arg2);
            return true;
        });

    }

    /*
     * Initialize the declared control objects
     */
    private void init() {
        this.showButton = findViewById(R.id.showbutton);
        this.courseName = findViewById(R.id.et_course_name);
        this.showList = findViewById(R.id.listView1);
        query(null);
    }

    /**
     * Query and display individual student information based on their name
     *
     * @param name
     */
    public void query(String name) {
        // 1. Call the relevant method to query the data
        CourseInfoDao dao = new CourseInfoDao(this);
        this.data = dao.getCourseData(name);
        // 2. Build the data source for the list
        ArrayList<String> dataSource = new ArrayList<>();
        for (int i = 0; i < this.data.size(); i++) {
            // Read the i-th record from the repository
            CourseInfo temp = this.data.get(i);
            // Build the content to be displayed in each row of the list
            String str = temp.getName();
            // Add to the data source
            dataSource.add(str);
        }
        // Connect the data source with the listview
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, dataSource);
        // Set the adapter for the listview
        this.showList.setAdapter(adapter);
    }

    /**
     * Event handling for long clicking on the list of queried student information
     */
    private void listLongAction(int num) {
        // 1. Get the data at index num from the data
        // 2. Store the retrieved data in the public data area - to be accessed in the student information maintenance interface
        ComData.ctem = this.data.get(num);
        // 3. Jump to the information modification/deletion interface
        Intent intent = new Intent(this, RepairCourseInfoActivity.class);
        startActivity(intent);
    }


    /**
     * Event handling for clicking on the list of queried student information
     */
    private void listAction(int num) {
        // 1. Get the data at index num from the data
        // 2. Store the retrieved data in the public data area - to be accessed in the attendance entry interface
        ComData.ctem = this.data.get(num);
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