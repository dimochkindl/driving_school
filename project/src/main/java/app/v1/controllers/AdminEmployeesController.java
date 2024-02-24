package app.v1.controllers;

import app.v1.services.interfaces.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@ComponentScan(basePackages = "app.v1.services")
@RequestMapping(name = "/admin/employee")
public class AdminEmployeesController {
    private final EmployeeService employeeService;

    @Autowired
    public AdminEmployeesController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


}
