package ru.gold_farm_app.income_control.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gold_farm_app.income_control.model.Employee;
import ru.gold_farm_app.income_control.repository.EmployeeRepository;
import ru.gold_farm_app.income_control.services.EmployeeService;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void add(Employee employee) {
        employeeRepository.save(employee);
    }


    @Override
    public Optional<Employee> get(Employee employee) {
        return Optional.of(employeeRepository.getById(employee.getId()));
    }

    @Override
    public void addIncome(String discordName, Long gold) {
        Employee employee = employeeRepository.findByDiscordName(discordName);
        employee.setGold(gold);
        employeeRepository.save(employee);
    }

    @Override
    public void delete(Employee employee) {
        employeeRepository.delete(employee);
    }
}
