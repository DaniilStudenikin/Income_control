package ru.gold_farm_app.income_control.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gold_farm_app.income_control.model.Employee;
import ru.gold_farm_app.income_control.repository.EmployeeRepository;

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
}
