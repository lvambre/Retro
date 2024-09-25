package com.hoadri.retro.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * A handler for the 403 forbidden error
 * In fact, without this handler, when one 403 error occurs (for the authentication system), only 403 errors occur
 * afterward because a session with bad identification is still created, which creates conflicts to call endpoints
 * The handler invalidates the created session after a 403 error occurs
 */
@Component
public class Handler403Error implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
    }
}
