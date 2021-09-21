package ru.gold_farm_app.income_control.services;

import ru.gold_farm_app.income_control.model.Employee;

import java.util.Optional;

public interface EmployeeService {
    void add(Employee employee);

    Optional<Employee> get(Employee employee);

}
