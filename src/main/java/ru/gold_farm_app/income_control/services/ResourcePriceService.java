package ru.gold_farm_app.income_control.services;

import ru.gold_farm_app.income_control.model.ResourcePrice;

import java.util.Optional;

public interface ResourcePriceService {
    void add(ResourcePrice resourcePrice);

    Optional<ResourcePrice> update(ResourcePrice resourcePrice);

    Optional<ResourcePrice> get(ResourcePrice resourcePrice);
}
