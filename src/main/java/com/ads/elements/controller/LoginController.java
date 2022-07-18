package com.ads.elements.controller;

import com.ads.elements.model.User;
import com.ads.elements.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    private CustomUserDetailsService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register() {
        final ModelAndView modelAndView = new ModelAndView();
        final User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute @Validated final User user, final BindingResult bindingResult) {
        final ModelAndView registerView = new ModelAndView("register");
        final ModelAndView loginView = new ModelAndView("login");

        if (bindingResult.hasErrors()) {
            bindingResult.reject("registration.error", "Correct the errors and try again");
            return registerView;
        }

        final User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult.reject("registration.error", "Correct the errors and try again");
            bindingResult.rejectValue("email", "error.user","There is already a user registered with the supplied email");
            return registerView;
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.user", "Passwords don't match");
            return registerView;
        }

        userService.saveUserAsClient(user);
        loginView.addObject("successMessage", "User has been registered successfully");
        loginView.addObject("user", new User());
        return loginView;
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboard() {
        final ModelAndView modelAndView = new ModelAndView();
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("fullName", "Welcome " + user.getFirstName() + " " + user.getLastName());
        modelAndView.addObject("adminMessage", "Content available only for users with ADMIN role");
        modelAndView.setViewName("dashboard");
        return modelAndView;
    }

    @RequestMapping(value = {"/","/home"}, method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }

}
