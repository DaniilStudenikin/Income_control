package ru.gold_farm_app.income_control.services;

import org.javacord.api.event.message.MessageCreateEvent;
import ru.gold_farm_app.income_control.model.ForSale;

import java.util.List;

public interface ForSaleService {

    ForSale createForSale(List<String[]> forSale, String discordName);

    void delete(Long id, MessageCreateEvent event);

}
