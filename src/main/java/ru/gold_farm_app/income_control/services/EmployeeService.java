package ru.gold_farm_app.income_control.services;

import org.javacord.api.entity.message.MessageAuthor;
import ru.gold_farm_app.income_control.model.Employee;
import ru.gold_farm_app.income_control.model.ServerFraction;

import java.util.Optional;

public interface EmployeeService {
    Boolean add(MessageAuthor employee, ServerFraction server);

    void update(MessageAuthor employee);

    Boolean isCreated(MessageAuthor employee);

    void addIncome(Employee employee, Long gold);

    void delete(Long id);
}
