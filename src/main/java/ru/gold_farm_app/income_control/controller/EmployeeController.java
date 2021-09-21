package ru.gold_farm_app.income_control.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gold_farm_app.income_control.model.Employee;


@RestController
public class EmployeeController {

    @GetMapping(value = "/test")
    public String test() {
        return "";
    }
}
