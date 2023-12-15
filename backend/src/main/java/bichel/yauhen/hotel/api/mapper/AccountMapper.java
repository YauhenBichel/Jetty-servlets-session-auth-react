package bichel.yauhen.hotel.api.mapper;

import bichel.yauhen.hotel.api.model.Account;
import bichel.yauhen.hotel.api.vo.LoginRequest;
import bichel.yauhen.hotel.api.vo.LoginResponse;
import bichel.yauhen.hotel.api.vo.RegistrationRequest;
import bichel.yauhen.hotel.api.vo.RegistrationResponse;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Mapper for Account
 */
public class AccountMapper {
    /**
     * Maps request RegistrationRequest to model Account
     * @param request RegistrationRequest
     * @return Account
     */
    public Account mapToModel(RegistrationRequest request) {
        Account model = new Account();
        model.setUsername(request.getUsername());
        model.setPassword(request.getPassword());
        model.setCreated(LocalDateTime.now());
        model.setModified(model.getCreated());

        return model;
    }

    public Account mapToModel(LoginRequest request) {
        Account model = new Account();
        model.setUsername(request.getUsername());
        model.setPassword(request.getPassword());
        model.setModified(LocalDateTime.now());

        return model;
    }

    public RegistrationRequest mapToRegistrationRequest(HttpServletRequest servletRequest) throws IOException {
        Gson gson = new Gson();
        String body = servletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        return gson.fromJson(body, RegistrationRequest.class);
    }

    public RegistrationResponse mapToRegistrationResponse(Account account) {
        RegistrationResponse response = new RegistrationResponse();
        response.setId(account.getId());
        response.setUsername(account.getUsername());
        response.setModified(account.getModified().toString());
        response.setCreated(account.getCreated().toString());

        return response;
    }

    public LoginRequest mapToLoginRequest(HttpServletRequest servletRequest) throws IOException {
        Gson gson = new Gson();
        String body = servletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        return gson.fromJson(body, LoginRequest.class);
    }

    public LoginResponse mapToLoginResponse(Account account) {
        LoginResponse response = new LoginResponse();
        response.setId(account.getId());
        response.setUsername(account.getUsername());
        response.setModified(account.getModified() == null ?
                account.getCreated().toString() :
                account.getModified().toString());
        response.setCreated(account.getCreated().toString());

        return response;
    }
}
