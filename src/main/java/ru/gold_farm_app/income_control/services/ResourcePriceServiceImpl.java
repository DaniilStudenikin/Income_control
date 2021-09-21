package ru.gold_farm_app.income_control.services;

import org.springframework.beans.factory.annotation.Autowired;
import ru.gold_farm_app.income_control.model.ResourcePrice;
import ru.gold_farm_app.income_control.repository.ResourcePriceRepository;

import java.util.Optional;

public class ResourcePriceServiceImpl implements ResourcePriceService {

    @Autowired
    private ResourcePriceRepository repository;

    @Override
    public void add(ResourcePrice resourcePrice) {
        repository.save(resourcePrice);
    }

    @Override
    public Optional<ResourcePrice> update(ResourcePrice resourcePrice) {
        return Optional.of(repository.save(resourcePrice));
    }

    @Override
    public Optional<ResourcePrice> get(ResourcePrice resourcePrice) {
        return Optional.of(repository.getById(resourcePrice.getId()));
    }
}
