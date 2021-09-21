package ru.gold_farm_app.income_control.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gold_farm_app.income_control.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
