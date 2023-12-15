package bichel.yauhen.hotel.api.service;

import bichel.yauhen.hotel.api.model.Account;
import bichel.yauhen.hotel.api.vo.LoginRequest;
import bichel.yauhen.hotel.api.vo.RegistrationRequest;

/**
 * Interface for account service
 */
public interface AccountService {
    Account register(RegistrationRequest request);

    Account login(LoginRequest request);

    Account logout(int userId);
}
