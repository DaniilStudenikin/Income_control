package ru.gold_farm_app.income_control.services.impl;

import org.javacord.api.entity.message.MessageAuthor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gold_farm_app.income_control.model.Employee;
import ru.gold_farm_app.income_control.model.ServerFraction;
import ru.gold_farm_app.income_control.repository.EmployeeRepository;
import ru.gold_farm_app.income_control.services.EmployeeService;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Override
    @Transactional
    public Boolean add(MessageAuthor employee, ServerFraction server) {

        if (isCreated(employee)) {
            employeeRepository.save(Employee.builder()
                    .discordName(employee.getDiscriminatedName())
                    .server(server)
                    .gold(0L)
                    .discordId(employee.getIdAsString())
                    .build());
            return true;
        } else
            return false;

    }

    @Override
    public void update(MessageAuthor employee) {
        Employee employeeFromDb = employeeRepository.findByDiscordId(employee.getIdAsString()).orElseThrow(IllegalArgumentException::new);
        employeeFromDb.setDiscordName(employee.getDiscriminatedName());
        employeeFromDb.setDiscordId(employee.getIdAsString());
        employeeRepository.save(employeeFromDb);
    }


    @Override
    public Boolean isCreated(MessageAuthor employee) {
        return employeeRepository.findByDiscordId(employee.getIdAsString()).isEmpty();
    }

    @Override
    public void addIncome(Employee employee, Long gold) {
        logger.info("Before adding income: " + employee.getGold());
        employee.setGold(gold + employee.getGold());
        logger.info("After adding income: " + employee.getGold());
        employeeRepository.save(employee);
    }

    @Override
    public void delete(Long id) {
        employeeRepository.getById(id).setIsDeleted(true);
    }
}
