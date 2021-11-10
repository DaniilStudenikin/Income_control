package ru.gold_farm_app.income_control.listeners.impl;

import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.gold_farm_app.income_control.listeners.ResourceListener;
import ru.gold_farm_app.income_control.model.Resource;
import ru.gold_farm_app.income_control.repository.ResourceRepository;
import ru.gold_farm_app.income_control.services.ResourceService;


import java.awt.*;
import java.util.List;

@Component
public class ResourceListenerImpl implements ResourceListener {

    @Autowired
    private ResourceService resourceService;

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().equals("!resources") && event.getMessageAuthor().getDisplayName().equals("fastrapier")) {
            List<Resource> resources = resourceService.findAll();
            for (Resource res : resources) {
                event.getChannel().sendMessage(new EmbedBuilder()
                        .setTitle(res.getName())
                        .setImage(res.getIcon().toString())
                        .setColor(Color.ORANGE));
            }
        }
    }
}
