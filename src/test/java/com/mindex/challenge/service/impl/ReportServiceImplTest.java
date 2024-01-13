package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportServiceImplTest {
    private String reportUrl;
    private String employeeUrl;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ReportServiceImpl reportService;
    @Autowired
    private EmployeeService employeeService;
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        reportUrl = "http://localhost:" + port + "/report/{id}";
        employeeUrl = "http://localhost:" + port + "/employee";
    }

    @Test
    public void testReport() {
        //clearing employee database
        employeeRepository.deleteAll();

        List<Employee> directReports = new ArrayList<Employee>();
        List<Employee> subDirectReports = new ArrayList<Employee>();

        subDirectReports.add(createEmployee("subDirectReport1", "4", null));
        directReports.add(createEmployee("directReport1", "3",subDirectReports));
        directReports.add(createEmployee("directReport2","2",null));
        Employee mainEmployee = createEmployee("John", "1", directReports);

        ReportingStructure reportingStructure = restTemplate.getForEntity(reportUrl, ReportingStructure.class, "1").getBody();

        assertNotNull(reportingStructure);
        assertEquals(3, reportingStructure.getNumberOfReports());
        assertEquals("John", reportingStructure.getEmployee().getFirstName());
        assertEquals("1", reportingStructure.getEmployee().getEmployeeId());

    }
    @Test
    public void testReportNoDirectReports() {
        //clearing employee database
        employeeRepository.deleteAll();

        Employee mainEmployee = createEmployee("John", "1", null);
        ReportingStructure reportingStructure = restTemplate.getForEntity(reportUrl, ReportingStructure.class, "1").getBody();

        assertNotNull(reportingStructure);
        assertEquals(0, reportingStructure.getNumberOfReports());
        assertEquals("John", reportingStructure.getEmployee().getFirstName());
        assertEquals("1", reportingStructure.getEmployee().getEmployeeId());


    }
    public Employee createEmployee(String name, String id, List<Employee> directReports ){
        Employee testEmployee = new Employee();
        testEmployee.setFirstName(name);
        testEmployee.setEmployeeId(id);
        testEmployee.setDirectReports(directReports);
        employeeRepository.insert(testEmployee);

        return testEmployee;
    }

}
