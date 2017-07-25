package firma.hibernate.service;

import firma.hibernate.entity.EmployeeFirm;

import java.util.List;

public interface EmployeeService {
    public Long create(EmployeeFirm employee);
    EmployeeFirm read(Long id);
    boolean update(EmployeeFirm update);
    boolean delete(EmployeeFirm delete);
    List<EmployeeFirm> getAll();
}
