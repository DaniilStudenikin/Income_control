package ru.gold_farm_app.income_control.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.gold_farm_app.income_control.model.Resource;
import ru.gold_farm_app.income_control.model.ResourcePrice;
import ru.gold_farm_app.income_control.model.ServerFraction;
import ru.gold_farm_app.income_control.repository.ResourceRepository;
import ru.gold_farm_app.income_control.services.ResourceService;
import ru.gold_farm_app.income_control.services.ServerFractionService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

@Service
public class ResourceServiceImpl implements ResourceService {

    private static final Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);
    @Autowired
    private ResourceRepository resourceRepository;


    @Override
    public void add(Long itemId) {

    }

    @Override
    public void delete(Long itemId) {

    }

    @Override
    public List<Resource> findAll() {
        return resourceRepository.findAll();
    }
}
