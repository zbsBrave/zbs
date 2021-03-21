package zbs.easyGenerator.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zbs
 * @since 2021/3/21
 */
public class TemplateUtils {
    public static String time(String format){
        return new SimpleDateFormat(format).format(new Date());
    }

    public static String time(){
        return DateFormat.getDateTimeInstance().format(new Date());
    }
}
