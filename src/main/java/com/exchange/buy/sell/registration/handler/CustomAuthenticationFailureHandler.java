package com.exchange.buy.sell.registration.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by oleht on 13.07.2018
 */
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        setDefaultFailureUrl("/login.html?error=true");

        super.onAuthenticationFailure(request, response, exception);

        String errorMessage = "message.badCredentials";

        if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
            errorMessage ="auth.message.disabled";
        } else if (exception.getMessage().equalsIgnoreCase("User account has expired")) {
            errorMessage ="auth.message.expired";
        }

        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
    }
}
