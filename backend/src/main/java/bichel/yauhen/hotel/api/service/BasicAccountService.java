package bichel.yauhen.hotel.api.service;

import bichel.yauhen.hotel.api.exception.AccountDuplicateException;
import bichel.yauhen.hotel.api.exception.AccountNotFoundException;
import bichel.yauhen.hotel.api.mapper.AccountMapper;
import bichel.yauhen.hotel.api.model.Account;
import bichel.yauhen.hotel.api.repository.AccountRepository;
import bichel.yauhen.hotel.api.vo.LoginRequest;
import bichel.yauhen.hotel.api.vo.RegistrationRequest;
import bichel.yauhen.hotel.core.data.ThreadSafeHotelReviewDataForReading;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

/**
 * Basic implementation of account service
 */
public class BasicAccountService implements AccountService {
    private static final Logger logger = LogManager.getLogger(BasicAccountService.class);

    private final ThreadSafeHotelReviewDataForReading hotelReviewData;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public BasicAccountService(AccountRepository accountRepository, AccountMapper accountMapper, ThreadSafeHotelReviewDataForReading hotelReviewData) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.hotelReviewData = hotelReviewData;
    }

    @Override
    public Account register(RegistrationRequest request) {
        logger.info("Register");
        Account account = accountMapper.mapToModel(request);

        Optional<Account> dbAccount = accountRepository.get(account);
        if(dbAccount.isPresent()) {
            logger.error("account exists");
            throw new AccountDuplicateException(account.getUsername());
        }

        Optional<Account> dbAccountOpt = accountRepository.create(account);

        if(dbAccountOpt.isEmpty()) {
            logger.error("account is not added for " + account.getUsername());
            throw new RuntimeException("account is not added for " + account.getUsername());
        }

        return dbAccountOpt.get();
    }

    public Account login(LoginRequest request) {
        logger.info("Auth");

        Account account = accountMapper.mapToModel(request);
        Optional<Account> dbAccountOpt = accountRepository.get(account);

        if(dbAccountOpt.isEmpty()) {
            logger.error("account not found");
            throw new AccountNotFoundException(account.getUsername());
        }

        return dbAccountOpt.get();
    }

    @Override
    public Account logout(int userId) {
        return null;
    }
}
