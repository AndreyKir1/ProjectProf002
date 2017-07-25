package firma.hibernate.service;

import firma.hibernate.dao.EmployeeDAO;
import firma.hibernate.dao.EmployeeDAOimpl;
import firma.hibernate.entity.EmployeeFirm;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDAO dao;

    public EmployeeServiceImpl () {
        dao = new EmployeeDAOimpl();
    }

    @Override
    public Long create(EmployeeFirm employee) {
        if (employee != null) return dao.create(employee);
        else return null;
    }

    @Override
    public EmployeeFirm read(Long id) {
        if (dao.read(id) != null) return dao.read(id);
        else {
            System.out.println("Something wrong :(");
            return null;
        }
    }

    @Override
    public boolean update(EmployeeFirm update) {
        if (update != null) return dao.update(update);
        else {
            return false;
        }
    }

    @Override
    public boolean delete(EmployeeFirm delete) {
        if (delete != null) return dao.delete(delete);
        else {
            return false;
        }
    }

    @Override
    public List<EmployeeFirm> getAll() {
        return dao.getAll();
    }
}
