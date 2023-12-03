package org.sawtooth.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.sawtooth.models.customer.Customer;
import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private IStorage storage;

    @Autowired
    public AuthenticationController(IStorage storage) {
        this.storage = storage;
    }

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
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Customer customer = storage.GetRepository(ICustomerRepository.class).Get(authentication.getName());

            if (authentication instanceof AnonymousAuthenticationToken)
                return false;
            return authentication.isAuthenticated() && customer.verified();
        }
        catch (Exception exception) {
            return  false;
        }
    }
}
