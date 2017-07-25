package firma.hibernate.service;

import firma.hibernate.entity.AccountEmployee;

import java.util.List;

public interface AccountService {
    Long create(AccountEmployee account);
    AccountEmployee read(Long id);
    boolean update(AccountEmployee update);
    boolean delete(AccountEmployee delete);
    List<AccountEmployee> getAll();
}
