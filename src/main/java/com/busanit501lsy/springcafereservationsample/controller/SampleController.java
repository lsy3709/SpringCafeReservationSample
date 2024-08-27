package com.busanit501lsy.springcafereservationsample.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/sample")
public class SampleController {

    @GetMapping("/doA")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<String> doA() {
        return Arrays.asList("123","456","789");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/doB")
    public List<String> doB() {
        return Arrays.asList("Admin123","Admin456","Admin789");
    }
}