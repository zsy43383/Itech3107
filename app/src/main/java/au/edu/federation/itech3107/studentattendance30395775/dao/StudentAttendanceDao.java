package au.edu.federation.itech3107.studentattendance30395775.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import au.edu.federation.itech3107.studentattendance30395775.bean.StudentAttendance;

import java.util.ArrayList;

/**
 * 学生成绩添加表 相关
 *
 * @author k01
 */
public class StudentAttendanceDao {
    //成员变量
    MySqitHelper myhelper;
    SQLiteDatabase st;//
    String TB_NAME = "Attendancetb";
    String COL1 = "_id";
    String COL2 = "name";
    String COL3 = "date";
    String COL4 = "num";
    String COL5 = "stuname";
    String COL6 = "stuclass";

    String COL7 = "status";
	
    //构造方法
    public StudentAttendanceDao(Context context) {
        //创建打开数据库
        this.myhelper = new MySqitHelper(context);
        //连接，初始化管理员
        try {
            //当内存足够用读写的方式进行打开
            this.st = this.myhelper.getWritableDatabase();
        } catch (Exception e) {
            //当内存不足时就只用只读的方式进行打开
            this.st = this.myhelper.getReadableDatabase();
        }
        //创建表
        try {
            String sql = "create table if not exists " + TB_NAME + "(" + COL1 + "   integer primary key autoincrement , " +
                    COL2 + "  varchar(10)," + COL3 + "  varchar(10)," 
                    + COL4 + "  varchar(10),"
                    + COL5 + "" + "  varchar(10),"
                    + COL6 + "" + "  varchar(10),"
                    + COL7 + "  varchar(10))";
            //发送指令
            this.st.execSQL(sql);
        } catch (Exception e) {
            // 数据表 创建 异常
            Log.e("学生考勤表创建异常", e.toString());
        }
    }

    /**
     * 添加学生成绩
     *
     * @param -包装为一个 StudentAttendance对象
     */
    public void addStudentAttendance(StudentAttendance tem) {
        ContentValues values = new ContentValues();
        values.put(COL2, tem.getName());
        values.put(COL3, tem.getDate());
        values.put(COL4, tem.getNum());
        values.put(COL5, tem.getStuname());
        values.put(COL6, tem.getStuclass());
        values.put(COL7, tem.getStatus());
        //发送管理员指令
        long n = this.st.insert(TB_NAME, null, values);
        this.free();
    }

    /**
     * 所有进行查询
     *
     * @return ArrayList<StudentAttendance>
     * @parma all
     */
    public ArrayList<StudentAttendance> getAllAttendanceData(String name, String date) {
        ArrayList<StudentAttendance> adata = new ArrayList<StudentAttendance>();
        //查询添加的学生成绩表中所有的学生成绩
        String sql = "select * from  " + TB_NAME;

        if (name != null && date != null) {
            sql += " where name = '" + name + "' and date = '" + date + "'";
        }
        //管理员 发送查寻指令
        Cursor cursor = this.st.rawQuery(sql, null);
        //从结果中逐条 取出 相关记录
        while (cursor.moveToNext()) {
            //取出当前记录
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COL1));
            @SuppressLint("Range") String s1 = cursor.getString(cursor.getColumnIndex(COL2));
            @SuppressLint("Range") String s2 = cursor.getString(cursor.getColumnIndex(COL3));
            @SuppressLint("Range") String s3 = cursor.getString(cursor.getColumnIndex(COL4));
            @SuppressLint("Range") String s4 = cursor.getString(cursor.getColumnIndex(COL5));
            @SuppressLint("Range") String s5 = cursor.getString(cursor.getColumnIndex(COL6));
            @SuppressLint("Range") String s6 = cursor.getString(cursor.getColumnIndex(COL7));
            //
            StudentAttendance tem = new StudentAttendance();
            //
            tem.setId(id);
            tem.setName(s1);
            tem.setDate(s2);
            tem.setNum(s3);
            tem.setStuname(s4);
            tem.setStuclass(s5);
            tem.setStatus(s6);
            //
            adata.add(tem);
        }
        cursor.close();
        this.st.close();
        this.myhelper.close();
        return adata;
    }



    /**
     * 根据 学号删除 对应的学生成绩
     *
     * @param num
     * @return long
     */
    public long deleteById(String num) {
        long n = this.st.delete(TB_NAME, COL2 + "=?", new String[]{num});
        this.st.close();
        this.myhelper.close();
        return n;
    }

    /**
     * 学生成绩数据修改
     *
     * @param --包装为 StudentAttendance 类对象
     * @return long
     */
    public long updateById(StudentAttendance tem) {
        ContentValues values = new ContentValues();
        //设置要进行 修改的字段值
        values.put(COL7, tem.getStatus());
        //通过管理员 发指令                                                                              where 列 1=？             设置参数                  String.values（整形数） 将整形数 转换为字符串
        long n = this.st.update(TB_NAME, values, COL1 + "=?", new String[]{String.valueOf(tem.getId())});
        this.free();
        return n;
    }
    /**
     * 释放
     */
    public void free() {
        this.st.close();
        this.myhelper.close();
    }
}