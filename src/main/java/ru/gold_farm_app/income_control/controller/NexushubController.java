package ru.gold_farm_app.income_control.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NexushubController {

    @PostMapping(value = "auth.nexushub.co/register")
    public String register(){
        return "";
    }
}
