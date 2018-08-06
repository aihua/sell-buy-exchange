package com.exchange.buy.sell.market.controller;

import com.exchange.buy.sell.market.domain.Privilege;
import com.exchange.buy.sell.market.domain.Role;
import com.exchange.buy.sell.market.domain.UserEntity;
import com.exchange.buy.sell.market.domain.VerificationToken;
import com.exchange.buy.sell.market.dto.PasswordDto;
import com.exchange.buy.sell.market.dto.UserDto;
import com.exchange.buy.sell.market.exception.EmailExistsException;
import com.exchange.buy.sell.market.exception.InvalidOldPasswordException;
import com.exchange.buy.sell.market.exception.UserAlreadyExistException;
import com.exchange.buy.sell.market.exception.UserNotFoundException;
import com.exchange.buy.sell.market.service.SecurityUserService;
import com.exchange.buy.sell.market.service.UserService;
import com.exchange.buy.sell.market.utils.GenericResponse;
import com.exchange.buy.sell.registration.event.OnRegistrationCompleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by oleht on 13.07.2018
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    @Autowired
    private SecurityUserService securityUserService;

    @RequestMapping(value = "/user/registration", method = RequestMethod.POST)
    @ResponseBody
    public GenericResponse registerUserAccount(
            @Valid UserDto accountDto, HttpServletRequest request) {
//        logger.debug("Registering user account with information: {}", accountDto);
        UserEntity registered = createUserAccount(accountDto);
        if (registered == null) {
            throw new UserAlreadyExistException();
        }
        String appUrl = "http://" + request.getServerName() + ":" +
                request.getServerPort() + request.getContextPath();

        eventPublisher.publishEvent(
                new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));

        return new GenericResponse("success");
    }


    @RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
    public String confirmRegistration(final HttpServletRequest request, final Model model, @RequestParam("token") final String token) throws UnsupportedEncodingException {
        Locale locale = request.getLocale();
        final String result = userService.validateVerificationToken(token);
        if (result.equals("valid")) {
            final UserEntity user = userService.getUser(token);
            // if (user.isUsing2FA()) {
            // model.addAttribute("qr", userService.generateQRUrl(user));
            // return "redirect:/qrcode.html?lang=" + locale.getLanguage();
            // }
            authWithoutPassword(user);
            model.addAttribute("message", messages.getMessage("message.accountVerified", null, locale));
            return "redirect:/console.html?lang=" + locale.getLanguage();
        }

        model.addAttribute("message", messages.getMessage("auth.message." + result, null, locale));
        model.addAttribute("expired", "expired".equals(result));
        model.addAttribute("token", token);
        return "redirect:/badUser.html?lang=" + locale.getLanguage();
    }

    @RequestMapping(value = "/user/resendRegistrationToken", method = RequestMethod.GET)
    @ResponseBody
    public GenericResponse resendRegistrationToken(final HttpServletRequest request, @RequestParam("token") final String existingToken) {
        final VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
        final UserEntity user = userService.getUser(newToken.getToken());
        mailSender.send(constructResendVerificationTokenEmail(getAppUrl(request), request.getLocale(), newToken, user));
        return new GenericResponse(messages.getMessage("message.resendToken", null, request.getLocale()));
    }

    @RequestMapping(value = "/user/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public GenericResponse resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) {
        UserEntity user = userService.findUserByEmail(userEmail);
        if (user == null) {
            throw new UserNotFoundException();
        }
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);
        mailSender.send(constructResetTokenEmail(getAppUrl(request),
                request.getLocale(), token, user));
        return new GenericResponse(
                messages.getMessage("message.resetPasswordEmail", null,
                        request.getLocale()));
    }

    @RequestMapping(value = "/user/changePassword", method = RequestMethod.GET)
    public String showChangePasswordPage(Locale locale, Model model,
                                         @RequestParam("id") long id, @RequestParam("token") String token) {
        String result = securityUserService.validatePasswordResetToken(id, token);
        if (result != null) {
            model.addAttribute("message",
                    messages.getMessage("auth.message." + result, null, locale));
            return "redirect:/login?lang=" + locale.getLanguage();
        }
        return "redirect:/updatePassword.html?lang=" + locale.getLanguage();
    }

    @RequestMapping(value = "/user/savePassword", method = RequestMethod.POST)
    @ResponseBody
    public GenericResponse savePassword(Locale locale,
                                        @Valid PasswordDto passwordDto) {
        UserEntity user =
                (UserEntity) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();

        userService.changeUserPassword(user, passwordDto.getNewPassword());
        return new GenericResponse(
                messages.getMessage("message.resetPasswordSuc", null, locale));
    }

    @RequestMapping(value = "/user/updatePassword", method = RequestMethod.POST)
    @PreAuthorize("hasRole('READ_PRIVILEGE')")
    @ResponseBody
    public GenericResponse changeUserPassword(Locale locale,
                                              @RequestParam("password") String password,
                                              @RequestParam("oldpassword") String oldPassword) {
        UserEntity user = userService.findUserByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName());

        if (!userService.checkIfValidOldPassword(user, oldPassword)) {
            throw new InvalidOldPasswordException();
        }
        userService.changeUserPassword(user, password);
        return new GenericResponse(messages.getMessage("message.updatePasswordSuc", null, locale));
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    private UserEntity createUserAccount(UserDto accountDto) {
        try {
            return userService.registerNewUserAccount(accountDto);
        } catch (EmailExistsException e) {
            return null;
        }
    }

    private SimpleMailMessage constructResendVerificationTokenEmail
            (String contextPath, Locale locale, VerificationToken newToken, UserEntity user) {
        String confirmationUrl =
                contextPath + "/regitrationConfirm.html?token=" + newToken.getToken();
        String message = messages.getMessage("message.resendToken", null, locale);
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject("Resend Registration Token");
        email.setText(message + " rn" + confirmationUrl);
        email.setFrom(env.getProperty("support.email"));
        email.setTo(user.getEmail());
        return email;
    }

    private SimpleMailMessage constructResetTokenEmail(
            String contextPath, Locale locale, String token, UserEntity user) {
        String url = contextPath + "/user/changePassword?id=" +
                user.getId() + "&token=" + token;
        String message = messages.getMessage("message.resetPassword",
                null, locale);
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body,
                                             UserEntity user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }


    public void authWithoutPassword(UserEntity user) {
        List<Privilege> privileges = user.getRoles().stream().map(Role::getPrivileges).flatMap(Collection::stream).distinct().collect(Collectors.toList());
        List<GrantedAuthority> authorities = privileges.stream().map(p -> new SimpleGrantedAuthority(p.getName())).collect(Collectors.toList());

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
