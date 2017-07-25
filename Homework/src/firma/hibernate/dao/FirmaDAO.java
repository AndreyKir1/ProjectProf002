package firma.hibernate.dao;

import firma.hibernate.entity.EmployeeFirm;

import java.util.List;

public interface FirmaDAO {
    Long create(EmployeeFirm employee);
    EmployeeFirm read(Long id);
    boolean update(EmployeeFirm employee);
    boolean delete(EmployeeFirm employee);
    List<EmployeeFirm> getAll();
}
