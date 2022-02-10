package com.simple.spring.app.validation;

import com.simple.spring.app.entity.User;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

@Component
public class UserValidator {

    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");

    public void validate(User user) throws RuntimeException {
        if (!StringUtils.hasText(user.getEmail())) {
            throw new BadCredentialsException("Email cannot be null or empty.");
        }
        if (!StringUtils.hasText(user.getPassword())) {
            throw new BadCredentialsException("Password cannot be null or empty.");
        }
        if (user.getPassword().length() < PASSWORD_MIN_LENGTH) {
            throw new BadCredentialsException(String.format("Password must contain at least %d characters.", PASSWORD_MIN_LENGTH));
        }
        if (!VALID_EMAIL_ADDRESS_REGEX.matcher(user.getEmail()).matches()) {
            throw new BadCredentialsException("The email address is not valid, please enter a valid email address");
        }
    }

}
