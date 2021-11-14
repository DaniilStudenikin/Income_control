package ru.gold_farm_app.income_control.listeners.impl;


import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.gold_farm_app.income_control.listeners.ForSaleListener;
import ru.gold_farm_app.income_control.model.ForSale;
import ru.gold_farm_app.income_control.services.ForSaleService;


import java.util.*;
import java.util.regex.Pattern;

@Component
public class ForSaleListenerImpl implements ForSaleListener {

    @Autowired
    private ForSaleService forSaleService;

    private static final Logger logger = LoggerFactory.getLogger(ForSaleListenerImpl.class);

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().startsWith("!forSale")) {
            String formattedForSale = event.getMessageContent().replace(" ", "")
                    .substring(8).replace("\n", "")
                    .replace("-----------------------", "")
                    .replace("Herbs:", "")
                    .replace("Ores:", "")
                    .replace("Gems:", "")
                    .replace("Primals:", "");
            String[] splitedForSale = formattedForSale.split(",");
            logger.info("Список ресурсов предоставленный работником:");
            logger.info(Arrays.toString(splitedForSale));
            List<String[]> forSaleResourcesList = new ArrayList<>();
            for (String value : splitedForSale) {
                forSaleResourcesList.add(value.split("="));
            }
            if (forSaleResourcesList.size() != 28) {
                event.getChannel().sendMessage("Дорогой " + event.getMessageAuthor().getDisplayName() + ", проверь пожалуйста свое сообщение внимательно, у тебя там ошибки, как исправишь попробуй еще раз.");
            } else {
                createForSale(forSaleResourcesList, event.getMessageAuthor(), event);
            }
        }

        if (event.getMessageAuthor().getDisplayName().equals("fastrapier")
                && event.getMessageContent().startsWith("!delete-for-sale")) {
            Long id = Long.valueOf(event.getMessageContent().replace(" ", "").substring(16));
            try {
                forSaleService.delete(id);
                event.getChannel().sendMessage("For Sale с id=" + id + " удален.");
            } catch (IllegalArgumentException e) {
                event.getChannel().sendMessage("Список на продажу с таким id не существует. Попробуйте еще раз.");
            }
        }
    }

    private void createForSale(List<String[]> forSaleResourcesList, MessageAuthor employee, MessageCreateEvent event) {
        ForSale forSale = forSaleService.createForSale(forSaleResourcesList, employee);
        // Presenting price in 000g 000s 000c format
        var g = forSale.getPrice() / 10000;
        var s = forSale.getPrice() / 100 % 100;
        var c = forSale.getPrice() % 100;
        event.getChannel().sendMessage("Ты заработал сегодня - " + g + "g " + s + "s " + c + "c" + "!");
        if (g > 2500) {

            new MessageBuilder().append("Поздравляю! Ты крут!♥")
                    .send(event.getChannel());
        } else {

            new MessageBuilder().append("Сегодня не твой день, надеюсь завтра будет лучше...").send(event.getChannel());
        }
    }
}