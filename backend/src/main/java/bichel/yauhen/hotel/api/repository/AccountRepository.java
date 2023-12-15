package bichel.yauhen.hotel.api.repository;

import bichel.yauhen.hotel.api.model.Account;

import java.util.Optional;

/**
 * Interface for account repository
 */
public interface AccountRepository {
    Optional<Account> create(Account account);
    Optional<Account> get(Account account);
    Optional<Account> remove(Account account);
}
