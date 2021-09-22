package ru.gold_farm_app.income_control.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gold_farm_app.income_control.services.ResourcePriceService;

@RestController
public class ResourcesPriceController {

    @Autowired
    private ResourcePriceService priceService;

    @GetMapping(value = "/prices/add")
    public ResponseEntity<String> addAll() {
        priceService.addAll();
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "prices/update")
    public ResponseEntity<String> updatePrices() {
        priceService.updateData();
        return ResponseEntity.ok().build();
    }
}
