package ru.gold_farm_app.income_control.listeners.impl;

import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.gold_farm_app.income_control.listeners.ResourcePriceListener;
import ru.gold_farm_app.income_control.services.ResourcePriceService;

@Component
public class ResourcePriceListenerImpl implements ResourcePriceListener {

    @Autowired
    private ResourcePriceService resourcePriceService;

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().equals("!priceAdd") && event.getMessageAuthor().getDisplayName().equals("fastrapier1")) {
            resourcePriceService.priceAdd();
            event.getChannel().sendMessage("All prices are updated!");
        }
        if (event.getMessageContent().equals("!priceUpdate") && event.getMessageAuthor().getDisplayName().equals("fastrapier1")) {
            resourcePriceService.updatePrice();
            event.getChannel().sendMessage("All prices are updated!");
        }
    }
}
