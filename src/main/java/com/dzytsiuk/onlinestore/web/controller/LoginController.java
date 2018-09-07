package com.dzytsiuk.onlinestore.web.controller;

import com.dzytsiuk.onlinestore.entity.security.Session;
import com.dzytsiuk.onlinestore.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
public class LoginController {
    private static final String USER_TOKEN_PARAMETER_NAME = "user-token";
    private SecurityService securityService;

    @Autowired
    public LoginController(SecurityService securityService) {
        this.securityService = securityService;
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginPage() {
        return "login.html";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String doLogin(@RequestParam String login, @RequestParam String password, HttpServletResponse resp, ModelMap modelMap) {
        Optional<Session> optionalSession = securityService.auth(login, password);
        Optional<Cookie> cookie = optionalSession.map(this::createCookie);
        String viewString = "redirect:/products";
        if(cookie.isPresent()){
            resp.addCookie(cookie.get());
        }else{
            modelMap.addAttribute("errorMessage", "Invalid login/password");
            viewString = "login.html";
        }
        return viewString;
    }

    private Cookie createCookie(Session session) {
        Cookie cookie = new Cookie(USER_TOKEN_PARAMETER_NAME, session.getToken());
        long secondsToExpire = securityService.getSessionTimeToLive(session);
        cookie.setMaxAge(Math.toIntExact(secondsToExpire));
        cookie.setHttpOnly(true);
        return cookie;
    }
}
