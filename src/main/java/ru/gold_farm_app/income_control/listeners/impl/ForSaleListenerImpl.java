package ru.gold_farm_app.income_control.listeners.impl;

import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.gold_farm_app.income_control.listeners.ForSaleListener;
import ru.gold_farm_app.income_control.model.ForSale;
import ru.gold_farm_app.income_control.services.EmployeeService;
import ru.gold_farm_app.income_control.services.ForSaleService;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Component
public class ForSaleListenerImpl implements ForSaleListener {

    @Autowired
    private ForSaleService forSaleService;

    @Autowired
    private EmployeeService employeeService;
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
                ForSale forSale = forSaleService.createForSale(forSaleResourcesList, event.getMessageAuthor().getDiscriminatedName());
                ///
                var g = forSale.getPrice() / 10000;
                var s = forSale.getPrice() / 100 % 100;
                var c = forSale.getPrice() % 100;
                event.getChannel().sendMessage("Your profit for today - " + g + "g " + s + "s " + c + "c" + "!");
                if (g > 2500) {
                    new MessageBuilder().append("Gratz!")
                            .send(event.getChannel());
                } else {
                    new MessageBuilder().append("You can do it better bro...").send(event.getChannel() );
                }
            }
        }
    }
}