package com.mindex.challenge.controller;

import com.mindex.challenge.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.mindex.challenge.data.ReportingStructure;

@RestController
@RequestMapping("/report")
public class ReportingController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private ReportService reportService;
    /*
        Task 1 direct report GET endpoint
     */
    @GetMapping("/{id}")
    public ReportingStructure report(@PathVariable String id) {
        LOG.debug("Received employee create request for id [{}]", id);

        return reportService.report(id);

    }
}
