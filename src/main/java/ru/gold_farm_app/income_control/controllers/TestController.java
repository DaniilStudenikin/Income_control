package ru.gold_farm_app.income_control.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping(value = "/")
    public String test(){
        return "resources/templates/index.html";
    }
}
