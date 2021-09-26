package ru.gold_farm_app.income_control.services;

import ru.gold_farm_app.income_control.model.Resource;

import java.util.List;

public interface ResourceService {

    void addAll();

    void updatePrice(Long id);

    void updateAll();

    List<Resource> getAllResources();
}
