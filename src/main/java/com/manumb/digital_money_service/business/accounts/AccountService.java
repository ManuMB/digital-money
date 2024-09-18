package com.manumb.digital_money_service.business.accounts;

import java.io.IOException;

public interface AccountService {
    void saveAccount(Account account);

    Account findById(Long id);

    Account findByAliasOrCvu(String identifier);

    void updateBalance(Long accountId, Double newBalance);

    void updateAlias(Long accountId, String newAlias);

    String generateCVU();

    String generateAlias() throws IOException;
}
