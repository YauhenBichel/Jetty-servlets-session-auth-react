package bichel.yauhen.hotel.api.controller;

import bichel.yauhen.hotel.api.utils.JsonUtils;
import bichel.yauhen.hotel.api.exception.AccountDuplicateException;
import bichel.yauhen.hotel.api.model.Account;
import bichel.yauhen.hotel.api.service.AccountService;
import bichel.yauhen.hotel.api.mapper.AccountMapper;
import bichel.yauhen.hotel.api.validator.EmailValidator;
import bichel.yauhen.hotel.api.validator.PasswordValidator;
import bichel.yauhen.hotel.api.vo.ErrorResponse;
import bichel.yauhen.hotel.api.vo.RegistrationRequest;
import bichel.yauhen.hotel.api.vo.RegistrationResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

import static bichel.yauhen.hotel.api.Constants.CONTENT_TYPE_JSON;
import static bichel.yauhen.hotel.api.Constants.ERROR_ACCOUNT_DUPLICATE;
import static bichel.yauhen.hotel.api.Constants.ERROR_EMAIL_NOT_VALID;
import static bichel.yauhen.hotel.api.Constants.ERROR_PASSWORD_NOT_VALID;
import static bichel.yauhen.hotel.api.Constants.KEY_ACCESS_CONTROL_ALLOW_ORIGIN;
import static bichel.yauhen.hotel.api.Constants.KEY_SESSION_USER_ID;

public class RegistrationServlet extends HttpServlet {

	private static final Logger logger = LogManager.getLogger(RegistrationServlet.class);

	private final AccountMapper accountMapper;
	private final AccountService accountService;
	private final EmailValidator emailValidator;
	private final PasswordValidator passwordValidator;

	public RegistrationServlet(AccountService accountService,
							   AccountMapper accountMapper,
							   EmailValidator emailValidator,
							   PasswordValidator passwordValidator) {
		super();
		this.accountMapper = accountMapper;
		this.accountService = accountService;
		this.emailValidator = emailValidator;
		this.passwordValidator = passwordValidator;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType(CONTENT_TYPE_JSON);
		response.addHeader(KEY_ACCESS_CONTROL_ALLOW_ORIGIN, "localhost, 127.0.0.1");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setStatus(HttpServletResponse.SC_CREATED);

		RegistrationRequest registerRequest = accountMapper.mapToRegistrationRequest(request);

		if(!emailValidator.isValid(registerRequest.getUsername())) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			PrintWriter out = response.getWriter();

			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setError(ERROR_EMAIL_NOT_VALID);
			errorResponse.setMessage("Email is not valid");
			out.write(JsonUtils.getJsonString(errorResponse));
			return;
		}

		if(!passwordValidator.isValid(registerRequest.getPassword())) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			PrintWriter out = response.getWriter();

			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setError(ERROR_PASSWORD_NOT_VALID);
			errorResponse.setMessage("Password is not valid");
			out.write(JsonUtils.getJsonString(errorResponse));
			return;
		}

		Account account = null;

		try {
			account = accountService.register(registerRequest);
		} catch (AccountDuplicateException ex) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			PrintWriter out = response.getWriter();

			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setError(ERROR_ACCOUNT_DUPLICATE);
			errorResponse.setMessage("User exists");
			out.write(JsonUtils.getJsonString(errorResponse));
			return;
		}

		RegistrationResponse bodyResp = accountMapper.mapToRegistrationResponse(account);
		logger.info("Successfully registered the user " + bodyResp.getUsername());

		//create a new session
		HttpSession session = request.getSession();
		session.setAttribute(KEY_SESSION_USER_ID, String.valueOf(bodyResp.getId()));

		PrintWriter out = response.getWriter();
		out.write(JsonUtils.getJsonString(bodyResp));
	}
}
