package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EmployeeRepository employeeRepository;

    private static final Logger LOG = LoggerFactory.getLogger(ReportServiceImpl.class);

    /*
        Task 1 ReportStructure implementation
     */
    @Override
    public ReportingStructure report(String id) {
        int totalReports = 0;
        LOG.debug("getting direct reports for employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employee ID: " + id);
        }

        ReportingStructure report = new ReportingStructure();
        report.setEmployee(employee);
        totalReports = calculateTotalReports(employee);
        report.setNumberOfReports(totalReports);

        return report;
    }
    /*
        Logic below for recursively calculating direct reports for given id
     */
    private int calculateTotalReports(Employee employee) {
        int totalReports = 0;
        if (employee.getDirectReports() != null) {
            totalReports =  employee.getDirectReports().size();

            for (Employee report : employee.getDirectReports()) {
                Employee directReport = employeeRepository.findByEmployeeId(report.getEmployeeId());
                totalReports += calculateTotalReports(directReport);
            }
        }
        return totalReports;
    }

}
