package firma.hibernate.dao;

import firma.hibernate.entity.AccountEmployee;
import firma.hibernate.entity.EmployeeFirm;
import firma.hibernate.util.HibernateUtil;
import firma.support.EmployeeRols;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DAOtest {

    public static void main(String[] args) throws ParseException {
        SessionFactory factory = HibernateUtil.getFactory();
        Session session = factory.openSession();
        EmployeeDAO daoFirma = new EmployeeDAOimpl();
        AccountDAO daoAccount = new AccountDAOimpl();

        AccountEmployee account1 = new AccountEmployee("login1", "password1");
        AccountEmployee account2 = new AccountEmployee("login2", "password2");
        AccountEmployee account3 = new AccountEmployee("login3", "password3");
        AccountEmployee account4 = new AccountEmployee("login4", "password4");
        AccountEmployee account5 = new AccountEmployee("login5", "password5");

        EmployeeFirm employee1 = new EmployeeFirm("Surname1", "Name1", "LastName1",
                new SimpleDateFormat("dd.MM.yyy").parse("01.01.2001"),
                new SimpleDateFormat("dd.MM.yyy").parse("01.01.2017"),
                EmployeeRols.ADMINISTRATOR, account1);

        EmployeeFirm employee2 = new EmployeeFirm("Surname2", "Name2", "LastName2",
                new SimpleDateFormat("dd.MM.yyy").parse("02.02.2002"),
                new SimpleDateFormat("dd.MM.yyy").parse("02.02.2017"),
                EmployeeRols.ADMINISTRATOR, account2);

        EmployeeFirm employee3 = new EmployeeFirm("Surname3", "Name3", "LastName3",
                new SimpleDateFormat("dd.MM.yyy").parse("03.03.2003"),
                new SimpleDateFormat("dd.MM.yyy").parse("03.03.2017"),
                EmployeeRols.ADMINISTRATOR, account3);

        EmployeeFirm employee4 = new EmployeeFirm("Surname4", "Name4", "LastName4",
                new SimpleDateFormat("dd.MM.yyy").parse("04.04.2004"),
                new SimpleDateFormat("dd.MM.yyy").parse("04.04.2017"),
                EmployeeRols.ADMINISTRATOR, account4);

        EmployeeFirm employee5 = new EmployeeFirm("Surname5", "Name5", "LastName5",
                new SimpleDateFormat("dd.MM.yyy").parse("05.05.2005"),
                new SimpleDateFormat("dd.MM.yyy").parse("05.05.2017"),
                EmployeeRols.ADMINISTRATOR, account5);

        daoAccount.create(account1);
        daoAccount.create(account2);
        daoAccount.create(account3);
        daoAccount.create(account4);
        daoAccount.create(account5);

        daoFirma.create(employee1);
        daoFirma.create(employee2);
        daoFirma.create(employee3);
        daoFirma.create(employee4);
        daoFirma.create(employee5);

        session.close();
        HibernateUtil.getFactory().close();
    }
}
