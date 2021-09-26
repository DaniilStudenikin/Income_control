package ru.gold_farm_app.income_control.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gold_farm_app.income_control.model.ForSale;
import ru.gold_farm_app.income_control.repository.ForSaleRepository;
import ru.gold_farm_app.income_control.services.ForSaleService;



@Service
public class ForSaleServiceImpl implements ForSaleService {

    @Autowired
    private ForSaleRepository repository;


    @Override
    public void add(ForSale forSale) {
        repository.save(forSale);
    }


}
