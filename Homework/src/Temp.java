import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Temp {
    public static void main(String[] args) {
        Date date = new Date();
//        date = new SimpleDateFormat("yyyy-MM-dd").parse()
        LocalDate ld = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        System.out.println(date);
//        System.out.println(ld);
//        ld = LocalDate.;
        System.out.println(ld);
//        System.out.println(date.getYear());
//        System.out.println(date.getMonth());
//        System.out.println(date.getDay());
//        System.out.println(date.getDate());
    }
}
