package com.manumb.digital_money_service.business.accounts;

import com.manumb.digital_money_service.business.accounts.dto.ResponseGetBalanceAccount;

public interface AccountService {
    Account findById(Long id);
}
