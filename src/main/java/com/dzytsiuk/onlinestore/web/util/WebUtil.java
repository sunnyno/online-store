package com.dzytsiuk.onlinestore.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

public class WebUtil {
    private static final String USER_TOKEN_COOKIE = "user-token";

    public static Optional<Cookie> getCurrentSessionCookie(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        Optional<Cookie> tokenCookie = Optional.empty();
        if (cookies != null) {
            tokenCookie = Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals(USER_TOKEN_COOKIE))
                    .findFirst();
        }
        return tokenCookie;
    }
}
