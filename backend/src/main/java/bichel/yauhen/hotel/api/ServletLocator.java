package bichel.yauhen.hotel.api;

import bichel.yauhen.hotel.api.controller.HotelReviewServlet;
import bichel.yauhen.hotel.api.controller.HotelServlet;
import bichel.yauhen.hotel.api.controller.HotelsByWordServlet;
import bichel.yauhen.hotel.api.controller.LoginServlet;
import bichel.yauhen.hotel.api.controller.LogoutServlet;
import bichel.yauhen.hotel.api.controller.RegistrationServlet;
import bichel.yauhen.hotel.api.db.SqlConnectionPool;
import bichel.yauhen.hotel.api.mapper.AccountMapper;
import bichel.yauhen.hotel.api.mapper.HotelReviewMapper;
import bichel.yauhen.hotel.api.repository.AccountRepository;
import bichel.yauhen.hotel.api.repository.JdbcAccountRepository;
import bichel.yauhen.hotel.api.repository.JdbcReviewRepository;
import bichel.yauhen.hotel.api.repository.ReviewRepository;
import bichel.yauhen.hotel.api.service.AccountService;
import bichel.yauhen.hotel.api.service.BasicAccountService;
import bichel.yauhen.hotel.api.service.BasicHotelService;
import bichel.yauhen.hotel.api.service.BasicReviewService;
import bichel.yauhen.hotel.api.service.HotelService;
import bichel.yauhen.hotel.api.service.ReviewService;
import bichel.yauhen.hotel.api.validator.EmailValidator;
import bichel.yauhen.hotel.api.validator.PasswordValidator;
import bichel.yauhen.hotel.core.data.ThreadSafeHotelReviewDataForReading;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;

public class ServletLocator {

    private static final Logger logger = LogManager.getLogger(ServletLocator.class);

    private final SqlConnectionPool sqlConnectionPool;
    private final ThreadSafeHotelReviewDataForReading hotelReviewData;

    public ServletLocator(SqlConnectionPool sqlConnectionPool, ThreadSafeHotelReviewDataForReading hotelReviewData) {
        this.sqlConnectionPool = sqlConnectionPool;
        this.hotelReviewData = hotelReviewData;
    }

    public HttpServlet getHttpServlet(Class clazz) {
        logger.info("locate http servlet " + clazz.getName());
        return lookup(clazz, sqlConnectionPool);
    }

    private HttpServlet lookup(Class clazz, SqlConnectionPool sqlConnectionPool) {

        HttpServlet servlet = null;

        AccountMapper accountMapper = new AccountMapper();

        if (clazz.getName().equals(LoginServlet.class.getName())) {
            AccountRepository accountRepository = new JdbcAccountRepository(sqlConnectionPool);
            AccountService accountService = new BasicAccountService(accountRepository, accountMapper, hotelReviewData);
            servlet = new LoginServlet(accountService, accountMapper);
        } else if (clazz.getName().equals(RegistrationServlet.class.getName())) {
            AccountRepository accountRepository = new JdbcAccountRepository(sqlConnectionPool);
            AccountService accountService = new BasicAccountService(accountRepository, accountMapper, hotelReviewData);
            EmailValidator emailValidator = new EmailValidator();
            PasswordValidator passwordValidator = new PasswordValidator();
            servlet = new RegistrationServlet(accountService, accountMapper,
                    emailValidator, passwordValidator);
        } else if (clazz.getName().equals(LogoutServlet.class.getName())) {
            servlet = new LogoutServlet();
        } else if (clazz.getName().equals(HotelsByWordServlet.class.getName())) {
            servlet = new HotelsByWordServlet(hotelReviewData);
        } else if (clazz.getName().equals(HotelServlet.class.getName())) {
            servlet = new HotelServlet(hotelReviewData);
        } else if (clazz.getName().equals(HotelReviewServlet.class.getName())) {
            HotelService hotelService = new BasicHotelService();
            ReviewRepository reviewRepository = new JdbcReviewRepository(sqlConnectionPool);
            HotelReviewMapper hotelReviewMapper = new HotelReviewMapper();
            ReviewService reviewService = new BasicReviewService(reviewRepository, hotelReviewMapper);
            servlet = new HotelReviewServlet(hotelService, reviewService);
        }

        return servlet;
    }
}
