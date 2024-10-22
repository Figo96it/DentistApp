package com.damian.figinski.dentistapp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
public class RedirectController {

    @GetMapping("/redirect")
    public ModelAndView redirectAfterLogin(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        boolean isAdmin = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        boolean isPatient = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_PATIENT"));
        boolean isDentist = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_DENTIST"));

        if (isAdmin) {
            System.out.println("admin");
            return new ModelAndView("redirect:/admin/dashboard");
        } else if (isPatient) {
            return new ModelAndView("redirect:/patient/dashboard");
        } else if (isDentist) {
            return new ModelAndView("redirect:/dentist/dashboard");
        }
        // Domyślnie, jeśli użytkownik nie ma żadnej z powyższych ról
        return new ModelAndView("redirect:/");
    }
}
