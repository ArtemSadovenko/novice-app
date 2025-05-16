package com.novice.debatenovice.aspects;

import com.novice.debatenovice.dto.UserDTO;
import com.novice.debatenovice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

@Aspect
@Component
@Getter
public class SecurityHandlerAspect {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserService userService;

    protected Long extractPathVariable(String paramName) {
        Map<String, String> uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String value = uriVars.get(paramName);
        if (value == null) {
            throw new IllegalArgumentException("Missing path variable: " + paramName);
        }
        return Long.parseLong(value);
    }

    protected UserDTO getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new IllegalStateException("No authentication found");
        }
        UserDetails value = (UserDetails) auth.getPrincipal();
        return userService.getUserByEmail(value.getUsername());
    }


}
