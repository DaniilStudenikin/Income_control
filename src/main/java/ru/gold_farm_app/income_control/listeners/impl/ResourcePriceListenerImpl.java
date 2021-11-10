package ru.gold_farm_app.income_control.listeners.impl;

import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.gold_farm_app.income_control.listeners.ResourcePriceListener;
import ru.gold_farm_app.income_control.model.ResourcePrice;
import ru.gold_farm_app.income_control.services.ResourcePriceService;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class ResourcePriceListenerImpl implements ResourcePriceListener {
    private static final Logger LOG = LoggerFactory.getLogger(ResourcePriceListenerImpl.class);
    @Autowired
    private ResourcePriceService resourcePriceService;

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().equals("!priceAdd") && event.getMessageAuthor().getDisplayName().equals("fastrapier")) {
            resourcePriceService.priceAdd();
            event.getChannel().sendMessage("All prices are updated!");
        }
        if (event.getMessageContent().equals("!priceUpdate") && event.getMessageAuthor().getDisplayName().equals("fastrapier")) {
            resourcePriceService.updatePrice();
            event.getChannel().sendMessage("All prices are updated!");
        }
        if (event.getMessageContent().equals("!resource-price")) {
            List<ResourcePrice> resourcePrices = resourcePriceService.resourcePrices(event);
            for (ResourcePrice resourcePrice : resourcePrices) {
                EmbedBuilder resourcePriceMessage = new EmbedBuilder()
                        .setTitle(resourcePrice.getResource().getName())
                        .addField("Цена за единицу ресурса", splitPrice(resourcePrice.getPrice()))
                        .addField("Время последнего сканирования", resourcePrice.getScannedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .setColor(Color.BLUE)
                        .setImage(resourcePrice.getResource().getIcon().toString());

                event.getMessageAuthor().asUser().orElseThrow(IllegalArgumentException::new).sendMessage(resourcePriceMessage);
            }
//                new MessageBuilder().setEmbed(resourcePriceMessage)
//                        .send(event.getMessageAuthor().asUser().orElseThrow(IllegalArgumentException::new));
//                new MessageBuilder()
//                        .addAttachment(resourcePrice.getResource().getIcon())
//                        .append("Resource name: " + resourcePrice.getResource().getName() + "\n")
//                        .append("Resource price: " + splitPrice(resourcePrice.getPrice()))
//                        .send(event.getMessageAuthor().asUser()
//                                .orElseThrow(IllegalArgumentException::new))
            event.getChannel().sendMessage("Успешно! Цены были отправлены вам в личные сообщения!");
        }
    }


    private String splitPrice(Long price) {
        var g = price / 10000;
        var s = price / 100 % 100;
        var c = price % 100;
        return g + "g " + s + "s " + c + "c";
    }
}
