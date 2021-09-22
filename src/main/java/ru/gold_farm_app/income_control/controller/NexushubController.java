package ru.gold_farm_app.income_control.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.gold_farm_app.income_control.model.NexushubUser;
import ru.gold_farm_app.income_control.services.NexushubService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Controller
public class NexushubController {
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private NexushubService service;

    @PostMapping(value = "/register")
    @ResponseBody
    public String register() {
        service.register();
        return "";
    }

    @PostMapping(value = "/authenticate")
    public String authenticate() {
        service.authenticate();
        return "";
    }
}
