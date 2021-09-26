package ru.gold_farm_app.income_control.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gold_farm_app.income_control.model.ServerFraction;
import ru.gold_farm_app.income_control.repository.ServerFractionRepository;
import ru.gold_farm_app.income_control.services.ServerFractionService;

import java.util.List;

@Service
public class ServerFractionServiceImpl implements ServerFractionService {

    @Autowired
    private ServerFractionRepository serverFractionRepository;

    @Override
    public void addServerFraction(String server) {
        serverFractionRepository.save(ServerFraction.builder()
                .serverFraction(server)
                .build());
    }

    @Override
    public List<ServerFraction> getAllServerFractions() {
        return serverFractionRepository.findAll();
    }
}
