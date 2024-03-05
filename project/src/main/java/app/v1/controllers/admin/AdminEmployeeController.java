package app.v1.controllers.admin;

import app.v1.entities.Employee;
import app.v1.services.impl.EmployeeServiceImpl;
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

    @Autowired
    public AdminEmployeeController(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
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

    @PostMapping("/editEmployee")
    public String editEmployee(@ModelAttribute Employee employee) {

        Long id = employee.getId();
        String name = employee.getName();
        String surname = employee.getSurname();
        String phone = employee.getPhone();
        float experience = employee.getExperience();
        Long postId = employee.getPost().getId();

        System.out.println("ediiiiiiit");

        // Ваша логика для обновления сотрудника в базе данных

        // После обновления сотрудника, можно вернуться на страницу сотрудников
        return "redirect:/admin/";
    }
}
