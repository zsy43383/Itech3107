package au.edu.federation.itech3107.studentattendance30395775.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import au.edu.federation.itech3107.studentattendance30395775.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {
    // Member variables
    GridView gvinfo;
    // Define an array for related data: image (int) and text (String)
    String[] title = new String[]{"Add Course Information", "Maintain Course Information", "Add Student Information", "Maintain Student Information", "Query Student Information", "Record Student Attendance", "Exit"};
    ArrayList<Map<String, Object>> data; // Data source to wrap the data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create an object to prepare the data source
        this.data = new ArrayList<>();
        for (int i = 0; i < title.length; i++) {
            // Build a Map object, subclass of HashMap
            Map<String, Object> tem = new HashMap<>();
            // Key-value pair to compose a small layout
            tem.put("miantitle", title[i]);
            // Add each small layout to the "warehouse" as map objects to be displayed in the interface
            data.add(tem);
        }
        // Initialize a simple adapter with data source, GridView as the "bridge", context, data source, layout
        SimpleAdapter sadapter = new SimpleAdapter(this, data, R.layout.mygrid,
                new String[]{"miantitle"}, new int[]{R.id.gridtext});
        // Initialize GridView and bind it with the corresponding widget
        this.gvinfo = (GridView) findViewById(R.id.gridView1);
        // Set the adapter for GridView
        this.gvinfo.setAdapter(sadapter);

        // Add event handling to gvinfo
        this.gvinfo.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {
                // Event handling: position is the index of the clicked unit in GridView
                mianAction(position);
            }
        });
    }

    /**
     * Click on the image management event handling
     */
    public void mianAction(int id) {
        // Manage the display of layout images and jump to secondary interfaces based on the text and layout images
        switch (id) {
            case 0:
                Intent intent0 = new Intent(this, AddCourseInfoActivity.class);
                startActivity(intent0);
                break;
            case 1:
                Intent intent1 = new Intent(this, WeihuCoursenfoActivity.class);
                startActivity(intent1);
                break;
            case 2:
                // Clicked "Add Student Information"
                startActivity(new Intent(this, AddStudentInfoActivity.class));
                break;
            case 3:
                // Clicked "Maintain Student Information"
                startActivity(new Intent(this, WeihuStudentInfoActivity.class));
                break;
            case 4:
                // Clicked "Query Student Information"
                startActivity(new Intent(this, ShowStudentInfoActivity.class));
                break;
            case 5:
                // Clicked "Record Student Attendance"
                Intent intent3 = new Intent(this, WeihuAttendanceActivity.class);
                startActivity(intent3);
                break;
            case 6:
                // User clicked "Exit"
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Prompt");
                builder.setMessage("Are you sure you want to exit the application?");

                // Confirm button
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                });

                // Cancel button
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
                builder.create().show();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_exit) {
            // User chose to exit
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Prompt");
            builder.setMessage("Are you sure you want to exit the application?");
            // Confirm button
            builder.setPositiveButton("Confirm", new OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            // Cancel button
            builder.setNegativeButton("Cancel", new OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            // Create and display the dialog
            builder.create().show();
        }

        return super.onOptionsItemSelected(item);
    }
}