package com.tribex.auth_service.presentation.controller;

import org.springframework.web.bind.annotation.*;

/*
    Test protected APIs
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    /*
        Protected endpoint
     */
    @GetMapping("/hello")
    public String hello() {

        return "Protected API accessed successfully";
    }

    /*
        Admin-only endpoint
     */
    @GetMapping("/admin")
    public String admin() {

        return "Admin API accessed";
    }
}