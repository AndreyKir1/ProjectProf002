package firma.hibernate.dao;

import firma.hibernate.entity.AccountEmployee;

import java.util.List;

public interface AccountDAO {
    Long create(AccountEmployee account);
    AccountEmployee read(Long id);
    boolean update(AccountEmployee account);
    boolean delete(AccountEmployee account);
    List<AccountEmployee> getAll();
}
