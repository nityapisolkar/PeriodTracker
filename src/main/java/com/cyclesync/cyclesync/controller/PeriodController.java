package com.cyclesync.cyclesync.controller;

import com.cyclesync.cyclesync.model.PeriodEntry;
import com.cyclesync.cyclesync.service.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.time.LocalDate;

@RestController
public class PeriodController {

    private final PeriodService periodService;

    public PeriodController(PeriodService periodService) {
        this.periodService = periodService;
    }

    // for testing vv
    // @GetMapping("/")
    // public String home() {
    // return "✅ Home is working!";
    // }

    @GetMapping("/ping")
    public String ping() {
        return "✅ pong is working!";
    }
    // for testing ^^

    // get all period entries
    @GetMapping("/periods")
    public List<PeriodEntry> getAllPeriods() {
        return periodService.getAllPeriods();
    }

    // add a new period entry (POST w json body)
    @PostMapping("/periods")
    public String addPeriod(@RequestBody PeriodEntry newEntry) {
        System.out.println("ADDING NEW PERIOD: " + newEntry);
        periodService.addPeriod(newEntry);
        return "Added period starting " + newEntry.getStartDate();
    }

    // update a period entry by index (put with json body)
    @PutMapping("/periods/{index}")
    public void updatePeriod(@PathVariable int index, @RequestBody PeriodEntry updatedEntry) {
        periodService.updatePeriod(index, updatedEntry);
    }

    @DeleteMapping("/periods/{index}")
    public void deletePeriod(@PathVariable int index) {
        periodService.deletePeriod(index);
    }

    // get prediction of next start date for period
    @GetMapping("/periods/predict")
    public LocalDate predictNextPeriod() {
        return periodService.predictNextPeriod();
    }
}
