package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee")
    public Employee create(@RequestBody Employee employee) {
        LOG.debug("Received employee create request for [{}]", employee);

        return employeeService.create(employee);
    }
    /*
        Task 2 Compensation POST endpoint
     */
    @PostMapping("/employee/compensation")
    public Compensation setCompensation(@RequestBody Compensation compensation) {
        LOG.debug("Received compensation create request");

        return employeeService.createCompensation(compensation);
    }
    /*
        Task 2 Compensation GET endpoint
     */
    @GetMapping("/employee/compensation/{employeeId}")
    public Compensation getCompensation(@PathVariable String employeeId) {
        LOG.debug("Received compensation request for id [{}]", employeeId);
        return employeeService.getCompensation(employeeId);
    }

    @GetMapping("/employee/{employeeId}")
    public Employee read(@PathVariable String employeeId) {
        LOG.debug("Received employee create request for id [{}]", employeeId);

        return employeeService.read(employeeId);
    }

    @PutMapping("/employee/{employeeId}")
    public Employee update(@PathVariable String employeeId, @RequestBody Employee employee) {
        LOG.debug("Received employee create request for id [{}] and employee [{}]", employeeId, employee);

        employee.setEmployeeId(employeeId);
        return employeeService.update(employee);
    }
}
