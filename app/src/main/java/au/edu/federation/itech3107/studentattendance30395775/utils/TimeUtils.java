package au.edu.federation.itech3107.studentattendance30395775.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TimeUtils {


    public static List<String> culAllTime(String startDateString) {

        List<String> dates = new ArrayList<>();
        // 定义日期格式
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

        try {
            // 解析开始日期
            Date startDate = format.parse(startDateString);

            // 转换为 Calendar 对象
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);

            // 计算 12 周后的日期
            calendar.add(Calendar.WEEK_OF_YEAR, 12);
            Date endDate = calendar.getTime();

            // 循环输出每周的日期
            calendar.setTime(startDate);
            while (calendar.getTime().before(endDate)) {
                Date nowDay = calendar.getTime();
                // 获取当前日期所在周的第一天和最后一天
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                Date firstDayOfWeek = calendar.getTime();
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                Date lastDayOfWeek = calendar.getTime();

                // 格式化输出日期范围
                String dateRange = format.format(firstDayOfWeek) + " ~ " + format.format(lastDayOfWeek) + " ---- " + format.format(nowDay);
                System.out.println(dateRange);

                dates.add(format.format(nowDay));
                calendar.setTime(nowDay);
                // 将当前日期递增一周
                calendar.add(Calendar.WEEK_OF_YEAR, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dates;
    }
}
