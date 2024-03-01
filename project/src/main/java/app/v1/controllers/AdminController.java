package app.v1.controllers;

import app.v1.entities.Employee;
import app.v1.entities.Post;
import app.v1.services.impl.PostServiceImpl;
import app.v1.services.interfaces.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final EmployeeService employeeService;

    private final PostServiceImpl postService;


    @Autowired
    public AdminController(EmployeeService employeeService, PostServiceImpl postService) {
        this.employeeService = employeeService;
        this.postService = postService;
    }

    @GetMapping({"", "/", "/all"})
    public String viewAllEmployees(Model model) {
        List<Employee> employees = new ArrayList<>(employeeService.getAll());
        List<Post> posts = new ArrayList<>(postService.getAll());

        model.addAttribute("service", employeeService);
        model.addAttribute("employees", employees);
        model.addAttribute("posts", posts);
        model.addAttribute("newEmployee", new Employee());
        return "admin.html";
    }

    @PostMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam("id") Long id ){
        employeeService.remove(id);
        return "redirect:/admin/employee/";
    }

    @PostMapping("/addEmployee")
    public String addEmployee(@ModelAttribute("newEmployee") Employee newEmployee){
        System.out.println("yoooo");
        employeeService.add(newEmployee);
        return "redirect:/admin/employee/";
    }
}

