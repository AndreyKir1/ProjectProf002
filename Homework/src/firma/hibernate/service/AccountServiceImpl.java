package firma.hibernate.service;

import firma.hibernate.dao.AccountDAO;
import firma.hibernate.dao.AccountDAOimpl;
import firma.hibernate.entity.AccountEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDAO dao;

    public AccountServiceImpl () {dao = new AccountDAOimpl(); }

    @Override
    public Long create(AccountEmployee account) {
        if (account != null) return dao.create(account);
        else return null;
    }

    @Override
    public AccountEmployee read(Long id) {
        if (dao.read(id) != null) return dao.read(id);
        else {
            System.out.println("Something wrong :(");
            return null;
        }
    }

    @Override
    public boolean update(AccountEmployee update) {
        if (update != null) return dao.update(update);
        else {
            return false;
        }
    }

    @Override
    public boolean delete(AccountEmployee delete) {
        if (delete != null) return dao.delete(delete);
        else {
            return false;
        }
    }

    @Override
    public List<AccountEmployee> getAll() {
        return dao.getAll();
    }
}
