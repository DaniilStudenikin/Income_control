package ru.gold_farm_app.income_control.services;

import ru.gold_farm_app.income_control.model.ServerFraction;

import java.util.List;

public interface ServerFractionService {
    void addServer(String server);

    void deleteServer(String server);

//    List<ServerFraction> getAllServerFractions();
}
