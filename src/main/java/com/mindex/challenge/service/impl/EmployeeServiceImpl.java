package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompensationRepository compensationRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String employeeId) {
        LOG.debug("Creating employee with id [{}]", employeeId);

        Employee employee = employeeRepository.findByEmployeeId(employeeId);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }

        return employee;
    }

    @Override
    public Compensation createCompensation(Compensation compensation) {
        LOG.debug("creating compensation for employee");

        compensationRepository.save(compensation);

        return compensation;
    }

    @Override
    public Compensation getCompensation(String id) {
        LOG.debug("Creating compensation for employee with id [{}]", id);

        Compensation compensation = compensationRepository.findAllByEmployeeId(id);

        if (compensation == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return compensation;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }
}
