package ru.gold_farm_app.income_control.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gold_farm_app.income_control.model.Resource;
import ru.gold_farm_app.income_control.model.ResourcePrice;
import ru.gold_farm_app.income_control.model.ServerFraction;

import java.util.Optional;

@Repository
public interface ResourcePriceRepository extends JpaRepository<ResourcePrice, Long> {
    Optional<ResourcePrice> findByResourceAndServer(Resource resource, ServerFraction serverFraction);
}
