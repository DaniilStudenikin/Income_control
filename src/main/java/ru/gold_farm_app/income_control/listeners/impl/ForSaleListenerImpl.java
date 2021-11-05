package ru.gold_farm_app.income_control.listeners.impl;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Component;
import ru.gold_farm_app.income_control.listeners.ForSaleListener;
import ru.gold_farm_app.income_control.model.ForSale;
import ru.gold_farm_app.income_control.services.EmployeeService;
import ru.gold_farm_app.income_control.services.ForSaleService;


import java.util.*;

@Component
public class ForSaleListenerImpl implements ForSaleListener {

    @Autowired
    private ForSaleService forSaleService;

    private static final Logger logger = LoggerFactory.getLogger(ForSaleListenerImpl.class);
    @Autowired
    private HikariDataSource dataSource;

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
                try {
                    createForSale(forSaleResourcesList, event);
                } catch (DataAccessResourceFailureException e) {
                    HikariPoolMXBean poolMXBean = dataSource.getHikariPoolMXBean();
                    poolMXBean.softEvictConnections();
                    createForSale(forSaleResourcesList, event);
                }
            }
        }
    }

    private void createForSale(List<String[]> forSaleResourcesList, MessageCreateEvent event) {
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
            new MessageBuilder().append("You can do it better bro...").send(event.getChannel());
        }
    }
}