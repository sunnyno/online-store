package com.dzytsiuk.onlinestore.web.controller;

import com.dzytsiuk.onlinestore.entity.security.AuthPrincipal;
import com.dzytsiuk.onlinestore.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LogoutController {
    private SecurityService securityService;

    @Autowired
    public LogoutController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String doLogout(AuthPrincipal authPrincipal) {
        securityService.logout(authPrincipal);
        return "redirect:/login";
    }
}
