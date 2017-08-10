package firma.hibernate.service.employee;

import firma.hibernate.dao.employee.EmployeeDAO;
import firma.hibernate.entity.AccountEmployee;
import firma.hibernate.entity.EmployeeFirm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDAO dao;

    @Override
    public void create(EmployeeFirm employee) {
        if (employee != null) {
            employee.setId(dao.create(employee));
        }
    }

    @Override
    public EmployeeFirm read(Long id) {
        if (dao.read(id) != null) {
            return dao.read(id);
        }
        else {
            System.out.println("Something wrong :(");
            return null;
        }
    }

    @Override
    public EmployeeFirm readByAccount(AccountEmployee account) {
        if(account != null) return dao.readByAccount(account);
        else return null;
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
