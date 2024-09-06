package com.manumb.digital_money_service.business.accounts;

public interface AccountService {
    void saveAccount(Account account);
    Account findById(Long id);
    Account findByAliasOrCvu(String identifier);
    void updateBalance(Long accountId, Double newBalance);
}
