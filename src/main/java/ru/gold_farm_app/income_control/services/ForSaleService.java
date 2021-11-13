package ru.gold_farm_app.income_control.services;

import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.event.message.MessageCreateEvent;
import ru.gold_farm_app.income_control.model.ForSale;

import java.util.List;

public interface ForSaleService {

    ForSale createForSale(List<String[]> forSale, MessageAuthor employee);

    void delete(Long id);

}
