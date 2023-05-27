package ae.encodelab.basics.controllers;

import ae.encodelab.basics.data.csv.model.student.Student;
import ae.encodelab.basics.data.csv.model.uni.Department;
import ae.encodelab.basics.service.StudentsService;
import ae.encodelab.basics.service.model.StudentBasicInfo;
import ae.encodelab.basics.service.model.StudentDetailedInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class MainController {
    public static final int DEFAULT_PAGE_SIZE = 10;
    @Value("${spring.application.name}")
    String appName;

    private final StudentsService studentsService;

    public MainController(StudentsService studentsService) {
        this.studentsService = studentsService;
    }

    @GetMapping
    public String homePage(Model model) {
        List<Department> departments = studentsService.loadAllDepartments();
        model.addAttribute("departments", departments);
        return "home";
    }

    @GetMapping("/students")
    private String students(Model model, HttpServletRequest request,  @RequestParam Optional<Integer> page) {
        Page<StudentBasicInfo> studentList = studentsService.loadStudents(page.orElse(0), DEFAULT_PAGE_SIZE);
        model.addAttribute("students", studentList);
        model.addAttribute("url", request.getRequestURI());
        return "students";
    }

    @GetMapping("/department/{code}/students")
    public String students(Model model, HttpServletRequest request, @PathVariable String code, @RequestParam Optional<Integer> page) {
        Page<StudentBasicInfo> studentList = studentsService.loadStudents(code, page.orElse(0), DEFAULT_PAGE_SIZE);
        model.addAttribute("students", studentList);
        String departmentName = studentsService.getDepartmentName(code);
        model.addAttribute("url", request.getRequestURI());
        model.addAttribute("departmentName", departmentName);
        return "students";
    }

    @GetMapping("/student/{id}")
    public String student(Model model, @PathVariable long id) {
        StudentDetailedInfo student = studentsService.loadStudent(id);
        model.addAttribute("student", student);
        return "student";
    }

}