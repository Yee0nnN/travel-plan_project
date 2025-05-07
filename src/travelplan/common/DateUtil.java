
package travelplan.common;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class DateUtil {

    public static String convertToString(Date d) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
    }

    public static Date convertToDate(String str) {
        try {
            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(str);
            return new Date(utilDate.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date convertToSQLDate(Date d) {
        return new Date(d.getTime());
    }
}
