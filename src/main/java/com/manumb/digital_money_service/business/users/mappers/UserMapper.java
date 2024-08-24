package com.manumb.digital_money_service.business.users.mappers;

import com.manumb.digital_money_service.business.users.User;

public interface UserMapper <T>{
    User toUser(T toBeMapped);
}
