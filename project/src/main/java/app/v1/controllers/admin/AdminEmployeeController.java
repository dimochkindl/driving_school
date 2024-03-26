package app.v1.controllers.admin;

import app.v1.entities.Employee;
import app.v1.entities.Post;
import app.v1.entities.Practice;
import app.v1.services.impl.EmployeeServiceImpl;
import app.v1.services.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/employee")
public class AdminEmployeeController {

    private final EmployeeServiceImpl employeeService;

    private final PostServiceImpl postService;

    @Autowired
    public AdminEmployeeController(EmployeeServiceImpl employeeService, PostServiceImpl postService) {
        this.employeeService = employeeService;
        this.postService = postService;
    }


    @PostMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam("id") Long id) {
        employeeService.remove(id);
        return "redirect:/admin/";
    }

    @PostMapping("/add")
    public String addEmployee(@ModelAttribute("newEmployee") Employee newEmployee) {
        System.out.println("yoooo");
        employeeService.add(newEmployee);
        return "redirect:/admin/";
    }

    @PostMapping("/edit")
    public String editEmployee(@ModelAttribute Employee employee, @ModelAttribute Post post) {
        employeeService.update(employee);
        postService.update(post);
        return "redirect:/admin/";
    }

    //do it
    @PostMapping("/editPractices")
    public String editEmployeePractice(@ModelAttribute Employee employee, @ModelAttribute Practice practice) {
        employeeService.update(employee);

        return "redirect:/admin/";
    }
}
