package ru.gold_farm_app.income_control.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gold_farm_app.income_control.model.Resource;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
