package firma.hibernate.service.employee;

import firma.hibernate.entity.AccountEmployee;
import firma.hibernate.entity.EmployeeFirm;

import java.util.List;

public interface EmployeeService {
    public void create(EmployeeFirm employee);
    EmployeeFirm read(Long id);
    EmployeeFirm readByAccount(AccountEmployee account);
    boolean update(EmployeeFirm update);
    boolean delete(EmployeeFirm delete);
    List<EmployeeFirm> getAll();
}
