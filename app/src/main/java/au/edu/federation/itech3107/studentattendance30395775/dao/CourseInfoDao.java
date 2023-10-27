package au.edu.federation.itech3107.studentattendance30395775.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import au.edu.federation.itech3107.studentattendance30395775.bean.CourseInfo;

import java.util.ArrayList;

/**
 * 课程信息添加表 相关
 *
 * @author k01
 */
public class CourseInfoDao {
    //成员变量
    MySqitHelper myhelper;//数据库的连接
    SQLiteDatabase st;//数据库管理员
    String TB_NAME = "Coursetb";//课程的信息表
    String COL1 = "_id";
    String COL2 = "name";
    String COL3 = "date";
    String COL4 = "starttime";
    String COL5 = "endtime";

    //构造方法
    public CourseInfoDao(Context context) {
        //创建打开数据库
        this.myhelper = new MySqitHelper(context);
        //连接，初始化管理员
        try {
            this.st = this.myhelper.getWritableDatabase();
        } catch (Exception e) {
            this.st = this.myhelper.getReadableDatabase();
        }
        //创建表
        try {
            String sql = "create table if not exists " + TB_NAME + "(" + COL1 + "   integer primary key autoincrement , " +
                    COL2 + "  varchar(10)," + COL3 + "  varchar(10)," + COL4 + "  varchar(10)," + COL5 + "" +
                    "  varchar(10) )";
            //发送指令
            this.st.execSQL(sql);
        } catch (Exception e) {
            // 数据表 创建 异常
            Log.e("课程信息表创建异常", e.toString());
        }
    }

    /**
     * 添加课程信息
     *
     * @param --包装为一个 CourseInfo对象
     * @return long
     */
    public long addCourseInfo(CourseInfo tem) {
        ContentValues values = new ContentValues();
        values.put(COL2, tem.getName());
        values.put(COL3, tem.getDate());
        values.put(COL4, tem.getStarttime());
        values.put(COL5, tem.getEndtime());
        //发送管理员指令
        long n = this.st.insert(TB_NAME, null, values);
        this.free();
        return n;
    }

    /**
     * 查询添加的所有课程信息  ，按所有进行查询
     *
     * @return ArrayList<CourseInfo>
     * @parma all
     */
    public ArrayList<CourseInfo> getCourseData(String name) {
        //初始化一个对象
        ArrayList<CourseInfo> adata = new ArrayList<CourseInfo>();
        //查询添加的课程信息表中所有的课程信息
        String sql = "select * from  " + TB_NAME;
        if (name != null) {
            sql += " where name = '" + name + "'";
        }

        //管理员 发送查寻指令
        Cursor cursor = this.st.rawQuery(sql, null);
        //从结果中逐条 取出 相关记录
        while (cursor.moveToNext()) {
            //取出当前记录
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COL1));
            @SuppressLint("Range") String courseName = cursor.getString(cursor.getColumnIndex(COL2));
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(COL3));
            @SuppressLint("Range") String starttime = cursor.getString(cursor.getColumnIndex(COL4));
            @SuppressLint("Range") String endtime = cursor.getString(cursor.getColumnIndex(COL5));
            //创建一个对象，进行初始化课程信息的相关bean
            CourseInfo tem = new CourseInfo();
            //用初始化的字段进行设置 相关的信息
            tem.setId(id);
            tem.setName(courseName);
            tem.setDate(date);
            tem.setStarttime(starttime);
            tem.setEndtime(endtime);
            //将相关的数据进行添加到初始化的课程信息表的相关存储中...
            adata.add(tem);
        }
        cursor.close();//关闭查询
        this.st.close();//关闭管理员命令
        this.myhelper.close();//关闭数据库连接
        return adata;//
    }

   /* *//**
     * 查询添加的课程信息  按学号进行查询
     *
     * @return ArrayList<CourseInfo>
     * @parma num0
     *//*
    public ArrayList<CourseInfo> getStudentnumData(String num0) {
        ArrayList<CourseInfo> adata = new ArrayList<CourseInfo>();
        //根据用户输入的学号查询单个课程信息
        String sql = "select * from  " + TB_NAME + "  where " + COL2 + "=?";
        //管理员 发送查寻指令
        Cursor cursor = this.st.rawQuery(sql, new String[]{num0});
        //从结果中逐条 取出 相关记录
        if (cursor.moveToNext()) {
            //查询成功取出当前记录
            int id = cursor.getInt(cursor.getColumnIndex(COL1));
            String num = cursor.getString(cursor.getColumnIndex(COL2));
            String name = cursor.getString(cursor.getColumnIndex(COL3));
            String sex = cursor.getString(cursor.getColumnIndex(COL4));
            String age = cursor.getString(cursor.getColumnIndex(COL5));
            //
            CourseInfo tem = new CourseInfo();
            //
            tem.setId(id);
            tem.setNum(num);
            tem.setName(name);
            tem.setSex(sex);
            tem.setAge(age);
            //
            adata.add(tem);
        }
        cursor.close();
        this.st.close();
        this.myhelper.close();
        return adata;
    }*/

    /**
     * 根据 id 删除 对应的课程信息
     *
     * @param id
     * @return long
     */
    public long deleteById(Integer id) {
        long n = this.st.delete(TB_NAME, COL1 + "=?", new String[]{String.valueOf(id)});
        this.st.close();
        this.myhelper.close();
        return n;
    }

    /**
     * 课程信息数据修改
     *
     * @return long
     */
    public long updateById(CourseInfo tem) {
        ContentValues values = new ContentValues();
        //设置要进行 修改的字段值
        values.put(COL2, tem.getName());
        values.put(COL3, tem.getDate());
        values.put(COL4, tem.getStarttime());
        values.put(COL5, tem.getEndtime());
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
