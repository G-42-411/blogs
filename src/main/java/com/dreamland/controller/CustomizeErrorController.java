package com.dreamland.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomizeErrorController implements ErrorController {

    @RequestMapping("/error")
    public String errorHandler(HttpServletRequest request){
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        switch (statusCode){
            case 404:
                return "/error/404";
            case 500:
                return "/error/500";
            default:
                return "/error/500";
        }
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
