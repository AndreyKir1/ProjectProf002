package firma.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import firma.hibernate.util.HibernateUtil;
import java.text.ParseException;

public class BaseFirmaTest {
    public static void main(String[] args) throws ParseException {
        SessionFactory factory = HibernateUtil.getFactory();
        Session session = factory.openSession();
        HibernateUtil.getFactory().close();
    }
}
