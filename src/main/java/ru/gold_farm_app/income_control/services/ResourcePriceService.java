package ru.gold_farm_app.income_control.services;

import org.javacord.api.event.message.MessageCreateEvent;
import ru.gold_farm_app.income_control.model.ResourcePrice;

import java.util.List;

public interface ResourcePriceService {
    void priceAdd();

    void updatePrice();

    List<ResourcePrice> resourcePrices(MessageCreateEvent event);

    List<ResourcePrice> resourcePricesByServer(MessageCreateEvent event, Long server);
}
