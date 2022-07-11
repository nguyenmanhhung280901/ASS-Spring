package com.example.assspring.controller;

import com.example.assspring.entity.EmployeeEntity;
import com.example.assspring.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class WebController {

    @Autowired
    EmployeeRepo employeeRepo;

    @GetMapping(value = {"/"})
    public String index(){
        return "index";
    }

    @GetMapping(value = {"/admin"})
    public String admin(){
        return "admin";
    }

    @GetMapping(value = { "/user"})
    public String user(){
        return "user";
    }

    @GetMapping(value = { "/401"})
    public String error(){
        return "401";
    }

    @GetMapping("/signup")
    public String showSignUpForm(EmployeeEntity employee) {
        return "add-employee";
    }

    @PostMapping("/addemployee")
    public String addUser(@Valid EmployeeEntity employee, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-employee";
        }

        employeeRepo.save(employee);
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String showUserList(Model model) {
        model.addAttribute("employees", employeeRepo.findAll());
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        EmployeeEntity employee = employeeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Employee Id:" + id));

        model.addAttribute("employee", employee);
        return "update-employee";
    }

//    @PostMapping("/update/{id}")
//    public String updateEmployee(@PathVariable("id") long id, @Valid EmployeeEntity employee,
//                             BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            employee.setId(id);
//            return "update-employee";
//        }
//
//        employeeRepo.save(employee);
//        return "redirect:/index";
//    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        EmployeeEntity employee = employeeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        employeeRepo.delete(employee);
        return "redirect:/index";
    }
}
