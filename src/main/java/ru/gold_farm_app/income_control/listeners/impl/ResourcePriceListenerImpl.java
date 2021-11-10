package ru.gold_farm_app.income_control.listeners.impl;

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
import java.util.regex.Pattern;

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
            LOG.info("Price updated!");
            event.getChannel().sendMessage("All prices are updated!");
        }
        if (event.getMessageContent().equals("!resource-price")) {
            sendPrice(event);
        }

        if (Pattern.matches("!resource-price \\d", event.getMessageContent())) {
            Long server = Long.parseLong(event.getMessageContent().replace("!resource-price", "").replace(" ", ""));
            try {
                sendPrice(event, server);
            } catch (IllegalArgumentException e) {
                event.getChannel().sendMessage("Сервера с таким id нет! Чтобы узнать id нужного вам сервера обратитесь к fastrapier#2112");
            }
        }
    }
    //Отправление цен по серверу вызвашего метод пользователя
    private void sendPrice(MessageCreateEvent event) {
        List<ResourcePrice> resourcePrices = resourcePriceService.resourcePrices(event);
        sendPriceMessage(event, resourcePrices);
    }
    //Отправка цен по выбранному пользователем айдишнику
    private void sendPrice(MessageCreateEvent event, Long server) {
        List<ResourcePrice> resourcePrices = resourcePriceService.resourcePricesByServer(event, server);
        sendPriceMessage(event, resourcePrices);
    }
    //Отправление сообщения,если вдруг захочу изменить само сообщение
    private void sendPriceMessage(MessageCreateEvent event, List<ResourcePrice> resourcePrices) {
        event.getMessageAuthor().asUser().orElseThrow(IllegalArgumentException::new)
                .sendMessage("Цены с сервера: " + resourcePrices.get(0).getServer().getServerFraction());

        for (ResourcePrice resourcePrice : resourcePrices) {
            EmbedBuilder resourcePriceMessage = new EmbedBuilder()
                    .setTitle(resourcePrice.getResource().getName())
                    .addField("Цена за единицу ресурса", splitPrice(resourcePrice.getPrice()))
                    .addField("Время последнего сканирования", resourcePrice.getScannedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .setColor(Color.BLUE)
                    .setImage(resourcePrice.getResource().getIcon().toString());

            event.getMessageAuthor().asUser().orElseThrow(IllegalArgumentException::new).sendMessage(resourcePriceMessage);
        }
        event.getChannel().sendMessage("Успешно! Цены были отправлены вам в личные сообщения!");
    }

    private String splitPrice(Long price) {
        var g = price / 10000;
        var s = price / 100 % 100;
        var c = price % 100;
        return g + "g " + s + "s " + c + "c";
    }
}
