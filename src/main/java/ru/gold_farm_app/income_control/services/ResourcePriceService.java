package ru.gold_farm_app.income_control.services;

import ru.gold_farm_app.income_control.model.ResourcePrice;

import java.util.Optional;

public interface ResourcePriceService {
    void addAll();

    void updateData();

    void add(String server);

    Optional<ResourcePrice> get(ResourcePrice resourcePrice);
}
