package ru.gold_farm_app.income_control.listeners.impl;


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
            logger.info(Arrays.toString(splitedForSale));
            List<String[]> forSaleResourcesList = new ArrayList<>();
            for (String value : splitedForSale) {
                forSaleResourcesList.add(value.split("="));
            }
            if (forSaleResourcesList.size() != 28) {
                event.getChannel().sendMessage("Dear " + event.getMessageAuthor().getDisplayName() + ", please check you message and try again! You have some mistakes.");
            } else {
                createForSale(forSaleResourcesList, event);
            }
        }
        //Надо придумать паттерн чисел для бесконечного количества айдишников
        if (event.getMessageAuthor().getDisplayName().equals("fastrapier")
                && (Pattern.matches("!delete-for-sale \\d", event.getMessageContent())
                || Pattern.matches("!delete-for-sale \\d\\d", event.getMessageContent())
                || Pattern.matches("!delete-for-sale \\d\\d\\d", event.getMessageContent()))) {
            Long id = Long.valueOf(event.getMessageContent().replace(" ", "").substring(17));
            try {
                forSaleService.delete(id);
                event.getChannel().sendMessage("For Sale с id=" + id + " удален.");
            } catch (IllegalArgumentException e) {
                event.getChannel().sendMessage("Список на продажу с таким id не существует. Попробуйте еще раз.");
            }
        }
    }

    private void createForSale(List<String[]> forSaleResourcesList, MessageCreateEvent event) {
        ForSale forSale = forSaleService.createForSale(forSaleResourcesList, event.getMessageAuthor().getDiscriminatedName());
        // Presenting price in 000g 000s 000c format
        var g = forSale.getPrice() / 10000;
        var s = forSale.getPrice() / 100 % 100;
        var c = forSale.getPrice() % 100;
        event.getChannel().sendMessage("Your profit for today - " + g + "g " + s + "s " + c + "c" + "!");
        if (g > 2500) {
            new MessageBuilder().append("Gratz!")
                    .send(event.getChannel());
        } else {
            new MessageBuilder().append("You can do it better bro...").send(event.getChannel());
        }
    }
}