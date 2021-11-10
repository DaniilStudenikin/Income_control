package ru.gold_farm_app.income_control.services;

import ru.gold_farm_app.income_control.model.Resource;

import java.util.List;

public interface ResourceService {
    void add(Long itemId);

    void delete(Long itemId);

    List<Resource> findAll();
}
