package com.madayV2.blog.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAdvice {

    @ModelAttribute("ctx")
    public String contextPath(HttpServletRequest request) {
        return request.getContextPath();
    }
}
