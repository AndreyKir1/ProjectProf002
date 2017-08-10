package firma.hibernate.service.account;

import firma.hibernate.entity.AccountEmployee;

import java.util.List;

public interface AccountService {
    void create(AccountEmployee account);
    AccountEmployee read(Long id);
    boolean update(AccountEmployee update);
    boolean delete(AccountEmployee delete);
    List<AccountEmployee> getAll();
}
