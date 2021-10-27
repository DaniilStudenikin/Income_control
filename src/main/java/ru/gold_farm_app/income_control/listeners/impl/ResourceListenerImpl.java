package ru.gold_farm_app.income_control.listeners.impl;

import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.gold_farm_app.income_control.listeners.ResourceListener;
import ru.gold_farm_app.income_control.model.Resource;
import ru.gold_farm_app.income_control.repository.ResourceRepository;
import ru.gold_farm_app.income_control.services.ResourceService;

import java.util.List;

@Component
public class ResourceListenerImpl implements ResourceListener {

    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().equals("!resources")) {
            System.out.println(12312312);
            List<Resource> resources = resourceRepository.findAll();
            for (Resource res : resources) {
                new MessageBuilder().append("Name = " + res.getName() + ".")
                        .addAttachment(res.getIcon()).send(event.getChannel());
            }
        }
    }
}
