package com.exchange.buy.sell.market.controller;

import com.exchange.buy.sell.market.domain.UserEntity;
import com.exchange.buy.sell.market.dto.UserDto;
import com.exchange.buy.sell.market.exception.EmailExistsException;
import com.exchange.buy.sell.market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Created by oleht on 13.07.2018
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    //TODO rewrite to Angular reality
    @RequestMapping(value = "/user/registration", method = RequestMethod.POST)
    public ModelAndView registerUserAccount(@Valid UserDto accountDto) {

        UserEntity registered = new UserEntity();
//        if (!result.hasErrors()) {
//            registered = createUserAccount(accountDto, result);
//        }
//        if (registered == null) {
//            result.rejectValue("email", "message.regError");
//        }
//        if (result.hasErrors()) {
//            return new ModelAndView("registration", "user", accountDto);
//        } else {
            return new ModelAndView("successRegister", "user", accountDto);
//        }
    }

    private UserEntity createUserAccount(UserDto accountDto, BindingResult result) {
        UserEntity registered = null;
        try {
            registered = userService.registerNewUserAccount(accountDto);
        } catch (EmailExistsException e) {
            return null;
        }
        return registered;
    }

}
