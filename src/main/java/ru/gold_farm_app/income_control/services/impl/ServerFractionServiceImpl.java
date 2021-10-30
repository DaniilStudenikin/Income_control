package ru.gold_farm_app.income_control.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gold_farm_app.income_control.model.ServerFraction;
import ru.gold_farm_app.income_control.repository.ServerFractionRepository;
import ru.gold_farm_app.income_control.services.ServerFractionService;


@Service
public class ServerFractionServiceImpl implements ServerFractionService {

    @Autowired
    private ServerFractionRepository serverFractionRepository;

    //adding server-fraction to Data Base from parsed string(!auth gehennas-horde)
    @Override
    public void addServer(String server) {
        serverFractionRepository.save(ServerFraction.builder()
                .serverFraction(server)
                .build());
    }

    @Override
    public void deleteServer(String server) {
        serverFractionRepository.delete(serverFractionRepository.findByServerFraction(server)
                .orElseThrow(IllegalArgumentException::new));
    }
}
