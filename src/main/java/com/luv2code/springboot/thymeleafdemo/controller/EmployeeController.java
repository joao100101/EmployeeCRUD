package com.luv2code.springboot.thymeleafdemo.controller;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/list")
    public String listEmployees(Model theModel) {

        // add to the spring model
        theModel.addAttribute("employees", employeeService.findAllByOrderByLastNameAsc());

        return "employees/list-employees";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel){
        Employee employee = new Employee();

        theModel.addAttribute("employee", employee);

        return "employees/employee-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeID") int theId, Model theModel){
        Employee dbEmployee = employeeService.findById(theId);

        theModel.addAttribute("employee", dbEmployee);

        return "employees/employee-form";

    }
    @GetMapping("/delete")
    public String delete(@RequestParam("employeeID") int theId){
        employeeService.deleteById(theId);

        return "redirect:/employees/list";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("employee")Employee employee){
        employeeService.save(employee);

        return "redirect:/employees/list";
    }
}









