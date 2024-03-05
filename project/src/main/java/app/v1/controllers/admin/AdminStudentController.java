package app.v1.controllers.admin;

import app.v1.entities.Student;
import app.v1.services.impl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/student")
public class AdminStudentController {
    private final StudentServiceImpl studentService;

    @Autowired
    public AdminStudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/add")
    public String addStudent(@ModelAttribute("newStudent") Student newStudent){
        studentService.add(newStudent);
        return "redirect:/admin/";
    }

    @PostMapping("/delete")
    public String deleteEmployee(@RequestParam("id") Long id) {
        studentService.remove(id);
        return "redirect:/admin/";
    }
}
