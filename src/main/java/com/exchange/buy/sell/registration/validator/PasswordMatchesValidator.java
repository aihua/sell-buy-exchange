package com.exchange.buy.sell.registration.validator;

import com.exchange.buy.sell.registration.annotation.PasswordMatches;
import com.exchange.buy.sell.market.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by oleht on 13.07.2018
 */
public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        UserDto user = (UserDto) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
