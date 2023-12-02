package org.sawtooth.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    @GetMapping("/get/username")
    @ResponseBody
    public String GetUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping("/logout")
    public void Logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("JSESSIONID", "");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @GetMapping("/check")
    @ResponseBody
    public boolean Check() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken)
            return false;
        return authentication.isAuthenticated();
    }
}
