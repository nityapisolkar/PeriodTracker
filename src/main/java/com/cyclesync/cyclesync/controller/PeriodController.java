package com.cyclesync.cyclesync.controller;

import com.cyclesync.cyclesync.model.PeriodEntry;
import com.cyclesync.cyclesync.service.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PeriodController {

    @GetMapping("/")
    public String home() {
        return "✅ Home is working!";
    }

    @GetMapping("/ping")
    public String ping() {
        return "✅ spong is working!";
    }
}
