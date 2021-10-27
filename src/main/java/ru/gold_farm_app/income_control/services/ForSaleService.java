package ru.gold_farm_app.income_control.services;

import ru.gold_farm_app.income_control.model.ForSale;

import java.util.List;

public interface ForSaleService {

    ForSale createForSale(List<String[]> forSale, String discordName);


}
