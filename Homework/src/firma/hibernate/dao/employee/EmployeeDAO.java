package firma.hibernate.dao.employee;

import firma.hibernate.entity.AccountEmployee;
import firma.hibernate.entity.EmployeeFirm;

import java.util.List;

public interface EmployeeDAO {
    Long create(EmployeeFirm employee);
    EmployeeFirm read(Long id);
    EmployeeFirm readByAccount(AccountEmployee account);
    boolean update(EmployeeFirm employee);
    boolean delete(EmployeeFirm employee);
    List<EmployeeFirm> getAll();
}
