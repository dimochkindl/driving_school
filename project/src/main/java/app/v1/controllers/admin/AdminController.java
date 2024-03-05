package app.v1.controllers.admin;

import app.v1.entities.Employee;
import app.v1.entities.Post;
import app.v1.entities.Student;
import app.v1.services.impl.PostServiceImpl;
import app.v1.services.impl.StudentServiceImpl;
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

    private final StudentServiceImpl studentService;


    @Autowired
    public AdminController(EmployeeService employeeService, PostServiceImpl postService, StudentServiceImpl studentService) {
        this.employeeService = employeeService;
        this.postService = postService;
        this.studentService = studentService;
    }

    @GetMapping({"", "/", "/all"})
    public String viewAllEmployees(Model model) {
        List<Employee> employees = new ArrayList<>(employeeService.getAll());
        List<Post> posts = new ArrayList<>(postService.getAll());

        model.addAttribute("service", employeeService);
        model.addAttribute("employees", employees);
        model.addAttribute("posts", posts);
        model.addAttribute("newEmployee", new Employee());
        model.addAttribute("newStudent", new Student());
        model.addAttribute("students", studentService.getAll());
        return "admin.html";
    }

    @PostMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam("id") Long id ){
        employeeService.remove(id);
        return "redirect:/admin/employee/";
    }

    @GetMapping("/employee/addEmployee")
    public String showEmployeeFrom(Model model){
        model.addAttribute("newEmployee", new Employee());
        model.addAttribute("posts", postService.getAll());
        return "addEmployee.html";
    }

    @GetMapping("/student/addStudent")
    public String showStudentForm(Model model){
        model.addAttribute("newStudent", new Student());
        return "addStudent.html";
    }
}

