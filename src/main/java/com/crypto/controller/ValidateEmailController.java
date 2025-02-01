package com.crypto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ValidateEmailController {
    @GetMapping("/validation-email")
    public String showValidationEmailPage() {
        return "auth/validation-email"; // Cela retournera la vue `verify-pin-2fa.html` ou `verify-pin-2fa.jsp`
    }
}
