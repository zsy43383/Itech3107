package au.edu.federation.itech3107.studentattendance30395775.ui;

import android.app.Activity;
import android.content.Intent;
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
import au.edu.federation.itech3107.studentattendance30395775.dao.ComData;

import java.util.ArrayList;

public class WeihuStudentInfoActivity extends Activity {
    // Member variables
    Button butshow;
    TextView numedit;
    ListView listshow;
    ArrayList<StudentInfo> adata; // Store the query results

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weihu_student_info);
        // Call the method to initialize
        this.init();
        // Add event listener to the "Search by Student Number" button
        this.butshow.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String num = numedit.getText().toString();
                if ("".equals(num) || "null".equals(num)) {
                    shownumData(null);
                } else {
                    shownumData(num);
                }
            }
        });
        // Add listener to the listshow control
        this.listshow.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {
            // TODO Auto-generated method stub
            listAction(arg2);
        });
        shownumData(null);
    }

    /*
     * Initialize the declared control objects
     */
    private void init() {
        this.butshow = (Button) findViewById(R.id.showbutton);
        this.numedit = (TextView) findViewById(R.id.numedit);
        this.listshow = (ListView) findViewById(R.id.listView1);
    }

    /**
     * Query and display individual student information based on student number
     *
     * @param
     */
    public void shownumData(String num) {
        // 1. Call the relevant method to query the data
        StudentInfoDao adao = new StudentInfoDao(this);
        this.adata = adao.getStudentnumData(num);
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
        // Connect the data source with the list
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, sdata);
        // Set the adapter for the list
        this.listshow.setAdapter(adapter);
    }

    /**
     * Event handling for clicking on the list of queried student information
     */
    private void listAction(int num) {
        // 1. Get the data at index num from the data
        StudentInfo tem = this.adata.get(num);
        // 2. Store the retrieved data in the public data area - obtained in the student information maintenance interface
        ComData.item = tem;
        // 3. Jump to the information modification/deletion interface
        Intent intent = new Intent(this, RepairStudentInfoActivity.class);
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