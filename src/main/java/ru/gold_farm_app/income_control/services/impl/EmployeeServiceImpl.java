package ru.gold_farm_app.income_control.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gold_farm_app.income_control.model.Employee;
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
    public Integer add(Employee employee) {
        if (get(employee).isEmpty()) {
            employeeRepository.save(employee);
            return 1;
        } else return 0;

    }


    @Override
    public Optional<Employee> get(Employee employee) {
        return Optional.ofNullable(employeeRepository.findByDiscordName(employee.getDiscordName()));
    }

    @Override
    public void addIncome(String discordName, Long gold) {
        Employee employee = employeeRepository.findByDiscordName(discordName);
        logger.info("Before adding income: " + employee.getGold());
        employee.setGold(gold + employee.getGold());
        logger.info("After adding income: " + employee.getGold());
        employeeRepository.save(employee);
    }

    @Override
    public void delete(Employee employee) {
        employeeRepository.delete(employee);
    }
}
