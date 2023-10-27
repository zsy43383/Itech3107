package au.edu.federation.itech3107.studentattendance30395775.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import au.edu.federation.itech3107.studentattendance30395775.R;
import au.edu.federation.itech3107.studentattendance30395775.bean.StudentAttendance;
import au.edu.federation.itech3107.studentattendance30395775.dao.StudentAttendanceDao;

/**
 * @author admin
 */
public class StudentAttendanceAdapter extends ArrayAdapter<StudentAttendance> {
    private Context mContext;

    public StudentAttendanceAdapter(Context context, List<StudentAttendance> studentAttendanceList) {
        super(context, 0, studentAttendanceList);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(mContext).inflate(R.layout.list_item_student, parent, false);
        }

        // 获取当前位置的学生对象
        final StudentAttendance currentStudentAttendance = getItem(position);

        // 获取列表项中的复选框和姓名视图
        CheckBox checkBox = listItemView.findViewById(R.id.checkbox);
        TextView nameTextView = listItemView.findViewById(R.id.student_name);

        if (currentStudentAttendance != null) {
            // 解绑监听器
            checkBox.setOnCheckedChangeListener(null);
            // 设置复选框和姓名的值
            checkBox.setChecked("1".equals(currentStudentAttendance.getStatus()));
            // 重新绑定监听器
            CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
                // 监听器的处理逻辑
                if (isChecked) {
                    // 当 CheckBox 被选中时执行的操作
                    currentStudentAttendance.setStatus("1");
                    StudentAttendanceDao studentAttendanceDao = new StudentAttendanceDao(getContext());
                    studentAttendanceDao.updateById(currentStudentAttendance);
                } else {
                    // 当 CheckBox 取消选中时执行的操作
                    currentStudentAttendance.setStatus("0");
                    StudentAttendanceDao studentAttendanceDao = new StudentAttendanceDao(getContext());
                    studentAttendanceDao.updateById(currentStudentAttendance);
                }
            };
            checkBox.setOnCheckedChangeListener(listener);
            nameTextView.setText(currentStudentAttendance.getStuname());
        }


        return listItemView;
    }
}
